package com.common;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by CoderZhao on 2015/11/23.
 */
public class TimeUtils
{
	public static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sf = new SimpleDateFormat("MM-dd HH:mm");

	public static long judgeTime(String s1, String s2) throws ParseException
	{
		Date d1 = fmt.parse(s1);
		Date d2 = fmt.parse(s2);
		return d1.getTime() - d2.getTime();
	}

	public static String transTime(String s) throws ParseException
	{
		Date d = fmt.parse(s, new ParsePosition(0));
		return sf.format(d);
	}


	public static void main(String[] args) throws ParseException
	{
		System.out.println(TimeUtils.transTime("2015-11-23 17:30:40"));
	}
}
