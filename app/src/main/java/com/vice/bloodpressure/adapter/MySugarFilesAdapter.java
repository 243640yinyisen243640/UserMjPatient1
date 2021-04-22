package com.vice.bloodpressure.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.MySugarLevel0Bean;
import com.vice.bloodpressure.bean.MySugarLevel1Bean;

import java.util.List;


/**
 * 描述:  肝病档案
 * 作者: LYD
 * 创建日期: 2020/4/8 9:54
 */
public class MySugarFilesAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public MySugarFilesAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.header_health_archive);
        addItemType(TYPE_LEVEL_1, R.layout.item_health_archive);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                MySugarLevel0Bean lv0 = (MySugarLevel0Bean) item;
                helper.setText(R.id.tv_title_name, lv0.getGroupName());
                helper.setImageResource(R.id.img_right_arrow, lv0.isExpanded() ? R.drawable.right_arrow_down : R.drawable.right_arrow);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                MySugarLevel1Bean lv1 = (MySugarLevel1Bean) item;
                //左边
                TextView tvName = helper.getView(R.id.tv_left);
                //右边
                TextView tvContent = helper.getView(R.id.tv_right);
                tvName.setText(lv1.getName());
                tvContent.setText(lv1.getContent());
                //位置确定
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = helper.getLayoutPosition();
                        ToastUtils.showShort(position);
                    }
                });
                break;
        }
    }
}
