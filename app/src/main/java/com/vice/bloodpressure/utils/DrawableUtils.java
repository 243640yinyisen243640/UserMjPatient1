package com.vice.bloodpressure.utils;

import android.graphics.drawable.GradientDrawable;

public class DrawableUtils {
    private DrawableUtils() {
    }

    /**
     * 获取一个GradientDrawable
     *
     * @param bgColor     背景颜色
     * @param borderWidth 边框宽度
     * @param borderColor 边框颜色
     * @return
     */
    public static GradientDrawable getGradientDrawable(float radius, int bgColor, int borderWidth, int borderColor) {
        //新建一个GradientDrawable对象
        GradientDrawable drawable = new GradientDrawable();
        //设置圆角半径
        drawable.setCornerRadius(radius);
        //设置圆角半径
        drawable.setCornerRadius(90);
        //设置背景的形状,默认就是矩形
        drawable.setShape(GradientDrawable.RECTANGLE);
        //设置背景色
        drawable.setColor(bgColor);
        //设置边框的厚度以及边框的颜色
        drawable.setStroke(borderWidth, borderColor);
        return drawable;
    }

    /**
     * @param bgColor
     * @param borderColor
     * @return
     */
    public static GradientDrawable getGradientDrawable(int bgColor, int borderColor) {
        return getGradientDrawable(5, bgColor, 1, borderColor);
    }

    /**
     * @param bgColor
     * @return
     */
    public static GradientDrawable getGradientDrawable(int bgColor) {
        return getGradientDrawable(90, bgColor, 1, bgColor);
    }


}
