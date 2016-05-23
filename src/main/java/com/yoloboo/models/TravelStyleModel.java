package com.yoloboo.models;

import java.io.Serializable;


/**
 * Created by ZHOU005 on 2016/2/20.
 */
public class TravelStyleModel implements Serializable
{
	private Long id;
	private String nameEn;
	private String whitePicture;
	private String blackPicture;
	private String greyPicture;
	private String noclickPicture;
	private String clickPicture;
	private String bgPicture;

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

	public String getWhitePicture()
	{
		return whitePicture;
	}

	public void setWhitePicture(String whitePicture)
	{
		this.whitePicture = whitePicture;
	}

	public String getBlackPicture()
	{
		return blackPicture;
	}

	public void setBlackPicture(String blackPicture)
	{
		this.blackPicture = blackPicture;
	}

	public String getGreyPicture()
	{
		return greyPicture;
	}

	public void setGreyPicture(String greyPicture)
	{
		this.greyPicture = greyPicture;
	}

	public String getNoclickPicture()
	{
		return noclickPicture;
	}

	public void setNoclickPicture(String noclickPicture)
	{
		this.noclickPicture = noclickPicture;
	}

	public String getClickPicture()
	{
		return clickPicture;
	}

	public void setClickPicture(String clickPicture)
	{
		this.clickPicture = clickPicture;
	}

	public String getBgPicture()
	{
		return bgPicture;
	}

	public void setBgPicture(String bgPicture)
	{
		this.bgPicture = bgPicture;
	}
}
