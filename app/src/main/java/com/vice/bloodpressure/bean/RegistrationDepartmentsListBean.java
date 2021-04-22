package com.vice.bloodpressure.bean;

import java.io.Serializable;

public class RegistrationDepartmentsListBean implements Serializable {

    /**
     * depid : 3
     * depname : 内分泌科
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
}
