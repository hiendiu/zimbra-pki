package com.ecoit.ca.auth;

import java.security.PublicKey;

public interface AuthenticationModule {

	public boolean checkCertificate();

	public String respond(String challenge);
	
	public PublicKey getTokenInfo();

}
