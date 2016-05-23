package com.yoloboo.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Remy4Pro on 2016/1/22.
 */
public class ActivityPictureModel implements Serializable {
    private Long id;
    private Long a_id;
    private Long user_id;
    private String pic_url;
    private String pic_square_url;
    private Date date;
    private Long praise_num;
    private Long comment_num;
    private Long awards_id;
    private String address;
    private String content;
    private Integer  isPraised;

    public Integer getIsPraised() {
        return isPraised;
    }

    public void setIsPraised(Integer isPraised) {
        this.isPraised = isPraised;
    }
//上传图片的人的头像url 和  昵称

    private String u_nickname;
    private String u_picture;


    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public String getU_picture() {
        return u_picture;
    }

    public void setU_picture(String u_picture) {
        this.u_picture = u_picture;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAwards_id() {
        return awards_id;
    }

    public void setAwards_id(Long awards_id) {
        this.awards_id = awards_id;
    }

    public Long getComment_num() {
        return comment_num;
    }

    public void setComment_num(Long comment_num) {
        this.comment_num = comment_num;
    }

    public Long getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(Long praise_num) {
        this.praise_num = praise_num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPic_square_url() {
        return pic_square_url;
    }

    public void setPic_square_url(String pic_square_url) {
        this.pic_square_url = pic_square_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getA_id() {
        return a_id;
    }

    public void setA_id(Long a_id) {
        this.a_id = a_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
