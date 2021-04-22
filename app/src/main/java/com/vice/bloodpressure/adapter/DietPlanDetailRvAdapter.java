package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;

import java.util.List;

public class DietPlanDetailRvAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    private List<DietPlanFoodChildBean> breakfast;
    private List<DietPlanFoodChildBean> lunch;
    private List<DietPlanFoodChildBean> dinner;
    private int dietPlanId;
    private String day;

    public DietPlanDetailRvAdapter(@Nullable List<String> data,
                                   int dietPlanId, String day,
                                   List<DietPlanFoodChildBean> breakfast, List<DietPlanFoodChildBean> lunch, List<DietPlanFoodChildBean> dinner,
                                   Context context) {
        super(R.layout.item_rv_diet_plan_detail, data);
        this.context = context;

        this.dietPlanId = dietPlanId;
        this.day = day;

        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String s) {
        int position = holder.getLayoutPosition();
        String[] listString = Utils.getApp().getResources().getStringArray(R.array.diet_plan_detail_meals_text);
        holder.setText(R.id.tv_meals, listString[position]);
        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.diet_plan_detail_meals_img);
        holder.setImageResource(R.id.img_meals, imgArray.getResourceId(position, 0));

        //设置RV
        RecyclerView rv = holder.getView(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(context));
        switch (position) {
            case 0:
                rv.setAdapter(new DietPlanDetailRvChildAdapter(breakfast, dietPlanId, day, "1"));
                break;
            case 1:
                rv.setAdapter(new DietPlanDetailRvChildAdapter(lunch, dietPlanId, day, "2"));
                break;
            case 2:
                rv.setAdapter(new DietPlanDetailRvChildAdapter(dinner, dietPlanId, day, "3"));
                break;
        }
        //添加点击事件
        holder.addOnClickListener(R.id.img_refresh);
    }
}
