package com.yoloboo.service;

import com.json.BaseBean;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;


/**
 * Created by ZHOU005 on 2015/12/28.
 */
public interface FriendsService
{
	void addFriends(String passivePeopleId, String userId, String type);

	void addListFriends(String listFriendsId, String userId);

	void uploadKey(String keyList, String userId, String type);

	void ignoreFriend(String userId, String passivePeopleId);

	BaseBean deleteFriendById(String friendUserId, String userId);

	HashMap obtainFriendsById(String userId, int page, int size, String content);

	List<HashMap> obtainRecommendFriends(String userId, int page, int size, String type);

	BaseBean addFriends_version1(String passivePeopleId, String userId, String type);

	BaseBean addListFriends_version1(String listFriendsId, String userId);

	BaseBean uploadKey_version1(String keyList, String userId, String type);
}
