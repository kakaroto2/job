package com.yoloboo.dao.impl;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.dao.ActivityDao;
import com.yoloboo.dao.BaseDao;
import com.yoloboo.models.ActivityModel;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/17.
 */

@Repository
public class ActivityDaoImpl extends BaseDao implements ActivityDao
{
	@Override
	public ActivityModel getModelByPK(Long id)
	{
		return (ActivityModel) sqlSession.selectOne("ActivityDao.getModelByPK", id);
	}

	@Override
	public List<ActivityModel> getListByStatus(ActivityBean ab) {
		return (List<ActivityModel>)sqlSession.selectList("ActivityDao.getListByStatus", ab);


	}

	@Override
	public int getStatusByPK(Long id) {
		return (int) sqlSession.selectOne("ActivityDao.getStatusByPK", id);
	}

}
