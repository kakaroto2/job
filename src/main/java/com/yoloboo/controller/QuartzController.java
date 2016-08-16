package com.yoloboo.controller;

import com.PushIphoneFeedBackThread;
import com.common.*;
import com.common.constans.NotificationListType;
import com.common.constans.SystemCodeContent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.AccessToken;
import com.json.BaseBean;
import com.yoloboo.Ticket;
import com.yoloboo.TokenUtils;
import com.yoloboo.dao.*;
import com.yoloboo.entity.User;
import com.yoloboo.models.ActivityModel;
import com.yoloboo.models.ActivityPictureModel;
import com.yoloboo.models.PushModel;
import com.yoloboo.models.TopicModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/quartz")
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
			HashMap<String, Object> param = new HashMap<String, Object>();
			List<HashMap<String, Object>> msgList = new ArrayList<HashMap<String, Object>>();
				 //content,pushToken,type
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
//					else if (type.equals("25")) {
//						String notificationListId = map.get("notificationListId").toString();
//						HashMap result=activityPictureDao.getNotificationById(notificationListId);
//						String apId=result.get("picture_id").toString();
//						ActivityPictureModel apm = activityPictureDao.getModelByPK(Long.valueOf(apId));
//						ActivityModel am = activityDao.getModelByPK(apm.getA_id());
//						if (map.get("language").toString().equals("0")) {// 表示英语
//							content = content.replaceAll("\\[replace\\]", " liked your<"+am.getName_en()+"> activity picture ");
////							content="Liked your picture";
//						}
//						if (map.get("language").toString().equals("1")) {// 中文简体
//							content = content.replaceAll("\\[replace\\]", "赞了你<"+am.getName_cn()+">活动的照片");
////							content="赞了你的照片";
//						}
//						if (map.get("language").toString().equals("2")) {// 繁体
//							content = content.replaceAll("\\[replace\\]", "贊了妳<"+am.getName_tw()+">活動的照片");
////							content="贊了妳的照片";
//						}
//						map.put("content", content);
//					}

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
							content = content.replaceAll("\\[replace\\]", "给你留言了");
							//content="评论了你的照片";
						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]", "給妳留言了");
							//content="評論了妳的照片";
						}
						map.put("content", content);
					}
//					else if (type.equals("30")) {// 小花回复了你的评论【显示被回复的照片】  是活动的
//
//						if (map.get("language").toString().equals("0")) { //表示英语
//							content = map.get("userName").toString() + (" replied to your comment");
//						}
//						if (map.get("language").toString().equals("1")) { //中文简体
//							content = map.get("userName").toString() + ("回复了你的评论");
//						}
//						if (map.get("language").toString().equals("2")) { //繁体
//							content = map.get("userName").toString() + ("回復了你的評論");
//						}
//						map.put("content", content);
//					}


