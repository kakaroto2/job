package com.yoloboo.service.common.impl;

import com.yoloboo.service.common.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;


/**
 * Created by ZHOU005 on 2015/12/22.
 */

@Service
public class MessageServiceImpl implements MessageService
{
	static int EN = 0;
	static int CN_ZH = 1;
	static int CN_TW = 2;

	@Autowired
	protected MessageSource yolobooMessageSource;

	@Override
	public String getMessage(String code, Integer language)
	{
		return this.getMessage(code, null, language);
	}

	@Override
	public String getMessage(String code, Object[] args, Integer language)
	{
		return getMessage(code, args, "", language);
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Integer language)
	{
		return yolobooMessageSource.getMessage(code, args, defaultMessage, getLocale(language));
	}

	private Locale getLocale(Integer language)
	{
		if (null == language || language == CN_ZH)
		{
			return Locale.SIMPLIFIED_CHINESE;
		}
		else if (language == EN)
		{
			return Locale.ENGLISH;
		}
		else if (language == CN_TW)
		{
			return Locale.TRADITIONAL_CHINESE;
		}

		return Locale.ENGLISH;
	}

}
