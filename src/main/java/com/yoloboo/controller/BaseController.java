package com.yoloboo.controller;

import com.common.Json;
import com.common.StringUtil;
import com.common.constans.SystemCodeContent;
import com.common.constans.SystemCodeContent;
import com.json.BaseBean;
import com.yoloboo.service.common.InterfaceVerificationService;
import com.yoloboo.service.common.MessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;

import java.util.Locale;


/**
 * Created by ZHOU005 on 2015/12/18.
 */

@Controller
public class BaseController
{
	@Autowired
	private MessageService messageService;

	@Autowired
	private InterfaceVerificationService interfaceVerificationService;

	protected String getMessage(String code, Integer language)
	{
		return getMessage(code, null, language);
	}

	protected String getMessage(String code, Object[] args, Integer language)
	{
		return getMessage(code, null, "", language);
	}

	protected String getMessage(String code, Object[] args, String defaultMessage, Integer language)
	{
		return messageService.getMessage(code, args, defaultMessage, language);
	}

	protected String jsonError(String msg)
	{
		return jsonError(msg, SystemCodeContent.COMMON_ERROR_CODE);
	}

	protected String jsonError(String msg, int errorCode)
	{
		BaseBean bean = new BaseBean();

		bean.setMsg(msg);
		bean.setStatus(errorCode);

		return Json.toString(bean);
	}

	protected String json(Object obj)
	{
		BaseBean bean = new BaseBean();

		bean.setRows(obj);
		bean.setStatus(SystemCodeContent.SUCCESS_CODE);

		return json(bean);
	}

	protected String json(BaseBean bean)
	{
		return Json.toString(bean);
	}

	protected InterfaceVerificationService getInterfaceVerify()
	{
		return this.interfaceVerificationService;
	}

}

