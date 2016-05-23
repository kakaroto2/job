package com.yoloboo.dao.impl;

import com.yoloboo.controller.BaseBean.NotesCollectBean;
import com.yoloboo.dao.impl.BaseDao;
import com.yoloboo.dao.TravelNotesCollectDao;
import com.yoloboo.models.NotesCollectModel;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/20.
 */

@Repository
public class TravelNotesCollectDaoImpl extends BaseDao implements TravelNotesCollectDao
{

	@Override
	public void insetNoteCollect(NotesCollectBean bean)
	{
		sqlSession.insert("NotesCollectDao.insetNoteCollect", bean);
	}

	@Override
	public void removeNoteCollect(NotesCollectBean bean)
	{
		sqlSession.delete("NotesCollectDao.removeNoteCollect", bean);
	}

	@Override
	public NotesCollectModel getModelByNoteAndUser(NotesCollectBean bean)
	{
		return (NotesCollectModel) sqlSession.selectOne("NotesCollectDao.getModelByNoteAndUser", bean);
	}

	@Override
	public List<NotesCollectModel> getModelsByUser(long userId)
	{
		return (List<NotesCollectModel>) sqlSession.selectList("NotesCollectDao.getModelsByUser", userId);
	}

}
