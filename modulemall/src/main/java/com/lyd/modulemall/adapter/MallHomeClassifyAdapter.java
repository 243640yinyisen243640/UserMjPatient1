package com.lyd.modulemall.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MallHomeIndexBean;
import com.lyd.modulemall.ui.activity.productlist.MallProductListActivity;

import java.util.List;

public class MallHomeClassifyAdapter extends BaseQuickAdapter<MallHomeIndexBean.CategoryListBean, BaseViewHolder> {
    public MallHomeClassifyAdapter(@Nullable List<MallHomeIndexBean.CategoryListBean> data) {
        super(R.layout.item_mall_classify, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallHomeIndexBean.CategoryListBean item) {
        LinearLayout llRoot = helper.getView(R.id.ll_classify_root);
        int screenWidth = ScreenUtils.getScreenWidth();
        int width = screenWidth / 5;
        ViewGroup.LayoutParams params = llRoot.getLayoutParams();
        params.width = width;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        llRoot.setLayoutParams(params);

        ImageView imgPic = helper.getView(R.id.img_classify_pic);
        String category_image = item.getCategory_image();
        Glide.with(Utils.getApp()).load(category_image).into(imgPic);
        String category_name = item.getCategory_name();
        helper.setText(R.id.tv_classify_text, category_name);
        //分类点击
        int category_id = item.getCategory_id();

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //活动
                Intent intent = new Intent(Utils.getApp(), MallProductListActivity.class);
                intent.putExtra("category_id", category_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
