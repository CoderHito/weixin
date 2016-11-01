/**
 * 
 */
package com.cf.cfsecurity.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import com.cf.cfsecurity.constant.DictConstant;
import com.cf.cfsecurity.dao.impl.CfUserDao;
import com.cf.base.BaseSupport;
import com.cf.util.security.Util;

/**
 * @author chl_seu
 *
 */
public class LogoutSuccessHandler implements  org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
	
	private CfUserDao cfUserDao; 

	public CfUserDao getCfUserDao() {
		return cfUserDao;
	}

	public void setCfUserDao(CfUserDao cfUserDao) {
		this.cfUserDao = cfUserDao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onLogoutSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
    if(authentication != null){
        System.out.print(authentication.getName() + "Logout");
        HashMap vo = new HashMap();
        //java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        //String s = format2.format(new Date());
        vo.put("LOCKER",authentication.getName());
        BaseSupport.DataLock.unLockData(vo);
        String s = Util.getCurrentDateTimeString();
        vo.put("CREATE_TIME",s);
        vo.put("USER_NAME",authentication.getName());
        vo.put("LAST_DATE",s);
        vo.put("LAST_IP",BaseSupport.CommonUtil.getClientIP(request));
        vo.put("MESSAGE","退出登录");
        vo.put("SESSION_ID", request.getSession().getId());
        vo.put("AUDIT_TYPE",DictConstant.AUDIT_TYPE_LOGOUT);
        vo.put("ID",BaseSupport.CommonUtil.getUUID());
        this.cfUserDao.LogoutForUpdate(vo);
    }
    response.sendRedirect(request.getContextPath()+"/tologin");
}

	

}
