package com.vice.bloodpressure.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;

import java.util.List;

public class TestOfHbpQuestionDescAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TestOfHbpQuestionDescAdapter(@Nullable List<String> data) {
        super(R.layout.item_top_question_of_hbp, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, String s) {
        TextView tvText = viewHolder.getView(R.id.tv_question_top_desc);
        tvText.setText(s);
    }
}
