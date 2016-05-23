package com.yoloboo.service;

import com.yoloboo.excptions.ResultNotFoundException;


/**
 * Created by CoderZhao on 2016/1/12.
 */
public interface VerificationService {
    String obtainVerificationCode(String phone,String code);

    Integer checkCode(String type, String phone, String code) throws ResultNotFoundException;

    Integer obtainPhoneCount(String phone);//判断手机号码是唯一
}
