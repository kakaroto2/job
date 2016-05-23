package com.common;

//import java.io.IOException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.common.StringUtil;
import com.github.kevinsawicki.http.HttpRequest;

public class Sendmessage {

	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	public static void main(String[] args) {

		sengmessage("18916291082", 1234);
		sengmessageAbroad("18916291082", 1234);
		sengmessageTanwan("18916291082", 1234);
	}
	private static void sendmessage(String phone, String message){
		if(phone!=null&&phone.contains(" ")){//国外
			sendmessage(phone, message,"cf_xuangong","Develop2015");
		}else{//国内
			sendmessage(phone, message,"cf_yoloboo","Develop2015");
		}
	}
	private static void sendmessage(String phone, String message,String account,String password) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("account", account);
		data.put("password", password);
		data.put("mobile", phone);
		data.put("content", message);

		try {

			String SubmitResult = HttpRequest.post(Url).form(data).body();

			// System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);

			if ("2".equals(code)) {
				System.out.println("");
			}
		} catch (Exception e) {LogException.printException(e);
			e.printStackTrace();
		}
	}

	// 国内的短信
	public static void sengmessage(String phone, int mobile_code) {
		String content = new String("您的手机验证码为：【" + mobile_code + "】，请在30分钟内按提示提交验证码。");
		sendmessage(phone, content, "cf_yoloboo", "Develop2015");
	}

	// 国外的短信
	public static void sengmessageAbroad(String phone, int mobile_code) {
		String content = new String(
				"Your YOLOBOO verification code is: " + mobile_code + " , it will expire in 30 mins");
		sendmessage(phone, content,"cf_xuangong","Develop2015");
	}

	// 台
	public static void sengmessageTanwan(String phone, int mobile_code) {

		String content = new String("您的YOLOBOO驗證碼為: " + mobile_code + " ，請30分鐘內按提示提交驗證碼。");
		sendmessage(phone, content,"cf_xuangong","Develop2015");

	}
	public static void sendVerificationCode(String phone,String code,final int mobile_code){
		if (code.replace("+", "").equals("86")) {// 是中国的
			//开启一个,让
			final String ph=phone;
			Thread t = new Thread(new Runnable(){
				public void run(){
					sengmessage(ph, mobile_code);
				}});
			t.start();
		} else if (code.replace("+", "").equals("886")) {// 是台湾省的
			phone = "886 0" + phone;
			final String ph=phone;
			Thread t = new Thread(new Runnable(){
				public void run(){
					sengmessageTanwan(ph,mobile_code);
				}});
			t.start();
		} else// 是非中国的 国家
		{
			phone = code.replace("+", "") + " " + phone;
			final String ph=phone;
			Thread t = new Thread(new Runnable(){
				public void run(){
					sengmessageAbroad(ph,mobile_code);
				}});
			t.start();
		}
	}
}