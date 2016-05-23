package com.yoloboo.service.impl;

import com.common.constans.SystemContent;
import com.yoloboo.dao.BaseDao;
import com.yoloboo.dao.TravelManager;
import com.yoloboo.models.TravelStyleModel;
import com.yoloboo.service.TravelStyleService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/9.
 */

@Repository
public class TravelStyleServiceImpl extends BaseDao implements TravelStyleService
{
	@Resource
	private TravelManager travelManager;

	@Override
	public List obtainTravelStyle(int version)
	{
		return travelManager.obtainTravelStyle(SystemContent.version_2);
	}

	@Override
	public TravelStyleModel getModelByPK(Long id) {
		return (TravelStyleModel) sqlSession.selectOne("TravelStyleDao.getModelByPK",id);
	}

}



