package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.AppointmentDoctorListBean;
import com.vice.bloodpressure.ui.activity.registration.PhysicalExaminationDoctorInfoActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 描述: 预约医生列表
 * 作者: LYD
 * 创建日期: 2019/10/23 9:48
 */
public class AppointmentOfDoctorAdapter extends CommonAdapter<AppointmentDoctorListBean> {

    public AppointmentOfDoctorAdapter(Context context, int layoutId, List<AppointmentDoctorListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AppointmentDoctorListBean data, int position) {
        //医生是否可预约 1是 2否
        int isschedule = data.getIsschedule();
        String imgurl = data.getImgurl();
        String docname = data.getDocname();
        String doczhc = data.getDoczhc();
        String contents = data.getContents();
        if (1 == isschedule) {
            holder.setImageResource(R.id.iv_state, R.drawable.doctor_appoint_hot_state_normal);
        } else {
            holder.setImageResource(R.id.iv_state, R.drawable.doctor_appoint_hot_state_grey);
        }
        QMUIRadiusImageView imgHead = holder.getView(R.id.iv_head);
        Glide.with(Utils.getApp())
                .load(imgurl)
                .placeholder(R.drawable.doctor_appoint_head_img)
                .error(R.drawable.doctor_appoint_head_img)
                .into(imgHead);
        holder.setText(R.id.tv_doctor_name, docname);
        holder.setText(R.id.tv_doctor_profession, doczhc);
        holder.setText(R.id.tv_doctor_skill, contents);
        int docuserid = data.getDocuserid();
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtils.showShort("医生排班详情");
                Intent intent = new Intent(Utils.getApp(), PhysicalExaminationDoctorInfoActivity.class);
                intent.putExtra("docuserid", docuserid);
                intent.putExtra("docname", docname);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
