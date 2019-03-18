package com.mmall.common;

/**
 * Author:huangjb
 * Date:2019/3/18
 * Description:
 */
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEEED_LOGIN)"),
    ILLEGA_ARGUMENT(2,"ILLEGA_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code ,String desc){
        this.code=code;
        this.desc=desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
