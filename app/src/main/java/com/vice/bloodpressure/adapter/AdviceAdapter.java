package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.vice.bloodpressure.bean.AdviceBean;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;

import java.util.List;

/**
 * 描述:医生建议Adapter
 * 作者: LYD
 * 创建日期: 2019/3/20 21:18
 */
public class AdviceAdapter extends MultiItemTypeAdapter<AdviceBean> {


    public AdviceAdapter(Context context, List<AdviceBean> datas) {
        super(context, datas);
        addItemViewDelegate(new DelegateAdvice1());
        addItemViewDelegate(new DelegateAdvice2());
    }
}
