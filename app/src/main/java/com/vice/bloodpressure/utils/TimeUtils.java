package com.vice.bloodpressure.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 描述: 日期工具类
 * 作者: LYD
 * 创建日期: 2019/10/23 20:44
 */
public class TimeUtils {
    private static final String TAG = "TimeUtils";

    private TimeUtils() {
    }

    /**
     * 当前日期加上天数后的日期
     *
     * @param num 为增加的天数
     * @return
     */
    public static String plusDay(int num) {
        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = sdf.format(date);
        //增加天数
        Calendar calendar = Calendar.getInstance();
        //num为增加的天数
        calendar.add(Calendar.DATE, num);
        date = calendar.getTime();
        //打印结果
        String endDate = sdf.format(date);
        return endDate;
    }

    /**
     * 获取日期间日期
     *
     * @param dateStart (2019-10-23)
     * @param dateEnd   (2019-10-29)
     * @return
     */
    public static List<Date> getBetweenDates(Date dateStart, Date dateEnd) {
        List<Date> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(dateStart);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(dateEnd);
        tempEnd.add(Calendar.DAY_OF_YEAR, 1);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    /**
     * 根据日期获取星期 （2019-10-23 ——> 星期三）
     *
     * @param dateTime
     * @return
     */
    public static String dateToWeek(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = format.parse(dateTime);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //一周的第几天
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


}
