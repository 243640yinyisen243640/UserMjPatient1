package com.vice.bloodpressure.bean;

public class FoodRiceBean {
    private String leftString;
    private String selectString;
    private String inputString;

    public FoodRiceBean(String leftString, String selectString, String inputString) {
        this.leftString = leftString;
        this.selectString = selectString;
        this.inputString = inputString;
    }

    public String getLeftString() {
        return leftString;
    }

    public void setLeftString(String leftString) {
        this.leftString = leftString;
    }

    public String getSelectString() {
        return selectString;
    }

    public void setSelectString(String selectString) {
        this.selectString = selectString;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }
}
