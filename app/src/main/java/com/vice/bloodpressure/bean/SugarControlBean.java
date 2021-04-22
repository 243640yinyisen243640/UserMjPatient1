package com.vice.bloodpressure.bean;

public class SugarControlBean {
    private String min;
    private String max;

    public SugarControlBean(String min, String max) {
        this.min = min;
        this.max = max;
    }


    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
