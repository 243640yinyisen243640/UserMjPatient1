package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/5/28.
 * 获取血糖数据的实体类
 */

public class BloodSugarBean {

    /**
     * glucosevalue : 30
     * category : 空腹
     * datetime : 2018-11-13 19:48
     * type : 1
     */

    private double glucosevalue;
    private String category;
    private String datetime;
    private int type;
    private String indextime;

    public double getGlucosevalue() {
        return glucosevalue;
    }

    public void setGlucosevalue(double glucosevalue) {
        this.glucosevalue = glucosevalue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIndextime() {
        return indextime;
    }

    public void setIndextime(String indextime) {
        this.indextime = indextime;
    }
}
