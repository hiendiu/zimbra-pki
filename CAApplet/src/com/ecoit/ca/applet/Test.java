package com.ecoit.ca.applet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.ecoit.ca.token.SampleTokenModule;
import com.ecoit.ca.token.TokenModule;
import com.ecoit.ca.token.TokenModules;
import com.ecoit.ca.utilities.UnicodeUtils;

public class Test {
	public static void main(String args[]){
		SampleApplet sampleApplet = new SampleApplet();
//		sampleApplet.setFileChoose("C:\\Users\\Administrator\\Downloads\\TKB11_.xlsx");
		try {
//			String base64String = UnicodeUtils.unicodeToBase64("test");
//			System.out.println(base64String);
//			String encrypt = sampleApplet.encryptionForm("abc123@@", sampleApplet.getPublicKeyString());
//			System.out.println();
//			System.out.println(encrypt);
//			System.out.println();
//			System.out.println(sampleApplet.decryptionForm(encrypt));
			
//			sampleApplet.signFile();
			
//			String signed = sampleApplet.signBinaryFile("D:/WORK/ECOIT/file.doc",false);
//			System.out.println(sampleApplet.verifyBinaryFile(sampleApplet.getFileContent(), signed, sampleApplet.getPublicKeyString()));
//			String publicKey = sampleApplet.getPublicKeyStringEncrypt();
//			String data = sampleApplet.getFileContent();
//			sampleApplet.setFileChoose("");
//			System.out.println(data);
//			String encrypt = sampleApplet.encryptionFile(data, publicKey);
//			int padding = sampleApplet.padding;
//			System.out.println(encrypt);
//			String encryptKey = sampleApplet.getEncryptedKey();
//			System.out.println(encryptKey);
//			String decrypt = sampleApplet.decryptionFile1(encrypt,encryptKey,sampleApplet.getCipherPadding());
//			System.out.println();
//			System.out.println(decrypt);
//			System.out.println("END");
//			System.out.println(decrypt);
//			System.out.println(data.equals(decrypt));
//			String signed = sampleApplet.signBinaryFile(data);
//			System.out.println(sampleApplet.verifyBinaryFile(data, signed, sampleApplet.getPublicKeyString()));
//			String signed = sampleApplet.signFile();
//			File file = new File("D:/text.xlsx");
//			FileOutputStream fout = new FileOutputStream(file);
//			fout.write(new BASE64Decoder().decodeBuffer(signed));
//			fout.close();
//			File file = new File("C:/debug1214.txt");
//			FileInputStream fis = new FileInputStream(file);
//			byte[] b = new byte[fis.available()];
//			fis.read(b);
//			fis.close();
			System.out.println(sampleApplet.checkTokenAvailable());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
