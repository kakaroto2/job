package com.yoloboo.service.impl;

import com.common.Commonparam;
import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.dao.*;
import com.yoloboo.models.*;
import com.yoloboo.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/25.
 */

@Service
public class PictureServiceImpl implements PictureService
{

	@Resource
	private PictureDao pictureDao;

	@Resource
	private TravelTipsDao travelTipsDao;

	@Resource
	private TravelNoteDao travelNoteDao;

	@Resource
	private LocationDao locationDao;

	@Resource
	private CountryManager countryManager;

	@Override
	public List<PictureModel> getPicturesByLocationAndTravelTip(PictureBean bean)
	{
		return pictureDao.getPicturesByLocationAndTravelTip(bean);
	}

	@Override
	public List<PictureModel> getPicturesByLocationAndTravelTipSize(HashMap param)
	{
		return pictureDao.getPicturesByLocationAndTravelTipSize(param);
	}

	@Override
	public PictureFindModel getRandomLocationAndCountryTipPictures(Long userId)
	{
		PictureBean bean = new PictureBean();

		List<Long> countryTipIds = travelTipsDao.selectRelationCountryTips(userId);
		int countryTipListIndex = Commonparam.createRandomNumByBig(countryTipIds.size());

		bean.setUserId(userId);
		bean.setCountryTipId(countryTipIds.get(countryTipListIndex));

		List<Long> locationIds = travelNoteDao.selectRelationNotesLocation(bean);
		int locationListIndex = Commonparam.createRandomNumByBig(locationIds.size());

		bean.setLocationId(locationIds.get(locationListIndex));

		return findTipsPictureByLocationAndCountryTip(bean);
	}

	@Override
	public PictureFindModel getRandomLocationAndCountryTipPictures2(Long userId)
	{
		PictureBean bean = new PictureBean();
		List<Long> countryTipIds = travelTipsDao.selectRelationCountryTips(userId);
		int countryTipListIndex = Commonparam.createRandomNumByBig(countryTipIds.size());

		bean.setUserId(userId);
		bean.setCountryTipId(countryTipIds.get(countryTipListIndex));

		List<Long> idList = pictureDao.getRelationPictureIds(bean);
		List<Long> locationIds = travelNoteDao.selectRelationNotesLocation2(idList);
		int locationListIndex = Commonparam.createRandomNumByBig(locationIds.size());

		bean.setLocationId(locationIds.get(locationListIndex));

		return findTipsPictureByLocationAndCountryTip(bean);
	}

	public PictureFindModel findTipsPictureByLocationAndCountryTip(PictureBean bean)
	{
		PictureFindModel model = new PictureFindModel();

		List<TravelTipsModel> travelTipsModels = travelTipsDao.selectRelationTravelTipsByLocationAndCountryTip(bean);

		bean.setTravelTipId(travelTipsModels.get(0).getId());

		fillCountryObj(model, bean);
		fillLocationObj(model, bean);

		model.setTravelTipId(bean.getTravelTipId());
		model.setCountryTipId(bean.getCountryTipId());
		model.setTravelTipsModels(travelTipsModels);
	//	model.setPictureModels(getPicturesByLocationAndTravelTip(bean));

		return model;
	}

	private void fillCountryObj(PictureFindModel model, PictureBean bean)
	{
		if(null != bean.getCountryId())
		{
			CountryModel country = countryManager.getModelByPK(bean.getCountryId());

			model.setCountryId(country.getCountryId());
			model.setNameCn(country.getNameCn());
			model.setNameEn(country.getNameEn());
			model.setNameTw(country.getNameTw());
		}
	}

	private void fillLocationObj(PictureFindModel model, PictureBean bean)
	{
		if(null != bean.getLocationId())
		{
			LocationModel location = locationDao.getModelByPK(bean.getLocationId());

			model.setLocationId(location.getLocationId());
			model.setNameCn(location.getNameCn());
			model.setNameEn(location.getNameEn());
			model.setNameTw(location.getNameTw());
		}
	}

}
