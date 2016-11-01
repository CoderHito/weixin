package com.cf.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	@RequestMapping("/page/404")
	public String to404(HttpServletRequest request){
		return "/page/404";
	}
	@RequestMapping("/page/405")
	public String to405(HttpServletRequest request){
		return "/page/405";
	}
	@RequestMapping("/page/500")
	public String to500(HttpServletRequest request){
		return "/page/500";
	}
}
