package com.cf.entity.message.request;

/**
 * 接收消息公共父类
 * 
 * @author Mall
 * @date 2016年1月20日 上午10:08:34
 */
public class BaseMessage{

	// 开发者微信号
	private String ToUserName;

	// 发送方账号
	private String FromUserName;

	// 创建时间
	private String CreateTime;

	// 消息类型
	private String MsgType;

	// 消息id
	private String MsgId;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

}
