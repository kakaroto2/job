package com.yoloboo.models;

import com.common.enums.SystemEnums.HomePageNavType;

import java.io.Serializable;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public class CmsContentModel implements Serializable
{
	private HomePageNavType contentType;

	public HomePageNavType getContentType()
	{
		return contentType;
	}

	public void setContentType(HomePageNavType contentType)
	{
		this.contentType = contentType;
	}
}
