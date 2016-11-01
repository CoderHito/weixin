package com.cf.biz.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cf.biz.action.base.ParentAction;
import com.cf.biz.domain.ClickButton;
import com.cf.biz.domain.ComplexButton;
import com.cf.biz.domain.MediaIDButton;
import com.cf.biz.domain.PropertyUtils;
import com.cf.biz.domain.ViewButton;
import com.cf.biz.validate.CommonCheck;
import com.cf.service.sys.IWeiXinMaintainService;
import com.cf.base.BaseSupport;
import com.cf.common.SimpleResult;
import com.cf.util.constant.CommonConstant;
import com.cf.util.http.UHttpServlet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxbatis.impl.data.Page;
import com.cf.biz.domain.Button;
import com.cf.biz.domain.CreateMenu;
import com.cf.biz.domain.HttpUtil;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 账户维护
 * @author sven
 *
 */
@Controller("weixinMaintainAction")
@Scope("prototype")
@ParentPackage("json-default") 
@Namespace("/")
public class WeiXinMaintainAction extends ParentAction{
	private static final long serialVersionUID = 1558639291096865959L;
	private static Gson gson = new Gson();
	boolean flag=false;
	@Autowired
	private IWeiXinMaintainService weixinMaintainService;
	
	@Action(value = "weixinMaintainAction!queryWeiXinPage", results = { 
			@Result(type = "json", params = {"includeProperties", "success,total,storeList.*\\.sin,retMessage","ignoreHierarchy","false"})})
	public String queryWeiXinPage(){
		try{
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HashMap<String, String> params = UHttpServlet.GetRequestParameters(request);
			Page<Map<String, String>> pageobj = weixinMaintainService.queryWeiXinMenu(params);
			storeList = pageobj.getResult();
			total = pageobj.getTotalCount();
			success = true;
			if (storeList.size() > 0) {
				retMessage = "";
			} else {
				retMessage = "查询无记录";
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
			retMessage = CommonConstant.QRY_FAIL;
			success = false;
		}
		return ParentAction.SUCCESS;
	}
	
	@Action(value = "weixinMaintainAction!dicWeiXinMenu", results = { 
			@Result(type = "json", params = {"includeProperties", "success,storeList.*\\.sin","ignoreHierarchy","false"})})
	public String dicWeiXinMenu(){
		try{
			storeList = weixinMaintainService.queWeiXinMenu();
			success = true;
		}catch(Exception e){
			logger.error(e.getMessage());
			retMessage = CommonConstant.QRY_FAIL;
			success = false;
		}
		return ParentAction.SUCCESS;
	}
	@Action(value = "weixinMaintainAction!IsActWeiXinMenu", results = { 
			@Result(type = "json", params = {"includeProperties", "success,retMessage","ignoreHierarchy","false"})})
	public String IsActWeiXinMenu(){
		try{
			int count1 = weixinMaintainService.selectWeixinMenu1();
			int count2 = weixinMaintainService.selectWeixinMenu2();
			int result = count1-count2;
			if(result==0){
				success = true;
				retMessage = "";
			}else{
				success = false;
				retMessage = "存在没给子菜单的一级菜单，请检查！";
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			retMessage = CommonConstant.QRY_FAIL;
			success = false;
		}
		return ParentAction.SUCCESS;
	}
	
	@Action(value = "weixinMaintainAction!IsDelWeiXinMenu", results = { 
			@Result(type = "json", params = {"includeProperties", "success,retMessage","ignoreHierarchy","false"})})
	public String IsDelWeiXinMenu(){
		try{
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			Map<String, String> params = UHttpServlet.GetRequestParameters(request); 
			int count = weixinMaintainService.queryParentMenu(params);
			if(count==0){
				success = true;
				retMessage = "";
			}else{
				success = false;
				retMessage = "改菜单下有子菜单，请先删除子菜单！";
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			retMessage = CommonConstant.DEL_FAIL;
			success = false;
		}
		return ParentAction.SUCCESS;
	}
	
	@Action(value = "weixinMaintainAction!addWeiXinMenu", results = { 
			@Result(type = "json", params = {"includeProperties", "success,retMessage","ignoreHierarchy","false"})})
	public String addWeiXinMenu(){
		try{
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HashMap<String, String> params = UHttpServlet.GetRequestParameters(request); 
		    String check = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
			String level=params.get("MENU_LEVEL");
			String url=params.get("URL");
			SimpleResult sr=new SimpleResult();
			sr = CommonCheck.checkSizeRange("菜单名称", params.get("MENU_NAME"), 0, 20, true);
			if(!sr.isSuccess()){
				success = false;
				retMessage = sr.getRetMessage();
				return ParentAction.SUCCESS;
			}
			if(url!=null&&!url.equals("")){
				sr =CommonCheck.checkWithRegex("网页链接", params.get("URL"), check, true);
				if(!sr.isSuccess()){
					success = false;
					retMessage = sr.getRetMessage();
					return ParentAction.SUCCESS;
				}
			}
			
				sr =CommonCheck.checkSizeRange("菜单排序号", params.get("sort"),0,2,true);
				if(!sr.isSuccess()){
					success = false;
					retMessage = sr.getRetMessage();
					return ParentAction.SUCCESS;
				}
			
			if(level.equals("0")){
				sr = weixinMaintainService.queryOneMenu();
				if(!sr.isSuccess()){
					success = false;
					retMessage = "新增菜单失败，一级菜单最多三个";
				}else{
					sr = weixinMaintainService.addWeinXinMenu(params);
					if(sr.isSuccess()){
						success = true;
						retMessage = "新增菜单成功";
					}else{
						success = false;
						retMessage = "新增菜单失败";
					}
				}
			}else{
				sr = weixinMaintainService.queryTwoMenu(params);
				if(!sr.isSuccess()){
					success = false;
					retMessage = "新增菜单失败，二级菜单最多五个";
				}else{
					sr = weixinMaintainService.addWeinXinMenu(params);
					if(sr.isSuccess()){
						success = true;
						retMessage = "新增菜单成功";
					}else{
						success = false;
						retMessage = "新增菜单失败";
					}
				}
			}
			    
		}catch(Exception e){
			logger.error(e.getMessage());
			retMessage = CommonConstant.ADD_FAIL;
			success = false;
		}
		return ParentAction.SUCCESS;
	}
	
	@Action(value = "weixinMaintainAction!delWeiXinMenu", results = { 
			@Result(type = "json", params = {"includeProperties", "success,retMessage","ignoreHierarchy","false"})})
	public String delWeiXinMenu(){
		try{
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HashMap<String, String> params = UHttpServlet.GetRequestParameters(request);
			SimpleResult sr = weixinMaintainService.delWeinXinMenu(params);
			if(sr.isSuccess()){
				success = true;
				retMessage = "删除菜单成功";
			}else{
				success = false;
				retMessage = "删除菜单失败";
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			retMessage = CommonConstant.DEL_FAIL;
			success = false;
		}
		return ParentAction.SUCCESS;
	}
	
	@Action(value = "weixinMaintainAction!updateWeiXinMenu", results = { 
			@Result(type = "json", params = {"includeProperties", "success,retMessage","ignoreHierarchy","false"})})
	public String updateWeiXinMenu(){
		try{
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			HashMap<String, String> params = UHttpServlet.GetRequestParameters(request);
			 String check = "^((https|http)://){1}(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?(([0-9]{1,3}.){3}[0-9]{1,3}|([0-9a-z_!~*\'()-]+.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$";
				String level=params.get("MENU_LEVEL");
				String url=params.get("URL");
				SimpleResult sr=new SimpleResult();
				sr = CommonCheck.checkSizeRange("菜单名称", params.get("MENU_NAME"), 0, 20, true);
				if(!sr.isSuccess()){
					success = false;
					retMessage = sr.getRetMessage();
					return ParentAction.SUCCESS;
				}
				sr =CommonCheck.checkSizeRange("菜单排序号", params.get("sort"),0,2,true);
				if(!sr.isSuccess()){
					success = false;
					retMessage = sr.getRetMessage();
					return ParentAction.SUCCESS;
				}
				if(url!=null&&!url.equals("")){
					sr =CommonCheck.checkWithRegex("网页链接", params.get("URL"), check, true);
					if(!sr.isSuccess()){
						success = false;
						retMessage = sr.getRetMessage();
						return ParentAction.SUCCESS;
					}
				}
			
			if(level.equals("1")){
				sr = weixinMaintainService.queryTwoMenu(params);
				if(!sr.isSuccess()){
					success = false;
					retMessage = "修改菜单失败，二级菜单最多五个";
				}else{
					sr = weixinMaintainService.upWeinXinMenu(params);
					if(sr.isSuccess()){
						success = true;
						retMessage = "修改菜单成功";
					}else{
						success = false;
						retMessage = "修改菜单失败";
					}
				}
			}else{
					sr = weixinMaintainService.upWeinXinMenu(params);
					if(sr.isSuccess()){
						success = true;
						retMessage = "修改菜单成功";
					}else{
						success = false;
						retMessage = "修改菜单失败";
					}
				}
			
		}catch(Exception e){
			logger.error(e.getMessage());
			retMessage = CommonConstant.MOD_FAIL;
			success = false;
		}
		return ParentAction.SUCCESS;
	}
	
	//激活菜单
	@Action(value = "weixinMaintainAction!actWeiXinMenu", results = { 
			@Result(type = "json", params = {"includeProperties", "success,retMessage","ignoreHierarchy","false"})})
	public String actWeiXinMenu() throws Exception{
		
		       //获取access_token
				String access_token = getAccessToken();
				//获取菜单URL
				String create_url_url = PropertyUtils.getProperty("api.weixin.create.menu");
				create_url_url = create_url_url.replace("ACCESS_TOKEN", access_token);
				logger.info(">>>创建菜单URL："+create_url_url);
				//获取菜单json串
				String menu_str = createMenu();
				logger.info(">>>创建菜单参数字符串："+menu_str);
				String responseStr = HttpUtil.sendHttpsRequest(create_url_url, "POST", menu_str);
				logger.info(">>>创建菜单返回字符串："+responseStr);
				Map<String,String> map = gson.fromJson(responseStr, new TypeToken<Map<String,String>>(){}.getType());
				if(map.containsKey("errcode") && "0".equals(map.get("errcode"))){
					success=true;
					retMessage="创建菜单成功";
					logger.info("创建菜单成功");
				}else{
					success=false;
					retMessage="创建菜单失败";
					logger.info("创建菜单失败，失败原因："+map.get("errmsg"));
				}		
		return ParentAction.SUCCESS;
	}
	
	
	public String createMenu() throws Exception{
		List<Map<String, String>> list = weixinMaintainService.queryOneMenu1(); 
		Button[] btn=new Button[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Map<String ,String> menubtn=list.get(i);
			btn[i]=menuBtn(menubtn);
			}
		CreateMenu menu = new CreateMenu();
		menu.setButton(btn);
		return gson.toJson(menu);	
	}
	
	/**
	 * 获取access_token,get 方式提交
	 */
	public static String getAccessToken(){
		//应用ID
		String appId = BaseSupport.CframeUtil.getSysParamsValue("0007", "0001");
		//应用密钥
		String appsecret = BaseSupport.CframeUtil.getSysParamsValue("0007", "0002");
		//获取access_token-URL
		String access_token_url = PropertyUtils.getProperty("api.weixin.access_token_url");
		access_token_url = access_token_url.replace("APPID", appId);
		access_token_url = access_token_url.replace("APPSECRET", appsecret);
		String access_token_str = HttpUtil.sendHttpsRequest(access_token_url, "GET", null);
		Map<String,String> map = gson.fromJson(access_token_str, new TypeToken<Map<String,String>>(){}.getType());
		if(map.containsKey("access_token")){
			return map.get("access_token");
		}else{
			return map.get("errcode");
		}
	}
	
	public Button menuBtn(Map<String, String> map){
		String type=map.get("type")==null?"":map.get("type");
		String level=map.get("level")==null?"":map.get("level");
		Button bu=new Button();
		if(level.equals("0")||flag){
			switch (type) {
			case "click":
				ClickButton cb = new ClickButton();
				cb.setName(map.get("menu_name"));
				cb.setType(map.get("type"));
				cb.setKey(map.get("menu_key"));
				return cb;
	        case "view":
				ViewButton vb = new ViewButton();
				vb.setName(map.get("menu_name"));
				vb.setType(map.get("type"));
				vb.setUrl(map.get("url"));
			    return vb;
	        case "scancode_push":
	        	ClickButton sp = new ClickButton();
				sp.setName(map.get("menu_name"));
				sp.setType(map.get("type"));
				sp.setKey(map.get("menu_key"));
				return sp;
	        case "scancode_waitmsg":
	        	ClickButton sw = new ClickButton();
				sw.setName(map.get("menu_name"));
				sw.setType(map.get("type"));
				sw.setKey(map.get("menu_key"));
				return sw;
	        case "pic_sysphoto":
	        	ClickButton ps = new ClickButton();
				ps.setName(map.get("menu_name"));
				ps.setType(map.get("type"));
				ps.setKey(map.get("menu_key"));
				return ps;
			case "pic_photo_or_album":
				ClickButton ppoa = new ClickButton();
				ppoa.setName(map.get("menu_name"));
				ppoa.setType(map.get("type"));
				ppoa.setKey(map.get("menu_key"));
				return ppoa;
			case "pic_weixin":
				ClickButton pw = new ClickButton();
				pw.setName(map.get("menu_name"));
				pw.setType(map.get("type"));
				pw.setKey(map.get("menu_key"));
				return pw;
			case "location_select":
				ClickButton ls = new ClickButton();
				ls.setName(map.get("menu_name"));
				ls.setType(map.get("type"));
				ls.setKey(map.get("menu_key"));
				return ls;
			case "media_id":
				MediaIDButton mi=new MediaIDButton();
				mi.setMedia_id(map.get("media_id"));
				mi.setName(map.get("menu_name"));
				mi.setType(map.get("type"));
				return mi;
			case "view_limited":
				MediaIDButton vl=new MediaIDButton();
				vl.setMedia_id(map.get("media_id"));
				vl.setName(map.get("menu_name"));
				vl.setType(map.get("type"));
				return vl;
			default:
				ComplexButton cxb=new ComplexButton();
				cxb.setName(map.get("menu_name"));
				List<Map<String, String>> list = weixinMaintainService.querySubMenu(map);
				Button[] btn=new Button[list.size()];
				if(list.size()>0){
					flag=true;
					for (int j = 0; j < list.size(); j++) {
						Map<String, String> ma=list.get(j);
						btn[j]=menuBtn(ma);
					}
				}
				cxb.setSub_button(btn);
				return cxb; 
			}
		}
		return bu;
		}
	
}
