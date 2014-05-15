package com.ecoit.ca.token;

import java.io.ByteArrayInputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Enumeration;

import sun.security.pkcs11.SunPKCS11;

import com.ecoit.ca.applet.PinInputter;
import com.ecoit.ca.applet.exception.TokenException;

public class ETokenModule implements TokenModule {

	private SunPKCS11 sunPKCS11;
	private KeyStore.Builder builder;
	
	public ETokenModule() {
		String providerString = "name=Token\nlibrary=C:\\Windows\\system32\\eTPKCS11.dll";
		
		sunPKCS11 = new SunPKCS11(new ByteArrayInputStream(
				providerString.getBytes()));
		
		System.out.println(Security.addProvider(sunPKCS11));

		builder = KeyStore.Builder.newInstance(
				"PKCS11",
				sunPKCS11,
				new KeyStore.CallbackHandlerProtection(PinInputter
						.getCallbackHandler()));
	}

	@Override
	public Certificate getCertificate() throws TokenException {
		try {
			KeyStore keyStore = builder.getKeyStore();

			Enumeration<String> aliasEnum = keyStore.aliases();
			Certificate certificate = null;

			while (aliasEnum.hasMoreElements()) {
				String alias = aliasEnum.nextElement();

				certificate = ((KeyStore.PrivateKeyEntry) keyStore.getEntry(
						alias, builder.getProtectionParameter(alias)))
						.getCertificate();

			}

			return certificate;
		} catch (GeneralSecurityException e) {
			throw new TokenException(e);
		}
	}

	@Override
	public PrivateKey getPrivateKey() throws TokenException {
		try {
			KeyStore keyStore = builder.getKeyStore();

			Enumeration<String> aliasEnum = keyStore.aliases();
			PrivateKey key = null;

			while (aliasEnum.hasMoreElements()) {
				String alias = aliasEnum.nextElement();

				key = ((KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
						builder.getProtectionParameter(alias))).getPrivateKey();

				System.out.println(key);

			}

			return key;
		} catch (GeneralSecurityException e) {
			throw new TokenException(e);
		}
	}

	@Override
	public boolean checkTokenAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeToken() {
		Security.removeProvider(sunPKCS11.getName());
	}

}
