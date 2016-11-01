package com.cf.util.security;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
*
* <p>Title: DESCrypto</p>
* <p>Description: 實現EDS算法對字符串和文件的加密解密
* 1. DES算法對字符串的加密解密
* 2. DES算法對文件的加密解密
* 
* 3. Triple DES算法對字符串的加密解密
* 4. Triple DES算法對文件的加密解密</p>
* @author Dumbbell Yang
* @Date   2010-01-11
* @version 1.0
*/

public class DESCrypto {
	private final static Logger logger = Logger.getLogger(DESCrypto.class);
	/**
	 * 密钥，長度必須為8的倍數
	 */
	private static final String DES_KEY = "CMMSGIBIITSSDEV1";
	private static final String DES = "DES";

	private static final String TRIPLE_DES_KEY = "321321000000000000000000";
	private static final String TRIPLE_DES = "TripleDES";

	//密鑰
	private static final String DES_KEY_FILE = "C:\\des.key";
	private static final String TRIPLE_DES_KEY_FILE = "C:\\tripledes.key";
	public static DESKeySpec    DESKeySpec;
	public static SecretKeySpec tripleDESKeySpec;
	
	//取得DES Key
	@SuppressWarnings("resource")
	public static DESKeySpec getDESKeySpec() 
		throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		//DES 128位
		byte[] bytes = new byte[8];
		File f = new File(DES_KEY_FILE);
		SecretKey key = null;
		DESKeySpec spec = null;
		
		//如果存在Key文件，讀取
		if (f.exists()) {
			new FileInputStream(f).read(bytes);
		} 
		//如果不存在，重新生成Key，并寫入Key文件
		else {
			KeyGenerator kgen = KeyGenerator.getInstance(DES);
			kgen.init(56);
			key = kgen.generateKey();
			bytes = key.getEncoded();
			new FileOutputStream(f).write(bytes);
		}
		
		spec = new DESKeySpec(bytes);
		
