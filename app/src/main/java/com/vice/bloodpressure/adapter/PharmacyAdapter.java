package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.PharmacyBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class PharmacyAdapter extends CommonAdapter<PharmacyBean> {
    public PharmacyAdapter(Context context, int layoutId, List<PharmacyBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, PharmacyBean item, int position) {
        viewHolder.setText(R.id.tv_title, item.getDrugname());//
        viewHolder.setText(R.id.tv_time, item.getDatetime());
        viewHolder.setText(R.id.tv_count, item.getTimes() + "次");
        viewHolder.setText(R.id.tv_dosage, item.getDosage());
    }
}
