package com.cf.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.common.SimpleResult;
import com.cf.service.sys.IWeiXinMaintainService;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

@Service("weixinMaintainService")
public class WeiXinMaintainServiceImpl implements IWeiXinMaintainService{
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;

	@Override
	public Page<Map<String, String>> queryWeiXinMenu(Map<String, String> params) throws Exception {
		// TODO Auto-generated method stub
		return this.myBatisSessionTemplate.selectPage("orm.sys.weixinMenu.queryWeiXinMenuPage", params);
	}

	@Override
	public SimpleResult addWeinXinMenu(Map<String, String> params) {
		SimpleResult sr=new SimpleResult();
		int count = (int)this.myBatisSessionTemplate.insert("orm.sys.weixinMenu.addWeiXinMenu", params);
        if(count>0){
        	sr.setSuccess(true);
        }else{
        	sr.setSuccess(false);
        }
		return sr;
	}

	@Override
	public SimpleResult delWeinXinMenu(Map<String, String> params) {
		SimpleResult sr=new SimpleResult();
		int count = this.myBatisSessionTemplate.delete("orm.sys.weixinMenu.delWeiXinMenu", params);
		if(count>0){
        	sr.setSuccess(true);
        }else{
        	sr.setSuccess(false);
        }
		return sr;
	}

	@Override
	public SimpleResult upWeinXinMenu(Map<String, String> params) {
		SimpleResult sr=new SimpleResult();
		int count = this.myBatisSessionTemplate.update("orm.sys.weixinMenu.updateWeiXinMenu", params);
		if(count>0){
        	sr.setSuccess(true);
        }else{
        	sr.setSuccess(false);
        }
		return sr;
	}

	@Override
	public List<Map<String, String>> queWeiXinMenu() {
		// TODO Auto-generated method stub
		return this.myBatisSessionTemplate.selectList("orm.sys.weixinMenu.queWeiXinMenu");
	}
	
	@Override
	public SimpleResult queryOneMenu() {
		// TODO Auto-generated method stub
		SimpleResult sr=new SimpleResult();
		List<Map<String, String>> list= this.myBatisSessionTemplate.selectList("orm.sys.weixinMenu.queryOneMenu");
		if(list.size()<3){
			sr.setSuccess(true);
        }else{
        	sr.setSuccess(false);
        }
		return sr;
	}

	@Override
	public SimpleResult queryTwoMenu(Map<String, String> params) {
		// TODO Auto-generated method stub
		SimpleResult sr=new SimpleResult();
		List<Map<String, String>> sub_menu =  this.myBatisSessionTemplate.selectList("orm.sys.weixinMenu.queryTwoMenu",params);
		if(sub_menu.size()<5){
			sr.setSuccess(true);
        }else{
        	sr.setSuccess(false);
        }
		return sr;
	}

	@Override
	public SimpleResult updOneMenu(HashMap<String, String> params) {
		SimpleResult sr=new SimpleResult();
	    int sub_menu = this.myBatisSessionTemplate.update("orm.sys.weixinMenu.updateOneMenu",params);
	    return sr;
	}

	@Override
	public List<Map<String, String>> querySubMenu(Map<String, String> params) {
		// TODO Auto-generated method stub
		return this.myBatisSessionTemplate.selectList("orm.sys.weixinMenu.querySubMenu",params);
	}

	@Override
	public int selectWeixinMenu1() {
		// TODO Auto-generated method stub
		int count = (int) this.myBatisSessionTemplate.selectOne("orm.sys.weixinMenu.selectWeixinMenu1");
		return count;
	}

	@Override
	public int selectWeixinMenu2() {
		// TODO Auto-generated method stub
		int count = (int) this.myBatisSessionTemplate.selectOne("orm.sys.weixinMenu.selectWeixinMenu2");
		return count;
	}

	@Override
	public List<Map<String, String>> queryOneMenu1() {
		// TODO Auto-generated method stub
		return this.myBatisSessionTemplate.selectList("orm.sys.weixinMenu.queryOneMenu");
	}

	@Override
	public int queryParentMenu(Map<String, String> params) {
		// TODO Auto-generated method stub
		int count = (int) this.myBatisSessionTemplate.selectOne("orm.sys.weixinMenu.queryParentMenu",params);
		return count;
	}

}
