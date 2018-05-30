package com.pi.poslovna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.poslovna.model.clients.Individuals;

@Repository
public interface IndividualClientRepository extends JpaRepository<Individuals, Long>{

}
