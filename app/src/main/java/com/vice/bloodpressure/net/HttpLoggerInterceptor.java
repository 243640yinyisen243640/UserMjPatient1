package com.vice.bloodpressure.net;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络日志拦截器
 */
public class HttpLoggerInterceptor implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        //Log.e("okhttp", message);
        Log.e("okhttp", message);
    }
}

