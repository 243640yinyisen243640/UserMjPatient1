package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.res.TypedArray;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;


public class AnalyseListAdapter extends CommonAdapter {

    public AnalyseListAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        TypedArray imgArray = Utils.getApp().getResources().obtainTypedArray(R.array.analyse_list_adapter);
        viewHolder.setImageResource(R.id.img_analyse_list, imgArray.getResourceId(position, 0));
    }

}
