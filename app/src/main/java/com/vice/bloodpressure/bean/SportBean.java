package com.vice.bloodpressure.bean;

import java.util.List;

public class SportBean {

    /**
     * id : 17
     * uid : 369
     * type : 自然走
     * duration : 11
     * imgsrc : null
     * datetime : 2019-02-21 00:00
     * remark : [null]
     */

    private int id;
    private int uid;
    private String type;
    private int duration;
    private Object imgsrc;
    private String datetime;
    private List<Object> remark;

    public List<Object> getRemark() {
        return remark;
    }

    public void setRemark(List<Object> remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Object getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(Object imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
