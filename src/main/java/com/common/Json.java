package com.common;

import java.text.DateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * json 处理
 * 
 * @author Zhu.Zhengguang
 * @date 2014年12月19日 上午10:29:38
 * @version V1.0
 */
public class Json {
	/**
	 * 对象转换为json(特殊符合不转码)
	 * 
	 * @param entity
	 * @return
	 */
	public static String toString(Object entity) {
		/*Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat(format).create();// new
																		// Gson();
																		// 后者会把特殊符号自动转换为Unicode转义字符
		String string = gson.toJson(entity);*/
		String jsonString =JSON.toJSONStringWithDateFormat(entity, "yyyy-MM-dd HH:mm:ss");

		String oldBaseUrl []={
				"http://app.yoloboo.com/yolobooManager/upload/",
				"http:/app.yoloboo.com/yolobooManager/upload/",
				"http://app.yoloboo.com/yolobooManager//upload/",
				"http://images.yoloboo.com/upload/"
		};
		for (int i = 0; i < oldBaseUrl.length; i++) {
			if(jsonString.contains(oldBaseUrl[i])){
				jsonString=jsonString.replaceAll(oldBaseUrl[i], QiNiuUtils.getBaseUrl()+"/upload/");
			}
		}
		
		
		return jsonString;
	}

	/**
	 * json 转换为对象
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	private static String format = "yyyy-MM-dd hh:mm:ss";
	public static <T> T fromJson(String json, Class<T> classOfT) {
		
		return JSON.parseObject(json, classOfT);
	}
	
	public static void main(String[] args) {
		System.out.println("-----------");
		TestBean testBean=new TestBean();
		
		System.err.println(Json.toString(testBean));
		
	}
	public static class TestBean{
		int a=1;
		Date date=new Date();
	}

}
