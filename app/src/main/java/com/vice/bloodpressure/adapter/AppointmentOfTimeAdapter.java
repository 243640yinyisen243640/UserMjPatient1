package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.AppointmentDoctorListBean;
import com.vice.bloodpressure.bean.ScheduleInfoPostBean;
import com.vice.bloodpressure.ui.activity.registration.AppointmentCheckActivity;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 描述: 预约医生列表
 * 作者: LYD
 * 创建日期: 2019/10/23 9:48
 */
public class AppointmentOfTimeAdapter extends CommonAdapter<AppointmentDoctorListBean> {
    private String time;

    public AppointmentOfTimeAdapter(Context context, int layoutId, List<AppointmentDoctorListBean> datas, String time) {
        super(context, layoutId, datas);
        this.time = time;
    }

    @Override
    protected void convert(ViewHolder holder, AppointmentDoctorListBean data, int position) {
        //医生是否可预约 1是 2否
        //int isschedule = data.getIsschedule();
        String imgurl = data.getImgurl();
        String docname = data.getDocname();
        String doczhc = data.getDoczhc();
        String contents = data.getContents();
        QMUIRadiusImageView imgHead = holder.getView(R.id.iv_head);
        Glide.with(Utils.getApp())
                .load(imgurl)
                .placeholder(R.drawable.doctor_appoint_head_img)
                .error(R.drawable.doctor_appoint_head_img)
                .into(imgHead);
        holder.setText(R.id.tv_doctor_name, docname);
        holder.setText(R.id.tv_doctor_profession, doczhc);
        holder.setText(R.id.tv_doctor_skill, contents);
        //1是  2否
        int am = data.getAm();
        int pm = data.getPm();
        ColorTextView tvAm = holder.getView(R.id.tv_am);
        ColorTextView tvPm = holder.getView(R.id.tv_pm);
        if (1 == am) {
            tvAm.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white_text));
            tvAm.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
        }
        if (1 == pm) {
            tvPm.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white_text));
            tvPm.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
        }
        tvAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == am) {
                    //跳转预约确认页面
                    ScheduleInfoPostBean postBean = new ScheduleInfoPostBean();
                    postBean.setSchday(time);
                    postBean.setSid(data.getSid() + "");
                    postBean.setType("1");
                    Intent intent = new Intent(Utils.getApp(), AppointmentCheckActivity.class);
                    intent.putExtra("data", postBean);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    ToastUtils.showShort("不可预约");
                }
            }
        });
        tvPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == pm) {
                    //跳转预约确认页面
                    ScheduleInfoPostBean postBean = new ScheduleInfoPostBean();
                    postBean.setSchday(time);
                    postBean.setSid(data.getSid() + "");
                    postBean.setType("2");
                    Intent intent = new Intent(Utils.getApp(), AppointmentCheckActivity.class);
                    intent.putExtra("data", postBean);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                } else {
                    ToastUtils.showShort("不可预约");
                }
            }
        });
    }
}
