package com.cf.util.security;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;  
  
public class MD5 {  
      
    public void TestMD5()  
    {  
        System.out.println("MD5加密结果:"+getMD5("123456"));  
        System.out.println("MD5加密结果:"+getMD5ByBase64("123456"));  
          
        System.out.println("MD5加密结果:"+getMD5("qq2014"));  
        System.out.println("MD5加密结果:"+getMD5("baidu2014"));  
        System.out.println("MD5加密结果:"+getMD5("md5123456"));  
        System.out.println("MD5加密结果:"+getMD5("北京天安门"));  
    }  
      
    /** 
     * MD5加密 
     * @param message 要进行MD5加密的字符串 
     * @return 加密结果为32位字符串 
     */  
    public static String getMD5(String message) {  
        MessageDigest messageDigest = null;  
        StringBuffer md5StrBuff = new StringBuffer();  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();  
            messageDigest.update(message.getBytes("UTF-8"));  
              
            byte[] byteArray = messageDigest.digest();  
            for (int i = 0; i < byteArray.length; i++)   
            {  
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
                else  
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
            }  
        } catch (Exception e) {  
            throw new RuntimeException();  
        }  
        return md5StrBuff.toString().toLowerCase();//字母大写  
    }  
      
    /** 
     * MD5加密,加密结果采用Base64进行编码 
     * @param message 要进行MD5加密的字符串 
     * @return 
     */  
    public static String getMD5ByBase64(String message) {  
        MessageDigest md= null;  
        try {  
            md= MessageDigest.getInstance("MD5");  
            byte md5[]=md.digest(message.getBytes());  
            BASE64Encoder base64=new BASE64Encoder();  
            return base64.encode(md5);  
        } catch (Exception e) {  
            throw new RuntimeException();  
        }  
    }  
      
} 
