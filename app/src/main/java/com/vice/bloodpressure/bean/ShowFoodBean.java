package com.vice.bloodpressure.bean;

public class ShowFoodBean {
    /**
     * id : 11
     * foodname : 大米
     * explain : 125
     * picurl : /Public/upload/20181127/201811271832451543314765.png
     */

    private int id;
    private String foodname;
    private String explain;
    private String picurl;

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
}
