package com.vice.bloodpressure.bean;

/**
 * Created by yicheng on 2018/5/31.
 * 获取饮食数据实体类
 */

public class FoodBean {
    /**
     * 标签id
     */
    private String id;
    /**
     * 用户id
     */
    private String uid;
    /**
     * 食物名
     */
    private String foodname;
    /**
     * 食物量
     */
    private String dosage;
    /**
     * 餐点类别
     */
    private String category;
    /**
     * 记录时间点
     */
    private String datetime;
    /**
     * 备注标签
     */
    private String[] remark;
    /**
     * 系统时间
     */
    private String addtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String[] getRemark() {
        return remark;
    }

    public void setRemark(String[] remark) {
        this.remark = remark;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
