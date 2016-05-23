package com.yoloboo.dao;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.models.ActivityPictureCommentsModel;
import com.yoloboo.models.ActivityPicturePraiseModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/26.
 */
public interface ActivityPicturePraiseDao
{
    @Transactional
    void addActivityPicturePraise(ActivityBean ab);

    @Transactional
    void cancelActivityPicturePraise(ActivityBean ab);

    List<ActivityPicturePraiseModel> getActivityPicturePraiseListByApId(Long apId);

    List<ActivityPicturePraiseModel> getPictureId();

    List<ActivityPicturePraiseModel> getPictureById(Long apId);

    List<ActivityPicturePraiseModel>  getModel(ActivityPicturePraiseModel model);

    void deleteData(ActivityPicturePraiseModel model);

    void insertModel(ActivityPicturePraiseModel model);
}
