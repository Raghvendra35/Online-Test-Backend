package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.config.JwtUtils;
import com.exam.helper.UsernameNotFoundException;
import com.exam.model.JwtRequest;
import com.exam.model.JwtResponse;
import com.exam.service.impl.UserDetailsServiceImpl;

import ch.qos.logback.core.subst.Token;

@RestController
public class AuthenticateController
{
	
	
    @Autowired
	private AuthenticationManager authenticationnManager;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired 
	private JwtUtils jwtUtils;
	
	
	//Generate token
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest)
	{
		try {
			
			this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			
		}catch(UserNotFoundException e)
		{
			e.printStackTrace();
			throw new Exception("User not Found !!!");
			
		}
		//User has been Authenticated
		//Now load user from user service 
	  UserDetails userDetails=this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
	//Now generate token
	  
	  String token
	  =this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	
	
	private void authenticate(String username, String password) throws Exception
	{
	  	try
	  	{
	  		authenticationnManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	  		
	  	}catch(DisabledException e)
	  	{
	  		throw new Exception("User Disabled "+  e.getMessage());
	  	}catch(BadCredentialsException b)
	  	{
	  		throw new Exception("Invalid Credentials"+ b.getMessage());
	  	}
	}
}
