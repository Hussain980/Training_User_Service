package com.example.demo.service;

import org.springframework.beans.BeanUtils;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

public class UserServiceUtils {

	public static User convertDtoToModel(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		return user;
	}
	
}
