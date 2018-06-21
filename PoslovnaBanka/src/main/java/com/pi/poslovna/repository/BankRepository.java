package com.pi.poslovna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.poslovna.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
