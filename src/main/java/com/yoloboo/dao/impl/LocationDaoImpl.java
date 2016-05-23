package com.yoloboo.dao.impl;

import com.yoloboo.dao.LocationDao;
import com.yoloboo.models.LocationModel;
import org.springframework.stereotype.Repository;


/**
 * Created by ZHOU005 on 2016/3/5.
 */

@Repository
public class LocationDaoImpl extends BaseDao implements LocationDao
{

	@Override
	public LocationModel getModelByPK(Long id)
	{
		return (LocationModel) sqlSession.selectOne("LocationDao.getModelByPK", id);
	}
}
