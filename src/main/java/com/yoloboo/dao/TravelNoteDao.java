package com.yoloboo.dao;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.controller.BaseBean.TopicBean;
import com.yoloboo.controller.BaseBean.UserBean;
import com.yoloboo.models.TravelNoteModel;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public interface TravelNoteDao
{
	TravelNoteModel getModelByPK(Long id);

	TravelNoteModel getModelForHomePage(Long id);

	List<TravelNoteModel> getModelForHomePageByUser(UserBean user);

	List<TravelNoteModel>  getModelsByTopicUser(TopicBean bean);

	Integer countPushedTravelNotes(Long userId);

	Integer isNoteReported(Long travelNoteId);

	Integer isPicReported(Long pId);

	List<Long> selectRelationNotesCountry(PictureBean bean);

	List<Long> selectRelationNotesLocation(PictureBean bean);

	List<Long> selectRelationNotesLocation2(List<Long> idList);

	String getTitleByNoteId(Long travelNoteId);

	String getPictureByNoteId(Long travelNoteId);
}
