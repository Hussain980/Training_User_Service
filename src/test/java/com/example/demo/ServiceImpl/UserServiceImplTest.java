package com.example.demo.ServiceImpl;

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
import com.example.demo.repository.UserRepository;
import com.example.demo.serviceImpl.UserServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	UserRepository userRepo;

	@InjectMocks
	UserServiceImpl userServiceImpl;

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
	@DisplayName("authentication : positive scenario")
	public void testAuthenticateUser() {
		when(userRepo.findByUserNameAndPassword("test@gmail.com", "1234")).thenReturn(user1);

		String result = userServiceImpl.findByUserNameAndPassword(userDto);

		assertEquals("Login Success", result);
	}

	@Test
	@DisplayName("authentication : negative scenario")
	public void testAuthenticateUser1() {
		when(userRepo.findByUserNameAndPassword("test@gmail.com", "1234")).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> userServiceImpl.findByUserNameAndPassword(userDto));
	}

	@Test
	@DisplayName("Find all Users : positive scenario")
	public void testFindAllUsers() {
		List<User> userlist = new ArrayList<>();
		userlist.add(user1);
		userlist.add(user2);

		when(userRepo.findAll()).thenReturn(userlist);
		List<User> user = userServiceImpl.findAllUsers();
		assertEquals(userlist, user);
	}

	@Test
	@DisplayName("Find all Users : negative scenario")
	public void testFindAllUsers1() {
		List<User> userlist = new ArrayList<>();
		userlist.add(user1);
		userlist.add(user2);

		when(userRepo.findAll()).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> userServiceImpl.findAllUsers());
	}

	@Test
	@DisplayName("Find user By name : positive scenario")
	public void testGetUserbyName() {
		when(userRepo.findByUserName("test@gmail.com")).thenReturn(user1);
		User userdb = userServiceImpl.getUser("test@gmail.com");
		assertEquals(user1, userdb);
	}

	@Test
	@DisplayName("Find user By name : negative scenario")
	public void testGetUserbyName1() {
		when(userRepo.findByUserName("test@gmail.com")).thenReturn(null);
		assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUser("test@gmail.com"));
	}

}
