package com.yoloboo.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Remy4Pro on 2016/1/22.
 */
public class AwardsModel implements Serializable {
    private Long id;
    private String name_en;
    private String name_cn;
    private String name_tw;
    private String describe_en;
    private String describe_cn;
    private String describe_tw;
    private Date date;


    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_cn() {
        return name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getName_tw() {
        return name_tw;
    }

    public void setName_tw(String name_tw) {
        this.name_tw = name_tw;
    }

    public String getDescribe_en() {
        return describe_en;
    }

    public void setDescribe_en(String describe_en) {
        this.describe_en = describe_en;
    }

    public String getDescribe_cn() {
        return describe_cn;
    }

    public void setDescribe_cn(String describe_cn) {
        this.describe_cn = describe_cn;
    }

    public String getDescribe_tw() {
        return describe_tw;
    }

    public void setDescribe_tw(String describe_tw) {
        this.describe_tw = describe_tw;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
