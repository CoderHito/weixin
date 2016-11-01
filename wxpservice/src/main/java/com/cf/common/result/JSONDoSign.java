package com.cf.common.result;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.SerializationParams;
import org.apache.struts2.json.smd.SMD;
import org.apache.struts2.json.smd.SMDGenerator;

import com.cf.biz.action.base.BaseAction;
import com.cf.util.security.MD5;
import com.cf.util.security.RSAUtil;
import com.wxbatis.jdbc.EncryptablePropertyPlaceholderConfigurer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.WildcardUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import net.sf.json.JSONObject;

public class JSONDoSign implements Result {
	public static final String DEFAULT_PARAM = "status";
	private static final long serialVersionUID = 8624350183189931165L;
	private static final Logger LOG = LoggerFactory.getLogger(JSONDoSign.class);
	private String encoding;
	private String defaultEncoding;
	private List<Pattern> includeProperties;
	private List<Pattern> excludeProperties;
	private String root;
	private boolean wrapWithComments;
	private boolean prefix;
	private boolean enableSMD;
	private boolean enableGZIP;
	private boolean ignoreHierarchy;
	private boolean ignoreInterfaces;
	private boolean enumAsBean;
	private boolean noCache;
	private boolean excludeNullProperties;
	private int statusCode;
	private int errorCode;
	private String callbackParameter;
	private String contentType;
	private String wrapPrefix;
	private String wrapSuffix;

	public JSONDoSign() {
		this.defaultEncoding = "ISO-8859-1";

		this.enableSMD = false;
		this.enableGZIP = false;
		this.ignoreHierarchy = true;
		this.ignoreInterfaces = true;
		this.enumAsBean = false;
		this.noCache = false;
		this.excludeNullProperties = false;
	}

	@Inject("struts.i18n.encoding")
	public void setDefaultEncoding(String val) {
		this.defaultEncoding = val;
	}

	public List<Pattern> getExcludePropertiesList() {
		return this.excludeProperties;
	}

	public void setExcludeProperties(String commaDelim) {
		Set<String> excludePatterns = JSONUtil.asSet(commaDelim);
		if (excludePatterns != null) {
			this.excludeProperties = new ArrayList<Pattern>(excludePatterns.size());
			for (String pattern : excludePatterns)
				this.excludeProperties.add(Pattern.compile(pattern));
		}
	}

	public void setExcludeWildcards(String commaDelim) {
		Set<String> excludePatterns = JSONUtil.asSet(commaDelim);
		if (excludePatterns != null) {
			this.excludeProperties = new ArrayList<Pattern>(excludePatterns.size());
			for (String pattern : excludePatterns)
				this.excludeProperties.add(WildcardUtil.compileWildcardPattern(pattern));
		}
	}

	public List<Pattern> getIncludePropertiesList() {
		return this.includeProperties;
	}

	public void setIncludeProperties(String commaDelim) {
		this.includeProperties = JSONUtil.processIncludePatterns(JSONUtil.asSet(commaDelim), "regexp");
	}

	public void setIncludeWildcards(String commaDelim) {
		this.includeProperties = JSONUtil.processIncludePatterns(JSONUtil.asSet(commaDelim), "wildcard");
	}

