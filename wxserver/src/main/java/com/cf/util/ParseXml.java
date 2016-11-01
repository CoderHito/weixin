package com.cf.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cf.util.aes.AesException;
import com.cf.util.aes.WXBizMsgCrypt;
/**
 * 将消息请求转化为Map类型
 * @author Mall
 * @date 2016年1月26日 下午4:41:10
 */
public class ParseXml {
	
	private static final Logger logger = Logger.getLogger(ParseXml.class);
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static void parseXml(HttpServletRequest request, Map<String,String> map) {
		// 将字符串转为字节数组，并将字节数组转为输入流
		InputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
			//加密字符串
			String postData = IOUtils.toString(inputStream);
			//解密后的字符串,带<xml>标签
			WXBizMsgCrypt msgcrypt = new WXBizMsgCrypt(map.get("token"), map.get("aeskey"), map.get("appid"));
			String decryptStr = msgcrypt.decryptMsg(map.get("msg_signature"), map.get("timestamp"), map.get("nonce"), postData);
			parseXml(decryptStr,map);
		} catch (IOException e1) {
			logger.error("ParseXml工具类解析失败："+e1);
		} catch (AesException e1) {
			logger.error("ParseXml工具类解析失败："+e1);
		} finally{
			// 释放资源
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("ParseXml工具类解析失败："+e);
			}
			inputStream = null;
		}
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void parseXml(String request,Map<String,String> map) {
		// 将字符串转为字节数组，并将字节数组转为输入流
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(request.getBytes("UTF-8"));
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("ParseXml工具类解析失败："+e1);
		} catch (DocumentException e1) {
			logger.error("ParseXml工具类解析失败："+e1);
		} finally{
			// 释放资源
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("ParseXml工具类解析失败："+e);
			}
			inputStream = null;
		}
	}
}
