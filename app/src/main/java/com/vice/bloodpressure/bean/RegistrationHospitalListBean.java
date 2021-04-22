package com.vice.bloodpressure.bean;

import java.io.Serializable;

public class RegistrationHospitalListBean implements Serializable {
    /**
     * id : 2
     * hospitalname : 首都医科大学附属北京世纪坛医院
     */

    private int id;
    private String hospitalname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }
}
