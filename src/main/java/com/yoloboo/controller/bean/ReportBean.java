package com.yoloboo.controller.bean;

import java.util.Date;

/**
 * Created by Remy4Pro on 2016/1/26.
 */
public class ReportBean
{
    private Long reportTypeId;
    private Long userId;
    private Long editorId;
    private Long travelNoteId;
    private Date date;
    private String status;
    private Long pId;
    private String pType;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public Long getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Long reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public Long getTravelNoteId() {
        return travelNoteId;
    }

    public void setTravelNoteId(Long travelNoteId) {
        this.travelNoteId = travelNoteId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
