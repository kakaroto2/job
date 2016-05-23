package com.yoloboo.dao.impl;

import com.yoloboo.controller.bean.CommentBean;
import com.yoloboo.controller.bean.PraiseBean;
import com.yoloboo.dao.CommentsManager;
import com.yoloboo.dao.CountryManager;
import com.yoloboo.models.CommentModel;
import com.yoloboo.models.PraiseModel;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/11.
 */

@Repository
public class CommentsManagerImpl extends BaseDao implements CommentsManager
{

	@Override
	public List getComments()
	{
		return (List) sqlSession.selectList("comments.getComments");
	}

	@Override
	public List<CommentModel> getCommentModelsByPictureId(CommentBean commentBean) {
		return (List<CommentModel>) sqlSession.selectList("comments.getCommentModelsByPictureId",commentBean);
	}

	@Override
	public List<PraiseModel> getPraiseModelsByPictureId(PraiseBean bean) {
		return (List<PraiseModel>) sqlSession.selectList("PraiseDao.getPraiseModelsByPictureId",bean);
	}

}
