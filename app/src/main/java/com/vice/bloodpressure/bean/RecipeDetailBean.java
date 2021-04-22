package com.vice.bloodpressure.bean;

import java.util.List;

public class RecipeDetailBean {
    /**
     * id : 4
     * dish_name : 二米饭
     * pic :
     * practice : 45
     * seasoning : 是否
     * name : ["大米","小米"]
     * gramsrate : ["0.6154","0.3846"]
     * hqrate : ["0.6154","0.3846"]
     */

    private int id;
    private String dish_name;
    private String pic;
    private String practice;
    private String seasoning;
    private List<String> name;
    private List<String> gramsrate;
    private List<String> hqrate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getSeasoning() {
        return seasoning;
    }

    public void setSeasoning(String seasoning) {
        this.seasoning = seasoning;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getGramsrate() {
        return gramsrate;
    }

    public void setGramsrate(List<String> gramsrate) {
        this.gramsrate = gramsrate;
    }

    public List<String> getHqrate() {
        return hqrate;
    }

    public void setHqrate(List<String> hqrate) {
        this.hqrate = hqrate;
    }
}
