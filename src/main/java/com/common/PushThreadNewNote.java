package com.common;

import com.yoloboo.dao.TravelManager;

import java.util.HashMap;

/**
 * Created by CoderZhao on 2016/1/19.
 */
public class PushThreadNewNote extends Thread {
    private TravelManager travelManager;
    private HashMap param;
    public PushThreadNewNote(TravelManager travelManager,HashMap param){
        this.travelManager=travelManager;
        this.param=param;
    }
    @Override
    public void run(){
        super.run();
        travelManager.addNotificationNewNote(this.param);
    }
}
