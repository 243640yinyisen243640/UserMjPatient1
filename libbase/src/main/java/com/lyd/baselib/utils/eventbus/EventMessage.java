package com.lyd.baselib.utils.eventbus;

/**
 * 描述: 事件(Event)基类
 * 作者: LYD
 * 创建日期: 2018/6/12 17:10
 */
public class EventMessage<T> {
    //消息类型,不同事件类型指定不同的code
    private int code;
    //传递的单个数据
    private String msg;
    //传递的多个数据
    private T data;

    /**
     * 不传递数据,仅仅用于刷新
     *
     * @param code
     */
    public EventMessage(int code) {
        this.code = code;
    }

    /**
     * 传递单个数据
     *
     * @param code
     * @param msg
     */
    public EventMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 传递多个数据
     *
     * @param code
     * @param data
     */
    public EventMessage(int code, T data) {
        this.code = code;
        this.data = data;
    }



    public EventMessage(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
