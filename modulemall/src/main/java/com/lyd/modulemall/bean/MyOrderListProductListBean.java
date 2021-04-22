package com.lyd.modulemall.bean;

public class MyOrderListProductListBean {

    /**
     * goods_img_url : http://d.xiyuns.cn/publics/shopimg/goods1.png
     * goods_id : 4
     * goods_name : 测试商品四
     * goods_sku_id : 0
     * sku_name :
     * price : 50.00
     * num : 10
     * shipping_money : 20.00
     */

    private String goods_img_url;
    private int goods_id;
    private String goods_name;
    private int goods_sku_id;
    private String sku_name;
    private String price;
    private int num;
    private String shipping_money;
    private int refund_status;

    public String getGoods_img_url() {
        return goods_img_url;
    }

    public void setGoods_img_url(String goods_img_url) {
        this.goods_img_url = goods_img_url;
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

    public int getGoods_sku_id() {
        return goods_sku_id;
    }

    public void setGoods_sku_id(int goods_sku_id) {
        this.goods_sku_id = goods_sku_id;
    }

    public String getSku_name() {
        return sku_name;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
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

    public String getShipping_money() {
        return shipping_money;
    }

    public void setShipping_money(String shipping_money) {
        this.shipping_money = shipping_money;
    }


    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }
}
