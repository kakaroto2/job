package com.yoloboo.controller.bean;

import java.util.Date;

/**
 * Created by Remy4Pro on 2016/1/21.
 */
public class ActivityBean {

    private int size;
    private int startIndex;
    private int aStatus;
    private Long aId;
    private Long apId;
    private Long acId;
    private Long userId;
    private String address;
    private String pictureContent;
    private String commentsContent;
    private Date date;
    private Long c_userId;
    private String c_nickname;
    private String picture;
    private String picture_square;
    private String userName;
    private String userPicture;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getPictureContent() {
        return pictureContent;
    }

    public void setPictureContent(String pictureContent) {
        this.pictureContent = pictureContent;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture_square() {
        return picture_square;
    }

    public void setPicture_square(String picture_square) {
        this.picture_square = picture_square;
    }

    public Long getAcId() {
        return acId;
    }

    public void setAcId(Long acId) {
        this.acId = acId;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getC_nickname() {
        return c_nickname;
    }

    public void setC_nickname(String c_nickname) {
        this.c_nickname = c_nickname;
    }

    public Long getC_userId() {
        return c_userId;
    }

    public void setC_userId(Long c_userId) {
        this.c_userId = c_userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentsContent() {
        return commentsContent;
    }

    public void setCommentsContent(String commentsContent) {
        this.commentsContent = commentsContent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getApId() {
        return apId;
    }

    public void setApId(Long apId) {
        this.apId = apId;
    }

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public int getaStatus() {
        return aStatus;
    }

    public void setaStatus(int aStatus) {
        this.aStatus = aStatus;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
