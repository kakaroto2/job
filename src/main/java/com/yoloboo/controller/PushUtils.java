package com.yoloboo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.common.LogException;
import com.yoloboo.dao.UserManager;
import javapns.feedback.AppleFeedbackServerBasicImpl;
import javapns.feedback.FeedbackServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class PushUtils {

    @Autowired
    private UserManager userManger;


    private static PushUtils pushUtils;


    @PostConstruct
    public void init() {
        pushUtils = this;
        pushUtils.userManger = this.userManger;

    }


    private static Logger logger = LoggerFactory.getLogger(PushUtils.class);

    /************************************************
     * 测试推送服务器地址：gateway.sandbox.push.apple.com /2195
     * 产品推送服务器地址：gateway.push.apple.com / 2195
     ***************************************************/
    private static String host = "gateway.push.apple.com";
    private static int port = 2195;
    private static String p12FilePassword = "apple2016!";


    /**
     *点对点
     * @param p12File
     * @param tokenData:{content,pushToken,type}
     */
    public static synchronized void push2MoreHashMap(String p12File, List<HashMap<String,Object>> tokenData) {

		PushNotificationManager pushManager = null;
		try {

            pushManager = new PushNotificationManager();
            // true：表示的是产品测试推送服务 false：表示的是产品发布推送服务
            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
                    p12File, p12FilePassword, true));
			// 开始循环推送
			for (int i = 0; i < tokenData.size(); i++) {
				if (tokenData.get(i).get("pushToken") != null
						&& tokenData.get(i).get("pushToken") .toString().trim().length()!= 0)
				{
                    PushNotificationPayload payLoad = new PushNotificationPayload();
					payLoad.addAlert(tokenData.get(i).get("content").toString());// push的内容
					payLoad.addBadge(1);// 应用图标上小红圈上的数值
					payLoad.addSound("default");// 铃音
					// 添加字典
					payLoad.addCustomDictionary("type",tokenData.get(i).get("type").toString());
                    Device device = new BasicDevice();

                    device.setToken(tokenData.get(i).get("pushToken").toString());
                    PushedNotification notification = pushManager.sendNotification(
                            device, payLoad, true);
                    //根据返回的信息  进行处理
                    if(!notification.isSuccessful()){
                        //从当前这条开始进行重新连接  并且将数据库的该条token置为空
                        System.out.println("fail: "+notification.getDevice().getToken());
                    }else{
                        System.out.println("success: "+notification.getDevice().getToken());
                    }
				}
				//删除该条临时表中的记录
				pushUtils.userManger.deleteTemp(Long.valueOf(tokenData.get(i).get("notificationListId").toString()));
			}
		} catch (Exception e) {
            LogException.printException(e);
        }
		 finally {
			if (pushManager != null) {
				// 断开链接
				try {
					pushManager.stopConnection();
				} catch (Exception e) {LogException.printException(e);
					e.printStackTrace();
				}
			}
		}
    }


    /**
     *  群发
     * @param p12File
     * @param tokenData:{content,pushToken,type}
     */
    public static synchronized void pushMoreHashMap(String p12File, List<HashMap<String,Object>> tokenData) {

        PushNotificationManager pushManager = null;
        FeedbackServiceManager feedbackServiceManager=null;
        try {

            pushManager = new PushNotificationManager();

            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
                    p12File, p12FilePassword, true));

            feedbackServiceManager=new FeedbackServiceManager();

            List<Device>   list=feedbackServiceManager.getDevices(new AppleFeedbackServerBasicImpl(p12File, p12FilePassword, true));

            if(list.size()>0){
                pushUtils.userManger.addBatchUseLessToken(list);
            }
            // true：表示的是产品测试推送服务 false：表示的是产品发布推送服务

            // 开始循环推送
            for (int i = 0; i < tokenData.size(); i++) {
                if (tokenData.get(i).get("pushToken") == null
                        || tokenData.get(i).get("pushToken") .toString().trim().length() == 0)
                    continue;

                PushNotificationPayload payLoad = new PushNotificationPayload();
                payLoad.addAlert(tokenData.get(i).get("content").toString());// push的内容
                payLoad.addBadge(1);// 应用图标上小红圈上的数值
                payLoad.addSound("default");// 铃音

                // 添加字典
                payLoad.addCustomDictionary("type",tokenData.get(i).get("type").toString());

                Device device = new BasicDevice();

                device.setToken(tokenData.get(i).get("pushToken").toString());

                System.out.println(payLoad.getPayloadAsBytes().length);

                PushedNotification notification = pushManager.sendNotification(
                        device, payLoad, true);
                //根据返回的信息  进行处理
                if(!notification.isSuccessful()){
                    //从当前这条开始进行重新连接  递归循环
                    System.out.println("fail: "+notification.getDevice().getToken());
                    pushManager.stopConnection();
                    //将这个错误无效token 插入到表中
                    pushUtils.userManger.insertFailToken(notification.getDevice().getToken());
                    List<HashMap<String,Object>>  sublist=new ArrayList<HashMap<String, Object>>();
                    sublist=tokenData.subList((i+1),tokenData.size());
                    if(sublist.size()>0){
                        pushMoreHashMap(p12File, sublist);
                    }else{
                        //结束循环  全部发送完毕
                        pushManager.stopConnection();
                    }
                    break;
                }else{
                    System.out.println("success: "+notification.getDevice().getToken());
                }

            }

        } catch (Exception e) {
            LogException.printException(e);
            System.out.println(e.getLocalizedMessage());
        } finally {
            if (pushManager != null) {
                // 断开链接
                try {
                    pushManager.stopConnection();
                } catch (Exception e) {
                    LogException.printException(e);
                }

            }
        }
    }



    /**
     *  第一次获取FeedBack内容
     * @param p12File
     */
    public static synchronized void getFeedBack(String p12File) {

        PushNotificationManager pushManager = null;
        FeedbackServiceManager feedbackServiceManager=null;
        try {

            pushManager = new PushNotificationManager();


            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
                    p12File, p12FilePassword, true));

            feedbackServiceManager=new FeedbackServiceManager();

            List<Device>   list=feedbackServiceManager.getDevices(new AppleFeedbackServerBasicImpl(p12File, p12FilePassword, true));

            //针对list  暂时存在数据库
            if(list.size()>0){
                pushUtils.userManger.addBatchUseLessToken(list);
            }

            //对于feedback和fail产生的无效token进行处理
            List<HashMap<String,Object>>    tokenList=pushUtils.userManger.getUseLessToken();

            if(tokenList.size()>0){
                for(HashMap map:tokenList){
                    pushUtils.userManger.updateUseLessToken(map);
                }
            }

        } catch (Exception e) {
            LogException.printException(e);
            System.out.println(e.getLocalizedMessage());
        } finally {
            if (pushManager != null) {
                // 断开链接
                try {
                    pushManager.stopConnection();
                } catch (Exception e) {
                    LogException.printException(e);
                }

            }
        }
    }


    public static void removeElements(List list,int start,int end){
        if(list!=null&&list.size()>0){
            for(int i=start-1;i<end-1;i++){
                Object o = list.get(i);
                if(o!=null){
                    list.remove(i);
                }
            }
        }
    }
    public static void main(String[] args) {
        List<HashMap<String,Object>>  list=new ArrayList<HashMap<String, Object>>();

        HashMap<String,Object>  map=new HashMap<>();
        HashMap<String,Object>  map1=new HashMap<>();
        HashMap<String,Object>  map2=new HashMap<>();
        HashMap<String,Object>  map3=new HashMap<>();
        HashMap<String,Object>  map4=new HashMap<>();

        map.put("0","0");
        map1.put("1","1");
        map2.put("2","2");
        map3.put("3","3");
        map4.put("4","4");

        list.add(0,map);
        list.add(1,map1);
        list.add(2,map2);
        list.add(3,map3);
        list.add(4,map4);

        test(list);

    }
    public  static void test(List<HashMap<String,Object>>  list){
        List<HashMap<String,Object>>  sublist=new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < list.size(); i++){
            if(i==1){
                sublist=list.subList(i+1,list.size());
                test(sublist);
            }
        }

    }


}