//					else if (type.equals("31")) {// 小花评论了妳的照片【显示被评论的照片】
//						String notificationListId = map.get("notificationListId").toString();
//						HashMap result=activityPictureDao.getNotificationById(notificationListId);
//						String apId=result.get("picture_id").toString();
//						ActivityPictureModel apm = activityPictureDao.getModelByPK(Long.valueOf(apId));
//						ActivityModel am = activityDao.getModelByPK(apm.getA_id());
//						if (map.get("language").toString().equals("0")) {// 表示英语
//							content = content.replaceAll("\\[replace\\]", " commented on your<"+am.getName_en()+"> activity picture ");
//							//content="Commented on your picture";
//						}
//						if (map.get("language").toString().equals("1")) {// 中文简体
//							content = content.replaceAll("\\[replace\\]", "评论了你<"+am.getName_cn()+">活动照片");
//							//content="评论了你的照片";
//						}
//						if (map.get("language").toString().equals("2")) {// 繁体
//							content = content.replaceAll("\\[replace\\]", "評論了妳<"+am.getName_tw()+">活動照片");
//							//content="評論了妳的照片";
//						}
//						map.put("content", content);
//					}
					else if (type.equals("3")) {// 妳收到了阿翾的好友邀请
						// 【显示邀请人头像】
						if (map.get("language").toString().equals("0")) {// 表示英语
							content = content.replaceAll("\\[replace\\]", "");
							content="["+content+"]"+"invited you to be her friend.";
						}
						if (map.get("language").toString().equals("1")) {// 中文简体
							content = content.replaceAll("\\[replace\\]", "");
							content="["+content+"]"+"向你发出了好友邀请.";
						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]", "");
							content="["+content+"]"+"向你發出了好友邀請.";

						}
						map.put("content", content);
					}

					else if (type.equals("4")) {// 好友接受了我的邀请
						//根据该条记录的user_id passive_id  判断这两个人是通过邀请码邀请还是添加好友
						String code=countryManager.getCode(Long.valueOf(map.get("notificationListId").toString()));
						String beCode=countryManager.getBeCode(Long.valueOf(map.get("notificationListId").toString()));
						if(code.equalsIgnoreCase(beCode)){
							if (map.get("language").toString().equals("0")) {// 表示英语
								content = "["+content.replaceAll("\\[replace\\]", "")+"]joined YOLOBOO with your invitation code!";
							}
							if (map.get("language").toString().equals("1")) {// 中文简体
								content = content.replaceAll("\\[replace\\]","");
								content = "["+content + "]已接受你的邀请加入了YOLOBOO!";

							}
							if (map.get("language").toString().equals("2")) {// 繁体
								content = content.replaceAll("\\[replace\\]","");
								content = "["+content + "]已接受妳的邀請加入了YOLOBOO!";
							}
						}else {
							if (map.get("language").toString().equals("0")) {// 表示英语
								content = "["+content.replaceAll("\\[replace\\]", "")+"]accepted you friend request.";
							}
							if (map.get("language").toString().equals("1")) {// 中文简体
								content = content.replaceAll("\\[replace\\]","");
								content = "["+content + "]已接受你的好友邀请。";

							}
							if (map.get("language").toString().equals("2")) {// 繁体
								content = content.replaceAll("\\[replace\\]","");
								content = "["+content + "]已接受妳的好友邀請。";
							}
						}
						map.put("content", content);
					}


//					else if (type.equals("10")) {
//						String topicId = content.substring(content.indexOf('[') + 1);
//
//						TopicModel topic = topicManager.getTopicById(Long.valueOf(topicId));
//						if (map.get("language").toString().equals("0")) {// 表示英语
//							content = "created a new topic:" + topic.getNameEn();
//						} else if (map.get("language").toString().equals("1")) {// 表示简体
//							content = "创建了新的话题:" + topic.getNameCn();
//						} else {// 表示繁体
//							content = "創建了新的話題:" + topic.getNameTw();
//						}
//						map.put("content", content);
//					}
//					else if (type.equals("11")) {
//						int i1 = content.indexOf("[");
//						int i2 = content.indexOf("]");
//						String author = content.substring(0,i1);
//						String noteName=content.substring(i1+1,i2);
//						if (map.get("language").toString().equals("0")) {// 表示英语
//							content = "["+author+"] published ["+ noteName+"]. Check it and get Inspired!";
//						} else if (map.get("language").toString().equals("1")) {// 表示简体
//							content = "["+author+"] 的新画报 ["+noteName+"] 上线，带你换个角度看世界";
//
//						} else {// 表示繁体
//							content ="["+author+"] 的新畫報 ["+noteName+"] 上線，帶妳換個角度看世界";
//						}
//
//						map.put("content", content);
//					}
//					else if (type.equals("13")) {
//						if (map.get("language").toString().equals("0")) {// 表示英语
//							content = map.get("userName")+"'s note is featured";
//						} else if (map.get("language").toString().equals("1")) {// 表示简体
//							content =map.get("userName")+ " 有文章入选精选";
//						} else {// 表示繁体
//							content = map.get("userName")+" 有文章入選精選";
//						}
//						map.put("content", content);
//					}
					else if (type.equals("14")) {
						if (map.get("language").toString().equals("0")) {// 表示英语
							content =" yay! you became our KOL!";
						} else if (map.get("language").toString().equals("1")) {// 表示简体
							content = "耶！你成为yoloboo的女神了!";
						} else {// 表示繁体
							content = "耶！你成爲yoloboo的女神了!";
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
								content = "Congrats! you are a winner of ["+am.getName_en()+"]!";
							} else if (map.get("language").toString().equals("1")) {// 中文简体
								content = "喜大普奔！恭喜你成为了["+ am.getName_cn()+"]活动的获胜者!";
							} else {// 繁体
								content = "喜大普奔！恭喜你成爲了["+ am.getName_tw()+"]活動的獲勝者!";
							}
							map.put("content", content);

						}
