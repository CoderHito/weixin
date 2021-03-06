package com.cf.cfsecurity.dao.model;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CfUser implements UserDetails{
    
	private static final long serialVersionUID = -4129175143156756935L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.USER_ID
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.PASSWD
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String passwd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.NAME
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.DEPT_CODE
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String deptCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.LAST_IP
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String lastIp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.LOGIN_COUNT
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private BigDecimal loginCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.LAST_DATE
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String lastDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.CREATOR
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.CREATE_TIME
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.accountNonExpired
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String accountnonexpired;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.accountNonLocked
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String accountnonlocked;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.credentialsNonExpired
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String credentialsnonexpired;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CNAPS2.CF_USER.STATUS
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    private String status;
    
    private int wrongPwdCount;
    
    private String employeeId;
    
    private String branchNo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.USER_ID
     *
     * @return the value of CNAPS2.CF_USER.USER_ID
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.USER_ID
     *
     * @param userId the value for CNAPS2.CF_USER.USER_ID
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.PASSWD
     *
     * @return the value of CNAPS2.CF_USER.PASSWD
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.PASSWD
     *
     * @param passwd the value for CNAPS2.CF_USER.PASSWD
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.NAME
     *
     * @return the value of CNAPS2.CF_USER.NAME
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.NAME
     *
     * @param name the value for CNAPS2.CF_USER.NAME
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.DEPT_CODE
     *
     * @return the value of CNAPS2.CF_USER.DEPT_CODE
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.DEPT_CODE
     *
     * @param deptCode the value for CNAPS2.CF_USER.DEPT_CODE
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.LAST_IP
     *
     * @return the value of CNAPS2.CF_USER.LAST_IP
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.LAST_IP
     *
     * @param lastIp the value for CNAPS2.CF_USER.LAST_IP
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.LOGIN_COUNT
     *
     * @return the value of CNAPS2.CF_USER.LOGIN_COUNT
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public BigDecimal getLoginCount() {
        return loginCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.LOGIN_COUNT
     *
     * @param loginCount the value for CNAPS2.CF_USER.LOGIN_COUNT
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setLoginCount(BigDecimal loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.LAST_DATE
     *
     * @return the value of CNAPS2.CF_USER.LAST_DATE
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getLastDate() {
        return lastDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.LAST_DATE
     *
     * @param lastDate the value for CNAPS2.CF_USER.LAST_DATE
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.CREATOR
     *
     * @return the value of CNAPS2.CF_USER.CREATOR
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.CREATOR
     *
     * @param creator the value for CNAPS2.CF_USER.CREATOR
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.CREATE_TIME
     *
     * @return the value of CNAPS2.CF_USER.CREATE_TIME
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.CREATE_TIME
     *
     * @param createTime the value for CNAPS2.CF_USER.CREATE_TIME
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.accountNonExpired
     *
     * @return the value of CNAPS2.CF_USER.accountNonExpired
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getAccountnonexpired() {
        return accountnonexpired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.accountNonExpired
     *
     * @param accountnonexpired the value for CNAPS2.CF_USER.accountNonExpired
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setAccountnonexpired(String accountnonexpired) {
        this.accountnonexpired = accountnonexpired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.accountNonLocked
     *
     * @return the value of CNAPS2.CF_USER.accountNonLocked
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getAccountnonlocked() {
        return accountnonlocked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.accountNonLocked
     *
     * @param accountnonlocked the value for CNAPS2.CF_USER.accountNonLocked
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setAccountnonlocked(String accountnonlocked) {
        this.accountnonlocked = accountnonlocked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.credentialsNonExpired
     *
     * @return the value of CNAPS2.CF_USER.credentialsNonExpired
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getCredentialsnonexpired() {
        return credentialsnonexpired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.credentialsNonExpired
     *
     * @param credentialsnonexpired the value for CNAPS2.CF_USER.credentialsNonExpired
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setCredentialsnonexpired(String credentialsnonexpired) {
        this.credentialsnonexpired = credentialsnonexpired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CNAPS2.CF_USER.STATUS
     *
     * @return the value of CNAPS2.CF_USER.STATUS
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CNAPS2.CF_USER.STATUS
     *
     * @param status the value for CNAPS2.CF_USER.STATUS
     *
     * @mbggenerated Mon Dec 02 21:39:08 CST 2013
     */
    public void setStatus(String status) {
        this.status = status;
    }

 	public int getWrongPwdCount() {
		return wrongPwdCount;
	}

	public void setWrongPwdCount(int wrongPwdCount) {
		this.wrongPwdCount = wrongPwdCount;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	// 获取当前登录用户详细信息必须重写hashCode和equals方法
 	public int hashCode() {
 		return this.getUsername().hashCode();
 	}

 	public boolean equals(Object object) {
 		boolean flag = false;
 		if (object instanceof UserDetails) {
 			UserDetails user = (UserDetails) object;
 			if (user.getUsername().equals(this.getUsername()))
 				flag = true;
 		}
 		return flag;
 	}

 	private Collection<? extends GrantedAuthority> authorities; // 权限集合

	
	public void setAuthorities(
 			Collection<? extends GrantedAuthority> authorities) {
 		this.authorities = authorities;
 	}

 	public Collection<? extends GrantedAuthority> getAuthorities() {
 		return authorities;
 	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.passwd;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}