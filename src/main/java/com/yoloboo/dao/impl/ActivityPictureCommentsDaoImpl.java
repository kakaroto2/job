package com.yoloboo.dao.impl;

import com.yoloboo.controller.bean.ActivityBean;
import com.yoloboo.dao.ActivityPictureCommentsDao;
import com.yoloboo.models.ActivityPictureCommentsModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Remy4Pro on 2016/1/26.
 */
@Repository
public class ActivityPictureCommentsDaoImpl extends BaseDao implements ActivityPictureCommentsDao
{
    @Transactional
    @Override
    public void addActivityPictureComments(ActivityBean ab) {
        sqlSession.insert("ActivityPictureCommentsDao.addActivityPictureComments",ab);
        sqlSession.update("ActivityPictureDao.addPcomments",ab);
    }

    @Override
    public List<ActivityPictureCommentsModel> getActivityPictureCommentsListByApId(Long apId) {
        return (List<ActivityPictureCommentsModel>)sqlSession.selectList("ActivityPictureCommentsDao.getActivityPictureCommentsListByApId",apId);
    }

    @Transactional
    @Override
    public void deleteActivityPictureComments(Long apcId) {
        sqlSession.update("ActivityPictureDao.deletePcomments",apcId);
        sqlSession.delete("ActivityPictureCommentsDao.deleteActivityPictureComments",apcId);
    }
}
