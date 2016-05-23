package com.yoloboo.dao.impl;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.dao.ActivityPicturePraiseDao;
import com.yoloboo.models.ActivityPictureCommentsModel;
import com.yoloboo.models.ActivityPicturePraiseModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/26.
 */
@Repository
public class ActivityPicturePraiseDaoImpl extends BaseDao implements ActivityPicturePraiseDao
{

    @Transactional
    @Override
    public void addActivityPicturePraise(ActivityBean ab) {
        sqlSession.insert("ActivityPicturePraiseDao.addActivityPicturePraise",ab);
        sqlSession.update("ActivityPictureDao.addPraise",ab);
    }

    @Transactional
    @Override
    public void cancelActivityPicturePraise(ActivityBean ab) {
        sqlSession.delete("ActivityPicturePraiseDao.cancelActivityPicturePraise",ab);
        //取消点赞 需要将点赞的数目减掉
        sqlSession.update("ActivityPictureDao.cancelPraise",ab);
        //取消点赞不需要将评论的总数也减
        //sqlSession.update("ActivityPictureDao.cancelPcomments",ab);
    }

    @Override
    public List<ActivityPicturePraiseModel> getActivityPicturePraiseListByApId(Long apId) {
        return (List<ActivityPicturePraiseModel>)sqlSession.selectList("ActivityPicturePraiseDao.getActivityPicturePraiseListByApId",apId);
    }
    @Override
    public List<ActivityPicturePraiseModel> getPictureId()
    {
        return (List<ActivityPicturePraiseModel>) sqlSession.selectList("ActivityPicturePraiseDao.getPictureId");
    }


    @Override
    public List<ActivityPicturePraiseModel> getPictureById(Long apId)
    {
        return (List<ActivityPicturePraiseModel>) sqlSession.selectList("ActivityPicturePraiseDao.getPictureById",apId);
    }

    @Override
    public   List<ActivityPicturePraiseModel> getModel(ActivityPicturePraiseModel model)
    {
        return  (List<ActivityPicturePraiseModel>)sqlSession.selectList("ActivityPicturePraiseDao.getModel",model);
    }

    @Override
    public void deleteData(ActivityPicturePraiseModel model) {
        sqlSession.delete("ActivityPicturePraiseDao.deleteData",model);
    }
    @Override
    public void insertModel(ActivityPicturePraiseModel model) {
        sqlSession.insert("ActivityPicturePraiseDao.insertModel",model);
    }
}
