package com.lyd.modulemall.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.AddressBean;

import java.util.List;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2021/1/27 19:38
 */
public class BottomSelectAddressAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {
    private CheckHelper mHelper;
    List<AddressBean> list;
    public BottomSelectAddressAdapter(@Nullable List<AddressBean> data, CheckHelper mHelper) {
        super(R.layout.popup_jdcitypicker_item, data);
        this.mHelper = mHelper;
        this.list = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddressBean item) {
        String cityname = item.getCityname();
        helper.setText(R.id.tv_name, cityname);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //CheckHelper和Adapter绑定
        mHelper.bind(list.get(position), holder, holder.itemView);
    }

}
