package com.lyd.modulemall.adapter.orderdetail;

import com.lyd.modulemall.bean.MyOrderDetailBean;

public class OrderDetailMultipleEntity {

    public static final int STATE_YES = 1;
    public static final int STATE_NO = 2;
    public static final int LOGISTICS = 3;
    public static final int RECEIVE_PRODUCT = 4;
    public static final int PRODUCT_DESC = 5;
    public static final int ORDER_DETAIL = 6;

    public int type;
    private MyOrderDetailBean myModel;


    public OrderDetailMultipleEntity(int type, MyOrderDetailBean myModel) {
        this.type = type;
        this.myModel = myModel;
    }

    public MyOrderDetailBean getMyModel() {
        return myModel;
    }

    public void setMyModel(MyOrderDetailBean myModel) {
        this.myModel = myModel;
    }
}
