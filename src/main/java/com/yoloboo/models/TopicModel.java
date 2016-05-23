package com.yoloboo.models;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/3.
 */
public class TopicModel extends CmsContentModel
{
	private Long id;
	private Long themeId;
	private String nameEn;
	private String nameCn;
	private String nameTw;
	private String picture;
	private Integer notesNum;
	private String creatorName;
	private List<TravelNoteModel> travelNotes;
	private ThemeModel theme;

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public ThemeModel getTheme() {
		return theme;
	}

	public void setTheme(ThemeModel theme) {
		this.theme = theme;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNameEn()
	{
		return nameEn;
	}

	public void setNameEn(String nameEn)
	{
		this.nameEn = nameEn;
	}

	public String getNameCn()
	{
		return nameCn;
	}

	public void setNameCn(String nameCn)
	{
		this.nameCn = nameCn;
	}

	public String getNameTw()
	{
		return nameTw;
	}

	public void setNameTw(String nameTw)
	{
		this.nameTw = nameTw;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

	public Integer getNotesNum()
	{
		return notesNum;
	}

	public void setNotesNum(Integer notesNum)
	{
		this.notesNum = notesNum;
	}

	public String getCreatorName()
	{
		return creatorName;
	}

	public void setCreatorName(String creatorName)
	{
		this.creatorName = creatorName;
	}

	public List<TravelNoteModel> getTravelNotes()
	{
		return travelNotes;
	}

	public void setTravelNotes(List<TravelNoteModel> travelNotes)
	{
		this.travelNotes = travelNotes;
	}
}
