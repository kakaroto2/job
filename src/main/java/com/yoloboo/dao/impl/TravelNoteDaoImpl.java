package com.yoloboo.dao.impl;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.controller.BaseBean.TopicBean;
import com.yoloboo.controller.BaseBean.UserBean;
import com.yoloboo.dao.BaseDao;
import com.yoloboo.dao.TravelNoteDao;
import com.yoloboo.models.TravelNote;
import com.yoloboo.models.TravelNoteModel;
import com.yoloboo.models.TravelNotePic;
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
	public List<TravelNoteModel> getUnPublishedNoteForHome(Long subjectId) {
		return (List<TravelNoteModel>) sqlSession.selectList("TravelNoteDao.getUnPublishedNoteForHome",subjectId) ;
	}

	@Override
	public Long getSubjectIdByTime(String nowTime) {
		return (Long)sqlSession.selectOne("TravelNoteDao.getSubjectIdByTime",nowTime);
	}

	@Override
	public void pushNote(String noteId) {
		sqlSession.update("TravelNoteDao.pushNote",noteId);
	}

	@Override
	public TravelNote searchTravelNoteByID(String noteId) {
		return (TravelNote)sqlSession.selectOne("TravelNoteDao.searchTravelNoteByID",noteId);
	}

	@Override
	public TravelNotePic getCoverPicByNote(String noteId) {
		return (TravelNotePic)sqlSession.selectOne("TravelNoteDao.getCoverPicByNote",noteId);
	}

	@Override
	public void addNotification(HashMap param) {
		sqlSession.insert("TravelNoteDao.addNotification",param);
	}
}
