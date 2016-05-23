package com.yoloboo.service.impl;

import com.common.*;
import com.common.constans.NotificationListType;
import com.yoloboo.excptions.LoginFailureException;
import com.yoloboo.excptions.ResultNotFoundException;
import com.json.BaseBean;
import com.yoloboo.controller.BaseBean.UserBean;
import com.yoloboo.controller.bean.NotificationBean;
import com.yoloboo.dao.*;
import com.yoloboo.models.TravelStyleModel;
import com.yoloboo.models.UserModel;
import com.yoloboo.service.TravelStyleService;
import com.yoloboo.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by ZHOU005 on 2015/12/28.
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserManager userManager;
    @Resource
    private CountryManager countryManager;
    @Resource
    private SystemManager systemManager;
    @Resource
    private MyCenterManager myCenterManager;
    @Resource
    private TravelManager travelManager;
    @Resource
    private TravelStyleService travelStyleService;

    @Override
    public BaseBean registerUser(UserBean userBean) {
        BaseBean baseBean = new BaseBean();

        HashMap param = new HashMap();
        HashMap param1 = new HashMap();
        if (userBean.getRegisterType() == 1) {// 手机注册
            Integer obtainPhoneCount = userManager.obtainPhoneCount(userBean.getPhone());// 获得手机号码是否唯一
            if (obtainPhoneCount > 0) {
                baseBean.setStatus(401);
                baseBean.setMsg("该手机号码已经注册过");

                return baseBean;
            }
            param.put("threeId", "");
        } else {//1手机注册2facebook3sina4微信

            HashMap user = userManager.obtainUserByThreeId(userBean.getThreeId(), userBean.getRegisterType());
            if (user != null) {
                user.put("uToken", UUID.randomUUID().toString());// 登录token值
                user.put("lastTime", Commonparam.Date2Str());// 登录时间
                userManager.recordLogin(user);

                baseBean.setStatus(200);
                baseBean.setRows(user);

                return baseBean;
            }
            param.put("threeId", userBean.getThreeId());
        }
        param.put("registerType", userBean.getRegisterType());

        // 生成自己的邀请码
        String invitationCode;
        // 当校验数据库中是否有重复的邀请码，有的话再随机生成一个。
        Integer codeCount;

        do {
            invitationCode = Commonparam.createRandomUpper();
            codeCount = userManager.checkCodeCount(invitationCode);
        }
        while (codeCount > 0);


        param.put("invitationCode", invitationCode);// 自己的邀请码
        param.put("phone", userBean.getPhone() == null ? "" : userBean.getPhone());
        param.put("password", userBean.getPassword());

        //传入qiniuKey
        if (StringUtils.isBlank(userBean.getQiniukey())) {   //使用第三方头像
            param.put("picture", userBean.getHeadImgUrl());   //第三方头像地址
        } else {
            param.put("picture", QiNiuUtils.getSaveDBUrl2(userBean.getQiniukey()));    //七牛key值

        }

        param.put("nickname", EmojiFilter.filterEmoji(userBean.getNickname()));

        param.put("code", userBean.getCode());// 被邀请人号码
        if (userBean.getCountryCode() == null) {
            param.put("countryCode", "");// 被邀请人国家代码
        } else {
            param.put("countryCode", userBean.getCountryCode());// 被邀请人国家代码
        }
        param.put("registerTime", Commonparam.Date2Str());
        String token = Commonparam.createRandomNumber() + Commonparam.createRandomNumber() + Commonparam.createRandomNumber();
        param.put("token", token);

        // 注册完成
        userManager.register(param);

        HashMap user = null;
        //is_nomember=1
        if (userBean.getRegisterType() == 1) {
            user = userManager.obtainUserId(userBean.getPhone());
        } else {
            user = userManager.obtainUserByThreeId(userBean.getThreeId(), userBean.getRegisterType());
        }
        user.put("uToken", UUID.randomUUID().toString());// 登录token值
        user.put("lastTime", Commonparam.Date2Str());// 登录时间
        userManager.recordLogin(user);
        userManager.insertSysNotifySetting(user);

        String[] ids = userBean.getTravelStyle().split(",");
        for (int i = 0; i < ids.length; i++) {
            param1.clear();
            param1.put("userId", user.get("userId"));
            param1.put("travelStyleId", ids[i]);
            userManager.updatetravelStyle(param1);
        }

        param.put("userId", user.get("userId"));
        // 在好友推荐表中信息也需要修改
        if (userBean.getRegisterType() == 1) {//手机用户
            userManager.isnoMemeberContentUpdatePhone(param);
        } else {//第三方用户
            userManager.isnoMemeberContentUpdateThree(param);
        }
        // 找到是谁的邀请码，保存到消息表中
        Long initiativeId = countryManager.obtainInitiativeIdByCode(userBean.getCode());// 获取主动的ID
        //添加官方账号并删除推荐表
        //userManager.addOfficialFriends(user.get("userId").toString());
        //添加邀请人并删除推荐表
        HashMap map = new HashMap();
        map.put("userId", user.get("userId"));
        map.put("friendId", initiativeId);
        map.put("addTime", Commonparam.Date2Str());
        userManager.addInviteFriends(map);

        // 在系统消息表中，添加一条消息记录
        // HashMap
        // obtainUserInfo=countryManager.obtainUserInfo(Long.valueOf(obtainUserId));//获得用户昵称和图片
        String content = userBean.getNickname() + "[replace]";
        param.put("content", content);
        param.put("headPicture", param.get("picture"));
        param.put("addTime", Commonparam.Date2Str());


        param.put("initiativeId", initiativeId);
        param.put("type", NotificationListType.ACCEPT_FRIEND_INVITATION);
        countryManager.addNotificationMessage(param);// 保存上面的消息到推送表中

        baseBean.setStatus(200);
        baseBean.setRows(user);
        baseBean.setMsg("注册成功");

        return baseBean;
    }

    @Override
    public String obtainCode(String ab) throws ResultNotFoundException {
        HashMap param = new HashMap();
        param.put("ab", ab);
        String code = userManager.obtainCode(param);
        if (code == null) {
            throw new ResultNotFoundException();
        } else {
            return code;
        }
    }

    @Override
    public UserModel loginApp(String phone, String password) throws ResultNotFoundException {
        HashMap param = new HashMap();

        phone = phone.replace("+", "");
        param.put("uPhone", phone);
        param.put("uPassword", password);

        HashMap user = userManager.loginApp(param);
        if (user == null) {
            throw new ResultNotFoundException();
        } else {
//			user.put("uToken", UUID.randomUUID().toString());// 登录token值
            user.put("lastTime", Commonparam.Date2Str());// 登录时间
            userManager.recordLogin_version2(user);
            return userManager.selectUserByPrimaryKey((Long) user.get("userId"));
        }
    }

    @Override
    public UserModel loginThree(String threeId, int registerType) throws LoginFailureException {
        HashMap user = userManager.obtainUserByThreeId(threeId,
                registerType);
        if (user != null) {
            //user.put("uToken", UUID.randomUUID().toString());// 登录token值
            user.put("lastTime", Commonparam.Date2Str());// 登录时间
            userManager.recordLogin_version2(user);
            return userManager.selectUserByPrimaryKey((Long) user.get("userId"));
        } else {
            throw new LoginFailureException();
        }
    }

    @Override
    public void editPassword(String userId, String newPassword) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("newPassword", newPassword);
        systemManager.editPassword(param);
    }

    @Override
    public void updatePushToken(String pushToken, String userId) {
        HashMap param = new HashMap();
        param.put("pushToken", pushToken);
        param.put("userId", userId);
        userManager.updateUserPushToken(param);
    }

    @Override
    public BaseBean updateUserPassword(String phone, String code, String password) {
        BaseBean bean = new BaseBean();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("phone", phone.replace("+", "").trim());
        param.put("code", code);
        param.put("password", password);
        param.put("nowTime", Commonparam.spaceMinute(-10));
        userManager.updateUserPassword(bean, param);
        return bean;
    }

    @Override
    public void updateUserLanguage(String language, String userId) {
        HashMap param = new HashMap();
        param.put("language", language);
        param.put("userId", userId);
        userManager.updateUserLanguage(param);
    }

    @Override
    public void updateUserPicture(String qiniukey, String userId) {
        HashMap param = new HashMap();
        qiniukey = QiNiuUtils.getSaveDBUrl2(qiniukey);
        param.put("qiniukey", qiniukey);
        param.put("userId", userId);
        userManager.updateUserPicture(param);
    }

    @Override
    public String updateBgPicture(String qiniukey, String userId) {
        HashMap param = new HashMap();
        qiniukey = QiNiuUtils.getSaveDBUrl2(qiniukey);
        param.put("qiniukey", qiniukey);
        param.put("userId", userId);
        userManager.updateBackPicture(param);
        return qiniukey;
    }

    @Override
    public BaseBean updateUserAllAcountStatus(String userId, String uToken, String registerType, String password, String threeId) {
        BaseBean bean = new BaseBean();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("uToken", uToken);
        param.put("registerType", registerType);
        param.put("password", password);
        if (Commonparam.StringIsNotNull(threeId))
            param.put("threeId", threeId);
        else
            param.put("threeId", "");
        if (Commonparam.StringIsNotNull(password))
            param.put("password", password);
        else
            param.put("password", "");
        userManager.updateUserAllAcountStatus(bean, param);
        return bean;
    }

    @Override
    public void editPhone(String userId, String phone) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("phone", phone);
        systemManager.editPhone(param);
    }

    @Override
    public void forgetPassword(String phone) throws ResultNotFoundException {
        HashMap map = (HashMap) userManager.checkIsUser(phone);
        if (map == null) {
            throw new ResultNotFoundException();
        } else {
            String ph = map.get("u_phone").toString();
            String code = map.get("country_code").toString();
            String msgCode = "";
            final int mobile_code = (int) ((Math.random() * 9 + 1) * 1000);
            msgCode = mobile_code + "";
            //发送短信
            Sendmessage.sendVerificationCode(ph, code, mobile_code);
            map.clear();
            map.put("phone", ph);
            map.put("createTime", Commonparam.Date2Str());
            map.put("msgCode", msgCode);
            userManager.obtainVerificationCode(map);
        }
    }

    @Override
    public void setSystemNotification(NotificationBean notificationBean) {
        HashMap param = new HashMap();
        param.put("userId", notificationBean.getUserId());
        param.put("award", notificationBean.getAward());
        param.put("friendsNewNotes", notificationBean.getFriendsNewNotes());
        param.put("friendsNewTopic", notificationBean.getFriendsNewTopic());
        param.put("friendsReceiveMyInvitation", notificationBean.getFriendsReceiveMyInvitation());
        param.put("newActivity", notificationBean.getNewActivity());
        param.put("newFriendsInvitation", notificationBean.getNewFriendsInvitation());
        param.put("replyFriends", notificationBean.getReplyFriends());
        param.put("officialNotification", notificationBean.getOfficialNotification());
        param.put("pictureReceiveComments", notificationBean.getPictureReceiveComments());
        param.put("pictureReceivePraise", notificationBean.getPictureReceivePraise());
        param.put("pushChoice", notificationBean.getPushChoice());
        param.put("starPerson", notificationBean.getStarPerson());
        param.put("youPushChoice", notificationBean.getYouPushChoice());
        param.put("youStarPerson", notificationBean.getYouStarPerson());
        systemManager.setSystemNotification2(param);
    }

    @Override
    public void addSuggestion(String suggestion, String email, String userId) {
        HashMap map = new HashMap();
        map.put("suggestion", suggestion);
        map.put("email", email);
        map.put("userId", userId);
        systemManager.suggestion(map);
    }

    @Override
    public HashMap obtainSystemNotification(String userId) {
        return systemManager.obtainSystemNotification(userId);
    }

    @Override
    public HashMap obtainAccountBound(String userId) {
        return systemManager.accountBound(userId);
    }

    @Override
    public BaseBean setAccountBound(String userId, String facebook, String facebookName, String weixin, String weixinName, String sina, String sinaName, String phoneNum, String password, String uToken) {
        BaseBean bean = new BaseBean();
        bean.setStatus(200);
        HashMap param = new HashMap();
        param.put("userId", userId);
        if (Commonparam.StringIsNotNull(facebook)) {
            if (facebook.equals("0")) {
                param.put("facebook", "");
                param.put("facebookName", "");
            } else {
                HashMap userParamHashMap = new HashMap<String, Object>();
                userParamHashMap.put("registerType", 2);
                userParamHashMap.put("threeId", facebook);
                HashMap user = systemManager.obtainUser(userParamHashMap);
                if (user != null) {
                    bean.setStatus(400);
                    bean.setMsg("facebook账号已绑定其它用户！");
                } else {
                    param.put("facebook", facebook);
                    param.put("facebookName", facebookName);
                }
            }
        }
        if (Commonparam.StringIsNotNull(weixin)) {
            if (weixin.equals("0")) {
                param.put("weixin", "");
                param.put("weixinName", "");
            } else {
                HashMap userParamHashMap = new HashMap<String, Object>();
                userParamHashMap.put("registerType", 4);
                userParamHashMap.put("threeId", weixin);
                HashMap user = systemManager.obtainUser(userParamHashMap);
                if (user != null) {
                    bean.setStatus(400);
                    bean.setMsg("weixin账号已绑定其它用户！");
                } else {
                    param.put("weixin", weixin);
                    param.put("weixinName", weixinName);
                }
            }
        }
        if (Commonparam.StringIsNotNull(sina)) {
            if (sina.equals("0")) {
                param.put("sina", "");
                param.put("sinaName", "");
            } else {
                HashMap userParamHashMap = new HashMap<String, Object>();
                userParamHashMap.put("registerType", 3);
                userParamHashMap.put("threeId", sina);
                HashMap user = systemManager.obtainUser(userParamHashMap);
                if (user != null) {
                    bean.setStatus(400);
                    bean.setMsg("sina账号已绑定其它用户！");
                } else {
                    param.put("sina", sina);
                    param.put("sinaName", sinaName);
                }

            }
        }
        if (Commonparam.StringIsNotNull(phoneNum)) {
            if (phoneNum.equals("0")) {
                param.put("phoneNum", "");
            } else {
                HashMap userParamHashMap = new HashMap<String, Object>();
                userParamHashMap.put("registerType", 1);
                userParamHashMap.put("threeId", phoneNum);
                HashMap user = systemManager.obtainUser(userParamHashMap);
                if (user != null) {
                    bean.setStatus(400);
                    bean.setMsg("该手机号已绑定其它用户！");
                } else {
                    param.put("phoneNum", phoneNum);
                    param.put("password", password);
                }
            }
        }
        if (bean.getStatus() == 200) {
            systemManager.setAccountBound(param);
            bean.setStatus(200);
            bean.setMsg("设置成功");
        }
        return bean;
    }

    @Override
    public HashMap shareContent(String userId) {
        HashMap param = new HashMap();
        param.put("userId", userId);

        String invitationCode = systemManager.obtainInvitationCode(userId);// 获取邀请码
        HashMap shareContent = systemManager.shareContent(userId);// 获取分享的内容

        String content = (java.lang.String) shareContent.get("content");
        String simpleContent = (java.lang.String) shareContent.get("simpleContent");
        String traditionalContent = (java.lang.String) shareContent.get("traditionalContent");

        content = content.replace("[invitationCode]", invitationCode);// 替换之后发出去
        simpleContent = simpleContent.replace("[invitationCode]", invitationCode);// 替换之后发出去
        traditionalContent = traditionalContent.replace("[invitationCode]", invitationCode);// 替换之后发出去
        HashMap temp = new HashMap();
        temp.put("content", content);
        temp.put("simpleContent", simpleContent);
        temp.put("traditionalContent", traditionalContent);
        return temp;
    }

    @Override
    public HashMap saveMyInfo(String travelStyle, String userId, String nickName, String zodiac, String birthday, String areaName, String qiniukey, String language,String description) {
        HashMap param = new HashMap();
        if (Commonparam.StringIsNotNull(travelStyle)) {
            HashMap param1 = new HashMap();
            myCenterManager.deletetravelStyleId(userId);// 删除个人的旅行风格

            String[] ids = travelStyle.split(",");
            for (int i = 0; i < ids.length; i++) {
                param1.clear();
                param1.put("userId", userId);
                param1.put("travelStyleId", ids[i]);//
                //插入兴趣标签
                myCenterManager.insertTravelStyleId(param1);
            }
            if (ids.length > 0) {
                param.put("travelStyleId", ids[0]);
            }
        }
        param.put("userId", userId);
        if (nickName == null) {
        } else {
            param.put("nickName", nickName);// 昵称
        }
        if (birthday == null) {
        } else {
            param.put("birthday", birthday);//生日
        }
        if (zodiac == null) {
        } else {
            param.put("zodiac", zodiac);// 星座
        }

        if (language == null) {
        } else {
            param.put("language", language);// 语言
        }
        if (description == null) {
        } else {
            param.put("description", description);// 个人简介
        }
        if (areaName == null) {
        } else {
            param.put("areaName", areaName);// 区域名称
        }

        if (qiniukey == null||""==qiniukey) {
        } else {
            //bean1 = Commonparam.saveFile(filename, filenameFileName, 0);// 添加图片时候必须
            qiniukey = QiNiuUtils.getSaveDBUrl2(qiniukey);
            param.put("picture", qiniukey);// 头像路径
        }

        myCenterManager.saveMyProfile(param);// 保存个人信息
        HashMap  map=new HashMap();
        map=myCenterManager.findUserById(userId);
        List style = myCenterManager.obtainMyTravelStyle(userId);
        map.put("travelStyle", style);// 旅行风格列表
        return map;
    }

    @Override
    public HashMap obtainMyInfo(String userId) {
        HashMap map = new HashMap();
        //获取兴趣标签
        List travelStyle = travelManager.obtainTravelStyleById(userId);
        map.put("travelStyle", travelStyle);
        //获取其他信息
        HashMap param = userManager.obtainUserInfo(userId);
        map.put("language", param.get("language") == null ? 0 : param.get("language"));//语言
        map.put("description", param.get("description") == null ? "" : param.get("description"));//简介
        map.put("nickName", param.get("nickName") == null ? "" : param.get("nickName"));//昵称
        map.put("picture", param.get("picture") == null ? "" : param.get("picture"));//图片
        map.put("userType", param.get("userType") == null ? 1 : param.get("userType"));//用户类型
        //map.put("zodiacEn",param.get("z_name_en")==null?"":param.get("z_name_en"));//星座en
        //map.put("zodiacCn",param.get("z_name_cn")==null?"":param.get("z_name_cn"));//星座cn
        //map.put("zodiacTw",param.get("z_name_tw")==null?"":param.get("z_name_tw"));//星座tw
        map.put("birthday", param.get("birthday") == null ? "" : param.get("birthday"));//生日
        map.put("areaName", param.get("areaName") == null ? "" : param.get("areaName"));//地区

        return map;
    }

    @Override
    public List obtainSlidePicture() {
        return systemManager.obtainSlidePicture();
    }

    @Override
    public void emailRegister(String email,String sex,String applicationReasons)  {
       //生成验证码 插入验证码表中  已使用  插入到用户与验证码表中  邮箱未发送
       HashMap param=new HashMap();
       param.put("email",email);
       param.put("sex",sex);
       param.put("applicationReasons",applicationReasons);
       String invitationCode;
       // 当校验数据库中是否有重复的邀请码，有的话再随机生成一个。
       Integer codeCount;
       do {
            invitationCode = Commonparam.createSixRandomNumber();
            codeCount =Integer.valueOf(userManager.checkEmailCodeCount(invitationCode).toString());
           } while (codeCount > 0);
        param.put("invitationCode",invitationCode.toUpperCase());

        SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        String newtime = sdf.format( new Date());
        param.put("newtime",newtime);
        userManager.insertInvitationCode(param);
    }

    @Override
    public BaseBean registerUser_version2(UserBean userBean) {
        BaseBean baseBean = new BaseBean();

        HashMap param = new HashMap();
        HashMap param1 = new HashMap();
        if (userBean.getRegisterType() == 1) {// 手机注册
            //国码补救
            if(null==userBean.getCountryCode()){
                if(null!=userBean.getThreeId()){
                    if(userBean.getThreeId().indexOf("+")!=-1){
                        userBean.setCountryCode(userBean.getThreeId().replace("+",""));
                    }else{
                        userBean.setCountryCode(userBean.getThreeId());
                    }
                }else{
                    userBean.setCountryCode(userBean.getThreeId());
                }
            }
            Integer obtainPhoneCount = userManager.obtainPhoneCount(userBean.getPhone());// 获得手机号码是否唯一
            if (obtainPhoneCount > 0) {
                baseBean.setStatus(401);
                baseBean.setMsg("该手机号码已经注册过");

                return baseBean;
            }
            param.put("threeId", "");
        } else {//1手机注册2facebook3sina4微信

            HashMap user = userManager.obtainUserByThreeId(userBean.getThreeId(), userBean.getRegisterType());
            if (user != null) {
                //user.put("uToken", UUID.randomUUID().toString());// 登录token值
                user.put("lastTime", Commonparam.Date2Str());// 登录时间
                userManager.recordLogin_version2(user);

                baseBean.setStatus(200);
                baseBean.setRows(user);

                return baseBean;
            }
            param.put("threeId", userBean.getThreeId());
        }
        param.put("registerType", userBean.getRegisterType());

        // 生成自己的邀请码
        String invitationCode;
        // 当校验数据库中是否有重复的邀请码，有的话再随机生成一个。
        Integer codeCount;

        do {
            invitationCode = Commonparam.createRandomUpper();
            codeCount = userManager.checkCodeCount(invitationCode);
        }
        while (codeCount > 0);


        param.put("invitationCode", invitationCode);// 自己的邀请码
        param.put("phone", userBean.getPhone() == null ? "" : userBean.getPhone());
        param.put("password", userBean.getPassword());

        //传入qiniuKey
        if(StringUtils.isBlank(userBean.getQiniukey())&&StringUtils.isBlank(userBean.getHeadImgUrl())){
              param.put("picture", "http://7xlamk.com5.z0.glb.qiniucdn.com/addHead%403x.png");
        }else{
            if (StringUtils.isBlank(userBean.getQiniukey())) {   //使用第三方头像
                param.put("picture", userBean.getHeadImgUrl());   //第三方头像地址
            } else {
                param.put("picture", QiNiuUtils.getSaveDBUrl2(userBean.getQiniukey()));    //七牛key值

            }
        }


        param.put("nickname", EmojiFilter.filterEmoji(userBean.getNickname()));

        param.put("code", userBean.getCode().toUpperCase());// 被邀请人号码
        if (userBean.getCountryCode() == null) {
            param.put("countryCode", "");// 被邀请人国家代码
        } else {
            param.put("countryCode", userBean.getCountryCode());// 被邀请人国家代码
        }
        param.put("registerTime", Commonparam.Date2Str());
        String token = Commonparam.createRandomNumber() + Commonparam.createRandomNumber() + Commonparam.createRandomNumber();
        param.put("token", token);
        param.put("language",userBean.getLanguage());
        // 注册完成
        userManager.register(param);

        HashMap user = null;
        //is_nomember=1
        if (userBean.getRegisterType() == 1) {
            user = userManager.obtainUserId(userBean.getPhone());
        } else {
            user = userManager.obtainUserByThreeId(userBean.getThreeId(), userBean.getRegisterType());
        }
        Long userId = (Long) user.get("userId");
        HashMap notification = new HashMap();
        notification.put("initiativeId", userId);
        notification.put("userId", userId);
        notification.put("addTime", Commonparam.Date2Str());
        notification.put("type", NotificationListType.NEW_USER);
        countryManager.addNotificationMessage(notification);

        user.put("uToken", UUID.randomUUID().toString());// 登录token值
        user.put("lastTime", Commonparam.Date2Str());// 登录时间
        userManager.recordLogin_version2(user);
        userManager.insertSysNotifySetting(user);

        List<TravelStyleModel> tsList = new ArrayList<TravelStyleModel>();

        String[] ids = userBean.getTravelStyle().split(",");
        for (int i = 0; i < ids.length; i++) {
            param1.clear();
            param1.put("userId", user.get("userId"));
            param1.put("travelStyleId", ids[i]);
            tsList.add(travelStyleService.getModelByPK(Long.valueOf(ids[i])));
            userManager.updatetravelStyle(param1);
        }

        user.put("travelStyleList", tsList);

        param.put("userId", user.get("userId"));
        // 在好友推荐表中信息也需要修改
        if (userBean.getRegisterType() == 1) {//手机用户
            userManager.isnoMemeberContentUpdatePhone(param);
        } else {//第三方用户
            userManager.isnoMemeberContentUpdateThree(param);
        }
        // 先判断是五位邀请码 还是六位邀请码  6位的是没有邀请人  五位的有邀请人 找到是谁的邀请码，保存到消息表中
        if(userBean.getCode().length()==5){
            Long initiativeId = countryManager.obtainInitiativeIdByCode(userBean.getCode());// 获取主动的ID
            //添加官方账号并删除推荐表
            userManager.addOfficialFriends(user.get("userId").toString());
            //添加邀请人并删除推荐表
            HashMap map = new HashMap();
            map.put("userId", user.get("userId"));
            map.put("friendId", initiativeId);
            map.put("addTime", Commonparam.Date2Str());
            userManager.addInviteFriends(map);

            // 在系统消息表中，添加一条消息记录
            // HashMap
            // obtainUserInfo=countryManager.obtainUserInfo(Long.valueOf(obtainUserId));//获得用户昵称和图片
            String content = userBean.getNickname() + "[replace]";
            param.put("content", content);
            param.put("headPicture", param.get("picture"));
            param.put("addTime", Commonparam.Date2Str());


            param.put("initiativeId", initiativeId);
            param.put("type", NotificationListType.ACCEPT_FRIEND_INVITATION);
            countryManager.addNotificationMessage(param);// 保存上面的消息到推送表中
        }else if(userBean.getCode().length()==6){
            //添加官方账号并删除推荐表
            userManager.addOfficialFriends(user.get("userId").toString());
        }


        baseBean.setStatus(200);
        baseBean.setRows(user);
        baseBean.setMsg("注册成功");

        return baseBean;
    }
}
