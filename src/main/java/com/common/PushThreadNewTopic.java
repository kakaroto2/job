package com.common;

import com.yoloboo.dao.TopicManager;

import java.util.HashMap;

/**
 * Created by CoderZhao on 2016/1/19.
 */
public class PushThreadNewTopic extends Thread {
    private TopicManager topicManager;
    private HashMap param;
    public PushThreadNewTopic(TopicManager topicManager,HashMap param){
        this.topicManager=topicManager;
        this.param=param;
    }
    @Override
    public void run(){
        super.run();
        topicManager.addNotificationNewTopic(this.param);
    }
}
