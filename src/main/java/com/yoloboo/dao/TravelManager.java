package com.yoloboo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




import com.json.BaseBean;
import com.yoloboo.models.PictureModel;


public interface TravelManager {

    void editIsnocover(HashMap param);//修改封面

	List obtainTravelStyle(int version);//获得旅行风格

	List obtainTravelTheme();//获得旅行主题
	
	List inputAddressQuery(String content);//常用地址查询
	
	List obtainNotesTips();//获取贴士标签
	
	List obtainMoodTips();//获取心情标签
	
	Long addTravelNotes(HashMap param);//添加旅行游记

	String newPictureTips(HashMap param);//新增贴士
	
	void moodTipsPicture(HashMap param);//新增心情比标签和贴士关系

	void notesTipsPicture(HashMap param);//新增贴士标签和贴士关系
	
	void deletePictureTips(HashMap param);//删除图片
	
	void deleteTravelPraiseAndCommentsByPictureId(HashMap param);//根据图片id根据
	
	void modifyPictureTips(HashMap param);//编辑贴士
	
	void deleteNotesTips(HashMap param);//删除所有与贴士相关的心情标签和贴士标签
	void deleteNotesMoodTips(HashMap param);//删除所有与贴士相关的心情标签和贴士标签
	
	void modifyNotes(HashMap param);//编辑游记
	
	 List<HashMap> obtainQuestion();//问题

//	void travelNotesRead(HashMap param);//把游记阅读关系表改一下，每个好友的都改一下
	
	HashMap obtainQuestionType(String questionId);//获得问题是否是国家贴士，是的话保存该条数据
	
	void addTipsDataContent(HashMap param);//添加贴士列表内容
	
	void addPitureToTipsDataContent(HashMap param); //贴士贴士到贴士列表中，这个是 图片的
	
	void editPitureToTipsDataContent(HashMap param); //编辑图片
	
	void addNotificationMessageWithFriends(HashMap param);//把每个好友中的通知全部放到这个里面
	
	void addNotificationTipsWithFriends(HashMap param);//新建贴士时候--把每个好友中的通知全部放到这个里面
	
	void addNotificationCompeleteWithFriends(HashMap param);//完成贴士的时候，可能有两种可能，一种是完成了，还有可能是新的国家，如果是朋友圈中新的国家新国家也要显示

	void questionAnswer(String userId, String travelNotesId, String countyId,String answerList);//问题答案

	void addCommoneAddress(String address);

	Object findNoteCountryIdByNotesId(String travelNotesId);

	Object findNoteCountryIdByPictureId(String pictureTipsId);
	
	Object findTravelNotesIdByPictureId(String pictureTipsId);

	void deletePitureToTipsDataContent(HashMap param2);

	HashMap findPicturePath(String pictureTipsId);


	void softDeleteNotes(HashMap param);

	List<HashMap> softDeletePic(HashMap param);

	void deleteTips(HashMap param);

	void deleteCommentPrasieById(HashMap param);

	void softDeleteById(HashMap param);

	List<HashMap> obtainUrlList();

	void updatePicWH(HashMap map);

	void deleteQuesByNoteId(HashMap param);

	void deleteTipsById(HashMap param);

	List<HashMap> obtainNotifiList();

	void updatePicId(HashMap map);

	List<HashMap> obtainNotesList();

	List<HashMap> obtainPicByNoteId(HashMap map);

	void updateNotesInfo(HashMap param);

	List<HashMap> obtainUserList();

	HashMap updatePicPriase(HashMap m1);

	List<HashMap> obtainPicInfo();

	void updateContent(HashMap map);

	List<HashMap> obtainTipsInfo();

	void updateTipsContent(HashMap map);

	void deletePraiseById(HashMap map);

	void deleteCommentById(String commentId);

	List<HashMap> obtainPicDetails();


	void updatePicSquare(HashMap map);
	/*############2.0新增################*/
	List obtainTravelStyleById(String userId);

	Long obtainTravelNotesCount(String id);

	List inputLocationQuery(String replace);

	List inputLocationOrCountryQuery(String replace);

	List obtainCategory();

	String insertNewNote(HashMap param);

	String insertNewPicture(HashMap param);

	String obtainCountryByLocation(String locationId);

	String obtainCountryByNote(String travelNoteId);

	void modifyTravelNote(HashMap param);

	String obtainPictureById(String pictureId);

	void deleteTravelNoteById(String travelNoteId);

	List<HashMap> deletePicturesById(String travelNoteId);

	void deleteNoteCollectById(String travelNoteId);

	String obtainUserByNote(String travelNoteId);

	void updateUserRead(HashMap map);

	String obtainNoteByPicture(String pictureId);

	long obtainPictureNumByNote(String travelNoteId);

	void softDeletePictureById(String pictureId);

	void updateNoteCommentPraise(HashMap param);

	void addNoteComments(HashMap param);

	void updatePictureComments(HashMap param);

	void updateNoteComments(HashMap param);

	void addNotesComment2(HashMap param);


	List<HashMap> obtainPicInfoList(String travelNoteId);

	long judgeIsCollect(HashMap param);

	HashMap obtainTravelNoteInfo(String travelNoteId);

	void addNoteRead(String travelNoteId);

	void addUserRead(String userId);

	void addCollectNote(HashMap param);

	void addNotificationNewNote(HashMap param);//推送消息表

	HashMap obtainNotesInfoToTips(String  travelNoteId);

	List<HashMap> obtainOneNotesTipsList(HashMap param);

	HashMap obtainCoverPicture(Long aLong);

	Object findNoteMoodList(String tipsId);

	Object findNoteStyleList(String tipsId);

	Object findNotePraiseList(String tipsId);

	PictureModel getPictureModelsByPictureId(String pictureId);

	PictureModel getPictureModelsByNoteId(String noteId);

	void uploadTravelNote(HashMap param);

	void clearCoverPicture(String travelNoteId);

	Integer autoCheckCover(String travelNoteId);

	Integer getLastSort(String travelNoteId);
}
