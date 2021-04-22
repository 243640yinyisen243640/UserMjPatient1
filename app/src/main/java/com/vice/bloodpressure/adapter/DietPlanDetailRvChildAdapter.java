package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.os.Bundle;
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
import com.vice.bloodpressure.ui.activity.smartdiet.RecipeDetailActivity;

import java.util.List;

public class DietPlanDetailRvChildAdapter extends BaseQuickAdapter<DietPlanFoodChildBean, BaseViewHolder> {
    private int dietPlanId;
    private String day;
    private String type;

    public DietPlanDetailRvChildAdapter(@Nullable List<DietPlanFoodChildBean> data, int dietPlanId, String day, String type) {
        super(R.layout.item_rv_item_diet_plan_detail, data);
        this.dietPlanId = dietPlanId;
        this.day = day;
        this.type = type;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, DietPlanFoodChildBean bean) {
        String name = bean.getDish_name();
        String str = bean.getStr();
        holder.setText(R.id.tv_food_name, name);
        holder.setText(R.id.tv_food_side_dish, str);
        ImageView imgPic = holder.getView(R.id.img_list);
        String pic = bean.getPic();
        Glide.with(Utils.getApp()).load(pic)
                .error(R.drawable.default_food_pic)
                .placeholder(R.drawable.default_food_pic)
                .into(imgPic);
        int greensid = bean.getGreensid();
        int position = holder.getLayoutPosition();

        //跳转菜谱详情
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), RecipeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("day", day);
                bundle.putInt("dietPlanId", dietPlanId);
                bundle.putString("type", type);
                bundle.putInt("index", position);
                bundle.putInt("greensid", greensid);
                bundle.putString("str", str);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
