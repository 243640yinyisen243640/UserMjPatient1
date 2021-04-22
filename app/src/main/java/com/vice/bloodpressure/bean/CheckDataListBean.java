package com.vice.bloodpressure.bean;

import android.text.TextUtils;

public class CheckDataListBean {
    /**
     * bloodpressure :
     * bmi :
     * cdu : 2019-09-11
     * ecg :
     * ct :
     * tr :
     * biochemical_analysis :
     * routine_blood : 2019-09-10
     * routine_urinary : 2019-09-10
     */
    private String bloodpressure;
    private String bmi;
    private String cdu;
    private String ecg;
    private String ct;
    private String tr;
    private String biochemical_analysis;
    private String routine_blood;
    private String routine_urinary;
    //医生建议
    private String docadvice;
    //电解质
    private String electrolyte;
    //荧光分析
    private String immuneinstrument;
    //凝血
    private String bloodinstrument;

    public String getImmuneinstrument() {
        return isNull(immuneinstrument);
    }

    public void setImmuneinstrument(String immuneinstrument) {
        this.immuneinstrument = immuneinstrument;
    }

    public String getBloodinstrument() {
        return isNull(bloodinstrument);
    }

    public void setBloodinstrument(String bloodinstrument) {
        this.bloodinstrument = bloodinstrument;
    }

    public String getBloodpressure() {
        return isNull(bloodpressure);
    }

    public void setBloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public String getBmi() {
        return isNull(bmi);
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getCdu() {
        return isNull(cdu);
    }

    public void setCdu(String cdu) {
        this.cdu = cdu;
    }

    public String getEcg() {
        return isNull(ecg);
    }

    public void setEcg(String ecg) {
        this.ecg = ecg;
    }

    public String getCt() {
        return isNull(ct);
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getTr() {
        return isNull(tr);
    }

    public void setTr(String tr) {
        this.tr = tr;
    }

    public String getBiochemical_analysis() {
        return isNull(biochemical_analysis);
    }

    public void setBiochemical_analysis(String biochemical_analysis) {
        this.biochemical_analysis = biochemical_analysis;
    }

    public String getRoutine_blood() {
        return isNull(routine_blood);
    }

    public void setRoutine_blood(String routine_blood) {
        this.routine_blood = routine_blood;
    }

    public String getRoutine_urinary() {
        return isNull(routine_urinary);
    }

    public void setRoutine_urinary(String routine_urinary) {
        this.routine_urinary = routine_urinary;
    }

    public String getDocadvice() {
        return isNull(docadvice);
    }

    public void setDocadvice(String docadvice) {
        this.docadvice = docadvice;
    }

    public String getElectrolyte() {
        return isNull(electrolyte);
    }

    public void setElectrolyte(String electrolyte) {
        this.electrolyte = electrolyte;
    }


    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    private String isNull(String str) {
        return TextUtils.isEmpty(str) ? "暂无数据" : "最新更新时间：" + str;
    }
}
