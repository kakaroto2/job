package com.yoloboo.models;

import com.common.enums.SystemEnums.HomePageNavType;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by ZHOU005 on 2016/1/16.
 */
public class ActivityModel extends CmsContentModel implements Serializable
{
	private Long id;

	private Long notification_list_id;
	private String name_en;
	private String name_cn;
	private String name_tw;
	private String describe_en;
	private String describe_cn;
	private String describe_tw;
	private String coverPicture;
	private Date startDate;
	private Date endDate;
	private Integer status;
	private Long pictureNum;
	private Long commentNum;

	//用来记录 推送内容
	private String content_tw;

	private String content_cn;

	private String content_en;

	public Long getNotification_list_id() {
		return notification_list_id;
	}

	public void setNotification_list_id(Long notification_list_id) {
		this.notification_list_id = notification_list_id;
	}

	public String getContent_tw() {
		return content_tw;
	}

	public void setContent_tw(String content_tw) {
		this.content_tw = content_tw;
	}

	public String getContent_cn() {
		return content_cn;
	}

	public void setContent_cn(String content_cn) {
		this.content_cn = content_cn;
	}

	public String getContent_en() {
		return content_en;
	}

	public void setContent_en(String content_en) {
		this.content_en = content_en;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName_en()
	{
		return name_en;
	}

	public void setName_en(String name_en)
	{
		this.name_en = name_en;
	}

	public String getName_cn()
	{
		return name_cn;
	}

	public void setName_cn(String name_cn)
	{
		this.name_cn = name_cn;
	}

	public String getName_tw()
	{
		return name_tw;
	}

	public void setName_tw(String name_tw)
	{
		this.name_tw = name_tw;
	}

	public String getDescribe_en()
	{
		return describe_en;
	}

	public void setDescribe_en(String describe_en)
	{
		this.describe_en = describe_en;
	}

	public String getDescribe_cn()
	{
		return describe_cn;
	}

	public void setDescribe_cn(String describe_cn)
	{
		this.describe_cn = describe_cn;
	}

	public String getDescribe_tw()
	{
		return describe_tw;
	}

	public void setDescribe_tw(String describe_tw)
	{
		this.describe_tw = describe_tw;
	}

	public String getCoverPicture()
	{
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture)
	{
		this.coverPicture = coverPicture;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Long getPictureNum()
	{
		return pictureNum;
	}

	public void setPictureNum(Long pictureNum)
	{
		this.pictureNum = pictureNum;
	}

	public Long getCommentNum()
	{
		return commentNum;
	}

	public void setCommentNum(Long commentNum)
	{
		this.commentNum = commentNum;
	}

	public HomePageNavType getContentType()
	{
		return HomePageNavType.Activity;
	}
}
