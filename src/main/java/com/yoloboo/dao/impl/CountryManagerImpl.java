package com.yoloboo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.yoloboo.models.CountryModel;
import org.springframework.stereotype.Repository;

import com.common.Commonparam;
import com.yoloboo.dao.CountryManager;

@Repository
public class CountryManagerImpl extends BaseDao implements CountryManager {

	@Override
	public CountryModel getModelByPK(Long id) {
		return (CountryModel) sqlSession.selectOne("country.getModelByPK",id);
	}

	// 获得国家列表
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainCountry() {
		return (List) sqlSession.selectList("country.obtainCountry");
	}
	
	// 获得国家列表
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainCountryList(HashMap param) {
		return (List) sqlSession.selectList("country.obtainCountryList",param);
	}
	
	//获得首页国家列表数量
	@SuppressWarnings("rawtypes")
	@Override
	public Integer obtainCountryListCount(HashMap param) {
		return (Integer) sqlSession.selectOne("country.obtainCountryListCount",param);
	}
 

	//获取某个国家下面的贴士列表
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainOneCountryTipsList(HashMap param) {
			return (List) sqlSession.selectList("country.obtainOneCountryTipsList",param);
		 
	}
	//获取某个国家下面的贴士列表  初始化
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainOneCountryTipsList1(HashMap param) {
			return (List) sqlSession.selectList("country.obtainOneCountryTipsList1",param);}
	
	//获取封面
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap obtainCoverPicture(Long travelNotesId) {
		HashMap hm  = (HashMap) sqlSession.selectOne("country.obtainCoverPicture",travelNotesId);
		if(hm.size() == 0){
			hm  = (HashMap) sqlSession.selectOne("country.obtainFakeCoverPicture",travelNotesId);
		}
		return hm;
	}
	
	//获取用户信息
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap obtainUserInfo(Long userId) {
		return (HashMap) sqlSession.selectOne("country.obtainUserInfo",userId);
	}
	
	//获取贴士的图片地址
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap obtainTipsInfoPic(Long userId) {
		return (HashMap) sqlSession.selectOne("country.obtainTipsInfoPic",userId);
	}

	//获取活动的图片地址
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap obtainActivityInfoPic(Long apId) {
		return (HashMap) sqlSession.selectOne("country.obtainActivityInfoPic",apId);
	}
	
	//获取旅游风格
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainStyleList(Long userId) {
		return (List) sqlSession.selectList("country.obtainStyleList",userId);
	}
	 
	//获取某个国家下面的贴士列表
	@SuppressWarnings("rawtypes")
	@Override
	public Integer obtainOneCountryTipsCount(HashMap param) {
		return (Integer) sqlSession.selectOne("country.obtainOneCountryTipsCount",param);
	}
	
	//获取某个贴士下面的游记
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainOneNotesTipsList(HashMap param) {
		return (List) sqlSession.selectList("country.obtainOneNotesTipsList",param);
	}
	
	
	//获取某个游记下面的贴士 总数
	@SuppressWarnings("rawtypes")
	@Override
	public Integer obtainOneNotesTipsCount(HashMap param) {
		return (Integer) sqlSession.selectOne("country.obtainOneNotesTipsCount",param);
	}
	//获取某个游记下的点赞总数
	@Override
	public Integer obtainOneNotesPraiseCount(HashMap param) {
		// TODO Auto-generated method stub
		return (Integer) sqlSession.selectOne("country.obtainOneNotesPraiseCount",param);
	}
	//获取游记的某些信息
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap obtainNotesInfoToTips(String travelNotesId) {
		return (HashMap) sqlSession.selectOne("country.obtainNotesInfoToTips",travelNotesId);
	} 
	 
	
	
	//获取贴士下面的评论
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainTipsCommentsList(HashMap param) {
		return (List) sqlSession.selectList("country.obtainTipsCommentsList",param);
	}
	
	//获取贴士下面的评论 总数量
	@SuppressWarnings("rawtypes")
	@Override
	public Integer obtainTipsCommentsCount(HashMap param) {
		return (Integer) sqlSession.selectOne("country.obtainTipsCommentsCount",param);
	}

