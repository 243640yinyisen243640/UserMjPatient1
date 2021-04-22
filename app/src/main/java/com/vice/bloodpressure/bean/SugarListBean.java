package com.vice.bloodpressure.bean;

public class SugarListBean {

    /**
     * glucosevalue : 10.6
     * datetime : 11:52
     * type : 手动上传
     */

    private double glucosevalue;
    private String datetime;
    private String type;
    private int id;

    public double getGlucosevalue() {
        return glucosevalue;
    }

    public void setGlucosevalue(double glucosevalue) {
        this.glucosevalue = glucosevalue;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
