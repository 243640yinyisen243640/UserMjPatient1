package com.vice.bloodpressure.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.HomeSportListBean;

import java.util.List;

public class HomeSportListChildAdapter extends BaseQuickAdapter<HomeSportListBean.SportsBean, BaseViewHolder> {
    public HomeSportListChildAdapter(@Nullable List<HomeSportListBean.SportsBean> data) {
        super(R.layout.item_child_home_sport_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeSportListBean.SportsBean item) {
        String sportType = item.getSportType();
        if ("步行".equals(sportType)) {
            helper.setText(R.id.tv_type_value, item.getMinutes() + "步");
        } else {
            helper.setText(R.id.tv_type_value, item.getMinutes());
        }
        helper.setText(R.id.tv_type, sportType);
        helper.setText(R.id.tv_type_kcal, item.getKcals() + "千卡");
    }
}
