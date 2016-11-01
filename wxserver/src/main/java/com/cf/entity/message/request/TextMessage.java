package com.cf.entity.message.request;

/**
 * 文本类消息
 * 
 * @author Mall
 * @date 2016年1月20日 上午10:21:26
 */
public class TextMessage extends BaseMessage {

	//消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
