package com.lyd.modulemall.adapter.orderdetail;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;

public class StateNoItemProvider extends BaseItemProvider<OrderDetailMultipleEntity, BaseViewHolder> {
    @Override
    public int viewType() {
        return MyOrderDetailAdapter.TYPE_STATE_NO;
    }

    @Override
    public int layout() {
        return R.layout.item_order_detail_type_state_no;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, OrderDetailMultipleEntity data, int position) {
        //取消类型 1 买家取消 2 卖家取消 3 系统取消
        int cancel_type = data.getMyModel().getCancel_type();
        switch (cancel_type) {
            case 1:
                helper.setText(R.id.tv_type_state_cancel_type, "关闭类型:" + "买家取消");
                break;
            case 2:
                helper.setText(R.id.tv_type_state_cancel_type, "关闭类型:" + "卖家取消");
                break;
            case 3:
                helper.setText(R.id.tv_type_state_cancel_type, "关闭类型:" + "系统取消");
                break;
        }
        String cancel_remark = data.getMyModel().getCancel_remark();
        helper.setText(R.id.tv_type_state_cancel_desc, "原因:" + cancel_remark);
    }
}
