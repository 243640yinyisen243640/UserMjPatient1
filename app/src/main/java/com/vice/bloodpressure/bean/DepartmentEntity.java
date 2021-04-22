package com.vice.bloodpressure.bean;
/*
 * 包名:     com.vice.bloodpressure.bean
 * 类名:     DepartmentEntity
 * 描述:     家签科室
 * 作者:     ZWK
 * 创建日期: 2020/1/8 11:51
 */

public class DepartmentEntity {

    /**
     * depname : 糖尿病科室
     * depid : 6
     */

    private String depname;
    private int depid;

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public int getDepid() {
        return depid;
    }

    public void setDepid(int depid) {
        this.depid = depid;
    }
}
