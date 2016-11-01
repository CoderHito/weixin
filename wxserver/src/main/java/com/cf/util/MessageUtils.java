package com.cf.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.cf.entity.ConstantType;
import com.cf.entity.event.EventClick;
import com.cf.entity.event.EventCommon;
import com.cf.entity.event.EventWelcome;
import com.google.common.collect.Maps;

/**
 * 微信服务器消息监听
 * @author Mall
 * @date 2016年1月19日 下午3:03:11
 */
public class MessageUtils {
	
	private static final Logger logger = Logger.getLogger(MessageUtils.class);
	
	//微信服务消息接收与回复
	public static void parseMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException, DocumentException {
		logger.info(">>> Weixin Message accept and response....");
		//传数传递主要变量
		Map<String, String> requestMap = Maps.newHashMap();
		//判断是否启用安全模式
		String encrypt_type = request.getParameter("encrypt_type");
		if("aes".equals(encrypt_type)){//安全模式
			requestMap.put("encrypt_type", encrypt_type);
			String msg_signature = request.getParameter("msg_signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			requestMap.put("msg_signature", msg_signature);//加密数据
			requestMap.put("timestamp", timestamp);//时间
			requestMap.put("nonce", nonce);//随机数
			requestMap.put("token", WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_TOKEN));
			requestMap.put("appid", WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_APPID));
			requestMap.put("aeskey", WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_APPSECRET));
			ParseXml.parseXml(request, requestMap);
		}else{//明文模式
			String requestStr = IOUtils.toString(request.getInputStream(),"utf-8");
			ParseXml.parseXml(requestStr, requestMap);
		}
		// 调用核心业务，接收消息，处理消息，回复消息
		String respMessage = "";
		
		logger.info("接收的参数为："+requestMap.toString());
		//关注取消关注事件
		if("event".equals(requestMap.get("MsgType")) && "subscribe".equals(requestMap.get("Event"))){
			if("aes".equals(encrypt_type)){
				respMessage = EventWelcome.getResXmlEncrypt(requestMap);
			}else{
				respMessage = EventWelcome.getResXml(requestMap);
			}
			
		}else if("event".equals(requestMap.get("MsgType")) && "unsubscribe".equals(requestMap.get("Event"))){
			logger.info(">>>:您已经取消关注=============================");
		}
		//事件推送
		if("event".equals(requestMap.get("MsgType")) && "CLICK".equals(requestMap.get("Event"))){
			String eventKey = requestMap.get("EventKey");
			if("aes".equals(encrypt_type)){
				//服务咨询事件
				if("click_key1".equals(eventKey)){
					respMessage = EventClick.getServiceXmlEncrypt(requestMap);
				}
				//联系我们
				if("click_key5".equals(eventKey)){
					respMessage = EventClick.getLianxiXmlEncrypt(requestMap);
				}
				//公司地址推送
				if("click_key4".equals(eventKey)){
					respMessage = EventClick.getGroupAddressEncrypt(requestMap);
				}
			}else{
				//服务咨询事件
				if("click_key1".equals(eventKey)){
					respMessage = EventClick.getServiceXml(requestMap);
				}
				//联系我们
				if("click_key5".equals(eventKey)){
					respMessage = EventClick.getLianxiXml(requestMap);
				}
				//公司地址推送
				if("click_key4".equals(eventKey)){
					respMessage = EventClick.getGroupAddress(requestMap);
				}
			}
		}
		
		//***********************普通消息begin*******************************
		String MsgType = requestMap.get("MsgType");
		if("aes".equals(encrypt_type)){
			if (ConstantType.REQ_MESSAGE_TEXT.equals(MsgType)) {
				//文本类型
				respMessage = EventCommon.getCommontextEncrypt(requestMap);
			}else if(ConstantType.REQ_MESSAGE_IMAGE.equals(MsgType)) {
				//图片类型
				respMessage = EventCommon.getCommonImageEncrypt(requestMap);
			}else if(ConstantType.REQ_MESSAGE_VOICE.equals(MsgType)) {
				//语音类型
				respMessage = EventCommon.getCommonVoiceEncrypt(requestMap);
			}else if(ConstantType.REQ_MESSAGE_VIDEO.equals(MsgType)) {
				//视频消息
				respMessage = EventCommon.getCommonVideoEncrypt(requestMap);
			}else if(ConstantType.REQ_MESSAGE_SHORTVIDEO.equals(MsgType)) {
				//小视频消息
				respMessage = EventCommon.getCommonShortVideoEncrypt(requestMap);
			}else if(ConstantType.REQ_MESSAGE_LOCATION.equals(MsgType)) {
				//地理位置消息
				respMessage = EventCommon.getCommonLocationEncrypt(requestMap);
			}else if(ConstantType.REQ_MESSAGE_LINK.equals(MsgType)) {
				//链接消息
				respMessage = EventCommon.getCommonLinkEncrypt(requestMap);
			}else{
				//无法辨别的消息
				respMessage = EventCommon.getCommonOtherEncrypt(requestMap);
			}
		}else{
			if (ConstantType.REQ_MESSAGE_TEXT.equals(MsgType)) {
				//文本类型
				respMessage = EventCommon.getCommonText(requestMap);
			}else if(ConstantType.REQ_MESSAGE_IMAGE.equals(MsgType)) {
				//图片类型
				respMessage = EventCommon.getCommonImage(requestMap);
			}else if(ConstantType.REQ_MESSAGE_VOICE.equals(MsgType)) {
				//语音类型
				respMessage = EventCommon.getCommonVoice(requestMap);
			}else if(ConstantType.REQ_MESSAGE_VIDEO.equals(MsgType)) {
				//视频消息
				respMessage = EventCommon.getCommonVideo(requestMap);
			}else if(ConstantType.REQ_MESSAGE_SHORTVIDEO.equals(MsgType)) {
				//小视频消息
				respMessage = EventCommon.getCommonShortVideo(requestMap);
			}else if(ConstantType.REQ_MESSAGE_LOCATION.equals(MsgType)) {
				//地理位置消息
				respMessage = EventCommon.getCommonLocation(requestMap);
			}else if(ConstantType.REQ_MESSAGE_LINK.equals(MsgType)) {
				//链接消息
				respMessage = EventCommon.getCommonLink(requestMap);
			}
		}
		
		
		//***********************普通消息end*********************************
		
		
		
		//上传图片事件
		/*if("image".equals(requestMap.get("MsgType"))){
			String PicUrl = requestMap.get("PicUrl");//图片链接
			String MediaId = requestMap.get("MediaId");//图片消息媒体id
			logger.info(">>>PicUrl："+PicUrl);
			logger.info(">>>MediaId："+MediaId);
			ImageUploadUtils.saveDisk(PicUrl, "D:\\weixin_image/", new Date().getTime()+".jpg");
			respMessage = EventClick.getUploadXml(requestMap);
		}*/
		PrintWriter out = null;
		try {
			out = response.getWriter();
			logger.info(respMessage);
			out.print(respMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
	}
	
	
}
