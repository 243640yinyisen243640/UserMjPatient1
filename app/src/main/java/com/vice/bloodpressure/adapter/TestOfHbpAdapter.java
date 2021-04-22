package com.vice.bloodpressure.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.wei.android.lib.colorview.view.ColorLinearLayout;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.List;

public class TestOfHbpAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    //默认选中位置
    private int selected = -1;
    private com.vice.bloodpressure.imp.OnItemClickListener mOnItemClickListener;

    private int questionNumber;

    public TestOfHbpAdapter(@Nullable List<String> datas, int questionNumber) {
        super(R.layout.item_question_of_hbp, datas);
        this.questionNumber = questionNumber;
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
        ColorLinearLayout llRoot = holder.getView(R.id.ll_question_of_hbp);
        ColorTextView tvQuestionNumber = holder.getView(R.id.tv_question_number);
        TextView tvQuestionDesc = holder.getView(R.id.tv_question_desc);
        //设值
        String[] numberArray = null;
        String[] questionArray = null;
        int questionCount = 0;
        switch (questionNumber) {
            case 2:
                questionCount = 4;
                break;
            case 3:
            case 4:
                questionCount = 3;
                break;
            case 5:
                questionCount = 6;
                break;
        }
        switch (questionCount) {
            case 3:
                numberArray = Utils.getApp().getResources().getStringArray(R.array.test_of_hbp_question_number_three);
                questionArray = Utils.getApp().getResources().getStringArray(R.array.test_three_of_hbp_question_desc);
                break;
            case 4:
                numberArray = Utils.getApp().getResources().getStringArray(R.array.test_of_hbp_question_number_four);
                questionArray = Utils.getApp().getResources().getStringArray(R.array.test_two_of_hbp_question_desc);
                break;
            case 6:
                numberArray = Utils.getApp().getResources().getStringArray(R.array.test_of_hbp_question_number_six);
                questionArray = Utils.getApp().getResources().getStringArray(R.array.test_five_of_hbp_question_desc);
                break;
        }
        tvQuestionNumber.setText(numberArray[position]);
        tvQuestionDesc.setText(questionArray[position]);
        //单选处理
        if (selected == position) {
            //根布局
            llRoot.getColorHelper().setBorderColorNormal(ColorUtils.getColor(R.color.main_home));
            llRoot.getColorHelper().setBorderWidthNormal(1);
            llRoot.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.white));
            //题号
            tvQuestionNumber.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
            tvQuestionNumber.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
            //题目
            tvQuestionDesc.setTextColor(ColorUtils.getColor(R.color.main_home));
        } else {
            //根布局
            //llRoot.getColorHelper().setBorderColorNormal(ColorUtils.getColor(R.color.main_home));
            llRoot.getColorHelper().setBorderWidthNormal(0);
            llRoot.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_f2));
            //题号
            tvQuestionNumber.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.black_text));
            tvQuestionNumber.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_f2));
            tvQuestionNumber.getColorHelper().setBorderWidthNormal(1);
            tvQuestionNumber.getColorHelper().setBorderColorNormal(ColorUtils.getColor(R.color.color_cb));
            //题目
            tvQuestionDesc.setTextColor(ColorUtils.getColor(R.color.color_69));
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
