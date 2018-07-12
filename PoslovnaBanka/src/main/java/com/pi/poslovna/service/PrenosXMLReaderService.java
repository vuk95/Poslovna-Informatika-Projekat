package com.pi.poslovna.service;

import java.security.Principal;

public interface PrenosXMLReaderService {

	public void readPrenosXML(String filePath, Principal principal);
	
}
