package com.lyd.modulemall.bean;

import java.util.List;

public class MyOrderDetailBean {
    /**
     * order_id : 187
     * order_no : 20210126140540370721
     * order_status : 3
     * pay_status : 1
     * pay_time : 1970-01-01 08:00:00
     * order_money : 400.00
     * pay_money : 400.00
     * coupon_money : 0.00
     * shipping_money : 0.00
     * create_time : 2021-01-26 14:05:40
     * signfor_time : 2019-08-27 13:56:19
     * delivery_time : 2021-01-27 16:19:45
     * finish_time : 1970-01-01 08:00:00
     * receiver_name : zzzz
     * receiver_mobile : 18697320800
     * receiver_address : 天津天津市河西区112323
     * deliver_id : 0
     * deliver_no : 54415
     * deliver_type : SFEXPRESS
     * seller_remark :
     * cancel_type : 1
     * cancel_remark :
     * count_down : 0
     * logistics_info : {"number":"780098068058-11","type":"zto","list":[{"time":"2018-03-09 11:59:26","status":"【石家庄市】快件已在【长安三部】 签收,期待再次为您服务!"}],"deliverystatus":"3","updateTime":"2019-08-27 13:56:19"}
     * order_goods : [{"goods_img_url":"http://d.xiyuns.cn/publics/shopimg/goods1.png","order_goods_id":190,"goods_id":2,"order_status":3,"goods_name":"测试商品二","goods_sku_id":2,"sku_name":"黄 M","price":"100.00","num":4,"shipping_money":"0.00","refund_status":0,"shelf_life":7,"sales_return":2}]
     * auto_receive_countdown : 1296091
     */

    private int order_id;
    private String order_no;
    private int order_status;
    private int pay_status;
    private String pay_time;
    private String order_money;
    private String pay_money;
    private String coupon_money;
    private String shipping_money;
    private String create_time;
    private String signfor_time;
    private String delivery_time;
    private String finish_time;
    private String receiver_name;
    private String receiver_mobile;
    private String receiver_address;
    private int deliver_id;
    private String deliver_no;
    private String deliver_type;
    private String seller_remark;
    private int cancel_type;
    private String cancel_remark;
    private int count_down;
    private String user_address_id;
    private LogisticsInfoBean logistics_info;
    private long auto_receive_countdown;
    private List<OrderGoodsBean> order_goods;

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

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getSignfor_time() {
        return signfor_time;
    }

    public void setSignfor_time(String signfor_time) {
        this.signfor_time = signfor_time;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
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

    public int getDeliver_id() {
        return deliver_id;
    }

    public void setDeliver_id(int deliver_id) {
        this.deliver_id = deliver_id;
    }

    public String getDeliver_no() {
        return deliver_no;
    }

    public void setDeliver_no(String deliver_no) {
        this.deliver_no = deliver_no;
    }

    public String getDeliver_type() {
        return deliver_type;
    }

    public void setDeliver_type(String deliver_type) {
        this.deliver_type = deliver_type;
    }

    public String getSeller_remark() {
        return seller_remark;
    }

    public void setSeller_remark(String seller_remark) {
        this.seller_remark = seller_remark;
    }

    public int getCancel_type() {
        return cancel_type;
    }

    public void setCancel_type(int cancel_type) {
        this.cancel_type = cancel_type;
    }

    public String getCancel_remark() {
        return cancel_remark;
    }

    public void setCancel_remark(String cancel_remark) {
        this.cancel_remark = cancel_remark;
    }

    public int getCount_down() {
        return count_down;
    }

    public void setCount_down(int count_down) {
        this.count_down = count_down;
    }

    public LogisticsInfoBean getLogistics_info() {
        return logistics_info;
    }

    public void setLogistics_info(LogisticsInfoBean logistics_info) {
        this.logistics_info = logistics_info;
    }

    public long getAuto_receive_countdown() {
        return auto_receive_countdown;
    }

    public void setAuto_receive_countdown(long auto_receive_countdown) {
        this.auto_receive_countdown = auto_receive_countdown;
    }

    public List<OrderGoodsBean> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<OrderGoodsBean> order_goods) {
        this.order_goods = order_goods;
    }

    public static class LogisticsInfoBean {
        /**
         * number : 780098068058-11
         * type : zto
         * list : [{"time":"2018-03-09 11:59:26","status":"【石家庄市】快件已在【长安三部】 签收,期待再次为您服务!"}]
         * deliverystatus : 3
         * updateTime : 2019-08-27 13:56:19
         */

        private String number;
        private String type;
        private int deliverystatus;
        private String updateTime;
        private List<ListBean> list;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getDeliverystatus() {
            return deliverystatus;
        }

        public void setDeliverystatus(int deliverystatus) {
            this.deliverystatus = deliverystatus;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * time : 2018-03-09 11:59:26
             * status : 【石家庄市】快件已在【长安三部】 签收,期待再次为您服务!
             */

            private String time;
            private String status;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }

    public static class OrderGoodsBean {
        /**
         * goods_img_url : http://d.xiyuns.cn/publics/shopimg/goods1.png
         * order_goods_id : 190
         * goods_id : 2
         * order_status : 3
         * goods_name : 测试商品二
         * goods_sku_id : 2
         * sku_name : 黄 M
         * price : 100.00
         * num : 4
         * shipping_money : 0.00
         * refund_status : 0
         * shelf_life : 7
         * sales_return : 2
         */

        private String goods_img_url;
        private int order_goods_id;
        private int goods_id;
        private int order_status;
        private String goods_name;
        private int goods_sku_id;
        private String sku_name;
        private String price;
        private int num;
        private String shipping_money;
        private int refund_status;
        private int shelf_life;
        private int sales_return;

        public String getGoods_img_url() {
            return goods_img_url;
        }

        public void setGoods_img_url(String goods_img_url) {
            this.goods_img_url = goods_img_url;
        }

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

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
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

        public int getShelf_life() {
            return shelf_life;
        }

        public void setShelf_life(int shelf_life) {
            this.shelf_life = shelf_life;
        }

        public int getSales_return() {
            return sales_return;
        }

        public void setSales_return(int sales_return) {
            this.sales_return = sales_return;
        }
    }

    public String getUser_address_id() {
        return user_address_id;
    }

    public void setUser_address_id(String user_address_id) {
        this.user_address_id = user_address_id;
    }
}
