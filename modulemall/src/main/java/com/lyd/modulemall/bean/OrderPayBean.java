package com.lyd.modulemall.bean;

public class OrderPayBean {
    /**
     * type : 1
     * order_id : 92
     * pay_money : 100
     * count_down : 1799
     */

    private int type;
    private String order_id;
    private double pay_money;
    private long count_down;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public double getPay_money() {
        return pay_money;
    }

    public void setPay_money(double pay_money) {
        this.pay_money = pay_money;
    }

    public long getCount_down() {
        return count_down;
    }

    public void setCount_down(long count_down) {
        this.count_down = count_down;
    }
}
