package com.vice.bloodpressure.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;

import java.util.HashMap;
import java.util.List;

public class IsHaveOtherDiseaseMultiCheckAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public static HashMap<Integer, Boolean> isSelected = new HashMap<>();
    private List<String> datas;


    private com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener;

    public IsHaveOtherDiseaseMultiCheckAdapter(@Nullable List<String> datas) {
        super(R.layout.item_register_question, datas);
        this.datas = datas;
        init();
    }

    public void setOnItemClickListener(com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private void init() {
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String s) {
        int position = holder.getLayoutPosition();
        holder.setText(R.id.tv_content, s);
        //多选处理
        ImageView imgCheck = holder.getView(R.id.img_check);
        Boolean isSelect = isSelected.get(position);
        if (isSelect) {
            imgCheck.setImageResource(R.drawable.sport_level_checked);
        } else {
            imgCheck.setImageResource(R.drawable.sport_level_uncheck);
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }


}
