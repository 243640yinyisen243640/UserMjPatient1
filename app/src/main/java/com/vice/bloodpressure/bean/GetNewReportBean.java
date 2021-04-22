package com.vice.bloodpressure.bean;

import java.util.List;

public class GetNewReportBean {


    /**
     * id : 3
     * treatment : 1
     * recommend : 1、3个月后到心内科专科门诊复查； 2、若治疗过程中出现血压异常，建议重新进入个性化治疗方案板块重新获取治疗方案，或就诊于当地心内科门诊。
     * rec_article : ["1、3个月后到心内科专科门诊复查；","2、若治疗过程中出现血压异常，建议重新进入个性化治疗方案板块重新获取治疗方案，或就诊于当地心内科门诊。"]
     * addtime : 2019-03-28 18:07:52
     * paid : 29
     * drugs : 1
     * pstatus : 3
     * message : null
     */

    private String id;
    private String treatment;
    private String recommend;
    private String addtime;
    private int paid;
    private int drugs;
    private int pstatus;
    private String message;
    private List<String> rec_article;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getDrugs() {
        return drugs;
    }

    public void setDrugs(int drugs) {
        this.drugs = drugs;
    }

    public int getPstatus() {
        return pstatus;
    }

    public void setPstatus(int pstatus) {
        this.pstatus = pstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getRec_article() {
        return rec_article;
    }

    public void setRec_article(List<String> rec_article) {
        this.rec_article = rec_article;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
