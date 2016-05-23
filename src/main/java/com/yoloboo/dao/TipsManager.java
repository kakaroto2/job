package com.yoloboo.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;






import com.json.BaseBean;
import com.yoloboo.controller.BaseBean.TravelTipsBean;
import com.yoloboo.models.CountryTipsModel;
import com.yoloboo.models.PictureModel;
import com.yoloboo.models.TravelTipsModel;


public interface TipsManager {


	void addTipsData(HashMap param);//添加贴士列表中的数据
	
	List obtainTipsDataList(HashMap param);//获得贴士列表中的数据
	
	Long obtainTipsDataListCount(HashMap param);//获得贴士列表中的数据总数
	
	List obtainCountryTipsList(HashMap param);//获得国家贴士标签列表中的数据
	
	Long obtainCountryTipsListCount(HashMap param);//获得国家贴士标签列表中的数据总数

	List<HashMap> obtainOneCountryTipsList(HashMap param); //获取制定国家的贴士列表

	List<HashMap> obtainNotesByCountryTip(HashMap param); //获取指定国家指定贴士的便签列表


	Object getPictureIdByColumn(String string);

	List<HashMap> obtainOneCountryTipsListByUserId(HashMap param);

	List<HashMap> obtainCountryByUserId(HashMap param);

	HashMap obtainTipsBgPic();

	List<HashMap> obtainTipsInfoByPicture(HashMap param1);

	List<HashMap> obtainMoodTipsByPicture(HashMap param1);

	List<HashMap> obtainDiyTipsByPicture(HashMap param1);

	HashMap obtainPicWH(HashMap param1);

	int isPraiseById(HashMap param1);

	List<HashMap> obtainNotesInfo(HashMap param);

	/*#######################2.0新增######################*/
	List obtainTravelTips();

	List obtainMoodTips();

	List<HashMap> obtainTravelTipsList(HashMap param);

	List obtainPictureByTravelTips(HashMap param);

	List<HashMap> obtainCountryIdByUserId(String userId);

	List<HashMap> obtainCountryTipsByCountry(HashMap param);

	TravelTipsModel getModelByPK(Long id);

	List<TravelTipsModel> obtainTravelTipsByCountryId(HashMap param);

	List<TravelTipsModel> obtainTravelTipsByLocationId(HashMap param);

	List<HashMap> getPicturesByTravelTipId(TravelTipsBean ttb);

	CountryTipsModel getCountryTipByTravelTip(Long id);
}
