package com.yoloboo.service;

import com.yoloboo.controller.BaseBean.NotesCollectBean;
import com.yoloboo.excptions.DuplicateException;
import com.yoloboo.models.NotesCollectModel;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/20.
 */
public interface TravelNotesCollectService
{
	void collectTravelNote(NotesCollectBean bean) throws DuplicateException;

	void removeNoteCollect(NotesCollectBean bean);

	List<NotesCollectModel> getModelsByUser(long userId);
}
