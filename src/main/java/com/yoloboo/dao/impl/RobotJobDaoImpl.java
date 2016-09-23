package com.yoloboo.dao.impl;
import com.yoloboo.dao.RobotJobDao;
import com.yoloboo.models.RobotJob;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by maojiancai on 16/9/18.
 */
@Repository
public class RobotJobDaoImpl extends BaseDao implements RobotJobDao {

    @Override
    public List<RobotJob> getRobotJobs(HashMap param) {
        return (List<RobotJob>) sqlSession.selectList("RobotJobDao.getRobotJobs",param);
    }

    @Override
    public Long getRandomPicIdByNote(Long noteId) {
        return (Long) sqlSession.selectOne("RobotJobDao.getRandomPicIdByNote",noteId);
    }

    @Override
    public void addRobotJob(HashMap map) {
        sqlSession.insert("RobotJobDao.addRobotJob",map);
    }

    @Override
    public Long getRobotForNote(String userId,String picId) {
        HashMap param = new HashMap();
        param.put("userId",userId);
        param.put("picId",picId);
        return (Long) sqlSession.selectOne("RobotJobDao.getRobotForNote",userId);
    }

    @Override
    public Long getRobotForActivity(String userId,String picId) {
        HashMap param = new HashMap();
        param.put("userId",userId);
        param.put("picId",picId);
        return (Long) sqlSession.selectOne("RobotJobDao.getRobotForActivity",userId);
    }

    @Override
    public Integer getNumByUserAndNoteType(String userId) {
        return (Integer) sqlSession.selectOne("RobotJobDao.getNumByUserAndNoteType",userId);
    }

    @Override
    public void updateRobertsNum(String userId) {
        sqlSession.update("RobotJobDao.updateRobertsNum",userId);
    }

    @Override
    public List<HashMap> getJobs(String endTime) {
        return (List<HashMap>) sqlSession.selectList("RobotJobDao.getJobs",endTime);
    }

    @Override
    public void updateFlag(String id) {
        sqlSession.update("RobotJobDao.updateFlag",id);
    }

    @Override
    public void updteReadNum(String noteId) {
        sqlSession.update("RobotJobDao.updteReadNum",noteId);
    }
}
