package com.cf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cf.util.encryption.SHAUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName: WeixinUtil
 * @Description: 微信工具类
 * @author sven
 * @date 2016-9-22 上午11:01:28
 *
 */
public class WeixinUtil {
	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	private static Gson gson = new Gson();

	private static Properties PROPERTIES = new Properties();
	private static String WX_PROPERTIES_FILE = "weixin.properties";// 微信配置属性文件
	public static String WX_KEY_TOKEN = "api.weixin.token";// token
	public static String WX_KEY_ENCODINGAESKEY = "api.weixin.encodingAESKey";// EncodingAESKey
	public static String WX_KEY_APPID = "api.weixin.appID";// appid
	public static String WX_KEY_APPSECRET = "api.weixin.appsecret";// appsecret
	public static String WX_KEY_ACCESS_TOKEN_URL = "api.weixin.access_token_url";// 获取access_token_url
																					// api
	public static String WX_KEY_CREATE_MENU = "api.weixin.create.menu";// 生成菜单
																		// api
	public static String WX_KEY_DELETE_MENU = "api.weixin.delete.menu";// 删除菜单
																		// api
	public static String WX_KEY_QUERY_USER_LIST = "api.weixin.query.user";// 获取用户列表
																			// api
	public static String WX_KEY_QUERY_USER_INFO = "api.weixin.queryInfo.user";// 获取用户基本信息
																				// api
	public static String WX_KEY_BATCH_QUERY_USER_INFO = "api.weixin.queryBatchInfo.user";// 批量获取用户的基本信息
																							// api
	public static String WX_KEY_QUERY_IP_LIST = "api.weixin.queryList.ip";// 获取微信服务器IP地址列表
																			// api
	public static String WX_KEY_SET_INDUSTRY = "api.weixin.industry.set";// 设置所属行业
																			// api
	public static String WX_KEY_GET_TEMPLATE = "api.weixin.template.add";// 获得模板
																			// api
	public static String WX_KEY_SEND_TEMPLATE = "api.weixin.template.send";// 发送模板消息
																			// api
	public static String WX_KEY_TICKET_JSSDK = "api.weixin.ticket.jssdk";// 调用微信JS接口的临时票据
																			// api
	public static String WX_KEY_SEND_ALL = "api.weixin.mass.sendall";// 群发消息 api

	static {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(WX_PROPERTIES_FILE);
		try {
			PROPERTIES.load(is);
		} catch (IOException e) {
			logger.error("加载微信配置属性文件失败：" + e.getMessage(), e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 获取微信配置
	 * 
	 * @param key
	 * @return
	 */
	public static String getWxProperty(String key) {
		return PROPERTIES.getProperty(key);
	}

	/**
	 * @Title: getAccessTokenFromWxServer @Description:
	 * 从微信服务器获取token_access @return String 返回类型 @throws
	 */
	public static String getAccessTokenFromWxServer() {
		// 应用ID
		String appId = WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_APPID);
		// 应用密钥
		String appsecret = WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_APPSECRET);
		// 获取access_token-URL
		String access_token_url = WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_ACCESS_TOKEN_URL);
		access_token_url = access_token_url.replace("APPID", appId);
		access_token_url = access_token_url.replace("APPSECRET", appsecret);
		String access_token_str = HttpUtil.sendHttpsRequest(access_token_url, "GET", null);
		// Map<String,String> map = gson.fromJson(access_token_str, new
		// TypeToken<Map<String,String>>(){}.getType());
		Map<String, String> map = CommonUtil.jsonToBean(access_token_str, new HashMap<String, String>());
		if (map.containsKey("access_token")) {
			return map.get("access_token");
		} else {
			return map.get("errcode");
		}
	}

	/**
	 * @Title: getAccessTokenFromCache @Description:
	 * 从全局缓存中获取access_token @return String 返回类型 @throws
	 */
	public static String getAccessTokenFromCache() {
		return CacheUtil.getApplicationValue(CacheUtil.GLOBAL_ACCESS_TOKEN).toString();
	}

	/**
	 * @Title: getJsSdkTicketFromWxServer @Description:
	 * 从微信服务器获取jsSdk_Ticket @return String 返回类型 @throws
	 */
	public static String getJsSdkTicketFromWxServer(String access_token) {
		// 获取ticket_jsapi_url-URL
		String ticket_jsapi_url = WeixinUtil.getWxProperty(WeixinUtil.WX_KEY_TICKET_JSSDK);
		ticket_jsapi_url = ticket_jsapi_url.replace("ACCESS_TOKEN", access_token);
		String ticket_jsapi_str = HttpUtil.sendHttpsRequest(ticket_jsapi_url, "GET", null);
		// Map<String,String> map = gson.fromJson(ticket_jsapi_str, new
		// TypeToken<Map<String,String>>(){}.getType());
		Map<String, String> map = CommonUtil.jsonToBean(ticket_jsapi_str, new HashMap<String, String>());
		if (map.containsKey("ticket")) {
			return map.get("ticket");
		} else {
			return map.get("errcode");
		}
	}

