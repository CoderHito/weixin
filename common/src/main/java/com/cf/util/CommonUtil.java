package com.cf.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cf.entity.request.JssdkConfigRequest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	private static ObjectMapper mapper; 
	public static String DATE_FORMATE_1 = "yyyy-MM-dd HH:mm:ss";
	public static String DATE_FORMATE_2 = "yyyy-MM-dd";
	public static String DATE_FORMATE_3 = "HH:mm:ss";
	public static String DATE_FORMATE_4 = "yyyyMMddHHmmss";
	public static String DATE_FORMATE_5 = "yyyyMMdd";
	public static String DATE_FORMATE_6 = "HHmmss";
	/**
	 * @Title: getUUID 
	 * @Description: 获取不带横线的uuid
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/**
	 * @Title: getObjectMapper 
	 * @Description: 获取objectMapper对象单例
	 * @return ObjectMapper    返回类型 
	 * @throws
	 */
	public static synchronized ObjectMapper getObjectMapper(){
		if(mapper == null){
			mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat(DATE_FORMATE_1));
			// 允许单引号
			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			// 字段和值都加引号
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			// 数字也加引号
			mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
			mapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
			mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN,true);
			// 忽略未知的属性
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.getSerializerProvider().setNullValueSerializer(
					new JsonSerializer<Object>() {
						// 空值处理为空串

						@Override
						public void serialize(Object value, JsonGenerator jg,
								SerializerProvider sp) throws IOException,
								JsonProcessingException {
							jg.writeString("");
						}
					});
		}
		return mapper;
	}
	/**
	 * @Title: beanToJson 
	 * @Description: bean转为json
	 * @return String    返回类型 
	 * @throws
	 */
	public static String beanToJson(Object o){
		try {
			return getObjectMapper().writeValueAsString(o);
		} catch (JsonProcessingException e) {
			logger.error("json转换异常："+e.getMessage(),e);
			return null;
		}
	}
	/**
	 * @Title: jsonToBean 
	 * @Description: json转为bean
	 * @return T    返回类型 
	 * @throws
	 */
	public static <T> T jsonToBean(String json,T t){
		try {
			return getObjectMapper().readValue(json,new TypeReference<T>(){}); //TODO 传对象 是否有问题
		} catch (IOException e) {
			logger.error("json转换异常："+e.getMessage(),e);
			return null;
		}
	}
	/**
	 * @Title: jsonToBean 
	 * @Description: json转为bean
	 * @return T    返回类型 
	 * @throws
	 */
	public static <T> T jsonToBean(String json,Class<T> cls){
		try {
			return getObjectMapper().readValue(json,cls); 
		} catch (IOException e) {
			logger.error("json转换异常："+e.getMessage(),e);
			return null;
		}
	}
	public static void main(String args[]){
		JssdkConfigRequest req = new JssdkConfigRequest();
		req.setNoncestr("123");
		System.out.println(beanToJson(req));
		
	}
}
