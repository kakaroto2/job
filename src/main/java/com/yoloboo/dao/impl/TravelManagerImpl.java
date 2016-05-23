package com.yoloboo.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.common.constans.NotificationListType;
import com.yoloboo.models.PictureModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.common.Commonparam;
import com.yoloboo.dao.TravelManager;

@Repository
public class TravelManagerImpl extends BaseDao implements TravelManager {

	@SuppressWarnings("rawtypes")
	@Override
	public void uploadTravelNote(HashMap param) {
		sqlSession.update("travel.uploadTravelNote",param);
	}

	@Override
	public void clearCoverPicture(String travelNoteId) {
		sqlSession.update("travel.clearCoverPicture",travelNoteId);
	}

	@Override
	public Integer autoCheckCover(String travelNoteId) {
		Integer noteHasCoverPicture = (Integer) sqlSession.selectOne("travel.noteHasCoverPicture",travelNoteId);
		if(noteHasCoverPicture == 0){
			Long topPicId = (Long) sqlSession.selectOne("travel.getTopPic",travelNoteId);
			sqlSession.update("travel.editIsnocoverPicture",topPicId);
			return 1;
		}
		return 0;
	}

	@Override
	public Integer getLastSort(String travelNoteId) {
		Integer sort = (Integer) sqlSession.selectOne("travel.getLastSort",travelNoteId);
	    return sort;
	}
	//编辑是否是封面
	@Override
	public void editIsnocover(HashMap param) {
		sqlSession.update("travel.editIsnocoverTravel", param.get("travelNotesId"));//把所有的游记是封面取消
		sqlSession.update("travel.editIsnocoverPicture", param.get("pictureTipsId"));//把这个贴士改为游记的封面
	}
		
	// 获得旅行风格
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainTravelStyle(int version) {
		return (List) sqlSession.selectList("travel.obtainTravelStyle", version);
	}

	
	// 获得旅行主题
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainTravelTheme() {
		return (List) sqlSession.selectList("travel.obtainTravelTheme");
	}
 
	// 常用地址查询
	@SuppressWarnings("rawtypes")
	@Override
	public List inputAddressQuery(String content) {
		return (List) sqlSession.selectList("travel.inputAddressQuery",content);
	}

	// 获取贴士标签
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainNotesTips() {
		return (List) sqlSession.selectList("travel.obtainNotesTips");
	}

	// 获取心情标签
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainMoodTips() {
		return (List) sqlSession.selectList("travel.obtainMoodTips");
	}
	
	//添加游记并获得游记ID
	@SuppressWarnings("rawtypes")
	@Override
	public synchronized Long addTravelNotes(HashMap param) {
		sqlSession.insert("travel.insertTravelNotes", param);
		return (Long) sqlSession.selectOne("travel.obtainTravelNotesId",param);
	}

	
	//添加贴士并且获得到贴士ID
	@SuppressWarnings("rawtypes")
	@Override
	public String newPictureTips(HashMap param) {
		sqlSession.insert("travel.newPictureTips", param);
		return (String) sqlSession.selectOne("travel.obtainPictureTipsId",param);
	}
	 
	
	//新增心情比标签和贴士关系
	@SuppressWarnings("rawtypes")
	@Override
	public void moodTipsPicture(HashMap param) {
		sqlSession.insert("travel.moodTipsPicture", param);
	}
	 
	//新增贴士标签和贴士关系
	@SuppressWarnings("rawtypes")
	@Override
	public void notesTipsPicture(HashMap param) {
		sqlSession.insert("travel.notesTipsPicture", param);
	}

	//删除贴士
	@Override
	public void deletePictureTips(HashMap param) {
		sqlSession.update("travel.deletePictureTips", param);
		sqlSession.update("travel.deletePictureTipsUpdatePraise", param);
		sqlSession.update("travel.deletePictureTipsUpdateComments", param);
	}
	
