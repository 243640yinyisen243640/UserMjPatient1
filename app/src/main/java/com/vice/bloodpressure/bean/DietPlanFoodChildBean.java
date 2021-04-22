package com.vice.bloodpressure.bean;

import java.io.Serializable;

public class DietPlanFoodChildBean implements Serializable {
    private int id;
    private String name;
    private String dish_name;
    private String pic;
    private String grams_avg;
    private int cid;
    private int hq;
    private String str;
    private int greensid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getGrams_avg() {
        return grams_avg;
    }

    public void setGrams_avg(String grams_avg) {
        this.grams_avg = grams_avg;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getHq() {
        return hq;
    }

    public void setHq(int hq) {
        this.hq = hq;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }


    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public int getGreensid() {
        return greensid;
    }

    public void setGreensid(int greensid) {
        this.greensid = greensid;
    }
}
