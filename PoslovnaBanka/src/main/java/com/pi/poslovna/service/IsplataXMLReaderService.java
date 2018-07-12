package com.pi.poslovna.service;

import java.security.Principal;

public interface IsplataXMLReaderService {

	public void readIsplataXML(String filePath, Principal principal);
}
