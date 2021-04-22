package com.vice.bloodpressure.bean;

import java.util.List;

public class FoodAndDrinkBean {


    /**
     * category : 1
     * datetime : 2019-07-01 11:13
     * foods : [{"foodname":"燕麦片","dosage":"20","kcal":"12"},{"foodname":"小麦粉（标准粉）","dosage":"60","kcal":"13"},{"foodname":"猪肝","dosage":"80","kcal":"14"}]
     */
    private int id;
    private String category;
    private String datetime;
    private List<FoodsBean> foods;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<FoodsBean> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodsBean> foods) {
        this.foods = foods;
    }

    public static class FoodsBean {
        /**
         * foodname : 燕麦片
         * dosage : 20
         * kcal : 12
         */

        private String foodname;
        private String dosage;
        private String kcal;

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

        public String getKcal() {
            return kcal;
        }

        public void setKcal(String kcal) {
            this.kcal = kcal;
        }
    }

}
