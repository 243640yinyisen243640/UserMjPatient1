package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.MakeBean;
import com.vice.bloodpressure.ui.activity.hospital.MakeDetailsActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


public class MakeAdapter extends CommonAdapter<MakeBean> {

    public MakeAdapter(Context context, int layoutId, List<MakeBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MakeBean item, int position) {
        TextView tvStatus = viewHolder.getView(R.id.tv_status);
        //预约状态 1：预约中 2：预约成功 3：预约失败
        switch (item.getStatus()) {
            case "1":
                tvStatus.setText("预约中");
                break;
            case "2":
                tvStatus.setText("预约成功");
                tvStatus.setTextColor(ColorUtils.getColor(R.color.main_home));
                break;
            case "3":
                tvStatus.setText("预约失败");
                tvStatus.setTextColor(ColorUtils.getColor(R.color.main_red));
                break;
        }
        viewHolder.setText(R.id.tv_time, item.getTime());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), MakeDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", item.getId());
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