	@Override
	public void deleteTravelPraiseAndCommentsByPictureId(HashMap param) {
		sqlSession.update("travel.deletePictureTipsUpdatePraise", param);
		sqlSession.update("travel.deletePictureTipsUpdateComments", param);
	}
	
	//编辑贴士
	@Override
	public void modifyPictureTips(HashMap param) {
//		String pictureTipsId=(String) param.get("pictureTipsId");
//		String  travelNoteId=(String) sqlSession.selectOne("travel.TravelIdByTipsId", pictureTipsId);//把所有的游记是封面取消
//		sqlSession.update("travel.editIsnocoverTravel", travelNoteId);//把所有的游记是封面取消
		sqlSession.update("travel.modifyPictureTips", param);
	}
	
	
	
	//删除所有与贴士相关贴士标签
	@Override
	public void deleteNotesTips(HashMap param) {
		sqlSession.delete("travel.deleteNotesTipsPicture", param);
	}
	//删除所有与贴士相关的的心情标签
	@Override public void deleteNotesMoodTips(HashMap param) {
		sqlSession.delete("travel.deleteMoodTipsPicture", param);
	}
	
	//编辑游记
	@Override
	public void modifyNotes(HashMap param) {
		sqlSession.update("travel.modifyNotes", param);
	}
	
	// 问题
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> obtainQuestion() {
		return ( List<HashMap>) sqlSession.selectList("travel.obtainQuestion");
	}


	// 问题答案
	@SuppressWarnings("rawtypes")
	@Override
	public void questionAnswer(String userId,String travelNotesId,String countryId,String answerList) {
		JSONArray array=JSON.parseArray(answerList);
		for(int h=0;h<array.size();h++){
			HashMap<String,Object> param = new HashMap<String,Object>();
			param.put("userId", userId);
			param.put("travelNotesId", travelNotesId);
			param.put("questionId", array.getJSONObject(h).get("questionId"));
			param.put("content", array.getJSONObject(h).get("content"));
			param.put("answer", array.getJSONObject(h).get("answer"));
			sqlSession.insert("travel.questionAnswer",param);
			
			HashMap questionMap=obtainQuestionType(array.getJSONObject(h).get("questionId").toString());//获得问题是否是属于国家贴士
			if(array.getJSONObject(h).get("type").toString().equals("1")){
			  String tipsContent	= array.getJSONObject(h).get("content")+"\n"+array.getJSONObject(h).get("answer") ;
			  param.put("countryTipsId", array.getJSONObject(h).get("countryTipsId"));
			  param.put("tipsContent", tipsContent);
			  param.put("tcDate", Commonparam.Date2Str());
			  param.put("countryId", countryId);
			  addTipsDataContent(param);//属于贴士的话要保存到贴士数据的那个表面//0否(默认)
				//1是
			}
			
			 
		}
		
	}
	
	
	// 把游记阅读关系表改一下，每个好友的都改一下
	
	@Transactional
	@Override
	public void addCommoneAddress(String address) {
		// TODO Auto-generated method stub
		int num= (int) sqlSession.selectOne("User.isCommeneAddress", address);
		if (num==0){
			sqlSession.insert("User.addCommoneAddress",address);
		}
//		try {
//			sqlSession.insert("User.addCommoneAddress",address);
//		} catch (Exception e) {
//			LogException.printException(e);
//		}
	}


	@Override
	public Object findNoteCountryIdByNotesId(String travelNotesId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("travel.findNoteCountryIdByNotesId",travelNotesId);
	}


	@Override
	public Object findNoteCountryIdByPictureId(String pictureTipsId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("travel.findNoteCountryIdByPictureId",pictureTipsId);
	}

