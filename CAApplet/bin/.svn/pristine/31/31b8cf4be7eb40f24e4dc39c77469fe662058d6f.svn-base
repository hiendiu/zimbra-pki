package com.ecoit.ca.applet;

import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.KeyFactory;
import java.security.PrivilegedAction;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.ArrayUtils;



import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.ecoit.ca.auth.BasicAuthenticationModule;
import com.ecoit.ca.encrypt.EncryptionModule;
import com.ecoit.ca.encrypt.SampleEncryption;
import com.ecoit.ca.signature.FileSigner;
import com.ecoit.ca.signature.FormSigner;
import com.ecoit.ca.signature.SigningModules;
import com.ecoit.ca.token.SampleTokenModule;
import com.ecoit.ca.token.TokenModule;
import com.ecoit.ca.token.TokenModules;
import com.ecoit.ca.utilities.Base64Utils;
import com.ecoit.ca.utilities.Convert;
import com.ecoit.ca.utilities.DateFromServer;
import com.ecoit.ca.utilities.HashData;
import com.ecoit.ca.utilities.UnicodeUtils;

public class SampleApplet extends Applet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name = "";
	private JFrame frame, frame2;
	private String fileChoose = "";
	private File filename;
	String myPath = "";
	public SampleApplet() {
		frame = new JFrame();
		createGUI();
	}

	public void showSelectPath() {
		AccessController.doPrivileged(new PrivilegedAction<Object>() {

			@Override
			public Object run() {
				try {
					javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
						public void run() {
							createGUI();
						}
					});
				} catch (Exception e) {
					System.err.println("createGUI didn't successfully complete");
				}
				return null;
			}
		});
	}
	public String getFilePath(){
		String myName = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
		if (myPath !=  null) return myPath;
		else return "";
			}
		});
		return myName;
	}
	public String getBinaryFilePath(){
		String myName = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				if (fileChoose !=  null) return fileChoose;
				else return "";
			}
		});
		return myName;
	}
	private JTextField field = new JTextField(20);

	private void createGUI() {

		field.setEditable(false);
		final JButton button = new JButton("Chọn File");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(SampleApplet.this.frame2);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					filename = fc.getSelectedFile();
					fileChoose = filename.toString();
					field.setText(fileChoose);
				} else {
				}
			}
		});
		this.add(field);
		this.add(button);
		this.setSize(400, 75);
	}

	public String getNameInToken() {

		AccessController.doPrivileged(new PrivilegedAction<Object>() {

			@Override
			public synchronized Object run() {
				try {
					TokenModule token = TokenModules.newDefaultTokenModule();
					name = ((X509Certificate) token.getCertificate())
							.getSubjectDN().getName();
					int index = name.indexOf("CN=");
					int indexLast = name.indexOf(",", index);
					name = name.substring(index + 3, indexLast);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});

		return this.name;

	}
	
	public String signFile() {
		String mysign = AccessController
				.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						try {
							if ("".equals(fileChoose)) JOptionPane.showMessageDialog(null, "Xin mời chọn file");
							else {
								final FileSigner signer = SigningModules .createFileSigner(fileChoose);
								MainGui gui = new MainGui(frame);
								String myString="";
								if (gui.ans == 1) myPath = signer.signFile();
								FileInputStream input = new FileInputStream(new File(myPath));
								byte[] dataByte = new byte[input.available()];
								input.read(dataByte);
								myString = (new BASE64Encoder()).encode(dataByte);
								input.close();
								fileChoose = "";
								return myString;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return "";
					}
				});

		return mysign;

	}

	public boolean verifyFile() {
		Boolean myverify = 
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

			@Override
			public Boolean run() {
				try {
					if ("".equals(fileChoose))
						JOptionPane.showMessageDialog(null, "Xin mời chọn file");
					else {
						final FileSigner signer = SigningModules.createFileSigner(fileChoose);
						fileChoose = "";
						return signer.verifyFile();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
		});
		return myverify;
	}

	public void setFileChoose(String fileChoose) {
		this.fileChoose = fileChoose;
	}

	public String signFileForSharepoint(final String path,
			final String base64String) {
		String myData =
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public  String run() {
				try {
					FileOutputStream outputStream = new FileOutputStream(
							new File(path));
					byte[] c = Base64Utils.base64Decode(base64String);
					outputStream.write(c);
					outputStream.close();
					final FileSigner signer = SigningModules
							.createFileSigner(path);
					MainGui gui = new MainGui(frame);
					String signedPath="";
					if (gui.ans == 1)
						 signedPath = signer.signFile();

					FileInputStream inputStream = new FileInputStream(new File(
							signedPath));

					byte[] d = new byte[inputStream.available()];
					inputStream.read(d);

					// find real data
					boolean allow = false;
					ArrayList<Byte> arr = new ArrayList<Byte>();
					for (int index = 0; index < d.length; index++) {
						if (d[index] != 0)
							allow = true;
						if (allow)
							arr.add(d[index]);
					}
					byte[] realData = new byte[arr.size()];
					for (int index = 0; index < arr.size(); index++)
						realData[index] = arr.get(index);

					
					inputStream.close();
					Files.delete(Paths.get(signedPath));
					return  new BASE64Encoder().encode(realData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "";
			}
		});
		return myData;

	}

	public boolean verifyFile(final String path,
			final String base64String) {
		Boolean verify = 
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

			@Override
			public Boolean run() {
				try {

					
					FileOutputStream outputStream = new FileOutputStream(
							path);
					byte[] c = (new BASE64Decoder()).decodeBuffer(base64String);
					outputStream.write(c);
					outputStream.close();
					final FileSigner signer = SigningModules
							.createFileSigner(path);
					boolean returnValue = signer.verifyFile();
					return returnValue;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		});

		return verify;

	}

	public String signForm(final String originalData) {
		String signature = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public  String run() {
				try {
					final FormSigner signer = SigningModules.createFormSigner();
					MainGui gui = new MainGui(frame);
					if (gui.ans == 1)
						return signer.signForm(originalData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "";
			}
		});
		return signature;

	}

	public boolean verifyFormString(final String signature,
			final String originalData, final String pub) {
		Boolean verify = 
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			@Override
			public Boolean run() {
				try {
					String modulusString = pub.substring(
							pub.indexOf("modulus") + 9,
							pub.indexOf("  public exponent:") - 1);
					BigInteger modulus = new BigInteger(modulusString);
					BigInteger exponent = new BigInteger("65537");

					KeyFactory keyFactory = KeyFactory.getInstance("RSA");
					RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus,
							exponent);
					RSAPublicKey key = (RSAPublicKey) keyFactory
							.generatePublic(pubKeySpec);
					return verifyForm(signature, originalData, key);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		});
		return verify;
	}

	public boolean verifyFormModulus(final String signature,
			final String originalData, final String modulusString) {
		Boolean verify = 
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			@Override
			public Boolean run() {
				try {

					BigInteger modulus = new BigInteger(modulusString);
					BigInteger exponent = new BigInteger("65537");

					KeyFactory keyFactory = KeyFactory.getInstance("RSA");
					RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus,
							exponent);
					RSAPublicKey key = (RSAPublicKey) keyFactory
							.generatePublic(pubKeySpec);
					return verifyForm(signature, originalData, key);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		});
		return verify;
	}

	public boolean verifyForm(final String signature,
			final String originalData, final PublicKey pub) {
		Boolean verify = 
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

			@Override
			public Boolean run() {
				try {
					FormSigner signer = SigningModules.createFormSigner();
					return  signer.verifyForm(signature, originalData, pub);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		});

		return verify;

	}

	public String encryptionForm(final String original, final String publicKey) {
		String encrypt = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				try {
					SampleEncryption em = new SampleEncryption(publicKey, "");
					final EncryptionModule en = em.createFormEncryption();
					MainGui gui = new MainGui(frame);
					if (gui.ans == 1)
						return en.encrypt(original);
				} catch (Exception ex) {
				}
				return "";
			}
		});
		return encrypt;
	}

	public String decryptionForm(final String encryptedData) {
		String decrypt = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public  String run() {
				try {
					SampleEncryption em = new SampleEncryption("", "");
					final EncryptionModule en = em.createFormEncryption();
					MainGui gui = new MainGui(frame);
					if (gui.ans == 1)
						return en.decrypt(encryptedData);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "";
				
			}
		});
		return decrypt;
	}

	public String encryptionFile(final String original) {
		String myPath = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				try {
					SampleEncryption em = new SampleEncryption("", "");
					final EncryptionModule en = em.createFileEncryption();
					MainGui gui = new MainGui(frame);
					if (gui.ans == 1)
						return en.encrypt(original);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "";
			}
		});
		return myPath;
	}

	public String decryptionFile(final String encryptedData) {
		String myPath = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public  String run() {
				try {
					SampleEncryption em = new SampleEncryption("", "");
					final EncryptionModule en = em.createFileEncryption();
					MainGui gui = new MainGui(frame);
					if (gui.ans == 1)
						return en.decrypt(encryptedData);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "";
			}
		});
		return myPath;
	}

	public boolean checkTokenAvailable() {
		Boolean available = 
		AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

			@Override
			public Boolean run() {
				try {
					TokenModule token = TokenModules.newDefaultTokenModule();
					return token.checkTokenAvailable();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return false;
			}
		});
		return available;
	}

	public String decryptFromServer(final String challenge) {
		String decrypt = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				String result = "";
				try {
					SampleTokenModule.lock = 1;
					BasicAuthenticationModule authen = new BasicAuthenticationModule();
					result = authen.respond(challenge);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				SampleTokenModule.lock = 2;
				return result;
			}
		});
		return decrypt;
	}

	public Certificate getCertificate() {
		Certificate cer = 
		AccessController.doPrivileged(new PrivilegedAction<Certificate>() {

			@Override
			public Certificate run() {
				try {
					TokenModule token = TokenModules.newDefaultTokenModule();
					return  token.getCertificate();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return null;
			}
		});
		return cer;
	}

	public PublicKey getPublicKey() {
		PublicKey pub =
		AccessController.doPrivileged(new PrivilegedAction<PublicKey>() {

			@Override
			public PublicKey run() {
				try {

					TokenModule token = TokenModules.newDefaultTokenModule();
					return token.getCertificate().getPublicKey();
					// pub.toString();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return null;
			}
		});
		return pub;
	}

	public String getPublicKeyString() {
		String pubString = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				try {

					TokenModule token = TokenModules.newDefaultTokenModule();
					return  token.getCertificate().getPublicKey().toString();
					// pub.toString();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return "";
			}
		});
		return pubString;
	}

	public void stopApplet() {
		System.exit(1);
	}

	public String unicodeToBase64(final String unicodeString) {
		String base64 = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				return UnicodeUtils.unicodeToBase64(unicodeString);
			}
		});
		return base64;
	}

	public String base64ToUnicode(final String base64String) {
		String unicode = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				return UnicodeUtils.base64ToUnicode(base64String);
			}
		});
		return unicode;
	}

	public String hashedData(final String data) {
		String hashed = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				return HashData.HashString(data);
			}
		});
		return hashed;
	}

	public String decryptForZimbra(final String encryptData,
			final String encryptKey) {
		String myString = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				try {
					SampleEncryption sampleEncryption = new SampleEncryption(
							encryptKey);
					EncryptionModule enc = sampleEncryption
							.createFormEncryption();
					return enc.decrypt(encryptData);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return "";
			}
		});
		return myString;
	}

	// TANDAN COMP
	public String signFile(final boolean timestamp) {
		String mysign = AccessController
				.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						try {
							DateFromServer.TIME_STAMP = timestamp;
							if ("".equals(fileChoose)) JOptionPane.showMessageDialog(null, "Xin mời chọn file");
							else {
								final FileSigner signer = SigningModules .createFileSigner(fileChoose);
								MainGui gui = new MainGui(frame);
								String myString="";
								if (gui.ans == 1) myPath = signer.signFile();
								FileInputStream input = new FileInputStream(new File(myPath));
								byte[] dataByte = new byte[input.available()];
								input.read(dataByte);
								myString = (new BASE64Encoder()).encode(dataByte);
								input.close();
								fileChoose = "";
								return myString;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return "";
					}
				});

		return mysign;

	}

	public boolean checkTokenAvailable(boolean CRL){
		Boolean available = 
				AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

					@Override
					public Boolean run() {
						try { 
							TokenModule token = TokenModules.newDefaultTokenModule();
							return token.checkTokenAvailable();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return false;
					}
				});
				return available;
	}
	private String data = "";
	public String signBinaryFile(boolean timestamp){
		String signature = 
				AccessController.doPrivileged(new PrivilegedAction<String>() {
					
					@Override
					public  String run() {
						String data = "";
						try {
							if ("".equals(fileChoose)) JOptionPane.showMessageDialog(null, "Xin mời chọn file");
							else {
								final FormSigner signer = SigningModules.createFormSigner();
								MainGui gui = new MainGui(frame);
								File file = new File(fileChoose);
								FileInputStream stream = new FileInputStream(file);
								byte b[] = new byte[stream.available()];
								data = Base64Utils.base64Encode(b);
								stream.close();
								if (gui.ans == 1) data = signer.signForm(data);
								JOptionPane.showMessageDialog(null, "Ký xong!");
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Ký lỗi!");
							e.printStackTrace();
						}
						return data;
					}
				});
				return signature;
	}
	public String checkFileType(){
		return 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				return  fileChoose.substring(fileChoose.lastIndexOf(".")+1);
			}
			
		});
	}

	public String getFileContent(){
		return 
				AccessController.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						try {
							File file = new File(fileChoose);
							FileInputStream stream = new FileInputStream(file);
							byte b[] = new byte[stream.available()];
							stream.read(b);
							data = new BASE64Encoder().encode(b);
							stream.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return data;
					}
					
				});
	}
	public String getEncryptedKey(){
		return 
				AccessController.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						return keyEncrypt;
					}
					
				});
	}
	private String keyEncrypt="";
	public String getCipherPadding(){
		return 
				AccessController.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						return String.valueOf(padding);
					}
					
				});
	}
	public static int padding=0;
	public String encryptionFile(final String original,final String publicKey) {
		String myPath = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				try {
					String key = Encrypt.generateRandomString(24);
					String data = Encrypt.encrypt3Des(original, key);
					keyEncrypt = Encrypt.encryptKey(key, publicKey);
					return data;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "";
			}
		});
		return myPath;
	}
	public String decryptionFile1(final String encryptedData,final String encryptKey,final String padding){
		String myData = 
				AccessController.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						try {
							SampleTokenModule.lock = 1;
							TokenModule token = TokenModules.newDefaultTokenModule();

							Cipher cipher1 = Cipher.getInstance("RSA/ECB/NoPadding");
							cipher1.init(Cipher.DECRYPT_MODE, token.getPrivateKey());
							byte[] realKey = cipher1
									.doFinal(Convert.hex2Byte(encryptKey));
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
							String base = new BASE64Encoder().encode(realData);
							Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
							SecretKeySpec key = new SecretKeySpec(base.getBytes(), "DESede");
							cipher.init(Cipher.DECRYPT_MODE, key);
							byte[] b = new BASE64Decoder().decodeBuffer(encryptedData);
							byte[] c = cipher.doFinal(b);
							byte[] decryptData = Arrays.copyOfRange(c, 0, c.length-Integer.parseInt(padding));
							return new BASE64Encoder().encode(decryptData);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						SampleTokenModule.lock = 2;
						return "";
					}
				});
				return myData;
	}
	public boolean verifyBinaryFile(final String data,final String signed,final String pub){
		boolean result = 
				AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

					@Override
					public Boolean run() {
						try {
							String modulusString = pub.substring(
									pub.indexOf("modulus") + 9,
									pub.indexOf("  public exponent:") - 1);
							BigInteger modulus = new BigInteger(modulusString);
							BigInteger exponent = new BigInteger("65537");

							KeyFactory keyFactory = KeyFactory.getInstance("RSA");
							RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus,
									exponent);
							RSAPublicKey key = (RSAPublicKey) keyFactory
									.generatePublic(pubKeySpec);
							final FormSigner signer = SigningModules.createFormSigner();
							return signer.verifyForm(signed, data, key);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						return false;
					}
				});
				return result;
				
	}
	public String getPublicKeyStringEncrypt() {
		String pubString = 
		AccessController.doPrivileged(new PrivilegedAction<String>() {

			@Override
			public String run() {
				String publicKey = "";
				try {
					SampleTokenModule.lock = 1;
					TokenModule token = TokenModules.newDefaultTokenModule();
					publicKey =  token.getCertificate().getPublicKey().toString();
					// pub.toString();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				SampleTokenModule.lock = 2;
				return publicKey;
			}
		});
		return pubString;
	}
	
	public String signFile(final String path){
		return 
				AccessController.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						String path="";
						try {
							FileSigner signer = SigningModules .createFileSigner(fileChoose);
							MainGui gui = new MainGui(frame);
							if (gui.ans == 1) path =  signer.signFile();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return path;
					}
					
				});
	}
	public boolean verifyFile(final String path){
		return 
				AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

					@Override
					public Boolean run() {
						try {
							FileSigner signer = SigningModules.createFileSigner(path);
							return signer.verifyFile();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return false;
					}
					
				});
	}

	public String signBinaryFile(final String path,boolean timestamp){
		String signature = 
				AccessController.doPrivileged(new PrivilegedAction<String>() {
					
					@Override
					public  String run() {
						String data = "";
						try {
								final FormSigner signer = SigningModules.createFormSigner();
								MainGui gui = new MainGui(frame);
								File file = new File(path);
								FileInputStream stream = new FileInputStream(file);
								byte b[] = new byte[stream.available()];
								data = Base64Utils.base64Encode(b);
								stream.close();
								if (gui.ans == 1) data = signer.signForm(data);
								JOptionPane.showMessageDialog(null, "Ký xong!");
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Ký lỗi!");
							e.printStackTrace();
						}
						return data;
					}
				});
				return signature;
	}

	public String signFile(final String base64,final String path){
		String value = 
				AccessController.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						String data = "";
						try {
							File file = new File(path);
							FileOutputStream fout = new FileOutputStream(file);
							fout.write(new BASE64Decoder().decodeBuffer(base64));
							fout.close();
							FileSigner signer = SigningModules .createFileSigner(path);
							myPath = signer.signFile();
							FileInputStream input = new FileInputStream(new File(myPath));
							byte[] dataByte = new byte[input.available()];
							input.read(dataByte);
							data = (new BASE64Encoder()).encode(dataByte);
							input.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return data;
					}
				});
				return value;
	}
	public String signBinaryFile(final String base64){
		String value = 
				AccessController.doPrivileged(new PrivilegedAction<String>() {

					@Override
					public String run() {
						String data = "";
						try {
							final FormSigner signer = SigningModules.createFormSigner();
							data = signer.signForm(base64);
							JOptionPane.showMessageDialog(null, "Ký xong!");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Ký lỗi!");
							ex.printStackTrace();
						}
						return data;
					}
				});
				return value;
	}
	public void clearFileChoose(){
		AccessController.doPrivileged(new PrivilegedAction<Object>() {

			@Override
			public Object run() {
				try {
					field.setText("");
					fileChoose = null;
					filename = null;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return null;
			}
		});
	}
	// VOFFICE
	public String decryptFileForVoffice(final String encryptData, final String key, final boolean hasEncrypt)
	  {
	    String myString = 
	      AccessController.doPrivileged(new PrivilegedAction<String>(){
	      public String run()
	      {
	        try {
	          SampleEncryption sampleEncryption = null;
	          if (hasEncrypt) sampleEncryption = new SampleEncryption(key); 
	          else
	            sampleEncryption = new SampleEncryption(key, false);
	          EncryptionModule enc = sampleEncryption
	            .createFormEncryption();
	          return enc.decrypt(encryptData);
	        } catch (Exception ex) {
	          ex.printStackTrace();
	        }
	        return "";
	      }
	    });
	    return myString;
	  }
}
