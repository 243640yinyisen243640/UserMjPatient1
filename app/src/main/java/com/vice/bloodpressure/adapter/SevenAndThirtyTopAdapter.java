package com.vice.bloodpressure.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.utils.DrawableUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

public class SevenAndThirtyTopAdapter extends CommonAdapter {
    private List<Double> listRight;

    public SevenAndThirtyTopAdapter(Context context, int layoutId, List datas, List<Double> listRight) {
        super(context, layoutId, datas);
        this.listRight = listRight;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {
        //左边
        String[] stringArray = Utils.getApp().getResources().getStringArray(R.array.seven_and_thirty_sugar);
        TextView tvLeft = viewHolder.getView(R.id.tv_left);
        tvLeft.setText(stringArray[position]);
        //右边
        int[] colors = Utils.getApp().getResources().getIntArray(R.array.seven_and_thirty_sugar_colors);
        TextView tvRight = viewHolder.getView(R.id.tv_right);
        if (position < 3) {
            tvRight.setText(String.format("%s次", listRight.get(position)));
        } else {
            tvRight.setText(String.format("%s", listRight.get(position)));
        }
        tvRight.setTextColor(colors[position]);
        //中间
        TextView tvCenter = viewHolder.getView(R.id.tv_center);
        //tvCenter.setBackgroundColor(colors[position]);
        int screenWidth = ScreenUtils.getScreenWidth();//屏幕总宽度
        int tvLeftWidth = SizeUtils.getMeasuredWidth(tvLeft);//左边宽度
        int tvRightWidth = SizeUtils.getMeasuredWidth(tvRight);//右边宽度
        int paddingWidth = ConvertUtils.dp2px(12 * 2);
        int marginWidth = ConvertUtils.dp2px(10 * 4);
        int tvCenterTotalWidth = screenWidth - (tvLeftWidth + tvRightWidth + paddingWidth + marginWidth);
        double high = listRight.get(0);
        double normal = listRight.get(1);
        double low = listRight.get(2);
        double highest = listRight.get(3);
        double average = listRight.get(4);
        double lowest = listRight.get(5);
        double totalCount = high + normal + low;
        GradientDrawable gradientDrawable;
        switch (position) {
            case 0:
                if (totalCount > 0) {
                    ViewGroup.LayoutParams layoutParams = tvCenter.getLayoutParams();
                    layoutParams.width = (int) ((high / totalCount) * tvCenterTotalWidth);
                    tvCenter.setLayoutParams(layoutParams);
                    gradientDrawable = DrawableUtils.getGradientDrawable(ColorUtils.getColor(R.color.main_red));
                    tvCenter.setBackground(gradientDrawable);
                }
                break;
            case 1:
                if (totalCount > 0) {
                    ViewGroup.LayoutParams layoutParams = tvCenter.getLayoutParams();
                    layoutParams.width = (int) ((normal / totalCount) * tvCenterTotalWidth);
                    tvCenter.setLayoutParams(layoutParams);
                    gradientDrawable = DrawableUtils.getGradientDrawable(ColorUtils.getColor(R.color.sugar_green_bright));
                    tvCenter.setBackground(gradientDrawable);
                }
                break;
            case 2:
                if (totalCount > 0) {
                    ViewGroup.LayoutParams layoutParams = tvCenter.getLayoutParams();
                    layoutParams.width = (int) ((low / totalCount) * tvCenterTotalWidth);
                    tvCenter.setLayoutParams(layoutParams);
                    gradientDrawable = DrawableUtils.getGradientDrawable(ColorUtils.getColor(R.color.home_blue));
                    tvCenter.setBackground(gradientDrawable);
                }
                break;
            case 3:
                ViewGroup.LayoutParams layoutParams3 = tvCenter.getLayoutParams();
                if (highest >= 33) {
                    layoutParams3.width = tvCenterTotalWidth;
                    tvCenter.setLayoutParams(layoutParams3);
                } else {
                    layoutParams3.width = (int) ((highest / 33) * tvCenterTotalWidth);
                    tvCenter.setLayoutParams(layoutParams3);
                }
                gradientDrawable = DrawableUtils.getGradientDrawable(ColorUtils.getColor(R.color.sugar_purple));
                tvCenter.setBackground(gradientDrawable);
                break;
            case 4:
                ViewGroup.LayoutParams layoutParams4 = tvCenter.getLayoutParams();
                if (average >= 33) {
                    layoutParams4.width = tvCenterTotalWidth;
                    tvCenter.setLayoutParams(layoutParams4);
                } else {
                    layoutParams4.width = (int) ((average / 33) * tvCenterTotalWidth);
                    tvCenter.setLayoutParams(layoutParams4);
                }
                gradientDrawable = DrawableUtils.getGradientDrawable(ColorUtils.getColor(R.color.sugar_green_light));
                tvCenter.setBackground(gradientDrawable);
                break;
            case 5:
                ViewGroup.LayoutParams layoutParams5 = tvCenter.getLayoutParams();
                if (lowest >= 33) {
                    layoutParams5.width = tvCenterTotalWidth;
                    tvCenter.setLayoutParams(layoutParams5);
                } else {
                    layoutParams5.width = (int) ((lowest / 33) * tvCenterTotalWidth);
                    tvCenter.setLayoutParams(layoutParams5);
                }
                gradientDrawable = DrawableUtils.getGradientDrawable(ColorUtils.getColor(R.color.sugar_blue_bright));
                tvCenter.setBackground(gradientDrawable);
                break;
        }

    }
}
