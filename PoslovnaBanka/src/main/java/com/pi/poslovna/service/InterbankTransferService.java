package com.pi.poslovna.service;

import java.util.List;

import com.pi.poslovna.model.InterbankTransfer;

public interface InterbankTransferService {

	public InterbankTransfer findOne(Long id);
	public List<InterbankTransfer> findAll();
	public void save(InterbankTransfer it);
	
}
