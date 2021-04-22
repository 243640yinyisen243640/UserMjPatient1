package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.ClottingReportListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述:   凝血四项
 * 作者: LYD
 * 创建日期: 2019/11/27 16:01
 */
public class ClottingReportListAdapter extends CommonAdapter<ClottingReportListBean> {
    public ClottingReportListAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ClottingReportListBean item, int position) {
        String name = item.getName();
        String ref = item.getRef();
        viewHolder.setText(R.id.tv_left, name);
        viewHolder.setText(R.id.tv_right, ref);
        String val = item.getVal();
        int status = item.getStatus();
        TextView tvCenter = viewHolder.getView(R.id.tv_center);
        tvCenter.setText(val);
        //1偏高  2偏低 0 正常
        switch (status) {
            case 0:
                tvCenter.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case 1:
                tvCenter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.clotting_report_red_arrow_up, 0);
                break;
            case 2:
                tvCenter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.clotting_report_red_arrow_down, 0);
                break;
        }
    }
}
