package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/6/4.
 * <p>
 * 预警信息
 */

public class WarningMessageBean {
    /**
     * 时间
     */
    private String time;
    /**
     * 信息
     */
    private String message;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
