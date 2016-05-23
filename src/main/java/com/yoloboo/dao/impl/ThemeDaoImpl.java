package com.yoloboo.dao.impl;

import com.yoloboo.dao.BaseDao;
import com.yoloboo.dao.ThemeDao;
import com.yoloboo.models.ThemeModel;
import org.springframework.stereotype.Repository;


/**
 * Created by ZHOU005 on 2016/1/17.
 */

@Repository
public class ThemeDaoImpl extends BaseDao implements ThemeDao
{

	@Override
	public ThemeModel getModelByPK(Long id)
	{
		return (ThemeModel)sqlSession.selectOne("ThemeDao.getModelByPK", id);
	}

}
