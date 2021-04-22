package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.ReportDoctorAdviceListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class ReportDoctorAdviceListAdapter extends CommonAdapter<ReportDoctorAdviceListBean> {
    public ReportDoctorAdviceListAdapter(Context context, int layoutId, List<ReportDoctorAdviceListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ReportDoctorAdviceListBean item, int postion) {
        viewHolder.setText(R.id.tv_time, "预约时间：" + item.getSchday());
        viewHolder.setText(R.id.tv_hospital_name, "预约医院：" + item.getHospitalname());
        viewHolder.setText(R.id.tv_doctor_name, "就诊医生：" + item.getDocname());
        viewHolder.setText(R.id.tv_advice, item.getAdvice());
    }
}
