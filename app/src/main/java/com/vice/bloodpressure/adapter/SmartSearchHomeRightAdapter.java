package com.vice.bloodpressure.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.LabelBean;

import java.util.List;

public class SmartSearchHomeRightAdapter extends BaseQuickAdapter<LabelBean, BaseViewHolder> {
    public SmartSearchHomeRightAdapter(@Nullable List<LabelBean> data) {
        super(R.layout.item_smart_search_second_level, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, LabelBean labelBean) {
        holder.setText(R.id.tv_text, labelBean.getRemarkname());
    }
}
