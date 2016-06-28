package com.yoloboo.dao.impl;

import java.util.HashMap;
import java.util.List;


import com.common.Commonparam;
import com.yoloboo.models.PushModel;
import com.yoloboo.models.UserModel;
import javapns.devices.Device;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yoloboo.dao.UserManager;
import com.json.BaseBean;

@Repository
public class UserManagerImpl extends BaseDao implements UserManager {

	@Override
	public void logout(Long userId) {
		sqlSession.update("User.logout",userId);
	}

	@Override
	public UserModel selectUserByPrimaryKey(Long userId)
	{
		return (UserModel)sqlSession.selectOne("User.selectUserByPrimaryKey", userId);
	}

	// 登录
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap loginApp(HashMap param) {
		return (HashMap) sqlSession.selectOne("User.loginApp", param);
	}
	
 
	@Override
	public void recordLogin(HashMap param) {
		sqlSession.update("User.recordLogin", param);
	}
	
	@Override
	public void updateUserLoginToken(HashMap param) {
		sqlSession.update("User.updateUserLoginToken", param);
	}
	
	//调用国家代码
	@Override
	public String obtainCode(HashMap param) {
		return (String) sqlSession.selectOne("User.obtainCode", param);
	}

	//意见反馈
	@Override
	public void updateFeedbackInfo(HashMap param) {
		sqlSession.insert("User.insertFeedbackInfo", param);
	}
	
	//校验验证码
	@Override
	public Integer checkVerificationCode(HashMap param) {
		return (Integer) sqlSession.selectOne("User.checkVerificationCode", param);
	}
	
	//校验邀请码
	@Override
	public Integer checkInvitationCode(HashMap param) {
		return (Integer) sqlSession.selectOne("User.checkInvitationCode", param);
	}

	//校验6位邀请码
	@Override
	public Integer checkInvitationCodeByEmail(HashMap param) {
		return (Integer) sqlSession.selectOne("User.checkInvitationCodeByEmail", param);
	}

	//校验批量6位邀请码
	@Override
	public Integer checkInvitationCodeByBatch(HashMap param) {
		return (Integer) sqlSession.selectOne("User.checkInvitationCodeByBatch", param);
	}

	//校验6位邀请码的使用次数
	@Override
	public Integer findUseageCount(HashMap param) {
		return (Integer) sqlSession.selectOne("User.findUseageCount", param);
	}


	//注册
	@Override
	public void register(HashMap param) {
		sqlSession.insert("User.register", param);
	}
	
	//校验随机码
	@Override
	public Integer checkCodeCount(String invitationCode) {
		return (Integer) sqlSession.selectOne("User.checkCodeCount", invitationCode);
	}
	
	
	//获得用户ID
	@Override
	public HashMap obtainUserId(String phone) {
		return (HashMap) sqlSession.selectOne("User.obtainUserId", phone);
	}
	
	
	//保存旅行风格
	@Override
	public void updatetravelStyle(HashMap param) {
		sqlSession.insert("User.updatetravelStyle", param);
		sqlSession.update("User.updateUserbackground", param);//更新个人信息的背景图片
	}
 
	//判断手机号码是否唯一
	@Override
	public Integer obtainPhoneCount(String phone) {
		return (Integer) sqlSession.selectOne("User.obtainPhoneCount", phone);
	}
	
	//在好友推荐表中，把是否是会员  改为是 手机
	@Override
	public void isnoMemeberContentUpdatePhone(HashMap param) {
		sqlSession.update("User.isnoMemeberContentUpdatePhone", param);
	}

	
	//在好友推荐表中，把是否是会员  改为是 第三方
	@Override
	public void isnoMemeberContentUpdateThree(HashMap param) {
		sqlSession.update("User.isnoMemeberContentUpdateThree", param);
	}


	@Override
	public void obtainVerificationCode(HashMap param) {
		// TODO Auto-generated method stub
		sqlSession.insert("system.insertPhoneCode", param);
	}

	@Override
	public HashMap obtainUserByThreeId(String threeId, Integer registerType) {
		// TODO Auto-generated method stub
		HashMap param = new HashMap();
		param.put("threeId", threeId);
		param.put("registerType", registerType);
		return (HashMap) sqlSession.selectOne("User.obtainUserByThreeId", param);
	}
	
	@Override
	public List<HashMap<String, Object>> findNotifyMsgList(HashMap param) {
		// TODO Auto-generated method stub
		return (List<HashMap<String, Object>>) sqlSession.selectList("User.findNotifyMsgList", param);
	}


	@Override
	public List<HashMap<String, Object>> findNotifyActivityMsgList(HashMap param) {
		// TODO Auto-generated method stub
		return (List<HashMap<String, Object>>) sqlSession.selectList("User.findNotifyActivityMsgList", param);
	}


	@Override
	public List<HashMap<String, Object>> getUserList() {
		// TODO Auto-generated method stub
		return (List<HashMap<String, Object>>) sqlSession.selectList("User.getUserList");
	}


	@Override
	public List<HashMap<String, Object>> selectNotSendNotification() {
		// TODO Auto-generated method stub
		return (List<HashMap<String, Object>>) sqlSession.selectList("User.selectNotSendNotification");
	}

	@Override
	public void updateNotifyPushStatus(Long notificationListId) {
		// TODO Auto-generated method stub
		sqlSession.update("User.updateNotifyPushStatus",notificationListId);
	}

	@Override
	public void updateActivityNotifyPushStatus() {
		// TODO Auto-generated method stub
		sqlSession.update("User.updateActivityNotifyPushStatus");
	}


