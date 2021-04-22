package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.RegistrationHospitalListBean;
import com.vice.bloodpressure.ui.activity.registration.PhysicalExaminationListOfDepartmentsActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class RegistrationHospitalListAdapter extends CommonAdapter<RegistrationHospitalListBean> {

    public RegistrationHospitalListAdapter(Context context, int layoutId, List<RegistrationHospitalListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, RegistrationHospitalListBean item, int position) {
        viewHolder.setText(R.id.tv_name, item.getHospitalname());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), PhysicalExaminationListOfDepartmentsActivity.class);
                intent.putExtra("hospitalBean", item);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
