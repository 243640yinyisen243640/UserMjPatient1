package com.vice.bloodpressure.bean;

public class ElectrolyteListBean {
    /**
     * potassium : 1
     * sodium : 2
     * calcium : 3
     * urobiliary : 4
     * nca : 5
     * ph : 6
     */

    private String potassium;
    private String sodium;
    private String calcium;
    private String urobiliary;
    private String nca;
    private String ph;

    public String getPotassium() {
        return potassium;
    }

    public void setPotassium(String potassium) {
        this.potassium = potassium;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public String getCalcium() {
        return calcium;
    }

    public void setCalcium(String calcium) {
        this.calcium = calcium;
    }

    public String getUrobiliary() {
        return urobiliary;
    }

    public void setUrobiliary(String urobiliary) {
        this.urobiliary = urobiliary;
    }

    public String getNca() {
        return nca;
    }

    public void setNca(String nca) {
        this.nca = nca;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }
}
