package com.yoloboo.service;

import com.json.BaseBean;
import com.yoloboo.controller.BaseBean.TravelTipsBean;
import com.yoloboo.controller.bean.PicturesAboutOneTipBean;
import com.yoloboo.excptions.ResultNotFoundException;
import com.yoloboo.models.CountryTipsModel;
import com.yoloboo.models.PictureModel;
import com.yoloboo.models.TravelTipsModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by CoderZhao on 2016/1/15.
 */
public interface TipsService {
    List obtainTips();

    String obtainCountryIdByName(String input);

    List obtainTipsByCountryId(String countryId,String userId) throws ResultNotFoundException;

    List obtainTravelTipsByTipsId(String userId, String travelTipsId, String countryId);

    List obtainSurpriseMePictures(String userId, int page, int size);

    List obtainPicturesByTravelTips(String userId, String countryId, String travelTipsId, int page, int size);

    List countryTips2travelTips(String countryOrLocationId, Integer type, String userId);
}
