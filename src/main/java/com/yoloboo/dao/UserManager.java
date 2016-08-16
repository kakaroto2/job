package com.yoloboo.dao;

import java.util.HashMap;
import java.util.List;




import com.json.BaseBean;
import com.yoloboo.models.PushModel;
import com.yoloboo.models.UserModel;
import javapns.devices.Device;


public interface UserManager {

	void logout(Long userId);

	UserModel selectUserByPrimaryKey(Long userId);

	void recordLogin(HashMap param);//
	
	HashMap loginApp(HashMap param);
	
	void updateUserLoginToken(HashMap user);

	String obtainCode(HashMap param);//获取国家代码

	void updateFeedbackInfo(HashMap param);//意见反馈

	Integer checkVerificationCode(HashMap param);//校验验证码
	
	Integer checkInvitationCode(HashMap param);//校验邀请码

	Integer checkInvitationCodeByEmail(HashMap param);//校验邀请码

	Integer checkInvitationCodeByBatch(HashMap param);//校验邀请码

	Integer findUseageCount(HashMap param);//校验6位邀请码的使用次数
	
	void register(HashMap param);//注册

	Integer checkCodeCount(String invitationCode);//校验邀请码，如果数据库已经有了，就要换一个用

	Integer checkEmailCodeCount(String invitationCode);//校验6位邀请码，如果数据库已经有了，就要换一个用
	
	HashMap obtainUserId(String phone);//获得用户ID
	
	void updatetravelStyle(HashMap param);//保存旅行风格

	Integer obtainPhoneCount(String phone);//判断手机号码是唯一
	
	void isnoMemeberContentUpdatePhone(HashMap param);//信息需要修改

	void isnoMemeberContentUpdateThree(HashMap param);//信息修改修改
	
/*##########2.0新增############*/
	void obtainVerificationCode(HashMap param);

	HashMap obtainUserByThreeId(String threeId, Integer registerType);

	List<HashMap<String,Object>> findNotifyMsgList(HashMap param);

	List<HashMap<String,Object>> findNotifyActivityMsgList(HashMap param);

	List<HashMap<String,Object>> getUserList();

	List<HashMap<String,Object>> selectNotSendNotification();

	void updateNotifyPushStatus(Long notificationListId);

	void updateActivityNotifyPushStatus();

	void updateUserPushToken(HashMap param);

	void insertSysNotifySetting(HashMap user);

	void updateUserAllAcountStatus(BaseBean bean, HashMap param);

	void updateUserLanguage(HashMap param);

	void updateUserPassword(BaseBean bean, HashMap<String, Object> param);

	void updateBackPicture(HashMap param);

	void insertInvitationCode(HashMap param);

	void updateUserPicture(HashMap param);

	Object checkIsUser(String phone);

	void addOfficialFriends(String userId);

	void addInviteFriends(HashMap map);

	HashMap obtainUserInfo(String userId);

	void recordLogin_version2(HashMap user);

	void modifyTalentStatus(HashMap param);

	Integer getDeclineTalent(Long userId);

	int isTalent(Long userId);

	HashMap getModelByPK(Long userId);

	Integer getUserViewNum(Long userId);

	HashMap getBirthDayByUserId(HashMap param);

	void addNotificationTemp(List<HashMap<String,Object>> list);

	void addBatchUseLessToken(List<Device> list);

	void  deleteTemp(Long notificationListId);

	HashMap  getModel();

	HashMap getModelById(Long param);

	void insertFailToken(String token);

	List<HashMap<String,Object>> getUseLessToken();

	void updateUseLessToken(HashMap map);

	void updateTicket(String jaspticket);
}
