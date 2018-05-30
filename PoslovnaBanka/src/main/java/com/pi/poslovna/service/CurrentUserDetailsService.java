package com.pi.poslovna.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pi.poslovna.model.users.CurrentUser;
import com.pi.poslovna.model.users.User;

@Service
@Transactional
public class CurrentUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.getUserByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new CurrentUser(user);
	}
}
