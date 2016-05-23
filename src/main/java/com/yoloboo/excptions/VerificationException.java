package com.yoloboo.excptions;

import com.common.constans.SystemCodeContent;


/**
 * Created by ZHOU005 on 2016/1/19.
 */
public class VerificationException extends BusinessException
{
	public VerificationException()
	{
		super(SystemCodeContent.FORBIDDEN_CODE);
	}

	public VerificationException(String message)
	{
		super(SystemCodeContent.FORBIDDEN_CODE, message);
	}

	public VerificationException(int code)
	{
		super(code);
	}

}
