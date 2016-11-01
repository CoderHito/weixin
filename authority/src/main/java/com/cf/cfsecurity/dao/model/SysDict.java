package com.cf.cfsecurity.dao.model;

import java.util.Date;

public class SysDict extends SysDictKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.GROUP_NAME
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    private String groupName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.DICT_NAME
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    private String dictDescn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.LANG
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    private String lang;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.CREATOR
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.CREATE_DATE
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    private Date createDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.GROUP_NAME
     *
     * @return the value of sys_dict.GROUP_NAME
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.GROUP_NAME
     *
     * @param groupName the value for sys_dict.GROUP_NAME
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.DICT_NAME
     *
     * @return the value of sys_dict.DICT_NAME
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public String getDictDescn() {
        return dictDescn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.DICT_NAME
     *
     * @param dictDescn the value for sys_dict.DICT_NAME
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public void setDictDescn(String dictDescn) {
        this.dictDescn = dictDescn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.LANG
     *
     * @return the value of sys_dict.LANG
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public String getLang() {
        return lang;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.LANG
     *
     * @param lang the value for sys_dict.LANG
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.CREATOR
     *
     * @return the value of sys_dict.CREATOR
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.CREATOR
     *
     * @param creator the value for sys_dict.CREATOR
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.CREATE_DATE
     *
     * @return the value of sys_dict.CREATE_DATE
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.CREATE_DATE
     *
     * @param createDate the value for sys_dict.CREATE_DATE
     *
     * @mbggenerated Mon Jul 15 14:09:07 CST 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}