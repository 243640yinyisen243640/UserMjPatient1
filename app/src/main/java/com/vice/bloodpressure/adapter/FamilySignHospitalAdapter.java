package com.vice.bloodpressure.adapter;
/*
 * 包名:     com.vice.bloodpressure.adapter
 * 类名:     FamilySignHospitalAdapter
 * 描述:     家签医院列表
 * 作者:     ZWK
 * 创建日期: 2020/1/8 11:35
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.HospitalEntity;

import java.util.List;

public class FamilySignHospitalAdapter extends BaseAdapter {

    private Context context;
    private List<HospitalEntity> hospitals;

    public FamilySignHospitalAdapter(Context context, List<HospitalEntity> hospitals) {
        this.context = context;
        this.hospitals = hospitals;
    }

    @Override
    public int getCount() {
        return hospitals == null ? 0 : hospitals.size();
    }

    @Override
    public Object getItem(int position) {
        return hospitals.get(position);
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
            textView.setText(String.format("（%s） %s", hospitals.get(position).getCityname(), hospitals.get(position).getHospitalname()));
        }
        return convertView;
    }
}