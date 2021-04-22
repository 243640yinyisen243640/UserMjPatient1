package com.vice.bloodpressure.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;

import java.util.List;

public class DietPlanSelectFoodAdapter extends BaseQuickAdapter<DietPlanFoodChildBean, BaseViewHolder> {
    //默认选中位置
    private int selected = -1;
    private com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener;

    public DietPlanSelectFoodAdapter(@Nullable List<DietPlanFoodChildBean> data) {
        super(R.layout.item_diet_plan_select_foods, data);
    }

    public void setOnItemClickListener(com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, DietPlanFoodChildBean bean) {
        int position = holder.getLayoutPosition();
        String pic = bean.getPic();
        String dish_name = bean.getDish_name();
        int hq = bean.getHq();
        holder.setText(R.id.tv_food_name, dish_name);
        holder.setText(R.id.tv_food_kcal, hq + "千卡/100g");
        ImageView img = holder.getView(R.id.img_food);
        Glide.with(Utils.getApp())
                .load(pic)
                .error(R.drawable.default_food_pic)
                .placeholder(R.drawable.default_food_pic)
                .into(img);
        //单选处理
        ImageView imgCheck = holder.getView(R.id.img_check);
        if (selected == position) {
            imgCheck.setImageResource(R.drawable.diet_plan_select_food_checked);
        } else {
            imgCheck.setImageResource(R.drawable.diet_plan_select_food_uncheck);
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }
}
