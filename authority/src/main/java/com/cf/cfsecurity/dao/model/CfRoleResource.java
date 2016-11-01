package com.cf.cfsecurity.dao.model;

import java.util.Date;

public class CfRoleResource extends CfRoleResourceKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cf_role_resource.CREATOR
	 * @mbggenerated  Thu Jun 27 15:36:25 CST 2013
	 */
	private String creator;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column cf_role_resource.CREATE_DATE
	 * @mbggenerated  Thu Jun 27 15:36:25 CST 2013
	 */
	private Date createDate;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cf_role_resource.CREATOR
	 * @return  the value of cf_role_resource.CREATOR
	 * @mbggenerated  Thu Jun 27 15:36:25 CST 2013
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cf_role_resource.CREATOR
	 * @param creator  the value for cf_role_resource.CREATOR
	 * @mbggenerated  Thu Jun 27 15:36:25 CST 2013
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column cf_role_resource.CREATE_DATE
	 * @return  the value of cf_role_resource.CREATE_DATE
	 * @mbggenerated  Thu Jun 27 15:36:25 CST 2013
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column cf_role_resource.CREATE_DATE
	 * @param createDate  the value for cf_role_resource.CREATE_DATE
	 * @mbggenerated  Thu Jun 27 15:36:25 CST 2013
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}