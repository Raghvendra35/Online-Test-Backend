package com.exam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exam.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MySecurityConfig 
{
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean() 
	{
		//return super.authenticationManagerBean();
		return null;

	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
	 return new BCryptPasswordEncoder();	
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity)
	{
		
		return httpSecurity.csrf()
				            .disable()
				            .cors()
				            .disable()
				            .authorizeHttpRequests()
				            .requestMatchers("/generate-token","/user/").permitAll()
				            .requestMatchers(HttpMethod.GET).permitAll()
				            .anyRequest()
				            .authenticated()
				            .and()
				            .exceptionHandling()
				            .authenticationEntryPoint(unauthorizedHandler)
				            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				            .and()
				            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				            
				            ;
				            
				            
	}
	
	
}

















