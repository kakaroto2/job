package com.yoloboo.dao.impl;

import java.util.HashMap;
import java.util.List;



import org.springframework.stereotype.Repository;

import com.yoloboo.dao.FriendsManager;


@Repository
public class FriendsManagerImpl extends BaseDao implements FriendsManager {

	// 忽略好友
	@SuppressWarnings("rawtypes")
	@Override
	public void ignoreFriends(HashMap param) {
		sqlSession.update("friends.ignoreFriends", param);
	}

	// 判断是否好友
	@SuppressWarnings("rawtypes")
	@Override
	public Integer judgeFriends(HashMap param) {
		return (Integer) sqlSession.selectOne("friends.judgeFriends", param);
	}

	// 添加好友
	@SuppressWarnings("rawtypes")
	@Override
	public void addFriends(HashMap param) {
		Integer num1=(Integer) sqlSession.selectOne("friends.judgeFriends", param);
		if(num1==0){
		 sqlSession.insert("friends.addFriends", param);//主动至被动 status 0
		}
		
		Integer num=(Integer) sqlSession.selectOne("friends.judgeFriends2", param);
		if(num==0){
			sqlSession.insert("friends.addFriends2", param);//主动至被动 status 1
		}
		
	}

	// 已经添加好友删除推荐的
	@SuppressWarnings("rawtypes")
	@Override
	public void addedDeleteFriends(HashMap param) {
		sqlSession.update("friends.addedDeleteFriends", param);
		sqlSession.update("friends.addedDeleteFriends2", param);
		
	}
	@Override
	public void addedDeleteFriendsSingle(HashMap param) {
		sqlSession.delete("friends.addedDeleteFriends", param);
		
	}
	// 判断weiboId数据是否存在
	@SuppressWarnings("rawtypes")
	@Override
	public Integer judgeWeiboFriends(HashMap param) {
		return (Integer) sqlSession.selectOne("friends.judgeWeiboFriends",
				param);
	}

	// 看该人是否是会员
	@SuppressWarnings("rawtypes")
	@Override
	public String isnoMember(HashMap param) {
		return (String) sqlSession.selectOne("friends.isnoMember", param);
	}

	// 增加该微博ID
	@SuppressWarnings("rawtypes")
	@Override
	public void addWeiboFriends(HashMap param) {
		sqlSession.insert("friends.addWeiboFriends", param);
	}

	// 根据facebokk看该人是否是会员
	@SuppressWarnings("rawtypes")
	@Override
	public String isnoMemberFacebook(HashMap param) {
		return (String) sqlSession.selectOne("friends.isnoMemberFacebook",
				param);
	}

	
	// 是否是官方
	@SuppressWarnings("rawtypes")
	@Override
	public Integer isnogovUserFried(HashMap param) {
		//
		Integer isnogov;
		isnogov= (Integer) sqlSession.selectOne("friends.isnogovUserFried",param);
//	    isnogov2= (Integer) sqlSession.selectOne("friends.isnogovUserFried2",param);
//		isnogov3= (Integer) sqlSession.selectOne("friends.isnogovUserFried3",param);

		return isnogov;
	}

	
	
	// 根据通讯录看该人是否是会员
	@SuppressWarnings("rawtypes")
	@Override
	public String isnoMemberAddress(HashMap param) {
		return (String) sqlSession
				.selectOne("friends.isnoMemberAddress", param);
	}
    //删除好友
	@Override
	public void deleteUserFried(HashMap param) {
		// TODO Auto-generated method stub
		sqlSession.update("friends.deleteUserFried",param);//删除对方和自己
		sqlSession.update("friends.deleteUserFried2",param);//删除主动和被动
	}
	
 
	
	 
	//更新推荐表 主动至被动
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateFriendsRecommend1(HashMap param) {
		sqlSession.update("friends.updateFriendsRecommend1", param);
	}
 
	//更新推荐表 被动至主动
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateFriendsRecommend2(HashMap param) {
		sqlSession.update("friends.updateFriendsRecommend2", param);
	}
	
	
	//判断是否在推荐表中 主动至被动
	@SuppressWarnings("rawtypes")
	@Override
	public Integer juageFriendsRecommend1(HashMap param) {
		return (Integer) sqlSession.selectOne("friends.juageFriendsRecommend1",param);
	   
	}
	
	//判断是否在推荐表中被动至主动
	@SuppressWarnings("rawtypes")
	@Override
	public Integer juageFriendsRecommend2(HashMap param) {
		return (Integer) sqlSession.selectOne("friends.juageFriendsRecommend2",param);
	   
	}
	
	//插入推荐表 主动至被动
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void insertFriendsRecommend1(HashMap param) {
		sqlSession.insert("friends.insertFriendsRecommend1", param);
	}
 
	//插入推荐表 被动至主动
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void insertFriendsRecommend2(HashMap param) {
		sqlSession.insert("friends.insertFriendsRecommend2", param);
	}
	
