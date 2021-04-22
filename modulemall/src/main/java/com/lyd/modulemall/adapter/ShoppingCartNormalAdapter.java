package com.lyd.modulemall.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.ShoppingCartListBean;
import com.lyd.modulemall.ui.activity.ProductDetailActivity;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import java.util.List;

public class ShoppingCartNormalAdapter extends BaseQuickAdapter<ShoppingCartListBean.NormalCartBean, BaseViewHolder> {
    private CheckHelper mHelper;
    private List<ShoppingCartListBean.NormalCartBean> list;

    public ShoppingCartNormalAdapter(@Nullable List<ShoppingCartListBean.NormalCartBean> list, CheckHelper mHelper) {
        super(R.layout.item_shopping_cart_normal_product, list);
        this.mHelper = mHelper;
        this.list = list;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShoppingCartListBean.NormalCartBean item) {
        String goods_img_url = item.getGoods_img_url();
        String goods_name = item.getGoods_name();
        String sku_name = item.getSku_name();
        String price = item.getPrice();
        int num = item.getNum();
        QMUIRadiusImageView imgProductPic = helper.getView(R.id.img_product_pic);
        Glide.with(Utils.getApp()).load(goods_img_url).into(imgProductPic);
        helper.setText(R.id.tv_product_name, goods_name);
        helper.setText(R.id.tv_product_desc, sku_name);
        helper.setText(R.id.tv_product_price, price);
        helper.setText(R.id.tv_count, num + "");
        helper.addOnClickListener(R.id.tv_minus);
        helper.addOnClickListener(R.id.tv_plus);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int goods_id = item.getGoods_id();
                Intent intent = new Intent(Utils.getApp(), ProductDetailActivity.class);
                intent.putExtra("goods_id", goods_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //CheckHelper和Adapter绑定
        mHelper.bind(list.get(position), holder, holder.getView(R.id.img_check));
    }
}
