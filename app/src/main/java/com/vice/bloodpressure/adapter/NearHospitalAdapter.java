package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.HospitalBean;
import com.vice.bloodpressure.ui.activity.hospital.HospitalDetailsActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by yicheng on 2018/6/4.
 */

public class NearHospitalAdapter extends CommonAdapter<HospitalBean> {


    public NearHospitalAdapter(Context context, int layoutId, List<HospitalBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final HospitalBean item, int position) {
        viewHolder.setText(R.id.tv_hospital_name, item.getHospitalname());
        //1: 社区卫生院 2： 二甲 3：三甲 4：村卫生所
        switch (item.getLevel()) {
            case "1":
                viewHolder.setText(R.id.tv_hospital_type, "社区卫生院");
                break;
            case "2":
                viewHolder.setText(R.id.tv_hospital_type, "二甲医院");
                break;
            case "3":
                viewHolder.setText(R.id.tv_hospital_type, "三甲医院");
                break;
            case "4":
                viewHolder.setText(R.id.tv_hospital_type, "村卫生所");
                break;
        }
        ImageView imgHospital = viewHolder.getView(R.id.iv_hospital);
        Glide.with(Utils.getApp()).load(item.getImgurl())
                .placeholder(R.drawable.default_hospital)
                .error(R.drawable.default_hospital).into(imgHospital);
        //跳转详情
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hid = item.getId() + "";
                Intent intent = new Intent(Utils.getApp(), HospitalDetailsActivity.class);
                intent.putExtra("hid", hid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });


    }
}
