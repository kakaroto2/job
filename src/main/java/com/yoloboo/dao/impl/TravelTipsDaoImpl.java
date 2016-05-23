package com.yoloboo.dao.impl;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.dao.TravelTipsDao;
import com.yoloboo.models.TravelTipsModel;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/26.
 */

@Repository
public class TravelTipsDaoImpl extends BaseDao implements TravelTipsDao
{

	@Override
	public List<TravelTipsModel> selectTravelTipsByCountryTip(Long countryTipId)
	{
		return (List<TravelTipsModel>) sqlSession.selectList("TravelTipsDao.selectTravelTipsByCountryTip", countryTipId);
	}

	@Override
	public List<Long> selectRelationTravelTipsByCountry(PictureBean bean)
	{
		return (List<Long>) sqlSession.selectList("TravelTipsDao.selectRelationTravelTipsByCountry", bean);
	}

	@Override
	public List<TravelTipsModel> selectRelationTravelTipsByLocationAndCountryTip(PictureBean bean)
	{
		return (List<TravelTipsModel>) sqlSession.selectList("TravelTipsDao.selectRelationTravelTipsByLocationAndCountryTip", bean);
	}

	@Override
	public List<Long> selectRelationCountryTips(Long userId)
	{
		return (List<Long>) sqlSession.selectList("TravelTipsDao.selectRelationCountryTips", userId);
	}

}
