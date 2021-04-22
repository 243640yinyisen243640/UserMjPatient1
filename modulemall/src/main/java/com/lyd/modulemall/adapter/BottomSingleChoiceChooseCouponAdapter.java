package com.lyd.modulemall.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.ConfirmOrderBean;

import java.util.List;

public class BottomSingleChoiceChooseCouponAdapter extends BaseQuickAdapter<ConfirmOrderBean.CouponListBean, BaseViewHolder> {
    private CheckHelper mHelper;
    private List<ConfirmOrderBean.CouponListBean> list;

    public BottomSingleChoiceChooseCouponAdapter(@Nullable List<ConfirmOrderBean.CouponListBean> list, CheckHelper mHelper) {
        super(R.layout.item_popup_bottom_single_choice, list);
        this.list = list;
        this.mHelper = mHelper;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ConfirmOrderBean.CouponListBean item) {
        String coupon_money = item.getCoupon_money();
        String coupon_name = item.getCoupon_name();
        helper.setText(R.id.tv_content, "省" + coupon_money + "元:  " + coupon_name);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //CheckHelper和Adapter绑定
        mHelper.bind(list.get(position), holder, holder.itemView);
    }
}
