package com.common;

import java.util.HashMap;
import java.util.List;

import com.yoloboo.dao.UserManager;

public class PushIphoneActivityThread extends Thread {
    private String p12Path;
    private List<HashMap<String,Object>> deviceTokens;
    private String content;
    private HashMap param;
    public PushIphoneActivityThread(String p12Path, List<HashMap<String,Object>> deviceTokens){
        this.p12Path =  p12Path;
        this.deviceTokens = deviceTokens;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();

       // userManger.updateActivityNotifyPushStatus();

        PushUtils.push2MoreHashMap(p12Path, deviceTokens);
    }


}
