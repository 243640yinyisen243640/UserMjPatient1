package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.MedicineSelectDetailListBean;
import com.vice.bloodpressure.ui.activity.smartmakepolicy.MedicineChangeListActivity;
import com.vice.bloodpressure.ui.activity.smartmakepolicy.MedicineDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MedicineSelectDetailListAdapter extends CommonAdapter<MedicineSelectDetailListBean.DrugsBean> {
    public MedicineSelectDetailListAdapter(Context context, int layoutId, List<MedicineSelectDetailListBean.DrugsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MedicineSelectDetailListBean.DrugsBean item, int position) {
        viewHolder.setText(R.id.tv_group_name, item.getDrugname());
        viewHolder.setImageResource(R.id.iv_pic, R.drawable.medicine_select_detail_change);
        viewHolder.setText(R.id.tv_text, "更换同类药物");
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), MedicineDetailActivity.class);
                intent.putExtra("id", item.getId() + "");
                //intent.putExtra("type", "1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
        viewHolder.getView(R.id.ll_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), MedicineChangeListActivity.class);
                intent.putExtra("id", item.getId() + "");
                intent.putExtra("listId", item.getListid() + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
