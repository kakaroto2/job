package com.common;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


/**
 * Created by ZHOU005 on 2016/1/12.
 */
public class UserVerifcationHelper
{
	public static String generateUserToken(String userId, HttpServletRequest request)
	{
		String token = UUID.randomUUID().toString();

		putToken(userId, token, request);

		return token;
	}

	public static void putToken(String userId, String token, HttpServletRequest request)
	{
		request.getServletContext().setAttribute(userId, token);
	}

	public static boolean isAccredited(String userId, String token, HttpServletRequest request)
	{
		Object tokenObj = request.getServletContext().getAttribute(userId);

		if (null != tokenObj && tokenObj.toString().equals(token))
		{
			return true;
		}

		return false;
	}

}
