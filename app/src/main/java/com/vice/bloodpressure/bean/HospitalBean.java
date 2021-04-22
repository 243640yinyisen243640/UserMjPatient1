package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/6/5.
 * <p>
 * 医院列表实体类
 */

public class HospitalBean {

    /**
     * id : 2
     * hospitalname : 首都医科大学附属北京世纪坛医院
     * address : 汶河路3666号
     * imgurl : http://xydoc.xiyuns.cn/Public/upload/20190228/201902280019111551284351.jpg
     * level : 3
     */

    private int id;
    private String hospitalname;
    private String address;
    private String imgurl;
    private String level;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
