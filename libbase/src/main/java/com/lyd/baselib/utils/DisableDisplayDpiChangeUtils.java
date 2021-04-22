package com.lyd.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.Display;

import java.lang.reflect.Method;

/**
 * 禁用字体缩放
 */
public class DisableDisplayDpiChangeUtils {

    @SuppressLint("PrivateApi")
    private static int getDefaultDisplayDensity() {
        try {
            Class classZ = Class.forName("android.view.WindowManagerGlobal");
            Method getWindowManagerService = classZ.getMethod("getWindowManagerService");
            getWindowManagerService.setAccessible(true);
            Object invoke = getWindowManagerService.invoke(classZ);
            Method getInitialDisplayDensity = invoke.getClass().getMethod("getInitialDisplayDensity", int.class);
            getInitialDisplayDensity.setAccessible(true);
            Object densityDpi = getInitialDisplayDensity.invoke(invoke, Display.DEFAULT_DISPLAY);
            return (int) densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void disabledDisplayDpiChange(Context context) {
        int defaultDisplayDensity = getDefaultDisplayDensity();
        if (defaultDisplayDensity != -1) {
            Resources resources = context.getResources();
            Configuration configuration = resources.getConfiguration();
            configuration.densityDpi = defaultDisplayDensity;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
    }
}
