package com.usermanagementsystem.dto;

import java.util.HashSet;
import java.util.Set;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private int id;
	@NotEmpty
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
	private String name;
	
	
	@NotEmpty(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
	
	
	@NotEmpty(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one digit, one lowercase, one uppercase letter, and one special character")
    private String password;
	
	private String city;
	
	
	Set<RoleDto>roles=new HashSet<>();
}
