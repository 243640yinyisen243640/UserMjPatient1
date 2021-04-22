package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.ui.activity.food.FoodDetailActivity;
import com.vice.bloodpressure.ui.activity.smartdiet.RecipeDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/12/19 10:13
 */
public class DietChildAdapter extends CommonAdapter<FoodsCategoryListBean> {
    private String type;

    public DietChildAdapter(Context context, int layoutId, List<FoodsCategoryListBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, FoodsCategoryListBean item, int position) {
        ImageView imgPic = viewHolder.getView(R.id.img_pic);
        TextView tvKcal = viewHolder.getView(R.id.tv_kcal);

        if ("two".equals(type)) {
            String picurl = item.getPicurl();
            String foodname = item.getFoodname();
            String explain = item.getExplain();
            String kcalval = item.getKcalval();

            Glide.with(Utils.getApp())
                    .load(picurl)
                    .error(R.drawable.food_material_default)
                    .placeholder(R.drawable.food_material_default)
                    .into(imgPic);
            String weight = String.format(Utils.getApp().getString(R.string.food_material_weight), explain);
            String kcal = String.format(Utils.getApp().getString(R.string.food_material_kcal), kcalval);
            viewHolder.setText(R.id.tv_name, foodname);
            viewHolder.setText(R.id.tv_weight, weight);
            tvKcal.setVisibility(View.VISIBLE);
            tvKcal.setText(kcal);
            //点击跳转详情
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Utils.getApp(), FoodDetailActivity.class);
                    intent.putExtra("ID", item.getId());
                    intent.putExtra("TITLE", item.getFoodname());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            });
        } else {
            String pic = item.getPic();
            String dish_name = item.getDish_name();
            //重量
            String grams_total = item.getGrams_total();
            //热量
            String hq_toal = item.getHq_toal();
            Glide.with(Utils.getApp())
                    .load(pic)
                    .error(R.drawable.green_default)
                    .placeholder(R.drawable.green_default)
                    .into(imgPic);
            viewHolder.setText(R.id.tv_name, dish_name);
            String weightAndKcal = String.format(Utils.getApp().getString(R.string.green_weight_kcal), grams_total, hq_toal);
            viewHolder.setText(R.id.tv_weight, weightAndKcal);
            tvKcal.setVisibility(View.GONE);
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Utils.getApp(), RecipeDetailActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            });
        }

    }
}
