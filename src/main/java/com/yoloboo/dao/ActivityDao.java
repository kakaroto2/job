package com.yoloboo.dao;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.models.ActivityModel;

import java.util.List;


/**
 * Created by ZHOU005 on 2016/1/17.
 */
public interface ActivityDao
{
	ActivityModel getModelByPK(Long id);
	List<ActivityModel> getListByStatus(ActivityBean ab);
	int getStatusByPK(Long id);
	List<ActivityModel>   getActivityList();
	void updateNotificationStatus();
}
