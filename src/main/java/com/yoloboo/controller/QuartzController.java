package com.yoloboo.controller;

import com.common.Commonparam;
import com.common.LogException;
import com.common.PushIphoneActivityThread;
import com.common.PushIphoneThread;
import com.yoloboo.dao.*;
import com.yoloboo.entity.User;
import com.yoloboo.models.ActivityModel;
import com.yoloboo.models.ActivityPictureModel;
import com.yoloboo.models.TopicModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 定时任务类，用于执行定时任务
 * 换成springmvc是需修改spring配置
 *
 */
@Controller
public class QuartzController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(QuartzController.class);


	@Value("${certp12Path}")
	String targetFolderTemp;
	@Resource
	private UserManager userManger;
	@Resource
	private CountryManager countryManager;
	@Resource
	private TopicManager topicManager;
	@Resource
	private ActivityDao activityDao;
	@Resource
	private ActivityPictureDao activityPictureDao;

	public void pushMsg() throws Exception {

		try {
  			logger.debug("certp12Path:" + targetFolderTemp);
		//	int page = 1, size = 20;
			HashMap<String, Object> param = new HashMap<String, Object>();
		//	param.put("size", size);
			List<HashMap<String, Object>> msgList = new ArrayList<HashMap<String, Object>>();
			//while (page > 0) {
				//param.put("startIndex", (page - 1) * size);
				// content,pushToken,type
				msgList = userManger.findNotifyMsgList(param);
				System.out.println("定时推送" + Commonparam.Date2Str() + ",msg count:" + msgList.size());
				for (int i = 0; i < msgList.size(); i++) {
					HashMap map = msgList.get(i);
					String content = (String) map.get("content");
					String type = map.get("type").toString();

					if(map.get("appString") != null){
						String appVersion = map.get("appString").toString();
						if(type.equals("32")&& appVersion!="2.0.5"){
							continue;
						}
					}


					if (type.equals("1")) {// 小花赞了妳的照片【显示被赞的照片】
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = content.replaceAll("\\[replace\\]", " liked your picture ");
//							content="Liked your picture";
						}
						if (map.get("language").toString().equals("1")) {// 中文简体
							content = content.replaceAll("\\[replace\\]", "赞了你的照片");
//							content="赞了你的照片";
						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]", "贊了妳的照片");
//							content="贊了妳的照片";
						}
						map.put("content", content);
					}
					else if (type.equals("25")) {// 小花赞了妳的照片【显示被赞的照片】
						String notificationListId = map.get("notificationListId").toString();
						HashMap result=activityPictureDao.getNotificationById(notificationListId);
						String apId=result.get("picture_id").toString();
						ActivityPictureModel apm = activityPictureDao.getModelByPK(Long.valueOf(apId));
						ActivityModel am = activityDao.getModelByPK(apm.getA_id());
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = content.replaceAll("\\[replace\\]", " liked your<"+am.getName_en()+"> activity picture ");
//							content="Liked your picture";
						}
						if (map.get("language").toString().equals("1")) {// 中文简体
							content = content.replaceAll("\\[replace\\]", "赞了你<"+am.getName_cn()+">活动的照片");
//							content="赞了你的照片";
						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]", "贊了妳<"+am.getName_tw()+">活動的照片");
//							content="贊了妳的照片";
						}
						map.put("content", content);
					}

					else if (type.equals("0")) {// 小花回复了你的评论【显示被回复的照片】

						if (map.get("language").toString().equals("0")) { //表示英语
							content = map.get("userName").toString() + (" replied to your comment");
						}
						if (map.get("language").toString().equals("1")) { //中文简体
							content = map.get("userName").toString() + ("回复了你的评论");
						}
						if (map.get("language").toString().equals("2")) { //繁体
							content = map.get("userName").toString() + ("回復了你的評論");
						}
						map.put("content", content);
					}


					else if (type.equals("2")) {// 小花评论了妳的照片【显示被评论的照片】
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = content.replaceAll("\\[replace\\]", " commented on your picture ");
							//content="Commented on your picture";
						}
						if (map.get("language").toString().equals("1")) {// 中文简体
							content = content.replaceAll("\\[replace\\]", "评论了你的照片");
							//content="评论了你的照片";
						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]", "評論了妳的照片");
							//content="評論了妳的照片";
						}
						map.put("content", content);
					}
					else if (type.equals("30")) {// 小花回复了你的评论【显示被回复的照片】

						if (map.get("language").toString().equals("0")) { //表示英语
							content = map.get("userName").toString() + (" replied to your comment");
						}
						if (map.get("language").toString().equals("1")) { //中文简体
							content = map.get("userName").toString() + ("回复了你的评论");
						}
						if (map.get("language").toString().equals("2")) { //繁体
							content = map.get("userName").toString() + ("回復了你的評論");
						}
						map.put("content", content);
					}


					else if (type.equals("31")) {// 小花评论了妳的照片【显示被评论的照片】
						String notificationListId = map.get("notificationListId").toString();
						HashMap result=activityPictureDao.getNotificationById(notificationListId);
						String apId=result.get("picture_id").toString();
						ActivityPictureModel apm = activityPictureDao.getModelByPK(Long.valueOf(apId));
						ActivityModel am = activityDao.getModelByPK(apm.getA_id());
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = content.replaceAll("\\[replace\\]", " commented on your<"+am.getName_en()+"> activity picture ");
							//content="Commented on your picture";
						}
						if (map.get("language").toString().equals("1")) {// 中文简体
							content = content.replaceAll("\\[replace\\]", "评论了你<"+am.getName_cn()+">活动照片");
							//content="评论了你的照片";
						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]", "評論了妳<"+am.getName_tw()+">活動照片");
							//content="評論了妳的照片";
						}
						map.put("content", content);
					}
					else if (type.equals("3")) {// 妳收到了阿翾的好友邀请
						// 【显示邀请人头像】
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = content.replaceAll("\\[replace\\]", "You received a friends request from ");
							//content = "Invited you to be friends.";
						}
						if (map.get("language").toString().equals("1")) {// 中文简体
							content = content.replaceAll("\\[replace\\]", "你收到了");
							content = content + "的好友邀请";//
							//content="邀请你成为好友";
						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]", "妳收到了");
							content = content + "的好友邀請";//
							//content="邀請你成為好友";

						}
						map.put("content", content);
					}

					else if (type.equals("4")) {// 好友接受了我的邀请
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = "You are now friends with " + content.replaceAll("\\[replace\\]", "");
							//content="Accepted your Invitation.";
						}
						if (map.get("language").toString().equals("1")) {// 中文简体
							content = content.replaceAll("\\[replace\\]","");
							content = "你和"+content + "成为了好友";
							//content="接受了你的好友邀请";

						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]","");
							content = "你和"+content + "成为了好友";
							//content="接受了你的好友邀請";
						}
						map.put("content", content);
					}


					else if (type.equals("10")) {
						String topicId = content.substring(content.indexOf('[') + 1);

						TopicModel topic = topicManager.getTopicById(Long.valueOf(topicId));
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = "created a new topic:" + topic.getNameEn();
						} else if (map.get("language").toString().equals("1")) {// 表示简体
							content = "创建了新的话题:" + topic.getNameCn();
						} else {// 表示繁体
							content = "創建了新的話題:" + topic.getNameTw();
						}
						map.put("content", content);
					}
					else if (type.equals("11")) {
						int i1 = content.indexOf("[");
						int i2 = content.indexOf("]");
						String author = content.substring(0,i1);
						String noteName=content.substring(i1+1,i2);
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = "["+author+"] published ["+ noteName+"]. Check it and get Inspired!";
						} else if (map.get("language").toString().equals("1")) {// 表示简体
							content = "["+author+"] 的新画报 ["+noteName+"] 上线，带你换个角度看世界";

						} else {// 表示繁体
							content ="["+author+"] 的新畫報 ["+noteName+"] 上線，帶妳換個角度看世界";
						}

						map.put("content", content);
					}
					else if (type.equals("13")) {
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = map.get("userName")+"'s note is featured";
						} else if (map.get("language").toString().equals("1")) {// 表示简体
							content =map.get("userName")+ " 有文章入选精选";
						} else {// 表示繁体
							content = map.get("userName")+" 有文章入選精選";
						}
						map.put("content", content);
					}
					else if (type.equals("14")) {
						if (map.get("language").toString().equals("0")) {// 表示英语
							content =map.get("userName")+ " has become a KOL!";
						} else if (map.get("language").toString().equals("1")) {// 表示简体
							content = map.get("userName")+"升级成为达人！";
						} else {// 表示繁体
							content = map.get("userName")+"升級成為達人";
						}
						map.put("content", content);
					}
					else if (type.equals("32")) {
						if (map.get("language").toString().equals("0")) {// 表示英语
							content ="BOO sent you a message, please check~";
						} else if (map.get("language").toString().equals("1")) {// 表示简体
							content = "小BOO给你发消息啦！快去查收~";
						} else {// 表示繁体
							content ="小BOO給妳發消息啦！快去查收~";
						}
						map.put("content", content);
					}
					else if (type.equals("18")) {
						//分两种情况 一种推送给自己  一种推送给获奖者的好友
						//根据消息表的主键判断该条消息记录是否存在passive_id 如果存在的话表示是好友的消息  如果不存在的话 表示是获奖者的消息
						String notificationListId = map.get("notificationListId").toString();
						Long passive_id=countryManager.isYouOrRecent(notificationListId);
						if(null==passive_id){
							int i1 = content.indexOf("[");
							int i2 = content.indexOf("]");
							String apId = content.substring(i1 + 1, i2);
							ActivityPictureModel apm = activityPictureDao.getModelByPK(Long.valueOf(apId));

							ActivityModel am = activityDao.getModelByPK(apm.getA_id());

							if (map.get("language").toString().equals("0")) {// 表示英语
								content = "Your <" + am.getName_en() + "> photo is rewarded!";
							} else if (map.get("language").toString().equals("1")) {// 中文简体
								content = "号外！你参加<" + am.getName_cn() + ">活动的照片获奖了！";
							} else {// 繁体
								content = "號外！你參加<" + am.getName_tw() + ">活動的照片獲獎了！";
							}
							map.put("content", content);

						}else{
							int i1 = content.indexOf("[");
							int i2 = content.indexOf("]");
							String apId = content.substring(i1 + 1, i2);
							ActivityPictureModel apm = activityPictureDao.getModelByPK(Long.valueOf(apId));

							ActivityModel am = activityDao.getModelByPK(apm.getA_id());


							if (map.get("language").toString().equals("0")) {// 表示英语
								content = map.get("userName")+"  Is the winner of <" + am.getName_en() + ">!";
							} else if (map.get("language").toString().equals("1")) {// 中文简体
								content =  map.get("userName")+"  成为<" + am.getName_cn() + ">活动的获奖者!";
							} else {// 繁体
								content = map.get("userName")+"  成爲<" + am.getName_tw() + ">活動的獲獎者!";
							}
							map.put("content", content);

						}
					}
				}
			//	if (msgList.size() > 0) {
			//		page++;
					new PushIphoneThread(targetFolderTemp, msgList, userManger).start();
			//	} else {
			//		page = 0;
			//	}

		//	}
		} catch (Exception e) {
			LogException.printException(e);
			logger.info("定时推送结果异常：" + e.getLocalizedMessage());
			System.out.println(e.getLocalizedMessage());
		}
	}


	public void pushActivityMsg() throws Exception {


		try {
			logger.debug("certp12Path:" + targetFolderTemp);
			System.out.println("定时活动推送" + Commonparam.Date2Str());
			//先找出没有被推送的新的活动
			List<ActivityModel>   list=activityDao.getActivityList();
//			//对于每个人进行发送
			List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();

			userList=userManger.getUserList();

			String content_cn="";
			String content_en="";
			String content_tw="";
			for(ActivityModel  model:list){
				 content_cn="YOLOBOO邀您参加 ["+model.getName_cn()+"] 活动，照片这么美，晒出来让大家羡慕一下呗!";
				 content_en= "YOLOBOO invites you to join ["+model.getName_en()+"]. Show us your awesome pics! ";
				 content_tw="YOLOBOO邀您參加 ["+model.getName_tw()+"] 活動，照片這麽美，曬出來讓大家羨慕壹下呗!";
				 model.setContent_cn(content_cn);
				 model.setContent_en(content_en);
				 model.setContent_tw(content_tw);
			}
			//生成所有的消息体
			List<HashMap<String, Object>> msgList = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object>  map=null;
			for(int i = 0; i < userList.size(); i++){
				for(ActivityModel  activityModel:list){
					map=new HashMap();
					map.put("notificationListId",activityModel.getNotification_list_id());
					map.put("type",12);
					map.put("pushToken",userList.get(i).get("u_ios_token"));
					if("0".equals(userList.get(i).get("u_language").toString())){
						map.put("content",activityModel.getContent_en().toString());
					}else if("1".equals(userList.get(i).get("u_language").toString())){
						map.put("content",activityModel.getContent_cn().toString());
					}else if("2".equals(userList.get(i).get("u_language").toString())){
						map.put("content",activityModel.getContent_tw().toString());
					}
					map.put("appVersion",userList.get(i).get("appVersion"));
					map.put("language",userList.get(i).get("u_language"));
					map.put("userName",userList.get(i).get("u_nickname"));
					msgList.add(map);
				}
			}
			if (msgList.size() > 0) {
				new PushIphoneActivityThread(targetFolderTemp, msgList, userManger).start();
			}

		} catch (Exception e) {
			LogException.printException(e);
			logger.info("定时推送活动结果异常：" + e.getLocalizedMessage());
			System.out.println(e.getLocalizedMessage());
		}
	}


	// 更新
	public void recommendFriends() throws Exception {

		try {

			// 把推荐表的'('')''-''+'' '全部去掉
			countryManager.updateRecommendFriendsList();// 更新
			// 手机号码 去推荐表中判断
			// 加上国家代码，去推荐表中判断
			// 如果是相同的，更新ID
			countryManager.updateRecommendFriendsListNum();//

			// 好友的好友录入到推荐列表中
			countryManager.updateFriendsFriends();//

		} catch (Exception e) {
			LogException.printException(e);
			logger.info("推荐好友更新异常：" + e.getLocalizedMessage());
			System.out.println(e.getLocalizedMessage());
		}
	}


	// 更新消息表中没有iostoken 的用户的是否推送的状态
	public void updateNotificationStatus() throws Exception {

		try {

			activityDao.updateNotificationStatus();

		} catch (Exception e) {
			LogException.printException(e);
			System.out.println(e.getLocalizedMessage());
		}
	}
}
