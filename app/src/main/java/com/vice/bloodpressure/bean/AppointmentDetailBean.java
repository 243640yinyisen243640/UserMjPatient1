package com.vice.bloodpressure.bean;

public class AppointmentDetailBean {
    /**
     * hospitalname : 首都医科大学附属北京世纪坛医院
     * depname : 糖尿病中心
     * docname : 盛医生
     * doczhc : 专业医生
     * schday : 2019-10-24 周四
     * schtime : 9:00~11:00
     * username : 测试
     * idcard : 410401********1055
     * schnum : 008
     * addtime : 2019-10-23 09:48:10
     * week : 4
     * status : 1
     */

    private String hospitalname;
    private String depname;
    private String docname;
    private String doczhc;
    private String schday;
    private String schtime;
    private String username;
    private String idcard;
    private String schnum;
    private String addtime;
    private int week;
    private int status;
    private String schuser;

    public String getSchuser() {
        return schuser;
    }

    public void setSchuser(String schuser) {
        this.schuser = schuser;
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

    public String getSchday() {
        return schday;
    }

    public void setSchday(String schday) {
        this.schday = schday;
    }

    public String getSchtime() {
        return schtime;
    }

    public void setSchtime(String schtime) {
        this.schtime = schtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSchnum() {
        return schnum;
    }

    public void setSchnum(String schnum) {
        this.schnum = schnum;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
