package com.yoloboo.dao;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.models.ActivityPictureCommentsModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/26.
 */
public interface ActivityPictureCommentsDao
{
    @Transactional
    void addActivityPictureComments(ActivityBean ab);

    List<ActivityPictureCommentsModel> getActivityPictureCommentsListByApId(Long apId);

    @Transactional
    void deleteActivityPictureComments(Long apcId);
}
