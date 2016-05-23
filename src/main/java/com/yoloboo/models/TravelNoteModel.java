package com.yoloboo.models;

import com.common.enums.SystemEnums.HomePageNavType;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public class TravelNoteModel extends CmsContentModel
{
	private Long id;
	private String date;
	private int isNoComplete;
	private Long userId;
	private int commentsNum;
	private int praiseNum;
	private String title;
	private String stringId;
	private String newTime;
	private Long viewNum;
	private String description;
	private int canPublish;
	private int isPushed;
	private ThemeModel theme;
	private LocationModel location;
	private CountryModel country;
	private List<PictureModel> pictures;
	private List<PictureModel> coverPictures;
	private UserModel user;

	public CountryModel getCountry() {
		return country;
	}

	public void setCountry(CountryModel country) {
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getIsNoComplete() {
		return isNoComplete;
	}

	public void setIsNoComplete(int isNoComplete) {
		this.isNoComplete = isNoComplete;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getCommentsNum() {
		return commentsNum;
	}

	public void setCommentsNum(int commentsNum) {
		this.commentsNum = commentsNum;
	}

	public int getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(int praiseNum) {
		this.praiseNum = praiseNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public String getNewTime() {
		return newTime;
	}

	public void setNewTime(String newTime) {
		this.newTime = newTime;
	}

	public Long getViewNum() {
		return viewNum;
	}

	public void setViewNum(Long viewNum) {
		this.viewNum = viewNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCanPublish() {
		return canPublish;
	}

	public void setCanPublish(int canPublish) {
		this.canPublish = canPublish;
	}

	public int getIsPushed() {
		return isPushed;
	}

	public void setIsPushed(int isPushed) {
		this.isPushed = isPushed;
	}

	public ThemeModel getTheme() {
		return theme;
	}

	public void setTheme(ThemeModel theme) {
		this.theme = theme;
	}

	public LocationModel getLocation() {
		return location;
	}

	public void setLocation(LocationModel location) {
		this.location = location;
	}

	public List<PictureModel> getPictures() {
		return pictures;
	}

	public void setPictures(List<PictureModel> pictures) {
		this.pictures = pictures;
	}

	public List<PictureModel> getCoverPictures() {
		return coverPictures;
	}

	public void setCoverPictures(List<PictureModel> coverPictures) {
		this.coverPictures = coverPictures;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public HomePageNavType getContentType()
	{
		return HomePageNavType.Note;
	}
}
