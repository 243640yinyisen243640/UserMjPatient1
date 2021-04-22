package com.lyd.modulemall.adapter.refunddetail;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;
import com.lyd.modulemall.ui.activity.refund.WriteRefundLogisticsActivity;

public class StateRefundDetailReceiveProductItemProvider extends BaseItemProvider<RefundDetailMultipleEntity, BaseViewHolder> {
    @Override
    public int viewType() {
        return RefundDetailAdapter.TYPE_RECEIVE_PRODUCT;
    }

    @Override
    public int layout() {
        return R.layout.item_refund_detail_receive_product;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, RefundDetailMultipleEntity data, int position) {
        int order_goods_id = data.getMyModel().getOrder_goods_id();
        String name = data.getMyModel().getSeller_refund_name();
        String mobile = data.getMyModel().getSeller_refund_mobile();
        String address = data.getMyModel().getSeller_refund_address();
        helper.setText(R.id.tv_type_state_name, name);
        helper.setText(R.id.tv_type_state_phone, mobile);
        helper.setText(R.id.tv_type_state_address, address);
        //跳转填写物流
        helper.getView(R.id.rl_add_logistics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), WriteRefundLogisticsActivity.class);
                intent.putExtra("order_goods_id", order_goods_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
