package com.common;

import com.common.constans.SystemContent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Created by ZHOU005 on 2016/1/7.
 */
public class Test
{
	public static void main(String[] arg)
	{
		//System.out.println(StringUtil.getMD5("2015-03-10" + SystemContent.YOLOBOO_KEY));

		BlockingQueue  queue =  new ArrayBlockingQueue(1024);


		Producer  producer=new Producer(queue);

		Consumer  consumer  =new Consumer(queue);

		new  Thread(producer).start();

		new Thread(consumer).start();

//		Thread.sleep(4000);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}






}
