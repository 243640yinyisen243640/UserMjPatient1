package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * Created by qw on 2018/08/05.
 */

public class BloodSugarTempBean implements Serializable {

    /**
     * 区分是什么时段
     */
    private int type;
    /**
     * 血糖值
     */
    private double glucosevalue;
    /**
     * 测量时间
     */
    private String datetime;

    public BloodSugarTempBean() {
    }

    public BloodSugarTempBean(int type, double glucosevalue, String datetime) {
        this.type = type;
        this.glucosevalue = glucosevalue;
        this.datetime = datetime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "BloodSugarTempBean{" +
                "type=" + type +
                ", glucosevalue=" + glucosevalue +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
