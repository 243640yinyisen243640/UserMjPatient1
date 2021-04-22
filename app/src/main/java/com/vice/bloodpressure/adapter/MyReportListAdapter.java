package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.ui.activity.smartanalyse.AnalyseMonthListActivity;
import com.vice.bloodpressure.ui.activity.smartmakepolicy.MyTreatPlanListActivity;
import com.vice.bloodpressure.ui.activity.tcm.TcmListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MyReportListAdapter extends CommonAdapter<String> {
    public MyReportListAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        String[] str = Utils.getApp().getResources().getStringArray(R.array.my_report_text);
        viewHolder.setText(R.id.tv_my_report, str[position]);

        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.my_report_img);
        viewHolder.setImageResource(R.id.img_my_report, imgArray.getResourceId(position, 0));

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (position) {
                    //0 血糖
                    //1 血压
                    case 0:
                    case 1:
                        intent = new Intent(Utils.getApp(), AnalyseMonthListActivity.class);
                        intent.putExtra("type", position + "");
                        break;
                    //个性化降糖方案
                    case 2:
                        intent = new Intent(Utils.getApp(), MyTreatPlanListActivity.class);
                        intent.putExtra("type", "1");
                        break;
                    //个性化降压方案
                    case 3:
                        intent = new Intent(Utils.getApp(), MyTreatPlanListActivity.class);
                        intent.putExtra("type", "0");
                        break;
                    case 4:
                        intent = new Intent(Utils.getApp(), TcmListActivity.class);
                        break;
                    //病足
                    case 5:
                        intent = new Intent(Utils.getApp(), MyTreatPlanListActivity.class);
                        intent.putExtra("type", "2");
                        break;
                    case 6:
                        intent = new Intent(Utils.getApp(), MyTreatPlanListActivity.class);
                        intent.putExtra("type", "4");
                        break;
                    case 7:
                        intent = new Intent(Utils.getApp(), MyTreatPlanListActivity.class);
                        intent.putExtra("type", "3");
                        break;
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
