package com.cf.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.cf.util.constant.TransactionCodeConstants;
import com.cf.util.security.MD5;
import com.cf.util.security.RSAUtil;
import com.cf.util.string.FastJsonUtil;
import com.wxbatis.jdbc.EncryptablePropertyPlaceholderConfigurer;

import net.sf.json.JSONObject;

public class HttpUtil {
	protected static Logger LOG = Logger.getLogger(HttpClient.class);
	private final static int SOCKET_CONNECT_TIMEOUT = 20000;

	
	
	public static String httpGet(String url) {
		org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpget = new HttpGet(url);
			httpclient.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			LOG.debug("executing request " + httpget.getURI());
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String res = httpclient.execute(httpget, responseHandler);
			LOG.debug("response str:" + res);
			return res;
		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error("execut-" + url + " error:" + e.getMessage());
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static String post(String url, String requeststr,
			String contenttype, String charSet) {

		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		HttpURLConnection httpURLConnection = null;
		String rtstr = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			httpURLConnection.setReadTimeout(SOCKET_CONNECT_TIMEOUT);// 最长10秒的延迟
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			// httpURLConnection.setRequestProperty("Content-Length",String.valueOf(requeststr.length()));
			httpURLConnection.setRequestProperty("Content-Type", contenttype);
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("POST");
			LOG.debug("请求：" + requeststr + "," + url);
			// 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(new OutputStreamWriter(
					httpURLConnection.getOutputStream(), charSet));
			// 发送请求参数
			printWriter.write(requeststr);
			// flush输出流的缓冲
			printWriter.flush();

			// 根据ResponseCode判断连接是否成功
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != 200) {

			} else {

			}
			// 定义BufferedReader输入流来读取URL的ResponseData
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream(), charSet));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				responseResult.append(line);
			}
			rtstr = responseResult.toString();

			LOG.debug("响应：" + rtstr);
		} catch (Exception e) {
			LOG.error("POST请求异常", e);
		} finally {
			httpURLConnection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return rtstr;
	}

	public static String post(String url, String requeststr, String charSet) {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		HttpURLConnection httpURLConnection = null;
		String rtstr = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			httpURLConnection.setReadTimeout(SOCKET_CONNECT_TIMEOUT);// 最长10秒的延迟
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			// httpURLConnection.setRequestProperty("Content-Length",String.valueOf(requeststr.length()));
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("POST");
			LOG.debug("请求：" + requeststr + "," + url);
			// 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(new OutputStreamWriter(
					httpURLConnection.getOutputStream(), charSet));
			// 发送请求参数
			printWriter.write(requeststr);
			// flush输出流的缓冲
			printWriter.flush();

			// OutputStream out = httpURLConnection.getOutputStream();
			//
			// out.write(requeststr.getBytes("UTF-8"));
			// out.flush();
			// out.close();

			// 根据ResponseCode判断连接是否成功
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != 200) {

			} else {

			}
			// 定义BufferedReader输入流来读取URL的ResponseData
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream(), charSet));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				responseResult.append(line);
			}
			rtstr = responseResult.toString();

			LOG.debug("响应：" + rtstr);
		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error("POST请求异常", e);
		} finally {
			httpURLConnection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return rtstr;
	}

	public static String post(String url, Map<String, String> params,
			String charSet) {
		String rtstr = "";
		try {
			org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			MultipartEntity reqentity = new MultipartEntity();
			// ContentBody cb=new ContentBody("");
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Map.Entry<String, String>> eset = params.entrySet();
			for (Map.Entry<String, String> e : eset) {
				nvps.add(new BasicNameValuePair(e.getKey(), e.getValue()));
			}

			post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String response = httpclient.execute(post, responseHandler);
			System.out.println("---" + response);
			// if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
			// HttpEntity entity = response.getEntity();
			// System.out.println(EntityUtils.toString(entity));
			// //显示内容
			// if (entity != null) {
			// System.out.println(EntityUtils.toString(entity));
			// }
			// if (entity != null) {
			// entity.consumeContent();
			// }
			// }
		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error("POST请求异常", e);
		} finally {
		}
		return rtstr;
	}
	@SuppressWarnings("unchecked")
	public static <T> T postMsg(String url, Map<String,Object> msg,Object o,T t,String charSet) {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		HttpURLConnection httpURLConnection = null;
		String rtstr = "";
		Map<String,Object> map=new HashMap<>();
		try {
		JSONObject obj = JSONObject.fromObject(msg);
		//加密
		o=FastJsonUtil.parseObject(obj.toString(), o.getClass());
		String checkString=EncryptablePropertyPlaceholderConfigurer.getContextProperty("rsa_check_string").toString();
    	RSAPublicKey publicKey=RSAUtil.loadPublicKeyByStr(checkString);
		String encryptString=RSAUtil.encrypt(publicKey, MD5.getMD5(o.toString()).getBytes("utf-8"));
		obj.getJSONObject("head").put("PACKETSIGNATURE",encryptString);
		
		String requeststr=TransactionCodeConstants.MSG_NAME+"="+obj.toString();
		
		
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			httpURLConnection.setReadTimeout(SOCKET_CONNECT_TIMEOUT);// 最长10秒的延迟
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			// httpURLConnection.setRequestProperty("Content-Length",String.valueOf(requeststr.length()));
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("POST");
			LOG.debug("请求：" + requeststr + "," + url);
			// 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(new OutputStreamWriter(
					httpURLConnection.getOutputStream(), charSet));
			// 发送请求参数
			printWriter.write(requeststr);
			// flush输出流的缓冲
			printWriter.flush();

			// OutputStream out = httpURLConnection.getOutputStream();
			//
			// out.write(requeststr.getBytes("UTF-8"));
			// out.flush();
			// out.close();

			// 根据ResponseCode判断连接是否成功
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != 200) {

			} else {

			}
			// 定义BufferedReader输入流来读取URL的ResponseData
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream(), charSet));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				responseResult.append(line);
			}
			rtstr = responseResult.toString();
			JSONObject jsonMsg = JSONObject.fromObject(rtstr);
			JSONObject msgBody= JSONObject.fromObject(jsonMsg.getString(TransactionCodeConstants.MSG_NAME));
			rtstr=msgBody.toString();
