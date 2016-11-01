package com.cf.entity.event;

import java.util.Date;
import java.util.Map;

import com.cf.entity.Event;
import com.cf.entity.message.request.LocationMessage;
import com.cf.entity.message.request.TextMessage;
import com.cf.util.aes.AesException;
import com.cf.util.aes.WXBizMsgCrypt;

/**
 * 点击事件推送
 * @author Mall
 * @date 2016年1月21日 下午1:41:45
 */
public class EventClick extends Event{
	
	//服务咨询事件
	public static String getServiceXml(Map<String,String> requestMap){
		TextMessage text = new TextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime()+"");
		text.setMsgType("text");
		text.setContent("1:业务咨询;\n2:人事外包;\n3:系统开发;\n4:系统集成;");
		return textMessageToXml(text);
	}
	
	//服务咨询事件-加密
	public static String getServiceXmlEncrypt(Map<String,String> requestMap){
		//组装要返回的明文xml
		String respMessage = getServiceXml(requestMap);
		//组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"), requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// 联系我们事件
	public static String getLianxiXml(Map<String, String> requestMap) {
		TextMessage text = new TextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType("text");
		text.setContent("咨询电话：021-50712857\n公司邮箱：coolservice@cool-srv.com\n公司地址：金桥路1398号金台大厦411室");
		return textMessageToXml(text);
	}
	
	// 联系我们事件-加密
	public static String getLianxiXmlEncrypt(Map<String,String> requestMap){
		//组装要返回的明文xml
		String respMessage = getLianxiXml(requestMap);
		//组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"), requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	//上传图片事件
	public static String getUploadXml(Map<String,String> requestMap){
		TextMessage text = new TextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime()+"");
		text.setMsgType("text");
		text.setContent("<a href='"+requestMap.get("PicUrl")+"'>图片</a>");
		return textMessageToXml(text);
	}
	
	//上传图片事件-加密
	public static String getUploadXmlEncrypt(Map<String,String> requestMap){
		//组装要返回的明文xml
		String respMessage = getUploadXml(requestMap);
		//组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"), requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//公司地址事件
	public static String getGroupAddress(Map<String,String> requestMap){
		LocationMessage location = new LocationMessage();
		location.setToUserName(requestMap.get("FromUserName"));
		location.setFromUserName(requestMap.get("ToUserName"));
		location.setCreateTime(new Date().getTime()+"");
		location.setMsgType("location");
		location.setLocation_X("31.25216671");//纬度
		location.setLocation_Y("121.58780694");//经度
		location.setScale("20");
		location.setLabel("公司地址");	
		return textMessageToXml(location);
	}

	//公司地址事件-加密
	public static String getGroupAddressEncrypt(Map<String,String> requestMap){
		//组装要返回的明文xml
		String respMessage = getGroupAddress(requestMap);
		//组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"), requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