		return spec;
  	}
	
	//取得Triple DES Key
	@SuppressWarnings("resource")
	public static SecretKeySpec getTripleDESKeySpec() 
		throws IOException, NoSuchAlgorithmException {
		//Triple DES
		byte[] bytes = new byte[24];
		File f = new File(TRIPLE_DES_KEY_FILE);
		SecretKey key = null;
		SecretKeySpec spec = null;
		
		//如果存在Key文件，讀取
		if (f.exists()) {
			new FileInputStream(f).read(bytes);
		} 
		//如果不存在，重新生成Key，并寫入Key文件
		else {
			KeyGenerator kgen = KeyGenerator.getInstance(TRIPLE_DES);
			kgen.init(168);
			key = kgen.generateKey();
			bytes = key.getEncoded();
			new FileOutputStream(f).write(bytes);
		}
		
		spec = new SecretKeySpec(bytes,TRIPLE_DES);
		
		return spec;
  	}
	
	//從字符串生成DES Key
	public static DESKeySpec getDESKeySpecFromString(String strKey) 
		throws NoSuchAlgorithmException, InvalidKeyException{
		DESKeySpec spec = new DESKeySpec(strKey.getBytes());
		return spec;
	}
	
	//從十六進制字節串生成DES Key
	public static DESKeySpec getDESKeySpecFromBytes(String strBytes) 
		throws NoSuchAlgorithmException, InvalidKeyException{
		DESKeySpec spec = new DESKeySpec(Util.hex2byte(strBytes.getBytes()));
		return spec;
	}
	
	//從字符串生成DES Key
	public static DESKeySpec getDESKeySpecFromOCString(String strKey) 
		throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException{
		String strKeyString = new String(Util.hex2byte(strKey.getBytes()));
		DESKeySpec spec = new DESKeySpec(strKeyString.getBytes());
		return spec;
	}
	
	//從十六進制字節串生成Triple DES Key
	public static SecretKeySpec getTripleDESKeySpecFromBytes(String strBytes) 
		throws NoSuchAlgorithmException{
		SecretKeySpec spec = new SecretKeySpec(Util.hex2byte(strBytes.getBytes()),TRIPLE_DES);
		return spec;
	}
	
	//從字符串生成Triple DES Key
	public static SecretKeySpec getTripleDESKeySpecFromString(String strKey) 
		throws NoSuchAlgorithmException{
		//SecretKeySpec spec = new SecretKeySpec(strKey.getBytes(),TRIPLE_DES);
		SecretKeySpec spec = new SecretKeySpec(strKey.getBytes(),"DESede");
		return spec;
	}
	
	public static SecretKeySpec getJSTripleDESKeySpecFromString(String strKey) 
		throws NoSuchAlgorithmException{
		SecretKeySpec spec = new SecretKeySpec(strKey.getBytes(),TRIPLE_DES);
		return spec;
	}
	
	//取得DES Key對應的字符串,此為傳給Objective C的密碼字串
	public static String getDESKeyString(){
		return Util.byte2hex(DESKeySpec.getKey());
	}
	
	//取得Triple DES Key對應的字符串,此為傳給Objective C的密碼字串
	public static String getTripleDESKeyString(){
		return Util.byte2hex(tripleDESKeySpec.getEncoded());
	}
	
	/**
	 * 加密
	 * 
	 * @param src
	 *            明文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 密文(字节)
	 * @throws Exception
	 */

	public static byte[] encrypt(byte[] src) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
    	// 一个SecretKey对象
    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    	SecretKey securekey = keyFactory.generateSecret(DESKeySpec);
    	
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
    	//Cipher cipher = Cipher.getInstance("DES/CBC/PKCS7Padding", new BouncyCastleProvider());

		//AlgorithmParameterSpec iv = new IvParameterSpec("abcdefgh".getBytes("UTF-8"));
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	public static byte[] tripleDESEncrypt(byte[] src)
			throws Exception {
		//SecureRandom sr = new SecureRandom();
		
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(TRIPLE_DES);

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, tripleDESKeySpec);

		// 现在，获取数据并加密
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            密文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 明文(字节)
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
    	// 一个SecretKey对象
    	//SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    	SecretKey securekey = keyFactory.generateSecret(DESKeySpec);
    	
		// Cipher对象实际完成解密操作
    	//AlgorithmParameterSpec iv = new IvParameterSpec("abcdefgh".getBytes("UTF-8"));
		//Cipher cipher = Cipher.getInstance("DES/CBC/PKCS7Padding", new BouncyCastleProvider());
    	Cipher cipher = Cipher.getInstance(DES);
    	
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}

	public static byte[] tripleDESDecrypt(byte[] src)
		throws Exception {
		//SecureRandom sr = new SecureRandom();
		
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(TRIPLE_DES);

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, tripleDESKeySpec);

		// 现在，获取数据并解密
		// 正式执行解密操作
		return cipher.doFinal(src);
	}
	
	/**
	 * 加密
	 * 
	 * @param src
	 *            明文(字符串)
	 * @return 密文(16进制字符串)
	 * @throws Exception
	 */
	public final static String encrypt(String src) {
		try {
			return Util.byte2hex(encrypt(src.getBytes()));
		} 
		catch (Exception e) {
			logger.debug(e.getMessage(),e);
		}
		return null;
	}

	public final static String tripleDESEncrypt(String src) {
		try {
			return Util.byte2hex(tripleDESEncrypt(src.getBytes()));
		} 
		catch (Exception e) {
			logger.debug(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            密文(字符串)
	 * @return 明文(字符串)
	 * @throws Exception
	 */
	public final static String decrypt(String src) {
		try {
			return new String(decrypt(Util.hex2byte(src.getBytes())));
		} 
		catch (Exception e) {
			logger.debug(e.getMessage(),e);
		}
		return null;
	}

	public final static String tripleDESDecrypt(String src) {
		try {
			return new String(tripleDESDecrypt(Util.hex2byte(src.getBytes())));
		} 
		catch (Exception e) {
			logger.debug(e.getMessage(),e);
		}
		return null;
	}

	// ============================把文件进行解密加密===================================
	public static File encryptFile(File file, String path, boolean isTripleDES) {
		File EncFile = new File(path);
		if (!EncFile.exists()){
			try {
				EncFile.createNewFile();
			} 
			catch (Exception e) {
				logger.debug(e.getMessage(),e);
			}
		}
		
		try {
			FileInputStream fin = new FileInputStream(file);
			ByteArrayOutputStream bout = new ByteArrayOutputStream(fin.available());
			byte b[] = new byte[fin.available()];

			while ((fin.read(b)) != -1) {
				byte temp[];
				if(isTripleDES){
					temp = tripleDESEncrypt(b);
				}
				else{
					temp = encrypt(b);
				}
				bout.write(temp, 0, temp.length);
			}
			fin.close();
			bout.close();
			FileOutputStream fout = new FileOutputStream(EncFile);
			BufferedOutputStream buffout = new BufferedOutputStream(fout);

			buffout.write(bout.toByteArray());
			buffout.close();
			fout.close();
		}

		catch (Exception e) {
			logger.debug(e.getMessage(),e);
		}

		return EncFile;
	}

	public static File decryptFile(File file, String path, boolean isTripleDES) {
		File desFile = new File(path);
		if (!desFile.exists()){
			try {
				desFile.createNewFile();
			} 
			catch (Exception e) {
				logger.debug(e.getMessage(),e);
			}
		}
		try {
			FileInputStream fin = new FileInputStream(file);
			int i = fin.available() - fin.available() % 8;

			ByteArrayOutputStream bout = new ByteArrayOutputStream(i);
			byte b[] = new byte[i];
			
			while (fin.read(b) != -1) {
				byte temp[];
				if (isTripleDES){
					temp = tripleDESDecrypt(b);
				}
				else{
					temp = decrypt(b);
				}
				bout.write(temp, 0, temp.length);
			}

			fin.close();
			bout.close();
			FileOutputStream fout = new FileOutputStream(desFile);
			BufferedOutputStream buffout = new BufferedOutputStream(fout);
			buffout.write(bout.toByteArray());
			buffout.close();
			fout.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return desFile;
	}

	public static void testGenerateDESKey() 
		throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		System.out.println("測試DES生成Key加密解密");
		DESKeySpec = getDESKeySpec();
		
		logger.info(getDESKeyString());
		
		for(int i = 0;i < Util.TEST_STRING.length;i ++){
			String cipherText = encrypt(Util.TEST_STRING[i]);
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = decrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}
	}
	
	public static void testLoadDESKey() 
		throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		logger.info("測試DES Load Key加密解密");
		//DESKeySpec = getDESKeySpecFromString("UeD3m+Df");//DES_KEY);
		DESKeySpec = getDESKeySpecFromString(DES_KEY);
		logger.info(getDESKeyString());
		for(int i = 0;i < Util.TEST_STRING.length;i ++){
			String cipherText = encrypt(Util.TEST_STRING[i]);
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = decrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}	
	}
	
	public static void testGenerateTripleDESKey() 
		throws NoSuchAlgorithmException, IOException{
		logger.info("測試Triple DES生成Key加密解密");
		tripleDESKeySpec = getTripleDESKeySpec();
		logger.info(getTripleDESKeyString());
		for(int i = 0;i < Util.TEST_STRING.length;i ++){
			String cipherText = tripleDESEncrypt(Util.TEST_STRING[i]);
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = tripleDESDecrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}
	}
	
	public static void testLoadTripleDESKey() 
		throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		logger.info("測試Triple DES Load Key加密解密");
		tripleDESKeySpec = getTripleDESKeySpecFromString(TRIPLE_DES_KEY);
		
		logger.info(getTripleDESKeyString());
		for(int i = 0;i < Util.TEST_STRING.length;i ++){
			String cipherText = tripleDESEncrypt(Util.TEST_STRING[i]);
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = tripleDESDecrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}	
	}
	
	public static void main1(String[] args) 
			throws InvalidKeyException, NoSuchAlgorithmException, IOException{
			logger.info("測試Triple DES Load Key加密解密");
			tripleDESKeySpec = getTripleDESKeySpecFromString(TRIPLE_DES_KEY);
			
			logger.info(getTripleDESKeyString());
			String cipherText = tripleDESEncrypt("123123");
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = tripleDESDecrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
			
		}
	
	public static void testDESFile() 
		throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		logger.info("測試DES生成Key文件加密解密");
		DESKeySpec = getDESKeySpec();
		
		logger.info(getDESKeyString());
		for(int i = 0;i < Util.TEST_FILES.length;i ++){
			logger.info("加密文件：" + Util.TEST_FILES[i] + " 開始 " + Util.getCurrentDateTime());
			encryptFile(new File(Util.TEST_FILES[i]),Util.ENCRYPTED_FILES[i],false);
			logger.info("加密文件：" + Util.TEST_FILES[i] + " 結束 " + Util.getCurrentDateTime());
			
			logger.info("解密文件：" + Util.ENCRYPTED_FILES[i] + " 開始 " + Util.getCurrentDateTime());
			decryptFile(new File(Util.ENCRYPTED_FILES[i]),Util.DECRYPTED_FILES[i],false);
			logger.info("解密文件：" + Util.ENCRYPTED_FILES[i] + " 結束 " + Util.getCurrentDateTime());
		}
	}

	public static void testTripleDESFile()
		throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		logger.info("測試Triple DES生成Key文件加密解密");
		tripleDESKeySpec = getTripleDESKeySpec();
		
		logger.info(getTripleDESKeyString());
		
		for(int i = 0;i < Util.TEST_FILES.length;i ++){
			logger.info("加密文件：" + Util.TEST_FILES[i] + " 開始 " + Util.getCurrentDateTime());
			encryptFile(new File(Util.TEST_FILES[i]),Util.ENCRYPTED_FILES[i],true);
			logger.info("加密文件：" + Util.TEST_FILES[i] + " 結束 " + Util.getCurrentDateTime());
			System.out.println("解密文件：" + Util.ENCRYPTED_FILES[i] + " 開始 " + Util.getCurrentDateTime());
			logger.info("解密文件：" + Util.ENCRYPTED_FILES[i] + " 開始 " + Util.getCurrentDateTime());
			decryptFile(new File(Util.ENCRYPTED_FILES[i]),Util.DECRYPTED_FILES[i],true);
			logger.info("解密文件：" + Util.ENCRYPTED_FILES[i] + " 結束 " + Util.getCurrentDateTime());
		}
	}

	//測試OC生成的DES加密的文本用Java解密
	public static void testOCDESEncryption() 
		throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
		String ocKey = "434d4d53474942494954535344455631";
		String[] ocEncrypted = new String[]{
				"7a6409d93de46a2f507db521abbb12d67522edd282ee6d6ebfd2c4ef079cf2ee1afea1c13b2d9eec786cc30b08043016dfb4b0829a2b884ecf2e5042665687b936c25466eeac51fc",
		        "d05e2780e430f5b8d04da0094001fe59fef5e31ca8cd0aa53571df39f97c7c9061f97085997476979380c1321dc8df527cd8997860a6487739a87236328c4a431295cd17515d2109",
		        "9b3c7c6f0ce2dc7622f1f1ce1855b311d7ceb74aacd4d92622f1f1ce1855b311f0425b1afb4c2285",
                "8cd8e047bb348b2603ed34b6e14d01a6103db83c19b163fc",
		        "afe60f1162736a36fbf2e35ed4f24bc40a1c595092df320de6f22883c22bdc3f",
                "050959f1d3d68a6935d8f779b9c612ea85170f7d931a61780a055ece4ae004ec"};
		logger.info("測試DES Load OC Key and Enrypted text 加密解密");
		DESKeySpec = getDESKeySpecFromBytes(ocKey.toUpperCase());
		logger.info(getDESKeyString());
		for(int i = 0;i < ocEncrypted.length;i ++){
			String cipherText = ocEncrypted[i].toUpperCase();
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = decrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}	
	}
	
	//測試OC生成的3DES加密的文本用Java解密
	public static void testOC3DESEncryption() 
		throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
		String ocKey = "464f58434f4e4e544543484e4f4c4f475947524f5550434e";
		String[] ocEncrypted = new String[]{
				"2f92bb7ae8d555030ec00155b15749a39f3f74b5cca7547b3611c19be7ff35c64069ef68b4d00e79164ca5ccc48c8790b37b8221321709ea739195db9f52241e99da5447c34dcd3f",
		        "1f33cdfe4eff5d7bc3e29aee0d59a10c8ddfeac16f68277fc22b1c2c54ca09da18b3770352106e4e55e826ecfb3329d5858335430c6f5db2f1b29c7c77d6b01625c2330b20f3dc07",
		        "402b236ed25661b2dc7834f82a4b714ad1145469fbd48694dc7834f82a4b714a202ec60ef80ae9a6",
                "69404ba98e3a4730d8c7307d3fc7e85b8501a1eb1ce14c03",
		        "f1a148028ad240c33a83cb99f8d94a4fa2f99aa39221ac3db9fa1088a1917049",
                "7c9b876ffa4527db0ec3e88458084d6ffcce9fcc171d65bc63d5b94b51cd9eb5"};
		logger.info("測試DES Load OC Key and Enrypted text 加密解密");
		//tripleDESKeySpec = getTripleDESKeySpecFromString("FOXCONNTECHNOLOGYGROUPCN");
		tripleDESKeySpec = getTripleDESKeySpecFromBytes(ocKey.toUpperCase());
		logger.info(getTripleDESKeyString());
		for(int i = 0;i < ocEncrypted.length;i ++){
			String cipherText = ocEncrypted[i].toUpperCase();
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = tripleDESDecrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}	
	}
	
	//測試Android生成的DES加密的文本用Java解密
	public static void testAndroidDESEncryption() 
		throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
		String androidKey = "40BF469D3D19BA46";
		String[] androidEncrypted = new String[]{
				"6E137D0B986D94D4D10DF3DFBE11D8A5F1A1FC212A6E9530DA4F04EEF0B202272289C291E471101101F8582667E022B553C4AAADB9EBB8ED2A065C81D16170D6ECF3443603AE45B3",
		        "BD00AB88F09231FAA194F2C74946C434B6A445FF818D5FD7A516125DDAA71CC07D846D5E06DBC78DF50FD9831F305D3B17545751443F3A6FC5BBBEE97C9CCB1A6927EE7B7D372E7F",
		        "A61D6137FCD846BD840A81A36243874838D786CA209A46E3840A81A3624387480BB0F71EFDD3C5C0",
                "21FC65CEF0367555B3D70DC4BF426398A1746B9440B0727C9F00095283001972",
		        "5424B3C3DB6639940545706CDA8E38015F87622C2BCBB0090B8758E2002C8D0C",
                "4CB1DB37220C1F41FE4054ED3B1B503C9DB53B6DCBDC4EC3EFF3E0E21D3E5034"};
		logger.info("測試DES Load Android Key and Enrypted text 加密解密");
		DESKeySpec = getDESKeySpecFromBytes(androidKey.toUpperCase());
		logger.info(getDESKeyString());
		for(int i = 0;i < androidEncrypted.length;i ++){
			String cipherText = androidEncrypted[i].toUpperCase();
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = decrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}	
	}
	
	//測試Android生成的3DES加密的文本用Java解密
	public static void testAndroid3DESEncryption() 
		throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
		String androidKey = "5240855810D0EF1A5DE0CB5BC8130E5129E9516445CD8A54";
		String[] androidEncrypted = new String[]{
				"AD7C83756E2256B0421F0465710680417D071E3CDDA7545764EE41BD69ACA9145B2904C8A097C58F8A7FB58C12471A96F8B9EA63AF26EAA13EE7D97C336415BF5EBFCFD254961457",
		        "F57FE57C2838886862A48D9BA6DA9A18C961C831A492F1A2E5515E368899416D646D66D1A81A68432A0FAE6B230D7221A32D1A35373F62382510A2FA89A1B25D7E68EE96ACE7B604",
		        "EEA476256F1A7662A1AB83478DC61D0E53450AA4D97ED802A1AB83478DC61D0E046793D64D25F187",
                "009835ED65795A9C6DC15147A5CED46FCDE0629D78466C4EEBBFB307FA445A31",
		        "58FB4E3E70717E163C7A7408EFED7C63E347CE52EC3098681F2698A3E42E832C",
                "9706ACDE253D36DD26303A9AEAED70975766BB678BE35AA2ED193DA3897F21CE"};
		logger.info("測試DES Load Android Key and Enrypted text 加密解密");
		tripleDESKeySpec = getTripleDESKeySpecFromBytes(androidKey.toUpperCase());
		logger.info(getTripleDESKeyString());
		for(int i = 0;i < androidEncrypted.length;i ++){
			String cipherText = androidEncrypted[i].toUpperCase();
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = tripleDESDecrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}	
	}
	
	//測試JS生成的DES加密的文本用Java解密
	public static void testJSDESEncryption() 
		throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
		String jsKey = "kFR0a_38";
		String[] jsEncrypted = new String[]{
				"3b94facda0bec2ddb434a2e714e977fb",
				"3b94facda0bec2dd983b3892c88bc6a3c95c03e4f5e17841",
                "3b94facda0bec2dd989dc10e53c773c43ae5e854a1900351",
                "3b94facda0bec2dd43b4526017f0b4baf964c44752e786db",
                "3b94facda0bec2dde77d23512d3b9b6213bd4f411884e016",
                "3b94facda0bec2dd12664df83fbc4dd3542c8e8cbff3073f",
                "3b94facda0bec2dd472d9b7b43aef0bfcd037deb36fe85c2",
                "3b94facda0bec2dd143719ba9e720d788299bbd69cc0a2b1",
                "3b94facda0bec2dd77c52e6340449434b434a2e714e977fb",
                "3b94facda0bec2dd77c52e63404494340ac05d1a6305c41dc95c03e4f5e17841"};
		logger.info("測試DES Load JS String Key and Enrypted text 加密解密");
		DESKeySpec = getDESKeySpecFromString(jsKey);
		logger.info(getDESKeyString());
		String strSuffix = getRandomSuffix();
		for(int i = 0;i < jsEncrypted.length;i ++){
			String cipherText = jsEncrypted[i].toUpperCase().substring(0,jsEncrypted[i].length() - 16) + strSuffix;
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = decrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText.trim());
		}	
	}
	
	
	//
	public static void testDESStringKeyForJS() 
		throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		System.out.println("測試DES String Key加密解密 for JavaScript");
		//DESKeySpec = getDESKeySpecFromString("UeD3m+Df");//DES_KEY);
		DESKeySpec = getDESKeySpecFromString("12345678");
	
		String[] javaString = new String[]{
				"Genius is one percent inspiration and ninety-nine percent perspiration",
				"Where there is a will there is a way",
				"He who laughs last laughs best",
				"abcdefgh",
				"12345"};
		
		logger.info(getDESKeyString());
		for(int i = 0;i < javaString.length;i ++){
			String cipherText = encrypt(javaString[i]);
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = decrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}	
	}
	
	public static void test3DESStringKeyForJS() 
		throws InvalidKeyException, NoSuchAlgorithmException, IOException{
		logger.info("測試DES String Key加密解密 for JavaScript");
		//DESKeySpec = getDESKeySpecFromString("UeD3m+Df");//DES_KEY);
		//tripleDESKeySpec = getTripleDESKeySpecFromString(TRIPLE_DES_KEY);
		tripleDESKeySpec = getTripleDESKeySpecFromString("cZzvzmbjZRWkdjZwj2BNWT1P");
		logger.info(getTripleDESKeyString());
		String[] javaString = new String[]{
				"Genius is one percent inspiration and ninety-nine percent perspiration",
				"Where there is a will there is a way",
				"He who laughs last laughs best",
				"abcdefgh",
				"12345"};
		
		for(int i = 0;i < javaString.length;i ++){
			String cipherText = tripleDESEncrypt(javaString[i]);
			logger.info("密文：");
			logger.info(cipherText);
			String plainText = tripleDESDecrypt(cipherText);
			logger.info("明文：");
			logger.info(plainText);
		}	
	}
	
	//ECB模式
	//因為DES和3DES算法算法，javascript和java加密的結果只有最后十六位不同，
	//javascript可以解密java加密的結果，只是需要剔除最后的不可見字符
	//但是java不能解密javascript的加密結果
	//所以，javascript加密時，明文后補充8個空格，生成密文，
	//java解密時，用java加密8個空字符的密文最后16位替換javascript密文的最后16位
	//最后對解密的密文剔除結尾的空格
	public static String getRandomSuffix(){
		String strRandom = encrypt("        ");
		return strRandom.substring(strRandom.length() - 16);
	}

	public static String get3DESRandomSuffix(){
		String strRandom = tripleDESEncrypt("        ");
		System.out.println(strRandom);
		return strRandom.substring(strRandom.length() - 16);
	}
	
	//測試JS生成的3DES加密的文本用Java解密
		public static void testJS3DESEncryption() 
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException{
			String jsKey = "cZzvzmbjZRWkdjZwj2BNWT1P";
			String[] jsEncrypted = new String[]{
					"0f3d0b698abf4282b4d073ea2c41e1bc",
			        "7095ed2b7b548762c2c11d3df9fa94ca",
			        "7095ed2b7b54876285f9de50059fd23f81802b4a44f0a6bc",
	                "7095ed2b7b548762c9609433f7a0556dc2c11d3df9fa94ca",
	                "0dfde5f919fc981cce019abac80931b2c713438900350886ce019abac80931b2383d64c1dd6e8092b4d073ea2c41e1bc",
	                "785611930ad1c793e6f65d15c47565eccdfc84295edc1c0854513f607797dc827ef1d547a8a0f6ef"
			};
			
			logger.info("測試DES Load JavaScript 3DES Key and Encrypted text 加密解密");
			tripleDESKeySpec = getJSTripleDESKeySpecFromString(jsKey);
			
			String strSuffix = get3DESRandomSuffix();
			logger.info(strSuffix);
			for(int i = 0;i < jsEncrypted.length;i ++){
				String cipherText = jsEncrypted[i].toUpperCase().substring(0,jsEncrypted[i].length() - 16) + strSuffix;
				logger.info("密文：");
				logger.info(cipherText);
				String plainText = tripleDESDecrypt(cipherText);
				logger.info("明文：");
				logger.info(plainText);
			}	
		}
		/**
		 * 解密JS的3des密文
		 */
		public static String JS3DESEncryption(String jsKey,String value){
			try{
				if(jsKey.length()>24){
					jsKey = jsKey.substring(0, 23);
					
				}else{
					for( int i= jsKey.length();i<24;i++){
						jsKey +="0";
					}
				}
				tripleDESKeySpec = getJSTripleDESKeySpecFromString(jsKey);
				
				String strSuffix = get3DESRandomSuffix();
				String cipherText = value.toUpperCase().substring(0,value.length() - 16) + strSuffix;
				//System.out.println("密文：");
				//System.out.println(cipherText);
				String plainText = tripleDESDecrypt(cipherText);
				//System.out.println("明文：");
				//System.out.println(plainText);
				return plainText.trim();
			}catch(Exception e){
				logger.debug(e.getMessage(),e);
				return "";
			}			
		}
		public static void main(String[] args) 
				throws InvalidKeyException, NoSuchAlgorithmException, IOException{
			
			String jsKey = "321321000000000000000000";
			String[] jsEncrypted = new String[]{
					"f658d7d229f959b5bcd7a64c47ce8da6",
			        "7095ed2b7b548762c2c11d3df9fa94ca",
			        "7095ed2b7b54876285f9de50059fd23f81802b4a44f0a6bc",
	                "7095ed2b7b548762c9609433f7a0556dc2c11d3df9fa94ca",
	                "0dfde5f919fc981cce019abac80931b2c713438900350886ce019abac80931b2383d64c1dd6e8092b4d073ea2c41e1bc",
	                "785611930ad1c793e6f65d15c47565eccdfc84295edc1c0854513f607797dc827ef1d547a8a0f6ef"
			};
			
			logger.info("測試DES Load JavaScript 3DES Key and Encrypted text 加密解密");
			tripleDESKeySpec = getJSTripleDESKeySpecFromString(jsKey);
			
			String strSuffix = get3DESRandomSuffix();
			logger.info(strSuffix);
			for(int i = 0;i < jsEncrypted.length;i ++){
				String cipherText = jsEncrypted[i].toUpperCase().substring(0,jsEncrypted[i].length() - 16) + strSuffix;
				logger.info("密文：");
				logger.info(cipherText);
				String plainText = tripleDESDecrypt(cipherText);
				logger.info("明文：");
				logger.info(plainText);
			}	
		}
	
}
