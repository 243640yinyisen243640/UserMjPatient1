package com.vice.bloodpressure.bean;


/**
 * 描述:  食物类型 一级数据
 * 作者: LYD
 * 创建日期: 2021/1/15 9:34
 */
public class FoodsCategoryBean {

    /**
     * id : 1
     * foodlei : 谷物类
     */
    private int id;
    private String foodlei;
    //新增
    private String classify_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodlei() {
        return foodlei;
    }

    public void setFoodlei(String foodlei) {
        this.foodlei = foodlei;
    }

    public String getClassify_name() {
        return classify_name;
    }

    public void setClassify_name(String classify_name) {
        this.classify_name = classify_name;
    }
}
