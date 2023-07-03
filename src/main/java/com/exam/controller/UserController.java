package com.exam.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

@Controller
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController
{
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepo;
	
	
	
	
	//Create User
	@PostMapping("")
	public ResponseEntity<User> createUser(@RequestBody User user) throws Exception
	{
		//Set Roles and Save User
		Set<UserRole> roles=new HashSet<>();
	    Role role=new Role();
       // role.setRoleId(32L);
        role.setRoleName("Normal");
        
        UserRole userRole=new UserRole();
         
        userRole.setUser(user);
        userRole.setRole(role);
                
	    roles.add(userRole);
	    try {
	       User user2=this.userService.createUser(user, roles);
	       return ResponseEntity.of(Optional.of(user2)); 
	       }catch(Exception e)
	      {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	      }
	    
	}

	
	@GetMapping("/username/{username}")
	public User getUser(@PathVariable("username") String username)
	{
	   return this.userService.getUser(username);
	}
	
	
	//delete user by Id
	@DeleteMapping("/delete/{userId}")
	public void deleteUser(@PathVariable Long userId)
	{
		this.userService.deleteUser(userId);
	
	
	
}
}















