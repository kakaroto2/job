package com.yoloboo.models;

/**
 * Created by CoderZhao on 2016/1/21.
 */
public class CommentModel {
    private Long id;
    private Long pictureId;
    private Long userId;
    private String nickName;
    private String userPicture;
    private String content;
    private String commentTime;
    private Long c_userId;
    private String c_nickName;


    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getC_userId() {
        return c_userId;
    }

    public void setC_userId(Long c_userId) {
        this.c_userId = c_userId;
    }

    public String getC_nickName() {
        return c_nickName;
    }

    public void setC_nickName(String c_nickName) {
        this.c_nickName = c_nickName;
    }


    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
