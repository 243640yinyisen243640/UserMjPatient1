package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.MedicineSearchLevelOneBean;
import com.vice.bloodpressure.bean.MedicineSearchLevelZeroBean;
import com.vice.bloodpressure.imp.AdapterViewClickListenerMedicine;

import java.util.List;

public class MedicineSearchResultLevelAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context mContext;

    private AdapterViewClickListenerMedicine adapterViewClickListener;

    public MedicineSearchResultLevelAdapter(List<MultiItemEntity> data, Context context, AdapterViewClickListenerMedicine adapterViewClickListener) {
        super(data);
        this.mContext = context;
        addItemType(TYPE_LEVEL_0, R.layout.item_medicine_search_level_zero);
        addItemType(TYPE_LEVEL_1, R.layout.item_medicine_search_level_one);
        this.adapterViewClickListener = adapterViewClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case 0://展开
                MedicineSearchLevelZeroBean lv0 = (MedicineSearchLevelZeroBean) item;
                helper.setText(R.id.tv_group_name, lv0.getClassname());
                helper.setImageResource(R.id.img_right_arrow, lv0.isExpanded() ? R.drawable.right_arrow_down : R.drawable.right_arrow);
                //回传处理逻辑
                helper.itemView.setOnClickListener(new OnAdapterViewClickListener(helper, lv0));
                break;
            case 1://跳转详情
                MedicineSearchLevelOneBean lv1 = (MedicineSearchLevelOneBean) item;
                helper.setText(R.id.tv_medicine_name, lv1.getMedicine());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Utils.getApp(), BaseWebViewActivity.class);
                        intent.putExtra("title", "用药要求");
                        intent.putExtra("url", lv1.getContenturl());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                    }
                });
                break;
        }
    }

    private class OnAdapterViewClickListener implements View.OnClickListener {

        BaseViewHolder helper;
        MedicineSearchLevelZeroBean bean;

        OnAdapterViewClickListener(BaseViewHolder helper, MedicineSearchLevelZeroBean bean) {
            this.helper = helper;
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
            if (adapterViewClickListener != null) {
                adapterViewClickListener.adapterViewClick(helper, bean);
            }
        }
    }
}
