package com.vice.bloodpressure.bean;

public class UpdateBean {


    /**
     * versionname : v1.1.0
     * version : 18
     * updateurl : http://doctor.xiyuns.cn/Public/app/com_xy_xydoctor.apk
     * appsize : 10.9M
     * upcontent :
     * 1.新增模块
     *2.修复已知bug
     */

    private String versionname;
    private int version;
    private String updateurl;
    private String appsize;
    private String upcontent;

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }

    public String getAppsize() {
        return appsize;
    }

    public void setAppsize(String appsize) {
        this.appsize = appsize;
    }

    public String getUpcontent() {
        return upcontent;
    }

    public void setUpcontent(String upcontent) {
        this.upcontent = upcontent;
    }
}
