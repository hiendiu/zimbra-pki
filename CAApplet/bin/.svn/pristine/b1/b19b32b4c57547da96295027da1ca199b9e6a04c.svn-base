package com.ecoit.ca.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.net.nntp.NewsgroupInfo;

import sun.awt.image.BytePackedRaster;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.ecoit.ca.applet.exception.TokenException;
import com.ecoit.ca.token.TokenModule;
import com.ecoit.ca.token.TokenModules;
import com.ecoit.ca.utilities.Base64Utils;
import com.ecoit.ca.utilities.Convert;
import com.ecoit.ca.utilities.UnicodeUtils;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;

public class SampleEncryption {
	private SecretKey key;
	private Cipher cipher;

	public SampleEncryption(String publicKey, String mark) {
		try {

			cipher = Cipher.getInstance("DESede");
			key = new SecretKeySpec(createKey(publicKey), "DESede");

		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	// VOFFICE
	public SampleEncryption(String keyString, boolean hasEncrypt) {
	    try { if (!hasEncrypt) {
	        this.cipher = Cipher.getInstance("DESede");
	        this.key = new SecretKeySpec(keyString.getBytes(), "DESede");
	      }
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	public SampleEncryption(String encryptKeyString) {
		try {
			TokenModule token = TokenModules.newDefaultTokenModule();

			Cipher cipher1 = Cipher.getInstance("RSA/ECB/NoPadding");
			cipher1.init(Cipher.DECRYPT_MODE, token.getPrivateKey());
			byte[] realKey = cipher1
					.doFinal(Convert.hex2Byte(encryptKeyString));
			ArrayList<Byte> arr = new ArrayList<Byte>();
			boolean allow = false;
			for (int index = 0; index < realKey.length; index++) {
				if (realKey[index] != 0)
					allow = true;
				if (allow)
					arr.add(realKey[index]);
			}
			byte[] realData = new byte[arr.size()];
			for (int index = 0; index < arr.size(); index++)
				realData[index] = arr.get(index);
			String base = Base64Utils.base64Encode(realData);

			byte[] c = base.getBytes();
//			 System.out.println(base);
			cipher = Cipher.getInstance("DESede");
			key = new SecretKeySpec(base.getBytes(), "DESede");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private byte[] getPublicKey(String pubicKey) {
		try {
			if (pubicKey.equals("")) {
				TokenModule token = TokenModules.newDefaultTokenModule();
				PublicKey pub = token.getCertificate().getPublicKey();
				return pub.toString().getBytes();
			} else
				return pubicKey.getBytes();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private byte[] createKey(String publicKey) {
		try {
			TokenModule token = TokenModules.newDefaultTokenModule();
			// PublicKey rpub = token.getCertificate().getPublicKey();
			PrivateKey rpri = token.getPrivateKey();

			Cipher cipher1 = Cipher.getInstance("RSA/ECB/NoPadding");
			cipher1.init(Cipher.ENCRYPT_MODE, rpri);

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] encrKey = cipher1.doFinal(md.digest(getPublicKey(publicKey)));
			byte[] digest = md.digest(encrKey);

			byte[] realKey = new byte[24];
			for (int i = 0; i < 16; i++) {
				if (i < digest.length)
					realKey[i] = digest[i];
			}

			return realKey;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public EncryptionModule createFormEncryption() {
		try {

			EncryptionModule encrypt = new EncryptionModule() {

				@Override
				public String encrypt(String originalData) throws Exception {

					cipher.init(Cipher.ENCRYPT_MODE, key);
					byte[] originalByte = UnicodeUtils
							.unicodeDecode(originalData);
					byte[] encryptData = cipher.doFinal(originalByte);
					return (new BASE64Encoder().encode(encryptData));
				}

				@Override
				public String decrypt(String encryptedData) throws Exception {
					cipher.init(Cipher.DECRYPT_MODE, key);
					byte[] encryptedByte = (new BASE64Decoder()
							.decodeBuffer(encryptedData));
					byte[] decryptDataByte = cipher.doFinal(encryptedByte);
					return new String(decryptDataByte,"UTF-8");
				}
			};
			return encrypt;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public EncryptionModule createFileEncryption() {
		try {
			EncryptionModule encrypt = new EncryptionModule() {

				@Override
				public String encrypt(String originalData) throws Exception {
					cipher.init(Cipher.ENCRYPT_MODE, key);
					String desData = originalData + ".eco";

					FileInputStream in = new FileInputStream(originalData);
					CipherInputStream cis = new CipherInputStream(in, cipher);
					FileOutputStream out = new FileOutputStream(desData);

					byte[] b = new byte[1024];
					int size = cis.read(b);
					while (size != -1) {
						out.write(b, 0, size);
						size = cis.read(b);
					}

					cis.close();
					in.close();
					out.close();

					Path p = Paths.get(originalData);
					Files.delete(p);

					JOptionPane.showMessageDialog(null,
							"M\u00E3 h\u00F3a th\u00E0nh c\u00F4ng");
					return desData;
				}

				@Override
				public String decrypt(String encryptedData) throws Exception {
					cipher.init(Cipher.DECRYPT_MODE, key);
					String desData = "";
					try {
						if (encryptedData.substring(encryptedData.length() - 4)
								.equals(".eco")) {
							desData = encryptedData.substring(0,
									encryptedData.length() - 4);

						} else
							throw new Exception();
					} catch (Exception ex) {
						JOptionPane
								.showMessageDialog(null,
										"Ch\u1EC9 gi\u1EA3i m\u00E3 nh\u1EEFng file .eco");
						return null;
					}

					FileInputStream in = new FileInputStream(encryptedData);
					CipherInputStream cis = new CipherInputStream(in, cipher);
					FileOutputStream out = new FileOutputStream(desData);

					byte[] b = new byte[1024];
					int size = cis.read(b);
					while (size != -1) {
						out.write(b, 0, size);
						size = cis.read(b);
					}

					cis.close();
					in.close();
					out.close();
					JOptionPane.showMessageDialog(null,
							"Gi\u1EA3i m\u00E3 th\u00E0nh c\u00F4ng");
					return desData;

				}
			};
			return encrypt;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}