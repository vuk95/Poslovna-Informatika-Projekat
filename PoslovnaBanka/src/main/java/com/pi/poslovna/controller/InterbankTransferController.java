package com.pi.poslovna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.converters.InterbankTransferToInterbankTransferDTO;
import com.pi.poslovna.model.InterbankTransfer;
import com.pi.poslovna.model.dto.InterbankTransferDTO;
import com.pi.poslovna.service.InterbankTransferService;

@RestController
public class InterbankTransferController {
	
	@Autowired
	private InterbankTransferService interbankService; 
	
	@Autowired
	private InterbankTransferToInterbankTransferDTO toInterbankDtoService; 
	
	@RequestMapping(value = "/interbankTransfers" , method = RequestMethod.GET)
	public ResponseEntity<List<InterbankTransferDTO>> getInterbankTransfers() {
		
		List<InterbankTransfer> transfers = interbankService.findAll();
		List<InterbankTransferDTO> transfersdto = toInterbankDtoService.convert(transfers);
		
		return new ResponseEntity<>(transfersdto,HttpStatus.OK);
	}
	
}
