package com.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class StringUtil
{
	public static String str;
	public static final String EMPTY_STRING = "";

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static String byteToHexString(byte b)
	{
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}


	public static String byteArrayToHexString(byte[] b)
	{
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
		{
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String MD5Encode(String origin)
	{
		String resultString = null;
		try
		{
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		}
		catch (Exception ex)
		{
		}
		return resultString;
	}


	public static String translate(String origin)
	{
		String rs = null;
		if (origin.contains("#"))
		{
			StringBuffer sb = new StringBuffer();
			String[] array = origin.split(" ");
			for (int i = 0; i < array.length; i++)
			{
				if (array[i].contains("#"))
				{
					array[i] = array[i].replace("#", "[").concat("]");
				}
				sb.append(array[i] + " ");
			}
			try
			{
				rs = new String(sb.toString().getBytes("UTF-8"), "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			rs = origin;
		}
		return rs;
	}

	/*
 * 方法名称：getMD5
 * 功    能：字符串MD5加密
 * 参    数：待转换字符串
 * 返 回 值：加密之后字符串
 */
	public static String getMD5(String sourceStr)
	{
		String resultStr = "";
		try
		{
			byte[] temp = sourceStr.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(temp);
			// resultStr = new String(md5.digest());
			byte[] b = md5.digest();
			for (int i = 0; i < b.length; i++)
			{
				char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
				char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F];
				ob[1] = digit[b[i] & 0X0F];
				resultStr += new String(ob);
			}
			return resultStr;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}


}
