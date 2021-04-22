package com.vice.bloodpressure.bean;
/*
 * 类名:     InHospitalEntity
 * 描述:     家签住院申请列表
 * 作者:     ZWK
 * 创建日期: 2020/1/8 17:30
 */

import androidx.annotation.NonNull;

public class InHospitalEntity {

    /**
     * id : 2
     * status : 1
     * time : 2020-01-08 17:20
     */

    private int id;
    private String status;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @NonNull
    @Override
    public String toString() {
        return "InHospitalEntity{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
