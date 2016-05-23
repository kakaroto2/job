package com.yoloboo.service;

import com.yoloboo.controller.BaseBean.PictureBean;
import com.yoloboo.models.PictureFindModel;
import com.yoloboo.models.PictureModel;

import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/25.
 */
public interface PictureService
{
	List<PictureModel> getPicturesByLocationAndTravelTip(PictureBean bean);

	List<PictureModel> getPicturesByLocationAndTravelTipSize(HashMap param);

	PictureFindModel getRandomLocationAndCountryTipPictures(Long userId);

	PictureFindModel getRandomLocationAndCountryTipPictures2(Long userId);

//	PictureFindModel findTipsPictureByLocationAndCountryTip(PictureBean bean);
}
