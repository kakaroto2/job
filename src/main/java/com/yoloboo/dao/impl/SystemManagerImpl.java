package com.yoloboo.dao.impl;

import java.util.HashMap;
import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoloboo.dao.SystemManager;

@Repository
public class SystemManagerImpl extends BaseDao implements SystemManager {
	
	// 意见反馈
	@SuppressWarnings("rawtypes")
	@Override
	public void suggestion(HashMap param) {
		sqlSession.insert("system.suggestion",param);
	}

	// 获取系统通知设置情况
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap systemNotificationSet(String userId) {
		return (HashMap)sqlSession.selectOne("system.systemNotificationSet",userId);
	}
	
	//设置系统通知
	@SuppressWarnings("rawtypes")
	@Override
	public void setSystemNotification(HashMap param) {
		 sqlSession.update("system.setSystemNotification",param);
	}
	
	//修改手机号码
	@SuppressWarnings("rawtypes")
	@Override
	public void editPhone(HashMap param) {
		 sqlSession.update("system.editPhone",param);
	}
	
	//编辑密码
	@SuppressWarnings("rawtypes")
	@Override
	public void editPassword(HashMap param) {
		 sqlSession.update("system.editPassword",param);
	}
 
	//绑定情况获取
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap accountBound(String userId) {
		 return (HashMap)sqlSession.selectOne("system.accountBound",userId);
	}
	
	//设置绑定的情况
	@SuppressWarnings("rawtypes")
	@Override
	public void setAccountBound(HashMap param) {
		 sqlSession.update("system.setAccountBound",param);
	}
 
	//获得该用户邀请码
	@SuppressWarnings("rawtypes")
	@Override
	public String obtainInvitationCode(String userId) {
		return (String)sqlSession.selectOne("system.obtainInvitationCode",userId);
	}
	
	//获取分享的内容
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap shareContent(String userId) {
		return (HashMap)sqlSession.selectOne("system.shareContent",userId);
	}

	@Override
	public HashMap obtainUser(HashMap userParamHashMap) {
		// TODO Auto-generated method stub
		return (HashMap)sqlSession.selectOne("User.obtainUserByThreeId",userParamHashMap);
	}

/*	version 2.0新增 */
	@Override
	public void setSystemNotification2(HashMap param) {
		sqlSession.update("User.setSystemNotification2",param);
	}

	@Override
	public HashMap obtainSystemNotification(String userId) {
		return (HashMap) sqlSession.selectOne("User.obtainSystemNotification",userId);
	}

	@Override
	public List obtainSlidePicture() {
		return sqlSession.selectList("system.selectSlidePicture");
	}


}
