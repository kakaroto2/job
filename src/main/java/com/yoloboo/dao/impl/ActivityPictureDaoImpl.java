package com.yoloboo.dao.impl;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.dao.ActivityPictureDao;
import com.yoloboo.models.ActivityModel;
import com.yoloboo.models.ActivityPictureModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/22.
 */
@Repository
public class ActivityPictureDaoImpl extends BaseDao implements ActivityPictureDao
{
    @Override
    public List<ActivityPictureModel> getActivityPictureListByAId0(ActivityBean ab) {
        return (List<ActivityPictureModel>)sqlSession.selectList("ActivityPictureDao.getActivityPictureListByAId0",ab);
    }

    @Override
    public List<ActivityPictureModel> getActivityPictureListByAId1(ActivityBean ab) {
        return (List<ActivityPictureModel>)sqlSession.selectList("ActivityPictureDao.getActivityPictureListByAId1",ab);
    }

    @Override
    public List<ActivityPictureModel> getActivityPictureListByAId2(ActivityBean ab) {
        return (List<ActivityPictureModel>)sqlSession.selectList("ActivityPictureDao.getActivityPictureListByAId2",ab);
    }

    @Transactional
    @Override
    public ActivityPictureModel addActivityPicture(ActivityBean ab) {
        sqlSession.insert("ActivityPictureDao.addActivityPicture",ab);
        sqlSession.update("ActivityDao.addPicture",ab);
        return (ActivityPictureModel)sqlSession.selectOne("ActivityPictureDao.getLastInsertEntity");
    }

    @Override
    public ActivityPictureModel getModelByPK(Long id) {
        return (ActivityPictureModel)sqlSession.selectOne("ActivityPictureDao.getModelByPK",id);
    }

    @Override
    public Integer cntAwardedPic(Long aId) {
        return (Integer) sqlSession.selectOne("ActivityPictureDao.cntAwardedPic",aId);
    }
    @Override
    public Integer isPariseByUser(Object param) {
       return (Integer) sqlSession.selectOne("ActivityPictureDao.isPariseByUser",param);
    }

    @Override
    public HashMap getNotificationById(String notificationListId)
    {
        return (HashMap) sqlSession.selectOne("ActivityPictureDao.getNotificationById", notificationListId);
    }
}
