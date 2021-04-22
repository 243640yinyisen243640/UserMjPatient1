package com.lyd.modulemall.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kproduce.roundcorners.RoundImageView;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MyProductCollectListBean;

import java.util.List;

public class MyProductCollectListAdapter extends BaseQuickAdapter<MyProductCollectListBean, BaseViewHolder> {

    public MyProductCollectListAdapter(@Nullable List<MyProductCollectListBean> data) {
        super(R.layout.item_product_collect, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyProductCollectListBean item) {
        String goods_img_url = item.getGoods_img_url();
        String goods_name = item.getGoods_name();
        String price = item.getPrice();
        int state = item.getState();
        RoundImageView imgPic = helper.getView(R.id.img_pic);
        Glide.with(Utils.getApp()).load(goods_img_url).into(imgPic);
        helper.setText(R.id.tv_name, goods_name);
        helper.setText(R.id.tv_price, price);
        //1上架（正常），2下架（失效），3.草稿箱（失效）
        LinearLayout llPrice = helper.getView(R.id.ll_price);
        ImageView imgCollectState = helper.getView(R.id.img_collect_state);
        TextView tvStateLoseEfficacy = helper.getView(R.id.tv_state_lose_efficacy);
        switch (state) {
            case 1:
                llPrice.setVisibility(View.VISIBLE);
                imgCollectState.setVisibility(View.VISIBLE);
                tvStateLoseEfficacy.setVisibility(View.GONE);
                break;
            case 2:
            case 3:
                llPrice.setVisibility(View.GONE);
                imgCollectState.setVisibility(View.GONE);
                tvStateLoseEfficacy.setVisibility(View.VISIBLE);
                break;
        }

        //添加点击事件
        helper.addOnClickListener(R.id.img_collect_state);
    }
}
