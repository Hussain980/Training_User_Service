package com.example.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


public class UserDto {

	
	@NotEmpty(message = "UserName is Required")
	@Email(message = "UserName should be a valid email")
	private String userName;
	
	@NotEmpty(message = "Password is Required")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDto [userName=" + userName + ", password=" + password + "]";
	}
	
	
}


