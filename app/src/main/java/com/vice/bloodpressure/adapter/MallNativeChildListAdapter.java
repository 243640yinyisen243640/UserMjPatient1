package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kproduce.roundcorners.RoundImageView;
import com.kproduce.roundcorners.RoundLinearLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.MallNativeChildBean;

import java.util.List;

public class MallNativeChildListAdapter extends BaseQuickAdapter<MallNativeChildBean.ListBean, BaseViewHolder> {
    public MallNativeChildListAdapter(@Nullable List<MallNativeChildBean.ListBean> data) {
        super(R.layout.item_mall_native_child, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallNativeChildBean.ListBean item) {
        int screenWidth = ScreenUtils.getScreenWidth();
        int paddingWidth = SizeUtils.dp2px(2 * 10 + 1 * 10);
        int totalWidth = screenWidth - paddingWidth;
        int width = totalWidth / 2;
        //计算离顶部Margin
        int strokeWidth = SizeUtils.dp2px(3);
        int topMargin = width - strokeWidth;


        RoundImageView imgGood = helper.getView(R.id.img_good);
        ViewGroup.LayoutParams paramGood = imgGood.getLayoutParams();
        paramGood.width = width;
        paramGood.height = width;
        imgGood.setLayoutParams(paramGood);

        RoundLinearLayout llDesc = helper.getView(R.id.ll_desc);
        FrameLayout.LayoutParams paramLL = (FrameLayout.LayoutParams) llDesc.getLayoutParams();
        paramLL.width = width;
        paramLL.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        paramLL.setMargins(0, topMargin, 0, 0);
        llDesc.setLayoutParams(paramLL);

        Glide.with(Utils.getApp()).load(item.getImgurl()).into(imgGood);
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_lable, "人气热卖")
                .setText(R.id.tv_desc, "简介")
                .setText(R.id.tv_sale_count, "已售" + item.getSales());
        //设置价格
        String price = "¥" + item.getPrice();
        String original = "¥" + item.getOriginal();
        TextView tvPrice = helper.getView(R.id.tv_price);
        SpanUtils.with(tvPrice)
                .append(price).setForegroundColor(ColorUtils.getColor(R.color.red_bright)).setFontSize(13, true)
                .appendSpace(15, Color.TRANSPARENT)
                .append(original).setForegroundColor(ColorUtils.getColor(R.color.gray_text)).setFontSize(11, true).setStrikethrough()
                .create();

        //点击
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                intent.putExtra("title", item.getName());
                intent.putExtra("url", item.getGid() + "/ishidden/1");
                intent.putExtra("goodsDetail", "goodsDetail");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
