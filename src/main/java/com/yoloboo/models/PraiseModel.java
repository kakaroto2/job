package com.yoloboo.models;

/**
 * Created by CoderZhao on 2016/1/21.
 */
public class PraiseModel {
    private Long id;
    private Long pictureId;
    private Long userId;
    private String userPicture;


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

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
}
