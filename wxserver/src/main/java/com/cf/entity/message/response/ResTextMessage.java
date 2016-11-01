package com.cf.entity.message.response;

/**
 * 发送文本消息
 * 
 * @author Mall
 * @date 2016年1月25日 上午10:26:18
 */
public class ResTextMessage extends ResBaseMessage {
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
