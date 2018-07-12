package com.pi.poslovna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.poslovna.model.InterbankTransfer;
import com.pi.poslovna.model.MessageTypes;

@Repository
public interface InterbankTransferRepository extends JpaRepository<InterbankTransfer, Long> {
	public List<InterbankTransfer> findByTypeOfMessage(MessageTypes typeOfMessage);
	
}
