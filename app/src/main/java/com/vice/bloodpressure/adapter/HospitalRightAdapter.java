package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.HospitalRightBean;
import com.vice.bloodpressure.ui.activity.hospital.DoctorDetailsActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class HospitalRightAdapter extends CommonAdapter<HospitalRightBean> {

    private static final String TAG = "HospitalRightAdapter";

    public HospitalRightAdapter(Context context, int layoutId, List<HospitalRightBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final HospitalRightBean item, int position) {
        ImageView imgHead = viewHolder.getView(R.id.iv_head);
        Glide.with(Utils.getApp()).load(item.getImgurl()).placeholder(R.drawable.default_head).error(R.drawable.default_head).into(imgHead);
        viewHolder.setText(R.id.tv_hospital_name, item.getHospitalname());
        viewHolder.setText(R.id.tv_doctor_type, item.getDoczhc());
        viewHolder.setText(R.id.tv_doctor_name, item.getDocname());
        //跳转医生详情
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), DoctorDetailsActivity.class);
                intent.putExtra("docid", item.getDocid() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
