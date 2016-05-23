package com.yoloboo.controller.bean;


/**
 * Created by ZHOU005 on 2015/12/28.
 */
public class UserBean
{
	private int registerType;
	private String phone;
	private String threeId;
	private String password;
	private String qiniukey;//用户新上传头像qiniukey
	private String headImgUrl;//系统默认头像
	private String nickname;
	private String code;
	private String countryCode;
	private String travelStyle;

	public int getRegisterType()
	{
		return registerType;
	}

	public void setRegisterType(int registerType)
	{
		this.registerType = registerType;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getThreeId()
	{
		return threeId;
	}

	public void setThreeId(String threeId)
	{
		this.threeId = threeId;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getQiniukey()
	{
		return qiniukey;
	}

	public void setQiniukey(String qiniukey)
	{
		this.qiniukey = qiniukey;
	}

	public String getHeadImgUrl()
	{
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl)
	{
		this.headImgUrl = headImgUrl;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	public String getTravelStyle()
	{
		return travelStyle;
	}

	public void setTravelStyle(String travelStyle)
	{
		this.travelStyle = travelStyle;
	}
}
