package com.vice.bloodpressure.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;


/**
 * 描述:   通知相关工具类
 * 作者: LYD
 * 创建日期: 2019/8/19 11:43
 */
public class NotificationUtils {
    private NotificationUtils() {
    }

    /**
     * 检查是否开启通知权限
     *
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {
        //判断应用是否开启了通知权限4.4以上可用
        //4.4以下默认返回true
        boolean isOpened = false;
        try {
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            isOpened = manager.areNotificationsEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOpened;
    }


    /**
     * 跳转到设置权限的页面
     *
     * @param context
     */
    public static void goToSettings(Context context) {
        try {
            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //这种方案适用于API 26, 即8.0（含8.0）以上可以用
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);
            //这种方案适用于API21——25，即5.0——7.1 之间的版本可以使用
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            //下面这种方案是直接跳转到当前应用的设置界面。
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }


}
