package com.vice.bloodpressure.bean;


import androidx.annotation.NonNull;

public class AppointmentDoctorListBean {
    /**
     * sid : 7
     * docuserid : 105
     * am : 2
     * amnum : 5
     * aminterview : 5
     * pm : 2
     * pmnum : 5
     * pminterview : 5
     * imgurl : http://xydoc.xiyuns.cn/Public/upload/20190219/201902191648521550566132.jpg
     * docname : 盛医生
     * doczhc : 专业医生
     * contents : 专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病专攻糖尿病
     * isschedule : 2
     */

    private int sid;
    private int docuserid;
    private int am;
    private int amnum;
    private int aminterview;
    private int pm;
    private int pmnum;
    private int pminterview;
    private String imgurl;
    private String docname;
    private String doczhc;
    private String contents;
    private int isschedule;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getDocuserid() {
        return docuserid;
    }

    public void setDocuserid(int docuserid) {
        this.docuserid = docuserid;
    }

    public int getAm() {
        return am;
    }

    public void setAm(int am) {
        this.am = am;
    }

    public int getAmnum() {
        return amnum;
    }

    public void setAmnum(int amnum) {
        this.amnum = amnum;
    }

    public int getAminterview() {
        return aminterview;
    }

    public void setAminterview(int aminterview) {
        this.aminterview = aminterview;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public int getPmnum() {
        return pmnum;
    }

    public void setPmnum(int pmnum) {
        this.pmnum = pmnum;
    }

    public int getPminterview() {
        return pminterview;
    }

    public void setPminterview(int pminterview) {
        this.pminterview = pminterview;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDoczhc() {
        return doczhc;
    }

    public void setDoczhc(String doczhc) {
        this.doczhc = doczhc;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getIsschedule() {
        return isschedule;
    }

    public void setIsschedule(int isschedule) {
        this.isschedule = isschedule;
    }

    @NonNull
    @Override
    public String toString() {
        return "AppointmentDoctorListBean{" +
                "sid=" + sid +
                ", docuserid=" + docuserid +
                ", am=" + am +
                ", amnum=" + amnum +
                ", aminterview=" + aminterview +
                ", pm=" + pm +
                ", pmnum=" + pmnum +
                ", pminterview=" + pminterview +
                ", imgurl='" + imgurl + '\'' +
                ", docname='" + docname + '\'' +
                ", doczhc='" + doczhc + '\'' +
                ", contents='" + contents + '\'' +
                ", isschedule=" + isschedule +
                '}';
    }
}
