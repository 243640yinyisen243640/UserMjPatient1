package com.vice.bloodpressure.bean.highbloodpressuretest;

public class TestOfHbpAddBean {
    private String access_token;
    private String userid;
    private String id;
    private String prlid;
    private TestOfDietBean dietary;
    private TestOfSportBean sports;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrlid() {
        return prlid;
    }

    public void setPrlid(String prlid) {
        this.prlid = prlid;
    }

    public TestOfDietBean getDietary() {
        return dietary;
    }

    public void setDietary(TestOfDietBean dietary) {
        this.dietary = dietary;
    }

    public TestOfSportBean getSports() {
        return sports;
    }

    public void setSports(TestOfSportBean sports) {
        this.sports = sports;
    }
}
