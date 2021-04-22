package com.lyd.modulemall.bean;

import java.util.List;

public class MyOrderListBean {

    /**
     * order_id : 25
     * order_no : 20210108110113313244
     * order_status : 3
     * pay_status : 2
     * order_money : 1020.00
     * pay_money : 515.00
     * coupon_money : 5.00
     * shipping_money : 20.00
     * order_goods : [{"goods_img_url":"http://d.xiyuns.cn/publics/shopimg/goods1.png","goods_id":4,"goods_name":"测试商品四","goods_sku_id":0,"sku_name":"","price":"50.00","num":10,"shipping_money":"20.00"}]
     */

    private int order_id;
    private String order_no;
    private int order_status;
    private int pay_status;
    private String user_address_id;
    private String order_money;
    private String pay_money;
    private String coupon_money;
    private String shipping_money;
    private List<MyOrderListProductListBean> order_goods;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public String getUser_address_id() {
        return user_address_id;
    }

    public void setUser_address_id(String user_address_id) {
        this.user_address_id = user_address_id;
    }

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(String coupon_money) {
        this.coupon_money = coupon_money;
    }

    public String getShipping_money() {
        return shipping_money;
    }

    public void setShipping_money(String shipping_money) {
        this.shipping_money = shipping_money;
    }

    public List<MyOrderListProductListBean> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<MyOrderListProductListBean> order_goods) {
        this.order_goods = order_goods;
    }
}