	//获取yolobooId
	@SuppressWarnings("rawtypes")
	@Override
	public String obtainYolobooId() {
		return (String) sqlSession.selectOne("friends.obtainYolobooId");
	}
	
	//根据推荐人ID
	@SuppressWarnings("rawtypes")
	@Override
	public String obtainRecommendPeopleId(String userId) {
		return (String) sqlSession.selectOne("friends.obtainRecommendPeopleId",userId);
	}

	@Override
	public void deleteReconmendFriend(HashMap param) {
		//A-B解除好友关系
		String userId=param.get("userId").toString();
		System.out.println(userId);
		String friendUserId=param.get("friendUserId").toString();
		System.out.println(friendUserId);
		//获取A的好友有列表
		List<HashMap> userFriends= (List<HashMap>) sqlSession.selectList("friends.friendsUserList", userId);
		//获取B的好友列表
		List<HashMap> friendUserFriends= (List<HashMap>) sqlSession.selectList("friends.friendsUserList",friendUserId);
		HashMap param1=new HashMap();

		if (userFriends.size()>0) {
			for (HashMap map : userFriends) {
				//获取A的一个好友
				String uuserId = map.get("userId").toString();
				//获取该好友的好友列表
				int count = 0;
				List<HashMap> list1 = (List<HashMap>) sqlSession.selectList("friends.friendsUserList", uuserId);
				for (HashMap map1 : list1) {
					String uuuserId = map1.get("userId").toString();
					if (!uuuserId.equals(userId)) {
						param1.put("userId", uuuserId);
						param1.put("friendFriendId", friendUserId);
						//判断跟B是否是好友
						int isFriend = (Integer) sqlSession.selectOne("country.judgeFriendCountry", param1);
						if (isFriend != 0) {
							break;
						} else {
							count++;
						}
					}
				}
				//说明该好友中只有A跟B是好友
				if (count == list1.size()) {
					param1.put("userId", uuserId);
					param1.put("recommendId", friendUserId);
					sqlSession.update("friends.deleteRecommend", param1);
					sqlSession.update("friends.deleteRecommend2", param1);//删除推荐关系推荐表
				}
				param1.clear();
			}
		}


		if (friendUserFriends.size()>0) {
			for (HashMap map : friendUserFriends) {
				//获取B的一个好友
				String uuserId = map.get("userId").toString();
				//获取该好友的好友
				int count = 0;
				List<HashMap> list1 = (List<HashMap>) sqlSession.selectList("friends.friendsUserList", uuserId);
				for (HashMap map1 : list1) {
					String uuuserId = map1.get("userId").toString();

					if (!uuuserId.equals(friendUserId)) {
						param1.put("userId", uuuserId);
						param1.put("friendFriendId", userId);
						//判断跟A是否是好友
						int isFriend = (Integer) sqlSession.selectOne("country.judgeFriendCountry", param1);
						if (isFriend != 0) {
							break;
						} else {
							count++;
						}
					}else{
						count++;
					}
				}
				//说明该好友中只有B跟A是好友
				if (count == list1.size()) {
					param1.put("userId", uuserId);
					param1.put("recommendId", userId);
					sqlSession.update("friends.deleteRecommend", param1);
					sqlSession.update("friends.deleteRecommend2", param1);//删除推荐关系推荐表
				}
				param1.clear();
			}
		}
	}
/*########2.0新增#######*/

	@Override
	public Integer isOfficialAccount(String friendUserId) {
		return (Integer) sqlSession.selectOne("User.judgeIsOfficalAccount", friendUserId);
	}

	@Override
	public List<HashMap> obtainFriendsIdList(String userId) {
		return (List<HashMap>) sqlSession.selectList("friends.selectFriendsIdList",userId);
	}

	@Override
	public Integer judgeIsFriends(HashMap param1) {
		return (Integer) sqlSession.selectOne("friends.judgeIsFriends",param1);
	}

	@Override
	public void deleteReconmendFriendById(HashMap param1) {
		sqlSession.update("friends.deleteRecommnedFriendById",param1);
		sqlSession.update("friends.deleteRecommnedFriendByPassiveId",param1);
	}

	@Override
	public List obtainMyFriends(HashMap param) {
		return sqlSession.selectList("friends.selectFriendsById",param);
	}

	@Override
	public Long obtainMyFriendsCount(HashMap param) {
		return (Long) sqlSession.selectOne("friends.obtainMyFriendsCount",param);
	}

	@Override
	public List<HashMap> obtainMyRecommendFriends(HashMap map) {
		return (List<HashMap>) sqlSession.selectList("friends.obtainRecommendFriends",map);
	}

	@Override
	public Integer judgeU_ASK_PASSIVE(HashMap param) {
		return (Integer) sqlSession.selectOne("friends.judgeU_ASK_PASSIVE",param);
	}

	@Override
	public Integer judgePASSIVE_ASK_U(HashMap param) {
		return (Integer) sqlSession.selectOne("friends.judgePASSIVE_ASK_U",param);
	}


}
