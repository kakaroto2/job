package com.yoloboo.excptions;

/**
 * Created by ZHOU005 on 2016/1/19.
 */
public class BusinessException extends Exception
{
	private int code;
	private String message;

	public BusinessException(int code)
	{
		this.code = code;
	}

	public BusinessException(int code, String message)
	{
		this.code = code;
		this.message = message;
	}

	public BusinessException(Throwable cause, int code)
	{
		super(cause);
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	public String getMessage()
	{
		return message;
	}

}
