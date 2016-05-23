package com.yoloboo.excptions;

import com.common.constans.SystemCodeContent;

/**
 * Created by Remy4Pro on 2016/3/7.
 */
public class LoginFailureException extends BusinessException {
    public LoginFailureException(String msg){
        super(SystemCodeContent.FORBIDDEN_CODE,msg);
    }
    public LoginFailureException(){
        super(SystemCodeContent.FORBIDDEN_CODE);
    }
}
