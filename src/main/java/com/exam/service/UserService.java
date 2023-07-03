package com.exam.service;

import java.util.Set;

import com.exam.model.User;
import com.exam.model.UserRole;

public interface UserService 
{
	//Create User
	//Set role of user
	public User createUser(User user, Set<UserRole> userRoles) throws Exception;

	//Get user by username
	public User getUser(String username);
	
	//Delete user by Id
	public void deleteUser(Long userId); 
}
