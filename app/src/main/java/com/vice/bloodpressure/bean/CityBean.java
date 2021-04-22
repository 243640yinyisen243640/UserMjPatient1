package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/6/5.
 * <p>
 * 城市
 */

public class CityBean {

    /**
     * citycode : 110101
     * cityname : 东城区
     */
    private String cityname;
    private String citycode;

    public CityBean() {
    }

    public CityBean(String cityname, String citycode) {
        this.cityname = cityname;
        this.citycode = citycode;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
