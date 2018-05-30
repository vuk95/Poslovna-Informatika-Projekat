package com.pi.poslovna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.poslovna.model.users.User;
import com.pi.poslovna.repository.UserRepository;
import com.pi.poslovna.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User getUserById(Long Id) {
		return userRepository.findOne(Id);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
