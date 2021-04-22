package com.vice.bloodpressure.bean;

import java.util.List;

public class DietPlanFoodListBean {
    private List<String> style;
    private List<DietPlanFoodChildBean> mainfood;
    private List<DietPlanFoodChildBean> meat;
    private List<DietPlanFoodChildBean> vegetables;
    private List<DietPlanFoodChildBean> drink;
    private List<DietPlanFoodChildBean> others;

    public List<String> getStyle() {
        return style;
    }

    public void setStyle(List<String> style) {
        this.style = style;
    }

    public List<DietPlanFoodChildBean> getMainfood() {
        return mainfood;
    }

    public void setMainfood(List<DietPlanFoodChildBean> mainfood) {
        this.mainfood = mainfood;
    }

    public List<DietPlanFoodChildBean> getMeat() {
        return meat;
    }

    public void setMeat(List<DietPlanFoodChildBean> meat) {
        this.meat = meat;
    }

    public List<DietPlanFoodChildBean> getVegetables() {
        return vegetables;
    }

    public void setVegetables(List<DietPlanFoodChildBean> vegetables) {
        this.vegetables = vegetables;
    }

    public List<DietPlanFoodChildBean> getDrink() {
        return drink;
    }

    public void setDrink(List<DietPlanFoodChildBean> drink) {
        this.drink = drink;
    }

    public List<DietPlanFoodChildBean> getOthers() {
        return others;
    }

    public void setOthers(List<DietPlanFoodChildBean> others) {
        this.others = others;
    }
}
