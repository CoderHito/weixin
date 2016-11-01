package com.cf.util.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.cf.util.constant.TransactionCodeConstants;
import com.cf.util.string.FastJsonUtil;
import com.wxbatis.jdbc.EncryptablePropertyPlaceholderConfigurer;

import net.sf.json.JSONObject;

/**
 * RSA软加密
 * @author daosu
 *
 */
public class RSAUtil {
	//密码加密公钥
	public static final String RSA_PASS_STRING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm/1FmKXwUg7ho2sj4wHfyYh00XX5aA9m2G4Ug88Os/sReYh2JgFfYxOFIYOyiX/9hUnWzBG9Bn2woyFk/W+MkH+v2WDxXuV2Q6vKhZitZ5noBkQZdkKmG7ZyxZz7vImxw3ZKtY0FlWj3+XK2xtk7MsqHXH2CrkW6kD/jqDSD7TE0CKiy3vmwTN0zQ8zDyVWYLzNCbQCuLZu/NYSeu4dPy57lxxvkpAg0HaPFLUqsw8owKNZ5cr7CFgr89vo3Lm2n93xtPVPo6me9gh/YHUE9KMx57FzwwvPl+P/aGtEEDO4RS1WXgvn8Ytbw/iR36j7YlRvvh6qmR7gMzUdF0nrBwwIDAQAB";
	//验签公钥
	public static final String RSA_CHECK_STRING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi37nS/eMwGYkVVhNwW01kxobi7tpfWqHtGjPCEkHRSSeD5sJ1LSEIkaxwlbc6UmzfzV3U14EVzirNp4eui1fdEermrGAgKvzdZhjKANi88PhdspDewOTE2bM98YL7VeFFRxVmqn93KqPTpN0aBKl+OOSfJc3TIJk/9rgnIINSN37xi1nEAKzYWub9P2hBTwR2fVWoi3QIQEDwKmDcMdJB+YwGRMgdhRe5ofG77Yj2dk+WGByogp+Ekcnw+yKVw5fls69ZkobEQ+//RYaKoNBuPdc0ARQOy49vqueyI4/jPlfOx7n3L60aNPuPTfzqtKTSulHVHsDSwvZRCPBQ2Z/hwIDAQAB";
	//我方私钥
	public static final String RSA_PRI_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIZWIuSDtKe8HAuWzUCLz/u00xw1f97gXLMqqNbCZleOW2X9Z4WCo6iJzf0E1G75LucIMQqh/mqWqOQpgYOOHv6eorTlgp2oM7XfPfJn7jGvf2fBur2fubKM4iOJ6v3pBbRRzhi0+vGIhXkiTErUeYRHpaNwaQUIQQhlE+4E4wz3AgMBAAECgYAylRp8Ww9aHV2eC4FrrXl5+KOJOqfUXzzohcafXwG0NDk8FQe24PMI0iLLA9eNlaxQd+gnbcnjjqk3iF1pEMmgWqIn83Bh7pumh9XATN1B5fE5T76XYrqYvbvS05cJUO40u+yvG9RSVOZxo4LtT4n396Osz6o6u9Ib1j+GCc8CQQJBAPiFi/h3ZgYYtLBBkH+C+nV/rwh5VpummQsqkfw+8zi5WRsWBQg3XBbkNMxBA85T52S5tY8BInP71jBQKqW8D9cCQQCKYPquxoGZpQTxvBKKR3Aq5J85gBIR7JNzNVPAQdqoQML5NQLPzZKBdoqEU73tJIIIOE79ABQQE1zhlPvS38fhAkBIh/lk57vv9Y/ujQQWIuzkFALKj1c2kDXEa4U1wE2N9ZJK9G89iaKnz6yVHDh3JEe5PvX9KZCblxlY+lyoCC37AkBl+I0CWhmKxUXddQAckgQgN5T15GBsgO8vSK7M0Dp3HjIPPncx9oTtjhMI7ETbCbogoI3kdjhSmFIrkYP4oxtBAkAxp11m2YLFJcybpobmqbZ7xLztx8UvzcIeJKdOf+9pMGoqaW6Kx1HEzytnb2JqzOSDsgg33+8qb/Q0UhEWIdfy";
	//我方公钥
	public static final String RSA_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGViLkg7SnvBwLls1Ai8/7tNMcNX/e4FyzKqjWwmZXjltl/WeFgqOoic39BNRu+S7nCDEKof5qlqjkKYGDjh7+nqK05YKdqDO13z3yZ+4xr39nwbq9n7myjOIjier96QW0Uc4YtPrxiIV5IkxK1HmER6WjcGkFCEEIZRPuBOMM9wIDAQAB";
	/*
	 * 签名算法
	 */
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/*
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}
	
	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static String encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return Base64.encode(output);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}
	
	/*
	 * 验签算法
	 * 参数为MD5加密后的密文,签名数据,公钥
	 */
	public static boolean doCheck(String content, String sign, String publicKey) throws Exception {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode(publicKey);
			PublicKey pubKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes());
			boolean bverify = signature.verify(Base64.decode(sign));
			
