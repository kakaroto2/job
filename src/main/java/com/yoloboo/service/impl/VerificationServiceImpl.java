package com.yoloboo.service.impl;

import com.common.Commonparam;
import com.common.Sendmessage;
import com.yoloboo.excptions.ResultNotFoundException;
import com.yoloboo.dao.UserManager;
import com.yoloboo.service.VerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by CoderZhao on 2016/1/12.
 */
@Service
@Transactional
public class VerificationServiceImpl implements VerificationService {
    @Resource
    private UserManager userManager;
    @Override
    public String obtainVerificationCode(String phone, String code) {
        HashMap param = new HashMap();
        param.put("phone", phone);// 不带区号
        param.put("createTime", Commonparam.Date2Str());
        String msgCode = "";
        final int mobile_code = (int)((Math.random()*9+1)*1000);
        msgCode=mobile_code+"";
        //发送验证码线程
        Sendmessage.sendVerificationCode(phone,code,mobile_code);
        param.put("msgCode", msgCode);// 验证码
        userManager.obtainVerificationCode(param);// 需要保存到数据
        return msgCode;
    }

    @Override
    public Integer checkCode(String type, String phone, String code) throws ResultNotFoundException{
        HashMap param = new HashMap();
        int count;
        int num;
        int size;
        int total;
        if (type == "1" || type.equals("1")) {
            // 验证码
            param.put("uPhone", phone);
            param.put("code", code);
            count = userManager.checkVerificationCode(param);
        } else {
            param.put("code", code);
//            num = userManager.checkInvitationCode(param);
//            size=userManager.checkInvitationCodeByEmail(param);
//            total=userManager.checkInvitationCodeByBatch(param);
//            if(num>0||size>0||total>0){
//                //还要去查询用户表中是否已经有这个邀请码注册过  有的话  表示不能再注册  没有的话 表示是一次性的 可以注册
//                if(0==userManager.findUseageCount(param)){
//                    count=1;
//                }else{
//                    count=-1;
//                }
//            }else{
//                count=0;
//            }
            //如果是5位的话
            if(5==code.length()){
                num = userManager.checkInvitationCode(param);
                if(num>0){
                    count=1;
                }else{
                    count=0;
                }
            }else{
                size=userManager.checkInvitationCodeByEmail(param);
                total=userManager.checkInvitationCodeByBatch(param);
                if(size>0||total>0){
                    if(0==userManager.findUseageCount(param)){
                        count=1;
                    }else{
                        count=-1;
                    }
                }else{
                    count=0;
                }
            }
        }
        if (count>0){
            return null;
        }else if(count==0){
            throw  new ResultNotFoundException();
        }else{
            return 1;
        }
    }
    @Override
    public Integer obtainPhoneCount(String phone) {
      return  userManager.obtainPhoneCount(phone);
    }

}
