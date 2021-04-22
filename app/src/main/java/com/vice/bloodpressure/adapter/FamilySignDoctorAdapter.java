package com.vice.bloodpressure.adapter;
/*
 * 包名:     com.vice.bloodpressure.adapter
 * 类名:     FamilySignDoctorAdapter
 * 描述:     家签医生
 * 作者:     ZWK
 * 创建日期: 2020/1/8 13:45
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.DoctorEntity;

import java.util.List;

public class FamilySignDoctorAdapter extends BaseAdapter {

    private Context context;
    private List<DoctorEntity> doctors;

    public FamilySignDoctorAdapter(Context context, List<DoctorEntity> doctors) {
        this.context = context;
        this.doctors = doctors;
    }

    @Override
    public int getCount() {
        return doctors == null ? 0 : doctors.size();
    }

    @Override
    public Object getItem(int position) {
        return doctors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contracted_community_hospitals, parent, false);
            TextView textView = convertView.findViewById(R.id.tv_hos_name);
            textView.setText(doctors.get(position).getDocname());
        }
        return convertView;
    }
}
