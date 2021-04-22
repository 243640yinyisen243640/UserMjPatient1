package com.lyd.modulemall.adapter;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luwei.checkhelper.CheckHelper;
import com.lyd.baselib.utils.ViewUtils;
import com.lyd.modulemall.R;
import com.lyd.modulemall.bean.ProductLabelListBean;

import java.util.List;

public class ProductLabelListAdapter extends BaseQuickAdapter<ProductLabelListBean, BaseViewHolder> {
    private CheckHelper mHelper;
    List<ProductLabelListBean> list;

    public ProductLabelListAdapter(@Nullable List<ProductLabelListBean> data, CheckHelper mHelper) {
        super(R.layout.item_mall_search_label, data);
        this.mHelper = mHelper;
        this.list = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProductLabelListBean item) {
        int totalWidth = ConvertUtils.dp2px(300);
        int paddingWidth = ConvertUtils.dp2px(3 * 14);
        int remainWidth = totalWidth - paddingWidth;
        int itemWidth = remainWidth / 3;

        LinearLayout llLabel = helper.getView(R.id.ll_label);
        ViewUtils.setWidth(llLabel, itemWidth);

        String label_name = item.getLabel_name();
        helper.setText(R.id.tv_label, label_name);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //CheckHelper和Adapter绑定
        mHelper.bind(list.get(position), holder, holder.itemView);
    }
}
