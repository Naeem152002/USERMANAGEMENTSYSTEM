package com.usermanagementsystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.usermanagementsystem.config.AppConstants;
import com.usermanagementsystem.entity.Role;
import com.usermanagementsystem.repo.RoleRepo;
@Component
public class DataInitiliazer implements CommandLineRunner {
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public void run(String... args) throws Exception {
		
		Role r1=new Role();
		r1.setId(AppConstants.ADMIN_USER);
		r1.setName("ROLE_ADMIN");
		
		Role r2=new Role();
		r2.setId(AppConstants.NORMAL_USER);
		r2.setName("ROLE_NORMAL");
		
		List<Role>roles=List.of(r1,r2);
		
		roleRepo.saveAll(roles);
		
	roles.forEach(role->{
		System.out.println(role.getName());	
	});
		
	}

}
