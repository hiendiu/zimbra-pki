package com.ecoit.ca.utilities;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class UnicodeUtils {
	public static byte[] unicodeDecode(String data){
		ByteBuffer bb =  Charset.forName("UTF-8").encode(data);
		return bb.array();
	}
	public static String unicodeEncode(byte[] byteData){
		String originalData="";
		ByteBuffer bb = ByteBuffer.wrap(byteData);
		CharBuffer cb = Charset.forName("UTF-8").decode(bb);
		char[] ch = cb.array();
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] != '\0') originalData = originalData + ch[i];
		}
		return originalData;
	}
	public static String unicodeToBase64(String unicodeString){
		byte[] byteUnicode = unicodeDecode(unicodeString);
		return Base64Utils.base64Encode(byteUnicode);
	}
	public static String base64ToUnicode(String base64String){
		byte[] byteUnicode = Base64Utils.base64Decode(base64String);
		return unicodeEncode(byteUnicode);
	}
}
