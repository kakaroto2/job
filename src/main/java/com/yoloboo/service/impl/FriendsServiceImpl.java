package com.yoloboo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.common.Commonparam;
import com.common.PhoneUtil;
import com.common.constans.NotificationListType;
import com.common.constans.RelationshipType;
import com.common.constans.SystemCodeContent;
import com.json.BaseBean;
import com.yoloboo.dao.CountryManager;
import com.yoloboo.dao.FriendsManager;
import com.yoloboo.dao.TravelManager;
import com.yoloboo.service.FriendsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import com.common.constans.RelationshipType;

/**
 * Created by ZHOU005 on 2015/12/28.
 */

@Service
@Transactional
public class FriendsServiceImpl implements FriendsService
{
	@Resource
	private FriendsManager friendsManager;
	@Resource
	private TravelManager travelManager;
	@Resource
	private CountryManager countryManager;

	@Override
	public BaseBean addFriends_version1(String passivePeopleId, String userId, String type) {
		BaseBean bean=new BaseBean();
		HashMap result = new HashMap();
		HashMap param = new HashMap();

		param.put("passivePeopleId", passivePeopleId);
		param.put("userId", userId);
		param.put("initiativeId", passivePeopleId);
		param.put("addTime", Commonparam.Date2Str());

		if (type.equals(RelationshipType.SYSTEM_RECOMMEND.toString()))
		{// 添加好友
			friendsManager.updateFriendsRecommend1(param);
			friendsManager.updateFriendsRecommend2(param);

			param.put("type", NotificationListType.RECEIVE_FRIEND_INVITATION);// 消息中是添加的

			result.put("nowType", RelationshipType.U_ASK_PASSIVE);
		}
		else if(type.equals(RelationshipType.NO_RELATIONSHIP.toString()))
		{
			friendsManager.insertFriendsRecommend1(param);
			friendsManager.insertFriendsRecommend2(param);

			param.put("type", NotificationListType.RECEIVE_FRIEND_INVITATION);// 消息中是添加的

			result.put("nowType", RelationshipType.U_ASK_PASSIVE);
		}
		else if (type.equals(RelationshipType.U_ASK_PASSIVE.toString()))// 接受好友
		{
			friendsManager.addFriends(param); // 在好友中添加
			friendsManager.addedDeleteFriends(param); // 在推荐的好友中删除

			param.put("type", NotificationListType.ACCEPT_FRIEND_INVITATION);// 消息中是接受的

			result.put("nowType", RelationshipType.IS_FRIEND);
		}
		else if (type.equals(RelationshipType.PASSIVE_ASK_U.toString()))// 主动添加别人
		{
//			friendsManager.addFriends(param); // 在好友中添加
//			friendsManager.addedDeleteFriends(param); // 在推荐的好友中删除
//
//			param.put("type", NotificationListType.ACCEPT_FRIEND_INVITATION);// 消息中是接受的
			// 判断主动方至被动方是否已经存在，存在修改状态，不存在就加一个
			Integer juageFriendsRecommend1 = friendsManager.juageFriendsRecommend1(param); // 判断
			if (juageFriendsRecommend1 == 1) {
				friendsManager.updateFriendsRecommend1(param);// 更新状态 status=0别人添加我
			} else {
				friendsManager.insertFriendsRecommend1(param);// 插入数据 status=1添加别人
			}
			// 判断被动方至主动是否已经存在，存在修改状态，不存在就加一个
			Integer juageFriendsRecommend2 = friendsManager.juageFriendsRecommend2(param); // 判断
			if (juageFriendsRecommend2 == 1) {
				friendsManager.updateFriendsRecommend2(param);// 更新状态
			} else {
				friendsManager.insertFriendsRecommend2(param);// 插入数据
			}

			param.put("type", NotificationListType.RECEIVE_FRIEND_INVITATION);// 消息中是添加的

			result.put("nowType", RelationshipType.U_ASK_PASSIVE);
		}

		HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片

		Integer t = (Integer)param.get("type");
		if(t == 3) {
			param.put("content", "[replace]" + obtainUserInfo.get("nickname"));
		}
		else{
			param.put("content", obtainUserInfo.get("nickname") + "[replace]");
		}
		param.put("headPicture", obtainUserInfo.get("headPicture"));
		param.put("addTime", Commonparam.Date2Str());



		countryManager.addNotificationMessage(param);// 保存上面的消息到推送表中
		bean.setRows(result);
		bean.setStatus(SystemCodeContent.SUCCESS_CODE);
		return bean;
	}

