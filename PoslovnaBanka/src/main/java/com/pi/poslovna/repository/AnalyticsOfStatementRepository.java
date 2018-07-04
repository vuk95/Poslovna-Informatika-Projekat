package com.pi.poslovna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.poslovna.model.AnalyticsOfStatement;

@Repository
public interface AnalyticsOfStatementRepository extends JpaRepository<AnalyticsOfStatement, Long>{

}
