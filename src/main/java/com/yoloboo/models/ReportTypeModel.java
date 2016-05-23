package com.yoloboo.models;

import java.io.Serializable;

/**
 * Created by Remy4Pro on 2016/1/27.
 */
public class ReportTypeModel implements Serializable {
    private Long id;
    private String name_en;
    private String name_cn;
    private String name_tw;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
