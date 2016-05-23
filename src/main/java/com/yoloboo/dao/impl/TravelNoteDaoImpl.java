package com.yoloboo.dao.impl;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.controller.BaseBean.TopicBean;
import com.yoloboo.controller.BaseBean.UserBean;
import com.yoloboo.dao.BaseDao;
import com.yoloboo.dao.TravelNoteDao;
import com.yoloboo.models.TravelNoteModel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/17.
 */

@Repository
public class TravelNoteDaoImpl extends BaseDao implements TravelNoteDao
{
	@Override
	public TravelNoteModel getModelByPK(Long id)
	{
		return (TravelNoteModel) sqlSession.selectOne("TravelNoteDao.getModelByPK", id);
	}

	@Override
	public TravelNoteModel getModelForHomePage(Long id)
	{
		return (TravelNoteModel) sqlSession.selectOne("TravelNoteDao.getModelForHomePage", id);
	}

	@Override
	public List<TravelNoteModel> getModelForHomePageByUser(UserBean user)
	{
		return (List<TravelNoteModel>) sqlSession.selectList("TravelNoteDao.getModelForHomePageByUser", user);
	}

	@Override
	public List<TravelNoteModel> getModelsByTopicUser(TopicBean bean)
	{
		return (List<TravelNoteModel>) sqlSession.selectList("TravelNoteDao.selectModelsByTopicUser", bean);
	}

	@Override
	public Integer countPushedTravelNotes(Long userId) {
		return (Integer) sqlSession.selectOne("TravelNoteDao.countPushedTravelNotes", userId);
	}

	@Override
	public Integer isNoteReported(Long travelNoteId) {
		return (Integer) sqlSession.selectOne("TravelNoteDao.isNoteReported", travelNoteId);
	}

	@Override
	public Integer isPicReported(Long pId) {
		return (Integer) sqlSession.selectOne("TravelNoteDao.isPicReported", pId);
	}

	@Override
	public List<Long> selectRelationNotesCountry(PictureBean bean)
	{
		return (List<Long>) sqlSession.selectList("TravelNoteDao.selectRelationNotesCountry", bean);
	}

	@Override
	public List<Long> selectRelationNotesLocation(PictureBean bean)
	{
		return (List<Long>) sqlSession.selectList("TravelNoteDao.selectRelationNotesLocation", bean);
	}

	@Override
	public List<Long> selectRelationNotesLocation2(List<Long> idList)
	{
		return (List<Long>) sqlSession.selectList("TravelNoteDao.selectRelationNotesLocation2", idList);
	}

	@Override
	public String getTitleByNoteId(Long travelNoteId) {
		return (String) sqlSession.selectOne("TravelNoteDao.getTitleByNoteId", travelNoteId);
	}

	@Override
	public String getPictureByNoteId(Long travelNoteId) {
		return (String) sqlSession.selectOne("TravelNoteDao.getPictureByNoteId", travelNoteId);
	}
}
