package com.vice.bloodpressure.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.utils.TimeUtils;
import com.wei.android.lib.colorview.view.ColorLinearLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DietPlanResultSevenDaysAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private GetListener getListener;
    private int mPosition;


    public DietPlanResultSevenDaysAdapter(@Nullable List<String> data) {
        super(R.layout.item_diet_plan_detail_for_seven_days, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String s) {
        ColorLinearLayout llBg = holder.getView(R.id.ll_bg);
        int position = holder.getLayoutPosition();
        String startDateString = TimeUtils.plusDay(1);
        String endDateString = TimeUtils.plusDay(7);
        DateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat mm = new SimpleDateFormat("MM");
        DateFormat dd = new SimpleDateFormat("dd");
        Date startDate = com.blankj.utilcode.util.TimeUtils.string2Date(startDateString, ymd);
        Date endDate = com.blankj.utilcode.util.TimeUtils.string2Date(endDateString, ymd);
        List<Date> dateList = TimeUtils.getBetweenDates(startDate, endDate);
        Date date = dateList.get(position);
        String ymdStr = ymd.format(date);
        String week = TimeUtils.dateToWeek(ymdStr);
        String month = mm.format(date);
        String day = dd.format(date);
        holder.setText(R.id.tv_week, "星期" + week);
        holder.setText(R.id.tv_day, month + "/" + day);

        //修改背景颜色
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener.onClick(position);
                notifyDataSetChanged();
            }
        });

        TextView tvWeek = holder.getView(R.id.tv_week);
        TextView tvDay = holder.getView(R.id.tv_day);
        if (position == getmPosition()) {
            llBg.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.diet_plan_result_seven_days_checked_bg));
            tvWeek.setTextColor(ColorUtils.getColor(R.color.white));
            tvDay.setTextColor(ColorUtils.getColor(R.color.white));
        } else {
            llBg.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.white));
            tvWeek.setTextColor(ColorUtils.getColor(R.color.gray_text));
            tvDay.setTextColor(ColorUtils.getColor(R.color.gray_text));
        }
    }

    public void setGetListener(GetListener getListener) {
        this.getListener = getListener;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public interface GetListener {
        void onClick(int position);
    }
}
