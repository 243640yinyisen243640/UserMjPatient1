package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SmartEducationLearnListBean;
import com.vice.bloodpressure.view.MyListView;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.List;

public class SmartEducationLearnListAdapter extends BaseQuickAdapter<SmartEducationLearnListBean.ListBean, BaseViewHolder> {
    private Context context;

    public SmartEducationLearnListAdapter(@Nullable List<SmartEducationLearnListBean.ListBean> data, Context context) {
        super(R.layout.item_smart_education, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SmartEducationLearnListBean.ListBean listBean) {
        ImageView imgPic = viewHolder.getView(R.id.img_pic);
        Glide.with(Utils.getApp()).load(listBean.getSeriesimg()).into(imgPic);
        //"learning": 1,//学习中
        //"unlearning": 3,//未学
        int learning = listBean.getLearning();
        ColorTextView tvLearnState = viewHolder.getView(R.id.tv_learn_state);
        TextView tvNotLearnCount = viewHolder.getView(R.id.tv_not_learn_count);
        if (1 == learning) {
            tvLearnState.setVisibility(View.VISIBLE);
            tvLearnState.setText("学习中");
            tvLearnState.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
            tvLearnState.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
            tvLearnState.getColorHelper().setBorderWidthNormal(0);
            tvNotLearnCount.setVisibility(View.VISIBLE);
            tvNotLearnCount.setText("还有" + listBean.getUnlearning() + "节课未学完");
        } else {
            tvLearnState.setVisibility(View.GONE);
            tvLearnState.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
            tvLearnState.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.white_text));
            tvLearnState.getColorHelper().setBorderWidthNormal(1);
            tvLearnState.getColorHelper().setBorderColorNormal(ColorUtils.getColor(R.color.gray_text));
            tvNotLearnCount.setVisibility(View.INVISIBLE);
        }
        viewHolder.setText(R.id.tv_title, listBean.getClassname());
        viewHolder.setText(R.id.tv_desc, listBean.getSeriesintro());
        //列表
        MyListView lvLearnList = viewHolder.getView(R.id.lv_learn_list);
        SmartEducationLearnListChildAdapter adapter = new SmartEducationLearnListChildAdapter(context, R.layout.item_smart_education_child, listBean.getClasses(),learning);
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
