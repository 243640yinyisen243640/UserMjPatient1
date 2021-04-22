package com.vice.bloodpressure.adapter;
/*
 * 类名:     InHospitalAdapter
 * 描述:     家签预约住院
 * 作者:     ZWK
 * 创建日期: 2020/1/8 17:44
 */

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.TimeUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.InHospitalEntity;
import com.vice.bloodpressure.ui.activity.homesign.HospitalizationAppointmentDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Date;
import java.util.List;


public class InHospitalAdapter extends CommonAdapter<InHospitalEntity> {

    private Context context;

    public InHospitalAdapter(Context context, int layoutId, List<InHospitalEntity> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, InHospitalEntity inhospitalEntity, int position) {
        Date date = TimeUtils.string2Date(inhospitalEntity.getTime(), "yyyy-MM-dd HH:mm");
        String time = TimeUtils.date2String(date, "yyyy-MM-dd");
        holder.setText(R.id.tv_time, time);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HospitalizationAppointmentDetailActivity.class);
                intent.putExtra("id", inhospitalEntity.getId());
                context.startActivity(intent);
            }
        });
        TextView textView = holder.getView(R.id.tv_state);
        switch (inhospitalEntity.getStatus()) {
            case "1":
                holder.setText(R.id.tv_state, "预约中");
                textView.setTextColor(ContextCompat.getColor(context, R.color.black_text));
                break;
            case "2":
                holder.setText(R.id.tv_state, "预约成功");
                textView.setTextColor(ContextCompat.getColor(context, R.color.main_home));
                break;
            case "3":
                holder.setText(R.id.tv_state, "预约失败");
                textView.setTextColor(ContextCompat.getColor(context, R.color.main_red));
                break;
            default:
                holder.setText(R.id.tv_state, "未知");
                textView.setTextColor(ContextCompat.getColor(context, R.color.black_text));
                break;
        }
    }
}

