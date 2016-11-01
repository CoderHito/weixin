package com.cf.util.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.cf.base.BaseSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 基本方法工具类实现类
 * 
 * @author shadow
 * @email 124010356@qq.com
 * @create 2012.04.28
 */
public class CommonUtilImpl implements CommonUtil {
	private final static Logger logger = Logger.getLogger(CommonUtilImpl.class);

	public String getClientIP() {
		return overshot(BaseSupport.ContextUtil.getRequest());
	}

	public String getClientIP(HttpServletRequest request) {
		return overshot(request);
	}

	/**
	 * 获取客户IP地址
	 * 
	 * @param request
	 * @return String
	 */
	private String overshot(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					logger.debug(e.getMessage(), e);
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
			// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	public List<Serializable> toArray(String string) {
		return handleString2Array(string, ",");
	}

	public List<Serializable> toArray(String string, String regex) {
		return handleString2Array(string, regex);
	}

	private List<Serializable> handleString2Array(String array, String regex) {
		List<Serializable> list = new ArrayList<Serializable>();
		Serializable[] strings = StringUtils.split(array, regex);
		for (Serializable string : strings)
			list.add(string);
		return list;
	}

	public Map<String, Object> toMap(Object[] keys, Object[] values) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == keys || null == values || keys.length <= 0 || values.length <= 0 || keys.length != values.length)
			return map;
		for (int i = 0, len = values.length; i < len; i++)
			map.put(keys[i].toString(), values[i]);
		return map;
	}

	public Map<String, Object> toMap(Map<String, Object> map, Object[] keys, Object[] values) {
		if (null == keys || null == values || keys.length <= 0 || values.length <= 0 || keys.length != values.length)
			return map;
		for (int i = 0, len = values.length; i < len; i++)
			map.put(keys[i].toString(), values[i]);
		return map;
	}

	public Map<String, Object> toMap(Map<String, Object> map, Object key, Object value) {
		if (null == key || null == value || "".equals(key))
			return map;
		map.put(key.toString(), value);
		return map;
	}

	public Map<String, Object> toMap(Object key, Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null == key || null == value || "".equals(key))
			return map;
		map.put(key.toString(), value);
		return map;
	}

	public String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public String toEncoding(String string, String oldEncode, String newEncode) {
		if (null == oldEncode || "".equals(oldEncode))
			oldEncode = "ISO8859_1";
		try {
			return new String(string.getBytes(oldEncode), newEncode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getRandomUUID(int length) {
		String strUUID = UUID.randomUUID().toString().replaceAll("-", "");
		while (strUUID.length() < length) {
			strUUID += UUID.randomUUID().toString().replaceAll("-", "");
		}

		return strUUID.substring(0, length);
	}

	/**
	 * 函数功能说明:从一个JSON数组得到一个java对象集合
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<?> getObjectList(String jsonString, Class<?> clazz) {
		JSONArray array = JSONArray.fromObject(jsonString);
		List list = new ArrayList();
		for (Iterator iterator = array.iterator(); iterator.hasNext();) {
			JSONObject jsonObject = (JSONObject) iterator.next();
			list.add(JSONObject.toBean(jsonObject, clazz));
		}
		return list;
	}

	/**
	 * 方法名称:transStringToMap 传入参数:mapString 形如 username'chenziwen^password'1234
	 * 返回值:Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map transStringToMap(String mapString) {
		Map map = new HashMap();
		java.util.StringTokenizer items;
		for (StringTokenizer entrys = new StringTokenizer(mapString, ","); entrys.hasMoreTokens(); map
				.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
			items = new StringTokenizer(entrys.nextToken(), ":");
		return map;
	}

	/**
	 * 解压缩
	 * 
	 * @param sZipPathFile
	 *            要解压的文件
	 * @param sDestPath
	 *            解压到某文件夹
	 * @return
	 */
	public ArrayList<Map<String, String>> zipEctract(String sZipPathFile, String sDestPath) {
		ArrayList<Map<String, String>> allFileName = new ArrayList<>();
		try {
			// 先指定压缩档的位置和档名，建立FileInputStream对象
			FileInputStream fins = new FileInputStream(sZipPathFile);
			// 将fins传入ZipInputStream中
			ZipInputStream zins = new ZipInputStream(fins);
			ZipEntry ze = null;
			byte[] ch = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {
				File zfile = new File(sDestPath + ze.getName());
				File fpath = new File(zfile.getParentFile().getPath());
				if (ze.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
					zins.closeEntry();
				} else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					Map<String, String> nameMap = new HashMap<>();
					nameMap.put("UZ_NAME", zfile.getName());
					nameMap.put("UZ_PARENT", zfile.getParent());
					allFileName.add(nameMap);
					while ((i = zins.read(ch)) != -1)
						fouts.write(ch, 0, i);
					zins.closeEntry();
					fouts.close();
				}
			}
			fins.close();
			zins.close();
		} catch (Exception e) {
			System.err.println("Extract error:" + e.getMessage());
			logger.debug(e.getMessage(), e);
		}
		return allFileName;
	}

	/**
	 * 获取properties
	 * 
	 * @param path
	 * @return
	 */
	public Properties getProperties(String path) {
		Properties props = new Properties();
		InputStream in;
		try {
			in = getClass().getResourceAsStream(path);
			props.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage(), e);
		}
		return props;
	}

	/**
	 * 打包zip
	 * 
	 * @param args
	 * @throws Exception
	 */
	public boolean zipFiles(String[] fileList, String strZipName){
		byte[] buffer = new byte[1024];
		// 生成的ZIP文件名为strZipName
		ZipOutputStream out;
		FileInputStream fis;
		try {
			out = new ZipOutputStream(new FileOutputStream(strZipName));
			for (int i = 0; i < fileList.length; i++) {
				File file = new File(fileList[i]);
				if (!file.exists()) {
					continue;
				}
				fis = new FileInputStream(file);
				out.putNextEntry(new ZipEntry(file.getName()));
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage(),e);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage(),e);
			return false;
		}
		return true;
	}
	
	public String getNowTimeMileSecond() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	public String checkNull(String str) {
		if (null == str) {
			return "";
		}
		return str;
	}
}
