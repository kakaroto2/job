package com.yoloboo.service.common.impl;

import com.common.StringUtil;
import com.common.constans.SystemContent;
import com.yoloboo.excptions.VerificationException;
import com.yoloboo.service.common.InterfaceVerificationService;
import org.springframework.stereotype.Service;


/**
 * Created by ZHOU005 on 2016/1/19.
 */

@Service
public class InterfaceVerificationServiceImpl implements InterfaceVerificationService
{
	@Override
	public void verify(String param, String encrypted) throws VerificationException
	{
		if (!encrypted.equals(StringUtil.getMD5(param + SystemContent.YOLOBOO_KEY)))
		{
			throw new VerificationException();
		}
	}

}
