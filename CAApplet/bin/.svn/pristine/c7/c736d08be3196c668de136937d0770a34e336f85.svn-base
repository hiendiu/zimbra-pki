package com.ecoit.ca.token;

import java.io.ByteArrayInputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Enumeration;

import sun.security.pkcs11.SunPKCS11;
import sun.security.pkcs11.wrapper.PKCS11;

import com.ecoit.ca.applet.PinInputter;
import com.ecoit.ca.applet.exception.TokenException;

public class SampleTokenModule implements TokenModule {

	private Provider provider;
	private KeyStore.Builder builder;
	private PKCS11 pkcs11;
	public static int lock = 2;
	public SampleTokenModule(String driverPath) throws TokenException {
		Provider sunPKCS11 = Security.getProvider("Token");
		
		if (sunPKCS11 == null)
			sunPKCS11 = addSecurityProvider(driverPath);

		if (sunPKCS11 == null)
			throw new TokenException("Token not available");

		provider = sunPKCS11;

		builder = KeyStore.Builder.newInstance(
				"PKCS11",
				null,
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
				if (lock == 1) break;

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

				if (lock == 1) break;
			}

			return key;
		} catch (GeneralSecurityException e) {
			throw new TokenException(e);
		}
	}

	@Override
	public boolean checkTokenAvailable() {
		// TODO Auto-generated method stub
		return !provider.isEmpty();
	}

	private Provider addSecurityProvider(String driverPath) {
		PKCS11 pkcs11 = null;
		try {
			pkcs11 = PKCS11.getInstance(driverPath, "C_GetFunctionList", null,
					false);

			System.out.println(pkcs11.C_GetInfo());

			long[] slotList = pkcs11.C_GetSlotList(true);
			
			if (slotList.length == 0)
				return null;

			String providerString = "name=Token\nlibrary=" + driverPath
					+ "\nslot=" + slotList[0];
			
			System.out.println(providerString);

			SunPKCS11 sunPKCS11 = new SunPKCS11(new ByteArrayInputStream(
					providerString.getBytes()));
			
			System.out.println(sunPKCS11.getInfo());

			Security.addProvider(sunPKCS11);
			this.pkcs11 = pkcs11;

			return sunPKCS11;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
//			if (pkcs11 != null)
//				try {
//					pkcs11.finalize();
//				} catch (Throwable e) {
//					e.printStackTrace();
//				}
		}
	}

	public void closeToken() {
		
		Security.removeProvider(provider.getName());
		try {
			pkcs11.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
