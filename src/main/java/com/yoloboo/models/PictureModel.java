package com.yoloboo.models;

import java.io.Serializable;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public class PictureModel implements Serializable
{

	private Long id;
	private String address;
	private String description;
	private Long travelNotesId;
	private Long commentsNum;
	private Long praiseNum;
	private Integer isNoDelete;
	private String date;
	private Integer isNoCover;
	private String stringId;
	private String picture;
	private String pictureSquare;
	private Long squareSide;
	private UserModel user;
	private List<CommentModel> comments;
	private List<PraiseModel> praises;
	private List<TravelTipsModel> travelTips;
	private List<MoodTipsModel> moodTips;

	public List<MoodTipsModel> getMoodTips() {
		return moodTips;
	}

	public void setMoodTips(List<MoodTipsModel> moodTips) {
		this.moodTips = moodTips;
	}

	public List<CommentModel> getComments() {
		return comments;
	}

	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}

	public Long getSquareSide() {
		return squareSide;
	}

	public void setSquareSide(Long squareSide) {
		this.squareSide = squareSide;
	}

	public List<PraiseModel> getPraises() {
		return praises;
	}

	public void setPraises(List<PraiseModel> praises) {
		this.praises = praises;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Long getTravelNotesId()
	{
		return travelNotesId;
	}

	public void setTravelNotesId(Long travelNotesId)
	{
		this.travelNotesId = travelNotesId;
	}

	public Long getCommentsNum()
	{
		return commentsNum;
	}

	public void setCommentsNum(Long commentsNum)
	{
		this.commentsNum = commentsNum;
	}

	public Long getPraiseNum()
	{
		return praiseNum;
	}

	public void setPraiseNum(Long praiseNum)
	{
		this.praiseNum = praiseNum;
	}

	public Integer getIsNoDelete()
	{
		return isNoDelete;
	}

	public void setIsNoDelete(Integer isNoDelete)
	{
		this.isNoDelete = isNoDelete;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public Integer getIsNoCover()
	{
		return isNoCover;
	}

	public void setIsNoCover(Integer isNoCover)
	{
		this.isNoCover = isNoCover;
	}

	public String getStringId()
	{
		return stringId;
	}

	public void setStringId(String stringId)
	{
		this.stringId = stringId;
	}

	public String getPicture()
	{
		return picture;
	}

	public void setPicture(String picture)
	{
		this.picture = picture;
	}

	public String getPictureSquare()
	{
		return pictureSquare;
	}

	public void setPictureSquare(String pictureSquare)
	{
		this.pictureSquare = pictureSquare;
	}

	public List<TravelTipsModel> getTravelTips()
	{
		return travelTips;
	}

	public void setTravelTips(List<TravelTipsModel> travelTips)
	{
		this.travelTips = travelTips;
	}

	public UserModel getUser()
	{
		return user;
	}

	public void setUser(UserModel user)
	{
		this.user = user;
	}
}
