package com.lyd.modulemall.adapter.orderdetail;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MyOrderDetailBean;
import com.lyd.modulemall.enumuse.OrderDeliveryStatus;

public class StateLogisticsItemProvider extends BaseItemProvider<OrderDetailMultipleEntity, BaseViewHolder> {
    @Override
    public int viewType() {
        return MyOrderDetailAdapter.TYPE_LOGISTICS;
    }

    @Override
    public int layout() {
        return R.layout.item_order_detail_type_logistics;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, OrderDetailMultipleEntity data, int position) {
        MyOrderDetailBean.LogisticsInfoBean logistics_info = data.getMyModel().getLogistics_info();
        if (logistics_info != null) {
            int deliverystatus = logistics_info.getDeliverystatus();
            String orderDescFromOrderStatus = OrderDeliveryStatus.getOrderDescFromOrderStatus(deliverystatus);
            helper.setText(R.id.tv_type_state_text, orderDescFromOrderStatus);
            if (data.getMyModel().getLogistics_info().getList() != null && data.getMyModel().getLogistics_info().getList().size() > 0) {
                helper.setText(R.id.tv_type_state_logistics_info, data.getMyModel().getLogistics_info().getList().get(0).getStatus());
            }
        } else {
            helper.setText(R.id.tv_type_state_text, "物流信息");
            helper.setText(R.id.tv_type_state_logistics_info, "包裹揽收中...");
        }
    }
}
