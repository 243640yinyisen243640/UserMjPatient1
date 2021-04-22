package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SportBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class SportAdapter extends CommonAdapter<SportBean> {

    public SportAdapter(Context context, int layoutId, List<SportBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, SportBean item, int position) {
        viewHolder.setText(R.id.tv_left, item.getDatetime());
        viewHolder.setText(R.id.tv_right_left, item.getDuration() + "分钟");
        viewHolder.setText(R.id.tv_right_right, item.getType());
    }
}
