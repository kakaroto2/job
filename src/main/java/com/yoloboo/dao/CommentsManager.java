package com.yoloboo.dao;

import com.yoloboo.controller.bean.CommentBean;
import com.yoloboo.controller.bean.PraiseBean;
import com.yoloboo.models.CommentModel;
import com.yoloboo.models.PraiseModel;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/11.
 */
public interface CommentsManager
{
	List getComments();

	List<CommentModel> getCommentModelsByPictureId(CommentBean commentBean);

	List<PraiseModel> getPraiseModelsByPictureId(PraiseBean bean);
}
