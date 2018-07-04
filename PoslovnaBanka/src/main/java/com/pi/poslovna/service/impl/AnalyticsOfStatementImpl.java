package com.pi.poslovna.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.poslovna.model.AnalyticsOfStatement;
import com.pi.poslovna.repository.AnalyticsOfStatementRepository;
import com.pi.poslovna.service.AnalyticsOfStatementService;

@Transactional
@Service
public class AnalyticsOfStatementImpl implements AnalyticsOfStatementService{

	@Autowired
	private AnalyticsOfStatementRepository analyticsRepository;
	
	@Override
	public void save(AnalyticsOfStatement analyticsOfStatement) {
		analyticsRepository.save(analyticsOfStatement);	
	}

}
