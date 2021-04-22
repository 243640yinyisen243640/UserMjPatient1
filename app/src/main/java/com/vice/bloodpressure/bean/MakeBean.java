package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/7/9.
 * 住院信息实体类
 */

public class MakeBean {
    /**
     * 预约住院信息id
     */
    private String id;
    /**
     * 预约状态1：预约中 2：预约成功 3：预约失败
     */
    private String status;
    /**
     * 时间
     */
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
