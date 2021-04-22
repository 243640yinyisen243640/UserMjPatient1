package com.lyd.modulemall.enumuse;

/**
 * 描述:  订单描述
 * 作者: LYD
 * 创建日期: 2021/2/4 15:33
 */
public enum MyOrderDesc {
    //订单状态 1待付款 2待发货 3待收货 5已完成 6已取消 （交易关闭）
    order_desc_01(1, "待付款"),
    order_desc_02(2, "已付款"),
    order_desc_03(3, "已发货"),
    order_desc_05(5, "交易完成"),
    order_desc_06(6, "交易关闭");

    private int order_status;
    private String orderDesc;

    MyOrderDesc(int order_status, String orderDesc) {
        this.order_status = order_status;
        this.orderDesc = orderDesc;
    }

    public static String getOrderDescFromOrderStatus(int order_status) {
        for (MyOrderDesc enums : MyOrderDesc.values()) {
            if (order_status == enums.getOrder_status()) {
                return enums.getOrderDesc();
            }
        }
        return "";
    }

    public int getOrder_status() {
        return order_status;
    }

    public String getOrderDesc() {
        return orderDesc;
    }
}
