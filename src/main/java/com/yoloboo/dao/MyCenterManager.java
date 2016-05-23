package com.yoloboo.dao;

import java.util.HashMap;
import java.util.List;





import com.json.BaseBean;


public interface MyCenterManager {


	List obtainMyTravelStyle(String userId);//获得旅行风格
	
	Long obtainMyTravelNotesNum(String userId);//游记数量
	
	Long obtainMyCountryNum(String userId);//去过国家数量
	
	List obtainMyNotesList(HashMap param);//游记列表

	List obtainMyNotesWithAnotherList(HashMap param);//游记列表
	
	HashMap obtainlanguageCountryBirthday(String userId);//获取语言等个人信息
	
	void insertTravelStyleId(HashMap param1);//插入旅行风格ID
	
	void deletetravelStyleId(String userId);//获取旅行风格ID
	
	void saveMyInfo(HashMap param);//保存个人信息
	
	List obtainMyFriends(HashMap param);//获取我的好友列表
 
	Long obtainMyFriendsCount(HashMap param);//获取我的好友列表总数
	
	List obtainNotificationList(HashMap param);//获取通知列表
	 
	Long obtainNotificationListCount(HashMap param);//获取通知列表总数

	List<HashMap> obtainRecommendFriends(HashMap param);//获取推荐好友
	
	Long obtainRecommendFriendsCount(HashMap param);//获取推荐好友总数
	

	HashMap findUserById(String userId);
	
	List<HashMap> obtainRecommendFriendsYoloboo(HashMap param);

	Integer judgeRecommendFriendsRecommend(HashMap param);
	
	void deleteRecommendFriendsRecommend(HashMap param);
	
	
	
	HashMap obtainUserPicture(String userId);

	void flushFriends(HashMap param);

	Integer judgeFriendsisExsit1(HashMap param);


	List<HashMap> obtanRecentList(HashMap param);

	List<HashMap> obtanActivityRecentList(HashMap param);

	List<HashMap> obtainType1List(HashMap param);

	List<HashMap> obtainType2List(HashMap param);

	List<HashMap> obtainYouList(HashMap param);

	List<HashMap> obtainActivityYouList(HashMap param);

	HashMap obtainPicComment(HashMap oneMap);

	HashMap obtainActivityPicComment(HashMap oneMap);

	HashMap obtainUserIdByThird(HashMap map);

	void updateReadStatus(HashMap obtainRecentList);

	HashMap isRecentNewNotify(HashMap param);

	HashMap isYouNewNotify(HashMap param);

	String obtainUserIdBy(HashMap map1);

	void updateCommentReadStatus(HashMap param);

	List<HashMap> obtainReplyComment(HashMap oneMap);

	//List<HashMap> obtanRecentListAll(HashMap param);

	void updateRefreshTime(HashMap param);

	HashMap isRecentNewNotify1(HashMap param);

	HashMap isYouNewNotify1(HashMap param);

	List<HashMap> obtainReportContent(int language);

	void reportUser(HashMap map);

	/*###############2.0新增##################*/
	void saveMyProfile(HashMap param);
}
