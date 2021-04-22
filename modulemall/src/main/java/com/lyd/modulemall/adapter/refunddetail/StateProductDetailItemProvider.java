package com.lyd.modulemall.adapter.refunddetail;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.lyd.modulemall.R;

public class StateProductDetailItemProvider extends BaseItemProvider<RefundDetailMultipleEntity, BaseViewHolder> {
    @Override
    public int viewType() {
        return RefundDetailAdapter.TYPE_PRODUCT_DETAIL;
    }

    @Override
    public int layout() {
        return R.layout.item_refund_detail_product_detail;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, RefundDetailMultipleEntity data, int position) {
        String goods_img_url = data.getMyModel().getGoods_img_url();
        String goods_name = data.getMyModel().getGoods_name();
        String sku_name = data.getMyModel().getSku_name();
        String action = data.getMyModel().getAction();
        String refund_real_money = data.getMyModel().getRefund_real_money();
        ImageView imgPic = helper.getView(R.id.img_pic);
        Glide.with(Utils.getApp()).load(goods_img_url).into(imgPic);
        helper.setText(R.id.tv_product_name, goods_name);
        helper.setText(R.id.tv_specification, sku_name);
        helper.setText(R.id.tv_refund_reason, action);
        helper.setText(R.id.tv_refund_money, refund_real_money);
    }
}
