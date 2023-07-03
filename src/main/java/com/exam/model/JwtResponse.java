package com.exam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse 
{
	
	String token;
	
	
     public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


}
