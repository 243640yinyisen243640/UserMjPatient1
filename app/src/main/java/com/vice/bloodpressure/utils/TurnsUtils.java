package com.vice.bloodpressure.utils;

import android.text.TextUtils;

/**
 * 描述: 安全转换String为数值
 * 作者: LYD
 * 创建日期: 2019/3/12 20:36
 */
public class TurnsUtils {
    private TurnsUtils() {
    }

    /**
     * @param content
     * @param defaultNum 当输出内容为null 时需要返回的值
     * @return
     */
    public static int getInt(String content, int defaultNum) {
        if (TextUtils.isEmpty(content)) {
            return defaultNum;
        }
        try {
            int num = Integer.valueOf(content);//把字符串强制转换为数字
            return num;//如果是数字，返回True
        } catch (Exception e) {
            return -100;//如果抛出异常，返回False
        }
    }

    /**
     * @param content
     * @param defaultNum 当输出内容为null 时需要返回的值
     * @return
     */
    public static float getFloat(String content, float defaultNum) {
        if (TextUtils.isEmpty(content)) {
            return defaultNum;
        }
        try {
            float num = Float.valueOf(content);// 把字符串强制转换为数字
            return num;// 如果是数字，返回True
        } catch (Exception e) {
            return -100f;// 如果抛出异常，返回False
        }
    }

    /**
     * @param content
     * @param defaultNum 当输出内容为null时需要返回的值
     * @return
     */
    public static double getDouble(String content, double defaultNum) {
        if (TextUtils.isEmpty(content)) {
            return defaultNum;
        }
        try {
            double num = Double.valueOf(content);// 把字符串强制转换为数字
            return num;// 如果是数字，返回True
        } catch (Exception e) {
            return -100d;// 如果抛出异常，返回False
        }
    }


}