			return bverify;
		}  catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (InvalidKeyException e) {
			throw new Exception("公钥非法");
		} catch (SignatureException e) {
			throw new Exception("创建 base64错误");
		}
	}
	
    /** 
     * 随机生成密钥对 
     */  
    public static void genKeyPair(String filePath) {  
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = null;  
        try {  
            keyPairGen = KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        // 初始化密钥对生成器，密钥大小为96-1024位  
        keyPairGen.initialize(1024,new SecureRandom());  
        // 生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        // 得到私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        // 得到公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        try {  
            // 得到公钥字符串  
            String publicKeyString = Base64.encode(publicKey.getEncoded());  
            // 得到私钥字符串  
            String privateKeyString = Base64.encode(privateKey.getEncoded());  
            // 将密钥对写入到文件  
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");  
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");  
            BufferedWriter pubbw = new BufferedWriter(pubfw);  
            BufferedWriter pribw = new BufferedWriter(prifw);  
            pubbw.write(publicKeyString);  
            pribw.write(privateKeyString);  
            pubbw.flush();  
            pubbw.close();  
            pubfw.close();  
            pribw.flush();  
            pribw.close();  
            prifw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    /** 
     * 从文件中输入流中加载公钥 
     *  
     * @param in 
     *            公钥输入流 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public static String loadPublicKeyByFile(String path) throws Exception {  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(path));  
            String readLine = null;  
            StringBuilder sb = new StringBuilder();  
            while ((readLine = br.readLine()) != null) {  
                sb.append(readLine);  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
            throw new Exception("公钥数据流读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥输入流为空");  
        }  
    }
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = Base64.decode(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }
    public static String sign(String content, String privateKey)  
    {  
        try   
        {  
            PKCS8EncodedKeySpec priPKCS8    = new PKCS8EncodedKeySpec( Base64.decode(privateKey) );   
            KeyFactory keyf = KeyFactory.getInstance("RSA");  
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);  
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);  
            signature.initSign(priKey);  
            signature.update( content.getBytes());  
            byte[] signed = signature.sign();  
            return Base64.encode(signed);  
        }  
        catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
        return null;  
    }
    /** 
     * 私钥解密过程 
     *  
     * @param privateKey 
     *            私钥 
     * @param cipherData 
     *            密文数据 
     * @return 明文 
     * @throws Exception 
     *             解密过程中的异常信息 
     */  
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)  
            throws Exception {  
        if (privateKey == null) {  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }  
    }
	public static String checkSign(Object o,String msg){
		JSONObject cbObj = JSONObject.fromObject(msg);
		JSONObject cbhead = cbObj.getJSONObject("head");
		boolean isChange = false;
		o=FastJsonUtil.parseObject(msg, o.getClass());
		String responseEncryptString = MD5.getMD5(o.toString());
		try {
			String checkString=EncryptablePropertyPlaceholderConfigurer.getContextProperty("rsa_check_string").toString();
			isChange = RSAUtil.doCheck(responseEncryptString, cbhead.getString("PACKETSIGNATURE"), checkString);
			if(!isChange){
				cbhead.put("ERRCODE", "01");
				cbhead.put("ERRMSG","内部验签失败");
				msg="{\"head\":"+cbhead.toString()+",\"body\":\"\"}";
			}		
		} catch (Exception e) {
			cbhead.put("ERRCODE", "01");
			cbhead.put("ERRMSG",e.getMessage());
			msg="{\"head\":"+cbhead.toString()+"\"body\":\"\"}";
		}
		return msg; 
	}
    public static void main(String []args){
    	try {
    		//loadPrivateKeyByStr(RSAUtil.RSA_PRI_KEY);
    		//System.out.println(sign("abcdefghu",RSAUtil.RSA_PRI_KEY));
    		//String sign =sign("abcdefgh",RSAUtil.RSA_PUB_KEY);
    		//System.out.println(doCheck("abcdefghu", sign, RSAUtil.RSA_PUB_KEY));
    		
    		System.out.println(encrypt(loadPublicKeyByStr(RSA_PUB_KEY), "abcdefghu".getBytes()));
    		String sign=encrypt(loadPublicKeyByStr(RSA_PUB_KEY), "abcdefghu".getBytes());
    		//String sign ="VNq4LXBEbTdgektxoxmk8NUfWO3oSmjH8bWpk27ZwlxN2O/rPi9XL5IlmvsPCR2rVnIlFKlcw4D0XkBDAQjR/LdJQAasybFTO2DrIQgD/5B94arpq1O1QhYaUW5r+0W1JQaA88AECid2fOMqlyOMhUTDwKIywFeU4R+3EjsIi6c=";
    		//System.out.println(doCheck("abcdefghu", sign, RSAUtil.RSA_PUB_KEY));
    	System.out.println(new String(decrypt(loadPrivateKeyByStr(RSAUtil.RSA_PRI_KEY), Base64.decode(sign))));
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