// else{
//							int i1 = content.indexOf("[");
//							int i2 = content.indexOf("]");
//							String apId = content.substring(i1 + 1, i2);
//							ActivityPictureModel apm = activityPictureDao.getModelByPK(Long.valueOf(apId));
//
//							ActivityModel am = activityDao.getModelByPK(apm.getA_id());
//
//
//							if (map.get("language").toString().equals("0")) {// 表示英语
//								content = map.get("userName")+"  Is the winner of <" + am.getName_en() + ">!";
//							} else if (map.get("language").toString().equals("1")) {// 中文简体
//								content =  map.get("userName")+"  成为<" + am.getName_cn() + ">活动的获奖者!";
//							} else {// 繁体
//								content = map.get("userName")+"  成爲<" + am.getName_tw() + ">活動的獲獎者!";
//							}
//							map.put("content", content);
//
//						}
					}
				}
			if (msgList.size() > 0) {
				new PushIphoneThread(targetFolderTemp, msgList, userManger).start();
			}

		} catch (Exception e) {
			LogException.printException(e);
			logger.info("定时推送结果异常：" + e.getLocalizedMessage());
			System.out.println(e.getLocalizedMessage());
		}
	}


	/**
	 * 管理员按照类型群发消息
	 * @throws Exception
	 */
	@RequestMapping(value = "pushMsgByType")
	@ResponseBody
	public void pushMsgByType(@RequestParam(required = true) Long msgId,HttpServletResponse response) throws Exception
	{
		BaseBean  bean=new BaseBean();
		try {
			logger.debug("certp12Path:" + targetFolderTemp);

			//对于feedback和fail产生的无效token进行处理
			List<HashMap<String,Object>>    list=userManger.getUseLessToken();

			if(list.size()>0){
				for(HashMap map:list){
					userManger.updateUseLessToken(map);
				}
			}

			//对于每个人进行发送
			List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();

			userList=userManger.getUserList();

			//获取此次活动推送内容的模版
			HashMap model=userManger.getModelById(msgId);

			//生成所有的消息体
			List<HashMap<String, Object>> msgList = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object>  map=null;
			for(int i = 0; i < userList.size(); i++){
				map=new HashMap();
				//map.put("notificationListId",activityModel.getNotification_list_id());
				if(null == model.get("type") ||("").equals(model.get("type"))){//默认为管理员类型
					map.put("type", NotificationListType.ADMIN_MSG);
				}else{
					map.put("type",model.get("type").toString());
				}
				map.put("m_type",model.get("m_type").toString());
				map.put("m_key",model.get("m_key").toString());
				map.put("pushToken",userList.get(i).get("u_ios_token"));
				if("0".equals(userList.get(i).get("u_language").toString())){
					map.put("content",model.get("m_en").toString());
				}else if("1".equals(userList.get(i).get("u_language").toString())){
					map.put("content",model.get("m_cn").toString());
				}else if("2".equals(userList.get(i).get("u_language").toString())){
					map.put("content",model.get("m_tw").toString());
				}
				map.put("appVersion",userList.get(i).get("appVersion"));
				map.put("language",userList.get(i).get("u_language"));
				map.put("userName",userList.get(i).get("u_nickname"));
				msgList.add(map);
			}
			if (msgList.size() > 0) {
				new PushIphoneMessageThread("/usr/local/tomcat/webapps/ROOT/common/cert.p12", msgList).start();
			}
			bean.setMsg("success");
			String json = Json.toString(bean);
			response.addHeader("Access-Control-Allow-Origin","*");
			response.getOutputStream().write(json.getBytes("UTF-8"));
		} catch (Exception e) {
			LogException.printException(e);
			System.out.println(e.getLocalizedMessage());
			bean.setMsg("false");
			String json = Json.toString(bean);
			response.addHeader("Access-Control-Allow-Origin","*");
			response.getOutputStream().write(json.getBytes("UTF-8"));
		}
	}



	/**
	 * 测试发送的字节数
	 * @throws Exception
	 */
	@RequestMapping(value = "getBytes")
	@ResponseBody
	public void getBytes(HttpServletResponse response) throws Exception
	{
		BaseBean  bean=new BaseBean();
		try {
			logger.debug("certp12Path:" + targetFolderTemp);


			//生成所有的消息体
			List<HashMap<String, Object>> msgList = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object>  map=null;
			map=new HashMap();
			map.put("type",40);//虽然消息表没有此处的消息类型 但是需要提供给客户端识别 此处需要定义特殊消息类型
			map.put("pushToken","ac616a98fda070579a12d80431c72e6697434f4839bafc3160933e2cb921f5da");
			map.put("content","Morning!It’s time to explore the world!");//英文
			msgList.add(map);
			if (msgList.size() > 0) {
				new PushIphoneMessageThread("/usr/local/tomcat/webapps/ROOT/common/cert.p12", msgList).start();
			}
			bean.setMsg("success");
			String json = Json.toString(bean);
			response.addHeader("Access-Control-Allow-Origin","*");
			response.getOutputStream().write(json.getBytes("UTF-8"));
		} catch (Exception e) {
			LogException.printException(e);
			System.out.println(e.getLocalizedMessage());
			bean.setMsg("false");
			String json = Json.toString(bean);
			response.addHeader("Access-Control-Allow-Origin","*");
			response.getOutputStream().write(json.getBytes("UTF-8"));
		}
	}


	/**
	 * 第一次单独获取feedback内容
	 * @throws Exception
	 */
	@RequestMapping(value = "getFeedBack")
	@ResponseBody
	public void getFeedBack(HttpServletResponse response) throws Exception
	{
		BaseBean  bean=new BaseBean();
		try {
			logger.debug("certp12Path:" + targetFolderTemp);
			new PushIphoneFeedBackThread("/usr/local/tomcat/webapps/ROOT/common/cert.p12").start();
			bean.setMsg("success");
			String json = Json.toString(bean);
			response.addHeader("Access-Control-Allow-Origin","*");
			response.getOutputStream().write(json.getBytes("UTF-8"));
		} catch (Exception e) {
			LogException.printException(e);
			System.out.println(e.getLocalizedMessage());
			bean.setMsg("false");
			String json = Json.toString(bean);
			response.addHeader("Access-Control-Allow-Origin","*");
			response.getOutputStream().write(json.getBytes("UTF-8"));
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

/*
    每隔两小时
    更新一次tb_ticket 的ticket
 */


	public void updateTicket() throws Exception {

		try {
			String    appId="wx5cc1c9a29e304c8d";
			String 	 appSecret="dc140156728b80e6d2e7ea00600ca2c9";
			//获取accesstoken
			TokenUtils  tokenUtils=new TokenUtils();

			String accessToken=tokenUtils.getAccess_token(appId,appSecret);


			//根据token  获取ticket

			Ticket  ticket=new Ticket();

			String jaspticket=ticket.getTicket(accessToken);

			//jasptickets  更新tb_ticket


		    userManger.updateTicket(jaspticket);


		} catch (Exception e) {
			LogException.printException(e);
			logger.info("定时任务更新ticket异常：" + e.getLocalizedMessage());
			System.out.println(e.getLocalizedMessage());
		}
	}


}
