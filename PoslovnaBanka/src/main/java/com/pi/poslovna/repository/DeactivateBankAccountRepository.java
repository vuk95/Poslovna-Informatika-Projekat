package com.pi.poslovna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.poslovna.model.DeactivateBankAccount;

@Repository
public interface DeactivateBankAccountRepository extends JpaRepository<DeactivateBankAccount, Long> {

	List<DeactivateBankAccount> findByAccountNumber(String accountNumber);
}