	@Override
	public BaseBean addListFriends_version1(String listFriendsId, String userId) {
		BaseBean bean=new BaseBean();
		HashMap param = new HashMap();

		JSONArray array = JSONArray.parseArray(listFriendsId);
		// 获取官方账号ID
		String yolobooId = friendsManager.obtainYolobooId();

		// 获取好友推荐
		String recommendPeopleId = friendsManager.obtainRecommendPeopleId(userId);

		if (array.size() > 0)
		{
			for (int i = 0; i < array.size(); i++)
			{
				param.clear();
				param.put("userId", userId);
				param.put("passivePeopleId", array.get(i));
				param.put("addTime", Commonparam.Date2Str());
				param.put("initiativeId", array.get(i));
				String linshi = array.get(i).toString();
				if (linshi.equals(yolobooId))
				{
					friendsManager.addFriends(param); // 在好友中添加
					friendsManager.addedDeleteFriends(param); // 在推荐的好友中删除
				}
				else if (linshi.equals(recommendPeopleId))
				{
					friendsManager.addFriends(param); // 在好友中添加
					friendsManager.addedDeleteFriends(param); // 在推荐的好友中删除
				}
				else
				{
					// 判断主动方至被动方是否已经存在，存在修改状态，不存在就加一个
					Integer juageFriendsRecommend1 = friendsManager.juageFriendsRecommend1(param); // 判断
					if (juageFriendsRecommend1 == 1)
					{
						friendsManager.updateFriendsRecommend1(param);// 更新状态
					}
					else
					{
						friendsManager.insertFriendsRecommend1(param);// 插入数据
					}
					// 判断被动方至主动是否已经存在，存在修改状态，不存在就加一个
					Integer juageFriendsRecommend2 = friendsManager.juageFriendsRecommend2(param); // 判断
					if (juageFriendsRecommend2 == 1)
					{
						friendsManager.updateFriendsRecommend2(param);// 更新状态
					}
					else
					{
						friendsManager.insertFriendsRecommend2(param);// 插入数据
					}

					HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片
					String content;
					content = "[replace]" + obtainUserInfo.get("nickname");
					param.put("content", content);
					param.put("picture", "");
					param.put("addTime", Commonparam.Date2Str());
					param.put("type", NotificationListType.RECEIVE_FRIEND_INVITATION);
					countryManager.addNotificationMessage(param);// 保存上面的消息到推送表中//增加消息
				}
			}
		}
		bean.setStatus(SystemCodeContent.SUCCESS_CODE);
		return bean;
	}

