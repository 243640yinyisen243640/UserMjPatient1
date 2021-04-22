package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.ReportListMainBean;
import com.vice.bloodpressure.ui.activity.hospital.checkdata.CheckDataListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class ReportListMainAdapter extends CommonAdapter<ReportListMainBean> {
    public ReportListMainAdapter(Context context, int layoutId, List<ReportListMainBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ReportListMainBean item, int position) {
        int id = item.getId();
        String username = item.getUsername();
        String tel = item.getTel();
        String idcard = item.getIdcard();
        viewHolder.setText(R.id.tv_name, username);
        viewHolder.setText(R.id.tv_tel, tel);
        viewHolder.setText(R.id.tv_id_number, idcard);
        //查看报告
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), CheckDataListActivity.class);
                intent.putExtra("name", username);
                intent.putExtra("ofid", id + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
