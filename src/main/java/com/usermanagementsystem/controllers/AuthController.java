package com.usermanagementsystem.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagementsystem.dto.UserDto;
import com.usermanagementsystem.entity.JwtRequest;
import com.usermanagementsystem.response.JwtResponse;
import com.usermanagementsystem.security.JwtTokenHelper;
import com.usermanagementsystem.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	AuthenticationManager authenticationManger;
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
		@PostMapping("/login")
		public ResponseEntity<JwtResponse>login(@Valid @RequestBody JwtRequest jwtRequest){
			
			this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
			
			UserDetails userDetails=userDetailsService.loadUserByUsername(jwtRequest.getEmail());
			String token=jwtTokenHelper.generateToken(userDetails);
			
			JwtResponse response=JwtResponse.builder().token(token).username(userDetails.getUsername()).build();
			
			return new ResponseEntity<JwtResponse>(response,HttpStatus.CREATED);
		}
	
		public void doAuthenticate(String email,String password) {
			UsernamePasswordAuthenticationToken up=new UsernamePasswordAuthenticationToken(email, password);
			try {
				authenticationManger.authenticate(up);
			
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Username and Password!!");
		}
		
	}
		
		@PostMapping("/admin")
		public ResponseEntity<?>creatAdmin(@Valid @RequestBody UserDto userDto){
			UserDto dto=userService.createAdmin(userDto);
			if(dto==null) {
			return	new ResponseEntity<>("Email Already Exist",HttpStatus.CONFLICT);
			}
			else {
			return	ResponseEntity.ok(dto);
		}
		}
		@PostMapping("/user")//admin register user and user rgister himself
		public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
			UserDto dto = userService.createUser(userDto);
			if (dto == null) {
				return new ResponseEntity<>("Email Already Exist", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<UserDto>(dto, HttpStatus.OK);
			}
		}

}
