package com.vice.bloodpressure.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.MedicineAddPopupListBean;

import java.util.List;

public class MedicineAddPopupAdapter extends BaseQuickAdapter<MedicineAddPopupListBean, BaseViewHolder> {

    public MedicineAddPopupAdapter(@Nullable List<MedicineAddPopupListBean> data) {
        super(R.layout.item_popup_medicine_add, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, MedicineAddPopupListBean medicineAddPopupListBean) {
        String medicine = medicineAddPopupListBean.getMedicine();
        viewHolder.setText(R.id.tv_medicine_name, medicine);
    }
}
