package com.usermanagementsystem.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermanagementsystem.config.AppConstants;
import com.usermanagementsystem.dto.RoleDto;
import com.usermanagementsystem.dto.UserDto;
import com.usermanagementsystem.entity.Role;
import com.usermanagementsystem.entity.User;
import com.usermanagementsystem.exceptions.ResourceNotFoundException;
import com.usermanagementsystem.repo.RoleRepo;
import com.usermanagementsystem.repo.UserRepo;


@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createAdmin(UserDto userDto) {
		if(userRepo.findByEmail(userDto.getEmail())!=null) {
			return null;
		}else {
			User user=modelMapper.map(userDto, User.class);
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			Role role=roleRepo.findById(AppConstants.ADMIN_USER).get();
			user.getRoles().add(role);
			User save=userRepo.save(user);
			return modelMapper.map(save, UserDto.class);
		}}

	@Override
	public UserDto updateUser(UserDto userDto, int id) {
	    User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
	    user.setName(userDto.getName());
	    user.setEmail(userDto.getEmail());
	    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
	    user.setCity(userDto.getCity());
	    User save=userRepo.save(user);
		return modelMapper.map(save, UserDto.class);
	}
	@Override
	public void deleteUser(int id) {
		 User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		 user.getRoles().clear();
		 userRepo.delete(user);
		
	}
	@Override
	public UserDto getSingleUserById(int id) {
		 User user=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		 return modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User>listUsers=userRepo.findAll();
		return listUsers.stream().map(user->modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
	}
	@Override
	public UserDto createUser(UserDto userDto) {
		if(userRepo.findByEmail(userDto.getEmail())!=null) {
			return null;
		}else {
		User user=modelMapper.map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		Role role=roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User save=userRepo.save(user);
		return modelMapper.map(save, UserDto.class);
	}}
	@Override
	public UserDto getProfile(String email){
        UserDto dto = new UserDto();
            User user = userRepo.findByEmail(email);
            if (user!=null) {
            	dto.setId(user.getId());
                dto.setName(user.getName());
                dto.setEmail(user.getEmail());
                dto.setPassword(user.getPassword());
                dto.setCity(user.getCity());
                Set<RoleDto> roleDtos = user.getRoles().stream().map(role -> {
                    RoleDto roleDto = new RoleDto();
                    roleDto.setId(role.getId());
                    roleDto.setName(role.getName());  // Assuming Role entity has a field 'roleName'
                    return roleDto;
                }).collect(Collectors.toSet());
                dto.setRoles(roleDtos);
                return dto;
            } else {
                throw new ResourceNotFoundException("User","Id",0);

    }}
	
	@Override
	public UserDto updateProfile(UserDto userDto,String email){
            User user = userRepo.findByEmail(email);
            int id=user.getId();
            User save=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id",id));
            save.setName(userDto.getName());
    	    save.setEmail(userDto.getEmail());
    	    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	    save.setCity(userDto.getCity());
    	    User save1=userRepo.save(save);
    		return modelMapper.map(save1, UserDto.class);
    }

	@Override
	public void deleteProfile(String email) {
		 User user = userRepo.findByEmail(email);
         int id=user.getId();
         User save=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","Id",id));
         save.getRoles().clear();
         userRepo.delete(save);
	}

}
