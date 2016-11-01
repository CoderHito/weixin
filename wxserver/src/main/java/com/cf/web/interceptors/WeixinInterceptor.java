package com.cf.web.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cf.util.MessageUtils;
/**
 * 消息监听器
 * @author Mall
 * @date 2016年1月19日 下午2:58:07
 */
public class WeixinInterceptor extends HandlerInterceptorAdapter {

	private static final ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();
	
	private static final ThreadLocal<HttpServletResponse> localResponse = new ThreadLocal<HttpServletResponse>();
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println(request.getRequestURI());
		if(request.getRequestURI().indexOf("/connection")==-1){
			return;
		}
		try {
			String echostr = request.getParameter("echostr");
			if(StringUtils.isNotBlank(echostr)){
				return;
			}
			MessageUtils.parseMessage(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("===========设置Request和Response属性===============");
		localRequest.set(request);
		localResponse.set(response);
		return true;
	}

	public static HttpServletRequest getRequest(){
		return localRequest.get();
	}
	
	public static HttpServletResponse getResponse(){
		return localResponse.get();
	}
	
}
