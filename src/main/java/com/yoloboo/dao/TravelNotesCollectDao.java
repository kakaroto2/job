package com.yoloboo.dao;

import com.yoloboo.controller.BaseBean.NotesCollectBean;
import com.yoloboo.models.NotesCollectModel;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/20.
 */
public interface TravelNotesCollectDao
{
	void insetNoteCollect(NotesCollectBean bean);

	void removeNoteCollect(NotesCollectBean bean);

	NotesCollectModel getModelByNoteAndUser(NotesCollectBean bean);

	List<NotesCollectModel> getModelsByUser(long userId);
}
