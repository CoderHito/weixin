/**
 * 
 */
package com.cf.datalock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.cf.base.AbstractBaseDao;
import com.cf.base.BaseSupport;
import com.cf.util.constant.CommonConstant;
import com.cf.util.security.Util;


/**
 * @author chl_seu
 * 检查数据锁
 *
 */
public class DataLock extends AbstractBaseDao {
	public static final String LOCK_TYPE_MOD = "mod";
	public static final String LOCK_TYPE_CHECK = "check";
	public static final String LOCK_TYPE_DEL = "del";
	/**
	 * 检查锁状态
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String,Object> checkDataLocked(HashMap<String,String> map){
		Map<String,Object> retMap = new HashMap<String,Object>();
		//判断是否被锁
		List<String> list = Arrays.asList(map.get("KEY_CODE").split(","));
		List<Map<String,String>> retList = this.getJdbcTemplate().selectList("orm.common.dataLock.queryLock",list);
		List<String> codeList = new ArrayList<String>();//插入时校验keycode存在则不插入
		for(Map<String,String> m:retList){
			codeList.add(m.get("KEY_CODE"));
			if(!m.get("LOCKER").equals(BaseSupport.CframeUtil.GetCurrentUserName())){
				retMap.put("success", false);
				retMap.put("retMessage", map.get("KEY_DESC")+":"+m.get("KEY_CODE")+"正在被其他管理员操作");
				return retMap;
			}
		}
		//判断是否状态允许该操作  修改  ：审核状态为待审核01则不能   审核：审核状态不为01则不能
		List<Map<String,String>> modList = this.getJdbcTemplate().selectList(map.get("SQL_MAP"),list);
		if(map.get("LOCK_TYPE").equals(LOCK_TYPE_MOD)){
			for(Map<String,String> m:modList){
				if(m.get("CHECK_STATUS").equals(CommonConstant.CHECK_STATUS_WAIT)){
					retMap.put("success", false);
					retMap.put("retMessage", map.get("KEY_DESC")+":"+m.get( map.get("SQL_KEY"))+"处于待审核状态，不能修改");
					return retMap;
				}else if(m.get("MOD_STATUS").equals(CommonConstant.MOD_STATUS_DEL)&&m.get("CHECK_STATUS").equals(CommonConstant.CHECK_STATUS_PASS)){
					retMap.put("success", false);
					retMap.put("retMessage", map.get("KEY_DESC")+":"+m.get( map.get("SQL_KEY"))+"处于已删除状态，不能修改");
					return retMap;
				}
			}
		}else if(map.get("LOCK_TYPE").equals(LOCK_TYPE_CHECK)){
			for(Map<String,String> m:modList){
				if(!m.get("CHECK_STATUS").equals(CommonConstant.CHECK_STATUS_WAIT)){
					retMap.put("success", false);
					retMap.put("retMessage", map.get("KEY_DESC")+":"+m.get( map.get("SQL_KEY"))+"已审核完，不能审核");
					return retMap;
				}
			}
		}else{
			for(Map<String,String> m:modList){
				if(m.get("CHECK_STATUS").equals(CommonConstant.CHECK_STATUS_WAIT)){
					retMap.put("success", false);
					retMap.put("retMessage", map.get("KEY_DESC")+":"+m.get( map.get("SQL_KEY"))+"处于待审核状态，不能删除");
					return retMap;
				}else if(m.get("MOD_STATUS").equals(CommonConstant.MOD_STATUS_DEL)&&m.get("CHECK_STATUS").equals(CommonConstant.CHECK_STATUS_PASS)){
					retMap.put("success", false);
					retMap.put("retMessage", map.get("KEY_DESC")+":"+m.get( map.get("SQL_KEY"))+"处于已删除状态，不能删除");
					return retMap;
				}
			}
		}
		//插入锁记录
		List<Map<String,String>> paramsList = new ArrayList<Map<String,String>>();
		for(String keyCode:list){
			if(!codeList.contains(keyCode)){
				Map<String,String> tmp = new HashMap<String,String>();
				tmp.put("WORK_DATE", Util.getCurrentDateTimeString());
				tmp.put("KEY_CODE", keyCode);
				if(map.get("LOCK_TYPE").equals(LOCK_TYPE_MOD)){
					tmp.put("LOCK_TYPE", LOCK_TYPE_MOD);
				}else if(map.get("LOCK_TYPE").equals(LOCK_TYPE_CHECK)){
					tmp.put("LOCK_TYPE", LOCK_TYPE_CHECK);
				}else{
					tmp.put("LOCK_TYPE", LOCK_TYPE_DEL);
				}
				tmp.put("LOCKER", BaseSupport.CframeUtil.GetCurrentUserName());
				tmp.put("LOCK_TIME", Util.getCurrentDateTimeString());
				paramsList.add(tmp);
			}
		}
		if(paramsList.size()>0){
			this.getJdbcTemplate().insert("orm.common.dataLock.lockData",paramsList);
		}
		retMap.put("success", true);
		return retMap;
	}
	
	
	/**
	 * 解锁
	 * true -解锁成功
	 * false -解锁失败
	 */
	public boolean unLockData(HashMap<String,String> map){
		Map<String,Object> params = new HashMap<>();
		if(map.get("KEY_CODE")!=null){
			List<String> list = Arrays.asList(map.get("KEY_CODE").split(","));
			params.put("KEY_CODES", list);
		}
		int ret = (int)this.getJdbcTemplate().delete("orm.common.dataLock.unLockData",params);
		if(ret<0){
			return false;
		}else{
			return true;
		}
	}
	
}