	@Override
	public Object findTravelNotesIdByPictureId(String pictureTipsId) {
		return sqlSession.selectOne("travel.findTravelNotesIdByPictureId",pictureTipsId);
	}
	
	
	//
//	@SuppressWarnings("rawtypes")
//	@Override
//	public void travelNotesRead(HashMap param) {
//		//判断是不是达人，或者官方账号，如果是的，把所有的全部修改了
//		//如果是达人或者官方，需要把所有会员的阅读改为未读，如果这个好友还没有数据阅读过该国家，还应该再去新增一条记录，如果不是应该是好友的
//		//userId=param.get("userId");
//		Integer uType= (Integer) sqlSession.selectOne("User.judgeMaster",param.get("userId"));//查询是否是达人或者官方账号
//		List<HashMap> userIdList;
//		if(uType==1){//读取好友的
//			userIdList=(List<HashMap>) sqlSession.selectList("friends.friendsUserList",param.get("userId"));
//		}
//		else
//		{//读取所有的
//		   userIdList=(List<HashMap>) sqlSession.selectList("friends.allUserList");
//		}
//		HashMap param1= new HashMap();
//		//把所有需要阅读关系都更新了
//		for(int i=0;i<userIdList.size();i++){
//			param1.clear();
//			param1.put("userId", userIdList.get(i).get("userId"));
//			param1.put("countryId", param.get("countryId"));
//			sqlSession.insert("friends.insertTravelNotesRead",param1);
//			sqlSession.update("friends.updateTravelNotesRead",param1);
//		}
//		
//		 
//	}
	
	@Override
	public void deletePitureToTipsDataContent(HashMap param) {
		// TODO Auto-generated method stub

		 sqlSession.delete("tips.deleteTravelNotesTipsPicture",param);
	}


	//获得问题是否是国家贴士，是的话保存该条数据
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap obtainQuestionType(String questionId) {
		return (HashMap) sqlSession.selectOne("travel.obtainTipsPictureType",questionId);
	} 
	
	
	//添加贴士列表内容
	@SuppressWarnings("rawtypes")
	@Override
	public void addTipsDataContent(HashMap param) {
		 sqlSession.insert("tips.addTipsDataContent", param);
	}

	//贴士贴士到贴士列表中，这个是 图片的
	@SuppressWarnings("rawtypes")
	@Override
	public void addPitureToTipsDataContent(HashMap param) {
		
		 sqlSession.insert("tips.addPitureToTipsDataContent", param);
	}
	
	//编辑图片
	@SuppressWarnings("rawtypes")
	@Override
	public void editPitureToTipsDataContent(HashMap param) {
		 sqlSession.delete("tips.editPitureToTipsDataContent", param);
	}
	
	
	//把每个好友中的通知全部放到这个里面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void addNotificationMessageWithFriends(HashMap param) {
			
		 //获取该用户所有好友
			List<HashMap> userIdList=(List<HashMap>) sqlSession.selectList("friends.friendsUserList",param.get("userId"));
			//获取该用户的信息
			HashMap param2=(HashMap) sqlSession.selectOne("country.obtainUserInfo",param.get("userId"));
			
