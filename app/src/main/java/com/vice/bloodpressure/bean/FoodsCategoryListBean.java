package com.vice.bloodpressure.bean;

import java.io.Serializable;

/**
 * 描述:  食物类型 二级数据
 * 作者: LYD
 * 创建日期: 2021/1/15 9:38
 */
public class FoodsCategoryListBean implements Serializable {
    /**
     * id : 11
     * foodname : 大米
     * explain : 25
     * picurl : http://xydoc.xiyuns.cn/Public/upload/20181127/201811271832451543314765.png
     * benefits : 80
     * ktang : 70
     * nutrition : 60
     * gival : 10
     * kcalval : 20
     * protein : 30
     * fatval : 40
     * carbohy : 50
     */
    private int id;
    private String foodname;
    private String explain;
    private String picurl;
    private String benefits;
    private String ktang;
    private String nutrition;
    private String gival;
    private String kcalval;
    private String protein;
    private String fatval;
    private String carbohy;
    //分类
    private int classify;
    private String dish_name;
    private String grams_total;
    private String hq_toal;
    private String pic;
    //
    private boolean isSelected;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getGrams_total() {
        return grams_total;
    }

    public void setGrams_total(String grams_total) {
        this.grams_total = grams_total;
    }

    public String getHq_toal() {
        return hq_toal;
    }

    public void setHq_toal(String hq_toal) {
        this.hq_toal = hq_toal;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getKtang() {
        return ktang;
    }

    public void setKtang(String ktang) {
        this.ktang = ktang;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getGival() {
        return gival;
    }

    public void setGival(String gival) {
        this.gival = gival;
    }

    public String getKcalval() {
        return kcalval;
    }

    public void setKcalval(String kcalval) {
        this.kcalval = kcalval;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFatval() {
        return fatval;
    }

    public void setFatval(String fatval) {
        this.fatval = fatval;
    }

    public String getCarbohy() {
        return carbohy;
    }

    public void setCarbohy(String carbohy) {
        this.carbohy = carbohy;
    }

}
