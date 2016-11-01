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
public class InnerServiceIntercetpor extends AbstractInterceptor {
	
	private static final long serialVersionUID = -4118869167091819474L;

	private static final Logger logger = Logger.getLogger(InnerServiceIntercetpor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws IOException {
		String actionResult = null;
		BaseAction action=null;
		Map<String, String> msgParam = new HashMap<String, String>();
		//Map<String, String> callBackParam = new HashMap<String,String>();
		try {
			String msgBody = getMsg();
			logger.error("请求中银通参数********="+msgBody);//test
			logger.error("invocation********="+invocation);//test
			if (stringIsNullOrEmpty(msgBody)) {
				// msgBody为空，结束
				msgParam.put("TXNCODE","0000");
				msgParam.put("TXNDATE",BaseSupport.CframeUtil.GetCurrentDay());
				msgParam.put("MERCHANTID","0");
				msgParam.put("MERCHANTSEQ",BaseSupport.CommonUtil.getRandomUUID(32));
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
			//加密
			msgParam.put("PACKETSIGNATURE",head.getString("PACKETSIGNATURE").replace(" ","+"));
			msgParam.put("PACKETCONTENT",obj.getString("body"));
			BaseSupport.myBatisSessionTemplate.insert("orm.mapper.msg.MsgReqBackupMapper.insertMsgReq", msgParam);
			
			actionResult = invocation.invoke();
			logger.error("请求中银通actionResult********="+actionResult);//test
			action = (BaseAction)invocation.getAction();
			logger.error("请求中银通action********="+action);//test
			String msg=action.getMsg();
			logger.error("请求中银通msg********="+msg);//test
			if(null==msg||"".equals(msg)){
				logger.error("请求中银通异常");
				throw new Throwable("请求中银通异常");
			}
			
			JSONObject cbObj = JSONObject.fromObject(msg);
			JSONObject cbhead = cbObj.getJSONObject("head");
			//验签
/*			Object o = action.getResponseObj();
			o=FastJsonUtil.parseObject(msg, o.getClass());
			String responseEncryptString = MD5.getMD5(o.toString());
			boolean isChange = RSAUtil.doCheck(responseEncryptString, cbhead.getString("PACKETSIGNATURE"), RSAUtil.RSA_CHECK_STRING);
			if(!isChange){
				logger.error("内部验签失败");
				throw new Throwable("返回报文验签失败");
			}*/
			msgParam.put("PACKETSIGNATURE",cbhead.getString("PACKETSIGNATURE"));
			msgParam.put("PLATFORMSEQ",cbhead.getString("PLATFORMSEQ"));
			msgParam.put("ERRCODE",cbhead.getString("ERRCODE"));
			msgParam.put("ERRMSG",cbhead.getString("ERRMSG"));
			msgParam.put("PACKETCONTENT",cbObj.getString("body"));
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}catch (Throwable  throwable) {
			// TODO Auto-generated catch block
			msgParam.put("ERRCODE", "01");
			msgParam.put("ERRMSG",throwable.getMessage());
			String response="{"+TransactionCodeConstants.MSG_NAME+":{\"head\":"+JSONObject.fromObject(msgParam).toString()+"}}";
			HttpServletResponse servletResponse = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
			servletResponse.setContentType("text/html;charset=utf-8");
			PrintWriter out = servletResponse.getWriter();
			out.write(response);
			out.close();
			if(action!=null){
				action.setMsg(response);
			}
			logger.error(throwable.getMessage(),throwable);
		}finally{
			BaseSupport.myBatisSessionTemplate.insert("orm.mapper.msg.MsgReqBackupMapper.insertMsgResp", msgParam);
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
		String msgBody = ServletActionContext.getRequest().getParameter(TransactionCodeConstants.MSG_NAME);
		return msgBody;
	}

}
