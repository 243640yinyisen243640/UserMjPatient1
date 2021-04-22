package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;
import com.vice.bloodpressure.ui.activity.food.FoodDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class FoodHomeSecondAdapter extends CommonAdapter<FoodsCategoryListBean> {
    public FoodHomeSecondAdapter(Context context, int layoutId, List<FoodsCategoryListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FoodsCategoryListBean item, int position) {
        String explain = item.getExplain();
        String kcalval = item.getKcalval();
        String weight = String.format(Utils.getApp().getString(R.string.food_material_weight), explain);
        String kcal = String.format(Utils.getApp().getString(R.string.food_material_kcal), kcalval);
        viewHolder.setText(R.id.tv_kcal, kcal);
        viewHolder.setText(R.id.tv_weight, weight);
        viewHolder.setText(R.id.tv_name, item.getFoodname());
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
    }
}
