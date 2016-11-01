package com.cf.biz.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

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

@SuppressWarnings({"deprecation","resource","unused"})
public class HttpUtil {
	protected static Logger LOG = Logger.getLogger(HttpClient.class);
	private final static int SOCKET_CONNECT_TIMEOUT = 20000;

	/**
	 * 基于Https安全协议的URL调用
	 * @param url 访问路径
	 * @param method 访问方式
	 * @param requestStr 参数，get为空
	 * @return
	 */
	public static String sendHttpsRequest(String requestUrl, String requestMethod, String requestParam){
		LOG.info(">>>请求的URL：" + requestUrl);
		//创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = {new MyX509TrustManager()};
		//创建安全套接字的上下文
		SSLContext sslContext;
		//获取SSL示例
		try {
			sslContext = SSLContext.getInstance("SSL","SunJSSE");
			//初始化
			sslContext.init(null, tm, new SecureRandom());
			//从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			//建立连接
			URL url = new URL(requestUrl);
			//打开连接
			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			//设置安全套接字工厂
			conn.setSSLSocketFactory(ssf);
			//设置是否允许输入与输出
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			//设置请求方式
			conn.setRequestMethod(requestMethod);
			//设置超时时间
			conn.setConnectTimeout(5000);
			//向输出流中输出参数
			if( null != requestParam){
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(requestParam.getBytes("UTF-8"));
				outputStream.close();
			}
			
			//从输入流中获取返回的内容
			InputStream inputStream = conn.getInputStream();
			//将字节流转换为字符流
			InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
			//带缓冲的字符流
			BufferedReader bufferedReader = new BufferedReader(reader);
			//开始读取数据
			String str = null;
			StringBuffer buffer = new StringBuffer();
			//循环读取一行数据
			while( (str = bufferedReader.readLine()) != null){
				buffer.append(str);
			}
			//关闭流
			bufferedReader.close();
			reader.close();
			inputStream.close();
			inputStream = null;
			
			//断开连接
			conn.disconnect();
			LOG.info(">>>返回的结果："+buffer.toString());
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			LOG.error("发送Https请求失败："+ e);
		} catch (NoSuchProviderException e) {
			LOG.error("发送Https请求失败："+ e);
		} catch (KeyManagementException e) {
			LOG.error("发送Https请求失败："+ e);
		} catch (MalformedURLException e) {
			LOG.error("发送Https请求失败："+ e);
		} catch (IOException e) {
			LOG.error("发送Https请求失败："+ e);
		}
		return "";
	}
	
	/**
	 * 基于http协议的get方式访问
	 */
	public static String httpGet(String url) {
		org.apache.http.client.HttpClient httpclient = new DefaultHttpClient(); 
		try {
			HttpGet httpget = new HttpGet(url);
			httpclient.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			LOG.debug(">>>request str:" + httpget.getURI());
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String res = httpclient.execute(httpget, responseHandler);
			LOG.debug(">>>response str:" + res);
			return res;
		} catch (Exception e) {
			LOG.error("execut-" + url + " error:" + e.getMessage());
			return "";
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 基于http协议的POST方式访问
	 */
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

	/**
	 * 基于Http协议的post方式访问
	 */
	public static String httpPost(String url, String requeststr, String charSet) {

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

			OutputStream out = httpURLConnection.getOutputStream();
			out.write(requeststr.getBytes("UTF-8"));
			out.flush();
			out.close();

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

	
	
	
}