	@Override
	public BaseBean uploadKey_version1(String keyList, String userId, String type) {
		BaseBean bean=new BaseBean();
		HashMap param = new HashMap();
		JSONArray array = JSONArray.parseArray(keyList);
		if (array.size() > 0)
		{
			for (int i = 0; i < array.size(); i++)
			{
				param.clear();
				String s = array.get(i).toString();
				s = PhoneUtil.checkPhone(s);
				param.put("thirdId", s);// 第三方ID
				param.put("userId", userId);// 用户ID
				String addTime = Commonparam.Date2Str();
				param.put("addTime", addTime);// 增加的时间
				if (type.equals("0"))
				{// 微博
					param.put("type", 0);
					// 判断以前是否已经有，有就不用再加了

					Integer weiboInt = friendsManager.judgeWeiboFriends(param);// 判断数据中是否已经存在
					if (weiboInt > 0)
					{// 微博
					}
					else
					{
						String passiveId = friendsManager.isnoMember(param);// 看该人是否是会员
						if (Commonparam.StringIsNotNull(passiveId))
						{
							param.put("isnoMember", 1);// 是会员
							param.put("passiveId", passiveId);// 会员ID
							if (!passiveId.equals(userId))
							{
								friendsManager.addWeiboFriends(param);// 增加该第三方ID
							}
						}
					}

				}
				else if (type.equals("1"))
				{// facebook

					param.put("type", 1);
					// 判断以前是否已经有，有就不用再加了
					Integer facebookInt = friendsManager.judgeWeiboFriends(param);// 判断数据中是否已经存在，公用
					if (facebookInt > 0)
					{
					}
					else
					{
						String passiveId = friendsManager.isnoMemberFacebook(param);// 看该人是否是会员

						if (Commonparam.StringIsNotNull(passiveId))
						{
							param.put("isnoMember", 1);// 是会员
							param.put("passiveId", passiveId);// 会员ID
							if (!passiveId.equals(userId))
							{
								friendsManager.addWeiboFriends(param);// 增加该第三方ID
							}
						}

					}

				}
				else
				{
					param.put("type", 2);// 通讯录
					// 判断以前是否已经有，有就不用再加了
					Integer addressInt = friendsManager.judgeWeiboFriends(param);// 判断数据中是否已经存在，公用
					if (addressInt > 0)
					{
					}
					else
					{
						String passiveId = friendsManager.isnoMemberAddress(param);// 看该人是否是会员

						if (Commonparam.StringIsNotNull(passiveId))
						{
							param.put("isnoMember", 1);// 是会员
							param.put("passiveId", passiveId);// 会员ID
							if (!passiveId.equals(userId))
							{
								friendsManager.addWeiboFriends(param);// 增加该第三方ID
							}
						}
					}

				}
			}
		}
		bean.setStatus(SystemCodeContent.SUCCESS_CODE);
		return bean;
	}
	@Override
	public void addFriends(String passivePeopleId, String userId, String type)
	{
		HashMap param = new HashMap();

		param.put("passivePeopleId", passivePeopleId);
		param.put("userId", userId);
		param.put("initiativeId", passivePeopleId);
		param.put("addTime", Commonparam.Date2Str());

		if (type.equals("1"))
		{// 1是添加好友
			// 判断主动方至被动方是否已经存在，存在修改状态，不存在就加一个
			Integer juageFriendsRecommend1 = friendsManager.juageFriendsRecommend1(param); // 判断
			if (juageFriendsRecommend1 == 1)
			{
				friendsManager.updateFriendsRecommend1(param);// 更新状态 status=0别人添加我
			}
			else
			{
				friendsManager.insertFriendsRecommend1(param);// 插入数据 status=1添加别人
			}
			// 判断被动方至主动是否已经存在，存在修改状态，不存在就加一个
			Integer juageFriendsRecommend2 = friendsManager.juageFriendsRecommend2(param); // 判断
			if (juageFriendsRecommend2 == 1)
			{
				friendsManager.updateFriendsRecommend2(param);// 更新状态
			}
			else
			{
				friendsManager.insertFriendsRecommend2(param);// 插入数据
			}

			param.put("type", NotificationListType.RECEIVE_FRIEND_INVITATION);// 消息中是添加的
		}
		else// 2是接受好友
		{
			friendsManager.addFriends(param); // 在好友中添加
			friendsManager.addedDeleteFriends(param); // 在推荐的好友中删除

			param.put("type", NotificationListType.ACCEPT_FRIEND_INVITATION);// 消息中是接受的
		}

		HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片

		Integer t = (Integer)param.get("type");
		if(t == 3) {
			param.put("content", "[replace]" + obtainUserInfo.get("nickname"));
		}
		else{
			param.put("content", obtainUserInfo.get("nickname") + "[replace]");
		}

		param.put("headPicture", obtainUserInfo.get("headPicture"));
		param.put("addTime", Commonparam.Date2Str());

		countryManager.addNotificationMessage(param);// 保存上面的消息到推送表中
	}

