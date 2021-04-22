package com.lyd.modulemall.bean;


/**
 * 描述:   省市县三级Bean
 * 作者: LYD
 * 创建日期: 2021/1/27 19:31
 */
public class AddressBean {
    /**
     * cityname : 北京
     * adcode : 110000
     */
    private String cityname;
    private String adcode;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }
}
