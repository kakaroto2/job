package com.yoloboo.excptions;

import com.common.constans.SystemCodeContent;


/**
 * Created by ZHOU005 on 2016/1/20.
 */
public class DuplicateException extends BusinessException
{
	public DuplicateException()
	{
		super(SystemCodeContent.DUPLICATE_CODE);
	}

	public DuplicateException(String message)
	{
		super(SystemCodeContent.DUPLICATE_CODE, message);
	}

	public DuplicateException(int code)
	{
		super(code);
	}
}
