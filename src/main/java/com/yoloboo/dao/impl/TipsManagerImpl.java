package com.yoloboo.dao.impl;

import java.util.*;


import com.yoloboo.controller.BaseBean.TravelTipsBean;
import com.yoloboo.models.CountryTipsModel;
import com.yoloboo.models.PictureModel;
import com.yoloboo.models.TravelTipsModel;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoloboo.dao.TipsManager;

@Repository
public class TipsManagerImpl extends BaseDao implements TipsManager {
	

	//增加贴士列表中的数据
	@SuppressWarnings("rawtypes")
	@Override
	public void addTipsData(HashMap param) {
		 sqlSession.insert("tips.addTipsData",param);
	}
	
	//获得贴士列表的数据
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainTipsDataList(HashMap param) {
		return(List) sqlSession.selectList("tips.obtainTipsDataList",param);
	}
	
	//获得贴士列表中的数据总数
	@SuppressWarnings("rawtypes")
	@Override
	public Long obtainTipsDataListCount(HashMap param) {
		return(Long) sqlSession.selectOne("tips.obtainTipsDataListCount", param);
	}
	
	//获得国家贴士标签列表的数据
	@SuppressWarnings("rawtypes")
	@Override
	public List obtainCountryTipsList(HashMap param) {
		return(List) sqlSession.selectList("tips.obtainCountryTipsList", param);
	}
	
	//获得国家贴士标签列表中的数据总数
	@SuppressWarnings("rawtypes")
	@Override
	public Long obtainCountryTipsListCount(HashMap param) {
		return(Long) sqlSession.selectOne("tips.obtainCountryTipsListCount", param);
	}



	@Override
	public List<HashMap> obtainOneCountryTipsList(HashMap param) {
		// TODO Auto-generated method stub
		return (List<HashMap>) sqlSession.selectList("tips.obtainOneCountryTipsList", param);
	}



	@Override
	public List<HashMap> obtainNotesByCountryTip(HashMap param) {
		// TODO Auto-generated method stub
		return (List<HashMap>) sqlSession.selectList("tips.obtainNotesByCountryTip", param);
	}



	@Override
	public Object getPictureIdByColumn(String string) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("tips.getPictureIdByColumn",string);
	}



	@Override
	public List<HashMap> obtainOneCountryTipsListByUserId(HashMap param) {
		// TODO Auto-generated method stub
		return (List<HashMap>) sqlSession.selectList("tips.obtainOneCountryTipsListByUserId", param);
	}



	@Override
	public List<HashMap> obtainCountryByUserId(HashMap param) {
		// TODO Auto-generated method stub
		return (List<HashMap>) sqlSession.selectList("tips.obtainCountryByUserId", param);
	}



	@Override
	public HashMap obtainTipsBgPic() {
		// TODO Auto-generated method stub
		return (HashMap) sqlSession.selectOne("tips.obtainTipsBgPic");
	}

	@Override
	public List<HashMap> obtainTipsInfoByPicture(HashMap param1) {
		return (List<HashMap>) sqlSession.selectList("tips.obtainTipsInfoByPicture",param1);
	}

	@Override
	public List<HashMap> obtainMoodTipsByPicture(HashMap param1) {
		return (List<HashMap>) sqlSession.selectList("tips.obtainMoodTipsByPicture",param1);
	}

	@Override
	public List<HashMap> obtainDiyTipsByPicture(HashMap param1) {
		return (List<HashMap>) sqlSession.selectList("tips.obtainDiyTipsByPicture",param1);
	}

	@Override
	public HashMap obtainPicWH(HashMap param1) {
		return (HashMap) sqlSession.selectOne("tips.obtainPicWH",param1);
	}

	@Override
	public int isPraiseById(HashMap param1) {
		return (int) sqlSession.selectOne("tips.isPraiseById",param1);
	}

	@Override
	public List<HashMap> obtainNotesInfo(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("tips.obtainNotesInfo",param);
	}

	/*#################2.0新增####################*/
	@Override
	public List obtainTravelTips() {
		return sqlSession.selectList("tips.selectTravelTips");
	}

	@Override
	public List obtainMoodTips() {
		return sqlSession.selectList("tips.selectMoodTips");
	}

	@Override
	public List<HashMap> obtainTravelTipsList(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("tips.obtainTravelTipsList",param);
	}

	@Override
	public List obtainPictureByTravelTips(HashMap param) {
		return sqlSession.selectList("tips.obtainPictureByTravelTips",param);
	}

	@Override
	public List<HashMap> obtainCountryIdByUserId(String userId) {
		return (List<HashMap>) sqlSession.selectList("tips.obtainCountryIdByUserId",userId);
	}

	@Override
	public List<HashMap> obtainCountryTipsByCountry(HashMap param) {
		return (List<HashMap>) sqlSession.selectList("tips.obtainCountryTipsByCountry",param);
	}

	@Override
	public TravelTipsModel getModelByPK(Long id) {
		return (TravelTipsModel) sqlSession.selectOne("tips.getModelByPK",id);
	}

	@Override
	public List<HashMap> getPicturesByTravelTipId(TravelTipsBean ttb) {
		return (List<HashMap>) sqlSession.selectList("PictureDao.getPicturesByTravelTipId",ttb);
	}

	@Override
	public CountryTipsModel getCountryTipByTravelTip(Long id) {
		return (CountryTipsModel) sqlSession.selectOne("tips.getCountryTipByTravelTip",id);
	}

	@Override
	public List<TravelTipsModel> obtainTravelTipsByCountryId(HashMap param) {
		return (List<TravelTipsModel>) sqlSession.selectList("tips.obtainTravelTipsByCountryId",param);
	}

	@Override
	public List<TravelTipsModel> obtainTravelTipsByLocationId(HashMap param) {
		return (List<TravelTipsModel>) sqlSession.selectList("tips.obtainTravelTipsByLocationId",param);
	}

}
