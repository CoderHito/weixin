package com.cf.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cf.biz.action.base.BaseAction;
import com.cf.base.BaseSupport;
import com.cf.util.constant.TransactionCodeConstants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import net.sf.json.JSONObject;

/**
 * 拦截器 <li>白名单校验</li> <li>验签</li> <li>时间戳校验</li> <li>统一异常处理</li>
 * 
 * @author
 * 
 */
public class CommonLogServiceIntercetpor extends AbstractInterceptor {

	private static final long serialVersionUID = -4118869167091819474L;

	private static final Logger logger = Logger.getLogger(CommonLogServiceIntercetpor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws IOException {
		String actionResult = null;
		Map<String, String> msgParam = new HashMap<String, String>();
		try {
			String msgBody = getMsg();
			if (stringIsNullOrEmpty(msgBody)) {
				// msgBody为空，结束
				logger.error("没有获取到json字符串");
				throw new Throwable("报文格式不正确");
			}
			JSONObject obj = JSONObject.fromObject(msgBody);
			JSONObject head = obj.getJSONObject("head");
			if(stringIsNullOrEmpty(head.getString("TXNCODE"))){
				logger.error("交易代码不能为空");
				throw new Throwable("交易代码不能为空");
			}
			if(stringIsNullOrEmpty(head.getString("MERCHANTID"))){
				logger.error("机构ID不能为空");
				throw new Throwable("机构ID不能为空");
			}
			if(stringIsNullOrEmpty(head.getString("MERCHANTSEQ"))){
				logger.error("机构流水不能为空");
				throw new Throwable("机构流水不能为空");
			}
			if(stringIsNullOrEmpty(head.getString("TXNDATE"))){
				logger.error("交易日期不能为空");
				throw new Throwable("交易日期不能为空");
			}
			msgParam.put("TXNCODE",head.getString("TXNCODE"));
			msgParam.put("VERSION",head.getString("VERSION"));
			msgParam.put("MERCHANTID",head.getString("MERCHANTID"));
			msgParam.put("MERCHANTSEQ",head.getString("MERCHANTSEQ"));
			msgParam.put("TXNDATE",head.getString("TXNDATE"));
			msgParam.put("TXNTIME",head.getString("TXNTIME"));
			Integer count = (Integer)BaseSupport.myBatisSessionTemplate.selectOne("orm.mapper.msg.MsgReqBackupMapper.selectMsgReqCount", msgParam);
			if(count>0){
				logger.error("重复报文");
				throw new Throwable("重复报文");
			}
			msgParam.put("PACKETSIGNATURE",head.getString("PACKETSIGNATURE").replace(" ","+"));
			msgParam.put("PACKETCONTENT",obj.getString("body"));
			BaseSupport.myBatisSessionTemplate.insert("orm.mapper.msg.MsgReqBackupMapper.insertMsgReq", msgParam);
			actionResult = invocation.invoke();
			BaseAction action = (BaseAction)invocation.getAction();
			String msg=action.getMsg();
			JSONObject cbObj = JSONObject.fromObject(msg);
			JSONObject cbhead = cbObj.getJSONObject("head");

			msgParam.put("PACKETSIGNATURE",cbhead.getString("PACKETSIGNATURE"));
			msgParam.put("PLATFORMSEQ",cbhead.getString("PLATFORMSEQ"));
			msgParam.put("ERRCODE",cbhead.getString("ERRCODE"));
			msgParam.put("ERRMSG",cbhead.getString("ERRMSG"));
			msgParam.put("PACKETCONTENT",cbObj.getString("body"));
			BaseSupport.myBatisSessionTemplate.insert("orm.mapper.msg.MsgReqBackupMapper.insertMsgResp", msgParam);
		} catch(Exception e){
			logger.error(e.getMessage(),e);
		} catch (Throwable  throwable) {
			// TODO Auto-generated catch block
			logger.error(throwable.getMessage(),throwable);
			msgParam.put("ERRCODE", TransactionCodeConstants.TRANSACTION_FORMAT_ERROR);
			msgParam.put("ERRMSG",throwable.getMessage());
			String response="{"+TransactionCodeConstants.MSG_NAME+":{\"head\":"+JSONObject.fromObject(msgParam).toString()+"}}";
			HttpServletResponse servletResponse = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
			servletResponse.setContentType("text/html;charset=utf-8");
			PrintWriter out = servletResponse.getWriter();
			out.write(response);
			out.close();
			logger.error(throwable.getMessage(),throwable);
		}
		return actionResult;
	}

	/**
	 * 判断字符是否为空
	 * 
	 * @param str
	 * @return
	 */
	private Boolean stringIsNullOrEmpty(String str) {
		return str == null || str == "" || str == "null";
	}

	/**
	 * 获取报文体
	 * 
	 * @return
	 */
	private String getMsg() {
		String msgBody = ServletActionContext.getRequest().getParameter("msg");
		return msgBody;
	}
}
