package com.vice.bloodpressure.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.BloodOxygenListBean;

import java.util.List;

public class BloodOxygenListAdapter extends BaseQuickAdapter<BloodOxygenListBean, BaseViewHolder> {

    public BloodOxygenListAdapter(@Nullable List<BloodOxygenListBean> data) {
        super(R.layout.item_blood_oxygen_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, BloodOxygenListBean bean) {
        String datetime = bean.getDatetime();
        holder.setText(R.id.tv_oxygen_time, datetime);
        String oxygen = bean.getOxygen();
        String bpmval = bean.getBpmval();
        holder.setText(R.id.tv_oxygen_number, oxygen + "" + "/" + bpmval);
        holder.setText(R.id.tv_tongyong, "%" + "/" + "bmp");
    }
}