	@Override
	public void updateUserPushToken(HashMap param) {
		// TODO Auto-generated method stub
		sqlSession.update("User.updateUserPushToken", param);
	}

	@Override
	public void insertSysNotifySetting(HashMap user) {
		// TODO Auto-generated method stub
		sqlSession.update("system.insertSysNotifySetting", user);
	}

	@Override
	public synchronized void updateUserAllAcountStatus(BaseBean bean,HashMap param) {
		// TODO Auto-generated method stub
		if(param.get("threeId").toString().length()>0){
			//重新绑定检查账号是否已绑定
			HashMap user = (HashMap) sqlSession.selectOne("User.obtainUserByThreeId", param);
			if(user!=null){
				bean.setStatus(401);
				bean.setMsg("已绑定其他账号！");
			}
			else{
				sqlSession.update("User.updateUserAllAcountStatus", param);
				bean.setStatus(200);
			}
		}
		else{
			sqlSession.update("User.updateUserAllAcountStatus", param);
			bean.setStatus(200);
		}
		
	}

	@Override
	public void updateUserPassword(BaseBean bean, HashMap<String, Object> param) {
		// TODO Auto-generated method stub
		Long count = (Long) sqlSession.selectOne("User.findUserCountByPhone", param);
		if(count==0){
			bean.setStatus(401);
			bean.setMsg("手机未注册");
		}
		else{
			Long userCodeCount = (Long) sqlSession.selectOne("User.findUserCodeCountByPhone", param);
			if(userCodeCount==0){
				bean.setStatus(402);
				bean.setMsg("验证码不正确！");
			}
			else{
				sqlSession.update("User.updateUserPassword", param);
				bean.setStatus(200);
				bean.setMsg("设置成功");
			}
		}
		
	}
/*#######2.0新增##########*/
	@Override
	public void updateUserLanguage(HashMap param) {
		// TODO Auto-generated method stub
		sqlSession.update("User.updateUserLanguage", param);
	}

	@Override
	public void updateBackPicture(HashMap param) {
		// TODO Auto-generated method stub
		sqlSession.update("User.updateBackPicture", param);
	}

	@Override
	public void updateUserPicture(HashMap param) {
		sqlSession.update("User.updateUserPicture",param);
	}

	@Override
	public HashMap checkIsUser(String phone) {
		return (HashMap) sqlSession.selectOne("User.checkIsUser",phone);
	}

	@Override
	public void addOfficialFriends(String userId) {
		List<HashMap> officialAccounts= (List<HashMap>) sqlSession.selectList("User.selectOfficialAccount");
		for (HashMap param:officialAccounts){
			param.put("userId",userId);
			param.put("addTime", Commonparam.Date2Str());
			sqlSession.insert("friends.addFriendById", param);
			sqlSession.update("friends.deleteRecommnedFriendById", param);
			sqlSession.insert("friends.addFriendByPassiveId", param);
			sqlSession.update("friends.deleteRecommnedFriendByPassiveId", param);
		}
	}

	@Override
	public void addInviteFriends(HashMap map) {
		sqlSession.insert("friends.addFriendById",map);
		sqlSession.update("friends.deleteRecommnedFriendById",map);
		sqlSession.insert("friends.addFriendByPassiveId", map);
		sqlSession.update("friends.deleteRecommnedFriendByPassiveId", map);
	}

	@Override
	public HashMap obtainUserInfo(String userId) {
		return (HashMap) sqlSession.selectOne("User.selectUserInfoById",userId);
	}

	@Override
	public void recordLogin_version2(HashMap user) {
		sqlSession.update("User.recordLogin_version2",user);
	}

	@Override
	public void modifyTalentStatus(HashMap param) {
		sqlSession.update("User.modifyTalentStatus",param);
	}

	@Override
	public Integer getDeclineTalent(Long userId) {
		return (Integer) sqlSession.selectOne("User.getDeclineTalent", userId);
	}

	@Override
	public int isTalent(Long userId) {
		return (Integer) sqlSession.selectOne("User.judgeMaster", userId);
	}

	@Override
	public HashMap getModelByPK(Long userId) {
		return (HashMap) sqlSession.selectOne("User.getModelByPK",userId);
	}

	@Override
	public Integer getUserViewNum(Long userId) {
		return (Integer) sqlSession.selectOne("TravelNoteDao.getUserViewNum", userId);
	}
	//校验6位随机码
	@Override
	public Integer checkEmailCodeCount(String invitationCode) {
		return (Integer) sqlSession.selectOne("User.checkEmailCodeCount", invitationCode);
	}

	//插入验证码和邮箱
	@Override
	public void insertInvitationCode(HashMap param) {
		sqlSession.insert("User.insertUserInvitationCode", param);//未发送
		sqlSession.update("User.insertInvitationCode", param);//已使用
	}

	//根据userd获取当前用户生日
	@Override
	public HashMap getBirthDayByUserId(HashMap param) {
		return (HashMap) sqlSession.selectOne("User.getBirthDayByUserId", param);
	}

	//批量将未推送的消息插入到临时表中
	@Override
	public void addNotificationTemp(List<HashMap<String,Object>> list) {
		sqlSession.insert("User.addNotificationTemp",list);
	}

	//批量将无效的token插入到临时表中
	@Override
	public void addBatchUseLessToken(List<Device> list) {
		sqlSession.insert("User.addBatchUseLessToken",list);
	}


	//删除临时表的某一条记录
	@Override
	public void deleteTemp(Long notificationListId) {
		sqlSession.update("User.deleteTemp", notificationListId);
	}


	@Override
	public HashMap getModel() {
		return (HashMap) sqlSession.selectOne("User.getModel");
	}

}
