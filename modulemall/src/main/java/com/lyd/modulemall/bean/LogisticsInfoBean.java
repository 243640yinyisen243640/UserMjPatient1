package com.lyd.modulemall.bean;

import java.util.List;

public class LogisticsInfoBean {
    /**
     * number : 4311797092126
     * type : YUNDA
     * list : [{"time":"2021-01-16 18:23:22","status":"【代收点】您的快件已签收，签收人在【金成时代广场3号楼一单元速递易Z65(金成时代广场3号楼一单元大厅南侧Z65)】领取。"},{"time":"2021-01-16 12:41:28","status":"【代收点】您的快件已送达 金成时代广场3号楼一单元速递易Z65 保管，地址：金成时代广场3号楼一单元大厅南侧Z65，请及时领取，如有疑问请电联快递员：孙进奥【18837149866】。"},{"time":"2021-01-16 10:52:47","status":"【郑州市】河南郑州公司鸿海大厦分部 快递员 孙进奥18837149866 正在为您派件【95121为韵达快递员外呼专属号码，请放心接听】"},{"time":"2021-01-15 22:37:24","status":"【郑州市】已离开 河南郑州分拨中心；发往 河南郑州公司鸿海大厦分部"},{"time":"2021-01-15 22:28:29","status":"【郑州市】已到达 河南郑州分拨中心"},{"time":"2021-01-14 23:02:14","status":"【广州市】已离开 广东广州分拨中心；发往 河南郑州分拨中心"},{"time":"2021-01-14 22:58:55","status":"【广州市】已到达 广东广州分拨中心"},{"time":"2021-01-14 18:49:52","status":"【广州市】已离开 广东广州花都区狮岭公司；发往 郑州中区包"},{"time":"2021-01-14 18:17:20","status":"【广州市】广东广州花都区狮岭公司 已揽收"}]
     * deliverystatus : 3
     * issign : 1
     * expName : 韵达快递
     * expSite : www.yundaex.com
     * expPhone : 95546
     * logo : https://img3.fegine.com/express/yd.jpg
     * courier :
     * courierPhone :
     * updateTime : 2021-01-16 18:23:22
     * takeTime : 2天0小时6分
     * order_goods : {"goods_img_url":"http://d.xiyuns.cn/publics/shopimg/goods1.png","goods_name":"系统商品一","goods_total_count":1}
     * receiver_address : 北京北京市朝阳区罗格湖街道上街办事处181号 刘一水收
     */

    private String number;
    private String type;
    private int deliverystatus;
    private String issign;
    private String expName;
    private String expSite;
    private String expPhone;
    private String logo;
    private String courier;
    private String courierPhone;
    private String updateTime;
    private String takeTime;
    private OrderGoodsBean order_goods;
    private String receiver_address;
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

    public String getIssign() {
        return issign;
    }

    public void setIssign(String issign) {
        this.issign = issign;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpSite() {
        return expSite;
    }

    public void setExpSite(String expSite) {
        this.expSite = expSite;
    }

    public String getExpPhone() {
        return expPhone;
    }

    public void setExpPhone(String expPhone) {
        this.expPhone = expPhone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }

    public OrderGoodsBean getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(OrderGoodsBean order_goods) {
        this.order_goods = order_goods;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class OrderGoodsBean {
        /**
         * goods_img_url : http://d.xiyuns.cn/publics/shopimg/goods1.png
         * goods_name : 系统商品一
         * goods_total_count : 1
         */

        private String goods_img_url;
        private String goods_name;
        private int goods_total_count;

        public String getGoods_img_url() {
            return goods_img_url;
        }

        public void setGoods_img_url(String goods_img_url) {
            this.goods_img_url = goods_img_url;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getGoods_total_count() {
            return goods_total_count;
        }

        public void setGoods_total_count(int goods_total_count) {
            this.goods_total_count = goods_total_count;
        }
    }

    public static class ListBean {
        /**
         * time : 2021-01-16 18:23:22
         * status : 【代收点】您的快件已签收，签收人在【金成时代广场3号楼一单元速递易Z65(金成时代广场3号楼一单元大厅南侧Z65)】领取。
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
