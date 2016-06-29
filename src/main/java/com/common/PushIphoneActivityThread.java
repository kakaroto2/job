package com.common;

import java.util.ArrayList;
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

        //对list进行截取
       List<List<HashMap<String,Object>>>  resultList=getSubList(deviceTokens,50);

        //循环进行处理   这样每100条就会重连一次苹果服务器
        for (List<HashMap<String,Object>> list:resultList){
            try{
                com.yoloboo.controller.PushUtils.pushMoreHashMap(p12Path, list);
                Thread.currentThread().sleep(5000);//每断开一次 休息5秒
            }catch(Exception e){
                e.printStackTrace();
                continue;
            }


        //如果上次执行过程中虽然以前的feedback已经全部处理完 但是之后新进来的用户依然有人已经卸载了app 则需要找出这些用户重新发送 再去获取一次feedback
      //  PushUtils.pushByFeedBack(p12Path,deviceTokens);
       }
    }

    /**
     * 将一个List按照固定的大小拆成很多个小的List
     *
     * @param listObj
     *            需要拆分的List
     * @param groupNum
     *            每个List的最大长度
     * @return 拆分后的List的集合
     */
    public static <T> List<List<T>> getSubList(List<T> listObj, int groupNum) {
        List<List<T>> resultList = new ArrayList<List<T>>();
        // 获取需要拆分的List个数
                int loopCount = (listObj.size() % groupNum == 0) ? (listObj.size() / groupNum)
                        : ((listObj.size() / groupNum) + 1);
        // 开始拆分
                for (int i = 0; i < loopCount; i++) {
        // 子List的起始值
                    int startNum = i * groupNum;
        // 子List的终止值
                    int endNum = (i + 1) * groupNum;
        // 不能整除的时候最后一个List的终止值为原始List的最后一个
                    if (i == loopCount - 1) {
                        endNum = listObj.size();
                    }
        // 拆分List
                    List<T> listObjSub = listObj.subList(startNum, endNum);
        // 保存差分后的List
                    resultList.add(listObjSub);
                }
                return resultList;

            }


}
