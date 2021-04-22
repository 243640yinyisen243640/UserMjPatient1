package com.lyd.baselib.utils;

import java.text.DecimalFormat;

/**
 * 描述: 金钱相关工具类
 * 作者: LYD
 * 创建日期: 2021/2/3 17:25
 */
public class MoneyUtils {

    /**
     * 格式化输出
     *
     * @param price
     * @return
     */
    public static String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //小数点后至少两位
        decimalFormat.setMinimumFractionDigits(2);
        //类似123.45
        decimalFormat.setGroupingSize(3);
        //向负无穷大的方向舍入
        //decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return decimalFormat.format(price);
    }
}
