package com.yoloboo.excptions;

import com.common.constans.SystemCodeContent;

/**
 * Created by CoderZhao on 2016/1/18.
 */
public class ActionPermissionDelayException extends BusinessException{
    public ActionPermissionDelayException(String msg){
        super(SystemCodeContent.ACTION_PERMISSION_CODE,msg);
    }
    public ActionPermissionDelayException(){
        super(SystemCodeContent.ACTION_PERMISSION_CODE);
    }
}
