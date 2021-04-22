package com.lyd.modulemall.enumuse;

/**
 * 描述:  订单描述
 * 作者: LYD
 * 创建日期: 2021/2/4 15:33
 */
public enum MyRefundOrderDesc {

    // 退款状态 1 买家申请退货卖家等待处理 2.卖家同意（需要退货的发送退货地址） 3.等待卖家收货 4.退款成功 5拒绝退款 6.买家撤销退款
    order_desc_01(1, "买家申请退货卖家等待处理"),
    order_desc_02(2, "卖家同意"),
    order_desc_03(3, "等待卖家收货"),
    order_desc_04(4, "退款成功"),
    order_desc_05(5, "拒绝退款"),
    order_desc_06(6, "买家撤销退款");

    private int order_status;
    private String orderDesc;

    MyRefundOrderDesc(int order_status, String orderDesc) {
        this.order_status = order_status;
        this.orderDesc = orderDesc;
    }

    public static String getOrderDescFromOrderStatus(int order_status) {
        for (MyRefundOrderDesc enums : MyRefundOrderDesc.values()) {
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
