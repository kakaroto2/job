package com.yoloboo.dao.impl;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.dao.*;
import com.yoloboo.models.ActivityCommentsModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/22.
 */

@Repository
public class ActivityCommentsDaoImpl extends BaseDao implements ActivityCommentsDao
{
    @Override
    public List<ActivityCommentsModel> getActivityCommentsListByAId(ActivityBean ab) {
        return (List<ActivityCommentsModel>)sqlSession.selectList("ActivityCommentsDao.getActivityCommentsListByAId",ab);
    }

    @Transactional
    @Override
    public void addActivityComments(ActivityBean ab) {
        sqlSession.insert("ActivityCommentsDao.addActivityComments",ab);
        sqlSession.update("ActivityDao.addComments",ab);
    }

    @Transactional
    @Override
    public void deleteActivityComments(Long acId) {
        sqlSession.update("ActivityDao.deleteComments",acId);
        sqlSession.delete("ActivityCommentsDao.deleteActivityComments",acId);
    }
}
