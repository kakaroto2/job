package com.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.omg.CORBA.NameValuePairHelper;

import com.github.kevinsawicki.http.HttpRequest;

public class HttpXmlUtil {
	// ############################此部分参数需要修改

	public static String userName = "dh20924"; // 接口账号
	public static String password = "fy123.com"; // 密码
	// public static String phone =
	// "15618221516,18917118898,18917766661,18621211212,18917766662"; //
	// 要发送的手机号码

	// public static String content = "欢迎您使用漕河泾e服务平台"; // 短信内容
	public static String sign = ""; // 短信签名，该签名必须提前报备【漕河泾开发区】
	public static String sign2 = "【淳汉】"; 
	public static String msgid = ""; // 自定义msgid
	public static String subcode = ""; // 扩展字号
	public static String sendtime = ""; // 定时发送时间，时间格式201305051230
	public static String url = "http://3tong.net"; // 无限通使用地址

	// public static String url="http://www.10690300.com"; //三网web通使用地址
	public static void main(String[] args) {
		// 发送短信
		System.out.println("*************发送短信*************");
		List<String> list = new ArrayList<String>();
	
		list.add("15618045376");

		for (String phone : list) {
			send(phone,
					"您的验证码22222。");

		}
		
//	 String ddd= "<msgid></msgid><result>19</result><desc>用户被禁发</desc>";
//	 String result = ddd.substring(ddd.indexOf("<result>")+8,ddd.indexOf("</result>"));
//	 String desc = ddd.substring(ddd.indexOf("<desc>")+6,ddd.indexOf("</desc>"));
//	 System.out.println(result);
//	 System.out.println(desc);
	 
	 
	 
	}

	// MD5 加密函数
	public static String MD5Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static final String byte2hexString(byte[] bytes) {
		StringBuffer bf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 0x10) {
				bf.append("0");
			}
			bf.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return bf.toString();
	}

	// 发送短信
	/**
	 * 发送短信方法使用document 对象方法封装XML 字符串
	 */
	public static String send(String phone, String content) {
		String posturl = url + "/http/sms/Submit";
		Map<String, String> params = new LinkedHashMap<String, String>();
		HttpXmlUtil docXml = new HttpXmlUtil();

		String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
				+ "<account>" + userName + "</account>" + "<password>"
				+ MD5Encode(password) + "</password>"
				+ "<msgid></msgid><phones>" + phone + "</phones><content>"
				+ content + "</content>" + "<sign>" + sign
				+ "</sign><subcode></subcode><sendtime></sendtime></message>";

		// String message = docXml.DocXml(userName, MD5Encode(password), msgid,
		// phone, content, sign, subcode, sendtime);
		System.out.println(message);
		params.put("message", message);
		String resp = doPost(posturl, params);
		System.out.println(resp);
		return resp;
	}
	
	/**
	 * 发送短信方法使用document 对象方法封装XML 字符串
	 */
	public static String sendSign(String phone, String content) {
		String posturl = url + "/http/sms/Submit";
		Map<String, String> params = new LinkedHashMap<String, String>();
		HttpXmlUtil docXml = new HttpXmlUtil();

		String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
				+ "<account>" + userName + "</account>" + "<password>"
				+ MD5Encode(password) + "</password>"
				+ "<msgid></msgid><phones>" + phone + "</phones><content>"
				+ content + "</content>" + "<sign>" + sign2
				+ "</sign><subcode></subcode><sendtime></sendtime></message>";

		// String message = docXml.DocXml(userName, MD5Encode(password), msgid,
		// phone, content, sign, subcode, sendtime);
		System.out.println(message);
		params.put("message", message);
		String resp = doPost(posturl, params);
		System.out.println(resp);
		return resp;
	}

	// 状态报告
	private static void getReport() {
		String posturl = url + "/http/sms/Report";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message>"
				+ "<account>" + userName + "</account>" + "<password>"
				+ MD5Encode(password) + "</password>"
				+ "<msgid></msgid><phone></phone></message>";
		params.put("message", message);
		String resp = doPost(posturl, params);
		System.out.println(resp);
	}

	// 查询余额
	private static void getBalance() {
		String posturl = url + "/http/sms/Balance";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
				+ userName
				+ "</account>"
				+ "<password>"
				+ MD5Encode(password)
				+ "</password></message>";
		params.put("message", message);
		System.out.println(message);
		String resp = doPost(posturl, params);
		System.out.println(resp);
	}

	// 获取上行
	private static void getSms() {
		String posturl = url + "/http/sms/Deliver";
		Map<String, String> params = new LinkedHashMap<String, String>();
		String message = "<?xml version='1.0' encoding='UTF-8'?><message><account>"
				+ userName
				+ "</account>"
				+ "<password>"
				+ MD5Encode(password)
				+ "</password></message>";
		params.put("message", message);
		String resp = doPost(posturl, params);
		System.out.println(resp);
	}

	/**
	 * 执行一个HTTP POST 请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL 地址
	 * @param params
	 *            请求的查询参数,可以为null
	 * @return 返回请求响应的HTML
	 */
	private static String doPost(String url, Map<String, String> params) {
		return HttpRequest.post(url).form(params).body();
	}

	/**
	 * 使用document 对象封装XML
	 * 
	 * @param userName
	 * @param pwd
	 * @param id
	 * @param phone
	 * @param contents
	 * @param sign
	 * @param subcode
	 * @param sendtime
	 * @return
	 */
	public String DocXml(String userName, String pwd, String msgid,
			String phone, String contents, String sign, String subcode,
			String sendtime) {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element message = doc.addElement("message");
		Element account = message.addElement("account");
		account.setText(userName);
		Element password = message.addElement("password");
		password.setText(pwd);
		Element msgid1 = message.addElement("msgid");
		msgid1.setText(msgid);
		Element phones = message.addElement("phones");
		phones.setText(phone);
		Element content = message.addElement("content");
		content.setText(contents);
		Element sign1 = message.addElement("sign");
		sign1.setText(sign);
		Element subcode1 = message.addElement("subcode");
		subcode1.setText(subcode);
		Element sendtime1 = message.addElement("sendtime");
		sendtime1.setText(sendtime);
		return message.asXML();

	}
}