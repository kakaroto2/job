package com.yoloboo.service.impl;

import com.yoloboo.controller.BaseBean.NotesCollectBean;
import com.yoloboo.dao.TravelNotesCollectDao;
import com.yoloboo.excptions.DuplicateException;
import com.yoloboo.models.NotesCollectModel;
import com.yoloboo.models.TravelNoteModel;
import com.yoloboo.service.TravelNotesCollectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/20.
 */

@Service
@Transactional
public class TravelNotesCollectServiceImpl implements TravelNotesCollectService
{

	@Resource
	private TravelNotesCollectDao travelNotesCollectDao;

	@Override
	public void collectTravelNote(NotesCollectBean bean) throws DuplicateException
	{
		NotesCollectModel model = travelNotesCollectDao.getModelByNoteAndUser(bean);

		if (null != model)
		{
			throw new DuplicateException("text.message.note.collect.duplicate");
		}

		travelNotesCollectDao.insetNoteCollect(bean);
	}

	@Override
	public void removeNoteCollect(NotesCollectBean bean)
	{
		travelNotesCollectDao.removeNoteCollect(bean);
	}

	@Override
	public List<NotesCollectModel> getModelsByUser(long userId)
	{
		return travelNotesCollectDao.getModelsByUser(userId);
	}

}