			HashMap param1= new HashMap();
			for(int i=0;i<userIdList.size();i++){
				param1.clear();
				param1.put("initiativeId", userIdList.get(i).get("userId"));
				param1.put("userId", param.get("userId"));
				param1.put("addTime", Commonparam.Date2Str());
				String content;
				content=param2.get("nickname")+"[replace]["+param.get("countryId")+"][replace2]";
				param1.put("content",content);
				param1.put("type", 5);
				param1.put("tipsId",param.get("pictureTipsId"));
				param1.put("headPicture", param.get("picture"));

				//自己发表note不会收到推送通知
				if(!param.get("userId").equals( userIdList.get(i).get("userId"))){
					sqlSession.insert("country.addNotificationMessage",param1);
				}

			}
		}
		
		//新建贴士的时候把每个好友中的通知全部放到这个里面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void addNotificationTipsWithFriends(HashMap param) {
			//获取用户ID
			HashMap param3= (HashMap) sqlSession.selectOne("friends.obtainByTravelNotesId",param.get("travelNotesId"));
			//根据该用户ID获取好友列表
			List<HashMap> userIdList= (List<HashMap>) sqlSession.selectList("friends.friendsUserList", param3.get("userId"));
			//获取该用户详细信息
			HashMap param2=(HashMap) sqlSession.selectOne("country.obtainUserInfo",param3.get("userId"));
			
			HashMap param1= new HashMap();
			for(int i=0;i<userIdList.size();i++){
				param1.clear();
				param1.put("initiativeId", userIdList.get(i).get("userId"));
				param1.put("userId", param3.get("userId"));
				param1.put("addTime", Commonparam.Date2Str());
				String content;
				content=param2.get("nickname")+"[replace]["+param3.get("countryId")+"][replace2]";
				param1.put("content",content);
				param1.put("type", 6);
				param1.put("tipsId",param.get("pictureTipsId"));
				param1.put("headPicture", param.get("picture"));
				
				sqlSession.insert("country.addNotificationMessage",param1);
			}
		}
		
		
		//完成游记把每个好友中的通知全部放到这个里面
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void addNotificationCompeleteWithFriends(HashMap param) {
			//获取用户ID
			HashMap param3= (HashMap) sqlSession.selectOne("friends.obtainByTravelNotesId", param.get("travelNotesId"));
			String travelNotesIdStr=(String) param.get("travelNotesId");
			HashMap map1=(HashMap) sqlSession.selectOne("country.obtainHeadPictureByTravelNotesId", travelNotesIdStr);
			String headPicture= map1.get("headPicture").toString();
			List<HashMap> userIdList= (List<HashMap>) sqlSession.selectList("friends.friendsUserList", param3.get("userId"));
			HashMap param2=(HashMap) sqlSession.selectOne("country.obtainUserInfo", param3.get("userId"));
			HashMap picMap=(HashMap) sqlSession.selectOne("travel.obtainPicIdByNotesId", param);
			
			HashMap countryNumMap= new HashMap();
			HashMap param1= new HashMap();
			for(int i=0;i<userIdList.size();i++){
				param1.clear();
				param1.put("initiativeId", userIdList.get(i).get("userId"));
				param1.put("userId", param3.get("userId"));
				param1.put("addTime", Commonparam.Date2Str());
				String content;
				content=param2.get("nickname")+"[replace]["+param3.get("countryId")+"][replace2]";
				param1.put("content",content);
				param1.put("type", 7);
				param1.put("headPicture", headPicture);
				param1.put("p_width", map1.get("p_width"));
				param1.put("p_height", map1.get("p_height"));
				param1.put("tipsId",picMap.get("pictureTipsId"));
				
				sqlSession.insert("country.addNotificationMessage",param1);
				
				//要增加判断，如果是朋友圈新的国家也要推送
				countryNumMap.put("userId", userIdList.get(i).get("userId"));
				countryNumMap.put("countryId", param3.get("countryId"));
				Integer newCountryNum=(Integer) sqlSession.selectOne("friends.obtainnewCountryNum",countryNumMap);
				if(newCountryNum==0){
					param1.put("type", 8);
					sqlSession.insert("country.addNotificationMessage",param1);
				}

			}
		}


		@Override
		public HashMap findPicturePath(String pictureTipsId) {
			// TODO Auto-generated method stub
			return (HashMap) sqlSession.selectOne("travel.findPicturePath",pictureTipsId);
		}

	@Override
	public void softDeleteNotes(HashMap param) {
		sqlSession.delete("travel.softDeleteNotes",param);
	}

	@Override
	public List<HashMap> softDeletePic(HashMap param) {
		sqlSession.update("travel.softDeletePic", param);
		return (List<HashMap>) sqlSession.selectList("travel.obtainPicId", param);
	}

	@Override
	public void deleteTips(HashMap param) {
		sqlSession.delete("travel.deleteMoodTips",param);
		sqlSession.delete("travel.deleteTravelTips",param);
		sqlSession.delete("travel.deleteCountryTips",param);
	}

	@Override
	public void deleteCommentPrasieById(HashMap map1) {
		sqlSession.delete("travel.deleteComments",map1);
		sqlSession.delete("travel.deletePraise", map1);
	}

	@Override
	public void softDeleteById(HashMap param) {
		sqlSession.update("travel.softDeleteById", param);
	}

	@Override
	public List<HashMap> obtainUrlList() {
		return (List<HashMap>) sqlSession.selectList("travel.obtainUrlList");
	}

	@Override
	public void updatePicWH(HashMap map) {
		sqlSession.update("travel.updatePicWH",map);
	}

	@Override
	public void deleteQuesByNoteId(HashMap param) {
		sqlSession.delete("travel.deleteQuesByNoteId",param);
	}

	@Override
	public void deleteTipsById(HashMap param) {
		sqlSession.delete("travel.deleteMoodById",param);
		sqlSession.delete("travel.deleteTipsById", param);
	}

	@Override
	public List<HashMap> obtainNotifiList() {
		return (List<HashMap>) sqlSession.selectList("travel.obtainNotifiList");
	}

	@Override
	public void updatePicId(HashMap map) {
		sqlSession.update("travel.updatePicId", map);
	}

	@Override
	public List<HashMap> obtainNotesList() {
		return (List<HashMap>) sqlSession.selectList("travel.obtainNotesList");
	}

	@Override
	public List<HashMap> obtainPicByNoteId(HashMap map) {
		return (List<HashMap>) sqlSession.selectList("travel.obtainPicByNoteId",map);
	}

	@Override
	public void updateNotesInfo(HashMap param) {
		sqlSession.update("travel.updateNotesInfo",param);
	}

	@Override
	public List<HashMap> obtainUserList() {
		return (List<HashMap>) sqlSession.selectList("travel.obtainUserList");
	}

	@Override
	public HashMap updatePicPriase(HashMap m1) {
		HashMap map= (HashMap) sqlSession.selectOne("travel.countPraise", m1);
		map.put("pictureId",m1.get("pictureId"));
		sqlSession.update("travel.updatePicPriase",map);
		return  map;
	}

	@Override
	public List<HashMap> obtainPicInfo() {
		return (List<HashMap>) sqlSession.selectList("travel.obtainPicInfo");
	}

	@Override
	public void updateContent(HashMap map) {
		sqlSession.update("travel.updateContent",map);
	}

	@Override
	public List<HashMap> obtainTipsInfo() {
		return (List<HashMap>) sqlSession.selectList("travel.obtainTipsInfo");
	}

	@Override
	public void updateTipsContent(HashMap map) {
		sqlSession.update("travel.updateTipsContent",map);
	}

	@Override
	public void deletePraiseById(HashMap map) {
		sqlSession.delete("travel.deletePraiseById",map);
	}

	@Override
	public void deleteCommentById(String commentId) {
		String pictureId= (String) sqlSession.selectOne("travel.selectByCommentId",commentId);
		sqlSession.delete("travel.deleteCommentById", commentId);
		sqlSession.update("travel.updatePictureCommentNum", pictureId);
		sqlSession.update("travel.updateNoteCommentNum", pictureId);
	}

	@Override
	public List<HashMap> obtainPicDetails() {
		return (List<HashMap>) sqlSession.selectList("travel.obtainPicDetails");
	}

	@Override
	public void updatePicSquare(HashMap map) {
		sqlSession.update("travel.updatePicSquare", map);
	}
