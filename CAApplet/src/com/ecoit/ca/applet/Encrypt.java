/*    */ package com.ecoit.ca.applet;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.security.KeyFactory;
/*    */ import java.security.interfaces.RSAPublicKey;
/*    */ import java.security.spec.KeySpec;
/*    */ import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
/*    */ import java.util.Random;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.DESedeKeySpec;
/*    */ import org.apache.commons.codec.binary.Hex;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/*    */ 
/*    */ public class Encrypt
/*    */ {
/*    */   public static String generateRandomString(int length)
/*    */   {
/* 20 */     String tmp = "";
/* 21 */     Random random = new Random();
/* 22 */     for (int i = 0; i < length; i++) {
/* 23 */       int loai = (int)(random.nextFloat() * 3.0F);
/* 24 */       switch (loai) {
/*    */       case 0:
/* 26 */         tmp = tmp + (char)(48 + (int)(random.nextFloat() * 9.0F));
/* 27 */         break;
/*    */       case 1:
/* 29 */         tmp = tmp + (char)(97 + (int)(random.nextFloat() * 26.0F));
/* 30 */         break;
/*    */       case 2:
/* 32 */         tmp = tmp + (char)(65 + (int)(random.nextFloat() * 26.0F));
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 37 */     return tmp;
/*    */   }
/*    */ 
/*    */   public static String encryptKey(String originalKey, String pubKey) //ma hoa key cua 3Des bang RSA
/*    */   {
/*    */     try {
/* 43 */       pubKey = pubKey.substring(pubKey.indexOf("modulus") + 9, pubKey.indexOf("  public exponent:") - 1);
/* 44 */       BigInteger modulus = new BigInteger(pubKey);
/* 45 */       BigInteger pubExp = new BigInteger("65537");
/*    */ 
/* 47 */       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/* 48 */       RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, pubExp);
/* 49 */       RSAPublicKey key = (RSAPublicKey)keyFactory.generatePublic(pubKeySpec);
/*    */ 
/* 51 */       Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
/* 52 */       cipher.init(1, key);
/* 53 */       byte[] cipherData = cipher.doFinal(new BASE64Decoder().decodeBuffer(originalKey));
/* 54 */       return Hex.encodeHexString(cipherData);
/*    */     } catch (Exception e) {
/* 56 */       e.printStackTrace();
/*    */     }
/* 58 */     return originalKey;
/*    */   }
/*    */ 
/*    */   public static String encrypt3Des(String text, String txtKey) { //ma hoa thu bang 3DES
/*    */     try {
/* 63 */       Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
/* 64 */       KeySpec ks = new DESedeKeySpec(txtKey.getBytes());
/* 65 */       SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
/* 66 */       SecretKey key = skf.generateSecret(ks);
/*    */ 
/* 68 */       cipher.init(1, key);

/* 69 */       byte[] plainText = new BASE64Decoder().decodeBuffer(text);
/*    */ 	
//				System.out.println(plainText.length);
				int padding = 8 - plainText.length%8;
				if (padding!=8){
					int N = plainText.length;
					plainText = Arrays.copyOf(plainText, N + padding);
					for (int i = 0; i < padding; i++) {
						plainText[N+i] = 0;
					}
				}
				SampleApplet.padding = padding;
/* 71 */       return new BASE64Encoder().encode(cipher.doFinal(plainText));
/*    */     } catch (Exception ex) {
/* 73 */       ex.printStackTrace();
/* 74 */     }return null;
/*    */   }
/*    */ }
	

/* Location:           F:\D\vOffice\linhhc\webapps\ROOT\WEB-INF\lib\vsdocsLib.jar
 * Qualified Name:     com.action.doc.docssend.Encrypt
 * JD-Core Version:    0.6.2
 */