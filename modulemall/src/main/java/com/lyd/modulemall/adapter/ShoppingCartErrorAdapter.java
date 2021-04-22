package com.lyd.modulemall.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.ShoppingCartListBean;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.List;

public class ShoppingCartErrorAdapter extends BaseQuickAdapter<ShoppingCartListBean.NormalCartBean, BaseViewHolder> {

    public ShoppingCartErrorAdapter(@Nullable List<ShoppingCartListBean.NormalCartBean> data) {
        super(R.layout.item_shopping_cart_error_product, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShoppingCartListBean.NormalCartBean item) {
        String goods_img_url = item.getGoods_img_url();
        String goods_name = item.getGoods_name();
        QMUIRadiusImageView imgProductPic = helper.getView(R.id.img_product_pic);
        Glide.with(Utils.getApp()).load(goods_img_url).into(imgProductPic);
        helper.setText(R.id.tv_product_name, goods_name);
    }
}
