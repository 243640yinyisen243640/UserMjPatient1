package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.ImmunoFluorescenceReportListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


public class ImmunoFluorescenceReportAdapter extends
        CommonAdapter<ImmunoFluorescenceReportListBean> {
    public ImmunoFluorescenceReportAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }


    @Override
    protected void convert(ViewHolder viewHolder, ImmunoFluorescenceReportListBean item, int position) {
        String name = item.getName();
        String val = item.getVal();
        String ref = item.getRef();
        String unit = item.getUnit();
        viewHolder.setText(R.id.tv_left, name);
        viewHolder.setText(R.id.tv_right_one, val);
        viewHolder.setText(R.id.tv_right_two, ref);
        viewHolder.setText(R.id.tv_right_three, unit);
    }
}
