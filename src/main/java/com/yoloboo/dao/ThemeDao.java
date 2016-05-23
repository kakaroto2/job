package com.yoloboo.dao;

import com.yoloboo.models.ThemeModel;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public interface ThemeDao
{
	ThemeModel getModelByPK(Long id);
}