	@Override
	public void addListFriends(String listFriendsId, String userId)
	{
		HashMap param = new HashMap();

		JSONArray array = JSONArray.parseArray(listFriendsId);
		// 获取官方账号ID
		String yolobooId = friendsManager.obtainYolobooId();

		// 获取好友推荐
		String recommendPeopleId = friendsManager.obtainRecommendPeopleId(userId);

		if (array.size() > 0)
		{
			for (int i = 0; i < array.size(); i++)
			{
				param.clear();
				param.put("userId", userId);
				param.put("passivePeopleId", array.get(i));
				param.put("addTime", Commonparam.Date2Str());
				param.put("initiativeId", array.get(i));
				String linshi = array.get(i).toString();
				if (linshi.equals(yolobooId))
				{
					friendsManager.addFriends(param); // 在好友中添加
					friendsManager.addedDeleteFriends(param); // 在推荐的好友中删除
				}
				else if (linshi.equals(recommendPeopleId))
				{
					friendsManager.addFriends(param); // 在好友中添加
					friendsManager.addedDeleteFriends(param); // 在推荐的好友中删除
				}
				else
				{
					// 判断主动方至被动方是否已经存在，存在修改状态，不存在就加一个
					Integer juageFriendsRecommend1 = friendsManager.juageFriendsRecommend1(param); // 判断
					if (juageFriendsRecommend1 == 1)
					{
						friendsManager.updateFriendsRecommend1(param);// 更新状态
					}
					else
					{
						friendsManager.insertFriendsRecommend1(param);// 插入数据
					}
					// 判断被动方至主动是否已经存在，存在修改状态，不存在就加一个
					Integer juageFriendsRecommend2 = friendsManager.juageFriendsRecommend2(param); // 判断
					if (juageFriendsRecommend2 == 1)
					{
						friendsManager.updateFriendsRecommend2(param);// 更新状态
					}
					else
					{
						friendsManager.insertFriendsRecommend2(param);// 插入数据
					}

					HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片
					String content;
					content = "[replace]" + obtainUserInfo.get("nickname");
					param.put("content", content);
					param.put("picture", "");
					param.put("addTime", Commonparam.Date2Str());
					param.put("type", NotificationListType.RECEIVE_FRIEND_INVITATION);
					countryManager.addNotificationMessage(param);// 保存上面的消息到推送表中//增加消息
				}
			}
		}
	}

	@Override
	public void uploadKey(String keyList, String userId, String type)
	{
		HashMap param = new HashMap();
		JSONArray array = JSONArray.parseArray(keyList);
		if (array.size() > 0)
		{
			for (int i = 0; i < array.size(); i++)
			{
				param.clear();
				String s = array.get(i).toString();
				s = PhoneUtil.checkPhone(s);
				param.put("thirdId", s);// 第三方ID
				param.put("userId", userId);// 用户ID
				String addTime = Commonparam.Date2Str();
				param.put("addTime", addTime);// 增加的时间
				if (type.equals("0"))
				{// 微博
					param.put("type", 0);
					// 判断以前是否已经有，有就不用再加了

					Integer weiboInt = friendsManager.judgeWeiboFriends(param);// 判断数据中是否已经存在
					if (weiboInt > 0)
					{// 微博
					}
					else
					{
						String passiveId = friendsManager.isnoMember(param);// 看该人是否是会员
						if (Commonparam.StringIsNotNull(passiveId))
						{
							param.put("isnoMember", 1);// 是会员
							param.put("passiveId", passiveId);// 会员ID
							if (!passiveId.equals(userId))
							{
								friendsManager.addWeiboFriends(param);// 增加该第三方ID
							}
						}
					}

				}
				else if (type.equals("1"))
				{// facebook

					param.put("type", 1);
					// 判断以前是否已经有，有就不用再加了
					Integer facebookInt = friendsManager.judgeWeiboFriends(param);// 判断数据中是否已经存在，公用
					if (facebookInt > 0)
					{
					}
					else
					{
						String passiveId = friendsManager.isnoMemberFacebook(param);// 看该人是否是会员

						if (Commonparam.StringIsNotNull(passiveId))
						{
							param.put("isnoMember", 1);// 是会员
							param.put("passiveId", passiveId);// 会员ID
							if (!passiveId.equals(userId))
							{
								friendsManager.addWeiboFriends(param);// 增加该第三方ID
							}
						}

					}

				}
				else
				{
					param.put("type", 2);// 通讯录
					// 判断以前是否已经有，有就不用再加了
					Integer addressInt = friendsManager.judgeWeiboFriends(param);// 判断数据中是否已经存在，公用
					if (addressInt > 0)
					{
					}
					else
					{
						String passiveId = friendsManager.isnoMemberAddress(param);// 看该人是否是会员

						if (Commonparam.StringIsNotNull(passiveId))
						{
							param.put("isnoMember", 1);// 是会员
							param.put("passiveId", passiveId);// 会员ID
							if (!passiveId.equals(userId))
							{
								friendsManager.addWeiboFriends(param);// 增加该第三方ID
							}
						}
					}

				}
			}
		}
	}

