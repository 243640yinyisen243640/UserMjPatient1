package com.lyd.modulemall.bean;

import java.util.List;

public class ShoppingCartListBean {

    private List<NormalCartBean> normal_cart;
    private List<NormalCartBean> disabled_cart;

    public List<NormalCartBean> getNormal_cart() {
        return normal_cart;
    }

    public void setNormal_cart(List<NormalCartBean> normal_cart) {
        this.normal_cart = normal_cart;
    }

    public List<NormalCartBean> getDisabled_cart() {
        return disabled_cart;
    }

    public void setDisabled_cart(List<NormalCartBean> disabled_cart) {
        this.disabled_cart = disabled_cart;
    }

    public static class NormalCartBean {
        /**
         * cart_id : 11
         * num : 10
         * sku_name : 黄
         * goods_sku_id : 6
         * goods_name : 后台测试商品2
         * goods_img_url : http://shoppic.xiyuns.cn/1609236412413509.png
         * price : 100.00
         */

        private int cart_id;
        private int num;
        private int goods_id;
        private String sku_name;
        private int goods_sku_id;
        private String goods_name;
        private String goods_img_url;
        private String price;

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public int getGoods_sku_id() {
            return goods_sku_id;
        }

        public void setGoods_sku_id(int goods_sku_id) {
            this.goods_sku_id = goods_sku_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_img_url() {
            return goods_img_url;
        }

        public void setGoods_img_url(String goods_img_url) {
            this.goods_img_url = goods_img_url;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
