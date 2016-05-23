package com.yoloboo.service;

import com.yoloboo.excptions.LoginFailureException;
import com.yoloboo.excptions.ResultNotFoundException;
import com.json.BaseBean;
import com.yoloboo.controller.BaseBean.UserBean;
import com.yoloboo.controller.bean.NotificationBean;
import com.yoloboo.models.UserModel;

import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2015/12/28.
 */
public interface UserService
{
	BaseBean registerUser(UserBean userBean);

	String obtainCode(String ab) throws ResultNotFoundException;

	UserModel loginApp(String phone, String password) throws ResultNotFoundException;

	void updatePushToken(String pushToken, String userId);

	UserModel loginThree(String threeId, int registerType) throws LoginFailureException;

	void editPassword(String userId, String newPassword);

	BaseBean updateUserPassword(String phone, String code, String password);

	void updateUserLanguage(String language, String userId);

	void updateUserPicture(String qiniukey, String userId);

	String updateBgPicture(String qiniukey, String userId);

	BaseBean updateUserAllAcountStatus(String userId, String uToken, String registerType, String password, String threeId);

	void editPhone(String userId, String phone);

	void forgetPassword(String phone) throws ResultNotFoundException;

	void setSystemNotification(NotificationBean notificationBean);

	void addSuggestion(String suggestion, String email, String userId);

	HashMap obtainSystemNotification(String userId);

	HashMap obtainAccountBound(String userId);

	BaseBean setAccountBound(String userId, String facebook, String facebookName, String weixin, String weixinName, String sina, String sinaName, String phoneNum, String password, String uToken);

	HashMap shareContent(String userId);

	HashMap saveMyInfo(String travelStyle, String userId, String nickName, String zodiac,String birthday, String areaName, String qiniukey, String language,String description);

	HashMap obtainMyInfo(String userId);

	List obtainSlidePicture();

	BaseBean registerUser_version2(UserBean userBean);

	void emailRegister(String email,String sex,String applicationReasons);
}
