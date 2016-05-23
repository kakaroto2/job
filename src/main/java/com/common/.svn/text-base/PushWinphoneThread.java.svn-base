package com.common;

import java.util.ArrayList;
import java.util.List;

public class PushWinphoneThread extends Thread {
    private String title;
    private List<Object> uriList;
    private String content;
    
	public PushWinphoneThread(List<Object> uriList,String  title, String content){
		this.title =  title;
		this.uriList = uriList;
		this.content = content;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		PushUtils.pushWinphoneToastNotifications(uriList, title, content);
		
	}

	
}
