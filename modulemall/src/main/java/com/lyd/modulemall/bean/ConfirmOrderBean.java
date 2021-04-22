package com.lyd.modulemall.bean;

import java.util.List;

public class ConfirmOrderBean {
    /**
     * goods_detail : [{"cart_id":4,"goods_id":8,"goods_sku_id":6,"sku_name":"黄 M","num":40,"attr_value_items":"1:2;2:4","price":"100.00","stock":99,"goods_name":"后台测试商品","goods_img_url":"http://shoppic.xiyuns.cn/1609236366574984.png","shipping_fee_id":1},{"cart_id":10,"goods_id":4,"goods_sku_id":0,"sku_name":"","num":20,"goods_name":"测试商品四","price":"100.00","stock":100,"goods_img_url":"http://d.xiyuns.cn/publics/shopimg/goods1.png","shipping_fee_id":1}]
     * coupon_list : [{"couon_user_id":2,"user_id":647558,"coupon_id":2,"coupon_money":"10","at_least":"100","coupon_name":"满减二","goods_ids":"3,4,5"}]
     * userdefaultaddress : {"id":1,"user_id":647558,"receiver_name":"第一个地址","receiver_mobile":"16616616611","receiver_address":"第一个地址","province_code":"258","city_code":"963","area_code":"147","is_default":1,"is_del":1,"province":"","city":"","area":""}
     * fee_detail : {"template_name":"邮费","fee":120}
     */
    private UserdefaultaddressBean userdefaultaddress;
    private FeeDetailBean fee_detail;
    private List<GoodsDetailBean> goods_detail;
    private List<CouponListBean> coupon_list;
    private double goods_total_money;
    private String goods_nums;

    public UserdefaultaddressBean getUserdefaultaddress() {
        return userdefaultaddress;
    }

    public void setUserdefaultaddress(UserdefaultaddressBean userdefaultaddress) {
        this.userdefaultaddress = userdefaultaddress;
    }

    public FeeDetailBean getFee_detail() {
        return fee_detail;
    }

    public void setFee_detail(FeeDetailBean fee_detail) {
        this.fee_detail = fee_detail;
    }

    public List<GoodsDetailBean> getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(List<GoodsDetailBean> goods_detail) {
        this.goods_detail = goods_detail;
    }

    public List<CouponListBean> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(List<CouponListBean> coupon_list) {
        this.coupon_list = coupon_list;
    }

    public static class UserdefaultaddressBean {
        /**
         * id : 1
         * user_id : 647558
         * receiver_name : 第一个地址
         * receiver_mobile : 16616616611
         * receiver_address : 第一个地址
         * province_code : 258
         * city_code : 963
         * area_code : 147
         * is_default : 1
         * is_del : 1
         * province :
         * city :
         * area :
         */

        private int id;
        private int user_id;
        private String receiver_name;
        private String receiver_mobile;
        private String receiver_address;
        private String province_code;
        private String city_code;
        private String area_code;
        private int is_default;
        private int is_del;
        private String province;
        private String city;
        private String area;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getReceiver_name() {
            return receiver_name;
        }

        public void setReceiver_name(String receiver_name) {
            this.receiver_name = receiver_name;
        }

        public String getReceiver_mobile() {
            return receiver_mobile;
        }

        public void setReceiver_mobile(String receiver_mobile) {
            this.receiver_mobile = receiver_mobile;
        }

        public String getReceiver_address() {
            return receiver_address;
        }

        public void setReceiver_address(String receiver_address) {
            this.receiver_address = receiver_address;
        }

        public String getProvince_code() {
            return province_code;
        }

        public void setProvince_code(String province_code) {
            this.province_code = province_code;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }

    public static class FeeDetailBean {
        /**
         * template_name : 邮费
         * fee : 120
         */

        private String template_name;
        private double fee;

        public String getTemplate_name() {
            return template_name;
        }

        public void setTemplate_name(String template_name) {
            this.template_name = template_name;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }
    }

    public static class GoodsDetailBean {
        /**
         * cart_id : 4
         * goods_id : 8
         * goods_sku_id : 6
         * sku_name : 黄 M
         * num : 40
         * attr_value_items : 1:2;2:4
         * price : 100.00
         * stock : 99
         * goods_name : 后台测试商品
         * goods_img_url : http://shoppic.xiyuns.cn/1609236366574984.png
         * shipping_fee_id : 1
         */

        private int cart_id;
        private int goods_id;
        private int goods_sku_id;
        private String sku_name;
        private int num;
        private String attr_value_items;
        private String price;
        private int stock;
        private String goods_name;
        private String goods_img_url;
        private int shipping_fee_id;

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
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

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getAttr_value_items() {
            return attr_value_items;
        }

        public void setAttr_value_items(String attr_value_items) {
            this.attr_value_items = attr_value_items;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
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

        public int getShipping_fee_id() {
            return shipping_fee_id;
        }

        public void setShipping_fee_id(int shipping_fee_id) {
            this.shipping_fee_id = shipping_fee_id;
        }
    }

    public static class CouponListBean {
        /**
         * couon_user_id : 2
         * user_id : 647558
         * coupon_id : 2
         * coupon_money : 10
         * at_least : 100
         * coupon_name : 满减二
         * goods_ids : 3,4,5
         */

        private int couon_user_id;
        private int user_id;
        private int coupon_id;
        private String coupon_money;
        private String at_least;
        private String coupon_name;
        private String goods_ids;

        public int getCouon_user_id() {
            return couon_user_id;
        }

        public void setCouon_user_id(int couon_user_id) {
            this.couon_user_id = couon_user_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(String coupon_money) {
            this.coupon_money = coupon_money;
        }

        public String getAt_least() {
            return at_least;
        }

        public void setAt_least(String at_least) {
            this.at_least = at_least;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getGoods_ids() {
            return goods_ids;
        }

        public void setGoods_ids(String goods_ids) {
            this.goods_ids = goods_ids;
        }
    }

    public double getGoods_total_money() {
        return goods_total_money;
    }

    public void setGoods_total_money(double goods_total_money) {
        this.goods_total_money = goods_total_money;
    }

    public String getGoods_nums() {
        return goods_nums;
    }

    public void setGoods_nums(String goods_nums) {
        this.goods_nums = goods_nums;
    }
}
