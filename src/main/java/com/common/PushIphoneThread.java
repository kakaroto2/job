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
//		PushUtils.push2MoreHashMap(p12Path, deviceTokens);

		for(HashMap msg : deviceTokens){
			 userManger.updateNotifyPushStatus(msg);
		 }
	}

	
}
