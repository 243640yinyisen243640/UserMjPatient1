package com.lyd.modulemall.bean;

public class MyRefundOrderListBean {
    /**
     * order_goods_id : 26
     * goods_id : 4
     * goods_name : 测试商品四
     * sku_name :
     * refund_real_money : 995.00
     * price : 100.00
     * num : 10
     * refund_status : 1
     * service_type : 1
     * shipping_money : 20.00
     * coupon_money : 5.00
     * goods_img_url : http://d.xiyuns.cn/publics/shopimg/goods1.png
     */

    private int order_goods_id;
    private int goods_id;
    private String goods_name;
    private String sku_name;
    private String refund_real_money;
    private String price;
    private int num;
    private int refund_status;
    private int service_type;
    private String shipping_money;
    private String coupon_money;
    private String goods_img_url;

    public int getOrder_goods_id() {
        return order_goods_id;
    }

    public void setOrder_goods_id(int order_goods_id) {
        this.order_goods_id = order_goods_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getSku_name() {
        return sku_name;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
    }

    public String getRefund_real_money() {
        return refund_real_money;
    }

    public void setRefund_real_money(String refund_real_money) {
        this.refund_real_money = refund_real_money;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public String getShipping_money() {
        return shipping_money;
    }

    public void setShipping_money(String shipping_money) {
        this.shipping_money = shipping_money;
    }

    public String getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(String coupon_money) {
        this.coupon_money = coupon_money;
    }

    public String getGoods_img_url() {
        return goods_img_url;
    }

    public void setGoods_img_url(String goods_img_url) {
        this.goods_img_url = goods_img_url;
    }
}
