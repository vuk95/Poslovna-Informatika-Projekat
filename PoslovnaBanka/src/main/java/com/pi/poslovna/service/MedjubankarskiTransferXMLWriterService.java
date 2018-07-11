package com.pi.poslovna.service;

import com.pi.poslovna.model.InterbankTransfer;

public interface MedjubankarskiTransferXMLWriterService {

	public void createRTGSXML(InterbankTransfer it);
	
	public void createClearingXML();
}
