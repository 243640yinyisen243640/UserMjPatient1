package com.vice.bloodpressure.bean;

import java.util.List;

public class MyTreatPlanDetailBean {
    /**
     * id : 16
     * treatment : 1
     * content : 心血管风险指个体在未来一段时间内（5～10年甚至终生）发生心血管事件（如心肌梗死、脑出血或脑梗死、心脏猝死等）的风险或概率。“很高危”意味着您未来10年缺血性心血管病发病风险＞30%鉴于此，我们建议您：（1）立即开始药物治疗；（2）坚持生活方式干预；（3）多次诊室测血压或进行动态血压监测,并坚持家中自我血压监测；（4）如药物治疗1～3个月后血压仍不达标，可咨询医生调整药物治疗方案
     * recommend : 若2周后血压仍控制不佳，建议重新进入个性化治疗方案板块重新获取治疗方案，或就诊于当地心内科门诊。
     * rec_article : ["若2周后血压仍控制不佳，建议重新进入个性化治疗方案板块重新获取治疗方案，或就诊于当地心内科门诊。"]
     * addtime : 2019-03-29 15:29:31
     * drugs : 1
     * message :
     * paid : 228
     * bplevel : 3
     * degree : 很高危
     */

    private int id;
    private String treatment;
    private String content;
    private String recommend;
    private String addtime;
    private int drugs;
    private String message;
    private int paid;
    private int bplevel;
    private String degree;
    private List<String> rec_article;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getDrugs() {
        return drugs;
    }

    public void setDrugs(int drugs) {
        this.drugs = drugs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getBplevel() {
        return bplevel;
    }

    public void setBplevel(int bplevel) {
        this.bplevel = bplevel;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<String> getRec_article() {
        return rec_article;
    }

    public void setRec_article(List<String> rec_article) {
        this.rec_article = rec_article;
    }
}
