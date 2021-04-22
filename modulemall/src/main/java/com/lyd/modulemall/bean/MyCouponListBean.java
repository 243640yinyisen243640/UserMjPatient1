package com.lyd.modulemall.bean;

public class MyCouponListBean {
    /**
     * coupon_id : 2
     * coupon_name : 满减二
     * coupon_money : 10
     * count : 100
     * remain_count : 100
     * at_least : 100
     * is_repetition : 2
     * goods_ids : 3,4,5
     * start_time : 2020-12-30 11:03:18
     * end_time : 2020-12-30 11:03:18
     * is_get : 1
     */

    private int coupon_id;
    private String coupon_name;
    private String coupon_money;
    private int count;
    private int remain_count;
    private String at_least;
    private int is_repetition;
    private String goods_ids;
    private String start_time;
    private String end_time;
    private int is_get;
    //我的优惠券
    private int couon_user_id;
    private int user_id;
    private int is_use;

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_money() {
        return coupon_money;
    }

    public void setCoupon_money(String coupon_money) {
        this.coupon_money = coupon_money;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRemain_count() {
        return remain_count;
    }

    public void setRemain_count(int remain_count) {
        this.remain_count = remain_count;
    }

    public String getAt_least() {
        return at_least;
    }

    public void setAt_least(String at_least) {
        this.at_least = at_least;
    }

    public int getIs_repetition() {
        return is_repetition;
    }

    public void setIs_repetition(int is_repetition) {
        this.is_repetition = is_repetition;
    }

    public String getGoods_ids() {
        return goods_ids;
    }

    public void setGoods_ids(String goods_ids) {
        this.goods_ids = goods_ids;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getIs_get() {
        return is_get;
    }

    public void setIs_get(int is_get) {
        this.is_get = is_get;
    }

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

    public int getIs_use() {
        return is_use;
    }

    public void setIs_use(int is_use) {
        this.is_use = is_use;
    }
}
