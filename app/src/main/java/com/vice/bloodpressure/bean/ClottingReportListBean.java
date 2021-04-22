package com.vice.bloodpressure.bean;

public class ClottingReportListBean {
    //"name": "PTT",//名称
    //"val": "",//值
    //"ref": "10.0-15.0",//参考值
    //"status": 2//数据状态 1偏高  2偏低 0 正常
    private String name;
    private String val;
    private String ref;
    private int status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
