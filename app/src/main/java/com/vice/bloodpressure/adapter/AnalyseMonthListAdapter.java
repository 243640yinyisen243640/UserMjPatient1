package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.ui.activity.smartanalyse.BloodPressureReportActivity;
import com.vice.bloodpressure.ui.activity.smartanalyse.BloodSugarReportActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class AnalyseMonthListAdapter extends CommonAdapter<String> {
    private String type;

    public AnalyseMonthListAdapter(Context context, int layoutId, List datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.tv_time, item);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                //分析报告
                if ("0".equals(type)) {
                    intent = new Intent(Utils.getApp(), BloodSugarReportActivity.class);
                    intent.putExtra("time", item);
                } else {
                    intent = new Intent(Utils.getApp(), BloodPressureReportActivity.class);
                    intent.putExtra("time", item);
                    intent.putExtra("type", "3");
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
