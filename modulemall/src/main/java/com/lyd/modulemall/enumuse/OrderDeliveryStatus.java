package com.lyd.modulemall.enumuse;

public enum  OrderDeliveryStatus {
    order_desc_00(0, "快递收件(揽件)"),
    order_desc_01(1, "在途中"),
    order_desc_02(2, "正在派件"),
    order_desc_03(3, "已签收"),
    order_desc_04(4, "派送失败"),
    order_desc_05(5, "疑难件"),
    order_desc_06(6, "退件签收");

    private int order_status;
    private String orderDesc;

    OrderDeliveryStatus(int order_status, String orderDesc) {
        this.order_status = order_status;
        this.orderDesc = orderDesc;
    }

    public static String getOrderDescFromOrderStatus(int order_status) {
        for (OrderDeliveryStatus enums : OrderDeliveryStatus.values()) {
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
