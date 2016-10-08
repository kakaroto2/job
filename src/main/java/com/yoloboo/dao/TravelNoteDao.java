package com.yoloboo.dao;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.controller.BaseBean.TopicBean;
import com.yoloboo.controller.BaseBean.UserBean;
import com.yoloboo.models.TravelNote;
import com.yoloboo.models.TravelNoteModel;
import com.yoloboo.models.TravelNotePic;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public interface TravelNoteDao
{
	TravelNoteModel getModelByPK(Long id);

	TravelNoteModel getModelForHomePage(Long id);

	List<TravelNoteModel> getModelForHomePageByUser(UserBean user);

	List<TravelNoteModel>  getModelsByTopicUser(TopicBean bean);

	List<Long> selectRelationNotesLocation(PictureBean bean);

	List<Long> selectRelationNotesLocation2(List<Long> idList);

	List<TravelNoteModel> getUnPublishedNoteForHome(Long subjectId);

	Long getSubjectIdByTime(String nowTime);

	void pushNote (String noteId);

	TravelNote searchTravelNoteByID(String noteId);

	TravelNotePic getCoverPicByNote(String noteId);

	void addNotification(HashMap param);
}
