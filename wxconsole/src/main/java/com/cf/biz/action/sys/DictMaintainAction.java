/**
 * 
 */
package com.cf.biz.action.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cf.biz.domain.Result;
import com.cf.service.sys.IDictMaintainService;
import com.cf.util.http.UHttpServlet;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wxbatis.impl.data.Page;


/**
 * 类 <code>DictMaintainAction</code>数据字典维护，主要包括增删改查功能
 * @author sven
 * @version 20150922
 */
@Controller("dictMaitainAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default") 
public class DictMaintainAction extends ActionSupport implements Action {
	private static final long serialVersionUID = -6225757945777787645L;
	private static final Logger logger = Logger.getLogger(DictMaintainAction.class);
	@Autowired
	private IDictMaintainService dictMaintainService;
	
	private String retMessage;
	private  boolean success;
	private  int total;
	private List<Map<String, String>> dictList;
	private HashMap<String, List<Map<String, String>>> dictMap1;
	private List<Map<String, String>> dictMapList;
	/**
	 * 从缓存中中取对应的段
	 * 参数是request中的groupcode
	 * 
	 * @return
	 */
	@org.apache.struts2.convention.annotation.Action(value = "dictMaintainAction!getDict",results = { @org.apache.struts2.convention.annotation.Result(type = "json",  
            params={"includeProperties","success,total,dictList.*\\.sin,retMessage"}) })
	public String getDict() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String gp = request.getParameter("groupcode"); 
	    dictList = dictMaintainService.getDict(gp);
	    success = true;
		return Action.SUCCESS;
	}
	

	/**
	 * 从缓存中中取对应的的LIST段
	 * 参数是request中的多个groupid
	 * 
	 * @return
	 */
	@org.apache.struts2.convention.annotation.Action(value = "dictMaintainAction!getDictMap",results = { @org.apache.struts2.convention.annotation.Result(type = "json",  
            params={"includeProperties","success,total,dictMap1.*\\.sin,retMessage"}) })
	public String getDictMap() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String str  = request.getParameter("groupcode"); 
		String[] strArray = str.split(",");
		List <String> param = new ArrayList<String>();
		Collections.addAll(param, strArray);
		Map<String, String>  Temp12 = null;
		
		dictMap1 = dictMaintainService.getDictMap(param);
		dictMapList = new ArrayList<Map<String,String>>();
		for(int i=0; i<param.size();i++){
			for(int j=0; j<dictMap1.get(param.get(i)).size(); j++){
				Temp12 =  dictMap1.get(param.get(i)).get(j);
				dictMapList.add(Temp12);
			}
		}
			
	    success = true;
		return Action.SUCCESS;
	}
	
	public List<Map<String, String>> getDictMapList() {
		return dictMapList;
	}


	public void setDictMapList(List<Map<String, String>> dictMapList) {
		this.dictMapList = dictMapList;
	}


	/**
	 * 刷新数据字典缓存
	 * 
	 * @return
	 */
	public String refreshDict() throws Exception {
		success = dictMaintainService.refreshDict();
		if (success){
			retMessage = "字典加载成功!";
		}else{
			retMessage = "字典加载失败!";
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 数据字典维护查询数据字典
	 * 参数:
	 * 
	 * @return
	 */
	@org.apache.struts2.convention.annotation.Action(value = "dictMaintainAction!queryDict",results = { @org.apache.struts2.convention.annotation.Result(type = "json",  
            params={"includeProperties","success,total,dictList.*\\.sin,retMessage"}) })
	public String queryDict() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HashMap<String,String> params = UHttpServlet.GetRequestParameters(request);
		try{
			Page<Map<String,String>> pageobj = dictMaintainService.queryDict(params);
			dictList = pageobj.getResult();
			total = pageobj.getTotalCount();
			success = true;
			if (dictList.size() > 0) {
				//retMessage = "查询成功";
			} else {
				retMessage = "查询无记录";
			}
		}catch(Exception e){
			retMessage = "查询失败-服务器异常";
			success = false;
			logger.error(e.getMessage(),e);
		}
		return Action.SUCCESS;
	}
	/**
	 * 新增数据字典记录
	 * @return
	 * @throws Exception
	 */
	@org.apache.struts2.convention.annotation.Action(value = "dictMaintainActionsave!saveDict",results = { @org.apache.struts2.convention.annotation.Result(type = "json",  
            params={"includeProperties","success,retMessage"}) })
	public String saveDict() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HashMap<String,String> params = UHttpServlet.GetRequestParameters(request);
		try{
			Result<T> rs = dictMaintainService.saveDict(params);
			success = rs.isSuccess();
			retMessage = rs.getRetMessage();
		}catch(Exception e){
			retMessage = "保存数据失败-服务器异常";
			success = false;
			logger.error(e.getMessage(),e);
			logger.error(retMessage);
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 删除数据字典记录
	 * @return
	 * @throws Exception
	 */
	@org.apache.struts2.convention.annotation.Action(value = "dictMaintainActionsave!delDict",results = { @org.apache.struts2.convention.annotation.Result(type = "json",  
            params={"includeProperties","success,retMessage"}) })
	public String delDict() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		HashMap<String,String> params = UHttpServlet.GetRequestParameters(request);
		try{
			int ret = dictMaintainService.delDict(params);
			if (ret ==1) {
				retMessage = "请求已提交，请等待审核";
				success = true;
			}else if(ret==-1) {
				retMessage = "该记录正在进行审核";
				success = false;
			}else if(ret==-2){
				retMessage = "请选择一条记录进行删除";
				success = false;
			} else{
				retMessage = "删除数据失败";
				success = false;
			}
		}catch(Exception e){
			retMessage = "删除数据失败-服务器异常";
			success = false;
			logger.error(e.getMessage(),e);
			logger.error(retMessage);
		}
		return Action.SUCCESS;
	}

	public String getRetMessage() {
		return retMessage;
	}

	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Map<String, String>> getDictList() {
		return dictList;
	}

	public HashMap<String, List<Map<String, String>>> getDictMap1() {
		return dictMap1;
	}

	public void setDictMap1(HashMap<String, List<Map<String, String>>> dictMap1) {
		this.dictMap1 = dictMap1;
	}
	
	public void setDictList(List<Map<String, String>> dictList) {
		this.dictList = dictList;
	}
	
}
