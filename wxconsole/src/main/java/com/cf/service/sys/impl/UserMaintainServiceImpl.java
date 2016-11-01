package com.cf.service.sys.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.biz.domain.Result;
import com.cf.service.sys.IRollBackService;
import com.cf.service.sys.IUserMaintainService;
import com.cf.cfsecurity.constant.DictConstant;
import com.cf.cfsecurity.domain.TbUser;
import com.cf.base.BaseSupport;
import com.cf.util.security.Util;
import com.wxbatis.impl.batch.Batchmate;
import com.wxbatis.impl.data.Page;
import com.wxbatis.impl.template.MyBatisSessionTemplate;

/**
 * 类 <code>UserMaintainServiceImpl</code>操作员维护
 * 
 * @author sven
 * @version 20150922
 */
@Service("userMaintainService")
public class UserMaintainServiceImpl implements IUserMaintainService {
	@Autowired
	private MyBatisSessionTemplate myBatisSessionTemplate;
	@Autowired
	private IRollBackService rollBackService;

	@SuppressWarnings("unchecked")
	@Override
	public Result<T> changePasswd(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<T>();
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUserTemp.UsertempByUSER_ID", params);
		if (count > 0) {
			rs.setSuccess(false);
			rs.setRetMessage("该记录正在进行审核");
			return rs;
		}
		String userid = BaseSupport.CframeUtil.GetCurrentUserName();
		String passwdold = params.get("password2");
		String passwd = params.get("password");
		String tmp1 = BaseSupport.Encoder.encrypt(passwdold, userid);
		String tmp2 = BaseSupport.Encoder.encrypt(passwd, userid);
		params.put("USER_ID", userid);
		params.put("PASSWD", tmp2);
		int ret = -1;
		// 验证原密码
		HashMap<String, String> map = (HashMap<String, String>) this.myBatisSessionTemplate
				.selectOne("orm.sys.tbUser.queryPasswd", params);
		String oldpasswd = map.get("PASSWD");
		if (!tmp1.equals(oldpasswd)) {
			rs.setRetMessage("原密码不正确");
			rs.setSuccess(false);
			return rs;
		}
		// 更新新密码
		ret = this.myBatisSessionTemplate.update("orm.sys.tbUser.updatePasswd", params);
		if (ret > 0) {
			rs.setRetMessage("修改密码成功！");
			rs.setSuccess(true);
		} else {
			rs.setRetMessage("修改密码失败！");
			rs.setSuccess(false);
		}
		return rs;
	}

