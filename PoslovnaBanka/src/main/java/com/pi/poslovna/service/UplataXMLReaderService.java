package com.pi.poslovna.service;

import java.security.Principal;

public interface UplataXMLReaderService {

	public void readUplataXML(String filePath, Principal principal);
	
}
