package com.cf.util.security;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
*
* <p>Title: Util</p>
* <p>Description: Java加密解密公共方法</p>
* @author Dumbbell Yang
* @Date   2010-01-11
* @version 1.0
*/

public class Util {
	private final static Logger logger = Logger.getLogger(Util.class);
	//測試用于加密解密的文件
    public final static String[] TEST_FILES = new String[]{
    	"C:\\xml\\391Sample.xml",
    	"C:\\xml\\2134Sample.xml",
    	"C:\\xml\\4876Sample.xml",
    	"C:\\xml\\8381Sample.xml",
    	"C:\\xml\\12571Sample.xml"
    };
    
    //測試用于加密解密的文件 for Android
    public final static String[] TEST_FILES_ANDROID = new String[]{
    	"C:\\xml\\391Sample.xml",
    	"C:\\xml\\375Sample.xml",
    	"C:\\xml\\353Sample.xml"
    };
    
    public final static String[] ENCRYPTED_FILES = new String[]{
    	"C:\\xml\\Enc391Sample.xml",
    	"C:\\xml\\Enc2134Sample.xml",
    	"C:\\xml\\Enc4876Sample.xml",
    	"C:\\xml\\Enc8381Sample.xml",
    	"C:\\xml\\Enc12571Sample.xml"
    };
    
    public final static String[] DECRYPTED_FILES = new String[]{
    	"C:\\xml\\Dec391Sample.xml",
    	"C:\\xml\\Dec2134Sample.xml",
    	"C:\\xml\\Dec4876Sample.xml",
    	"C:\\xml\\Dec8381Sample.xml",
    	"C:\\xml\\Dec12571Sample.xml"
    };
    
    public final static String ZIP_FOLDER = "C:\\zip\\";
    public final static String ZIP_FOLDER2 = "C:\\zip2\\";
    public final static String ZIP_FOLDER3 = "C:\\zip3\\";
    public final static String ZIP_TO_FILE = "C:\\sales_data.zip";
    public final static String ZIP_TO_FILE_NAME = "C:\\sales_data_";
    public final static String ZIP_TO_FILE_EXT = ".zip";
    public final static String UNZIP_TO_FOLDER = "C:\\unzip\\";
    public final static String SYMMETRIC_KEY_FILE = "symmetric.key";
    
	//測試用于加密解密的字符串
	public final static String[] TEST_STRING = new String[]{
		"321321",
		"123123"
	};
	
	public static String getFileName(String strFilePath){
		return strFilePath.substring(strFilePath.lastIndexOf("\\") + 1);
	}
	
