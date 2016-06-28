package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huhaosumail on 16/6/28.
 */
public class PushIphoneFeedBackThread extends Thread{
    private String p12Path;
    private List<HashMap<String,Object>> deviceTokens;
    private String content;
    private HashMap param;
    public PushIphoneFeedBackThread(String p12Path){
        this.p12Path =  p12Path;
    }
    @Override
    public void run() {

        super.run();

        com.yoloboo.controller.PushUtils.getFeedBack(p12Path);

    }


}
