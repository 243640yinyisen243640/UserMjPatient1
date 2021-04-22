package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.res.TypedArray;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2019/3/29 15:09
 */
public class MakePolicyAdapter extends CommonAdapter {
    private static final String TAG = "MakePolicyAdapter";

    public MakePolicyAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        TypedArray imgListHome = Utils.getApp().getResources().obtainTypedArray(R.array.make_policy_list_home);
        viewHolder.setImageResource(R.id.img_make_policy, imgListHome.getResourceId(position, 0));
    }
}
