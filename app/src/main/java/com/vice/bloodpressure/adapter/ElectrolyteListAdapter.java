package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.ElectrolyteListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class ElectrolyteListAdapter extends CommonAdapter<ElectrolyteListBean> {
    public ElectrolyteListAdapter(Context context, int layoutId, List<ElectrolyteListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ElectrolyteListBean item, int position) {
        String potassium = item.getPotassium();
        String sodium = item.getSodium();
        String calcium = item.getCalcium();
        String urobiliary = item.getUrobiliary();
        String nca = item.getNca();
        String ph = item.getPh();
        viewHolder.setText(R.id.tv_top_left, "钾：" + potassium);
        viewHolder.setText(R.id.tv_top_right, "钠：" + sodium);
        viewHolder.setText(R.id.tv_center_left, "氯：" + calcium);
        viewHolder.setText(R.id.tv_center_right, "实测离子钙：" + urobiliary);
        viewHolder.setText(R.id.tv_bottom_left, "标准钙：" + nca);
        viewHolder.setText(R.id.tv_bottom_right, "ph：" + ph);
    }
}
