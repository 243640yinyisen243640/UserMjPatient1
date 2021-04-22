package com.vice.bloodpressure.bean;

public class ImmunoFluorescenceReportAndClottingReportBean {
    /**
     * icid : 1
     * addtime : 2019-07-20 04:50:23
     */
    //免疫荧光
    private int icid;
    //凝血
    private int bdid;
    private String addtime;

    public int getIcid() {
        return icid;
    }

    public void setIcid(int icid) {
        this.icid = icid;
    }

    public int getBdid() {
        return bdid;
    }

    public void setBdid(int bdid) {
        this.bdid = bdid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
