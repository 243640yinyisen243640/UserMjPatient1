package com.vice.bloodpressure.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;

import java.util.List;


public class RegisterQuestionSingleCheckAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    //默认选中位置
    private int selected = -1;
    private com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener;

    public RegisterQuestionSingleCheckAdapter(@Nullable List<String> datas) {
        super(R.layout.item_register_question, datas);
    }

    public void setOnItemClickListener(com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String s) {
        int position = holder.getLayoutPosition();
        holder.setText(R.id.tv_content, s);
        //单选处理
        ImageView imgCheck = holder.getView(R.id.img_check);
        if (selected == position) {
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
