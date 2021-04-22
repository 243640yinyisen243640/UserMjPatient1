package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.AdviceBean;
import com.vice.bloodpressure.ui.activity.food.FoodAdvActivity;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

/**
 * 描述:1：患教 2：群发消息
 * 作者: LYD
 * 创建日期: 2019/6/21 15:16
 */
public class DelegateAdvice2 implements ItemViewDelegate<AdviceBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_advice_mass_msg;
    }

    @Override
    public boolean isForViewType(AdviceBean item, int position) {
        return item.isOperate();
    }

    @Override
    public void convert(ViewHolder holder, AdviceBean adviceBean, int position) {
        ImageView img = holder.getView(R.id.img_advice_mass_msg);
        Glide.with(Utils.getApp())
                .load(adviceBean.getImage())
                .error(R.drawable.banner)
                .placeholder(R.drawable.banner)
                .into(img);
        holder.setText(R.id.tv_title, adviceBean.getTitle());
        holder.setText(R.id.tv_time, adviceBean.getCreattime());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), FoodAdvActivity.class);
                intent.putExtra("TITLE", adviceBean.getTitle());
                intent.putExtra("CONTENT", adviceBean.getContent());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
