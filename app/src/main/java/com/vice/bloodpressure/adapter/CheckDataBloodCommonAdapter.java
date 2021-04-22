package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class CheckDataBloodCommonAdapter extends CommonAdapter<String> {
    private int type;

    public CheckDataBloodCommonAdapter(Context context, int layoutId, List<String> datas, int type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        String[] stringLeft = Utils.getApp().getResources().getStringArray(R.array.check_data_blood_common_left);
        String[] stringRight = Utils.getApp().getResources().getStringArray(R.array.check_data_blood_common_right);
        viewHolder.setText(R.id.tv_left, stringLeft[position]);
        TextView tvRight = viewHolder.getView(R.id.tv_right);
        if (5 == type) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(stringRight[position]);
        } else {
            tvRight.setVisibility(View.GONE);
        }
        viewHolder.setText(R.id.tv_center, item);
    }
}