	public void execute(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) actionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
		try {
			BaseAction rootObject =  (BaseAction)readRootObject(invocation);
			rootObject=doSign(rootObject);
			writeToResponse(response, createJSONString(request, rootObject), enableGzip(request));
		} catch (IOException exception) {
			LOG.error(exception.getMessage(), exception, new String[0]);
			throw exception;
		}
	}

	protected Object readRootObject(ActionInvocation invocation) {
		if (this.enableSMD) {
			return buildSMDObject(invocation);
		}
		return findRootObject(invocation);
	}

	protected Object findRootObject(ActionInvocation invocation) {
		Object rootObject;
		if (this.root != null) {
			ValueStack stack = invocation.getStack();
			rootObject = stack.findValue(this.root);
		} else {
			rootObject = invocation.getStack().peek();
		}
		return rootObject;
	}

	protected String createJSONString(HttpServletRequest request, Object rootObject) throws JSONException {
		String json = JSONUtil.serialize(rootObject, this.excludeProperties, this.includeProperties,
				this.ignoreHierarchy, this.enumAsBean, this.excludeNullProperties);
		json = addCallbackIfApplicable(request, json);
		return json;
	}

	protected boolean enableGzip(HttpServletRequest request) {
		return ((this.enableGZIP) && (JSONUtil.isGzipInRequest(request)));
	}

	protected void writeToResponse(HttpServletResponse response, String json, boolean gzip) throws IOException {
		JSONUtil.writeJSONToResponse(new SerializationParams(response, getEncoding(), isWrapWithComments(), json, false,
				gzip, this.noCache, this.statusCode, this.errorCode, this.prefix, this.contentType, this.wrapPrefix,
				this.wrapSuffix));
	}

	protected SMD buildSMDObject(ActionInvocation invocation) {
		return new SMDGenerator(findRootObject(invocation), this.excludeProperties, this.ignoreInterfaces)
				.generate(invocation);
	}

	protected String getEncoding() {
		String encoding = this.encoding;

		if (encoding == null) {
			encoding = this.defaultEncoding;
		}

		if (encoding == null) {
			encoding = System.getProperty("file.encoding");
		}

		if (encoding == null) {
			encoding = "UTF-8";
		}

		return encoding;
	}

	protected String addCallbackIfApplicable(HttpServletRequest request, String json) {
		if ((this.callbackParameter != null) && (this.callbackParameter.length() > 0)) {
			String callbackName = request.getParameter(this.callbackParameter);
			if ((callbackName != null) && (callbackName.length() > 0))
				json = callbackName + "(" + json + ")";
		}
		return json;
	}

	public String getRoot() {
		return this.root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public boolean isWrapWithComments() {
		return this.wrapWithComments;
	}

	public void setWrapWithComments(boolean wrapWithComments) {
		this.wrapWithComments = wrapWithComments;
	}

	public boolean isEnableSMD() {
		return this.enableSMD;
	}

	public void setEnableSMD(boolean enableSMD) {
		this.enableSMD = enableSMD;
	}

	public void setIgnoreHierarchy(boolean ignoreHierarchy) {
		this.ignoreHierarchy = ignoreHierarchy;
	}

	public void setIgnoreInterfaces(boolean ignoreInterfaces) {
		this.ignoreInterfaces = ignoreInterfaces;
	}

	public void setEnumAsBean(boolean enumAsBean) {
		this.enumAsBean = enumAsBean;
	}

	public boolean isEnumAsBean() {
		return this.enumAsBean;
	}

	public boolean isEnableGZIP() {
		return this.enableGZIP;
	}

	public void setEnableGZIP(boolean enableGZIP) {
		this.enableGZIP = enableGZIP;
	}

	public boolean isNoCache() {
		return this.noCache;
	}

	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}

	public boolean isIgnoreHierarchy() {
		return this.ignoreHierarchy;
	}

	public boolean isExcludeNullProperties() {
		return this.excludeNullProperties;
	}

	public void setExcludeNullProperties(boolean excludeNullProperties) {
		this.excludeNullProperties = excludeNullProperties;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setCallbackParameter(String callbackParameter) {
		this.callbackParameter = callbackParameter;
	}

	public String getCallbackParameter() {
		return this.callbackParameter;
	}

	public void setPrefix(boolean prefix) {
		this.prefix = prefix;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getWrapPrefix() {
		return this.wrapPrefix;
	}

	public void setWrapPrefix(String wrapPrefix) {
		this.wrapPrefix = wrapPrefix;
	}

	public String getWrapSuffix() {
		return this.wrapSuffix;
	}

	public void setWrapSuffix(String wrapSuffix) {
		this.wrapSuffix = wrapSuffix;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public BaseAction doSign(BaseAction obj) throws Exception{
		String msg=obj.getMsg();
		JSONObject cbMsg =JSONObject.fromObject(msg);
		String pri_key=EncryptablePropertyPlaceholderConfigurer.getContextProperty("rsa_pri_key").toString();
		String encryptString=RSAUtil.sign(MD5.getMD5(obj.getResponStr()),pri_key);
		cbMsg.getJSONObject("head").put("PACKETSIGNATURE", encryptString);
		obj.setMsg(cbMsg.toString());
		return obj;
	}
}