	//字串寫入二進制文件
	public static void saveToFile(String strString,String strFile) {
		try {
			FileOutputStream file = new FileOutputStream(strFile);
			file.write(strString.getBytes());
			file.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//測試用OC生成的RSA Public Key字符串
    public static final String OC_RSA_PUBLIC_KEY = 
    	  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArYO1yULWFbBF8DxC9Rw4"
        + "AFg88Ft0EpgcSN4O9SWzXi5ktGUA2ZYp2HdKaWe/qV7G9fJU1W1vkueABtuy3zKe"
        + "u2zbAzCd0d3hQtfxDlwEkU2AfxXXRnm8Oyv4EoWAqEe8d/EE7ocSotx+yLBsI2vr"
        + "XddQrnVbpIKgvsPyewNVG0ppuRqQifNPQpAW3lmOUmz7j74qCV66zEgP5Ikvb4Dc"
        + "nZN+iu9KUKWOvwU90Sg/XHknBvBkVnm4l1NnWJ7MRR5qvFud0k883CrBHSHA9i0O"
        + "C1NDMwveI5MXWNK/0UB9kE+cq6aXWus8YCAOuCxz88cyY1P3fiVfSco11wpuZLTL"
        + "pQIDAQAB";
	
	//測試用Android生成的RSA Public Key字符串
   public static final String ANDROID_RSA_PUBLIC_KEY = 
	      "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv0mQn/+d9miop0tgjBl5"
		+ "kIOVRBLXbi1M8K0X4/fK/UqqGyFfWifdUA6C3k4lXbtXrGaYhWjr6PisVqQNp9wI"
		+ "EwAzFOHWmZjt3XFfD5ENtBvFYSdXM3aICoGy9vM01bPPXaHVydA41fD9EAmipuWO"
		+ "ZtjXlATH3M1ROvM5w0z33JPnqBn92sIt/U2PoNdEOqWhsGimrmpfaZzscAcUNN1H"
		+ "HJaWt2oksrBuDHOZ5eBidrvkRPTAcWJ6108LDgpBYsYA0URkQ0F/sia2lmyv+rK3"
		+ "RSSTHrP8zaodVyP463p4RvVKdTKYac6XtzhVWSagteZPxF2vY1b77tZM/BhmsMUQ"
		+ "PwIDAQAB";
	
	//根據指定字符集生成隨機字符串
	private static final String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_+/";
	public static String getRandomString(int length) {
	    Random rand = new Random(System.currentTimeMillis());
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {
	        int pos = rand.nextInt(charset.length());
	        sb.append(charset.charAt(pos));
	    }
	    return sb.toString();
	}
    public static String getRandomNum(int minlen, int maxlen) {
        String s = "";
        if(minlen > maxlen) {
            minlen = maxlen;
        }
        for(; s.length() < maxlen; s = ("" + Math.random()).substring(2) + s) {
            ;
        }
        s = s.substring(s.length() - maxlen);
        int n = maxlen - minlen;
        if(n > 0) {
            n = (int) Math.round((double) n * Math.random());
        }
        if(n > 0) {
            s = s.substring(n);
        }
        return s;
    }
	
	//卡类型字典
	public static String getCardStatus(String cardStatus){
		switch (cardStatus) {
		case "p":
			cardStatus="制卡中";
			break;
		case "E":
			cardStatus="已入库";
			break;
		case "A":
			cardStatus="已激活";
			break;
		case "N":
			cardStatus="未激活";
			break;
		case "T":
			cardStatus="已换卡";
			break;
		case "G":
			cardStatus="已作废（可回收）";
			break;
		case "X":
			cardStatus="已过期";
			break;
		case "K":
			cardStatus="口头挂失";
			break;
		case "L":
			cardStatus="正式挂失";
			break;
		case "R":
			cardStatus="已回收";
			break;
		case "J":
			cardStatus="已换卡登记";
			break;
		case "D":
			cardStatus="已作废（不可回收）";
			break;
		case "M":
			cardStatus="已作废（已丢失）";
			break;
		}
		return cardStatus;
		
	}

	//從UUID生成隨機字符串
	public static String getRandomUUID(int length) {
		String strUUID = UUID.randomUUID().toString();
		System.out.println("UUID length:" + strUUID.length());
		while(strUUID.length() < length){
			strUUID += UUID.randomUUID().toString();
		}
	 
	    return strUUID.substring(0, length);
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	//剔除文本中的換行符
	public static String replaceNewLine(String strText){
		String strResult = "";
		int intStart = 0;
		int intLoc = strText.indexOf("\n", intStart);
		while(intLoc != -1){
			strResult += strText.substring(intStart, intLoc - 1);
			intStart = intLoc + 1;
			intLoc = strText.indexOf("\n", intStart);
		}
		strResult += strText.substring(intStart,strText.length());
		return strResult;
	}
	
	//字節到十六進制串轉換
	public static String byte2hex(byte[] b){
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n ++){
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs += ("0" + stmp);
			else
				hs += stmp;
		}
		return hs.toUpperCase();
	}
	
	//十六進制串到字節轉換
	public static byte[] hex2byte(byte[] b){
		if ((b.length % 2) != 0)
			 throw new IllegalArgumentException("长度不是偶数!");
	  
		byte[] b2 = new byte[b.length / 2];
	  
		for (int n = 0; n < b.length; n += 2){
			String item = new String(b, n, 2);
			b2[n/2] = (byte)Integer.parseInt(item, 16);
		}
		return b2;
	}
	
	public static byte[] Base64Encode(byte[] bytes) 
		throws UnsupportedEncodingException{
		BASE64Encoder base64encoder = new BASE64Encoder(); 
		String encode = base64encoder.encode(bytes);
		
		return encode.getBytes();
	}
	
	public static byte[] Base64Decode(byte[] bytes) 
		throws IOException{
		BASE64Decoder base64decoder = new BASE64Decoder(); 
		return base64decoder.decodeBuffer(new String(bytes)); 
	}
	 public static byte[] getFromBase64(String s) {  
	        byte[] b = null;  
	        String result = null;  
	        if (s != null) {  
	            BASE64Decoder decoder = new BASE64Decoder();  
	            try {  
	                b = decoder.decodeBuffer(s);  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return b;  
	}
	 
	 public static String formatNumber(String num){
		 String newStr = num.replaceAll("^(0+)", "");
		 return newStr; 
	 }
	
	 
	public static String getCurrentDate(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");      
		return sDateFormat.format(new java.util.Date());
	}
	
	public static String getCurrentDateTime(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
		return sDateFormat.format(new java.util.Date());
	}
	
	public static String getCurrentDateTimeString(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");      
		return sDateFormat.format(new java.util.Date());
	}
	public static String getDateFormat(String date) throws ParseException{
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date da=sDateFormat.parse(date);
		return DateFormat.format(da);
	}
	
	public static String getCurrentTimeString(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HHmmss");      
		return sDateFormat.format(new java.util.Date());
	}
	private static String zipFiles(String[] files, String zipToFile){ 
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];
		File file = new File(zipToFile);
		if (file.exists()){
			file.delete();
		}
		
		try {
		    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipToFile));

		    // Compress the files
		    for (int i=0; i< files.length; i++) {
		    	if (new File(files[i]).exists()){
			        FileInputStream in = new FileInputStream(files[i]);
	
			        // Add ZIP entry to output stream.
			        out.putNextEntry(new ZipEntry(getFileName(files[i])));
	
			        // Transfer bytes from the file to the ZIP file
			        int len;
			        while ((len = in.read(buf)) > 0) {
			            out.write(buf, 0, len);
			        }
	
			        // Complete the entry
			        out.closeEntry();
			        in.close();
		    	}
		    }

		    // Complete the ZIP file
		    out.close();
		    
		    return zipToFile;
		} 
		catch (IOException e) {
			logger.debug(e.getMessage(),e);
			return "";
		}
	}

	@SuppressWarnings("unused")
	private static String zipFolder(String strFolder,String zipToFile){
		File folder = new File(strFolder);
		
		if (folder.isDirectory()){
			File[] files = folder.listFiles();
			String[] arrFiles = new String[files.length];
			for(int i = 0;i < files.length;i ++){
				arrFiles[i] = files[i].getAbsolutePath();
			}
			return zipFiles(arrFiles,zipToFile);
		}
		else{
			return zipFiles(new String[]{strFolder},zipToFile);
		}
	}

	public static String unzipFile(String zippedFile, String unzipToFolder){
		if (new File(zippedFile).exists()){
			try {              
	            InputStream in = new BufferedInputStream(new FileInputStream(zippedFile));   
	            ZipInputStream zin = new ZipInputStream(in);   
	            
	            File file = new File(unzipToFolder);
	            if (file.exists() == false){
	            	file.mkdirs();
	            }
	            
	            ZipEntry e;   
	            while((e = zin.getNextEntry())!= null) {                   
	                String s = e.getName();   
	                File f = new File(unzipToFolder, s);   
	             
	                FileOutputStream out = new FileOutputStream(f);   
	                byte [] b = new byte[512];   
	                int len = 0;   
	                while ((len = zin.read(b))!= -1 ) {   
	                    out.write(b,0,len);   
	                }   
	                out.close();                                       
	            }   
	            zin.close(); 
	            
	            return unzipToFolder;
	        } 
			catch (IOException e) { 
				logger.debug(e.getMessage(),e);
				return "";
			} 
		}
		else{
			return "";
		}
	}
	
}
