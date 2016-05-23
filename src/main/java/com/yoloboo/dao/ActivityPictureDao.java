package com.yoloboo.dao;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.models.ActivityPictureModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/22.
 */
public interface ActivityPictureDao
{
    List<ActivityPictureModel> getActivityPictureListByAId0(ActivityBean ab);

    List<ActivityPictureModel> getActivityPictureListByAId1(ActivityBean ab);

    List<ActivityPictureModel> getActivityPictureListByAId2(ActivityBean ab);

    @Transactional
    ActivityPictureModel addActivityPicture(ActivityBean ab);

    ActivityPictureModel getModelByPK(Long id);

    Integer cntAwardedPic(Long aId);

    Integer isPariseByUser(Object param);

    HashMap getNotificationById(String notificationListId);
}
