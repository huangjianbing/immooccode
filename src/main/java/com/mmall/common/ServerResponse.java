package com.mmall.common;

import com.mmall.pojo.User;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Author:huangjb
 * Date:2019/3/18
 * Description:
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证json序列化的时候value 为空的时候key也消失
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status) {

        this.status = status;
    }
    @JsonIgnore//不序列化
    public Boolean isSuccess(){
        return this.status==ResponseCode.SUCCESS.getCode();
    }
    public int getStatus(){
        return this.status;
    }
    public String getMsg(){
        return  this.msg;
    }
    public T getData(){
        return this.data;
    }
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createBySuccessMsg(String Msg){
        return  new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),Msg);
    }
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode());
    }
    public static <T> ServerResponse<T> createByErrorMsg(String Msg){
        return  new ServerResponse<T>(ResponseCode.ERROR.getCode(),Msg);
    }
    public static <T> ServerResponse<T> createByError(int code ,String msg,T data){
        return new ServerResponse<T>(code,msg,data);
    }

    public static <T>ServerResponse<T> createByErrorCodeMsg(int code, String Msg) {
        return  new ServerResponse<T>(code,Msg);
    }
}
