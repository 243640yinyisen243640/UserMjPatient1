package com.lyd.modulemall.bean;

public class RefundDetailBean {
    /**
     * order_goods_id : 26
     * goods_id : 4
     * goods_name : 测试商品四
     * sku_name :
     * goods_money : 1000.00
     * order_status : 7
     * coupon_money : 5.00
     * hospital_ids : 554740
     * goods_img_url : http://d.xiyuns.cn/publics/shopimg/goods1.png
     * refund_time : 2021-01-19 17:59:06
     * service_type : 1
     * seller_refund_name : null
     * seller_refund_mobile : null
     * seller_refund_address : null
     * seller_refund_remark : null
     * refund_real_money : 995.00
     * refund_shipping_no : xxxxxxx
     * refund_spcid : 1
     * refund_express_name : null
     * refund_mobile : 16616616610
     * seller_agree_time : 1970-01-01 08:00:00
     * refund_status : 1
     * is_mail : 1
     * refund_countdown : 547490
     * cancel_refund_countdown : 0
     * action : zzzz
     * orderrefund : {"action_way":1,"action":"zzzz","create_time":"2021-01-19 17:59"}
     */

    private int order_goods_id;
    private int goods_id;
    private String goods_name;
    private String sku_name;
    private String goods_money;
    private int order_status;
    private String coupon_money;
    private String hospital_ids;
    private String goods_img_url;
    private String refund_time;
    private int service_type;
    private String seller_refund_name;
    private String seller_refund_mobile;
    private String seller_refund_address;
    private String seller_refund_remark;
    private String refund_real_money;
    private String refund_shipping_no;
    private int refund_spcid;
    private String refund_express_name;
    private String refund_mobile;
    private String seller_agree_time;
    private int refund_status;
    private int is_mail;
    private int refund_countdown;
    private int cancel_refund_countdown;
    private String action;
    private OrderrefundBean orderrefund;

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

    public String getGoods_money() {
        return goods_money;
    }

    public void setGoods_money(String goods_money) {
        this.goods_money = goods_money;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(String coupon_money) {
        this.coupon_money = coupon_money;
    }

    public String getHospital_ids() {
        return hospital_ids;
    }

    public void setHospital_ids(String hospital_ids) {
        this.hospital_ids = hospital_ids;
    }

    public String getGoods_img_url() {
        return goods_img_url;
    }

    public void setGoods_img_url(String goods_img_url) {
        this.goods_img_url = goods_img_url;
    }

    public String getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(String refund_time) {
        this.refund_time = refund_time;
    }

    public int getService_type() {
        return service_type;
    }

    public void setService_type(int service_type) {
        this.service_type = service_type;
    }

    public String getSeller_refund_name() {
        return seller_refund_name;
    }

    public void setSeller_refund_name(String seller_refund_name) {
        this.seller_refund_name = seller_refund_name;
    }

    public String getSeller_refund_mobile() {
        return seller_refund_mobile;
    }

    public void setSeller_refund_mobile(String seller_refund_mobile) {
        this.seller_refund_mobile = seller_refund_mobile;
    }

    public String getSeller_refund_address() {
        return seller_refund_address;
    }

    public void setSeller_refund_address(String seller_refund_address) {
        this.seller_refund_address = seller_refund_address;
    }

    public String getSeller_refund_remark() {
        return seller_refund_remark;
    }

    public void setSeller_refund_remark(String seller_refund_remark) {
        this.seller_refund_remark = seller_refund_remark;
    }

    public String getRefund_real_money() {
        return refund_real_money;
    }

    public void setRefund_real_money(String refund_real_money) {
        this.refund_real_money = refund_real_money;
    }

    public String getRefund_shipping_no() {
        return refund_shipping_no;
    }

    public void setRefund_shipping_no(String refund_shipping_no) {
        this.refund_shipping_no = refund_shipping_no;
    }

    public int getRefund_spcid() {
        return refund_spcid;
    }

    public void setRefund_spcid(int refund_spcid) {
        this.refund_spcid = refund_spcid;
    }

    public String getRefund_express_name() {
        return refund_express_name;
    }

    public void setRefund_express_name(String refund_express_name) {
        this.refund_express_name = refund_express_name;
    }

    public String getRefund_mobile() {
        return refund_mobile;
    }

    public void setRefund_mobile(String refund_mobile) {
        this.refund_mobile = refund_mobile;
    }

    public String getSeller_agree_time() {
        return seller_agree_time;
    }

    public void setSeller_agree_time(String seller_agree_time) {
        this.seller_agree_time = seller_agree_time;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public int getIs_mail() {
        return is_mail;
    }

    public void setIs_mail(int is_mail) {
        this.is_mail = is_mail;
    }

    public int getRefund_countdown() {
        return refund_countdown;
    }

    public void setRefund_countdown(int refund_countdown) {
        this.refund_countdown = refund_countdown;
    }

    public int getCancel_refund_countdown() {
        return cancel_refund_countdown;
    }

    public void setCancel_refund_countdown(int cancel_refund_countdown) {
        this.cancel_refund_countdown = cancel_refund_countdown;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public OrderrefundBean getOrderrefund() {
        return orderrefund;
    }

    public void setOrderrefund(OrderrefundBean orderrefund) {
        this.orderrefund = orderrefund;
    }

    public static class OrderrefundBean {
        /**
         * action_way : 1
         * action : zzzz
         * create_time : 2021-01-19 17:59
         */

        private int action_way;
        private String action;
        private String create_time;

        public int getAction_way() {
            return action_way;
        }

        public void setAction_way(int action_way) {
            this.action_way = action_way;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
