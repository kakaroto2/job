package com.yoloboo.service;

import com.yoloboo.models.TravelStyleModel;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/9.
 */
public interface TravelStyleService
{

	//根据版本获得旅行风格
	List obtainTravelStyle(int version);

	TravelStyleModel getModelByPK(Long id);
}
