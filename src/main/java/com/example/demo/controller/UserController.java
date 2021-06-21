package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.http.auth.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.JwtResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.serviceImpl.UserServiceImpl;
import com.example.demo.utility.JWTUtility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * @author mohd.hussain
 *
 */
@Api(value = "UserController", tags = { "User Controller" })
@SwaggerDefinition(tags = {
		@Tag(name = "User Controller", description = "User Controller contains some end points related to user operation") })
@RestController
@RequestMapping("/api")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	UserService userService;

	@Value("${server.port}")
	private int portNumber;

	@ApiOperation(value = "server port number")
	@GetMapping("/port")
	public int getPortNumber() {
		return portNumber;
	}

	@ApiOperation(value = "login end point")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "404 error") })
	@PostMapping("/authenticate")
	public JwtResponse authenticateUser(@Valid @RequestBody UserDto userdto) throws Exception {
		logger.info("inside authenticate method ====   {}  " + userdto);

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userdto.getUserName(), userdto.getPassword()));
		} catch (BadCredentialsException e) {
			throw new InvalidCredentialsException("INVALID_CREDENTIALS", e);
		}

		final UserDetails userDetails = userServiceImpl.loadUserByUsername(userdto.getUserName());

		final String token = jwtUtility.generateToken(userDetails);

		return new JwtResponse(token);
	}

	@ApiOperation(value = "get all users")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "404 error") })
	@GetMapping("/users")
	public List<User> getAllUsers() {
		logger.info("inside getAllUsers method ====   {}");
		return userService.findAllUsers();
	}

	@ApiOperation(value = "get user by username")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "404 error") })
	@GetMapping("/user")
	public User getUserByUserName(@RequestParam String userName) {
		logger.info("inside getUserByUserName method ====   {}");
		return userService.getUser(userName);

	}

}
