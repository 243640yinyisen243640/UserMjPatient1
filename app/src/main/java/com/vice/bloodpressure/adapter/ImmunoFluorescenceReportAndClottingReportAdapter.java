package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.ImmunoFluorescenceReportAndClottingReportBean;
import com.vice.bloodpressure.ui.activity.registration.report.ClottingReportListActivity;
import com.vice.bloodpressure.ui.activity.registration.report.ImmunoFluorescenceReportListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class ImmunoFluorescenceReportAndClottingReportAdapter extends CommonAdapter<ImmunoFluorescenceReportAndClottingReportBean> {
    private int posi;

    public ImmunoFluorescenceReportAndClottingReportAdapter(Context context, int layoutId, List<ImmunoFluorescenceReportAndClottingReportBean> datas, int posi) {
        super(context, layoutId, datas);
        this.posi = posi;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ImmunoFluorescenceReportAndClottingReportBean item, int position) {
        String addtime = item.getAddtime();
        if (9 == posi) {
            viewHolder.setText(R.id.tv_title, "免疫荧光");
        } else {
            viewHolder.setText(R.id.tv_title, "凝血");
        }
        viewHolder.setText(R.id.tv_time, addtime);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int icid = item.getIcid();
                int bdid = item.getBdid();
                Intent intent = null;
                switch (posi) {
                    //免疫荧光
                    case 9:
                        intent = new Intent(Utils.getApp(), ImmunoFluorescenceReportListActivity.class);
                        intent.putExtra("id", icid);
                        break;
                    //凝血
                    case 10:
                        intent = new Intent(Utils.getApp(), ClottingReportListActivity.class);
                        intent.putExtra("id", bdid);
                        break;
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
