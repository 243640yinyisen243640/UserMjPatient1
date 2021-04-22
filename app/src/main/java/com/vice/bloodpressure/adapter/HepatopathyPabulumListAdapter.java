package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.HepatopathyPabulumListBean;
import com.vice.bloodpressure.ui.activity.healthrecordlist.HepatopathyPabulumDetailActivity;

import java.util.List;

public class HepatopathyPabulumListAdapter extends BaseQuickAdapter<HepatopathyPabulumListBean, BaseViewHolder> {


    public HepatopathyPabulumListAdapter(@Nullable List<HepatopathyPabulumListBean> data) {
        super(R.layout.item_hepatopathy_pabulum, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder holder, HepatopathyPabulumListBean item) {
        holder.setText(R.id.tv_time, item.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), HepatopathyPabulumDetailActivity.class);
                intent.putExtra("id", item.getId() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
