package com.yoloboo.dao;

import com.yoloboo.models.CountryModel;

import java.util.HashMap;
import java.util.List;


public interface CountryManager {

	CountryModel getModelByPK(Long id);

	List obtainCountry();//获得国家列表
	
	List obtainCountryList(HashMap param);//获得首页国家列表
	
	Integer obtainCountryListCount(HashMap param);//获得首页国家列表数量
	
	List obtainOneCountryTipsList(HashMap param);//获取某个国家下面的贴士列表

	List obtainOneCountryTipsList1(HashMap param);//获取某个国家下面的贴士列表 初始化
	
	HashMap obtainCoverPicture(Long travelNotesId);//获取封面
	
	HashMap obtainUserInfo(Long userId);//获取用户信息
	
	HashMap obtainTipsInfoPic(Long tipsId);//获取该贴士的图片

	HashMap obtainActivityInfoPic(Long apId);//获取该活动的图片
	
	List obtainStyleList(Long userId);//获取旅游风格
	
	Integer obtainOneCountryTipsCount(HashMap param);//获取某个国家下面的贴士列表 总数

	HashMap obtainNotesInfoToTips(String travelNotesId);//获取游记的某些信息
	
	List obtainOneNotesTipsList(HashMap param);//获取游记下面的贴士	
	
	Integer obtainOneNotesTipsCount(HashMap param);//获取某个游记下面的贴士数量
	
	Integer obtainOneNotesPraiseCount(HashMap param); //获取某个游记下面的点赞数量
	
    List obtainTipsCommentsList(HashMap param);//获取贴士下面的评论	
	
	Integer obtainTipsCommentsCount(HashMap param);//获取贴士下面的评论 总数量
	
	void addTipsComments(HashMap param);//增加贴士评论
	
	void addTipsPraise(HashMap param);//增加贴士点赞

	void cancelTipsPraise(HashMap param);//取消贴士点赞
	
	String obtainInitiativeId(String tipsId);//获取主动的ID

	String obtainActivityInitiativeId(Long apId);//获取主动的ID即活动照片的上传者
	
	Long obtainInitiativeIdByCode(String code);//根据验证码获取主动的ID
	
	void addNotificationMessage(HashMap param);//保存上面的消息到推送表中

	void addActivityNotificationMessage(HashMap param);//保存活动照片上面的消息到推送表中
	 
	String obtainCountryName(String countryId);//获得首页国家名称
	
	String obtainCountrySimpleName(String countryId);//获得首页国家名称
	
	String obtainCountryTraditionalName(String countryId);//获得首页国家名称

	Object findNoteMoodList(String string);
	
	Object findNotePraiseList(String string);

	Object findNoteStyleList(String string);
	
	void updateRecommendFriendsList();//更新
	
	void updateRecommendFriendsListNum();//
	
	
	void updateFriendsFriends();//更新好友的好友

	List obtainCountry(HashMap param); //获取首页所有国家的列表

	Integer obtainOneCountryNotesDays(Long travelNotesId);

	Integer obtainOneNotesTipsNumber(Long travelNotesId);

	List obtainOneNotesDays(Long teavelNotesId);

	void addTipsComments2(HashMap param);

	HashMap obtainC_userId(HashMap param);

	HashMap obtainCountryById(HashMap param);


	HashMap obtainTitleById(HashMap param);

	HashMap obtainPicComment(HashMap param);

	HashMap obtainCommentNum(HashMap oneMap);

	HashMap obtainActivityCommentNum(HashMap oneMap);

	HashMap obtainPraiseNum(HashMap oneMap);

	HashMap obtainActivityPraiseNum(HashMap oneMap);

	Integer judgeIsExistReconmmend(HashMap param);

	HashMap obtainNotesDelete(HashMap oneMap);

	void insertManage();

	HashMap obtainPicInfo(Long aLong);

	String obtainCountryIdByName(String input);

	Integer canPublishOrNot(String travelNoteId);

	String getDescription(String travelNoteId);

	HashMap obtainNoteLocationInfo(String travelNoteId);

	HashMap obtainNoteThemeInfo(String travelNoteId);

	Long isYouOrRecent(String notificationListId);

	void deleteActivityPicturePraiseNotification(HashMap param);
}
