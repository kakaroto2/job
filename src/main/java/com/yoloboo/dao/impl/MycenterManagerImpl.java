package com.yoloboo.dao.impl;

import java.util.HashMap;
import java.util.List;







import org.springframework.stereotype.Repository;


import com.yoloboo.dao.MyCenterManager;


@Repository
public class MycenterManagerImpl extends BaseDao implements MyCenterManager {
	//获得旅行风格
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainMyTravelStyle(String userId) {
		return (List) sqlSession.selectList("mycenter.obtainTravelStyle", userId);
	}

	
	// 获得游记数
	@SuppressWarnings("rawtypes")
	@Override
	public Long obtainMyTravelNotesNum(String userId) {
		return (Long) sqlSession.selectOne("mycenter.obtainMyTravelNotesNum", userId);
	}
 
	//去过国家数量
	@SuppressWarnings("rawtypes")
	@Override
	public Long obtainMyCountryNum(String userId) {
		return (Long) sqlSession.selectOne("mycenter.obtainMyCountryNum", userId);
	}
 
	//游记列表
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainMyNotesList(HashMap param) {
		return (List) sqlSession.selectList("mycenter.obtainMyNotesList", param);
	}

	//游记列表
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainMyNotesWithAnotherList(HashMap param) {
		return (List) sqlSession.selectList("mycenter.obtainMyNotesWithAnotherList", param);
	}
	
	//获取语言等个人信息
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap obtainlanguageCountryBirthday(String userId) {
		return (HashMap) sqlSession.selectOne("mycenter.obtainlanguageCountryBirthday", userId);
	}
	
 
	
	//旅行风格插入
	@SuppressWarnings("rawtypes")
	@Override
	public void insertTravelStyleId(HashMap param1) {
		 sqlSession.insert("mycenter.insertTravelStyleId", param1);
	}
	
	//旅行风格删除
	@SuppressWarnings("rawtypes")
	@Override
	public void deletetravelStyleId(String userId) {
		 sqlSession.delete("mycenter.deletetravelStyleId", userId);
	}
 
	
	//保存个人信息
	@SuppressWarnings("rawtypes")
	@Override
	public void saveMyInfo(HashMap param) {
		 sqlSession.update("mycenter.saveMyInfo", param);
		 //sqlSession.update("mycenter.updateuserUpicture",param);
	}
	 
	//获取好友列表
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainMyFriends(HashMap param) {
		return (List)sqlSession.selectList("mycenter.obtainMyFriends", param);
	}
	
	//获取好友列表总数
	@SuppressWarnings("rawtypes")
	@Override
	public Long obtainMyFriendsCount(HashMap param) {
		return (Long)sqlSession.selectOne("mycenter.obtainMyFriendsCount", param);
	}
	
	//获取通知列表
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainNotificationList(HashMap param) {
		return (List)sqlSession.selectList("mycenter.obtainNotificationList", param);
	}
	
	//获取好友通知列表总数
	@SuppressWarnings("rawtypes")
	@Override
	public Long obtainNotificationListCount(HashMap param) {
		return (Long)sqlSession.selectOne("mycenter.obtainNotificationListCount", param);
	}

	@Override
	public List<HashMap> obtainRecommendFriends(HashMap param) {
		// 推荐的好友
		return (List)sqlSession.selectList("mycenter.obtainRecommendFriends", param);
	}

