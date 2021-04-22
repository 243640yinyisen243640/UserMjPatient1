package com.vice.bloodpressure.bean;

import android.text.TextUtils;

public class ReportDoctorAdviceListBean {

    /**
     * hospitalname : 首都医科大学附属北京世纪坛医院
     * depname : 糖尿病中心
     * docname : 盛医生
     * schday : 2019-10-22
     * schtime : 9:00~11:00
     * type : 上午
     * advice :
     */

    private String hospitalname;
    private String depname;
    private String docname;
    private String schday;
    private String schtime;
    private String type;
    private String advice;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdvice() {
        return TextUtils.isEmpty(advice) ? "暂无医生建议" : advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
