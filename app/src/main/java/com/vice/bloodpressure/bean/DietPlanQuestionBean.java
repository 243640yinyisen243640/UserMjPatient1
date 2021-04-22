package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * 描述:  饮食答题
 * 作者: LYD
 * 创建日期: 2019/12/17 14:30
 */
public class DietPlanQuestionBean implements Serializable {
    //sex	是	int	1男2女
    //height	是	int	身高
    //weight	是	int	体重
    //profession	是	int	1轻体力2中体力3重体力 4卧床
    //dn	是	int	糖尿病肾病1有2无
    //dn_type	是	int	糖尿病肾病类型（1一期2二期）

    //二期移除
    //bg	是	int	血糖1是2否
    //bg_type	是	int	血糖偏高时间段（1早餐前/后2午餐前/后）

    private String sex;
    private String height;
    private String weight;
    private String profession;
    private String dn;
    private String dn_type;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getDn_type() {
        return dn_type;
    }

    public void setDn_type(String dn_type) {
        this.dn_type = dn_type;
    }
}
