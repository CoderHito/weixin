package com.cf.util.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHAUtil {
	private static final Logger logger = LoggerFactory.getLogger(SHAUtil.class);

	/**
	 * SHA-1 加密 
	 * @param msg 需要加密的消息
	 * @return 加密之后的密文
	 */
	public static String encryptSHA1(String msg) {
		MessageDigest md = null;
		byte[] bytes = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			bytes = md.digest(msg.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			return "";
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			return "";
		}
		return byteArrayToHexString(bytes);
	}

	/**
	 * 转换字节数组为十六进制字符串 
     * @param bytes 字节数组 
     * @return 十六进制字符串 
	 */
	private static String byteArrayToHexString(byte[] bytes) {
		StringBuffer hexstr = new StringBuffer();
		String shaHex = "";
		for (int i = 0; i < bytes.length; i++) {
			shaHex = Integer.toHexString(bytes[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexstr.append(0);
			}
			hexstr.append(shaHex);
		}
		return hexstr.toString();
	}

	public static void main(String[] args) {
		System.out.println(SHAUtil.encryptSHA1("abc"));
	}
}
