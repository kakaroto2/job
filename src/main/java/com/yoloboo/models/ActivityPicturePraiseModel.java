package com.yoloboo.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Remy4Pro on 2016/1/25.
 */
public class ActivityPicturePraiseModel implements Serializable
{
    private Long id;
    private Long user_id;
    private Long p_id;
    private Date date;
    private String userName;
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
