package com.yoloboo.dao.impl;

import com.yoloboo.dao.BaseDao;
import com.yoloboo.dao.HomeCmsDao;
import com.yoloboo.models.HomeCmsModel;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/16.
 */

@Repository
public class HomeCmsDaoimpl extends BaseDao implements HomeCmsDao
{

	@Override
	public List<HomeCmsModel> getNavContent()
	{
		return (List<HomeCmsModel>) sqlSession.selectList("HomeCmsDao.getNavContent");
	}

}
