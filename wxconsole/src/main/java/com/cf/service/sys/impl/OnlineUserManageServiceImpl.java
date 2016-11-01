package com.cf.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import com.cf.base.BaseSupport;
import com.cf.base.CustomUser;
import com.cf.biz.domain.Result;
import com.cf.cfsecurity.service.impl.HttpSessionMessageListener;
import com.cf.service.sys.IOnlineUserManageService;
import com.cf.util.security.ISecurityUtil;





@SuppressWarnings("unchecked")
@Service("onlineUserManageService")
public class OnlineUserManageServiceImpl implements IOnlineUserManageService {

	@Override
	public List<Map<String, String>> queryOnlineUser(Map<String, String> params)throws Exception{
		List<Map<String,String>> userList = new ArrayList<Map<String,String>>();
		HashMap<String,HttpSession> sessionMap = HttpSessionMessageListener.getSessionMap();
		Iterator<String> iterator = sessionMap.keySet().iterator();
		while(iterator.hasNext()) {
			HttpSession tmpsession = (HttpSession)sessionMap.get(iterator.next());
			SecurityContext context = (SecurityContext)tmpsession.getAttribute(ISecurityUtil.SPRING_SECURITY_CONTEXT);
			if(context==null){
				continue;
			}
			CustomUser u = (CustomUser)context.getAuthentication().getPrincipal();
			HashMap<String,String> record = new HashMap<String,String>();
			//tmpsession.invalidate();
			if(StringUtils.isNotBlank(params.get("USER_NAME"))){
				if(params.get("USER_NAME").equals(u.getUsername())){
					record.put("USER_NAME", u.getUsername());
					record.put("sessionid", tmpsession.getId());
					userList.add(record);
					break;
				}
			}else{
				record.put("USER_NAME", u.getUsername());
				record.put("sessionid", tmpsession.getId());
				userList.add(record);
			}
		}
		return userList;
	}

	@Override
	public Result<T> destoryOnlineUser(Map<String,String> params) throws Exception {
		Result<T> rs = new Result<T>();
		HashMap<String,HttpSession> sessionMap = HttpSessionMessageListener.getSessionMap();
		Iterator<String> iterator = sessionMap.keySet().iterator();
		while(iterator.hasNext()){
			HttpSession tmpsession = (HttpSession) sessionMap.get(iterator.next());
			//如果需要注销的用户是当前自己登录的用户，则提示不允许注销
			if(BaseSupport.CframeUtil.GetCurrentUserName().equals(params.get("userId"))){
				rs.setRetMessage("当前登录的用户不能注销!");
				rs.setSuccess(true);
				return rs;
			}
			if(tmpsession.getId().equals(params.get("sessionid"))){
				tmpsession.invalidate();
				sessionMap.remove(tmpsession);
				rs.setRetMessage("注销成功!");
				rs.setSuccess(true);
				return rs;
			}
		}
		return null;
	}

}
