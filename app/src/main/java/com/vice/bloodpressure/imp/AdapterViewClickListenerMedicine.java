package com.vice.bloodpressure.imp;

import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.bean.MedicineSearchLevelZeroBean;

/**
 * @author 用于处理Adaper中的view被点击
 */
public interface AdapterViewClickListenerMedicine {
    void adapterViewClick(BaseViewHolder help, MedicineSearchLevelZeroBean bean);
}
