package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/6/4.
 * <p>
 * 糖化血红蛋白实体类
 */

public class HemoglobinBean {


    /**
     * id : 21
     * uid : 110
     * diastaticvalue : 18.8
     * datetime : 2019-02-28 00:00
     * remark : null
     */

    private int id;
    private int uid;
    private String diastaticvalue;
    private String datetime;
    private Object remark;

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

    public String getDiastaticvalue() {
        return diastaticvalue;
    }

    public void setDiastaticvalue(String diastaticvalue) {
        this.diastaticvalue = diastaticvalue;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }
}
