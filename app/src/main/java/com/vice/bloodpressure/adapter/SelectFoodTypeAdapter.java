package com.vice.bloodpressure.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FoodsCategoryListBean;

import java.util.List;

public class SelectFoodTypeAdapter extends BaseQuickAdapter<FoodsCategoryListBean, BaseViewHolder> {
    private CheckHelper mHelper;
    List<FoodsCategoryListBean> list;

    public SelectFoodTypeAdapter(@Nullable List<FoodsCategoryListBean> data, CheckHelper mHelper) {
        super(R.layout.item_food_type_layout, data);
        this.mHelper = mHelper;
        this.list = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FoodsCategoryListBean item) {
        ImageView imageView = helper.getView(R.id.img_pic);
        switch (item.getClassify()) {
            case 1:
                imageView.setImageResource(R.drawable.gushuzalianggreen);
                break;
            case 2:
                imageView.setImageResource(R.drawable.rougreen);
                break;
            case 3:
                imageView.setImageResource(R.drawable.shucainew);
                break;
            case 4:
                imageView.setImageResource(R.drawable.niunaigreen);
                break;
            case 5:
                imageView.setImageResource(R.drawable.shuiguogreen);
                break;
            case 6:
                imageView.setImageResource(R.drawable.zhifanggreen);
                break;
            case 7:
                imageView.setImageResource(R.drawable.douleigreen);
                break;
            case 8:
                imageView.setImageResource(R.drawable.danleinew);
                break;
            case 9:
                imageView.setImageResource(R.drawable.haixiannew);
                break;
            case 10:
                imageView.setImageResource(R.drawable.tiaoweipingreen);
                break;
            case 11:
                imageView.setImageResource(R.drawable.jianguonew);
                break;
            case 12:
                imageView.setImageResource(R.drawable.xiaochigreen);
                break;
            case 13:
                imageView.setImageResource(R.drawable.yinliaogreennew);
                break;
            case 14:
                imageView.setImageResource(R.drawable.tanggreen);
                break;
        }
        String KCAL = String.format("热量: %s千卡", item.getKcalval());
        helper.setText(R.id.tv_kcal, KCAL);
        helper.setText(R.id.tv_name, item.getFoodname());
        String weight = String.format(Utils.getApp().getString(R.string.total_weight), item.getExplain());
        helper.setText(R.id.tv_weight, weight);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //CheckHelper和Adapter绑定
        mHelper.bind(list.get(position), holder, holder.itemView);
    }
}
