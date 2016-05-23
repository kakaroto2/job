package com.yoloboo.dao.impl;

import com.common.Commonparam;
import com.common.constans.NotificationListType;
import com.yoloboo.controller.BaseBean.TopicBean;
import com.yoloboo.dao.TopicManager;
import com.yoloboo.models.TopicModel;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


/**
 * Created by CoderZhao on 2016/1/15.
 */
@Repository
public class TopicManagerImpl extends BaseDao implements TopicManager
{
	@Override
	public void insertNewTopic(HashMap param)
	{
		sqlSession.insert("topic.insertNewTopic", param);
	}

	@Override
	public String obtainInsertTopicId(String userId)
	{
		return (String) sqlSession.selectOne("topic.selectInsertTopicId", userId);
	}

	@Override
	public List<TopicModel> obtainLastTopicById(HashMap param)
	{
		return (List<TopicModel>) sqlSession.selectList("topic.obtainLastTopicById", param);
	}

	@Override
	public List<TopicModel> obtainFriendsLastTopic(HashMap param)
	{
		return (List<TopicModel>) sqlSession.selectList("topic.obtainFriendsLastTopic", param);
	}

	@Override
	public List<TopicModel> obtainSystemHotestTopic(String themId)
	{
		return (List<TopicModel>) sqlSession.selectList("topic.obtainSystemHotestTopic", themId);
	}

	//新建话题消息
	@Override
	public void addNotificationNewTopic(HashMap param)
	{
		//获取好友列表
		List<HashMap> friendsList = (List<HashMap>) sqlSession
				.selectList("friends.obtainFriendsListById", param.get("userId").toString());
		//获取用户昵称
		String nickName = (String) sqlSession.selectOne("User.obtainNickNameById", param.get("userId").toString());
		HashMap param1 = new HashMap();
		for (HashMap map : friendsList)
		{
			param1.clear();
			param1.put("initiativeId", map.get("userId"));
			param1.put("userId", param.get("userId"));
			param1.put("addTime", Commonparam.Date2Str());
			String content;
			content = nickName + "[" + param.get("topicId");
			param1.put("content", content);
			param1.put("type", NotificationListType.NEW_TOPIC);
			param1.put("picture", param.get("qiniukey"));

			//不推送给自己
			if (!param.get("userId").equals( map.get("userId"))){
				sqlSession.insert("notification.addNotificationMessage", param1);
			}

		}
	}

	@Override
	public void updateNoteTopicByNoteId(HashMap map)
	{
		sqlSession.update("travel.updateNoteTopicByNoteId", map);
	}

	@Override
	public Integer getTopicCountByTheme(Long themeId)
	{
		Object object = sqlSession.selectOne("topic.getTopicCountByTheme", themeId);

		if (null != object)
		{
			return Integer.valueOf(object.toString());
		}
		return 0;
	}

	public List<TopicModel> getCategoryTopicList(TopicBean bean)
	{
		return (List<TopicModel>) sqlSession.selectList("topic.selectCategoryTopicList", bean);
	}

	@Override
	public TopicModel getTopicById(Long id)
	{
		return (TopicModel) sqlSession.selectOne("topic.selectTopicById", id);
	}
}
