package com.yoloboo.dao;

import com.yoloboo.controller.BaseBean.TopicBean;
import com.yoloboo.models.TopicModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CoderZhao on 2016/1/15.
 */
public interface TopicManager {
    void insertNewTopic(HashMap param);

    String obtainInsertTopicId(String userId);

    List<TopicModel> obtainLastTopicById(HashMap param);

    List<TopicModel> obtainFriendsLastTopic(HashMap param);

    List<TopicModel> obtainSystemHotestTopic(String themId);

    void addNotificationNewTopic(HashMap param);

    void updateNoteTopicByNoteId(HashMap map);

    Integer getTopicCountByTheme(Long themeId);

    List<TopicModel> getCategoryTopicList(TopicBean bean);

    TopicModel getTopicById(Long id);
}
