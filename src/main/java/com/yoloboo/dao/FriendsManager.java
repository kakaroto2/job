package com.yoloboo.dao;

import java.util.HashMap;
import java.util.List;




import com.json.BaseBean;


public interface FriendsManager {

	void ignoreFriends(HashMap param); //忽略好友
	
	Integer judgeFriends(HashMap param); //判断是否已经是好友
	
	
	Integer juageFriendsRecommend1(HashMap param); //判断是否在推荐表中 主动至被动
	
	void updateFriendsRecommend1(HashMap param); //更新推荐表
	
	void insertFriendsRecommend1(HashMap param); //插入推荐表
	
    Integer juageFriendsRecommend2(HashMap param); //判断是否在推荐表中被动至主动
	
	void updateFriendsRecommend2(HashMap param); //更新推荐表
	
	void insertFriendsRecommend2(HashMap param); //插入推荐表
	
	
	void addFriends(HashMap param); //在好友中添加
	
	void addedDeleteFriends(HashMap param); //在推荐的好友中删除
	
	void addedDeleteFriendsSingle(HashMap param); 
	
	Integer judgeWeiboFriends(HashMap param);//判断数据中是否已经存在
	
	String isnoMember(HashMap param);//根据第三方ID查是否是会员
	
	void addWeiboFriends(HashMap param);//增加该微博ID
	
	String isnoMemberFacebook(HashMap param);//根据facebook第三方ID查是否是会员
	
	String isnoMemberAddress(HashMap param);//根据通讯录第三方ID查是否是会员

	void deleteUserFried(HashMap param);
	
	Integer isnogovUserFried(HashMap param);
	
	String obtainYolobooId();//获取yolobooId
	
	String obtainRecommendPeopleId(String userId);//根据推荐人ID

	void deleteReconmendFriend(HashMap param);
	/*#######################2.0新增#############################*/
	Integer isOfficialAccount(String friendUserId);

	List<HashMap> obtainFriendsIdList(String userId);

	Integer judgeIsFriends(HashMap param1);

	void deleteReconmendFriendById(HashMap param1);

	List obtainMyFriends(HashMap param);

	Long obtainMyFriendsCount(HashMap param);

	List<HashMap> obtainMyRecommendFriends(HashMap map);

	Integer judgeU_ASK_PASSIVE(HashMap param);

	Integer judgePASSIVE_ASK_U(HashMap param);

}
