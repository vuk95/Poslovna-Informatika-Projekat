package com.pi.poslovna.service;

import com.pi.poslovna.model.BankAccount;

public interface IzvodXMLWriterService {

	public void createIzvodXML(BankAccount bankAccount, String path);
}
