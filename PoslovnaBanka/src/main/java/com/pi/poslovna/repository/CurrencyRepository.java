package com.pi.poslovna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.poslovna.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