/*######################2.0新增##################*/
	@Override
	public List obtainTravelStyleById(String userId) {
		return sqlSession.selectList("travel.selectTravelStyleById",userId);
	}

	@Override
	public Long obtainTravelNotesCount(String id) {
		return (Long) sqlSession.selectOne("travel.selectTravelNotesCount",id);
	}

	@Override
	public List inputLocationQuery(String replace) {
		if(replace.matches("[a-zA-Z]*")) {
			return sqlSession.selectList("travel.selectLocationBySubEn", replace);
		}
		else{
			return sqlSession.selectList("travel.selectLocationBySubCn", replace);
		}
	}

	@Override
	public List inputLocationOrCountryQuery(String replace) {
		List res = new ArrayList();
		if(replace.matches("[a-zA-Z]*")) {
			List ll = sqlSession.selectList("travel.selectCountryBySubEn", replace);
			if(ll.size() == 0) {
				ll = sqlSession.selectList("travel.selectLocationBySubEn", replace);
				res.add(ll);
			}
			else{
				res.add(ll);
				res.add(new ArrayList());
			}
			return res;
		}
		else{
			List ll = sqlSession.selectList("travel.selectCountrySubCn", replace);
			if(ll.size() == 0){
				ll = sqlSession.selectList("travel.selectLocationBySubCn", replace);
				res.add(ll);
			}
			else{
				res.add(ll);
				res.add(new ArrayList());
			}
			return res;
		}
	}

	@Override
	public List obtainCategory() {
		return sqlSession.selectList("travel.selectCategory");
	}

	@Override
	public String insertNewNote(HashMap param) {
		sqlSession.insert("travel.insertNewNote",param);
		return (String) sqlSession.selectOne("travel.selectLastInsertNote",param.get("stringId").toString());
	}

	@Override
	public String insertNewPicture(HashMap param) {
		sqlSession.insert("travel.insertNewPicture",param);
		return (String) sqlSession.selectOne("travel.selectLastInsertPicture",param.get("stringId").toString());
	}

	@Override
	public String obtainCountryByLocation(String locationId) {
		return (String) sqlSession.selectOne("travel.selectCountryIdByLocationId",locationId);
	}

	@Override
	public String obtainCountryByNote(String travelNoteId) {
		return (String) sqlSession.selectOne("travel.obtainCountryByNote",travelNoteId);
	}

	@Override
	public void modifyTravelNote(HashMap param) {
		sqlSession.update("travel.updateTravelNote",param);
	}

	@Override
	public String obtainPictureById(String pictureId) {
		return (String) sqlSession.selectOne("travel.selectPictureById",pictureId);
	}

	@Override
	public void deleteTravelNoteById(String travelNoteId) {
		sqlSession.update("travel.deleteTravelNoteById", travelNoteId);
	}

	@Override
	public List<HashMap> deletePicturesById(String travelNoteId) {
		sqlSession.update("travel.softDeletePictures",travelNoteId);
		return (List<HashMap>) sqlSession.selectList("travel.selectPicturesById", travelNoteId);
	}

	@Override
	public void deleteNoteCollectById(String travelNoteId) {
		sqlSession.delete("travel.deleteNoteCollectById", travelNoteId);
	}

	@Override
	public String obtainUserByNote(String travelNoteId) {
		return (String) sqlSession.selectOne("travel.obtainUserByNote", travelNoteId);
	}

	@Override
	public void updateUserRead(HashMap map) {
		sqlSession.update("User.updateUserRead", map);
	}

	@Override
	public String obtainNoteByPicture(String pictureId) {
		return (String) sqlSession.selectOne("travel.obtainNoteByPicture",pictureId);
	}

	@Override
	public long obtainPictureNumByNote(String travelNoteId) {
		return (long) sqlSession.selectOne("travel.obtainPictureNumByNote",travelNoteId);
	}

	@Override
	public void softDeletePictureById(String pictureId) {
		sqlSession.update("travel.softDeletePictureById",pictureId);
	}

	@Override
	public void updateNoteCommentPraise(HashMap param) {
		sqlSession.update("travel.updateNoteComment",param);
		sqlSession.update("travel.updateNotePraise",param);
	}

	@Override
	public void addNoteComments(HashMap param) {
		sqlSession.insert("travel.addNoteComments", param);
	}

	@Override
	public void updatePictureComments(HashMap param) {
		sqlSession.update("travel.updatePictureComments",param);
	}

	@Override
	public void updateNoteComments(HashMap param) {
		sqlSession.update("travel.updateNoteComments",param);
	}
	@Override
	public void addNotesComment2(HashMap param) {
		sqlSession.insert("travel.addNotesComment2",param);
	}

	@Override
	public List<HashMap> obtainPicInfoList(String travelNoteId) {
		return (List<HashMap>) sqlSession.selectList("travel.obtainPicInfoList",travelNoteId);
	}

	@Override
	public long judgeIsCollect(HashMap param) {
		return (long) sqlSession.selectOne("travel.judgeIsCollect",param);
	}

	@Override
	public HashMap obtainTravelNoteInfo(String travelNoteId) {
		return (HashMap) sqlSession.selectOne("travel.selectNoteById",travelNoteId);
	}

	@Override
	public void addNoteRead(String travelNoteId) {
		sqlSession.update("travel.addNoteRead",travelNoteId);
	}

	@Override
	public void addUserRead(String userId) {
		sqlSession.update("User.addUserRead",userId);
	}

	@Override
	public void addCollectNote(HashMap param) {
		sqlSession.insert("travel.addCollectNote",param);
	}

	//新建文章推送消息表
	@Override
	public void addNotificationNewNote(HashMap param) {
		//获取好友列表
		List<HashMap> friendsList= (List<HashMap>) sqlSession.selectList("friends.obtainFriendsListById",param.get("userId").toString());
		//获取该好友信息
		String nickName= (String) sqlSession.selectOne("User.obtainNickNameById",param.get("userId").toString());
		HashMap param1=new HashMap();
		for(HashMap map:friendsList){
			param1.clear();
			param1.put("initiativeId", map.get("userId"));
			param1.put("userId", param.get("userId"));
			param1.put("addTime", Commonparam.Date2Str());
			String content;
			content=nickName+"["+param.get("title")+"]"+param.get("countryId");
			param1.put("content",content);
			param1.put("type", NotificationListType.NEW_NOTE);
			param1.put("pictureId",param.get("pictureId"));
			param1.put("picture", param.get("picture"));

			sqlSession.insert("notification.addNotificationMessage",param1);
		}
	}

	@Override
	public HashMap obtainNotesInfoToTips(String travelNoteId) {
		return (HashMap) sqlSession.selectOne("travel.obtainNotesInfoToTips",travelNoteId);

	}

	@Override
	public List<HashMap> obtainOneNotesTipsList(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("travel.obtainOneNotesTipsList",param);
	}

	@Override
	public HashMap obtainCoverPicture(Long aLong) {
		return (HashMap) sqlSession.selectOne("travel.obtainCoverPicture",aLong);
	}

	@Override
	public Object findNoteMoodList(String tipsId) {
		return sqlSession.selectList("travel.findNoteMoodList", tipsId);
	}

	@Override
	public Object findNoteStyleList(String tipsId) {
		return sqlSession.selectList("travel.findNoteStyleList", tipsId);
	}

	@Override
	public Object findNotePraiseList(String tipsId) {
		return sqlSession.selectList("travel.findNotePraiseList",tipsId);
	}

	@Override
	public PictureModel getPictureModelsByPictureId(String pictureId) {
		return (PictureModel) sqlSession.selectOne("PictureDao.getPictureInfoByPictureId",pictureId);
	}

	@Override
	public PictureModel getPictureModelsByNoteId(String noteId) {
		return (PictureModel) sqlSession.selectOne("PictureDao.getPictureInfoByNoteId",noteId);
	}
}
