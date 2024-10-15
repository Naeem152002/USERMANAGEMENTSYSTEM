package com.usermanagementsystem.services;

import java.util.List;

import com.usermanagementsystem.dto.UserDto;

public interface UserService {
	
	public UserDto createAdmin(UserDto userDto);
	public UserDto updateUser(UserDto userDto,int id);
	public void deleteUser(int id);
	public UserDto getSingleUserById(int id);
	public List<UserDto>getAllUsers();
	public UserDto createUser(UserDto userDto);
	public UserDto getProfile(String email);
	public UserDto updateProfile(UserDto userDto,String email);
	public void deleteProfile(String email);
	
}
