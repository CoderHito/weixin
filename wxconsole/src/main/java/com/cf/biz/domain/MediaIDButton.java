package com.cf.biz.domain;
/**
 * ���Ӱ�ť
 * @author huanglun
 * @date 
 */
public class MediaIDButton extends Button{
	
	private String type;
	//调用新增永久素材接口返回的合法media_id
	private String media_id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	
}
