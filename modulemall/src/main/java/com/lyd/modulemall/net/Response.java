package com.lyd.modulemall.net;


/**
 * 描述: 返回数据基类
 * 作者: LYD
 * 创建日期: 2019/12/9 14:43
 */
public class Response<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
