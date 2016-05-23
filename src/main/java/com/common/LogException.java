package com.common;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LogException {
	public static Logger logger = LoggerFactory.getLogger(LogException.class);
	
	public static void printException(Exception e){
		StringWriter sw = new StringWriter();
		  e.printStackTrace(new PrintWriter(sw));
		  logger.error(sw.toString());
	}
}
