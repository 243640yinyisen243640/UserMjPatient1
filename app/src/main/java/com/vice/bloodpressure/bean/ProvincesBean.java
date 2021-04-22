package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/6/5.
 * <p>
 * 省份
 */

public class ProvincesBean {

    /**
     * id : 1
     * cityname : 北京市
     * citycode : 110000
     */

    private int id;
    private String cityname;
    private String citycode;

    public ProvincesBean() {
    }

    public ProvincesBean(int id, String cityname, String citycode) {
        this.id = id;
        this.cityname = cityname;
        this.citycode = citycode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }
}
