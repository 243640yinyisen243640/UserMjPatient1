package com.lyd.modulemall.bean;

import java.util.List;

public class ProductDetailBean {
    /**
     * goods_id : 2
     * goods_name : 测试商品二
     * organization_id : 1
     * supplier_id : 1
     * market_price : 200.00
     * price : 100.00
     * sales : 50
     * real_sales : 82
     * shipping_fee_id : 1
     * stock : 518
     * description : http://xiyuncsgit.xin/shop/Nsoffline/nsdetails/goods_id/2
     * goods_img_url : ["http://d.xiyuns.cn/publics/shopimg/goods1.png","http://d.xiyuns.cn/publics/shopimg/goods2.png"]
     * return_desc : 想要退货，那是不可能的
     * state : 1
     * shipments_address : --
     * shipping_default_money : 6.00
     * total_sales : 132
     * collect_type : 1
     */

    private int goods_id;
    private String goods_name;
    private int organization_id;
    private int supplier_id;
    private String market_price;
    private String price;
    private int sales;
    private int real_sales;
    private int shipping_fee_id;
    private int stock;
    private String description;
    private String return_desc;
    private int state;
    private String shipments_address;
    private String shipping_default_money;
    private int total_sales;
    private int collect_type;
    private List<String> goods_img_url;

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

    public int getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(int organization_id) {
        this.organization_id = organization_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
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

    public int getShipping_fee_id() {
        return shipping_fee_id;
    }

    public void setShipping_fee_id(int shipping_fee_id) {
        this.shipping_fee_id = shipping_fee_id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReturn_desc() {
        return return_desc;
    }

    public void setReturn_desc(String return_desc) {
        this.return_desc = return_desc;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getShipments_address() {
        return shipments_address;
    }

    public void setShipments_address(String shipments_address) {
        this.shipments_address = shipments_address;
    }

    public String getShipping_default_money() {
        return shipping_default_money;
    }

    public void setShipping_default_money(String shipping_default_money) {
        this.shipping_default_money = shipping_default_money;
    }

    public int getTotal_sales() {
        return total_sales;
    }

    public void setTotal_sales(int total_sales) {
        this.total_sales = total_sales;
    }

    public int getCollect_type() {
        return collect_type;
    }

    public void setCollect_type(int collect_type) {
        this.collect_type = collect_type;
    }

    public List<String> getGoods_img_url() {
        return goods_img_url;
    }

    public void setGoods_img_url(List<String> goods_img_url) {
        this.goods_img_url = goods_img_url;
    }
}
