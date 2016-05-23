package com.yoloboo.service.impl;

import com.common.Commonparam;
import com.common.constans.NotificationListType;
import com.json.BaseBean;
import com.yoloboo.controller.bean.CommentBean;
import com.yoloboo.controller.bean.PraiseBean;
import com.yoloboo.dao.CommentsManager;
import com.yoloboo.dao.CountryManager;
import com.yoloboo.dao.MyCenterManager;
import com.yoloboo.dao.TravelManager;
import com.yoloboo.models.CommentModel;
import com.yoloboo.models.PraiseModel;
import com.yoloboo.service.CommentsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2015/12/28.
 */

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService
{
	@Resource
	private CountryManager countryManager;
	@Resource
	private MyCenterManager myCenterManager;
	@Resource
	private CommentsManager commentsManager;
	@Resource
	private TravelManager travelManager;

	@Override
	public BaseBean addTipsComments(String tipsId, String userId, String commentsContent, String c_userId, String c_nickname)
	{
		BaseBean bean = new BaseBean();
		HashMap param = new HashMap();

		param.put("commentsTime", Commonparam.Date2Str());
		param.put("commentsContent", commentsContent);
		param.put("userId", userId);
		param.put("c_userId", c_userId);
		param.put("c_nickname", c_nickname);
		param.put("tipsId", tipsId); //照片ID

		if (StringUtils.isNotBlank(c_nickname) && StringUtils.isNotBlank(c_userId))
		{//回复评论
			countryManager.addTipsComments(param);// 增加 回复评论
		}
		else
		{
			countryManager.addTipsComments2(param);//仅评论
		}

		HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片

		String content;
		HashMap obtainTipsInfoPic = countryManager.obtainTipsInfoPic(Long.valueOf(tipsId));// 获得该照片的存储地址

		param.put("headPicture", obtainTipsInfoPic.get("headPicture"));
		param.put("p_width", obtainTipsInfoPic.get("p_width"));
		param.put("p_height", obtainTipsInfoPic.get("p_height"));
		param.put("addTime", Commonparam.Date2Str());

		if (StringUtils.isNotBlank(c_nickname) && StringUtils.isNotBlank(c_userId))
		{
			param.put("content", commentsContent);
			param.put("initiativeId", c_userId); //userId回复了c_userId的评论
			param.put("type", NotificationListType.COMMENT_REPLIED);
			countryManager.addNotificationMessage(param); //保存回复评论的消息到推送表
		}
		else
		{
			content = obtainUserInfo.get("nickname") + "[replace]";
			param.put("content", content);
			String initiativeId = countryManager.obtainInitiativeId(tipsId);// 获取主动的ID
			param.put("initiativeId", initiativeId);
			param.put("type", NotificationListType.PICTURE_COMMENTED);
			if (!initiativeId.equals(userId))
			{
				countryManager.addNotificationMessage(param);
			}
		}

		bean.setStatus(200);
		bean.setMsg("添加成功");

		return bean;
	}

	@Override
	public void addActivityPictureCommentsNotification(Long apId, Long userId, String commentsContent, Long c_userId, String c_nickname)
	{
		HashMap param = new HashMap();

		param.put("commentsTime", Commonparam.Date2Str());
		param.put("commentsContent", commentsContent);
		param.put("userId", userId);
		param.put("c_userId", c_userId);
		param.put("c_nickname", c_nickname);
		param.put("apId", apId); //照片ID


		HashMap obtainUserInfo = countryManager.obtainUserInfo(userId);// 获得用户昵称和图片

		String content;
		HashMap obtainTipsInfoPic = countryManager.obtainActivityInfoPic(apId);// 获得该照片的存储地址

		param.put("ap_picture", obtainTipsInfoPic.get("ap_picture"));
		param.put("addTime", Commonparam.Date2Str());

		if (StringUtils.isNotBlank(c_nickname) && StringUtils.isNotBlank(c_userId.toString()))
		{
			param.put("content", commentsContent);
			param.put("initiativeId", c_userId); //userId回复了c_userId的评论
			param.put("type", NotificationListType.ACTIVITY_COMMENT_REPLIED);
			countryManager.addActivityNotificationMessage(param); //保存回复评论的消息到推送表
		}
		else
		{
			content = obtainUserInfo.get("nickname") + "[replace]";
			param.put("content", content);
			String initiativeId = countryManager.obtainActivityInitiativeId(apId);// 获取主动的ID
			param.put("initiativeId", initiativeId);
			param.put("type", NotificationListType.ACTIVITY_PICTURE_COMMENTED);
			if (!initiativeId.equals(userId.toString()))
			{
				countryManager.addActivityNotificationMessage(param);
			}
		}

	}
	@Override
	public void addActivityPicturePraiseNotification(Long apId, Long userId, String userPicture, String userName)
	{
		HashMap param = new HashMap();

		param.put("userId", userId);
		param.put("apId", apId);

		HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片
		HashMap obtainTipsInfoPic = countryManager.obtainActivityInfoPic(apId);// 获得该照片的存储地址

		String content;
		content = obtainUserInfo.get("nickname") + "[replace]";

		param.put("addTime", Commonparam.Date2Str());
		param.put("content", content);
		param.put("ap_picture", obtainTipsInfoPic.get("ap_picture"));

		// 找到是谁的图片，保存到消息表中
		String initiativeId = countryManager.obtainActivityInitiativeId(apId);// 获取主动的ID
		param.put("initiativeId", initiativeId);
		param.put("type", NotificationListType.ACTIVITY_PICTURE_PRAISED);
		if (!initiativeId.equals(userId.toString())) {
			countryManager.addActivityNotificationMessage(param);// 保存上面的消息到推送表中
		}
	}
	@Override
	public void deleteActivityPicturePraiseNotification(Long apId, Long userId, String userPicture, String userName)
	{
		HashMap param = new HashMap();

		param.put("userId", userId);
		param.put("apId", apId);

		HashMap obtainTipsInfoPic = countryManager.obtainActivityInfoPic(apId);// 获得该照片的存储地址

		// 找到是谁的图片，保存到消息表中
		String initiativeId = countryManager.obtainActivityInitiativeId(apId);// 获取主动的ID
		param.put("initiativeId", initiativeId);
		param.put("type", NotificationListType.ACTIVITY_PICTURE_PRAISED);
		countryManager.deleteActivityPicturePraiseNotification(param);
	}

	@Override
	public BaseBean addTipsPraise(String userId, String tipsId)
	{
		BaseBean bean = new BaseBean();
		HashMap param = new HashMap();

		param.put("praiseTime", Commonparam.Date2Str());
		param.put("userId", userId);
		param.put("tipsId", tipsId);
		countryManager.addTipsPraise(param);// 增加点赞

		HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片
		HashMap obtainTipsInfoPic = countryManager.obtainTipsInfoPic(Long.valueOf(tipsId));// 获得贴士昵称和图片

		String content;
		content = obtainUserInfo.get("nickname") + "[replace]";

		param.put("addTime", Commonparam.Date2Str());
		param.put("content", content);
		param.put("headPicture", obtainTipsInfoPic.get("headPicture"));
		param.put("p_width", obtainTipsInfoPic.get("p_width"));
		param.put("p_height", obtainTipsInfoPic.get("p_height"));

		// 找到是谁的图片，保存到消息表中
		String initiativeId = countryManager.obtainInitiativeId(tipsId);// 获取主动的ID
		param.put("initiativeId", initiativeId);
		param.put("type", NotificationListType.PICTURE_PRAISED);
		if (!initiativeId.equals(userId)) {
			countryManager.addNotificationMessage(param);// 保存上面的消息到推送表中
		}

		bean.setStatus(200);
		bean.setMsg("添加成功");

		return bean;
	}

	/*###############################2.0新增############################*/
	@Override
	public void deleteNoteCommentById(String commentId) {
		travelManager.deleteCommentById(commentId);
	}

	@Override
	public void addNoteComment(String pictureId, String userId, String commentContent, String c_nickname, String c_userId) {
		HashMap param=new HashMap();
		param.put("commentsTime", Commonparam.Date2Str());
		param.put("commentsContent", commentContent);
		param.put("userId", userId);
		param.put("c_userId", c_userId);
		param.put("c_nickname", c_nickname);
		param.put("tipsId", pictureId); //照片ID

		if (StringUtils.isNotBlank(c_nickname) && StringUtils.isNotBlank(c_userId))
		{//回复评论
			travelManager.addNoteComments(param);// 增加 回复评论
			param.put("commentNum", 1);
			travelManager.updatePictureComments(param);
			travelManager.updateNoteComments(param);
		}
		else
		{
			travelManager.addNotesComment2(param);//仅评论
			param.put("commentNum", 1);
			travelManager.updatePictureComments(param);
			travelManager.updateNoteComments(param);
		}

		HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片

		String content;
		HashMap obtainPicInfo = countryManager.obtainPicInfo(Long.valueOf(pictureId));// 获得该照片的存储地址

		param.put("headPicture", obtainPicInfo.get("squarePicture"));
		param.put("squareSide",obtainPicInfo.get("squareSide"));
		param.put("addTime", Commonparam.Date2Str());

		if (StringUtils.isNotBlank(c_nickname) && StringUtils.isNotBlank(c_userId))
		{
			param.put("content", commentContent);
			param.put("initiativeId", c_userId); //userId回复了c_userId的评论
			param.put("type", NotificationListType.COMMENT_REPLIED);
			countryManager.addNotificationMessage(param); //保存回复评论的消息到推送表
		}
		else
		{
			content = obtainUserInfo.get("nickname") + "[replace]";
			param.put("content", content);
			String initiativeId = countryManager.obtainInitiativeId(pictureId);// 获取主动的ID
			param.put("initiativeId", initiativeId);
			param.put("type", NotificationListType.PICTURE_COMMENTED);
			if (!initiativeId.equals(userId))
			{
				countryManager.addNotificationMessage(param);
			}
		}
	}

	@Override
	public void addNotePraise(String pictureId, String userId) {
		HashMap param = new HashMap();

		param.put("praiseTime", Commonparam.Date2Str());
		param.put("userId", userId);
		param.put("tipsId", pictureId);
		countryManager.addTipsPraise(param);// 增加点赞

		HashMap obtainUserInfo = countryManager.obtainUserInfo(Long.valueOf(userId));// 获得用户昵称和图片
		HashMap obtainPicInfo = countryManager.obtainPicInfo(Long.valueOf(pictureId));// 获得贴士昵称和图片

		String content;
		content = obtainUserInfo.get("nickname") + "[replace]";

		param.put("addTime", Commonparam.Date2Str());
		param.put("content", content);
		param.put("headPicture", obtainPicInfo.get("squarePicture"));
		param.put("squareSide",obtainPicInfo.get("squareSide"));
		// 找到是谁的图片，保存到消息表中
		String initiativeId = countryManager.obtainInitiativeId(pictureId);// 获取主动的ID
		param.put("initiativeId", initiativeId);
		param.put("type", NotificationListType.PICTURE_PRAISED);
		if (!initiativeId.equals(userId)) {
			countryManager.addNotificationMessage(param);// 保存上面的消息到推送表中
		}
	}

	@Override
	public List<CommentModel> obtainCommentsByPictureId(CommentBean commentBean) {
		return commentsManager.getCommentModelsByPictureId(commentBean);
	}

	@Override
	public List<PraiseModel> obtainPraiseByPictureId(PraiseBean bean) {
		return commentsManager.getPraiseModelsByPictureId(bean);
	}

	@Override
	public BaseBean cancelTipsPraise(String userId, String tipsId)
	{
		BaseBean bean = new BaseBean();
		HashMap param = new HashMap();

		param.put("userId", userId);
		param.put("tipsId", tipsId);
		countryManager.cancelTipsPraise(param);// 增加点赞


		bean.setStatus(200);
		bean.setMsg("取消成功");

		return bean;
	}
}
