package com.exam.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtils jwtUtil;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
	 final String requestTokenHeader=request.getHeader("Authorization");
	
	 System.out.println(requestTokenHeader);
	 String username=null;
	 String jwtToken=null;
	 if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer "))
	 {
		 //token valid
		 		 
			 jwtToken=requestTokenHeader.substring(7);
		try {
			 username =this.jwtUtil.extractUsername(jwtToken);
		 }catch(ExpiredJwtException e)
		 {
			 e.printStackTrace();
			 System.out.println("Jwt Token has expired !!!");
		 }catch(Exception e)
		{
			 e.printStackTrace();
			 System.out.println("Error ");
		}
	 }else
		 
	 {
		 System.out.println("Invalid token Not start with Bearer....");
	 }
	 
	 //Once we got the token then validate 
	 if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
	 {
	  final UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
	  if(jwtUtil.validateToken(jwtToken, userDetails))
	  {
		  //token is valid
		  //Set Authentication
		  UsernamePasswordAuthenticationToken usernamePasswordAuthentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		  usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
	  }
	  else
	  {
		  System.out.println("Token is not valid !!!");
	  }
	  
	  filterChain.doFilter(request, response);
	 }
	}
	

	
}














