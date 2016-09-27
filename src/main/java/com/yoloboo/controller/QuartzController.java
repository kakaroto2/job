package com.yoloboo.controller;

import com.PushIphoneFeedBackThread;
import com.common.*;
import com.common.constans.NotificationListType;
import com.common.constans.SystemCodeContent;
import com.common.constans.SystemContent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.AccessToken;
import com.json.BaseBean;
import com.yoloboo.Ticket;
import com.yoloboo.TokenUtils;
import com.yoloboo.dao.*;
import com.yoloboo.entity.User;
import com.yoloboo.models.*;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
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
	private ActivityDao activityDao;
	@Resource
	private ActivityPictureDao activityPictureDao;
	@Resource
	private TravelNoteDao travelNoteDao;
	@Resource
	private RobotJobDao robotJobDao;


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
					if(null == map.get("language")){
						map.put("language",1);
					}
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
					else if (type.equals("25")) {
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
							content="["+content+"]"+"followed you.";
						}
						if (map.get("language").toString().equals("1")) {// 中文简体
							content = content.replaceAll("\\[replace\\]", "");
							content="["+content+"]"+"剛剛關註了妳.";
						}
						if (map.get("language").toString().equals("2")) {// 繁体
							content = content.replaceAll("\\[replace\\]", "");
							content="["+content+"]"+"刚刚关注了你.";

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
					else if (type.equals("13")) {
						int i2 = content.indexOf("]");
						String tnId = content.substring(i2 + 1);
						TravelNoteModel tnm = travelNoteDao.getModelByPK(Long.valueOf(tnId));

						if (map.get("language").toString().equals("0")) {// 表示英语
							content = "Your note<" + tnm.getTitle() + "> is featured！";
						} else if (map.get("language").toString().equals("1")) {// 表示简体
							content = "恭喜你的文章<" + tnm.getTitle() + ">被选入精选！";
						} else {// 表示繁体
							content = "恭喜你的文章<" + tnm.getTitle() + ">被選入精選！";
						}
						map.put("content", content);
					}
//					else if (type.equals("14")) {
//						if (map.get("language").toString().equals("0")) {// 表示英语
//							content =" yay! you became our KOL!";
//						} else if (map.get("language").toString().equals("1")) {// 表示简体
//							content = "耶！你成为yoloboo的女神了!";
//						} else {// 表示繁体
//							content = "耶！你成爲yoloboo的女神了!";
//						}
//						map.put("content", content);
//					}
					else if (type.equals("15")) {
						if (map.get("language").toString().equals("0")) {// 表示英语
							content =" yay! you are one step closer to becoming YOLOBOO goddness!";
						} else if (map.get("language").toString().equals("1")) {// 表示简体
							content = "耶！你在成长为女神的路上又前进了一步！";
						} else {// 表示繁体
							content = "耶！你在成長為女神的路上又前進了一步！";
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
				if(null != model.get("m_type")){
					map.put("m_type",model.get("m_type").toString());
				}
				if(null != model.get("m_key")){
					map.put("m_key",model.get("m_key").toString());
				}
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
			map.put("pushToken","cd906e98846b1936a78ebc4d34fc7a365f47a2cd7977bc35a0b37d729d3e0d68");
			map.put("content","1");//英文
			map.put("m_type","note");
			map.put("m_key","2496");
			msgList.add(map);
			if (msgList.size() > 0) {
				System.out.println("start******************************************************");
				new PushIphoneMessageThread("/usr/local/tomcat/webapps/ROOT/common/cert.p12", msgList).start();
				System.out.println("end******************************************************");
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

			// 好友的好友录入到推荐列表中(VERSION2.1.6及之后版本取消好友的好友)
			//countryManager.updateFriendsFriends();//

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
			String    appId="wxcc378e02c15daf33";
			String 	 appSecret="2ed59dcbd68bc1f885b4eebc87a2ce9a";
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

	/**
	 * 更新机器人所需要执行的任务表
	 */
	public void updateRobotJob(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		Date beforeDate = new Date(now.getTime() -180000);
		String startTime = Commonparam.Date2Str(beforeDate);

		HashMap param = new HashMap();
		HashMap jopParam = new HashMap();
		param.put("startTime",startTime);

		List<RobotJob> list = robotJobDao.getRobotJobs(param);
		List<String> timeStr = new ArrayList();
		List<Long> robots = new ArrayList();

		if(!list.isEmpty()){
			for(RobotJob robotJob :list){
				if(("note").equals(robotJob.getJobType())){

					if(4 <robotJobDao.getNumByUserAndNoteType(robotJob.getUserId().toString())){
						continue;
					}
					Long picId = robotJobDao.getRandomPicIdByNote(robotJob.getTypeId());
					Date jobTime = Commonparam.StringtoDate(robotJob.getJobTime().toString());
					Date after3m = new Date(jobTime.getTime()  +180000);
					Date after8m = new Date(jobTime.getTime()  +480000);
					Date after10m = new Date(jobTime.getTime() +600000);
					Date after70m = new Date(jobTime.getTime() +4200000);
					Date after110m = new Date(jobTime.getTime()+6600000);
					Date after25h = new Date(jobTime.getTime() +90000000);

					timeStr.add(0,Commonparam.Date2Str(after3m));
					timeStr.add(1, Commonparam.Date2Str(after8m));
					timeStr.add(2,Commonparam.Date2Str(after10m));
					timeStr.add(3,Commonparam.Date2Str(after70m));
					timeStr.add(4,Commonparam.Date2Str(after110m));
					timeStr.add(5,Commonparam.Date2Str(after25h));

					for(int i=0;i<6;i++){
						jopParam.put("jobTime",timeStr.get(i));
						jopParam.put("userId",robotJobDao.getRobotForNote(robotJob.getUserId().toString(),picId.toString()));
						jopParam.put("jobType",robotJob.getJobType());
						jopParam.put("picId",picId);
						robotJobDao.addRobotJob(jopParam);
						jopParam.clear();
					}
					//更新用户被发布文章被机器人点赞的次数
					robotJobDao.updateRobertsNum(robotJob.getUserId().toString());
					//更新文章的阅读量
					robotJobDao.updteReadNum(robotJob.getTypeId().toString());

					timeStr.clear();
					robots.clear();
				}else{
					Date jobTime = Commonparam.StringtoDate(robotJob.getJobTime().toString());

					Date after3m = new Date(jobTime.getTime()  +180000);
					Date after55m = new Date(jobTime.getTime()  +3300000);
					Date after50m = new Date(jobTime.getTime()  +3000000);
					timeStr.add(0,Commonparam.Date2Str(after3m));
					timeStr.add(1,Commonparam.Date2Str(after3m));
					timeStr.add(2,Commonparam.Date2Str(after3m));
					timeStr.add(3, Commonparam.Date2Str(after50m));
					timeStr.add(4, Commonparam.Date2Str(after50m));
					timeStr.add(5, Commonparam.Date2Str(after50m));
					timeStr.add(6, Commonparam.Date2Str(after55m));
					timeStr.add(7, Commonparam.Date2Str(after55m));
					timeStr.add(8, Commonparam.Date2Str(after55m));

					for(int i=0;i<9;i++){
						jopParam.put("jobTime",timeStr.get(i));
						jopParam.put("userId",robotJobDao.getRobotForActivity(robotJob.getUserId().toString(),robotJob.getTypeId().toString()));
						jopParam.put("jobType",robotJob.getJobType());
						jopParam.put("picId",robotJob.getTypeId());
						robotJobDao.addRobotJob(jopParam);
						jopParam.clear();
					}
					timeStr.clear();
					robots.clear();
				}
			}
		}
	}


	/**
	 * 机器人执行任务
	 */

	public void robotExecute(){
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		String endTime = Commonparam.Date2Str();
		List<HashMap> list = robotJobDao.getJobs(endTime);
		if(!list.isEmpty()){
			for(HashMap map:list){
				if(("note").equals(map.get("jobType"))){
                        //关注用户
					try {
						StringBuilder param = new StringBuilder();
						Integer passiveId = robotJobDao.getUserIdByPic(map.get("picId").toString());
						param.append("&passiveId="+passiveId);
						param.append("&userId="+map.get("userId"));
						URL realUrl = new URL(SystemContent.FOLLOWUSER_URL);
						//打开和Url之间的连接
						URLConnection conn = realUrl.openConnection();
						//设置的通用请求属性
						conn.setRequestProperty("accept","*/*");
						conn.setRequestProperty("connection","Keep-Alive");
						conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

						conn.setDoOutput(true);
						conn.setDoInput(true);

						// 获取URLConnection对象对应的输出流
						out = new PrintWriter(conn.getOutputStream());
						//发送请求参数
						out.print(param);
						//flush输出流的缓存
						out.flush();
						//定义Buffer输入流来读取Url的响应
						in =  new BufferedReader(new InputStreamReader(conn.getInputStream()));

						String line;
						while((line = in.readLine())!= null){
							result +=line;
						}
						//更新任务状态
						robotJobDao.updateFlag(map.get("id").toString());

					} catch (Exception e) {
						LogException.printException(e);
						logger.info("定时机器人执行异常"+e.getLocalizedMessage());
						System.out.println(e.getLocalizedMessage());
					}
					finally {
						try {
							if(out != null){
								out.close();
							}
							if(in != null){
								in.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println(result);
					}


					//点赞
					try {
						StringBuilder param = new StringBuilder();
						param.append("&userId="+map.get("userId"));
						param.append("&tipsId="+map.get("picId"));
						URL realUrl = new URL(SystemContent.NOTEPICPRAISE_URL);
						//打开和Url之间的连接
						URLConnection conn = realUrl.openConnection();
						//设置的通用请求属性
						conn.setRequestProperty("accept","*/*");
						conn.setRequestProperty("connection","Keep-Alive");
						conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

						conn.setDoOutput(true);
						conn.setDoInput(true);

						// 获取URLConnection对象对应的输出流
						out = new PrintWriter(conn.getOutputStream());
						//发送请求参数
						out.print(param);
						//flush输出流的缓存
						out.flush();
						//定义Buffer输入流来读取Url的响应
						in =  new BufferedReader(new InputStreamReader(conn.getInputStream()));

						String line;
						while((line = in.readLine())!= null){
							result +=line;
						}
						//更新任务状态
						robotJobDao.updateFlag(map.get("id").toString());

					} catch (Exception e) {
						LogException.printException(e);
						logger.info("定时机器人执行异常"+e.getLocalizedMessage());
						System.out.println(e.getLocalizedMessage());
					}
					finally {
						try {
							if(out != null){
								out.close();
							}
							if(in != null){
								in.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println(result);
					}

				}else{
					try {
						StringBuilder param = new StringBuilder();
						param.append("&userId="+map.get("userId"));
						param.append("&apId="+map.get("picId"));
						param.append("&userPicture="+map.get("userPicture"));
						param.append("&userName="+map.get("userName"));
						URL realUrl = new URL(SystemContent.ACTIVITYPICPRAISE_URL);
						//打开和Url之间的连接
						URLConnection conn = realUrl.openConnection();
						//设置的通用请求属性
						conn.setRequestProperty("accept","*/*");
						conn.setRequestProperty("connection","Keep-Alive");
						conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

						conn.setDoOutput(true);
						conn.setDoInput(true);

						// 获取URLConnection对象对应的输出流
						out = new PrintWriter(conn.getOutputStream());
						//发送请求参数
						out.print(param);
						//flush输出流的缓存
						out.flush();
						//定义Buffer输入流来读取Url的响应
						in =  new BufferedReader(new InputStreamReader(conn.getInputStream()));

						String line;
						while((line = in.readLine())!= null){
							result +=line;
						}
						//更新任务状态
						robotJobDao.updateFlag(map.get("id").toString());

					} catch (Exception e) {
						LogException.printException(e);
						logger.info("定时机器人执行异常"+e.getLocalizedMessage());
						System.out.println(e.getLocalizedMessage());
					}
					finally {
						try {
							if(out != null){
								out.close();
							}
							if(in != null){
								in.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

						System.out.println(result);
					}


				}

			}
		}

	}

}
