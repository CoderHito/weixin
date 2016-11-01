package com.cf.util.http;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;

import com.cf.base.BaseSupport;
import com.cf.util.constant.SysParamsDefine;
import com.cf.util.constant.TransactionCodeConstants;
import com.cf.util.security.MD5;
import com.cf.util.security.RSAUtil;
import com.cf.util.string.FastJsonUtil;
import com.wxbatis.jdbc.EncryptablePropertyPlaceholderConfigurer;

import net.sf.json.JSONObject;
import sun.net.www.protocol.https.Handler;

public class HttpsUtil {
	protected static Logger logger = Logger.getLogger(HttpClient.class);
    /** 
     * 获得KeyStore. 
     * @param keyStorePath 
     *            密钥库路径 
     * @param password 
     *            密码 
     * @return 密钥库 
     * @throws Exception 
     */  
    public static KeyStore getKeyStore(String password, String keyStorePath)  
            throws Exception {  
        // 实例化密钥库  
        KeyStore ks = KeyStore.getInstance("JKS");  
        // 获得密钥库文件流  
        FileInputStream is = new FileInputStream(keyStorePath);  
        // 加载密钥库  
        ks.load(is, password.toCharArray());  
        // 关闭密钥库文件流  
        is.close();  
        return ks;  
    }  
  
    /** 
     * 获得SSLSocketFactory. 
     * @param password 
     *            密码 
     * @param keyStorePath 
     *            密钥库路径 
     * @param trustStorePath 
     *            信任库路径 
     * @return SSLSocketFactory 
     * @throws Exception 
     */  
    public static SSLContext getSSLContext(String password,  
            String keyStorePath, String trustStorePath) throws Exception {  
        // 实例化密钥库  
        KeyManagerFactory keyManagerFactory = KeyManagerFactory  
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());  
        // 获得密钥库  
        KeyStore keyStore = getKeyStore(password, keyStorePath);  
        // 初始化密钥工厂  
        keyManagerFactory.init(keyStore, password.toCharArray());  
  
