package com.yoloboo.models;

/**
 * Created by CoderZhao on 2016/1/22.
 */
public class LocationModel {
    private Long locationId;
    private Long countryId;
    private String nameEn;
    private String nameCn;
    private String nameTw;


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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }


}
