package com.yoloboo.models;

import java.io.Serializable;

/**
 * Created by Remy4Pro on 2016/3/1.
 */
public class CountryModel implements Serializable
{
    private Long countryId;
    private String nameEn;
    private String nameCn;
    private String nameTw;
    private String abbr;
    private String code;
    private String firstLetter;
    private String picture;
    private String firstLetterCn;
    private String foreignCode;

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
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

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFirstLetterCn() {
        return firstLetterCn;
    }

    public void setFirstLetterCn(String firstLetterCn) {
        this.firstLetterCn = firstLetterCn;
    }

    public String getForeignCode() {
        return foreignCode;
    }

    public void setForeignCode(String foreignCode) {
        this.foreignCode = foreignCode;
    }
}
