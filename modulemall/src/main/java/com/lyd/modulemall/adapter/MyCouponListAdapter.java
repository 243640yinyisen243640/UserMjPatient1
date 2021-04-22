package com.lyd.modulemall.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MyCouponListBean;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.List;

public class MyCouponListAdapter extends BaseQuickAdapter<MyCouponListBean, BaseViewHolder> {
    private String activity_id;

    public MyCouponListAdapter(@Nullable List<MyCouponListBean> data, String activity_id) {
        super(R.layout.item_mall_coupon, data);
        this.activity_id = activity_id;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyCouponListBean item) {
        String coupon_money = item.getCoupon_money();
        String at_least = item.getAt_least();
        String coupon_name = item.getCoupon_name();
        String end_time = item.getEnd_time();
        //是否能领取 1可领取 2不可领取（数量不足）3不可领取（已领取未使用） 4不可领取（已领取已使用）
        int is_get = item.getIs_get();
        //是否使用 0已过期 1已使用 2未使用
        int is_use = item.getIs_use();
        ColorButton btCouponToGet = helper.getView(R.id.bt_coupon_to_get);
        ColorButton btCouponHaveGet = helper.getView(R.id.bt_coupon_have_get);
        ImageView imgHaveUsed = helper.getView(R.id.img_coupon_have_used);
        helper.setText(R.id.tv_coupon_value, coupon_money);
        helper.setText(R.id.tv_coupon_desc, "满" + at_least + "可用");
        helper.setText(R.id.tv_coupon_text, coupon_name);
        helper.setText(R.id.tv_coupon_time, "有效期至 " + end_time);
        if ("-1".equals(activity_id)){
            switch (is_use) {
                case 0:
                    btCouponToGet.setVisibility(View.GONE);
                    btCouponHaveGet.setVisibility(View.GONE);
                    imgHaveUsed.setImageResource(R.drawable.mall_coupon_have_over_date);
                    imgHaveUsed.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    btCouponToGet.setVisibility(View.GONE);
                    btCouponHaveGet.setVisibility(View.GONE);
                    imgHaveUsed.setImageResource(R.drawable.mall_coupon_have_used);
                    imgHaveUsed.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    btCouponToGet.setVisibility(View.GONE);
                    btCouponHaveGet.setVisibility(View.VISIBLE);
                    imgHaveUsed.setVisibility(View.GONE);
                    break;

            }
        }else{
            switch (is_get) {
                case 1:
                    btCouponToGet.setVisibility(View.VISIBLE);
                    btCouponHaveGet.setVisibility(View.GONE);
                    imgHaveUsed.setVisibility(View.GONE);
                    break;
                case 3:
                    btCouponToGet.setVisibility(View.GONE);
                    btCouponHaveGet.setVisibility(View.VISIBLE);
                    imgHaveUsed.setVisibility(View.GONE);
                    break;
                case 4:
                    btCouponToGet.setVisibility(View.GONE);
                    btCouponHaveGet.setVisibility(View.GONE);
                    imgHaveUsed.setImageResource(R.drawable.mall_coupon_have_used);
                    imgHaveUsed.setVisibility(View.VISIBLE);
                    break;
            }
        }

        //添加点击事件
        helper.addOnClickListener(R.id.bt_coupon_to_get);
    }
}
