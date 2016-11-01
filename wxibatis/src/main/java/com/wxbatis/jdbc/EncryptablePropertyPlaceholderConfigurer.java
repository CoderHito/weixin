package com.wxbatis.jdbc;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 解析properties文件 并对系统进行配置
 * 
 * @author 
 * 
 */
public class EncryptablePropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	private static final String key = "0002000200020002";
	private static Map<String, Object> ctxPropertiesMap;
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		
		try {

             String url = props.getProperty("jdbc.url").trim();  
             if (url != null) { 
            	 System.out.println(Des.Decrypt(url, Des.hex2byte(key)));
                 props.setProperty("jdbc.url", Des.Decrypt(url, Des.hex2byte(key))); 
                 
             }  
            
             String driverClassName = props.getProperty("jdbc.driverClassName").trim();  
             if(driverClassName != null){  
                 props.setProperty("jdbc.driverClassName", driverClassName);  
                 
             }  
             String username = props.getProperty("jdbc.username").trim();  
             if (username != null) {  
                 props.setProperty("jdbc.username", Des.Decrypt(username, Des.hex2byte(key)));  
                 
             }  
               
             String password = props.getProperty("jdbc.password").trim();  
             if (password != null) {  
                 props.setProperty("jdbc.password", Des.Decrypt(password, Des.hex2byte(key))); 
                
             }  
			
			super.processProperties(beanFactory, props);
			ctxPropertiesMap = new HashMap<String, Object>();
			for (Object key : props.keySet()) {
				String keyStr = key.toString();
				String value = props.getProperty(keyStr);
				ctxPropertiesMap.put(keyStr, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeanInitializationException(e.getMessage());
		}
	}
	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}
}