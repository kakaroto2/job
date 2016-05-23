package com.yoloboo.controller.bean;

import com.yoloboo.models.PictureModel;
import com.yoloboo.models.TravelTipsModel;

import java.util.List;

/**
 * Created by Remy4Pro on 2016/2/23.
 */
public class PicturesAboutOneTipBean
{
    private TravelTipsModel tip;
    private List<PictureModel> pictureList;


    public List<PictureModel> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<PictureModel> pictureList) {
        this.pictureList = pictureList;
    }

    public TravelTipsModel getTip() {
        return tip;
    }

    public void setTip(TravelTipsModel tip) {
        this.tip = tip;
    }

}
