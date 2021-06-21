
package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;

/**
 * @author mohd.hussain
 *
 */

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	UserService userService;

	@InjectMocks
	UserController userController;

	static UserDto userDto;

	static User user1;

	static User user2;

	@BeforeAll
	public static void setup() {
		userDto = new UserDto();
		userDto.setUserName("test@gmail.com");
		userDto.setPassword("1234");

		user1 = new User();
		user2 = new User();
		user1.setUserName("test@gmail.com");
		user1.setPassword("1234");
		user2.setUserName("demo@gmail.com");
		user2.setPassword("123456");
	}

	@Test
	@DisplayName("Find all Users : positive scenario")
	public void testFindAllUsers() {
		List<User> userlist = new ArrayList<>();
		userlist.add(user1);
		userlist.add(user2);

		when(userService.findAllUsers()).thenReturn(userlist);
		List<User> user = userController.getAllUsers();
		assertEquals(userlist, user);
	}

	@Test
	@DisplayName("Find all Users : negative scenario")
	public void testFindAllUsers1() {
		List<User> userlist = new ArrayList<>();
		userlist.add(user1);
		userlist.add(user2);

		when(userService.findAllUsers()).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> userController.getAllUsers());
	}

	@Test
	@DisplayName("Find user By name : positive scenario")
	public void testGetUserbyName() {
		when(userService.getUser("test@gmail.com")).thenReturn(user1);
		User userdb = userController.getUserByUserName("test@gmail.com");
		assertEquals(user1, userdb);
	}

	@Test
	@DisplayName("Find user By name : negative scenario")
	public void testGetUserbyName1() {
		when(userService.getUser("test@gmail.com")).thenReturn(null);
		User userdb = userController.getUserByUserName("test@gmail.com");
		assertEquals(null, userdb);
	}

}
