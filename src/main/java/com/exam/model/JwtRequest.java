package com.exam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtRequest 
{
  // this class will handle data when user will be logen;
 // when we send response then we have to send jwt token so we will create another class -->JwtResponse	
	String username;
	String password;
	

}
