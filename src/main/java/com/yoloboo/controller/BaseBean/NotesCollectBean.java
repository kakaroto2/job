package com.yoloboo.controller.BaseBean;

/**
 * Created by huhaosumail on 16/5/9.
 */
public class NotesCollectBean extends PagingBean{
    private Long id;
    private Long noteId;
    private Long userId;
    private String date;
    private Integer status;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getNoteId()
    {
        return noteId;
    }

    public void setNoteId(Long noteId)
    {
        this.noteId = noteId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
