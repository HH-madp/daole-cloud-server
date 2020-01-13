package com.daole.cloud.vo;

import lombok.Data;

@Data
public class R <T>{
    private static final int CODE_SUCCESS = 200;

    private static final int CODE_FAIL = 400;

    private static final String MSG_SUCCESS="success";
    private static final String MSG_FAIL="failed";

    private int code;
    private T data;
    private String msg;

    public R(){

    }

    public R(int code){
        this.code = code;
    }

    public R(int code, T data){
        this.code = code;
        this.data = data;
    }

    public R(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public R(int code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data=data;
    }

    public static R success(){
        return new R(CODE_SUCCESS,MSG_SUCCESS);
    }

    public static R success(Object data){
        return new R(CODE_SUCCESS,MSG_SUCCESS, data);
    }

    public static R fail(String msg){
        return new R(CODE_FAIL, msg);
    }
}
