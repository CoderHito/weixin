package com.cf.cfsecurity.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cf.cfsecurity.constant.DictConstant;
import com.cf.cfsecurity.dao.impl.CfUserDao;
import com.cf.base.BaseSupport;
import com.cf.base.CustomUser;
import com.cf.util.security.Util;

/**
 * spring security登录成功处理
 * 
 * @author 
 */
@SuppressWarnings({ "unchecked", "rawtypes","unused"})
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	private CfUserDao cfUserDao;
	

	public CfUserDao getCfUserDao() {
		return cfUserDao;
	}

	public void setCfUserDao(CfUserDao cfUserDao) {
		this.cfUserDao = cfUserDao;
	}


	private String indexUrl; // 登陆成功跳转路径

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserDetails user = (UserDetails) BaseSupport.SecurityUtil.getUserDetails();
		HashMap vo = getUpdateVO(user, request);
		this.cfUserDao.LoginForUpdate(vo);
		response.setContentType("text/json");
		response.getWriter().print("{\"success\" : true}");
		Cookie cookie = new Cookie("USER_ID",user.getUsername());
	    cookie.setPath("/");
	    response.addCookie(cookie);
	    CustomUser u = (CustomUser) authentication.getPrincipal();;
	    Cookie cookie1 = new Cookie("BRANCH_NO",u.getBranchNo());
	    cookie1.setPath("/");
	    response.addCookie(cookie1);
	    Authentication auth =SecurityContextHolder.getContext().getAuthentication();
	}

	
	private HashMap getUpdateVO(UserDetails user, HttpServletRequest request) {
		HashMap vo = new HashMap();
        vo.put("USER_NAME",user.getUsername());
        vo.put("WRONG_PWD_COUNT",0);
        //java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        //String s = format2.format(new Date());
        String s = Util.getCurrentDateTimeString();
        vo.put("CREATE_TIME",s);
        vo.put("LAST_DATE",s);
        System.out.println(s);
        vo.put("LAST_IP",BaseSupport.CommonUtil.getClientIP(request));
        vo.put("MESSAGE","登陆成功");
        vo.put("SESSION_ID", request.getSession().getId());
        vo.put("AUDIT_TYPE",DictConstant.AUDIT_TYPE_LOGIN);
        vo.put("ID",BaseSupport.CommonUtil.getUUID());
        vo.put("SOURCE_TYPE", DictConstant.SOURCE_TYPE_OPR);
		return vo;
	}
}
