package com.yoloboo.models;

import com.common.enums.SystemEnums.HomePageNavType;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public class UpdatePictureSortModel extends PictureModel
{
	private  Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
