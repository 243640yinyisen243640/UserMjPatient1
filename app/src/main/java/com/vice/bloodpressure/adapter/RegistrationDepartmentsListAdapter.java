package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.RegistrationDepartmentsListBean;
import com.vice.bloodpressure.ui.activity.registration.PhysicalExaminationDoctorMainActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class RegistrationDepartmentsListAdapter extends CommonAdapter<RegistrationDepartmentsListBean> {

    public RegistrationDepartmentsListAdapter(Context context, int layoutId, List<RegistrationDepartmentsListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, RegistrationDepartmentsListBean item, int position) {
        viewHolder.setText(R.id.tv_name, item.getDepname());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), PhysicalExaminationDoctorMainActivity.class);
                intent.putExtra("departmentsBean", item);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
