package com.vice.bloodpressure.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;

import java.util.HashMap;
import java.util.List;

public class RvSportQuestionFourAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HashMap<Integer, Boolean> isSelected = new HashMap<>();
    private List<String> datas;
    private com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener;

    public RvSportQuestionFourAdapter(@Nullable List<String> datas) {
        super(R.layout.item_sport_question_four, datas);
        this.datas = datas;
        init();
    }

    private void init() {
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String item) {
        int position = holder.getLayoutPosition();
        holder.setText(R.id.tv_text, item);
        //多选处理
        ImageView imgCheck = holder.getView(R.id.img_check);
        TextView tvText = holder.getView(R.id.tv_text);
        Boolean check = isSelected.get(position);
        if (check) {
            imgCheck.setImageResource(R.drawable.sport_level_checked);
            tvText.setTextColor(ColorUtils.getColor(R.color.main_home));
        } else {
            imgCheck.setImageResource(R.drawable.sport_level_uncheck);
            tvText.setTextColor(ColorUtils.getColor(R.color.black_text));
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

    public void setOnItemClickListener(com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
