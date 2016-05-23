package com.yoloboo.models;

import java.io.Serializable;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/2/20.
 */
public class UserModel implements Serializable
{
	private Long userId;
	private String uPhone;
	private String uNickname;
	private String uPicture;
	private Integer uType;
	private String uToken;
	private String uLanguage;
	private List<TravelStyleModel> travelStyleList;


	public String getuLanguage() {
		return uLanguage;
	}

	public void setuLanguage(String uLanguage) {
		this.uLanguage = uLanguage;
	}

	public String getuToken() {
		return uToken;
	}

	public void setuToken(String uToken) {
		this.uToken = uToken;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getuPhone()
	{
		return uPhone;
	}

	public void setuPhone(String uPhone)
	{
		this.uPhone = uPhone;
	}

	public String getuNickname()
	{
		return uNickname;
	}

	public void setuNickname(String uNickname)
	{
		this.uNickname = uNickname;
	}

	public String getuPicture()
	{
		return uPicture;
	}

	public void setuPicture(String uPicture)
	{
		this.uPicture = uPicture;
	}

	public Integer getuType()
	{
		return uType;
	}

	public void setuType(Integer uType)
	{
		this.uType = uType;
	}

	public List<TravelStyleModel> getTravelStyleList()
	{
		return travelStyleList;
	}

	public void setTravelStyleList(List<TravelStyleModel> travelStyleList)
	{
		this.travelStyleList = travelStyleList;
	}
}
