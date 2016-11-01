package com.cf.util.context;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * CONTEXT工具类接口
 * 
 * @author shadow
 * @email 124010356@qq.com
 * @create 2012.04.28
 * 
 */
public abstract class ContextUtil {

	/**
	 * 默认编码
	 */
	public final static String DEFAULT_ENCODING = "UTF-8";

	private String encoding = encodingHook();

	/**
	 * 自定义传入编码
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 获取编码
	 * 
	 * @return String
	 */
	public String getEncoding() {
		if (null == encoding || "".equals(encoding))
			encoding = DEFAULT_ENCODING;
		return encoding;
	}

	/**
	 * 获取HttpServletRequest实例
	 * 
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = requestHook();
		try {
			request.setCharacterEncoding(getEncoding());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		return request;
	}

	/**
	 * 获取HttpServletResponse实例
	 * 
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse() {
		HttpServletResponse response = responseHook();
		response.setCharacterEncoding(getEncoding());
		return response;
	}

	/**
	 * 获取HttpSession实例
	 * 
	 * @return HttpSession
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public ServletContext getContext() {
		return contextHook();
	}
	

	/**
	 * 序列化成为JSON字符
	 * 
	 * @param object
	 * @return String
	 */
	public String getJSON(Object object) {
		return JSONHook(object);
	}

	/**
	 * 注销SESSION
	 */
	public void clearSession(HttpSession session) {
		session.invalidate();
	}

	/**
	 * 向前台push数据,默认text/plain类型数据(可有效防止XSS漏洞攻击)
	 * 
	 * @param object
	 * @param contentType
	 */
	public void flush(Object object, String contentType) {
		HttpServletResponse response = getResponse();
		if (null == contentType || "".equals(contentType))
			contentType = "text/plain";
		response.setContentType(contentType);
		try {
			response.getWriter().print(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加Cookie
	 * 
	 * @param cookie
	 */
	public void addCookie(Cookie cookie) {
		cookie.setMaxAge(604800);
		cookie.setPath("/");
		getResponse().addCookie(cookie);
	}

	/**
	 * 添加Cookie
	 * 
	 * @param cookie
	 * @param path
	 * @param time
	 */
	public void addCookie(Cookie cookie, String path, int time) {
		if (time > 0) {
			cookie.setPath(path);
			cookie.setMaxAge(time);
			getResponse().addCookie(cookie);
		}
	}

	/**
	 * 获取Cookie
	 * 
	 * @param cookieName
	 * @return Cookie
	 */
	public Cookie getCookie(String cookieName) {
		Cookie[] cookies = getRequest().getCookies();
		if (null == cookies || cookies.length <= 0)
			return null;
		for (int i = 0, len = cookies.length; i < len; i++) {
			if (cookieName.equals(cookies[i].getName()))
				return cookies[i];
		}
		return null;
	}

	/**
	 * 清除Cookie
	 * 
	 * @param cookie
	 */
	public void clearCookie(Cookie cookie) {
		cookie.setMaxAge(0);
		getResponse().addCookie(cookie);
	}

	/**
	 * 获取项目根路径
	 * 
	 * @return String
	 */
	public String getContextPath() {
		return getContext().getContextPath();
	}

	/**
	 * 获取绝对路径
	 * 
	 * @param dirPath
	 * @return String
	 */
	public String getRealPath(String dirPath) {
		if (null == dirPath || "".equals(dirPath))
			dirPath = "/";
		return getContext().getRealPath(dirPath);
	}

	/**
	 * 简化script语言的前后标签
	 * 
	 * @param content
	 * @return String
	 */
	public String simplifyScript(String content) {
		String script = "<script type='text/javascript'>" + content
				+ "</script>";
		return script;
	}
	
	// ======================重载方法======================
	
	/**
	 * request初始化钩子
	 * 
	 * @return HttpServletRequest
	 */
	public HttpServletRequest requestHook() {
		return null;
	}

	/**
	 * response初始化钩子
	 * 
	 * @return HttpServletResponse
	 */
	public HttpServletResponse responseHook() {
		return null;
	}

	/**
	 * context初始化钩子
	 * 
	 * @return ServletContext
	 */
	public ServletContext contextHook() {
		return null;
	}

	/**
	 * 编码初始化钩子
	 * 
	 * @return String
	 */
	public String encodingHook() {
		return DEFAULT_ENCODING;
	}

	/**
	 * JSON工具初始化钩子
	 * 
	 * @return String
	 */
	public String JSONHook(Object object) {
		return null;
	}
}
