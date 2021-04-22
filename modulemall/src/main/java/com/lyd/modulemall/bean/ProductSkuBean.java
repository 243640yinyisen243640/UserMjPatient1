package com.lyd.modulemall.bean;

import java.util.List;

/**
 * 描述:  商品规格 数据bean
 * 作者: LYD
 * 创建日期: 2021/1/22 14:43
 */
public class ProductSkuBean {
    private List<SpecBean> spec;
    private List<ListBean> list;

    public List<SpecBean> getSpec() {
        return spec;
    }

    public void setSpec(List<SpecBean> spec) {
        this.spec = spec;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class SpecBean {
        /**
         * key : 颜色
         * value : ["红","黄"]
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }

    public static class ListBean {
        /**
         * goods_sku_id : 1
         * goods_id : 1
         * price : 100.00
         * stock : 48
         * sku_img :
         * attr_value_items : 1:1;2:3
         * spec_1 : 红
         * spec_2 : L
         * spec : 红,L
         * spec_value : ["红","L"]
         */

        private int goods_sku_id;
        private int goods_id;
        private double price;
        private int stock;
        private String sku_img;
        private String attr_value_items;
        private String spec_1;
        private String spec_2;
        private String spec;
        private List<String> spec_value;

        public int getGoods_sku_id() {
            return goods_sku_id;
        }

        public void setGoods_sku_id(int goods_sku_id) {
            this.goods_sku_id = goods_sku_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getSku_img() {
            return sku_img;
        }

        public void setSku_img(String sku_img) {
            this.sku_img = sku_img;
        }

        public String getAttr_value_items() {
            return attr_value_items;
        }

        public void setAttr_value_items(String attr_value_items) {
            this.attr_value_items = attr_value_items;
        }

        public String getSpec_1() {
            return spec_1;
        }

        public void setSpec_1(String spec_1) {
            this.spec_1 = spec_1;
        }

        public String getSpec_2() {
            return spec_2;
        }

        public void setSpec_2(String spec_2) {
            this.spec_2 = spec_2;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public List<String> getSpec_value() {
            return spec_value;
        }

        public void setSpec_value(List<String> spec_value) {
            this.spec_value = spec_value;
        }
    }
}
