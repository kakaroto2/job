package com.yoloboo.dao;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.models.TravelTipsModel;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/26.
 */
public interface TravelTipsDao
{
	List<TravelTipsModel> selectTravelTipsByCountryTip(Long countryTipId);

	List<TravelTipsModel> selectRelationTravelTipsByLocationAndCountryTip(PictureBean bean);

	List<Long> selectRelationTravelTipsByCountry(PictureBean bean);

	List<Long> selectRelationCountryTips(Long userId);

}
