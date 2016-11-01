package com.cf.base;

import com.cf.datalock.DataLock;
import com.cf.util.common.ICframeUtil;
import com.cf.util.common.impl.CframeUtilImpl;
import com.cf.util.constant.SysParamsMap;
import com.cf.util.context.CommonUtil;
import com.cf.util.context.CommonUtilImpl;
import com.cf.util.context.ContextUtil;
import com.cf.util.context.ContextUtilImpl;
import com.cf.util.security.IEncoder;
import com.cf.util.security.ISecurityUtil;
import com.cf.util.security.MD5Encoder;
import com.cf.util.security.SecurityUtilImpl;
import com.cf.util.spring.SpringContextUtils;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 工具集合支持类
 * 
 * @author chl_seu
 * @create 2013.04.03
 */
public abstract class BaseSupport {

	/**
	 * 数据库操作模板类
	 */
	public static MyBatisSessionTemplate myBatisSessionTemplate = (MyBatisSessionTemplate) SpringContextUtils
			.getBean("myBatisSessionTemplate");
	/**
	 * CFrame全局变量操作类
	 */
	public static ICframeUtil CframeUtil = (ICframeUtil) SpringContextUtils.getBean("CframeUtil");

//	public static ICframeUtil CframeUtil = new CframeUtilImpl();

	/**
	 * 业务权限控制类
	 */
	// public static IBizAuth BizAuth =
	// (IBizAuth)SpringContextUtils.getBean("BizAuth");

	/**
	 * 步骤控制类
	 */
	// public static IStepControl StepControl =
	// (IStepControl)SpringContextUtils.getBean("stepControl");

	/**
	 * CONTEXT工具类
	 */
	public static ContextUtil ContextUtil = new ContextUtilImpl();

	/**
	 * 一般工具类
	 */
	public static CommonUtil CommonUtil = new CommonUtilImpl();

	/**
	 * spring工具类
	 */
	// public static SpringUtil SpringUtil = new SpringUtilImpl();

	/**
	 * SECURITY工具类
	 */
	public static ISecurityUtil SecurityUtil = new SecurityUtilImpl();

	// /**
	// * 上传工具类
	// */
	// public static UploadUtil UploadUtil = new UploadUtilImpl();
	//
	// /**
	// * 文件工具类
	// */
	// public static FileUtil FileUtil = new FileUtilImpl();

	/**
	 * 加密工具类(默认使用SECURITY的MD5加密)
	 */
	public static IEncoder Encoder = new MD5Encoder();

	// /**
	// * 校验工具类
	// */
	// public static Validator Validator = new ValidatorImpl();
	//
	// /**
	// * 敏感字符匹配工具
	// */
	// public static AcUtil AcUtil = new AcUtilImpl();
	//
	// /**
	// * 拼音转换工具
	// */
	// public static PinYinUtil PinYinUtil = new PinYinUtilImpl();
	public static SysParamsMap SysParamsMap = new SysParamsMap();
	/**
	 * 数据锁定
	 */
	public static DataLock DataLock = (DataLock) SpringContextUtils.getBean("dataLock");
}
