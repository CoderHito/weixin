package com.cf.cfsecurity.service.impl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionTimeoutFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
       
        // 判断是否为ajax请求
        if (httpRequest.getHeader("x-requested-with") != null
                && httpRequest.getHeader("x-requested-with")
                        .equalsIgnoreCase("XMLHttpRequest")&& session.getAttribute("SPRING_SECURITY_LAST_USERNAME") == null) {
            httpResponse.addHeader("sessionstatus", "timeOut");
            chain.doFilter(request, response);
        } else {
        	 chain.doFilter(request, response);
        }
   
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
  

}
