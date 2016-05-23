package com.yoloboo.models;

import java.io.Serializable;

/**
 * Created by Remy4Pro on 2016/3/1.
 */
public class MoodTipsModel implements Serializable
{
    private Long mtId;
    private String nameEn;
    private String nameCn;
    private String nameTw;
    private String noClickPic;
    private  String clickPic;

    public Long getMtId() {
        return mtId;
    }

    public void setMtId(Long mtId) {
        this.mtId = mtId;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameTw() {
        return nameTw;
    }

    public void setNameTw(String nameTw) {
        this.nameTw = nameTw;
    }

    public String getNoClickPic() {
        return noClickPic;
    }

    public void setNoClickPic(String noClickPic) {
        this.noClickPic = noClickPic;
    }

    public String getClickPic() {
        return clickPic;
    }

    public void setClickPic(String clickPic) {
        this.clickPic = clickPic;
    }
}
