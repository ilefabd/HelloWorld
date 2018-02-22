package com.info.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.model.Role;
import com.info.model.User;
import com.info.repo.RoleRepository;
import com.info.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	
	
	@Override
	public void saveFinacier(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("Financier");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);		
	}
	
	

}