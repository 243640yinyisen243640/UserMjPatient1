package com.vice.bloodpressure.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BloodPressureListActivity;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BmiListActivity;
import com.vice.bloodpressure.ui.activity.hospital.checkdata.CheckDataDetailListActivity;
import com.vice.bloodpressure.ui.activity.registration.ElectrolyteListActivity;
import com.vice.bloodpressure.ui.activity.registration.report.ImmunoFluorescenceReportAndClottingReportListActivity;
import com.vice.bloodpressure.ui.activity.registration.report.ReportDataDetailListActivity;
import com.vice.bloodpressure.ui.activity.registration.report.ReportDoctorAdviceListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述:   检验数据Adapter
 * 作者: LYD
 * 创建日期: 2019/9/9 11:07
 */
public class CheckDataListAdapter extends CommonAdapter<String> {
    private String ofid;

    public CheckDataListAdapter(Context context, int layoutId, List datas, String ofid) {
        super(context, layoutId, datas);
        this.ofid = ofid;
    }

    @SuppressLint("Recycle")
    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        //使用图片资源
        TypedArray imgArray = null;
        String[] titleArray = null;
        if (TextUtils.isEmpty(ofid)) {
            //检验数据
            imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.check_data_img);
            titleArray = Utils.getApp().getResources().getStringArray(R.array.check_data_title);
        } else {
            //预约模块 报告查询
            imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.report_data_img);
            titleArray = Utils.getApp().getResources().getStringArray(R.array.report_data_title);
        }
        viewHolder.setText(R.id.tv_title, titleArray[position]);
        viewHolder.setImageResource(R.id.img, imgArray.getResourceId(position, 0));
        viewHolder.setText(R.id.tv_time, item);

        //跳转详情
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ofid)) {
                    //检验数据
                    Intent intent;
                    switch (position) {
                        case 0://血压
                            intent = new Intent(Utils.getApp(), BloodPressureListActivity.class);
                            intent.putExtra("key", 1);
                            break;
                        case 1://身高
                            intent = new Intent(Utils.getApp(), BmiListActivity.class);
                            intent.putExtra("key", 5);
                            break;
                        default://默认
                            intent = new Intent(Utils.getApp(), CheckDataDetailListActivity.class);
                            break;
                    }
                    intent.putExtra("checkDataType", position);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    //预约模块报告查询
                    Intent intent;
                    switch (position) {
                        //就诊建议
                        case 0:
                            intent = new Intent(Utils.getApp(), ReportDoctorAdviceListActivity.class);
                            break;
                        //电解质
                        case 1:
                            intent = new Intent(Utils.getApp(), ElectrolyteListActivity.class);
                            break;
                        //免疫荧光
                        //凝血
                        case 9:
                        case 10:
                            intent = new Intent(Utils.getApp(), ImmunoFluorescenceReportAndClottingReportListActivity.class);
                            intent.putExtra("position", position);
                            break;
                        //预约报告
                        default:
                            intent = new Intent(Utils.getApp(), ReportDataDetailListActivity.class);
                            intent.putExtra("checkDataType", position);
                            break;
                    }
                    intent.putExtra("ofid", ofid);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
            }
        });

    }
}
