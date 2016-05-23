package com.yoloboo.models;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by ZHOU005 on 2016/1/20.
 */
public class NotesCollectModel implements Serializable
{
	private Long id;
	private Long userId;
	private Date createTime;
	private TravelNoteModel travelNote;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public TravelNoteModel getTravelNote()
	{
		return travelNote;
	}

	public void setTravelNote(TravelNoteModel travelNote)
	{
		this.travelNote = travelNote;
	}
}
