package com.usermanagementsystem.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.usermanagementsystem.security.JwtAuthenticationEntryPoint;
import com.usermanagementsystem.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	 @Autowired
	 private JwtAuthenticationEntryPoint authenticationPoint;
	   
	 @Autowired
	  private JwtAuthenticationFilter filter;
	
	 
	
	@Bean
	SecurityFilterChain getSecurity(HttpSecurity http) throws Exception {
		
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(authorize->authorize.
				 requestMatchers("/auth/**").permitAll().
				 requestMatchers("/admin/**").hasRole("ADMIN").
				 requestMatchers(HttpMethod.GET).permitAll().//in this generate documetation
				anyRequest()
				 .authenticated()).exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationPoint))
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//jwt work stateless machanism
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManger(AuthenticationConfiguration c) throws Exception {
		return c.getAuthenticationManager();
	}

}
