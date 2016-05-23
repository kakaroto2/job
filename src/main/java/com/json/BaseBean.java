package com.json;

import com.common.constans.SystemCodeContent;


public class BaseBean
{
	private String msg;
	private Object rows;
	private Object total;
	private int status = SystemCodeContent.SUCCESS_CODE;

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getStatus()
	{
		return status;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getMsg()
	{
		return msg;
	}

	public Object getRows()
	{
		return rows;
	}

	public void setRows(Object rows)
	{
		this.rows = rows;
	}

	public Object getTotal()
	{
		return total;
	}

	public void setTotal(Object total)
	{
		this.total = total;
	}
}
