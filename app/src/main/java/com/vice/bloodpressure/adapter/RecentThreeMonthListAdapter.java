package com.vice.bloodpressure.adapter;

import android.content.Context;

import com.blankj.utilcode.util.ColorUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.bean.SugarReportBean;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class RecentThreeMonthListAdapter extends CommonAdapter<SugarReportBean.DanbaiBean> {
    private String type;

    public RecentThreeMonthListAdapter(Context context, int layoutId, List<SugarReportBean.DanbaiBean> datas, String type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, SugarReportBean.DanbaiBean item, int position) {
        String datetime = item.getDatetime();
        int value = TurnsUtils.getInt(item.getDiastaticvalue(), 0);
        String condition = item.getBiao();
        //1型糖尿病6.1~7.0%
        //2型糖尿病6.0%~7.0%
        //妊娠糖尿病4%~6.0%
        //1：1型  2：2型  3：妊娠  4 其他
        switch (type) {
            case "Ⅰ":
                //如果偏高则标红并显示向上的箭头；如果偏低则标蓝并显示向下的箭头
                if (value > 7.0) {
                    viewHolder.setText(R.id.tv_lv_result, value + "↑");//红 向上
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.sugar_high));
                } else if (value > 6.1) {
                    viewHolder.setText(R.id.tv_lv_result, value + "");
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.black_text));
                } else {
                    viewHolder.setText(R.id.tv_lv_result, value + "↓");//蓝 向下
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.sugar_bp_low));
                }
                break;
            case "Ⅱ":
                //viewHolder.setText(R.id.tv_lv_result, value);
                if (value > 7.0) {
                    viewHolder.setText(R.id.tv_lv_result, value + "↑");//红 向上
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.sugar_high));
                } else if (value > 6.0) {
                    viewHolder.setText(R.id.tv_lv_result, value + "");
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.black_text));
                } else {
                    viewHolder.setText(R.id.tv_lv_result, value + "↓");//蓝 向下
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.sugar_bp_low));
                }
                break;
            case "妊娠":
                if (value > 6.0) {
                    viewHolder.setText(R.id.tv_lv_result, value + "↑");//红 向上
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.sugar_high));
                } else if (value > 4.0) {
                    viewHolder.setText(R.id.tv_lv_result, value + "");
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.black_text));
                } else {
                    viewHolder.setText(R.id.tv_lv_result, value + "↓");//蓝 向下
                    viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.sugar_bp_low));
                }
                break;
            default:
                viewHolder.setText(R.id.tv_lv_result, value + "");
                viewHolder.setTextColor(R.id.tv_lv_result, ColorUtils.getColor(R.color.black_text));
                break;
        }
        viewHolder.setText(R.id.tv_lv_time, datetime);
        viewHolder.setText(R.id.tv_lv_condition, condition);
    }
}