	//获取好友推荐列表总数
	@SuppressWarnings("rawtypes")
	@Override
	public Long obtainRecommendFriendsCount(HashMap param) {
		return (Long)sqlSession.selectOne("mycenter.obtainRecommendFriendsCount", param);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public HashMap findUserById(String userId) {
		return  (HashMap)sqlSession.selectOne("User.findUserById", userId);
	}
	
	
	@Override
	public List<HashMap> obtainRecommendFriendsYoloboo(HashMap param) {
		return (List)sqlSession.selectList("mycenter.obtainRecommendFriendsYoloboo", param);
	}
	
	@Override
	public Integer judgeRecommendFriendsRecommend(HashMap param) {
		String RecommendId=(String)sqlSession.selectOne("mycenter.judgeRecommendFriendsRecommendId",param);
		param.put("RecommendId", RecommendId);
		return (Integer)sqlSession.selectOne("mycenter.judgeRecommendFriendsRecommend", param);
	}
	
	@Override
	public void deleteRecommendFriendsRecommend(HashMap param) {
		String RecommendId=(String)sqlSession.selectOne("mycenter.judgeRecommendFriendsRecommendId",param);
		param.put("RecommendId", RecommendId);
		 sqlSession.delete("mycenter.deleteRecommendFriendsRecommend", param);
	}
	 
	
	@Override
	public HashMap obtainUserPicture(String userId) {
		// TODO Auto-generated method stub
		return (HashMap) sqlSession.selectOne("mycenter.obtainUserPicture",userId);
	}

	@Override
	public void flushFriends(HashMap param) {
		// TODO Auto-generated method stub
		sqlSession.update("mycenter.flushFriends",param);
	}

	@Override
	public Integer judgeFriendsisExsit1(HashMap param) {
			return (Integer) sqlSession.selectOne("mycenter.judgeFriendsisExsit1", param);

	}

	@Override
	public List<HashMap> obtanRecentList(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("mycenter.obtanRecentList", param);
	}

	@Override
	public List<HashMap> obtanActivityRecentList(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("mycenter.obtanActivityRecentList", param);
	}

	@Override
	public List<HashMap> obtainType1List(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("mycenter.obtainType1List",param);
	}

	@Override
	public List<HashMap> obtainType2List(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("mycenter.obtainType2List",param);
	}

	@Override
	public List<HashMap> obtainYouList(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("mycenter.obtanYouList", param);
	}

	@Override
	public List<HashMap> obtainActivityYouList(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("mycenter.obtanActivityYouList", param);
	}

	@Override
	public HashMap obtainPicComment(HashMap oneMap) {
		return (HashMap) sqlSession.selectOne("travel.obtainPicComment", oneMap);
	}

	@Override
	public HashMap obtainActivityPicComment(HashMap oneMap) {
		return (HashMap) sqlSession.selectOne("travel.obtainActivityPicComment", oneMap);
	}

	@Override
	public HashMap obtainUserIdByThird(HashMap map) {
		return (HashMap) sqlSession.selectOne("mycenter.obtainUserIdByThird",map);
	}

	@Override
	public void updateReadStatus(HashMap param) {
		sqlSession.update("mycenter.updateReadStatus",param);
	}

	@Override
	public HashMap isRecentNewNotify(HashMap param) {
		return (HashMap) sqlSession.selectOne("mycenter.isRecentNewNotify",param);
	}

	@Override
	public HashMap isYouNewNotify(HashMap param) {
		return (HashMap) sqlSession.selectOne("mycenter.isYouNewNotify",param);
	}

	@Override
	public String obtainUserIdBy(HashMap map1) {
		return (String) sqlSession.selectOne("mycenter.obtainUserIdBy",map1);
	}

	@Override
	public void updateCommentReadStatus(HashMap param) {
		sqlSession.update("mycenter.updateCommentReadStatus",param);
	}

	@Override
	public List<HashMap> obtainReplyComment(HashMap oneMap) {
		return (List<HashMap>) sqlSession.selectList("travel.obtainReplyComment",oneMap);
	}

	@Override
	public void updateRefreshTime(HashMap param) {
		List<HashMap> obtainList=null;
		if (param.get("notifiType").equals("0")){
			obtainList = (List<HashMap>) sqlSession.selectList("mycenter.obtainRecentListALL", param);
		}else if (param.get("notifiType").equals("1")){
			obtainList= (List<HashMap>) sqlSession.selectList("mycenter.obtainYouListAll",param);
		}
		for (HashMap map:obtainList){
			System.out.println(param.get("refresh_time"));
			map.put("refresh_time", param.get("refresh_time"));
			sqlSession.update("mycenter.updateRefreshTime",map);
		}
	}

	@Override
	public HashMap isRecentNewNotify1(HashMap param) {
		return (HashMap) sqlSession.selectOne("mycenter.isRecentNewNotify1",param);
	}

	@Override
	public HashMap isYouNewNotify1(HashMap param) {
		return (HashMap) sqlSession.selectOne("mycenter.isYouNewNotify1",param);
	}

	@Override
	public void reportUser(HashMap map) {
		sqlSession.insert("mycenter.reportUser",map);
	}

	@Override
	public List<HashMap> obtainReportContent(int language) {
		return (List<HashMap>) sqlSession.selectList("mycenter.selectReportType",language);
	}


	/*#######################2.0新增######################*/
	@SuppressWarnings("rawtypes")
	@Override
	public void saveMyProfile(HashMap param) {
		sqlSession.update("User.updateUserInfo",param);
	}
}
