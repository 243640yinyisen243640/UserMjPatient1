package com.vice.bloodpressure.bean;

public class HospitalLeftBean {

    /**
     * depid : 1
     * depname : 慢病管理科
     */

    private int depid;
    private String depname;

    public int getDepid() {
        return depid;
    }

    public void setDepid(int depid) {
        this.depid = depid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    @Override
    public String toString() {
        return "HospitalLeftBean{" +
                "depid=" + depid +
                ", depname='" + depname + '\'' +
                '}';
    }
}
