package com.yoloboo.models;

import com.common.enums.SystemEnums.HomePageNavType;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public class ThemeModel extends CmsContentModel
{
	private Long id;
	private Integer topicNum;
	private String nameEn;
	private String nameCn;
	private String nameTw;
	private String descriptionEn;
	private String descriptionCh;
	private String descriptionTw;
	private String clickPicture;
	private String noclickPicture;
	private String blackPicture;
	private String whitePicture;
	private String picture;
	private String allTopicPicture;
	List<TravelNoteModel> noteModelList;


	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNameTw() {
		return nameTw;
	}

	public void setNameTw(String nameTw) {
		this.nameTw = nameTw;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getTopicNum()
	{
		return topicNum;
	}

	public void setTopicNum(Integer topicNum)
	{
		this.topicNum = topicNum;
	}



	public String getClickPicture()
	{
		return clickPicture;
	}

	public void setClickPicture(String clickPicture)
	{
		this.clickPicture = clickPicture;
	}

	public String getNoclickPicture()
	{
		return noclickPicture;
	}

	public void setNoclickPicture(String noclickPicture)
	{
		this.noclickPicture = noclickPicture;
	}

	public String getBlackPicture()
	{
		return blackPicture;
	}

	public void setBlackPicture(String blackPicture)
	{
		this.blackPicture = blackPicture;
	}

	public String getWhitePicture()
	{
		return whitePicture;
	}

	public void setWhitePicture(String whitePicture)
	{
		this.whitePicture = whitePicture;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

	public HomePageNavType getContentType()
	{
		return HomePageNavType.Cate;
	}

	public String getAllTopicPicture()
	{
		return allTopicPicture;
	}

	public void setAllTopicPicture(String allTopicPicture)
	{
		this.allTopicPicture = allTopicPicture;
	}

	public List<TravelNoteModel> getNoteModelList()
	{
		return noteModelList;
	}

	public void setNoteModelList(List<TravelNoteModel> noteModelList)
	{
		this.noteModelList = noteModelList;
	}

	public String getDescriptionEn()
	{
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn)
	{
		this.descriptionEn = descriptionEn;
	}

	public String getDescriptionCh()
	{
		return descriptionCh;
	}

	public void setDescriptionCh(String descriptionCh)
	{
		this.descriptionCh = descriptionCh;
	}

	public String getDescriptionTw()
	{
		return descriptionTw;
	}

	public void setDescriptionTw(String descriptionTw)
	{
		this.descriptionTw = descriptionTw;
	}
}
