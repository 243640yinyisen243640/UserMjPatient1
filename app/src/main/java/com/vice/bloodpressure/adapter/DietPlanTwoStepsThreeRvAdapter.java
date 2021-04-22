package com.vice.bloodpressure.adapter;

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

public class DietPlanTwoStepsThreeRvAdapter extends BaseQuickAdapter<DietPlanFoodChildBean, BaseViewHolder> {
    public DietPlanTwoStepsThreeRvAdapter(@Nullable List<DietPlanFoodChildBean> data) {
        super(R.layout.item_diet_plan_select_foods, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, DietPlanFoodChildBean bean) {
        String pic = bean.getPic();
        String dish_name = bean.getDish_name();
        int hq = bean.getHq();
        holder.setText(R.id.tv_food_name, dish_name);
        holder.setText(R.id.tv_food_kcal, hq + "千卡/100g");
        ImageView img = holder.getView(R.id.img_food);
        Glide.with(Utils.getApp()).load(pic)
                .error(R.drawable.default_food_pic)
                .placeholder(R.drawable.default_food_pic)
                .into(img);
        holder.setVisible(R.id.img_check, false);
    }
}
