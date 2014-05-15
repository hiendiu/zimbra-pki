package com.ecoit.ca.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashData {
	public static String HashString(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte dataByte[] = UnicodeUtils.unicodeDecode(data);
			byte hashed[] = md.digest(dataByte);
			return Base64Utils.base64Encode(hashed);
		} catch (NoSuchAlgorithmException ns) {
			ns.printStackTrace();
			return null;
		}
	}
}
