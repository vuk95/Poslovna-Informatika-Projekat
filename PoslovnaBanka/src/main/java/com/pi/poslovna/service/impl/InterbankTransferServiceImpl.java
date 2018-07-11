package com.pi.poslovna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.InterbankTransfer;
import com.pi.poslovna.repository.InterbankTransferRepository;
import com.pi.poslovna.service.InterbankTransferService;


@Transactional
@Service
public class InterbankTransferServiceImpl implements InterbankTransferService {

	@Autowired
	private InterbankTransferRepository repository;
	
	@Override
	public InterbankTransfer findOne(Long id) {
		return repository.findOne(id);
	}


	@Override
	public List<InterbankTransfer> findAll() {
		return repository.findAll();
	}

	@Override
	public void save(InterbankTransfer it) {
		repository.save(it);
	}



	

	
	
}
