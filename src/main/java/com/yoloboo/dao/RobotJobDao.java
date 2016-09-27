package com.yoloboo.dao;

import com.yoloboo.models.RobotJob;

import java.util.HashMap;
import java.util.List;

/**
 * Created by maojiancai on 16/9/18.
 */
public interface RobotJobDao {

    List<RobotJob> getRobotJobs(HashMap param);

    Long getRandomPicIdByNote(Long noteId);

    void addRobotJob(HashMap map);
    //找出可以关注点赞画报的机器人
    Long getRobotForNote(String userId,String picId);
    //找出可以关注点赞活动照片的机器人
    Long getRobotForActivity(String userId,String picId);
    //查看该用户第几次发布画报被机器人点赞
    Integer getNumByUserAndNoteType(String userId);

    void updateRobertsNum(String userId);

    List<HashMap> getJobs(String endTime);

    //更新任务的执行状态
    void updateFlag(String id);

    //更新画报的阅读量
    void updteReadNum(String noteId);

    //通过画报照片找出用户的Id
    Integer getUserIdByPic(String picId);
}