	//增加贴士评论
	@SuppressWarnings("rawtypes")
	@Override
	public void addTipsComments(HashMap param) {
		 sqlSession.insert("country.addTipsComments",param);
		 param.put("commentNum", 1);
		 // 更新图片评论数量
		 sqlSession.update("country.updateTipsCommentsNum",param);
		 //更新游记评论数量
		 sqlSession.update("country.updateNotesCommentsNum",param);
	}
	@Override
	public void addTipsComments2(HashMap param) {
		// TODO Auto-generated method stub
		 sqlSession.insert("country.addTipsComments2",param);
		 param.put("commentNum", 1);
		 // 更新图片评论数量
		 sqlSession.update("country.updateTipsCommentsNum",param);
		 //更新游记评论数量
		 sqlSession.update("country.updateNotesCommentsNum",param);
	}
	//点赞
	@SuppressWarnings("rawtypes")
	@Override
	public void addTipsPraise(HashMap param) {
		 sqlSession.delete("country.addTipsPraise",param);
		 param.put("praiseNum", 1);//1点赞-1取消点赞
		 // 更新图片点赞数量
		 sqlSession.update("country.updateTipsPraiseNum",param);
		 //更新游记点赞数量
		 sqlSession.update("country.updateNotesPraiseNum",param);
	}
	//取消活动图片点赞
	@SuppressWarnings("rawtypes")
	@Override
	public void deleteActivityPicturePraiseNotification(HashMap param) {
		sqlSession.delete("country.deleteActivityPicturePraiseNotification",param);
	}
	//取消点赞
	@SuppressWarnings("rawtypes")
	@Override
	public void cancelTipsPraise(HashMap param) {
		sqlSession.insert("country.cancelTipsPraise",param);
		param.put("praiseNum", -1);//1点赞-1取消点赞
		// 更新图片点赞数量
		sqlSession.update("country.updateTipsPraiseNum",param);
		//更新游记点赞数量
		sqlSession.update("country.updateNotesPraiseNum",param);
	}
	 
	//获取主动ID
	@SuppressWarnings("rawtypes")
	@Override
	public String obtainInitiativeId(String tipsId) {
		return(String) sqlSession.selectOne("country.obtainInitiativeId",tipsId);
	}

	//获取主动ID
	@SuppressWarnings("rawtypes")
	@Override
	public String obtainActivityInitiativeId(Long apId) {
		return(String) sqlSession.selectOne("country.obtainActivityInitiativeId",apId);
	}
	 
	//根据验证码获取主动的ID
	@SuppressWarnings("rawtypes")
	@Override
	public Long obtainInitiativeIdByCode(String code) {
		Long userId = (Long) sqlSession.selectOne("country.obtainInitiativeIdByCode",code);
		return userId; 
	}
	
	//保存上面的消息到推送表中
	@SuppressWarnings("rawtypes")
	@Override
	public void addNotificationMessage(HashMap param) {
		 sqlSession.insert("country.addNotificationMessage",param);
	}

	//保存活动照片上面的消息到推送表中
	@SuppressWarnings("rawtypes")
	@Override
	public void addActivityNotificationMessage(HashMap param) {
		sqlSession.insert("country.addActivityNotificationMessage",param);
	}
	
	
	//获得首页国家名称
	@SuppressWarnings("rawtypes")
	@Override
	public String obtainCountryName(String countryId) {
		return(String) sqlSession.selectOne("country.obtainCountryName",countryId);
	}

	//获得首页国家名称 --中文简体
	@SuppressWarnings("rawtypes")
	@Override
	public String obtainCountrySimpleName(String countryId) {
		return(String) sqlSession.selectOne("country.obtainCountrySimpleName",countryId);
	}	
	
	//获得首页国家名称 --中文繁体
	@SuppressWarnings("rawtypes")
	@Override
	public String obtainCountryTraditionalName(String countryId) {
		return(String) sqlSession.selectOne("country.obtainCountryTraditionalName",countryId);
	}
	
	@Override
	public Object findNotePraiseList(String tipsId) {
		// TODO Auto-generated method stub
		return  sqlSession.selectList("country.findNotePraiseList", tipsId);
	}

	@Override
	public Object findNoteMoodList(String tipsId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("country.findNoteMoodList",tipsId);
	}

	@Override
	public Object findNoteStyleList(String tipsId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("country.findNoteStyleList",tipsId);
	}	
	
	//更新
	@Override
	public void updateRecommendFriendsList() {
		 sqlSession.update("country.updateRecommendFriendsListGang");
		 sqlSession.update("country.findNoteStyleListKongge");
		 sqlSession.update("country.findNoteStyleListJiahao");
	}	
	
	//更新
	@SuppressWarnings("unchecked")
	@Override
	public void updateRecommendFriendsListNum() {
		List<HashMap<String,Object>> userList = new ArrayList<HashMap<String,Object>>();
		userList=(List<HashMap<String, Object>>) sqlSession.selectList("country.obtainUserList");
		HashMap<String,Object> param = new HashMap<String,Object>();
		
		 
		 for(int i=0; i<userList.size();i++){
			 param.put("phone", userList.get(i).get("phone"));
			 param.put("userId", userList.get(i).get("userId"));
			 String codePhone=(String)userList.get(i).get("phone") + (String)userList.get(i).get("countryCode");
			 param.put("countryCode", codePhone);
			 
			 sqlSession.update("country.updateRecommendFriendsListNum",param);
		 }
	}	
	
	
	//更新好友的好友
	@SuppressWarnings("unchecked")
	@Override
	public void updateFriendsFriends() {
		List<HashMap<String,Object>> userList = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> friendList = new ArrayList<HashMap<String,Object>>();
		//获取所有用户
		userList=(List<HashMap<String, Object>>) sqlSession.selectList("country.obtainUserList");
		HashMap<String,Object> param = new HashMap<String,Object>();
		String nowtime = Commonparam.Date2Str();
		param.put("nowtime", nowtime);
		 for(int i=0; i<userList.size();i++){
			 //拿到一个userId
			 String userId= userList.get(i).get("userId").toString();
			 //获取该用户的好友列表
			 friendList=(List<HashMap<String, Object>>) sqlSession.selectList("country.obtainFriendListByRecommend",userId);
			 for(int j=0; j<friendList.size();j++){//循环
				 param.put("userId", userId);
				 
				 param.put("friendFriendId", friendList.get(j).get("friendFriendId"));
				//判断是否是好友
				 int isFriend =(Integer) sqlSession.selectOne("country.judgeFriendCountry",param);
				 if(isFriend==0){
					//判断推荐表中是否有关系
					 int isReconmmend =(Integer) sqlSession.selectOne("country.judgeReconmmendCountry",param);
					 if(isReconmmend==0){
						//增加一条数据
						 sqlSession.insert("country.insertRecommendFriendsFriends",param);
					 }
					 
				 }
			 }
			 
		 }
		
	 
	}

