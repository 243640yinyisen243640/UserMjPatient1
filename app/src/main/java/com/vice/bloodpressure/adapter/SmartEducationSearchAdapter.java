package com.vice.bloodpressure.adapter;

import android.content.Context;
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
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SmartEducationSearchListBean;
import com.vice.bloodpressure.view.MyListView;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.List;

public class SmartEducationSearchAdapter extends BaseQuickAdapter<SmartEducationSearchListBean, BaseViewHolder> {
    private Context context;

    public SmartEducationSearchAdapter(@Nullable List<SmartEducationSearchListBean> data, Context context) {
        super(R.layout.item_smart_education, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SmartEducationSearchListBean listBean) {
        ImageView imgPic = viewHolder.getView(R.id.img_pic);
        Glide.with(Utils.getApp()).load(listBean.getSeriesimg()).into(imgPic);
        ColorTextView tvLearnState = viewHolder.getView(R.id.tv_learn_state);
        TextView tvNotLearnCount = viewHolder.getView(R.id.tv_not_learn_count);
        tvLearnState.setVisibility(View.GONE);
        tvNotLearnCount.setVisibility(View.GONE);
        viewHolder.setText(R.id.tv_title, listBean.getClassname());
        viewHolder.setText(R.id.tv_desc, listBean.getSeriesintro());
        //列表
        MyListView lvLearnList = viewHolder.getView(R.id.lv_learn_list);
        SmartEducationSearchChildAdapter adapter = new SmartEducationSearchChildAdapter(context, R.layout.item_smart_education_child, listBean.getArtlist());
        lvLearnList.setAdapter(adapter);
        lvLearnList.setTag(0);
        //展开收起
        LinearLayout llUpDown = viewHolder.getView(R.id.ll_up_down);
        ImageView imgArrow = viewHolder.getView(R.id.img_arrow);
        LinearLayout llLearnList = viewHolder.getView(R.id.ll_learn_list);
        llUpDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) lvLearnList.getTag();
                if (0 == tag) {
                    llLearnList.setVisibility(View.VISIBLE);
                    lvLearnList.setTag(1);
                    imgArrow.setImageResource(R.drawable.smart_education_gray_arrow_down);
                } else {
                    lvLearnList.setTag(0);
                    llLearnList.setVisibility(View.GONE);
                    imgArrow.setImageResource(R.drawable.smart_education_gray_arrow_up);
                }
            }
        });
    }
}
