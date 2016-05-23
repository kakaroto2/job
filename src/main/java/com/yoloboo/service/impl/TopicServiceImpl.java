package com.yoloboo.service.impl;

import com.common.Commonparam;
import com.common.PushThreadNewTopic;
import com.common.QiNiuUtils;
import com.yoloboo.controller.BaseBean.TopicBean;
import com.yoloboo.dao.TravelNoteDao;
import com.yoloboo.dao.UserManager;
import com.yoloboo.excptions.ResultNotFoundException;
import com.yoloboo.dao.TopicManager;
import com.yoloboo.models.TopicModel;
import com.yoloboo.models.UserModel;
import com.yoloboo.service.TopicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * Created by CoderZhao on 2016/1/15.
 */
@Service
public class TopicServiceImpl implements TopicService
{

	@Resource
	private UserManager userManager;

	@Resource
	private TopicManager topicManager;

	@Resource
	private TravelNoteDao travelNoteDao;


	@Override
	public String createTopic(String qiniukey, String themeId, String topicName, String userId)
	{
		HashMap param = new HashMap();
		UserModel userModel = userManager.selectUserByPrimaryKey(Long.valueOf(userId));

		param.put("qiniukey", QiNiuUtils.getSaveDBUrl2(qiniukey));
		param.put("themeId", themeId);
		param.put("topicName", topicName);
		param.put("userId", userId);
		param.put("nickName", userModel.getuNickname());
		param.put("addTime", Commonparam.Date2Str());
		//插入话题
		topicManager.insertNewTopic(param);
		//保存至消息表
		String tId = topicManager.obtainInsertTopicId(userId);
		param.put("topicId", tId);
		new PushThreadNewTopic(topicManager, param).start();
		return tId;
	}

	@Override
	public List obtainTopicListById(String userId, String themId) throws ResultNotFoundException
	{
		List<TopicModel> topicList = new ArrayList();
		HashMap param = new HashMap();
		param.put("userId", userId);
		param.put("themeId", themId);
		List<TopicModel> l2 = topicManager.obtainLastTopicById(param);
		List<TopicModel> l1 = topicManager.obtainFriendsLastTopic(param);
		//List<TopicModel> l3 = topicManager.obtainSystemHotestTopic(themId);
		TopicBean topicBean = new TopicBean();
		topicBean.setThemeId(Long.valueOf(themId));
		topicBean.setPageIndex(1);

		List<TopicModel>  l3 = getCategoryTopicList(topicBean);
		topicList.addAll(l1);
		topicList.addAll(l2);
		topicList.addAll(l3);

		List<TopicModel> unRepeatedTopicList = new ArrayList();
		HashSet<Long> hs = new HashSet<Long>();
		for(TopicModel tm : topicList){
			if(!hs.contains(tm.getId())){
				hs.add(tm.getId());
				unRepeatedTopicList.add(tm);
			}

		}


//		if(unRepeatedTopicList.size() < 4)
//		{

//
//			topicList.clear();
//
//			topicList.addAll(l1);
//			topicList.addAll(l2);
//			topicList.addAll(l3);
//
//			unRepeatedTopicList.clear();
//			hs.clear();
//			for(TopicModel tm : topicList){
//				if(!hs.contains(tm.getId())){
//					hs.add(tm.getId());
//					unRepeatedTopicList.add(tm);
//				}
//
//			}
		if (unRepeatedTopicList.size() == 0)
		{
			throw new ResultNotFoundException();
		}
		//	}

		return unRepeatedTopicList.subList(0,4);

	}

	@Override
	public void updateNoteTopicByNoteId(String travelNoteId, String topicId)
	{
		HashMap map = new HashMap();
		map.put("travelNoteId", travelNoteId);
		map.put("topicId", topicId);
		topicManager.updateNoteTopicByNoteId(map);
	}

	@Override
	public List<TopicModel> getCategoryTopicList(TopicBean bean)
	{
		return topicManager.getCategoryTopicList(bean);
	}

	@Override
	public TopicModel getTopicNoteList(TopicBean bean)
	{
		TopicModel topicModel = topicManager.getTopicById(bean.getId());
		topicModel.setTravelNotes(travelNoteDao.getModelsByTopicUser(bean));

		return topicModel;
	}

}
