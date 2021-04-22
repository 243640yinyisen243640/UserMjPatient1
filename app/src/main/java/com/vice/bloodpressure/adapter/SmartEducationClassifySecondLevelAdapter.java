package com.vice.bloodpressure.adapter;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SmartEducationClassifyRightBean;
import com.vice.bloodpressure.ui.activity.smarteducation.SmartEducationClassifyListActivity;

import java.util.List;

public class SmartEducationClassifySecondLevelAdapter extends BaseQuickAdapter<SmartEducationClassifyRightBean, BaseViewHolder> {

    public SmartEducationClassifySecondLevelAdapter(@Nullable List<SmartEducationClassifyRightBean> data) {
        super(R.layout.item_smart_education_classify_second_level, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SmartEducationClassifyRightBean bean) {
        viewHolder.setText(R.id.tv_second_level_text, bean.getClassname());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = bean.getId();
                Intent intent = new Intent(Utils.getApp(), SmartEducationClassifyListActivity.class);
                intent.putExtra("id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }

}
