package com.cf.entity.event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.cf.entity.Event;
import com.cf.entity.message.request.TextMessage;
import com.cf.util.aes.AesException;
import com.cf.util.aes.WXBizMsgCrypt;

/**
 * 关注时的事件推送-关注时自定义欢迎语
 * @author Mall
 * @date 2016年1月21日 下午1:30:50
 */
public class EventWelcome extends Event{
	
	/**
	 * 欢迎语
	 * @param requestMap
	 * @return
	 */
	public static String getResXml(Map<String,String> requestMap){
		TextMessage text = new TextMessage();
		text.setToUserName(requestMap.get("FromUserName"));
		text.setFromUserName(requestMap.get("ToUserName"));
		text.setCreateTime(new Date().getTime()+"");
		text.setMsgType("text");
		text.setContent("您好，欢迎光临！今天是：\n"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\n\n"
				+ "上海酷服信息科技有限公司感谢您的关注~~,\n\n咨询热线：021-50712857");
		return textMessageToXml(text);
	}
	
	/**
	 * 欢迎语加密报文
	 * @param requestMap
	 * @return
	 */
	public static String getResXmlEncrypt(Map<String,String> requestMap){
		//组装要返回的明文xml
		String respMessage = getResXml(requestMap);
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
