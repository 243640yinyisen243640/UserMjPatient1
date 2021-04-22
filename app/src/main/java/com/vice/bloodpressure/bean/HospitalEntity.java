package com.vice.bloodpressure.bean;
/*
 * 包名:     com.vice.bloodpressure.bean
 * 类名:     HospitalEntity
 * 描述:     家签医院
 * 作者:     ZWK
 * 创建日期: 2020/1/8 11:00
 */

public class HospitalEntity {


    /**
     * hospitalname : 熙云科技智慧医疗
     * id : 4
     * areaid : 410100
     * cityname : 郑州市
     */

    private String hospitalname;
    private int id;
    private int areaid;
    private String cityname;

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
