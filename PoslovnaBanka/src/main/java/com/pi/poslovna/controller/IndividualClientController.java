package com.pi.poslovna.controller;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.poslovna.converters.IndividualsDTOToIndividuals;
import com.pi.poslovna.converters.IndividualsToIndividualsDTO;
import com.pi.poslovna.model.Bank;
import com.pi.poslovna.model.BankAccount;
import com.pi.poslovna.model.DeactivateBankAccount;
import com.pi.poslovna.model.clients.Individuals;
import com.pi.poslovna.model.dto.IndividualsDTO;
import com.pi.poslovna.model.users.User;
import com.pi.poslovna.service.BankAccountService;
import com.pi.poslovna.service.DeactivateBankAccountService;
import com.pi.poslovna.service.IndividualClientService;
import com.pi.poslovna.service.UserService;


@RestController
@RequestMapping(value = "/individual")
public class IndividualClientController {

	@Autowired
	private IndividualClientService clientService;
	
	@Autowired
	private IndividualsToIndividualsDTO toIndividualsDTO;
	
	@Autowired
	private IndividualsDTOToIndividuals toIndividuals;
	
	//@Autowired
	//private BankAccountDTOToBankAccount toBankAccount;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BankAccountService accountService;
	
	@Autowired
	private DeactivateBankAccountService deactivateService;
	
	@RequestMapping(value="getIndividuals", method = RequestMethod.GET)
	public ResponseEntity<List<IndividualsDTO>> getIndividuals() {

		List<Individuals> individuals = clientService.findAll();

		return new ResponseEntity<>(toIndividualsDTO.convert(individuals), HttpStatus.OK);
	}
	
	@RequestMapping(value="getBankIndividuals", method = RequestMethod.GET)
	public ResponseEntity<List<IndividualsDTO>> getBankIndividuals(Principal principal) {
		User user = userService.getUserByEmail(principal.getName());
		Bank bank = user.getBank();
		List<Individuals> individuals = bank.getIndividualClients();
		
		return new ResponseEntity<>(toIndividualsDTO.convert(individuals), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
	public ResponseEntity<IndividualsDTO> getIndividual(@PathVariable Long id) {
		
		Individuals person = clientService.findOne(id);
		
		if(person == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		
		return new ResponseEntity<>(toIndividualsDTO.convert(person),HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<?> addIndividuals(@Validated @RequestBody IndividualsDTO individualDTO, Errors errors, Principal principal){
		User user = userService.getUserByEmail(principal.getName());
		
		if(errors.hasErrors()) {
			return new ResponseEntity<String>(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
		}
		
		individualDTO.setBankId(user.getBank().getId());
		Individuals newIndividual = clientService.save(toIndividuals.convert(individualDTO));
		
		return new ResponseEntity<>(toIndividualsDTO.convert(newIndividual), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}" , method = RequestMethod.DELETE)
	public ResponseEntity<IndividualsDTO> deleteIndividuals(@PathVariable Long id) {
		
		Individuals deletedIndiviual = clientService.delete(id); 
		
		return new ResponseEntity<>(toIndividualsDTO.convert(deletedIndiviual),HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT , consumes="application/json" )
	public ResponseEntity<?> updateIndividuals(@RequestBody IndividualsDTO individualDTO) {
		
		Individuals persons = clientService.findOne(toIndividuals.convert(individualDTO).getId());
		
		persons.setName(toIndividuals.convert(individualDTO).getName());
		persons.setLastname(toIndividuals.convert(individualDTO).getLastname());
		persons.setJmbg(toIndividuals.convert(individualDTO).getJmbg());
		persons.setAddress(toIndividuals.convert(individualDTO).getAddress());
		persons.setPlace(toIndividuals.convert(individualDTO).getPlace());
		persons.setEmail(toIndividuals.convert(individualDTO).getEmail());
		persons.setPhone(toIndividuals.convert(individualDTO).getPhone());
		persons.setClientType(toIndividuals.convert(individualDTO).getClientType());
		
		clientService.save(persons);
		
		return new ResponseEntity<>(toIndividualsDTO.convert(persons),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findIndividuals", method=RequestMethod.GET, consumes="application/json")
	public ResponseEntity<List<IndividualsDTO>> find(@RequestParam(value = "name", required = false, defaultValue = "") String name, 
			@RequestParam(value = "lastname", required = false, defaultValue = "") String lastname, @RequestParam(value = "jmbg", required = false, defaultValue = "") String jmbg,
			@RequestParam(value = "place", required = false, defaultValue = "") String place,
			@RequestParam(value = "address", required = false, defaultValue = "") String address, @RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "phone", required = false, defaultValue = "") String telephone){
		
		List<Individuals> individuals = clientService.findIndividuals(name, lastname, jmbg, place, address, email, telephone);
		
		return new ResponseEntity<>(toIndividualsDTO.convert(individuals), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/indivudalClient/{id}" , method = 	RequestMethod.GET)
	public ResponseEntity<Individuals> profile(@PathVariable() Long id) {
		
		Individuals person = clientService.findOne(id);
		
		return  new ResponseEntity<>(person,HttpStatus.OK);
	}
	
	//Otvaranje racuna fizickog lica
	@RequestMapping(value = "/indivudalClient/{id}/openBankAccount" , method = RequestMethod.POST, consumes="application/json")
	public ResponseEntity<BankAccount> openBankAccount(@PathVariable() Long id ,@RequestBody BankAccount account) {
		Individuals person = clientService.findOne(id);
		
		Calendar calendar = Calendar.getInstance();
		Date today = new Date(calendar.getTime().getTime());
		
		account.setOpeningDate(today);
		account.setIndividual(person);
		account.setBank(person.getBank());
		account.setClientType(person.getClientType());
		
		BankAccount newAccount = accountService.save(account);
		clientService.addBankAccount(newAccount, person.getId());
		
		return new ResponseEntity<>(newAccount,HttpStatus.OK); 
	}
	
	@RequestMapping(value = "/individualClient/{id}/getBankAccounts" , method = RequestMethod.GET)
	public ResponseEntity<List<BankAccount>> getAllAccounts(@PathVariable Long id){
		
		Individuals person = clientService.findOne(id);
		List<BankAccount> racuni = person.getMojiRacuni();
		return new ResponseEntity<>(racuni,HttpStatus.OK);
		
	}
	
	//Gasenje racuna
	@RequestMapping(value = "/individualClient/account/deactivate/{id}" , method = RequestMethod.POST)
	public ResponseEntity<DeactivateBankAccount> deactivateAccount(@PathVariable Long id, @RequestBody String accountNumber){
		
		BankAccount bank_account = accountService.findOne(id);
		DeactivateBankAccount dba = deactivateService.create(accountNumber, bank_account);
		accountService.save(bank_account);
		
		return new ResponseEntity<>(dba,HttpStatus.OK);
	}
	
}

