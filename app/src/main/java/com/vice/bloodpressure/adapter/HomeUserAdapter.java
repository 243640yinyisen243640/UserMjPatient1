package com.vice.bloodpressure.adapter;

import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;

import java.util.List;

public class HomeUserAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeUserAdapter(@Nullable List<String> data) {
        super(R.layout.item_home_user_my_service, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String s) {
        int position = holder.getAdapterPosition();
        String[] str = Utils.getApp().getResources().getStringArray(R.array.home_user_my_service_text);
        holder.setText(R.id.tv_text, str[position]);

        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.home_user_my_service_img);
        holder.setImageResource(R.id.img_pic, imgArray.getResourceId(position, 0));
    }
}
