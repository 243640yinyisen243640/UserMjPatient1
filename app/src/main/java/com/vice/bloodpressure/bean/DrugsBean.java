package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/1.
 * 药物实体类d大类
 */

public class DrugsBean implements Serializable {
    /**
     * id
     */
    private int id;
    /**
     * 规则id
     */
    private String rule_id;
    /**
     * 药物信息（可置换的信息）
     */
    private String drugs;
    /**
     * 推荐药物信息组
     *
     * @return
     */
    private String re_drugs;
    /**
     * @return
     */
    private String title;
    /**
     * @return
     */
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRule_id() {
        return rule_id;
    }

    public void setRule_id(String rule_id) {
        this.rule_id = rule_id;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getRe_drugs() {
        return re_drugs;
    }

    public void setRe_drugs(String re_drugs) {
        this.re_drugs = re_drugs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DrugsBean{" +
                "id='" + id + '\'' +
                ", rule_id='" + rule_id + '\'' +
                ", drugs='" + drugs + '\'' +
                ", re_drugs='" + re_drugs + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
