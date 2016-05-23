package com.yoloboo.service;

import com.json.BaseBean;
import com.yoloboo.controller.bean.CommentBean;
import com.yoloboo.controller.bean.PraiseBean;
import com.yoloboo.models.CommentModel;
import com.yoloboo.models.PraiseModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Created by ZHOU005 on 2015/12/28.
 */
public interface CommentsService
{
	BaseBean addTipsComments(String tipsId, String userId, String commentsContent, String c_userId, String c_nickname);

	BaseBean addTipsPraise(String userId,String tipsId);

	BaseBean cancelTipsPraise(String userId,String tipsId);

	void deleteNoteCommentById(String commentId);

	void addNoteComment(String pictureId, String userId, String commentContent, String c_nickname, String c_userId);

	void addNotePraise(String pictureId, String userId);

	List<CommentModel> obtainCommentsByPictureId(CommentBean commentBean);

	List<PraiseModel> obtainPraiseByPictureId(PraiseBean bean);
	//活动照片的评论加入到消息表中
	void addActivityPictureCommentsNotification(Long apId, Long userId, String commentsContent, Long c_userId, String c_nickname);
	//活动照片的点赞加入到消息表中
	void addActivityPicturePraiseNotification(Long apId, Long userId, String userPicture, String userName);
	// 活动图片被取消点赞后需要删除消息表中的记录
	void deleteActivityPicturePraiseNotification(Long apId, Long userId, String userPicture, String userName);
}
