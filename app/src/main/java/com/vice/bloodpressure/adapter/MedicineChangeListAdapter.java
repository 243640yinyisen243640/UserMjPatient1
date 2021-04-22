package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.MedicineChangeListBean;
import com.vice.bloodpressure.imp.CommonAdapterClickImp;
import com.vice.bloodpressure.ui.activity.smartmakepolicy.MedicineDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MedicineChangeListAdapter extends CommonAdapter<MedicineChangeListBean> {
    private CommonAdapterClickImp adapterViewClick;

    public MedicineChangeListAdapter(Context context, int layoutId, List<MedicineChangeListBean> datas, CommonAdapterClickImp adapterViewClick) {
        super(context, layoutId, datas);
        this.adapterViewClick = adapterViewClick;
    }

    @Override
    protected void convert(ViewHolder viewHolder, MedicineChangeListBean item, int position) {
        viewHolder.setText(R.id.tv_group_name, item.getDrugname());
        viewHolder.setImageResource(R.id.iv_pic, R.drawable.medicine_select_detail_change);
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
        viewHolder.setText(R.id.tv_text, "更换");
        viewHolder.getView(R.id.ll_change).setOnClickListener(new OnAdapterViewClickListener(position));
    }

    private class OnAdapterViewClickListener implements View.OnClickListener {

        int position;

        OnAdapterViewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (adapterViewClick != null) {
                adapterViewClick.adapterViewClick(position);
            }
        }
    }
}
