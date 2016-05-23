package com.yoloboo.service;

import com.yoloboo.controller.BaseBean.TopicBean;
import com.yoloboo.excptions.ResultNotFoundException;
import com.yoloboo.models.TopicModel;

import java.util.List;


/**
 * Created by CoderZhao on 2016/1/15.
 */
public interface TopicService
{
	String createTopic(String qiniukey, String themeId, String topicName, String userId);

	List obtainTopicListById(String userId, String themId) throws ResultNotFoundException;

	void updateNoteTopicByNoteId(String travelNoteId, String topicId);

	List<TopicModel> getCategoryTopicList(TopicBean bean);

	TopicModel getTopicNoteList(TopicBean bean);
}
