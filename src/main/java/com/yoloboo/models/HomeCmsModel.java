package com.yoloboo.models;

import java.io.Serializable;


/**
 * Created by ZHOU005 on 2016/1/16.
 */
public class HomeCmsModel implements Serializable
{
	private Long id;
	private Long navId;
	private String navType;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getNavId()
	{
		return navId;
	}

	public void setNavId(Long navId)
	{
		this.navId = navId;
	}

	public String getNavType()
	{
		return navType;
	}

	public void setNavType(String navType)
	{
		this.navType = navType;
	}
}
