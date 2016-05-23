package com.yoloboo.models;

import java.io.Serializable;

/**
 * Created by Remy4Pro on 2016/2/23.
 */
public class CountryTipsModel extends CmsContentModel implements Serializable
{
    private Long id;
    private String nameEn;
    private String nameCn;
    private String nameTw;
    private String noclickPicture;
    private String clickPicture;
    private String stringId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNoclickPicture() {
        return noclickPicture;
    }

    public void setNoclickPicture(String noclickPicture) {
        this.noclickPicture = noclickPicture;
    }

    public String getClickPicture() {
        return clickPicture;
    }

    public void setClickPicture(String clickPicture) {
        this.clickPicture = clickPicture;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

}
