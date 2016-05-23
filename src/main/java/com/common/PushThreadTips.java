package com.common;

import java.util.HashMap;

import com.yoloboo.dao.TravelManager;

public class PushThreadTips extends Thread {
    private TravelManager travelManager;
    private HashMap pictureMap;
    
	public PushThreadTips(TravelManager travelManager ,HashMap pictureMap){
		this.pictureMap =  pictureMap;
		this.travelManager = travelManager;
	}
	@Override
	public void run() {
		super.run();
		travelManager.addNotificationTipsWithFriends(this.pictureMap);
		
	}

	
}
