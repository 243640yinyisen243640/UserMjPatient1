package com.vice.bloodpressure.bean;

import java.io.Serializable;

public class SignDoctorInfoBean implements Serializable {
    /**
     * docname : 李医生
     * docid : 6745
     * doczhc : 主任医师
     * specialty : 糖尿病
     * contents : 李医生
     * imgurl : http://ceshi.xiyuns.cn/Public/upload/20200103/202001031158061578023886.png
     * address : 郑州市金水区红专路110号19层1804室
     * applause_rate :
     * median_rate :
     * negative_rate :
     * signing_rate :
     * hospitalname : 熙云科技智慧医疗
     * depname : 内科糖尿病
     */

    private String docname;
    private int docid;
    private String doczhc;
    private String specialty;
    private String contents;
    private String imgurl;
    private String address;
    private String applause_rate;
    private String median_rate;
    private String negative_rate;
    private String signing_rate;
    private String hospitalname;
    private String depname;

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public String getDoczhc() {
        return doczhc;
    }

    public void setDoczhc(String doczhc) {
        this.doczhc = doczhc;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplause_rate() {
        return applause_rate;
    }

    public void setApplause_rate(String applause_rate) {
        this.applause_rate = applause_rate;
    }

    public String getMedian_rate() {
        return median_rate;
    }

    public void setMedian_rate(String median_rate) {
        this.median_rate = median_rate;
    }

    public String getNegative_rate() {
        return negative_rate;
    }

    public void setNegative_rate(String negative_rate) {
        this.negative_rate = negative_rate;
    }

    public String getSigning_rate() {
        return signing_rate;
    }

    public void setSigning_rate(String signing_rate) {
        this.signing_rate = signing_rate;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }
}
