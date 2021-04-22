package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.FollowUpVisitListBean;
import com.vice.bloodpressure.view.MyListView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 描述: 随访管理列表
 * 作者: LYD
 * 创建日期: 2019/7/19 11:09
 */
public class FollowUpVisitListAdapter extends CommonAdapter<FollowUpVisitListBean.DataBean> {

    private String type;

    public FollowUpVisitListAdapter(Context context, int layoutId, List<FollowUpVisitListBean.DataBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, FollowUpVisitListBean.DataBean item, int position) {
        viewHolder.setText(R.id.tv_time, item.getPlan_name());
        MyListView lvChild = viewHolder.getView(R.id.lv_child);
        lvChild.setAdapter(new FollowUpChildVisitListAdapter(lvChild.getContext(), R.layout.item_follow_up_child_visit_list, item.getPlan_list(), type));
    }
}
