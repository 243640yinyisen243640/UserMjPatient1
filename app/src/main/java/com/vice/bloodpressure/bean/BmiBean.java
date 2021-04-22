package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/5/28.
 * 获取BMI数据实体类
 */

public class BmiBean {

    /**
     * id : 15
     * uid : 110
     * height : 156
     * weight : 156
     * bmi : 64.1
     * datetime : 2019-02-28 02:25
     * remark : null
     */

    private int id;
    private int uid;
    private String height;
    private String weight;
    private Object remark;
    //首页Bmi只返回了以下两个开始
    private double bmi;
    private String datetime;
    private String indextime;
    //首页Bmi只返回了以下两个结束

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
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


    public String getIndextime() {
        return indextime;
    }

    public void setIndextime(String indextime) {
        this.indextime = indextime;
    }
}
