package com.ecoit.ca.auth;

import java.security.PublicKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.crypto.Cipher;

import sun.misc.BASE64Encoder;

import com.ecoit.ca.applet.exception.TokenException;
import com.ecoit.ca.token.TokenModule;
import com.ecoit.ca.token.TokenModules;
import com.ecoit.ca.utilities.DateFromServer;

public class BasicAuthenticationModule implements AuthenticationModule {
	private TokenModule token;
	private boolean hasToken = false;

	public BasicAuthenticationModule() {
		try {
			token = TokenModules.newDefaultTokenModule();
			hasToken = true;
		} catch (Exception ex) {
			hasToken = false;
		}
	}

	@Override
	public boolean checkCertificate() {
		// TODO Auto-generated method stub
		try {
			X509Certificate certificate = (X509Certificate) token
					.getCertificate();
			certificate.checkValidity(DateFromServer.getDate());
			return true;
		} catch (TokenException | CertificateExpiredException
				| CertificateNotYetValidException tke) {
			tke.printStackTrace();
		}
		return false;
	}

	@Override
	public String respond(String challenge) {
		// TODO Auto-generated method stub
		try {
			Cipher ci = Cipher.getInstance("RSA/ECB/nopadding", "SunPKCS11-Token");
			ci.init(Cipher.DECRYPT_MODE, token.getPrivateKey());
			byte[] dataByte = com.ecoit.ca.utilities.Convert.hex2Byte(challenge);
			byte[] decryptByte = ci.doFinal(dataByte);
			ArrayList<Byte> arr = new ArrayList<Byte>();
			boolean allow = false;
			for (int index = 0; index < decryptByte.length; index++) {
				if (decryptByte[index] != 0)
					allow = true;
				if (allow)
					arr.add(decryptByte[index]);
			}
			byte[] realData = new byte[arr.size()];
			for (int index = 0; index < arr.size(); index++)
				realData[index] = arr.get(index);

			return (new BASE64Encoder()).encode(realData);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public PublicKey getTokenInfo() {
		if (hasToken) {
			try {
				return token.getCertificate().getPublicKey();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	
}
