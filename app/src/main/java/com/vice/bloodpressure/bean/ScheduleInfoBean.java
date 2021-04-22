package com.vice.bloodpressure.bean;

import java.util.List;

public class ScheduleInfoBean {
    /**
     * sid : 1
     * docuserid : 105
     * hospitalname : 首都医科大学附属北京世纪坛医院
     * depname : 糖尿病中心
     * docname : 盛医生
     * doczhc : 专业医生
     * schweek : 7
     * schday : 20191024
     * id : 13
     * username : 测试
     * type : 1
     * strschday : 2019-10-24 周日
     */

    private int sid;
    private int docuserid;
    private String hospitalname;
    private String depname;
    private String docname;
    private String doczhc;
    private int schweek;
    private String schday;
    private int id;
    private String username;
    private String type;
    private String strschday;
    //可预约时间段
    private List<String> stimestr;

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

    public int getSchweek() {
        return schweek;
    }

    public void setSchweek(int schweek) {
        this.schweek = schweek;
    }

    public String getSchday() {
        return schday;
    }

    public void setSchday(String schday) {
        this.schday = schday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStrschday() {
        return strschday;
    }

    public void setStrschday(String strschday) {
        this.strschday = strschday;
    }

    public List<String> getStimestr() {
        return stimestr;
    }

    public void setStimestr(List<String> stimestr) {
        this.stimestr = stimestr;
    }
}
