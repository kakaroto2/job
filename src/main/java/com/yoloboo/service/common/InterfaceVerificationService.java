package com.yoloboo.service.common;

import com.yoloboo.excptions.VerificationException;


/**
 * Created by ZHOU005 on 2016/1/19.
 */
public interface InterfaceVerificationService
{
	void verify(String param, String encrypted) throws VerificationException;
}
