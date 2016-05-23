package com.yoloboo.excptions;

import com.common.constans.SystemCodeContent;

/**
 * Created by CoderZhao on 2016/1/14.
 */
public class ResultNotFoundException extends BusinessException {
    public ResultNotFoundException (String msg){
        super(SystemCodeContent.NOT_FOUND_ERROR_CODE,msg);
    }
    public ResultNotFoundException(){
        super(SystemCodeContent.NOT_FOUND_ERROR_CODE);
    }
}
