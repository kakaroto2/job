package com.yoloboo.service;

import com.yoloboo.controller.BaseBean.UserBean;
import com.yoloboo.excptions.ActionPermissionDelayException;
import com.json.BaseBean;
import com.yoloboo.controller.BaseBean.TravelNoteBean;

import com.yoloboo.models.PictureModel;
import com.yoloboo.models.ThemeModel;
import com.yoloboo.models.TravelNoteModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2015/12/28.
 */
public interface TravelNotesService
{
	BaseBean addTravelNotes(TravelNoteBean bean);

	BaseBean modifyPictureTips(TravelNoteBean bean);

	List inputLocationQuery(String replace);

	List inputLocationOrCountryQuery(String replace);

	List obtainCategory();

	HashMap newTravelNote(String userId, String noteName, String locationId, String themeId, String noteDescription, String qiniukey,String squareSide, String picDate, String picDescription, String isnoCover, String picAddress, String travelTipsId, String moodTipsId);

	HashMap newNotePicture(String userId,String qiniukey, String travelNoteId, String picAddress, String picDate, String picDescription, String isnoCover, String squareSide, String travelTipsId, String moodTipsId);

	void modifyTravelNote(String travelNoteId, String locationId, String themeId,  String noteName, String noteDescription);

	HashMap modifyNotePicture(String userId,String qiniukey, String travelNoteId, String pictureId, String picDate, String picDescription, String picAddress, String isnoCover, String squareSide, String travelTipsId, String moodTipsId);

	void deleteTravelNote(String travelNoteId);

	void deleteNotePicture(String pictureId) throws ActionPermissionDelayException;

	List<HashMap> obtainPicturesByNote(String travelNoteId,String userId);

	void collectNote(String userId, String travelNoteId);

	List<TravelNoteModel> getNotesForHomePage(UserBean userBean);

	ThemeModel getCategoryNotes(UserBean userBean);

	PictureModel obtainPictureInfoByPictureId(String pictureId);

	TravelNoteModel obtainNoteInfoByNoteId(String travelNoteId);

	void uploadTravelNote(String noteName,String travelNoteId,String userId,String locationId,Date now);
}
