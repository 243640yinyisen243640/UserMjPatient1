package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * Created by yicheng on 2018/6/5.
 * <p>
 * 医院详情类
 */

public class HospitalDetailsBean implements Serializable {
    /**
     * 医院id
     */
    private String id;
    /**
     * 医院名称
     */
    private String hospitalname;
    /**
     * 省份id
     */
    private String pronid;
    /**
     * 城市id
     */
    private String areaid;
    /**
     * 医院等级
     */
    private String level;
    /**
     * 医院地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 医院图片
     */
    private String imgurl;
    /**
     * 医院简介
     */
    private String contents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getPronid() {
        return pronid;
    }

    public void setPronid(String pronid) {
        this.pronid = pronid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
