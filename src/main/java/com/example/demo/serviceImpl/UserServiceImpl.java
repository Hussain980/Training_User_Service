package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import net.bytebuddy.asm.Advice.OffsetMapping.ForThisReference;

/**
 * @author mohd.hussain
 *
 */
@Service
public class UserServiceImpl implements UserService,UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(ForThisReference.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	public String findByUserNameAndPassword(UserDto userdto) {
		logger.info("inside userService ==== {} "+ userdto);
		User userExist = userRepository.findByUserNameAndPassword(userdto.getUserName(),userdto.getPassword());
		if(userExist == null) {
			logger.warn(">>>>>>>>>>>>>>  {}   User Doesn't exist");
			throw new UserNotFoundException("User Not Found With this Credentials");
		}
		  return "Login Success";
		
	}

	@Override
	public List<User> findAllUsers() {
	   return userRepository.findAll();
	}

	@Override
	public User getUser(String userName) {
		User user = userRepository.findByUserName(userName);
		if(user == null) {
			throw new UserNotFoundException("User Doesn't exist");
		}
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		User user =  userRepository.findByUserName(userName);
		if(user == null) {
			throw new UserNotFoundException("User doesn't exist");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
	}

}
