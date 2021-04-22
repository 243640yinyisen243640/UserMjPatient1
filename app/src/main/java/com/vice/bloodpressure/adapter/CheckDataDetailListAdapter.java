package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class CheckDataDetailListAdapter extends CommonAdapter<String> {
    private int type;

    public CheckDataDetailListAdapter(Context context, int layoutId, List<String> datas, int type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.tv_time, item);
        String[] title = Utils.getApp().getResources().getStringArray(R.array.check_data_title);
        for (int i = 2; i < 9; i++) {
            if (type == 2) {
                viewHolder.setText(R.id.tv_title, title[type]);
            } else if (type == i) {
                viewHolder.setText(R.id.tv_title, title[type] + "报告");
            }
        }
    }
}
