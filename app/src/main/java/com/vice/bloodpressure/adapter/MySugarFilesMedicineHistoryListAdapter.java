package com.vice.bloodpressure.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.PersonalRecordMedicineHistoryBean;

import java.util.List;

public class MySugarFilesMedicineHistoryListAdapter extends BaseQuickAdapter<PersonalRecordMedicineHistoryBean, BaseViewHolder> {
    public MySugarFilesMedicineHistoryListAdapter(@Nullable List<PersonalRecordMedicineHistoryBean> data) {
        super(R.layout.item_health_archive_medicine_history, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, PersonalRecordMedicineHistoryBean historyBean) {
        String drugname = historyBean.getDrugname();

        String times = historyBean.getTimes();

        String dosage = historyBean.getDosage();

        viewHolder.setText(R.id.tv_left, drugname);
        viewHolder.setText(R.id.tv_center, times);
        viewHolder.setText(R.id.tv_right, dosage);

    }
}
