package com.lyd.modulemall.adapter.orderdetail;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;

public class StateReceiveProductItemProvider extends BaseItemProvider<OrderDetailMultipleEntity, BaseViewHolder> {
    @Override
    public int viewType() {
        return MyOrderDetailAdapter.TYPE_RECEIVE_PRODUCT;
    }

    @Override
    public int layout() {
        return R.layout.item_order_detail_type_receive_product;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, OrderDetailMultipleEntity data, int position) {
        String name = data.getMyModel().getReceiver_name();
        String mobile = data.getMyModel().getReceiver_mobile();
        String address = data.getMyModel().getReceiver_address();
        helper.setText(R.id.tv_type_state_name, name);
        helper.setText(R.id.tv_type_state_phone, mobile);
        helper.setText(R.id.tv_type_state_address, address);
    }
}
