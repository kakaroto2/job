package com.yoloboo.models;

/**
 * Created by maojiancai on 16/10/8.
 */
public class TravelNotePic {

    private Long picture_id;

    private Long travel_notes_id;

    private String p_description ;

    private String p_picture;

    private Long sort;

    private String p_address;

    private String  p_date;

    private Integer p_isno_cover;

    public Long getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(Long picture_id) {
        this.picture_id = picture_id;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    public String getP_picture() {
        return p_picture;
    }

    public void setP_picture(String p_picture) {
        this.p_picture = p_picture;
    }

    public Long getTravel_notes_id() {return travel_notes_id;}

    public void setTravel_notes_id(Long travel_notes_id) {this.travel_notes_id = travel_notes_id;}

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
    }

    public String getP_date() {
        return p_date;
    }

    public void setP_date(String p_date) {
        this.p_date = p_date;
    }

    public Integer getP_isno_cover() {
        return p_isno_cover;
    }

    public void setP_isno_cover(Integer p_isno_cover) {
        this.p_isno_cover = p_isno_cover;
    }
}
