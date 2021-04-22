package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/5/18.
 */

public class MessageEventBean {

    private String message;

    private String type;

    public MessageEventBean(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
