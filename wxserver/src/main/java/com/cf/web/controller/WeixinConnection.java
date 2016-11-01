package com.cf.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cf.util.WeixinUtil;
import com.cf.util.aes.AesException;
import com.cf.util.aes.WXBizMsgCrypt;
/**
 * 微信服务器接入
 * @author Mall
 * @date 2016年1月19日 上午10:53:32
 */
@Controller
public class WeixinConnection extends BaseController{
	
	/**
	 * 微信接入
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/connection",method={RequestMethod.POST,RequestMethod.GET})
	public void getConnection(HttpServletRequest request,
		HttpServletResponse response) {
		//基于明文模式下的微信接入
		this.weixinConnection(request, response);
	}

	public void weixinConnectionSecurity(HttpServletRequest request, HttpServletResponse response) {
		String msg_signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		try {
			//获取输入流
			InputStream inputStream = request.getInputStream();
			String postData = IOUtils.toString(inputStream);
			logger.info("接收到的原始密文是："+postData);
			//存放解密后的明文
			String decryptMessage = "";
			//存放需要响应的加密数据
			String respEncryptMessage ="";
			//开始解密
			//创建消息加解密类的对象
			WXBizMsgCrypt  wxMsgCrypt = new WXBizMsgCrypt(WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_TOKEN), 
					WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_ENCODINGAESKEY), WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_APPID));
			//获得解密后的明文XML数据
			decryptMessage = wxMsgCrypt.decryptMsg(msg_signature, timestamp, nonce, postData);
			System.out.println("明文XML数据："+decryptMessage);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AesException e) {
			e.printStackTrace();
		}
	}
	
	
	public void weixinConnection(HttpServletRequest request, HttpServletResponse response){
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		if(null==echostr){
			return;
		}
		logger.info("===========微信服务器接收参数=================");
		logger.info("signature=" + signature);
		logger.info("timestamp=" + timestamp);
		logger.info("nonce=" + nonce);
		logger.info("echostr=" + echostr);
		logger.info("===========微信服务器接收参数==================");
		PrintWriter out = null;
		try {
			String result = WeixinUtil.createConnectionSignature( WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_TOKEN), timestamp, nonce );
			logger.info("加密后的字符串为："+result);
			out = response.getWriter();
			if (result.equals(signature)) {
				out.print(echostr);
			}
		} catch (IOException e1) {
			logger.error("微信服务器接入失败："+e1);
		} finally {
			out.close();
			out = null;
		}
	}
	
}
