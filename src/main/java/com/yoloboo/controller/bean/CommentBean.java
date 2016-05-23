package com.yoloboo.controller.bean;

/**
 * Created by CoderZhao on 2016/1/22.
 */
public class CommentBean {
    private Long pictureId;
    private int startIndex;
    private int size;

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
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
