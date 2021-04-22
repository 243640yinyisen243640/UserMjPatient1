package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.MyAppointmentListBean;
import com.vice.bloodpressure.imp.AdapterClickImp;
import com.vice.bloodpressure.ui.activity.registration.AppointmentDetailActivity;
import com.wei.android.lib.colorview.helper.ColorTextViewHelper;
import com.wei.android.lib.colorview.view.ColorLinearLayout;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MyAppointmentAdapter extends CommonAdapter<MyAppointmentListBean> {
    private AdapterClickImp imp;

    public MyAppointmentAdapter(Context context, int layoutId, List<MyAppointmentListBean> datas, AdapterClickImp imp) {
        super(context, layoutId, datas);
        this.imp = imp;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MyAppointmentListBean item, int position) {
        String schnum = item.getSchnum();
        String addtime = item.getAddtime();
        String hospitalname = item.getHospitalname();
        String depname = item.getDepname();
        String docname = item.getDocname();
        String schday = item.getSchday();
        String username = item.getUsername();
        String address = item.getAddress();
        viewHolder.setText(R.id.tv_number, "预约编号：" + schnum);
        viewHolder.setText(R.id.tv_operate_time, "操作时间：" + addtime);
        viewHolder.setText(R.id.tv_hospital_name, "医院名称：" + hospitalname);
        viewHolder.setText(R.id.tv_department_name, "科室名称：" + depname);
        viewHolder.setText(R.id.tv_doctor_name, "医生名称：" + docname);
        viewHolder.setText(R.id.tv_treat_time, "日期：" + schday);
        viewHolder.setText(R.id.tv_patient_name, "就诊人：" + username);
        viewHolder.setText(R.id.tv_patient_address, "就诊地址：" + address);
        //删除点击
        TextView tvDel = viewHolder.getView(R.id.tv_del);
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imp.onAdapterClick(tvDel, position);
            }
        });
        //状态判断
        ColorLinearLayout llTitle = viewHolder.getView(R.id.ll_title);
        ColorTextView tvState = viewHolder.getView(R.id.tv_state);
        ColorTextViewHelper helper = tvState.getColorHelper();
        //1已预约  2取消预约  3已过期  4 已就诊
        int status = item.getStatus();
        switch (status) {
            //1 已预约
            case 1:
                tvDel.setVisibility(View.GONE);
                tvState.setText("已预约");
                helper.setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                break;
            //2 已取消
            case 2:
                tvDel.setVisibility(View.VISIBLE);
                tvState.setText("已取消");
                helper.setBackgroundColorNormal(ColorUtils.getColor(R.color.color_666));
                break;
            //3 已过期
            case 3:
                tvDel.setVisibility(View.VISIBLE);
                tvState.setText("已过期");
                helper.setBackgroundColorNormal(ColorUtils.getColor(R.color.my_appointment_have_treat));
                break;
            //4 已就诊
            case 4:
                tvDel.setVisibility(View.GONE);
                tvState.setText("已就诊");
                helper.setBackgroundColorNormal(ColorUtils.getColor(R.color.my_appointment_past_due));
                break;
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), AppointmentDetailActivity.class);
                intent.putExtra("schid", item.getSchid() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
