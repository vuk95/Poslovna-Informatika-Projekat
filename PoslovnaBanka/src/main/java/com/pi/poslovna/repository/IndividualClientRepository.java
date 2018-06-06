package com.pi.poslovna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.poslovna.model.clients.Individuals;

@Repository
public interface IndividualClientRepository extends JpaRepository<Individuals, Long>{
		
	List<Individuals> findByNameIgnoreCaseContainingAndLastnameIgnoreCaseContainingAndJmbgIgnoreCaseContainingAndPlaceIgnoreCaseContainingAndAddressIgnoreCaseContainingAndEmailIgnoreCaseContainingAndPhoneIgnoreCaseContaining(String name,String lastname,String jmbg,String place,String address,String email,String phone);
	
}
