package com.cf.cfsecurity.domain;

/*
 * TB_Sysparam实体类
 */
public class TbSysparam {
	String PARAMGROUP_ID;
	String PARAM_ID;
	String PARAM_VAL;
	String PARAM_NAME;
	String IF_CANMODIFY;
	String REMARK;
	String MODIFY_USER;
	String MODIFY_TIME;
	String CHECK_TLRNO;
	String CHECK_TIME;
	String OP_FLAG;
	public String getIF_CANMODIFY() {
		return IF_CANMODIFY;
	}
	public void setIF_CANMODIFY(String iF_CANMODIFY) {
		IF_CANMODIFY = iF_CANMODIFY;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getMODIFY_USER() {
		return MODIFY_USER;
	}
	public void setMODIFY_USER(String mODIFY_USER) {
		MODIFY_USER = mODIFY_USER;
	}
	public String getMODIFY_TIME() {
		return MODIFY_TIME;
	}
	public void setMODIFY_TIME(String mODIFY_TIME) {
		MODIFY_TIME = mODIFY_TIME;
	}
	public String getCHECK_TLRNO() {
		return CHECK_TLRNO;
	}
	public void setCHECK_TLRNO(String cHECK_TLRNO) {
		CHECK_TLRNO = cHECK_TLRNO;
	}
	public String getCHECK_TIME() {
		return CHECK_TIME;
	}
	public void setCHECK_TIME(String cHECK_TIME) {
		CHECK_TIME = cHECK_TIME;
	}
	public String getOP_FLAG() {
		return OP_FLAG;
	}
	public void setOP_FLAG(String oP_FLAG) {
		OP_FLAG = oP_FLAG;
	}
	public String getPARAMGROUP_ID() {
		return PARAMGROUP_ID;
	}
	public void setPARAMGROUP_ID(String pARAMGROUP_ID) {
		PARAMGROUP_ID = pARAMGROUP_ID;
	}
	public String getPARAM_ID() {
		return PARAM_ID;
	}
	public void setPARAM_ID(String pARAM_ID) {
		PARAM_ID = pARAM_ID;
	}
	public String getPARAM_VAL() {
		return PARAM_VAL;
	}
	public void setPARAM_VAL(String pARAM_VAL) {
		PARAM_VAL = pARAM_VAL;
	}
	public String getPARAM_NAME() {
		return PARAM_NAME;
	}
	public void setPARAM_NAME(String pARAM_NAME) {
		PARAM_NAME = pARAM_NAME;
	}

}