	@Override
	public Page<Map<String, String>> queryUser(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectPage("orm.sys.tbUser.queryUserPage", params);
	}
	@Override
	public List<Map<String, String>> queryUserQueryRole(HashMap<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return this.myBatisSessionTemplate.selectList("orm.sys.tbUser.queryUserRole", params);
	}
	@Override
	public Result<T> saveUser(Map<String, String> params,List list) throws Exception {
		Result<T> rs = new Result<T>();
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUserTemp.UsertempByUSER_ID", params);
		if (count > 0) {
			rs.setSuccess(false);
			rs.setRetMessage("该记录正在进行审核");
			return rs;
		}
		String userid = params.get("USER_ID");
		String tmp1 = BaseSupport.Encoder.encrypt(userid, userid);
		String saveFlag = params.get("saveFlag");
		int ret = -1;
		if ("mod".equals(saveFlag)) {
			TbUser tbUser = (TbUser) this.myBatisSessionTemplate.selectOne("orm.sys.tbUser.queryUserByUSER_ID", params);
			tbUser.setOP_FLAG("修改");
			try {
				ret = rollBackService.modUser(tbUser, params,list);
				if (ret > 0) {
					rs.setRetMessage("请求已提交，请等待审核");
					rs.setSuccess(true);
				} else {
					rs.setRetMessage("修改用户失败！");
					rs.setSuccess(false);
				}
			} catch (Exception e) {
				rs.setSuccess(false);
				rs.setRetMessage("审核失败，数据库操作异常");
				return rs;
			}
		} else if ("add".equals(saveFlag)) {
			// 创建人、创建时间
			params.put("CREATOR", BaseSupport.CframeUtil.GetCurrentUserName());
			params.put("CREATE_TIME", Util.getCurrentDateTimeString());
			params.put("STATUS", DictConstant.USER_STATUS_NORMAL);
			params.put("PASSWD", tmp1);
			Integer userCount = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUser.selectUserCount",
					params);
			if (userCount == 0) {
				params.put("OP_FLAG", "增加");
				ret=saveUserRole(params,list);				
			} else {
				rs.setRetMessage("用户已存在，不能新增！");
				rs.setSuccess(false);
				return rs;
			}
			if (ret > 0) {
				rs.setRetMessage("请求已提交，请等待审核");
				rs.setSuccess(true);
			} else {
				rs.setRetMessage("新增用户失败！");
				rs.setSuccess(false);
			}
		}
		return rs;
	}

	@Override
	public Result<T> delUser(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<T>();
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUserTemp.UsertempByUSER_ID", params);
		if (count > 0) {
			rs.setSuccess(false);
			rs.setRetMessage("该记录正在进行审核");
			return rs;
		}
		// 注销状态
		params.put("STATUS", DictConstant.USER_STATUS_LOCKED);
		Integer userCount = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUser.selectUserStatusCount",
				params);
		if (userCount > 0) {
			rs.setRetMessage("该操作员为注销状态，不需注销！");
			rs.setSuccess(false);
			return rs;
		} else {
			int ret = this.myBatisSessionTemplate.update("orm.sys.tbUser.updateUser", params);
			if (ret > 0) {
				rs.setRetMessage("注销成功！");
				rs.setSuccess(true);
			} else {
				rs.setRetMessage("注销失败！");
				rs.setSuccess(false);
			}
		}
		return rs;
	}

	@Override
	public Result<T> activeUser(Map<String, String> params) throws Exception {
		Result<T> rs = new Result<T>();
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUserTemp.UsertempByUSER_ID", params);
		if (count > 0) {
			rs.setSuccess(false);
			rs.setRetMessage("该记录正在进行审核");
			return rs;
		}
		// 注销状态
		params.put("STATUS", DictConstant.USER_STATUS_NORMAL);
		params.put("LOGIN_COUNT", "0");
		Integer userCount = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUser.selectUserStatusCount",
				params);
		if (userCount > 0) {
			rs.setRetMessage("该操作员为正常状态，不需激活！");
			rs.setSuccess(false);
			return rs;
		} else {
			int ret = this.myBatisSessionTemplate.update("orm.sys.tbUser.updateUser", params);
			if (ret > 0) {
				rs.setRetMessage("激活成功！");
				rs.setSuccess(true);
			} else {
				rs.setRetMessage("激活失败！");
				rs.setSuccess(false);
			}
		}
		return rs;
	}

	@Override
	public int resetPassword(Map<String, String> params) throws Exception {
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUserTemp.UsertempByUSER_ID", params);
		if (count > 0) {
			
			return -1;
		}
		return this.myBatisSessionTemplate.update("orm.sys.tbUser.resetPasswd", params);
	}

	@Override
	public List<Map<String, String>> queryUserRole(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectList("orm.sys.tbUser.queryUserRole", params);
	}
	@Override
	public List<Map<String, String>> queryAddUserRole(Map<String, String> params) throws Exception {
		return this.myBatisSessionTemplate.selectList("orm.sys.tbUser.queryAddUserRole", params);
	}
	
	@Override
	public TbUser queryByUserId(Map<String, String> params) throws Exception {
		return (TbUser) this.myBatisSessionTemplate.selectOne("orm.sys.tbUser.queryUserByUSER_ID", params);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public int saveUserRole(Map<String, String> params,List list) throws Exception {
		Integer count = (Integer) this.myBatisSessionTemplate.selectOne("orm.sys.tbUserTemp.UsertempByUSER_ID", params);
		if (count > 0) {
			return -1;
		}
		TbUser tbUser = (TbUser) this.myBatisSessionTemplate.selectOne("orm.sys.tbUser.queryUserByUSER_ID", params);
		try {
			ArrayList<Batchmate> batchmates = new ArrayList<Batchmate>();
			for (int k = 0; k < list.size(); k++) {
				HashMap<String, String> tmpmap = (HashMap<String, String>) list.get(k);
				tmpmap.put("CREATOR", BaseSupport.CframeUtil.GetCurrentUserName());
				tmpmap.put("CREATE_TIME", Util.getCurrentDateTimeString());
				Batchmate bt = new Batchmate();
				bt.setOptType(Batchmate.TYPE.INSERT);
				if(tmpmap.get("checked").equals("true"))
				{
				bt.setStatement("orm.sys.tbUser.insertUserRoleTemp");
				}
				else {
				continue;
				}
				bt.setParameter(tmpmap);
				batchmates.add(bt);
			}
			Batchmate bt2 = new Batchmate();
			bt2.setOptType(Batchmate.TYPE.INSERT);
			bt2.setStatement("orm.sys.tbUserTemp.addUserTemp");
			bt2.setParameter(params);
		    batchmates.add(bt2);
			Batchmate[] batchmates1 = new Batchmate[batchmates.size()];
			for (int i = 0; i < batchmates.size(); i++) {
				batchmates1[i] = batchmates.get(i);
			}
			return this.myBatisSessionTemplate.batch(batchmates1);
		} catch (Exception e) {
			return 0;
		}
	}

}