        // 实例化信任库  
        TrustManagerFactory trustManagerFactory = TrustManagerFactory  
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());  
        // 获得信任库  
        KeyStore trustStore = getKeyStore(password, trustStorePath);  
        // 初始化信任库  
        trustManagerFactory.init(trustStore);  
        // 实例化SSL上下文  
        SSLContext ctx = SSLContext.getInstance("TLS");  
        // 初始化SSL上下文  
        ctx.init(keyManagerFactory.getKeyManagers(),  
                trustManagerFactory.getTrustManagers(), null);  
        // 获得SSLSocketFactory  
        return ctx;  
    }  
  
    /** 
     * 初始化HttpsURLConnection. 
     * @param password 
     *            密码 
     * @param keyStorePath 
     *            密钥库路径 
     * @param trustStorePath 
     *            信任库路径 
     * @throws Exception 
     */  
    public static void initHttpsURLConnection(String password,  
            String keyStorePath, String trustStorePath) throws Exception {  
        // 声明SSL上下文  
        SSLContext sslContext = null;  
        // 实例化主机名验证接口  
        HostnameVerifier hnv = new MyHostnameVerifier();
        try {  
            sslContext = getSSLContext(password, keyStorePath, trustStorePath);  
        } catch (GeneralSecurityException e) {  
            e.printStackTrace();  
        }  
        if (sslContext != null) {  
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext  
                    .getSocketFactory());  
        }  
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);  
    }  
  
    /** 
     * 发送请求. 
     * @param httpsUrl 
     *            请求的地址 
     * @param xmlStr 
     *            请求的数据 
     */  
    @SuppressWarnings("unchecked")
	public static Map<String,Object> postMsg(String httpsUrl, Map<String,Object> msg) {
		String password=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_PASSWORD);
		String keyStorePath=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_STORE_PATH);
		String trustStorePath=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_TRUST_PATH);
        HttpsURLConnection urlCon = null;
        BufferedReader bufferedReader = null;
        StringBuffer responseResult = new StringBuffer();
        PrintWriter printWriter = null;
        Map<String,Object> map=new HashMap<>();
        String rtstr = "";
        try {
        	initHttpsURLConnection(password, keyStorePath, trustStorePath);
    		JSONObject obj = JSONObject.fromObject(msg);
    		String requeststr=TransactionCodeConstants.MSG_NAME+"="+obj.toString();
            urlCon = (HttpsURLConnection)(new URL(null,httpsUrl,new Handler())).openConnection();  
            urlCon.setDoInput(true);  
            urlCon.setDoOutput(true);  
            urlCon.setRequestMethod("POST");  
            urlCon.setRequestProperty("Content-Length",  
                    String.valueOf(requeststr.getBytes().length));  
            urlCon.setUseCaches(false);
            logger.debug("请求：" + requeststr + "," + httpsUrl);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题  
			printWriter = new PrintWriter(new OutputStreamWriter(
					urlCon.getOutputStream(), "utf-8"));
            printWriter.write(requeststr);
            printWriter.flush();  
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    urlCon.getInputStream(), "utf-8"));  
            String line;  
            while ((line = bufferedReader.readLine()) != null) {  
                //System.out.println(line);
                responseResult.append(line);
            }
			rtstr = responseResult.toString();
			JSONObject jsonMsg = JSONObject.fromObject(rtstr);
			JSONObject msgBody= JSONObject.fromObject(jsonMsg.getString(TransactionCodeConstants.MSG_NAME));
			rtstr=msgBody.toString();
			logger.debug("响应：" + rtstr);
        } 	catch (Exception e) {  
            logger.debug("POST请求异常：" + e);
			Map<String,String> head=new HashMap<>();
			head.put("ERRCODE", "01");
			head.put("ERRMSG", "POST请求异常");
			map.put("head", head);
			rtstr=FastJsonUtil.toJSONString(map);
        }  finally{
        	try {
        		if(urlCon!=null){
        			urlCon.disconnect();
        		}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException ex) {
				logger.error(ex.getMessage(), ex);
			}
        }
        return FastJsonUtil.parseObject(rtstr,Map.class);
    }
    
    
    @SuppressWarnings("unchecked")
	public static <T> T postMsg(String httpsUrl, Map<String,Object> msg,Object o,T t) {
		String password=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_PASSWORD);
		String keyStorePath=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_STORE_PATH);
		String trustStorePath=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_TRUST_PATH);
        HttpsURLConnection urlCon = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        StringBuffer responseResult = new StringBuffer();
        Map<String,Object> map=new HashMap<>();
        String rtstr = "";
        try {
        	initHttpsURLConnection(password, keyStorePath, trustStorePath);
    		JSONObject obj = JSONObject.fromObject(msg);
    		//加密
    		o=FastJsonUtil.parseObject(obj.toString(), o.getClass());
    		String checkString=EncryptablePropertyPlaceholderConfigurer.getContextProperty("rsa_check_string").toString();
        	RSAPublicKey publicKey=RSAUtil.loadPublicKeyByStr(checkString);
    		String encryptString=RSAUtil.encrypt(publicKey, MD5.getMD5(o.toString()).getBytes("utf-8"));
    		obj.getJSONObject("head").put("PACKETSIGNATURE",encryptString);
    		
    		String requeststr=TransactionCodeConstants.MSG_NAME+"="+obj.toString();
        	
        	
            urlCon = (HttpsURLConnection) (new URL(null,httpsUrl,new Handler())).openConnection();  
            urlCon.setDoInput(true);  
            urlCon.setDoOutput(true);  
            urlCon.setRequestMethod("POST");  
            urlCon.setRequestProperty("Content-Length",  
                    String.valueOf(requeststr.getBytes().length));  
            urlCon.setUseCaches(false);
            logger.debug("请求：" + requeststr + "," + httpsUrl);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题  
			printWriter = new PrintWriter(new OutputStreamWriter(
					urlCon.getOutputStream(), "utf-8"));
            printWriter.write(requeststr);
            printWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    urlCon.getInputStream(),"utf-8"));  
            String line;  
            while ((line = bufferedReader.readLine()) != null) {  
                //System.out.println(line);
                responseResult.append(line);
            }
			rtstr = responseResult.toString();
			JSONObject jsonMsg = JSONObject.fromObject(rtstr);
			JSONObject msgBody= JSONObject.fromObject(jsonMsg.getString(TransactionCodeConstants.MSG_NAME));
			rtstr=msgBody.toString();
			logger.debug("响应：" + rtstr);
        } 	catch (Exception e) {  
            logger.debug("POST请求异常：" + e);
			Map<String,String> head=new HashMap<>();
			head.put("ERRCODE", "01");
			head.put("ERRMSG", "POST请求异常");
			map.put("head", head);
			rtstr=FastJsonUtil.toJSONString(map);
        }  finally{
        	try {
        		if(urlCon!=null){
        			urlCon.disconnect();
        		}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException ex) {
				logger.error(ex.getMessage(), ex);
			}
        }
        return (T)FastJsonUtil.parseObject(rtstr,t.getClass());
    }
    /** 
     * 发送请求. 
     * @param httpsUrl 
     *            请求的地址 
     * @param xmlStr 
     *            请求的数据 
     */  
	public static String postMsg(String httpsUrl, String requeststr) throws Exception{
		String password=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_PASSWORD);
		String keyStorePath=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_STORE_PATH);
		String trustStorePath=BaseSupport.CframeUtil.getSysParamsValue(SysParamsDefine.SSL_GROUP, SysParamsDefine.SSL_TRUST_PATH);
		initHttpsURLConnection(password, keyStorePath, trustStorePath);
        HttpsURLConnection urlCon = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        StringBuffer responseResult = new StringBuffer();
        String rtstr = "";
        try {
            urlCon = (HttpsURLConnection) (new URL(null,httpsUrl,new Handler())).openConnection();  
            urlCon.setDoInput(true);  
            urlCon.setDoOutput(true);  
            urlCon.setRequestMethod("POST");  
            urlCon.setRequestProperty("Content-Length",  
                    String.valueOf(requeststr.getBytes().length));  
            urlCon.setUseCaches(false);
            logger.debug("请求：" + requeststr + "," + httpsUrl);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题  
			printWriter = new PrintWriter(new OutputStreamWriter(
					urlCon.getOutputStream(), "utf-8"));
            printWriter.write(requeststr);
            printWriter.flush();   
            bufferedReader = new BufferedReader(new InputStreamReader(  
                    urlCon.getInputStream(), "utf-8"));  
            String line;  
            while ((line = bufferedReader.readLine()) != null) {  
                responseResult.append(line);
            }
			rtstr = responseResult.toString();
			logger.debug("响应：" + rtstr);
        } 	catch (Exception e) {  
            logger.debug("POST请求异常：" + e);
            throw e;
        }  finally{
        	try {
        		if(urlCon!=null){
        			urlCon.disconnect();
        		}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException ex) {
				logger.error(ex.getMessage(), ex);
			}
        }
        return rtstr;
    }
/*    public static void main(String[] args) throws Exception {  
        // 密码  
        String password = "kanzaki";  
        // 密钥库  
        String keyStorePath = "D:/tomcat.keystore";  
        // 信任库  
        String trustStorePath = "D:/tomcat.keystore";  
        // 本地起的https服务  
        String httpsUrl = "https://localhost:8443/";  
        // 传输文本  
        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fruitShop><fruits><fruit><kind>萝卜</kind></fruit><fruit><kind>菠萝</kind></fruit></fruits></fruitShop>";  
        HttpsUtil.initHttpsURLConnection(password, keyStorePath, trustStorePath);  
        // 发起请求  
        HttpsUtil.post(httpsUrl, xmlStr);  
    } */
}
