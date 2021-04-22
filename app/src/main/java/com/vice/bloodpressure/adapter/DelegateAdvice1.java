package com.vice.bloodpressure.adapter;

import android.view.View;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.AdviceBean;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

/**
 * 描述:1：患教 2：群发消息
 * 作者: LYD
 * 创建日期: 2019/6/21 15:16
 */
public class DelegateAdvice1 implements ItemViewDelegate<AdviceBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_advice_patient_education;
    }

    @Override
    public boolean isForViewType(AdviceBean item, int position) {
        return !item.isOperate();
    }

    @Override
    public void convert(ViewHolder viewHolder, AdviceBean item, int position) {
        viewHolder.setText(R.id.tv_content, item.getContent());
        viewHolder.setText(R.id.tv_time, item.getCreattime());
        TextView tvRight = viewHolder.getView(R.id.tv_right);
        if ("1".equals(item.getType())) {
            tvRight.setVisibility(View.VISIBLE);
            String high = item.getInfo().getSystolic() + "";
            String low = item.getInfo().getDiastole() + "";
            tvRight.setText(String.format("%s/%smmHg", high, low));
        } else {
            tvRight.setVisibility(View.GONE);
        }
    }
}
