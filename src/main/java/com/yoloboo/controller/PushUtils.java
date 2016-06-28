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



    public static void main(String[] args) throws Exception {

//		/**
//		 * iphone推送
//		 */
        List<Object> deviceTokens = new ArrayList<Object>();
        deviceTokens.add("de3d570aa5495ebd2f54f1cdbb7d77923bfa0aa58228a8b7d6b8e89bdcc37f74");
        String content ="正式：您收到YOLOOO一条消息!";
        String p12File = "d:/cert.p12";// 这里是一个.p12格式的文件路径，需要去apple官网申请一个
        HashMap  param =new HashMap<String,Object>();
        param.put("type", 1);

//		/**
//		 * winphone推送
//		 */
//		List<Object> uriTokens = new ArrayList<Object>();
//		uriTokens.add("http://hk1.notify.live.net/throttledthirdparty/01.00/AQEPOwWkjv2dTq8HTw0tA4K_AgAAAAADcQAAAAQUZm52OjFDNzY1Q0UyNDU3NDcyMTQFBkFTRUEwMQ");
//        System.out.println("http://hk1.notify.live.net/throttledthirdparty/01.00/AQEPOwWkjv2dTq8HTw0tA4K_AgAAAAADbQAAAAQUZm52OjFDNzY1Q0UyNDU3NDcyMTQFBkFTRUEwMQ".equals("http://hk1.notify.live.net/throttledthirdparty/01.00/AQEPOwWkjv2dTq8HTw0tA4K_AgAAAAADbAAAAAQUZm52OjFDNzY1Q0UyNDU3NDcyMTQFBkFTRUEwMQ"));
//		pushWinphoneToastNotifications(uriTokens, "顶顶顶", "方法方法方法呵呵呵呵方法方法方法呵呵呵呵方法方法方法呵呵呵呵方呵呵呵44");


    }

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
       // FeedbackServiceManager feedbackServiceManager=null;
        try {

            pushManager = new PushNotificationManager();
           // feedbackServiceManager=new FeedbackServiceManager();
           // List<Device>   list=feedbackServiceManager.getDevices(new AppleFeedbackServerBasicImpl(p12File, p12FilePassword, true));

            //针对list  暂时存在数据库
          //  pushUtils.userManger.addBatchUseLessToken(list);
            // true：表示的是产品测试推送服务 false：表示的是产品发布推送服务
            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
                    p12File, p12FilePassword, true));
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
}
