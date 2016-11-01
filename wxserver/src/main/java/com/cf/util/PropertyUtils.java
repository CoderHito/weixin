package com.cf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * 属性文件获取
 * 
 * @author Mall
 * @date 2016年1月19日 上午10:37:08
 */
public class PropertyUtils {

	private static final Logger logger = Logger.getLogger(PropertyUtils.class);

	private static Properties properties = new Properties();

	//加载系统属性文件
	static {
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("remote-server.properties");
		try {
			properties.load(input);
			input.close();
			input = null;
		} catch (IOException e) {
			logger.error("加载属性文件失败"+ e);
		} 
	}

	//通过键获取属性文件
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	@Test
	public void testDemo(){
		System.out.println(getProperty("api.weixin.token"));
	}

}
