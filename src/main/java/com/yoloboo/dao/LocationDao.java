package com.yoloboo.dao;

import com.yoloboo.models.LocationModel;


/**
 * Created by ZHOU005 on 2016/3/5.
 */
public interface LocationDao
{
	LocationModel getModelByPK(Long id);
}
