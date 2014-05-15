package com.ecoit.ca.utilities;

public class Convert {

	// String to Byte
	public static byte[] stringToByte(String st) {
		byte[] output = new byte[st.length()];
		for (int i = 0; i < st.length(); i++) {
			output[i] = (byte) st.charAt(i);
		}
		return output;
	}

	// Byte to String
	public static String byteToString(byte[] bt) {
		StringBuilder sb = new StringBuilder();
		for (byte i : bt)
			sb.append((char) i);
		return sb.toString();
	}

	public static byte[] hex2Byte(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),
					16);
		}
		return bytes;
	}
}
