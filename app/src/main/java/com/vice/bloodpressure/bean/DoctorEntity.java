package com.vice.bloodpressure.bean;
/*
 * 包名:     com.vice.bloodpressure.bean
 * 类名:     DoctorEntity
 * 描述:     家签医生
 * 作者:     ZWK
 * 创建日期: 2020/1/8 13:26
 */

public class DoctorEntity {

    /**
     * docname : 小润
     * docid : 6200
     */

    private String docname;
    private int userid;

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
