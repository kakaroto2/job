package com.yoloboo.dao;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.models.ActivityCommentsModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/22.
 */
public interface ActivityCommentsDao {

    List<ActivityCommentsModel> getActivityCommentsListByAId(ActivityBean ab);

    @Transactional
    void addActivityComments(ActivityBean ab);
    @Transactional
    void deleteActivityComments(Long acId);
}
