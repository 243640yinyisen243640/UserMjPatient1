package com.lyd.baselib.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
    /**
     * 设置高度
     *
     * @param view
     * @param height
     */
    public static void setHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
    }

    /**
     * 设置宽度
     *
     * @param view
     * @param width
     */
    public static void setWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
    }
}
