package com.lyd.baselib.utils;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.R;

/**
 * 描述: 常用TableLayout操作
 * 作者: LYD
 * 创建日期: 2019/9/27 9:33
 */
public class TableLayoutUtils {


    /**
     * 0)水平选项卡布局
     * 1)
     * android:layout_width="match_parent"
     * android:layout_height="45dp"
     */
    private TableLayoutUtils() {
    }

    /**
     * 添加垂直分割线
     *
     * @param tabLayout 需要添加的TabLayout
     * @param id        分割线资源文件
     * @param height    分割线高度 (和设置的值成反比)
     */
    public static void addVerticalDivider(TabLayout tabLayout, int id, float height) {
        //添加分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(Utils.getApp(), id));
        //设置分割线高度
        linearLayout.setDividerPadding(ConvertUtils.dp2px(height));
    }


    /**
     * 设置下划线圆角
     *
     * @param tabLayout
     * @param radius
     */
    public static void setIndicatorCornerRadius(TabLayout tabLayout, int radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(ConvertUtils.dp2px(radius));
        tabLayout.setSelectedTabIndicator(gradientDrawable);
    }

    /**
     * 去除TabLayout 点击背景
     *
     * @param tabLayout
     * @param context
     */
    public static void setTabRippleColor(TabLayout tabLayout, Context context) {
        tabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
    }

}
