package com.vice.bloodpressure.adapter;
/*
 * 包名:     com.vice.bloodpressure.adapter
 * 类名:     FamilySignDepartmentAdapter
 * 描述:     家签科室
 * 作者:     ZWK
 * 创建日期: 2020/1/8 13:11
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.DepartmentEntity;

import java.util.List;

public class FamilySignDepartmentAdapter extends BaseAdapter {

    private Context context;
    private List<DepartmentEntity> departmnents;

    public FamilySignDepartmentAdapter(Context context, List<DepartmentEntity> departmnents) {
        this.context = context;
        this.departmnents = departmnents;
    }

    @Override
    public int getCount() {
        return departmnents == null ? 0 : departmnents.size();
    }

    @Override
    public Object getItem(int position) {
        return departmnents.get(position);
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
            textView.setText(departmnents.get(position).getDepname());
        }
        return convertView;
    }
}
