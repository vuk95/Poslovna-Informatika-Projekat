package com.pi.poslovna.service;

import java.util.List;

import com.pi.poslovna.model.users.User;

public interface UserService {

	User getUserById(Long Id);
	
	User getUserByEmail(String email);
	
	List<User> getAllUsers();
	
}
