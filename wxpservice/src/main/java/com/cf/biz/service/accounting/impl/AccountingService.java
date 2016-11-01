/**
 * 
 */
package com.cf.biz.service.accounting.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cf.biz.service.accounting.IAccountingService;
import com.wxbatis.impl.template.MyBatisSessionTemplate;



/**
 * @author chl_seu
 * 0001-充值
 * 0002-退款
 * 0003-POS消费
 * 0004-POS消费撤销
 * 0005-线上消费
 * 0006-线上消费撤销
 * 0007-管理费
 * 0008-管理费退费
 * 0009-手续费
 * 0010-手续费退费
 * 0011-卡注销
 *
 */
@Service("accountingService")
public class AccountingService implements IAccountingService {
	protected final static Logger logger = Logger.getLogger(AccountingService.class);
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	
	/* (non-Javadoc)
	 * @see com.cf.biz.service.accounting.IAccountingService#AccountingMain(java.util.HashMap)
	 * 记账接口传HashMap 字段TRANSTYPE（交易类型），ACCNO（客户账户，不涉及客户帐可不填），AMT（金额） 
	 */
	@Override
	public boolean AccountingMain(HashMap<String, String> param) {
		String sTransType = param.get("TRANSTYPE");
		try{
			List<Map<String,String>> accSettingList = myBatisSessionTemplate.selectList("orm.accounting.queryAccSetting",param);
			if((accSettingList==null)||(accSettingList.size()<=0)){
				logger.error("记账接口不存在，sTransType："+sTransType);
				return false;
			}
			switch(sTransType){
			case "0001": return Acc0000(param,accSettingList);
			case "0002": return Acc0000(param,accSettingList);
			case "0003": return Acc0000(param,accSettingList);
			case "0004": return Acc0000(param,accSettingList);
			case "0005": return Acc0000(param,accSettingList);
			case "0006": return Acc0000(param,accSettingList);
			case "0007": return Acc0000(param,accSettingList);
			case "0008": return Acc0000(param,accSettingList);
			case "0009": return Acc0000(param,accSettingList);
			case "0010": return Acc0000(param,accSettingList);
			case "0011": return Acc0000(param,accSettingList);
			default:{
				logger.error("记账接口不存在，sTransType："+sTransType);
				return false;				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	//通用记账
	@Transactional(rollbackFor = Exception.class)
	public boolean Acc0000(HashMap<String, String> param, List<Map<String, String>> accSettingList) throws Exception {
		if ((accSettingList == null) || (accSettingList.size() <= 0)) {
			return false;
		}
		// 锁定账户
		for (int i = 0; i < accSettingList.size(); i++) {
			String ACCNO = accSettingList.get(i).get("SUBJECT");
			if ((ACCNO == null) || ("".equals(ACCNO))) {
				ACCNO = param.get("ACCNO");
			}
			// 账户当前余额
			String AMT = AccLock(ACCNO);
			// 记账 0-DR贷入 1-CR借出
			String DRCR = accSettingList.get(i).get("DR_CR");
			if ("0".equals(DRCR)) {
				AMT = String.valueOf(Long.valueOf(AMT) + Long.valueOf(param.get("AMT")));
			} else if ("1".equals(DRCR)) {
				AMT = String.valueOf(Long.valueOf(AMT) - Long.valueOf(param.get("AMT")));
			} else {
				throw new Exception(
						"记账设置不正确，sTransType：" + param.get("TRANSTYPE") + " ACCNO：" + ACCNO + " DR_CR：" + DRCR);
			}
			// 更新余额
			if (Long.valueOf(AMT) < 0) {
				throw new Exception(
						"账户余额不足，sTransType：" + param.get("TRANSTYPE") + " ACCNO：" + ACCNO + " DR_CR：" + DRCR);
			}
			HashMap params = new HashMap<String, String>();
			params.put("ACCNO", ACCNO);
			params.put("AMT", AMT);
			myBatisSessionTemplate.update("orm.accounting.updateAcc", params);
		}

		return true;
	}
		
	//0001-充值
	@Transactional(rollbackFor=Exception.class) 
	public boolean Acc0001(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		if((accSettingList==null)||(accSettingList.size()<=0)){
			return false;
		}
		//锁定账户
		for(int i=0;i<accSettingList.size();i++){
			String ACCNO = accSettingList.get(i).get("SUBJECT");
			if((ACCNO==null)||("".equals(ACCNO))){
				ACCNO=param.get("ACCNO");
			}
			//账户当前余额
			String AMT=AccLock(ACCNO);
			//记账 0-DR贷入	1-CR借出
			String DRCR=accSettingList.get(i).get("DR_CR");
			if("0".equals(DRCR)){
				AMT = String.valueOf(Integer.valueOf(AMT)+Integer.valueOf(param.get("AMT")));
			}else if("1".equals(DRCR)){
				AMT = String.valueOf(Integer.valueOf(AMT)-Integer.valueOf(param.get("AMT")));
			}else{
				throw new Exception("记账设置不正确，sTransType："+param.get("TRANSTYPE")+" ACCNO："+ACCNO+" DR_CR："+DRCR);
			}
			//更新余额
			if(Integer.valueOf(AMT)<0){
				throw new Exception("账户余额不足，sTransType："+param.get("TRANSTYPE")+" ACCNO："+ACCNO+" DR_CR："+DRCR);
			}
			HashMap params= new HashMap<String,String>();
			params.put("ACCNO", ACCNO);
			params.put("AMT", AMT);
			myBatisSessionTemplate.update("orm.accounting.updateAcc", params);
		}
		
		return true;
	}
	//0002-退款
	public boolean Acc0002(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		return false;
	}
	
	//0003-POS消费
	public boolean Acc0003(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		return false;
	}
	
	//0004-POS消费撤销
	public boolean Acc0004(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		return false;
	}
	
	//0005-线上消费
	public boolean Acc0005(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		return false;
	}
	
	//0006-线上消费撤销
	public boolean Acc0006(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		return false;
	}
	
	//0007-管理费
	public boolean Acc0007(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		return false;
	}
	
	//0008-管理费退费
	public boolean Acc0008(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		return false;
	}

	//0009-手续费
	public boolean Acc0009(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
		return false;
	}
	
	//0010-手续费退费
	public boolean Acc0010(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
	    return false;
	}
	
	//0011-卡注销
	public boolean Acc0011(HashMap<String, String> param,List<Map<String,String>> accSettingList) throws Exception  {
	    return false;
	}
	
	private String AccLock(String AccNO) throws Exception{
		HashMap params= new HashMap<String,String>();
		params.put("ACCNO", AccNO);
		List<Map<String,String>> accList = myBatisSessionTemplate.selectList("orm.accounting.queryAccForUpdate",params);
		if((accList==null)||(accList.size()<=0)){
			logger.error("账户不存在，ACCNO："+AccNO);
			throw new Exception("账户不存在");
		}
		String AMT=accList.get(0).get("AMT");
		return AMT;
	}
}
