package com.lyd.modulemall.adapter;

import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.lyd.modulemall.R;

import java.util.List;

public class PayTypeSelectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private CheckHelper mHelper;
    private List<String> list;

    public PayTypeSelectAdapter(@Nullable List<String> list, CheckHelper mHelper) {
        super(R.layout.item_pay_type_select_list, list);
        this.list = list;
        this.mHelper = mHelper;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text, item);
        int position = helper.getLayoutPosition();
        TypedArray sportImages = Utils.getApp().getResources().obtainTypedArray(R.array.pay_type_pic);
        helper.setImageResource(R.id.img_pic, sportImages.getResourceId(position, 0));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //CheckHelper和Adapter绑定
        mHelper.bind(list.get(position), holder, holder.itemView);
    }
}
