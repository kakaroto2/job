package com.yoloboo.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Remy4Pro on 2016/1/26.
 */
public class ActivityPictureCommentsModel implements Serializable {
    private Long id;
    private String commentsContent;
    private Long userId;
    private Long apId;
    private Date date;
    private Long c_userId;
    private String c_nickname;
    private String userName;
    private String userPicture;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommentsContent() {
        return commentsContent;
    }

    public void setCommentsContent(String commentsContent) {
        this.commentsContent = commentsContent;
    }

    public Long getApId() {
        return apId;
    }

    public void setApId(Long apId) {
        this.apId = apId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getC_userId() {
        return c_userId;
    }

    public void setC_userId(Long c_userId) {
        this.c_userId = c_userId;
    }

    public String getC_nickname() {
        return c_nickname;
    }

    public void setC_nickname(String c_nickname) {
        this.c_nickname = c_nickname;
    }
}
