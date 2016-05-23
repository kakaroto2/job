package com.yoloboo.dao;

import java.util.HashMap;
import java.util.List;




import com.json.BaseBean;


public interface SystemManager {


	void suggestion(HashMap param);//意见反馈

	HashMap systemNotificationSet(String userId);//获取系统通知设置情况
	 
	void setSystemNotification(HashMap param);//设置系统通知
	
	void editPhone(HashMap param);//设置系统通知
	
	void editPassword(HashMap param);//编辑密码
	
	HashMap accountBound(String userId);//绑定情况获取
	
	void setAccountBound(HashMap param);//设置绑定的账号
	
	String obtainInvitationCode(String userId);//获得该用户邀请码

	HashMap shareContent(String userId);//获得要分享的内容

	HashMap obtainUser(HashMap userParamHashMap);

	/* version2.0新增*/

	void setSystemNotification2(HashMap param);

	HashMap obtainSystemNotification(String userId);

	List obtainSlidePicture();
}