	@Override
	public void ignoreFriend(String userId, String passivePeopleId) {
		HashMap map=new HashMap();
		map.put("userId",userId);
		map.put("passivePeople",passivePeopleId);
		friendsManager.ignoreFriends(map);
	}

	@Override
	public BaseBean deleteFriendById(String friendUserId, String userId) {
		BaseBean bean=new BaseBean();
		HashMap param=new HashMap();
		param.put("friendId",friendUserId);
		param.put("userId",userId);
		param.put("addTime",Commonparam.Date2Str());
		//判断是否为官方账号
		Integer isOfficialAccount=friendsManager.isOfficialAccount(friendUserId);
		if (isOfficialAccount==0){//不是官方账号
			//软删除好友表
			friendsManager.deleteUserFried(param);
			//更新推荐关系
			List<HashMap> userFriends=friendsManager.obtainFriendsIdList(userId);//获取好友列表(去除官方账号)
			List<HashMap> friendUserFriends=friendsManager.obtainFriendsIdList(friendUserId);
			HashMap param1=new HashMap();
			if (userFriends.size()>0) {
				for (HashMap map : userFriends) {
					//获取A的一个好友
					String uuserId = map.get("userId").toString();
					//获取该好友的好友列表
					int count = 0;
					List<HashMap> list1 = friendsManager.obtainFriendsIdList(uuserId);
					for (HashMap map1 : list1) {
						String uuuserId = map1.get("userId").toString();
						if (!uuuserId.equals(userId)) {
							param1.put("userId", uuuserId);
							param1.put("friendId", friendUserId);
							//判断跟B是否是好友
							Integer isFriend = friendsManager.judgeIsFriends(param1);
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
						//删除推荐表信息
						friendsManager.deleteReconmendFriendById(param1);
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
					List<HashMap> list1 = friendsManager.obtainFriendsIdList(uuserId);
					for (HashMap map1 : list1) {
						String uuuserId = map1.get("userId").toString();

						if (!uuuserId.equals(friendUserId)) {
							param1.put("userId", uuuserId);
							param1.put("friendId", userId);
							//判断跟A是否是好友
							Integer isFriend =friendsManager.judgeIsFriends(param1);
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
						//删除推荐表信息
						friendsManager.deleteReconmendFriendById(param1);
					}
					param1.clear();
				}
			}
			bean.setStatus(200);
			bean.setMsg("删除成功");
		}else{
			bean.setStatus(400);
			bean.setMsg("不能删除");
		}
		return bean;
	}

	@Override
	public HashMap obtainFriendsById(String userId, int page, int size, String content) {
		HashMap param=new HashMap();
		param.put("userId", userId);
		if (Commonparam.StringIsNotNull(content)) {
			param.put("content", content);// 查询条件
		}
		param.put("size", size);
		param.put("startIndex", (page - 1) * size);
		List obtainMyFriends = friendsManager.obtainMyFriends(param);
		Long obtainMyFriendsCount = friendsManager.obtainMyFriendsCount(param);
		param.clear();
		param.put("obtainMyFriends", obtainMyFriends);
		param.put("obtainMyFriendsCount", obtainMyFriendsCount);
		return param;
	}

	@Override
	public List<HashMap> obtainRecommendFriends(String userId, int page, int size, String type) {
		HashMap map=new HashMap();
		map.put("userId",userId);
		map.put("page",page);
		map.put("size",size);
		map.put("type",type);
		List<HashMap> obtainMyFriends=friendsManager.obtainMyRecommendFriends(map);
		for (HashMap param:obtainMyFriends){
			//兴趣标签
			String id=param.get("userId").toString();
			List travelStyle=travelManager.obtainTravelStyleById(id);
			param.put("travelStyle",travelStyle);
			//文章数
			Long travelNotesCount=travelManager.obtainTravelNotesCount(id);
			param.put("travelNotesCount",travelNotesCount==null? 0 : travelNotesCount);
			//精选数
//			String choiceCount=travelManager.obtainChoiceCount(id);
//			param.put("choiceCount",choiceCount==null?0:choiceCount);
		}
		return obtainMyFriends;
	}

}
