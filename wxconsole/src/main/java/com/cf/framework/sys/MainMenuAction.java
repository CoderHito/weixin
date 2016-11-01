package com.cf.framework.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.cf.base.CustomUser;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

@SuppressWarnings("serial")
@ParentPackage("json-default") 
@Namespace("/")
@Controller
public class MainMenuAction extends ActionSupport implements Action {
	
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	//利用spring注入后就可以取所有登录用户，不能加static属性，否则会取不到

	private SessionRegistry sessionRegistry;
	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	private List<Map<String, String>> sl;
	
	public List<Map<String, String>> getSl() {
		return sl;
	}

	public void setSl(List<Map<String, String>> sl) {
		this.sl = sl;
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@org.apache.struts2.convention.annotation.Action(value = "mainMenuAction",results = { @Result(type = "json",  
            params={"root","sl","ignoreHierarchy","false"}) })
	public String getMenu() throws Exception {
		Authentication auth =SecurityContextHolder.getContext().getAuthentication();
		//Details可转换为WebAuthenticationDetails
		//wauth包含着1:remoteAddress 2:sessionId
		WebAuthenticationDetails wauth =(WebAuthenticationDetails) auth.getDetails();
		System.out.println("当前登录用户的ip："+ wauth.getRemoteAddress());
		System.out.println("当前登录用户的sessionID："+ wauth.getSessionId());
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		
		System.out.println("当前登录用户的sessionID："+ request.getSession().getId());
		//Principal可转换为User
		//u包含登录账户和权限，扩展此对象可以让其包含更多信息，像真实姓名，所在部门，用户id...
		CustomUser u = (CustomUser) auth.getPrincipal();
		System.out.println("当前登录用户的账号："+ u.getUsername());
		System.out.println("当前登录用户的权限："+ u.getAuthorities());
		//System.out.println("当前登录用户的ID："+ u.getId());
		String userName=request.getParameter("userName"); 
		String userPsw=request.getParameter("userPsw"); 
		
		HttpSession hs = request.getSession();
		String node=request.getParameter("node"); 
		Map map = new HashMap();
		map.put("userid", u.getUsername());
		map.put("node", node);
		
		sl = myBatisSessionTemplate.selectList("sys.sys.getMenu",map);
//		Map map = new HashMap();
//		map.put("test", "aa");
//		sl.add(map);
		return Action.SUCCESS;
	}
}
