package com.cf.entity.event;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.cf.entity.ConstantType;
import com.cf.entity.Event;
import com.cf.entity.message.response.ResTextMessage;
import com.cf.util.aes.AesException;
import com.cf.util.aes.WXBizMsgCrypt;

/**
 * 普通消息数据包组装
 * 
 * @author Mall
 * @date 2016年1月21日 下午2:00:33
 */
public class EventCommon extends Event {

	/**
	 * 普通文本消息发送
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonText(Map<String, String> requestMap) {

		Iterator<Entry<String, String>> it = requestMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println("key=" + key + "       value=" + value);
		}
		String content = requestMap.get("Content");
		// 回复文本消息
		ResTextMessage text = new ResTextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType(ConstantType.RES_MESSAGE_TEXT);
		if (content.contains("1")) {
			text.setContent("您输入的是文本消息!微信消息服务正在努力打造中，请稍后再试。");
		} else {
			text.setContent("您输入的是文本消息!微信消息服务正在努力打造中，请稍后再试。"
					+ "<a target=\"_blank\" href=\"http://http://www.cool-srv.com\">了解酷服官网</a>");
		}
		return textMessageToXml(text);

	}

	/**
	 * 普通文本加密消息发送
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommontextEncrypt(Map<String, String> requestMap) {
		// 组装要返回的明文xml
		String respMessage = getCommonText(requestMap);
		// 组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"),
					requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 发送图片消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonImage(Map<String, String> requestMap) {
		// 回复文本消息
		ResTextMessage text = new ResTextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType(ConstantType.RES_MESSAGE_TEXT);
		text.setContent("您输入的是图片消息!微信消息服务正在努力打造中，请稍后再试。"
				+ "<a target=\"_blank\" href=\"http://http://www.cool-srv.com\">了解酷服官网</a>");
		return textMessageToXml(text);

	}

	/**
	 * 发送加密图片消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonImageEncrypt(Map<String, String> requestMap) {
		// 组装要返回的明文xml
		String respMessage = getCommonImage(requestMap);
		// 组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"),
					requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 发送语音消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonVoice(Map<String, String> requestMap) {
		// 回复文本消息
		ResTextMessage text = new ResTextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType(ConstantType.RES_MESSAGE_TEXT);
		text.setContent("您输入的是语音消息!微信消息服务正在努力打造中，请稍后再试。"
				+ "<a target=\"_blank\" href=\"http://http://www.cool-srv.com\">了解酷服官网</a>");
		return textMessageToXml(text);

	}

	/**
	 * 发送加密语音消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonVoiceEncrypt(Map<String, String> requestMap) {
		// 组装要返回的明文xml
		String respMessage = getCommonVoice(requestMap);
		// 组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"),
					requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 发送视频消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonVideo(Map<String, String> requestMap) {
		// 回复文本消息
		ResTextMessage text = new ResTextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType(ConstantType.RES_MESSAGE_TEXT);
		text.setContent("您输入的是视频消息!微信消息服务正在努力打造中，请稍后再试。"
				+ "<a target=\"_blank\" href=\"http://http://www.cool-srv.com\">了解酷服官网</a>");
		return textMessageToXml(text);

	}

	/**
	 * 发送加密视频消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonVideoEncrypt(Map<String, String> requestMap) {
		// 组装要返回的明文xml
		String respMessage = getCommonVideo(requestMap);
		// 组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"),
					requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 发送小视频消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonShortVideo(Map<String, String> requestMap) {
		// 回复文本消息
		ResTextMessage text = new ResTextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType(ConstantType.RES_MESSAGE_TEXT);
		text.setContent("您输入的是小视频消息!微信消息服务正在努力打造中，请稍后再试。"
				+ "<a target=\"_blank\" href=\"http://http://www.cool-srv.com\">了解酷服官网</a>");
		return textMessageToXml(text);
	}

	/**
	 * 发送加密小视频消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonShortVideoEncrypt(Map<String, String> requestMap) {
		// 组装要返回的明文xml
		String respMessage = getCommonShortVideo(requestMap);
		// 组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"),
					requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 发送地理位置消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonLocation(Map<String, String> requestMap) {
		// 回复文本消息
		ResTextMessage text = new ResTextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType(ConstantType.RES_MESSAGE_TEXT);
		text.setContent("您输入的是地理位置消息！微信消息服务正在努力打造中，请稍后再试。"
				+ "<a target=\"_blank\" href=\"http://http://www.cool-srv.com\">了解酷服官网</a>");
		return textMessageToXml(text);

	}

	/**
	 * 发送加密地理位置消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonLocationEncrypt(Map<String, String> requestMap) {
		// 组装要返回的明文xml
		String respMessage = getCommonLocation(requestMap);
		// 组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"),
					requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 发送链接消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonLink(Map<String, String> requestMap) {
		// 回复文本消息
		ResTextMessage text = new ResTextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType(ConstantType.RES_MESSAGE_TEXT);
		text.setContent("您输入的是链接消息!微信消息服务正在努力打造中，请稍后再试。"
				+ "<a target=\"_blank\" href=\"http://http://www.cool-srv.com\">了解酷服官网</a>");
		return textMessageToXml(text);
	}

	/**
	 * 发送加密链接消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonLinkEncrypt(Map<String, String> requestMap) {
		// 组装要返回的明文xml
		String respMessage = getCommonLink(requestMap);
		// 组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"),
					requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 无法辨别类型的消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonOther(Map<String, String> requestMap) {
		// 回复文本消息
		ResTextMessage text = new ResTextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime() + "");
		text.setMsgType(ConstantType.RES_MESSAGE_TEXT);
		text.setContent("您输入的消息类型无法辨别!微信消息服务正在努力打造中，请稍后再试。"
				+ "<a target=\"_blank\" href=\"http://http://www.cool-srv.com\">了解酷服官网</a>");
		return textMessageToXml(text);
	}

	/**
	 * 无法辨别类型的消息
	 * 
	 * @param requestMap
	 * @return
	 */
	public static String getCommonOtherEncrypt(Map<String, String> requestMap) {
		// 组装要返回的明文xml
		String respMessage = getCommonLink(requestMap);
		// 组装要返回的加密xml
		WXBizMsgCrypt msgcrypt;
		try {
			msgcrypt = new WXBizMsgCrypt(requestMap.get("token"), requestMap.get("aeskey"), requestMap.get("appid"));
			String encryptMessage = msgcrypt.encryptMsg(respMessage, requestMap.get("timestamp"),
					requestMap.get("nonce"));
			return encryptMessage;
		} catch (AesException e) {
			e.printStackTrace();
		}
		return "";
	}

}