	/**
	 * @Title: getJsSdkTicketFromCache @Description: 从缓存中获取jssdk_ticket @return
	 * String 返回类型 @throws
	 */
	public static String getJsSdkTicketFromCache() {
		return CacheUtil.getApplicationValue(CacheUtil.GLOBAL_JSSDK_TICKET).toString();
	}

	/**
	 * @Title: getTimer @Description: 90分钟间隔刷新缓存中
	 * access_token、jssdk_ticket @return void 返回类型 @throws
	 */
	public static void getTimer() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				long starttime = Long.valueOf(CacheUtil.getApplicationValue(CacheUtil.GLOBAL_START_TIME).toString());
				long endtime = System.currentTimeMillis();
				long time = 1000 * 60 * 90;
				if ((endtime - starttime) > time) {
					String access_token = getAccessTokenFromWxServer();
					String ticket_jsapi_str = getJsSdkTicketFromWxServer(access_token);
					CacheUtil.setApplicationValue(CacheUtil.GLOBAL_ACCESS_TOKEN, access_token);// 刷新token
					CacheUtil.setApplicationValue(CacheUtil.GLOBAL_JSSDK_TICKET, ticket_jsapi_str);// 刷新ticket_jsapi_str
					CacheUtil.setApplicationValue(CacheUtil.GLOBAL_START_TIME, System.currentTimeMillis());// 刷新最后时间
				}
			}
		};
		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 1000 * 60 * 30;
		timer.scheduleAtFixedRate(task, delay, intevalPeriod);
	}

	/**
	 * 生成微信连接签名
	 * 
	 * @param token
	 *            公众号token
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机字符窜
	 * @return
	 */
	public static String createConnectionSignature(String token, String timestamp, String nonce) {
		String[] str = new String[] { token, timestamp, nonce };
		Arrays.sort(str);
		StringBuffer sb = new StringBuffer(0);
		for (String s : str) {
			sb.append(s);
		}
		return SHAUtil.encryptSHA1(sb.toString());
	}

	/**
	 * 生成jssdk 使用权限签名 签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket,
	 * timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。对所有待签名参数按照字段名的ASCII
	 * 码从小到大排序（字典序）后，
	 * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。这里需要注意的是所有参数名均为小写字符。
	 * 对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
	 * 
	 * @param jsapi_ticket
	 *            微信服务器返回的jssdk票据
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机字符窜
	 * @param url
	 * @return
	 */
	public static String createJsSdkSignature(String jsapi_ticket, long timestamp, String noncestr, String url) {
		if (url.indexOf("#") > -1) {
			url = url.substring(0, url.indexOf("#"));
		}
		StringBuffer sb = new StringBuffer(0);
		sb.append("jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url);
		return SHAUtil.encryptSHA1(sb.toString());
	}

	/**
	 * 创建菜单，POST方式提交
	 */
	public static void createMenu() {
		// wLygzIUhheMFMnJAQB-SawlfMJeLwBKXlOEvzRxBCblIwovFRNcTQdglnTTdzJnPP7WrwTrZcGnJBNNRzU6aGMSieXhpOd7Eq2_fi7ZQRLSZFL6n1yAsJHQ9RFuGy9WaYUEfAJANJT
		// 获取access_token
		String access_token = getAccessTokenFromWxServer();
		// 获取菜单URL
		String create_url_url = PropertyUtils.getProperty("api.weixin.create.menu");
		create_url_url = create_url_url.replace("ACCESS_TOKEN", access_token);
		logger.info(">>>创建菜单URL：" + create_url_url);
		// 获取菜单json串
		String menu_str = MenuUtil.createMenu();
		logger.info(">>>创建菜单参数字符串：" + menu_str);
		String responseStr = HttpUtil.sendHttpsRequest(create_url_url, "POST", menu_str);
		logger.info(">>>创建菜单返回字符串：" + responseStr);
		Map<String, String> map = gson.fromJson(responseStr, new TypeToken<Map<String, String>>() {
		}.getType());
		if (map.containsKey("errcode") && "0".equals(map.get("errcode"))) {
			logger.info("创建菜单成功");
		} else {
			logger.info("创建菜单失败，失败原因：" + map.get("errcode"));
		}
	}

	public static void main(String[] args) {
		// createMenu();
	}
}
