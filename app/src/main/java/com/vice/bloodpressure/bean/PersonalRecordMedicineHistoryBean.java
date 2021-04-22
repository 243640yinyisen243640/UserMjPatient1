package com.vice.bloodpressure.bean;

import java.io.Serializable;

public class PersonalRecordMedicineHistoryBean implements Serializable {

    /**
     * id : 7711
     * times : 1
     * drugname : 测试药品
     * dosage : 1g
     * starttime :
     * endtime :
     */

    private int id;
    private String times;
    private String drugname;
    private String dosage;
    private String starttime;
    private String endtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
