package com.lyd.modulemall.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.lyd.modulemall.R;

import java.util.List;

public class BottomSingleChoiceStringAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private CheckHelper mHelper;
    private List<String> list;

    public BottomSingleChoiceStringAdapter(@Nullable List<String> list, CheckHelper mHelper) {
        super(R.layout.item_popup_bottom_single_choice, list);
        this.list = list;
        this.mHelper = mHelper;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_content, item);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //CheckHelper和Adapter绑定
        mHelper.bind(list.get(position), holder, holder.itemView);
    }
}
