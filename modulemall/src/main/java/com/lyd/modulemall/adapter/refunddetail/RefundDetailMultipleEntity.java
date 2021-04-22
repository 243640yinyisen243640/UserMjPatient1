package com.lyd.modulemall.adapter.refunddetail;

import com.lyd.modulemall.bean.RefundDetailBean;

public class RefundDetailMultipleEntity {
    //除了四个红点
    public static final int STATE_ONE = 1;
    //四个红点
    public static final int STATE_TWO = 2;
    //退款金额
    public static final int MONEY = 3;
    //收货地址
    public static final int RECEIVE_PRODUCT = 4;
    //商品详情(退款信息)
    public static final int PRODUCT_DETAIL = 5;


    public int type;
    private RefundDetailBean myModel;


    public RefundDetailMultipleEntity(int type, RefundDetailBean myModel) {
        this.type = type;
        this.myModel = myModel;
    }

    public RefundDetailBean getMyModel() {
        return myModel;
    }

    public void setMyModel(RefundDetailBean myModel) {
        this.myModel = myModel;
    }
}
