package com.vice.bloodpressure.bean;

public class MyAppointmentListBean {
    /**
     * hospitalname : 首都医科大学附属北京世纪坛医院
     * address : 汶河路3666号
     * depname : 糖尿病中心
     * schday : 2019-10-25 周四
     * schtime : 9:00~11:00
     * username : null
     * schid : 25
     * schnum : 007
     * addtime : 2019-10-23 09:47:11
     * week : 4
     * status : 1
     */

    private String hospitalname;
    private String address;
    private String depname;
    private String schday;
    private String schtime;
    private String username;
    private int schid;
    private String schnum;
    private String addtime;
    private int week;
    private int status;
    private String docname;


    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
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

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
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

    public int getSchid() {
        return schid;
    }

    public void setSchid(int schid) {
        this.schid = schid;
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