	@Override
	public List obtainCountry(HashMap param) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("country.obtainCountry", param);
	}

	@Override
	public Integer obtainOneCountryNotesDays(Long travelNotesId) {
		// TODO Auto-generated method stub
		return (Integer) sqlSession.selectOne("country.obtainOneCountryNotesDays", travelNotesId);
	}

	@Override
	public Integer obtainOneNotesTipsNumber(Long travelNotesId) {
		// TODO Auto-generated method stub
		return (Integer)sqlSession.selectOne("country.obtainOneNotesTipsNumber", travelNotesId);
	}

	@Override
	public List obtainOneNotesDays(Long teavelNotesId) {
		// TODO Auto-generated method stub
		
		
		return sqlSession.selectList("country.obtainOneNotesDays",teavelNotesId);
	}

	@Override
	public HashMap obtainC_userId(HashMap param) {
		// TODO Auto-generated method stub
		return (HashMap) sqlSession.selectOne("country.obtainC_userId", param);
	}

	@Override
	public HashMap obtainCountryById(HashMap param) {
		// TODO Auto-generated method stub
		return (HashMap) sqlSession.selectOne("country.obtainCountryById", param);
	}

	@Override
	public HashMap obtainTitleById(HashMap param) {
		return (HashMap) sqlSession.selectOne("travel.obtainTitleById",param);
	}

	@Override
	public HashMap obtainPicComment(HashMap param) {
		return (HashMap) sqlSession.selectOne("travel.obtainPicComment",param);
	}

	@Override
	public HashMap obtainCommentNum(HashMap param) {
		return (HashMap) sqlSession.selectOne("travel.obtainCommentNum", param);
	}

	@Override
	public HashMap obtainActivityCommentNum(HashMap param) {
		return (HashMap) sqlSession.selectOne("travel.obtainActivityCommentNum", param);
	}

	@Override
	public HashMap obtainPraiseNum(HashMap param) {
		return (HashMap) sqlSession.selectOne("travel.obtainPraiseNum", param);
	}


	@Override
	public HashMap obtainActivityPraiseNum(HashMap param) {
		return (HashMap) sqlSession.selectOne("travel.obtainActivityPraiseNum", param);
	}

	@Override
	public Integer judgeIsExistReconmmend(HashMap param) {
		return (Integer) sqlSession.selectOne("country.judgeIsExistReconmmend",param);
	}

	@Override
	public HashMap obtainNotesDelete(HashMap oneMap) {
		return (HashMap) sqlSession.selectOne("country.obtainNotesDelete",oneMap);
	}

	@Override
	public HashMap obtainPicInfo(Long aLong) {
		return (HashMap) sqlSession.selectOne("travel.obtainPictureInfo",aLong);
	}

	@Override
	public String obtainCountryIdByName(String input) {
		return (String) sqlSession.selectOne("country.obtainCountryIdByName",input);
	}

	@Override
	public Integer canPublishOrNot(String travelNoteId) {
		return (Integer) sqlSession.selectOne("travel.canPublishOrNot",travelNoteId);
	}

	@Override
	public String getDescription(String travelNoteId) {
		return (String) sqlSession.selectOne("travel.getDescription",travelNoteId);
	}

	@Override
	public HashMap obtainNoteLocationInfo(String travelNoteId) {
		return (HashMap) sqlSession.selectOne("travel.obtainNoteLocationInfo",travelNoteId);
	}

	@Override
	public HashMap obtainNoteThemeInfo(String travelNoteId) {
		return (HashMap) sqlSession.selectOne("travel.obtainNoteThemeInfo",travelNoteId);
	}

	@Override
	public Long isYouOrRecent(String notificationListId) {
		return (Long) sqlSession.selectOne("country.isYouOrRecent",notificationListId);
	}

	@Override
	public String getCode(Long notificationListId) {
		return (String) sqlSession.selectOne("country.getCode",notificationListId);
	}

	@Override
	public String getBeCode(Long notificationListId) {
		return (String) sqlSession.selectOne("country.getBeCode",notificationListId);
	}
}
