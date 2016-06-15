package com.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

public class PushUtils {

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
		 push2More(p12File, deviceTokens,content,param);// 群组推送

//		/**
//		 * winphone推送
//		 */
//		List<Object> uriTokens = new ArrayList<Object>();
//		uriTokens.add("http://hk1.notify.live.net/throttledthirdparty/01.00/AQEPOwWkjv2dTq8HTw0tA4K_AgAAAAADcQAAAAQUZm52OjFDNzY1Q0UyNDU3NDcyMTQFBkFTRUEwMQ");
//        System.out.println("http://hk1.notify.live.net/throttledthirdparty/01.00/AQEPOwWkjv2dTq8HTw0tA4K_AgAAAAADbQAAAAQUZm52OjFDNzY1Q0UyNDU3NDcyMTQFBkFTRUEwMQ".equals("http://hk1.notify.live.net/throttledthirdparty/01.00/AQEPOwWkjv2dTq8HTw0tA4K_AgAAAAADbAAAAAQUZm52OjFDNzY1Q0UyNDU3NDcyMTQFBkFTRUEwMQ")); 
//		pushWinphoneToastNotifications(uriTokens, "顶顶顶", "方法方法方法呵呵呵呵方法方法方法呵呵呵呵方法方法方法呵呵呵呵方呵呵呵44");
		
		
	}

	
	/**
	 * 向单个iphone手机推送消息.
	 * 
	 * @param deviceToken
	 *            iphone手机获取的token
	 * @param p12File
	 *            CustomDictionary字典组
	 * @param content
	 *            推送内容
	 */
	public static void push2One(String p12File, String deviceToken,
			String content) {
		try {
			PayLoad payLoad = new PayLoad();
			payLoad.addAlert(content);// push的内容
			payLoad.addBadge(1);// 应用图标上小红圈上的数值
			payLoad.addSound("default");// 铃音
			
			// 添加字典
			payLoad.addCustomDictionary("url", "www.baidu.com");
			PushNotificationManager pushManager = PushNotificationManager
					.getInstance();
			pushManager.addDevice("iphone", deviceToken);

			// 链接到APNs
			pushManager.initializeConnection(host, port, p12File,
					p12FilePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);

			// 开始推送
			Device client = pushManager.getDevice("iphone");
			pushManager.sendNotification(client, payLoad);
			// 断开链接
			pushManager.stopConnection();
			pushManager.removeDevice("iphone");

		} catch (Exception e) {LogException.printException(e);

		}
	}

	/**
	 * 向iphone群组推送消息.
	 * 
	 * @param deviceTokens
	 *            iphone手机获取的token
	 * @param p12File
	 *            .p12格式的文件路径
	 * @param customDictionarys
	 *            CustomDictionary字典
	 * @param content
	 *            推送内容
	 */
	public static void push2More(String p12File, List<Object> deviceTokens,
			String content,HashMap customDictionarys) {
		
		PushNotificationManager pushManager = null;
		try {
			PayLoad payLoad = new PayLoad();
			payLoad.addAlert(content);// push的内容
			payLoad.addBadge(1);// 应用图标上小红圈上的数值
			payLoad.addSound("default");// 铃音
			
			// 添加字典
			if(customDictionarys!=null){
				for(Object key : customDictionarys.keySet()){
					payLoad.addCustomDictionary(key.toString(),customDictionarys.get(key).toString());
				}
			}
			
			pushManager = PushNotificationManager.getInstance();

			// 链接到APNs
			pushManager.initializeConnection(host, port, p12File,
					p12FilePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);

			// 开始循环推送
			for (int i = 0; i < deviceTokens.size(); i++) {
				if (deviceTokens.get(i) == null
						|| deviceTokens.get(i).toString().trim().length() == 0)
					continue;
				pushManager.addDevice("iphone" + i, deviceTokens.get(i).toString());
				Device client = pushManager.getDevice("iphone" + i);
				pushManager.sendNotification(client, payLoad);
			}

		} catch (Exception e) {
			LogException.printException(e);
			
		} finally {
			if (pushManager != null) {
				// 断开链接
				try {
					pushManager.stopConnection();
					for (int i = 0; i < deviceTokens.size(); i++) {
						pushManager.removeDevice("iphone" + i);
					}
				} catch (Exception e) {LogException.printException(e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
	/**
	 * 
	 * @param p12File
	 * @param tokenData:{content,pushToken,type}
	 */
	public static synchronized void push2MoreHashMap(String p12File, List<HashMap<String,Object>> tokenData) {
		
		PushNotificationManager pushManager = null;
		try {
			
			
			pushManager = PushNotificationManager.getInstance();
			System.out.println("1");
			// 链接到APNs
			pushManager.initializeConnection(host, port, p12File,
					p12FilePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
			System.out.println("2");
			// 开始循环推送
			for (int i = 0; i < tokenData.size(); i++) {
				System.out.println("3");
				logger.debug("send msg start. pushToken:"+tokenData.get(i).get("pushToken") +";content:"+tokenData.get(i).get("content").toString());
				if (tokenData.get(i).get("pushToken") == null
						|| tokenData.get(i).get("pushToken") .toString().trim().length() == 0)
					continue;
				System.out.println("4");
				PayLoad payLoad = new PayLoad();
				payLoad.addAlert(tokenData.get(i).get("content").toString());// push的内容
				payLoad.addBadge(1);// 应用图标上小红圈上的数值
				payLoad.addSound("default");// 铃音
				
				// 添加字典
				payLoad.addCustomDictionary("type",tokenData.get(i).get("type").toString());
					
				
				pushManager.addDevice("iphone" + i, tokenData.get(i).get("pushToken").toString());
				Device client = pushManager.getDevice("iphone" + i);
				pushManager.sendNotification(client, payLoad);
				logger.debug("send msg success. pushToken:"+tokenData.get(i).get("pushToken") +";content:"+tokenData.get(i).get("content").toString());
				System.out.println("5");
			}
			System.out.println("6");

		} catch (Exception e) {LogException.printException(e);
			System.out.println(e.getLocalizedMessage());
		} finally {
			if (pushManager != null) {
				// 断开链接
				try {
					pushManager.stopConnection();
					for (int i = 0; i < tokenData.size(); i++) {
						pushManager.removeDevice("iphone" + i);
					}
					System.out.println("7");
				} catch (Exception e) {LogException.printException(e);
					e.printStackTrace();
				}

			}
		}
	}
	/**
	 * 推送toast通知
	 * 
	 * @param uriString
	 *            推送服务通知uri
	 * @param title
	 *            toast标题
	 * @param content
	 *            toast内容
	 * @return 推送通知服务响应码
	 * @throws IOException
	 */
	public static int pushWinphoneToastNotifications(List<Object> uriString,
			String title, String content) {
		for (Object uri : uriString) {
			if (uri == null || uri.toString().trim().length() == 0)
				continue;
			String toastMsg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+ "<wp:Notification xmlns:wp=\"WPNotification\">"
					+ "<wp:Toast>" + "<wp:Text1>"+title+"</wp:Text1>"
					+ "<wp:Text2>" + content + "</wp:Text2>"
					+ "<wp:Param>/MainPage.xaml</wp:Param>" + "</wp:Toast>"
					+ "</wp:Notification>";

			URL url = null;
			HttpURLConnection http = null;
			StringBuffer sb = new StringBuffer();
			try {
				url = new URL(uri.toString());
				http = (HttpURLConnection) url.openConnection();

				http.setDoOutput(true);
				http.setRequestMethod("POST");
				http.setRequestProperty("Content-Type",
						"text/xml");
				http.setRequestProperty("X-WindowsPhone-Target", "toast");
				http.setRequestProperty("X-NotificationClass", "2");
				http.setRequestProperty("Content-Length", "1024");
				http.getOutputStream().write(toastMsg.getBytes("utf-8"));
				// 刷新对象输出流，将任何字节都写入潜在的流中
				http.getOutputStream().flush();
				// 关闭输出流
				http.getOutputStream().close();
				int rt = http.getResponseCode();
				if (rt == 200) {
					InputStream in = http.getInputStream();
					byte[] b = new byte[1024];
					int i = 0;
					while ((i = in.read(b)) != -1) {
						sb.append(new String(b, 0, i, "UTF-8"));
					}
				}
				

			} catch (Exception e) {LogException.printException(e);
				e.printStackTrace();
			} finally {
				if (http != null) {
					http.disconnect();
				}
			}
			System.out.println(sb.toString());
		}

		return 1;
	}
}
