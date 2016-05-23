package com.yoloboo.service.impl;

import com.common.Commonparam;
import com.yoloboo.controller.BaseBean.TravelTipsBean;
import com.yoloboo.controller.bean.PicturesAboutOneTipBean;
import com.yoloboo.excptions.ResultNotFoundException;
import com.yoloboo.dao.CountryManager;
import com.yoloboo.dao.TipsManager;
import com.yoloboo.models.CountryTipsModel;
import com.yoloboo.models.PictureModel;
import com.yoloboo.models.TravelTipsModel;
import com.yoloboo.service.TipsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CoderZhao on 2016/1/15.
 */
@Service
public class TipsServiceImpl implements TipsService {
    @Resource
    private TipsManager tipsManager;
    @Resource
    private CountryManager countryManager;

    @Override
    public List obtainTips() {
        List tipsList = new ArrayList();
        tipsList.addAll(tipsManager.obtainTravelTips());
        tipsList.addAll(tipsManager.obtainMoodTips());
        return tipsList;
    }

    @Override
    public String obtainCountryIdByName(String input) {
        return countryManager.obtainCountryIdByName(input);
    }

    //获取指定国家的国家贴士tb_country_tips
    @Override
    public List obtainTipsByCountryId(String countryId, String userId) throws ResultNotFoundException {
        HashMap map = new HashMap();
        map.put("countryId", countryId);
        map.put("userId", userId);
        List<HashMap> tipsList = tipsManager.obtainOneCountryTipsListByUserId(map);
        if (tipsList.size() <= 0) {
            throw new ResultNotFoundException();
        } else {
            return tipsList;
        }
    }


    //获取指定国家、指定贴士的旅游贴士
    @Override
    public List obtainTravelTipsByTipsId(String userId, String countryId, String tipsId) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("countryId", countryId);
        param.put("tipsId", tipsId);

        //获取travelTipsIdList
        return tipsManager.obtainTravelTipsList(param);
    }

    @Override
    public List obtainSurpriseMePictures(String userId, int page, int size) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        //随机出一个国家
        List<HashMap> obtainCountryIdByUserId = tipsManager.obtainCountryIdByUserId(userId);
        if (obtainCountryIdByUserId.size() > 0) {
            int random = Commonparam.createRandomNumByBig(obtainCountryIdByUserId.size()); //产生一个随机数
            param.put("countryId", obtainCountryIdByUserId.get(random).get("countryId"));
        }
        //随机出当前国家的countryTips
        List<HashMap> obtainCountryTipsByCountry = tipsManager.obtainCountryTipsByCountry(param);
        if (obtainCountryTipsByCountry.size() > 0) {
            int random = Commonparam.createRandomNumByBig(obtainCountryTipsByCountry.size()); //产生一个随机数
            param.put("tipsId", obtainCountryTipsByCountry.get(random).get("tipsId"));
        }
        return tipsManager.obtainTravelTipsList(param);
    }

    @Override
    public List obtainPicturesByTravelTips(String userId, String countryId, String travelTipsId, int page, int size) {
        HashMap param = new HashMap();
        param.put("userId", userId);
        param.put("countryId", countryId);
        param.put("travelTipsId", travelTipsId);
        param.put("startIndex", size * (page - 1));
        param.put("size", size);
        return tipsManager.obtainPictureByTravelTips(param);
    }

    @Override
    public List countryTips2travelTips(String countryOrLocationId, Integer type, String userId) {
        List<TravelTipsModel> obtainTravelTipsList;
        List<CountryTipsModel> obtainCountryTipsList = new ArrayList<CountryTipsModel>();

        HashMap param = new HashMap();
        param.put("userId", userId);

        if (type != null) {
            param.put("countryId", countryOrLocationId);
            obtainTravelTipsList = tipsManager.obtainTravelTipsByCountryId(param);
        } else {
            param.put("locationId", countryOrLocationId);
            obtainTravelTipsList = tipsManager.obtainTravelTipsByLocationId(param);
        }

        List<TravelTipsModel> toBeRemoved = new ArrayList<TravelTipsModel>();

        for (TravelTipsModel ttm : obtainTravelTipsList) {
            CountryTipsModel ctm = tipsManager.getCountryTipByTravelTip(ttm.getId());
            if(ctm == null){
                toBeRemoved.add(ttm);
            }
            else {
                obtainCountryTipsList.add(ctm);
            }
        }

        for (TravelTipsModel ttm : toBeRemoved){
            obtainTravelTipsList.remove(ttm);
        }

        List result = new ArrayList();
        result.add(obtainTravelTipsList);
        result.add(obtainCountryTipsList);
        return result;
    }
}
