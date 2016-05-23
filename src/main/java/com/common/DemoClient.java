package com.common;

import java.io.UnsupportedEncodingException;
import java.net.*;

public class DemoClient {

	private static String sn = PublicConfig.SN;
	private static String pwd = PublicConfig.PWD;

	public static void sendMsg(String info, String phone)
			throws UnsupportedEncodingException {

		Client client = new Client(sn, pwd);

		info = info + PublicConfig.SNEND;

		// 短信发送
		String content = URLEncoder.encode(info, "utf8");
		String result_mt = client.mdsmssend(phone, content, "", "", "", "");
		System.out.print(result_mt);
	}

	public static void sendMsg(String phone, String userPhone, String url)
			throws UnsupportedEncodingException {

		Client client = new Client(sn, pwd);

		String content = "您的朋友:" + phone + ",邀请您参与" + PublicConfig.SNEND
				+ ",链接地址:" + url;

		content = URLEncoder.encode(content, "utf8");

		String result_mt = client.mdsmssend(userPhone, content, "", "", "", "");
		System.out.print(result_mt);
	}
}
