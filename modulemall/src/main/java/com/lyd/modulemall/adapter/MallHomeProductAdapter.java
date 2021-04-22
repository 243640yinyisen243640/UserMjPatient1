package com.lyd.modulemall.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kproduce.roundcorners.RoundImageView;
import com.kproduce.roundcorners.RoundLinearLayout;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.MallHomeProductBean;
import com.lyd.modulemall.ui.activity.ProductDetailActivity;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 首页商品
 * 作者: LYD
 * 创建日期: 2021/1/22 16:15
 */
public class MallHomeProductAdapter extends BaseQuickAdapter<MallHomeProductBean, BaseViewHolder> {
    public MallHomeProductAdapter(@Nullable List<MallHomeProductBean> data) {
        super(R.layout.item_mall_home_product, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallHomeProductBean item) {
        int screenWidth = ScreenUtils.getScreenWidth();
        int paddingWidth = SizeUtils.dp2px(2 * 5 + 2 * 5 + 2 * 5);
        int totalWidth = screenWidth - paddingWidth;
        int width = totalWidth / 2;

        RoundImageView imgGood = helper.getView(R.id.img_good);
        LinearLayout.LayoutParams paramGood = (LinearLayout.LayoutParams) imgGood.getLayoutParams();
        paramGood.width = width;
        paramGood.height = width;
        imgGood.setLayoutParams(paramGood);

        RoundLinearLayout llDesc = helper.getView(R.id.ll_desc);
        LinearLayout.LayoutParams paramLL = (LinearLayout.LayoutParams) llDesc.getLayoutParams();
        paramLL.width = width;
        //paramLL.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        llDesc.setLayoutParams(paramLL);


        String goods_img_url = item.getGoods_img_url();
        String goods_name = item.getGoods_name();
        List<String> goods_label_ids = item.getGoods_label_ids();
        String price = item.getPrice();
        String market_price = item.getMarket_price();
        int show_sales = item.getShow_sales();
        //RoundImageView imgGood = helper.getView(R.id.img_good);
        Glide.with(Utils.getApp()).load(goods_img_url).into(imgGood);
        helper.setText(R.id.tv_name, goods_name);
        //设置标签 开始
        ColorTextView tvLabelOne = helper.getView(R.id.tv_label_one);
        ColorTextView tvLabelTwo = helper.getView(R.id.tv_label_two);
        ColorTextView tvLabelThree = helper.getView(R.id.tv_label_three);
        List<ColorTextView> listTv = new ArrayList<>();
        listTv.add(tvLabelOne);
        listTv.add(tvLabelTwo);
        listTv.add(tvLabelThree);
        if (goods_label_ids != null && goods_label_ids.size() > 0) {
            int size = goods_label_ids.size();
            switch (size) {
                case 1:
                    tvLabelOne.setVisibility(View.VISIBLE);
                    tvLabelTwo.setVisibility(View.GONE);
                    tvLabelThree.setVisibility(View.GONE);
                    break;
                case 2:
                    tvLabelOne.setVisibility(View.VISIBLE);
                    tvLabelTwo.setVisibility(View.VISIBLE);
                    tvLabelThree.setVisibility(View.GONE);
                    break;
                case 3:
                    tvLabelOne.setVisibility(View.VISIBLE);
                    tvLabelTwo.setVisibility(View.VISIBLE);
                    tvLabelThree.setVisibility(View.VISIBLE);
                    break;
            }
            for (int i = 0; i < goods_label_ids.size(); i++) {
                listTv.get(i).setText(goods_label_ids.get(i));
            }
        }
        //设置标签 结束
        helper.setText(R.id.tv_price, "￥" + price);
        TextView tvMarketPrice = helper.getView(R.id.tv_market_price);
        tvMarketPrice.setText("￥" + market_price);
        tvMarketPrice.setPaintFlags(tvMarketPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.tv_sale_count, "已售" + show_sales);
        //商品点击到详情
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
}
