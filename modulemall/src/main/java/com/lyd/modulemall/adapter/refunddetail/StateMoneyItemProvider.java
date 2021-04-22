package com.lyd.modulemall.adapter.refunddetail;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;

public class StateMoneyItemProvider extends BaseItemProvider<RefundDetailMultipleEntity, BaseViewHolder> {
    @Override
    public int viewType() {
        return RefundDetailAdapter.TYPE_MONEY;
    }

    @Override
    public int layout() {
        return R.layout.item_refund_detail_money;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, RefundDetailMultipleEntity data, int position) {
        String refund_real_money = data.getMyModel().getRefund_real_money();
        helper.setText(R.id.tv_refund_money, refund_real_money);
    }
}
