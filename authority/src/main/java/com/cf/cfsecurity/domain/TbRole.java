package com.cf.cfsecurity.domain;

/*
 * TB_Role实体类
 */
public class TbRole {
	String ROLE_ID;
	String DESCR;
	String ROLE_NAME;
	String ENABLED;
	String CREATOR;
	String CREATE_TIME;
	String OP_FLAG;
	String CHECK_TIME;
	public String getOP_FLAG() {
		return OP_FLAG;
	}

	public void setOP_FLAG(String oP_FLAG) {
		OP_FLAG = oP_FLAG;
	}

	public String getCHECK_TIME() {
		return CHECK_TIME;
	}

	public void setCHECK_TIME(String cHECK_TIME) {
		CHECK_TIME = cHECK_TIME;
	}

	public String getCHECK_TLRNO() {
		return CHECK_TLRNO;
	}

	public void setCHECK_TLRNO(String cHECK_TLRNO) {
		CHECK_TLRNO = cHECK_TLRNO;
	}

	String CHECK_TLRNO;

	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	public String getDESCR() {
		return DESCR;
	}

	public void setDESCR(String dESCR) {
		DESCR = dESCR;
	}

	public String getROLE_NAME() {
		return ROLE_NAME;
	}

	public void setROLE_NAME(String rOLE_NAME) {
		ROLE_NAME = rOLE_NAME;
	}

	public String getENABLED() {
		return ENABLED;
	}

	public void setENABLED(String eNABLED) {
		ENABLED = eNABLED;
	}

	public String getCREATOR() {
		return CREATOR;
	}

	public void setCREATOR(String cREATOR) {
		CREATOR = cREATOR;
	}

	public String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

}
