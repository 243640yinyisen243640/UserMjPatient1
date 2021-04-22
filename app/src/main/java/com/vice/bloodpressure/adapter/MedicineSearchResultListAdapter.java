package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.MedicineSearchLevelOneBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class MedicineSearchResultListAdapter extends CommonAdapter<MedicineSearchLevelOneBean> {
    public MedicineSearchResultListAdapter(Context context, int layoutId, List<MedicineSearchLevelOneBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MedicineSearchLevelOneBean item, int position) {
        viewHolder.setText(R.id.tv_medicine_name, item.getMedicine());
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                intent.putExtra("title", "用药要求");
                intent.putExtra("url", item.getContenturl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }
        });
    }
}
