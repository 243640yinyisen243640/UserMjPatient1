package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.WeightListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class WeightListAdapter extends CommonAdapter<WeightListBean> {


    public WeightListAdapter(Context context, int layoutId, List<WeightListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, WeightListBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        viewHolder.setText(R.id.tv_right, item.getWeight() + "Kg");
    }
}
