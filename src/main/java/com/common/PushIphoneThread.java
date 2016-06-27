package com.common;

import java.util.HashMap;
import java.util.List;

import com.yoloboo.dao.UserManager;

public class PushIphoneThread extends Thread {
    private String p12Path;
    private List<HashMap<String,Object>> deviceTokens;
    private String content;
    private HashMap param;
    UserManager userManger;
	public PushIphoneThread(String p12Path, List<HashMap<String,Object>> deviceTokens,UserManager userManger){
		this.p12Path =  p12Path;
		this.deviceTokens = deviceTokens;
		this.userManger=userManger;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		//通过记录的最大主键  更改原来消息表中所有的记录
		userManger.updateNotifyPushStatus(Long.valueOf(deviceTokens.get(0).get("notificationListId").toString()));
		//全部插入到临时表中
		userManger.addNotificationTemp(deviceTokens);
		//通过读取临时表中的记录  包括以前未推送的消息进行推送

		List<HashMap<String,Object>>  list=userManger.selectNotSendNotification();

		System.out.println(list.size());

		//com.yoloboo.controller.PushUtils.push2MoreHashMap(p12Path, list);



	}

	
}
