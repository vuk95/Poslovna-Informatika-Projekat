package com.pi.poslovna.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.model.clients.LegalEntities;
import com.pi.poslovna.model.dto.BankAccountDTO;
import com.pi.poslovna.service.BankService;
import com.pi.poslovna.service.IndividualClientService;
import com.pi.poslovna.service.LegalEntityService;

@Component
public class BankAccountDTOToBankAccount implements Converter<BankAccountDTO, BankAccount> {

	@Autowired
	private IndividualClientService clientService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private LegalEntityService leService;

	@Override
	public BankAccount convert(BankAccountDTO source) {
		
		if(source == null) {
			return null;
		}
		
		BankAccount account = new BankAccount();
		account.setId(source.getId());
		account.setAccountNumber(source.getAccountNumber());
		account.setOpeningDate(source.getOpeningDate());
		account.setMoney(source.getMoney());
		account.setActive(source.isActive());
		account.setClientType(source.getClientType());
		
		if(source.getIndividualId() != null) {
			Individuals person = clientService.findOne(source.getIndividualId());
			account.setIndividual(person);	
		}
		
		if(source.getLegalEntityId() != null) {
			LegalEntities le = leService.findOne(source.getLegalEntityId());
			account.setLegalEntity(le);
		}
		
		if(source.getBankId() != null) {
			Bank bank = bankService.findOne(source.getBankId());
			account.setBank(bank);
		}
		
		return null;
	}
	
}
