package com.vice.bloodpressure.adapter;

import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;

import java.util.List;

public class HomeEightModuleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeEightModuleAdapter(@Nullable List<String> data) {
        super(R.layout.item_home_eight_module, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String s) {
        int position = holder.getAdapterPosition();
        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.home_eight_module);
        holder.setImageResource(R.id.img_module, imgArray.getResourceId(position, 0));
    }
}
