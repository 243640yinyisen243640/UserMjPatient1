package com.lyd.modulemall.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.LogisticsCompanyListBean;

import java.util.List;

public class BottomSingleChoiceLogisticsCompanyAdapter extends BaseQuickAdapter<LogisticsCompanyListBean.LogisticsBean, BaseViewHolder> {
    private CheckHelper mHelper;
    private List<LogisticsCompanyListBean.LogisticsBean> list;

    public BottomSingleChoiceLogisticsCompanyAdapter(@Nullable List<LogisticsCompanyListBean.LogisticsBean> list, CheckHelper mHelper) {
        super(R.layout.item_popup_bottom_single_choice, list);
        this.list = list;
        this.mHelper = mHelper;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LogisticsCompanyListBean.LogisticsBean item) {
        helper.setText(R.id.tv_content, item.getName());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //CheckHelper和Adapter绑定
        mHelper.bind(list.get(position), holder, holder.itemView);
    }
}
