package com.lyd.modulemall.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.ConfirmOrderBean;

import java.util.List;

public class ConfirmOrderProductListAdapter extends BaseQuickAdapter<ConfirmOrderBean.GoodsDetailBean, BaseViewHolder> {
    public ConfirmOrderProductListAdapter(@Nullable List<ConfirmOrderBean.GoodsDetailBean> data) {
        super(R.layout.item_confirm_order_product_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ConfirmOrderBean.GoodsDetailBean item) {
        String goods_img_url = item.getGoods_img_url();
        String goods_name = item.getGoods_name();
        String price = item.getPrice();
        int num = item.getNum();
        String sku_name = item.getSku_name();
        ImageView imgPic = helper.getView(R.id.img_pic);
        Glide.with(Utils.getApp()).load(goods_img_url).into(imgPic);
        helper.setText(R.id.tv_name, goods_name);
        helper.setText(R.id.tv_price, price);
        helper.setText(R.id.tv_count, "X" + num);
        helper.setText(R.id.tv_sku_name, sku_name);
    }
}
