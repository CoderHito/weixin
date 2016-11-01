package com.cf.cfsecurity.domain;

/*
 * TB_User实体类
 */
public class TbUser {
	String USER_ID;
	String PASSWD;
	String USER_NAME;
	String EMPLOYEE_ID;
	String LAST_IP;
	String LAST_DATE;
	String WRONG_PWD_COUNT;
	String CREATOR;
	String CREATE_TIME;
	String STATUS;
	String CHECK_STATUS;
	String CHECKER;
	String CHECK_TIME;
	String OP_FLAG;
	String CHANGE_PWD_FLAG;
	public void setOP_FLAG(String oP_FLAG) {
		OP_FLAG = oP_FLAG;
	}
	public String getOP_FLAG() {
		return OP_FLAG;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getPASSWD() {
		return PASSWD;
	}
	public void setPASSWD(String pASSWD) {
		PASSWD = pASSWD;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	public String getEMPLOYEE_ID() {
		return EMPLOYEE_ID;
	}
	public void setEMPLOYEE_ID(String eMPLOYEE_ID) {
		EMPLOYEE_ID = eMPLOYEE_ID;
	}
	public String getLAST_IP() {
		return LAST_IP;
	}
	public void setLAST_IP(String lAST_IP) {
		LAST_IP = lAST_IP;
	}
	public String getLAST_DATE() {
		return LAST_DATE;
	}
	public void setLAST_DATE(String lAST_DATE) {
		LAST_DATE = lAST_DATE;
	}
	public String getWRONG_PWD_COUNT() {
		return WRONG_PWD_COUNT;
	}
	public void setWRONG_PWD_COUNT(String wRONG_PWD_COUNT) {
		WRONG_PWD_COUNT = wRONG_PWD_COUNT;
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
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getCHECK_STATUS() {
		return CHECK_STATUS;
	}
	public void setCHECK_STATUS(String cHECK_STATUS) {
		CHECK_STATUS = cHECK_STATUS;
	}
	public String getCHECKER() {
		return CHECKER;
	}
	public void setCHECKER(String cHECKER) {
		CHECKER = cHECKER;
	}
	public String getCHECK_TIME() {
		return CHECK_TIME;
	}
	public void setCHECK_TIME(String cHECK_TIME) {
		CHECK_TIME = cHECK_TIME;
	}
	public String getCHANGE_PWD_FLAG() {
		return CHANGE_PWD_FLAG;
	}
	public void setCHANGE_PWD_FLAG(String cHANGE_PWD_FLAG) {
		CHANGE_PWD_FLAG = cHANGE_PWD_FLAG;
	}
}
