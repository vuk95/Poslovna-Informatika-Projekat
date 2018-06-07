package com.pi.poslovna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.poslovna.model.clients.LegalEntities;

@Repository
public interface LegalEntityRepository  extends JpaRepository<LegalEntities, Long>{

	List<LegalEntities> findByNameIgnoreCaseContainingAndResponsiblePersonIgnoreCaseContainingAndPibIgnoreCaseContainingAndPlaceIgnoreCaseContainingAndAddressIgnoreCaseContainingAndEmailIgnoreCaseContainingAndPhoneIgnoreCaseContainingAndFaxIgnoreCaseContaining(String name,String responsiblePerson,String pib,String place,String address,String email,String phone,String fax);

}
