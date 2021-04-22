package com.lyd.modulemall.bean;

import java.util.List;

/**
 * 描述: 商品列表数据
 * 作者: LYD
 * 创建日期: 2021/1/22 16:14
 */
public class MallHomeProductBean {
    /**
     * goods_id : 1
     * goods_name : 测试商品一
     * goods_label_ids : ["标签一"]
     * market_price : 200.00
     * price : 100.00
     * sales : 23
     * real_sales : 42
     * goods_img_url : http://d.xiyuns.cn/publics/shopimg/goods1.png
     * total_sales : 65
     * show_sales : 65
     */

    private int goods_id;
    private String goods_name;
    private String market_price;
    private String price;
    private int sales;
    private int real_sales;
    private String goods_img_url;
    private int total_sales;
    private int show_sales;
    private List<String> goods_label_ids;

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

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getReal_sales() {
        return real_sales;
    }

    public void setReal_sales(int real_sales) {
        this.real_sales = real_sales;
    }

    public String getGoods_img_url() {
        return goods_img_url;
    }

    public void setGoods_img_url(String goods_img_url) {
        this.goods_img_url = goods_img_url;
    }

    public int getTotal_sales() {
        return total_sales;
    }

    public void setTotal_sales(int total_sales) {
        this.total_sales = total_sales;
    }

    public int getShow_sales() {
        return show_sales;
    }

    public void setShow_sales(int show_sales) {
        this.show_sales = show_sales;
    }

    public List<String> getGoods_label_ids() {
        return goods_label_ids;
    }

    public void setGoods_label_ids(List<String> goods_label_ids) {
        this.goods_label_ids = goods_label_ids;
    }
}
