package com.mmall.common;


/**
 * Author:huangjb
 * Date:2018/11/4
 * Description:
 */

public enum AppConstants {
    HTTP_PRODOCOL("http","传输协议"),
    RESHOST("47.105.171.105","fastdfs安装IP"),
    PORT("","对应端口")
    ;

    private String arg;
    private String message;

    AppConstants(String arg, String message) {
        this.arg = arg;
        this.message = message;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
