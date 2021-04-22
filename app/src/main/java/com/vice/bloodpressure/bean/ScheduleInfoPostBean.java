package com.vice.bloodpressure.bean;

import java.io.Serializable;

public class ScheduleInfoPostBean implements Serializable {
    private String sid;
    private String schday;
    private String type;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSchday() {
        return schday;
    }

    public void setSchday(String schday) {
        this.schday = schday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
