package com.yoloboo;


import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by huhaosumail on 16/8/9.
 */
public class TokenUtils {


    public  String getAccess_token(String appId, String appSecret) {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="

                + appId+ "&secret=" + appSecret;

        String accessToken = null;

        try {

            URL urlGet = new URL(url);

            HttpURLConnection http = (HttpURLConnection) urlGet

                    .openConnection();

            http.setRequestMethod("GET"); // 必须是get方式请求

            http.setRequestProperty("Content-Type",

                    "application/x-www-form-urlencoded");

            http.setDoOutput(true);

            http.setDoInput(true);

            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒

            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

            http.connect();

            InputStream is = http.getInputStream();

            int size = is.available();

            byte[] jsonBytes = new byte[size];

            is.read(jsonBytes);

            String message = new String(jsonBytes, "UTF-8");

            JSONObject demoJson = new JSONObject(message);

            accessToken = demoJson.getString("access_token");

            System.out.println(accessToken);

            is.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return accessToken;

    }
}
