package com.ecoit.ca.token;

import java.security.PrivateKey;
import java.security.cert.Certificate;

import com.ecoit.ca.applet.exception.TokenException;

public interface TokenModule {

	public Certificate getCertificate() throws TokenException;

	public PrivateKey getPrivateKey() throws TokenException;
	
	public boolean checkTokenAvailable();
	
	public void closeToken();

}
