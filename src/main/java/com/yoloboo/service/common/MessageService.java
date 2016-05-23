package com.yoloboo.service.common;

/**
 * Created by ZHOU005 on 2015/12/22.
 */
public interface MessageService
{
	String getMessage(String code, Integer language);

	String getMessage(String code, Object[] args, Integer language);

	String getMessage(String code, Object[] args, String defaultMessage, Integer language);
}

