package com.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;


public class HttpGetMsg {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
//		String data=HttpGetMsg.postUrl("http://127.0.0.1:8091/crazygoInterface/user/findOrderUpmpTn.action",
//				"userId=2&orderId=123&totalMoney=1&saleFlowNumber=20140901000003");
		
		//String data=HttpGetMsg.postUrl("http://127.0.0.1:8091/crazygoInterface/user/findOrderWeiXinPackage.action",
		//		"totalMoney=1&saleFlowNumber=2014092300001&body=测试");
	//	System.out.println(data);
		
		
	}

	
	
	
	public static boolean isNetOk(String ip) {
		boolean state = false;
		try {
			InetAddress ad = InetAddress.getByName(ip);

			try {
				state = ad.isReachable(5000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}// 测试是否可以达到该地址

		} catch (UnknownHostException e) {

		}
		return state;
	}

	public static String postUrl(String remoteUrl, String param) {
		URL url = null;
		HttpURLConnection httpurlconnection = null;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(remoteUrl);

			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.getOutputStream().write(param.getBytes("UTF-8"));
			httpurlconnection.getOutputStream().close();
			InputStream in = httpurlconnection.getInputStream();
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) != -1) {
				sb.append(new String(b, 0, i, "UTF-8"));
			}

		} catch (Exception e) {LogException.printException(e);
			e.printStackTrace();
			sb.append("");

		} finally {
			if (httpurlconnection != null)
				httpurlconnection.disconnect();

		}
		return sb.toString();
	}

}
