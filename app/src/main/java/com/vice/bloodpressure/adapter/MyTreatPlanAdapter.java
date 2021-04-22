package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.MyTreatPlanBean;
import com.vice.bloodpressure.ui.activity.smartmakepolicy.HbpDetailActivity;
import com.vice.bloodpressure.ui.activity.sport.SportWeekPagerDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MyTreatPlanAdapter extends CommonAdapter<MyTreatPlanBean> {
    private String type;

    public MyTreatPlanAdapter(Context context, int layoutId, List<MyTreatPlanBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MyTreatPlanBean item, int position) {
        switch (type) {
            case "0":
                viewHolder.setText(R.id.tv_name, "我的治疗方案");
                viewHolder.setText(R.id.tv_time, item.getTime());
                viewHolder.setImageResource(R.id.img_pic, R.drawable.my_treat_plan);
                break;
            case "1":
            case "4":
                viewHolder.setText(R.id.tv_name, "我的治疗方案");
                viewHolder.setText(R.id.tv_time, item.getSendtime());
                viewHolder.setImageResource(R.id.img_pic, R.drawable.my_treat_plan);
                break;
            case "2":
                viewHolder.setText(R.id.tv_name, "我的治疗方案");
                viewHolder.setText(R.id.tv_time, item.getCreatedatetime());
                viewHolder.setImageResource(R.id.img_pic, R.drawable.my_treat_plan);
                break;
            case "3":
                viewHolder.setText(R.id.tv_name, "运动周报");
                viewHolder.setText(R.id.tv_time, item.getTime());
                viewHolder.setImageResource(R.id.img_pic, R.drawable.my_treat_plan_sport);
                break;
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case "0":
                        String treatment = item.getTreatment();
                        if ("1".equals(treatment)) {
                            String homePressureUrl = item.getUrl();
                            Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                            intent.putExtra("title", "血压管理方案");
                            intent.putExtra("url", homePressureUrl);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Utils.getApp().startActivity(intent);
                        } else {
                            Intent intent = new Intent(Utils.getApp(), HbpDetailActivity.class);
                            intent.putExtra("id", item.getId() + "");
                            intent.putExtra("type", "1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Utils.getApp().startActivity(intent);
                        }
                        break;
                    case "1":
                    case "4":
                        Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                        intent.putExtra("title", "处方");
                        intent.putExtra("url", item.getUrl());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                        break;
                    case "2":
                        String startUrl = "file:///android_asset/pdf/pdf.html?";
                        String pdf_url = item.getPdf_url();
                        Intent intent1 = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                        intent1.putExtra("title", "糖尿病足");
                        intent1.putExtra("url", startUrl + pdf_url);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent1);
                        break;
                    case "3":
                        Intent intent2 = new Intent(Utils.getApp(), SportWeekPagerDetailActivity.class);
                        intent2.putExtra("beginTime", item.getBegin_time());
                        intent2.putExtra("endTime", item.getEnd_time());
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent2);
                        break;
                }
            }
        });
    }
}
