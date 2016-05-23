package com.common;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CoderZhao on 2015/10/22.
 */
public class HttpRequest {
    public static void main(String[] args){
        String json=HttpRequest.sendGet("http://7xlamk.com2.z0.glb.qiniucdn.com/upload/yoloboo/2015/10/20/e7bfac5e-4407-475f-8849-682bacd6e94d.jpg?imageInfo");
        if (StringUtils.isNotBlank(json)) {
            HashMap map1 = Json.fromJson(json, HashMap.class);
          System.out.println(map1.get("width").toString());
            System.out.println(map1.get("height").toString());
        }
    }
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
