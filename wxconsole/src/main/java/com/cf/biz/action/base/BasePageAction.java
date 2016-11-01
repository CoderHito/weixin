package com.cf.biz.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Namespace("/")
@Controller
public class BasePageAction extends ParentAction {
	@Action(value = "tologin", results = { @Result(name = "success", location = "/jsp/login.jsp") })
	public String toLogin() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "main", results = { @Result(name = "success", location = "/jsp/sys/weixinMaintain.jsp") })
	public String main() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "roleMaintain", results = { @Result(name = "success", location = "/jsp/sys/roleMaintain.jsp") })
	public String roleMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "userMaintain", results = { @Result(name = "success", location = "/jsp/sys/userMaintain.jsp") })
	public String userMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "qryLoginAudits", results = {
			@Result(name = "success", location = "/jsp/sysQry/qryLoginAudits.jsp") })
	public String qryLoginAudits() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "qryOperateAudits", results = {
			@Result(name = "success", location = "/jsp/sysQry/qryOperateAudits.jsp") })
	public String qryOperateAudits() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "onlineUserManage", results = {
			@Result(name = "success", location = "/jsp/sys/onlineUserManage.jsp") })
	public String onlineUserManage() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "sysParamMaintain", results = {
			@Result(name = "success", location = "/jsp/sys/sysParamMaintain.jsp") })
	public String sysParamMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "dictMaintain", results = { @Result(name = "success", location = "/jsp/sys/dictMaintain.jsp") })
	public String dictMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "roleMaintainCheck", results = {
			@Result(name = "success", location = "/jsp/sys/roleMaintainCheck.jsp") })
	public String roleMaintainCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "userMaintainCheck", results = {
			@Result(name = "success", location = "/jsp/sys/userMaintainCheck.jsp") })
	public String userMaintainCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "sysParamMaintainCheck", results = {
			@Result(name = "success", location = "/jsp/sys/sysParamMaintainCheck.jsp") })
	public String sysParamMaintainCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "dictMaintainCheck", results = {
			@Result(name = "success", location = "/jsp/sys/dictMaintainCheck.jsp") })
	public String dictMaintainCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "uploadImportFile", results = {
			@Result(name = "success", location = "/jsp/inclusive/fileUpload.jsp") })
	public String uploadImportFile() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "fsTransationData", results = {
			@Result(name = "success", location = "/jsp/biz/fsTransationData.jsp") })
	public String fsTransationData() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "fsTransationDataCheck", results = {
			@Result(name = "success", location = "/jsp/biz/fsTransationDataCheck.jsp") })
	public String fsTransationDataCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "subjectSetting", results = {
			@Result(name = "success", location = "/jsp/account/subjectSetting.jsp") })
	public String subjectSetting() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "subjectSettingCheck", results = {
			@Result(name = "success", location = "/jsp/account/subjectSettingCheck.jsp") })
	public String subjectSettingCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "accMaintain", results = { @Result(name = "success", location = "/jsp/account/accMaintain.jsp") })
	public String accMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "accCheck", results = { @Result(name = "success", location = "/jsp/account/accCheck.jsp") })
	public String accCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "agentMaintain", results = { @Result(name = "success", location = "/jsp/card/agentMaintain.jsp") })
	public String agentMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "agentCheck", results = { @Result(name = "success", location = "/jsp/card/agentCheck.jsp") })
	public String agentCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "cardInfoMaintain", results = {
			@Result(name = "success", location = "/jsp/card/cardInfoMaintain.jsp") })
	public String cardInfoMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "cardOrderMaintain", results = {
			@Result(name = "success", location = "/jsp/card/cardOrderMaintain.jsp") })
	public String cardOrderMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}
	// 订单查询  add by hito
	@Action(value = "cardQuery", results = { @Result(name = "success", location = "/jsp/card/cardQuery.jsp") })
	public String cardQuery() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "cardOrderFinanceCheck", results = {
			@Result(name = "success", location = "/jsp/card/cardOrderFinanceCheck.jsp") })
	public String cardOrderFinanceCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "cardOrderBizCheck", results = {
			@Result(name = "success", location = "/jsp/card/cardOrderBizCheck.jsp") })
	public String cardOrderBizCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "accReconciliation", results = {
			@Result(name = "success", location = "/jsp/account/accReconciliation.jsp") })
	public String accReconciliation() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "accReconciliationLog", results = {
			@Result(name = "success", location = "/jsp/account/accReconciliationLog.jsp") })
	public String accReconciliationLog() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "accReconciliationCheck", results = {
			@Result(name = "success", location = "/jsp/account/accReconciliationCheck.jsp") })
	public String accReconciliationCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	@Action(value = "cardAllocating", results = {
			@Result(name = "success", location = "/jsp/card/cardAllocating.jsp") })
	public String cardAllocating() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 单边账维护
	@Action(value = "accountMaintain", results = {
			@Result(name = "success", location = "/jsp/card/accountMaintain.jsp") })
	public String accountMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 交易明细报表
	@Action(value = "transDetail", results = { @Result(name = "success", location = "/jsp/report/transDetail.jsp") })
	public String transDetail() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 订单余额汇总报表
	@Action(value = "orderBalanceSummary", results = {
			@Result(name = "success", location = "/jsp/report/orderBalanceSummary.jsp") })
	public String orderBalanceSummary() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 交易日报表
	@Action(value = "transDaily", results = { @Result(name = "success", location = "/jsp/report/transDaily.jsp") })
	public String transDaily() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 卡产品维护
	@Action(value = "cardProductManage", results = {
			@Result(name = "success", location = "/jsp/card/cardProduct.jsp") })
	public String cardProductManage() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 卡产品维护审核
	@Action(value = "cardProductCheck", results = {
			@Result(name = "success", location = "/jsp/card/cardProductCheck.jsp") })
	public String cardProductCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 管理费维护
	@Action(value = "manageFee", results = { @Result(name = "success", location = "/jsp/card/manageFee.jsp") })
	public String manageFee() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 管理费维护审核
	@Action(value = "manageFeeCheck", results = {
			@Result(name = "success", location = "/jsp/card/manageFeeCheck.jsp") })
	public String manageFeeCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 交易明细查询
	@Action(value = "cardTransInfoQry", results = {
			@Result(name = "success", location = "/jsp/card/cardTransInfoQry.jsp") })
	public String cardTransInfoQry() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 换卡
	@Action(value = "changeCard", results = { @Result(name = "success", location = "/jsp/card/changeCard.jsp") })
	public String changeCard() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 换卡审核
	@Action(value = "changeCardCheck", results = {
			@Result(name = "success", location = "/jsp/card/changeCardCheck.jsp") })
	public String changeCardCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 卡日常管理
	@Action(value = "cardDailyMaintain", results = {
			@Result(name = "success", location = "/jsp/card/cardDailyManage.jsp") })
	public String cardDailyManage() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 收费规则维护
	@Action(value = "chargeMaintain", results = {
			@Result(name = "success", location = "/jsp/account/chargeMaintain.jsp") })
	public String chargeMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 收费规则维护审核
	@Action(value = "chargeMaintainCheck", results = {
			@Result(name = "success", location = "/jsp/account/chargeMaintainCheck.jsp") })
	public String chargeMaintainCheck() throws Exception {
		return ParentAction.SUCCESS;
	}

	// 微信菜单维护
	@Action(value = "weixinMaintain", results = { @Result(name = "success", location = "/jsp/sys/weixinMaintain.jsp") })
	public String weixinMaintain() throws Exception {
		return ParentAction.SUCCESS;
	}

	//消费流水表
	@Action(value = "consumeReport", results = { @Result(name = "success", location = "/jsp/report/consumeReport.jsp") })
	public String consumeReport() throws Exception {
		return ParentAction.SUCCESS;
	}
	
}
