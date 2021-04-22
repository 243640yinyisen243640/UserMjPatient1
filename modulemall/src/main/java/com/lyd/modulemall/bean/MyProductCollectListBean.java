package com.lyd.modulemall.bean;

public class MyProductCollectListBean {
    /**
     * collect_id : 5
     * create_time : 2020-12-29 11:44:06
     * goods_id : 1
     * goods_name : 测试商品一
     * market_price : 200.00
     * price : 100.00
     * goods_img_url : http://d.xiyuns.cn/publics/shopimg/goods1.png
     * state : 1
     */

    private int collect_id;
    private String create_time;
    private int goods_id;
    private String goods_name;
    private String market_price;
    private String price;
    private String goods_img_url;
    private int state;

    public int getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(int collect_id) {
        this.collect_id = collect_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoods_img_url() {
        return goods_img_url;
    }

    public void setGoods_img_url(String goods_img_url) {
        this.goods_img_url = goods_img_url;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