/*			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("head", Map.class);
			classMap.put("body", Map.class);
			classMap.put("list", List.class);
			map=(Map<String,Object>)JSONObject.toBean(msgBody, Map.class,classMap);*/
			LOG.debug("响应：" + rtstr);
		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error("POST请求异常", e);
			Map<String,String> head=new HashMap<>();
			head.put("ERRCODE", "01");
			head.put("ERRMSG", "POST请求异常");
			map.put("head", head);
			rtstr=FastJsonUtil.toJSONString(map);
		} finally {
			httpURLConnection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				LOG.error(ex.getMessage(), ex);
			}
		}
		return (T)FastJsonUtil.parseObject(rtstr,t.getClass());
	}
	public static <T> Map<String,Object> postMsg(String url, Map<String,Object> msg,String charSet) {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		StringBuffer responseResult = new StringBuffer();
		HttpURLConnection httpURLConnection = null;
		String rtstr = "";
		Map<String,Object> map=new HashMap<>();
		try {
		JSONObject obj = JSONObject.fromObject(msg);
		String requeststr=TransactionCodeConstants.MSG_NAME+"="+obj.toString();
		
		
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			httpURLConnection.setReadTimeout(SOCKET_CONNECT_TIMEOUT);// 最长10秒的延迟
			// 设置通用的请求属性
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			// httpURLConnection.setRequestProperty("Content-Length",String.valueOf(requeststr.length()));
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("POST");
			LOG.debug("请求：" + requeststr + "," + url);
			// 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(new OutputStreamWriter(
					httpURLConnection.getOutputStream(), charSet));
			// 发送请求参数
			printWriter.write(requeststr);
			// flush输出流的缓冲
			printWriter.flush();

			// OutputStream out = httpURLConnection.getOutputStream();
			//
			// out.write(requeststr.getBytes("UTF-8"));
			// out.flush();
			// out.close();

			// 根据ResponseCode判断连接是否成功
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode != 200) {

			} else {

			}
			// 定义BufferedReader输入流来读取URL的ResponseData
			bufferedReader = new BufferedReader(new InputStreamReader(
					httpURLConnection.getInputStream(), charSet));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				responseResult.append(line);
			}
			rtstr = responseResult.toString();
			JSONObject jsonMsg = JSONObject.fromObject(rtstr);
			JSONObject msgBody= JSONObject.fromObject(jsonMsg.getString(TransactionCodeConstants.MSG_NAME));
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("head", Map.class);
			map=(Map<String,Object>)JSONObject.toBean(msgBody, Map.class,classMap);
			LOG.debug("响应：" + rtstr);
		} catch (Exception e) {
			// e.printStackTrace();
			LOG.error("POST请求异常", e);
			Map<String,String> head=new HashMap<>();
			head.put("ERRCODE", "01");
			head.put("ERRMSG", "POST请求异常");
			map.put("head", head);
			return map;
		} finally {
			httpURLConnection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				LOG.error(ex.getMessage(), ex);
			}
		}
		return map;
	}
	static {
		// 不采用证书认证

		// org.apache.commons.c.client.Protocol https = new Protocol("https",
		// new HTTPSSecureProtocolSocketFactory(), TPF_HTTPS_PORT);
		// org.apache.commons.client.protocol.Protocol.registerProtocol("https",
		// https);
	}

	public static void main(String args[]) {
		System.out
				.println(post(
						"http://localhost:8080/ruift_bank/servlet/BankServlet",
						"<?xml version=\"1.0\" encoding=\"utf-8\"?><rftReq><transID>20120307070338100398</transID><appId>1000000900</appId><apiName>ZFM_coll</apiName><merAcct>lxf627@sina.com</merAcct><reqTime>20120307194715</reqTime><content><![CDATA[ZXc5aXZXvVXF9eYoqNTTUMSZRfEgXgNJSLwmG/k/4DaxnxXk5QmpvqssehtopXDWxdxKynhaOnu7IaluB8ze1mIvh3kMKm0BYbs0EURd12NpvODb7ElI6cWdWG3UstjEe0vjpfjJCR3SLXoE7dinucN/kvJbkHBEQ5j4oiI55lfB9tPVD9Z6j++siS1gM12bjPVehT+cDfqimqY5YcLEpWyRe3fA4ida+oNhvCjI2I3XMSYDA05IvijoFwy6mgVSgf6Q2/Wv3Jp2NVANPUUgeeKRrQ3n2GK/deC/Q/HKNgEWg076wl3A3CX7kUzvgWbaVUEgY6l74VqeEkxMrHNLCTCoLOvalSrs1VQcueyf41y1eynbpRctd0npSN+JWD9izC6lXrcx1EPgxM2PjgGldWjiBrPyAFuq0m33YNCk8LUJrRAArcl/JMYaFjNrDx2rGRiSMlZIF7rLdxrMiUwmyssxDv6p0nKM/olZ1BnRDGQ+TlbCW1di0euTqbNrjzHQDoQHvVM0yY/+aXwFzPVMULqsT4LodEGZtT/Lq51AyaWKMxxovhBbMQ==]]></content><sign>908144f4022abb17a91dcf19720fb69a</sign></rftReq>",
						"UTF-8"));
		// System.out.println(httpGet("http://192.168.10.109:8080/app/appstore/receiveNotify.html?return_code=0000&message=&notify_time=20111227031150&trade_id=1112271511000002&out_trade_id=20111227150744008463&mer_var=appsign=MC0CFQCDd2CvSbv7Zs/rJkSIhje5YjUbsAIURTo+bPKGj9UifxFIf9BnqqOABnc="));
	}

	public String postHttps(String url, Map<String, String> params)
			throws IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope("ebspay.boc.cn", 443),
				new UsernamePasswordCredentials(""));

		HttpPost httpost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> eset = params.entrySet();
		for (Map.Entry<String, String> e : eset) {
			System.out.println(e.getKey());
			nvps.add(new BasicNameValuePair(e.getKey(), e.getValue()));
		}

		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String res = httpclient.execute(httpost, responseHandler);
		System.out.println("res:" + res);
		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();

		return res;
	}
}
