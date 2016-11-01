package com.cf.biz.service.card;

import java.util.Map;

import com.wxbatis.impl.data.Page;

/**
 * 
 * @author hito
 *
 */
public interface ICardQueryService {
	
	//分页查询卡
	public Page<Map<String, String>> queryCardPage(Map<String, String> params);
}
