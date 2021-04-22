package com.vice.bloodpressure.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;

/**
 * 描述:  白名单工具类
 * 作者: LYD
 * 创建日期: 2019/12/20 14:46
 */
public class RequestIgnoreBatteryUtils {
    private static final String TAG = "RequestIgnoreBatteryUtils";

    private RequestIgnoreBatteryUtils() {
    }

    /**
     * 是否已经加入白名单
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isIgnore() {
        String appPackageName = AppUtils.getAppPackageName();
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) Utils.getApp().getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(appPackageName);
        }
        return isIgnoring;
    }

    /**
     * 申请白名单
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestIgnore() {
        String appPackageName = AppUtils.getAppPackageName();
        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + appPackageName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getApp().startActivity(intent);
    }


}
