package com.pi.poslovna.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.users.User;
import com.pi.poslovna.service.UserService;

@RestController
public class BankController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/myBank", method = RequestMethod.GET)
	public ResponseEntity<Bank> getMyBank(Principal principal) {
		User user = userService.getUserByEmail(principal.getName());
		Bank bank = user.getBank();
		
		return new ResponseEntity<>(bank, HttpStatus.OK);
	}
}
