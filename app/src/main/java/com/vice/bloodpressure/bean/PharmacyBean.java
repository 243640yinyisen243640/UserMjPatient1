package com.vice.bloodpressure.bean;

import java.util.List;

public class PharmacyBean {


    /**
     * id : 15
     * uid : 369
     * times : 3
     * drugname : 阿莫西林胶囊
     * dosage : 0.5mg
     * datetime : 2019-02-25 09:34
     * remark : [null]
     */

    private int id;
    private int uid;
    private String times;
    private String drugname;
    private String dosage;
    private String datetime;
    private List<Object> remark;

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

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<Object> getRemark() {
        return remark;
    }

    public void setRemark(List<Object> remark) {
        this.remark = remark;
    }
}

