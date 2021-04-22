package com.lyd.modulemall.net;

import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonParseException;
import com.lyd.baselib.utils.TurnsUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import rxhttp.wrapper.exception.ParseException;

/**
 * Http请求错误信息
 * User: ljx
 * Date: 2019-06-26
 * Time: 14:26
 */
public class ErrorInfo {
    //仅指服务器返回的错误码
    private int errorCode;

    public ErrorInfo(Throwable throwable) {
        //错误文案，网络错误、请求失败错误、服务器返回的错误等文案
        String errorMsg = "";
        if (throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            errorMsg = "网络连接不可用，请稍后重试";
        } else if (throwable instanceof TimeoutException || throwable instanceof InterruptedIOException) {
            errorMsg = "网络连接超时,请稍后重试";
        } else if (throwable instanceof JsonParseException || throwable instanceof JSONException) {
            //请求成功，但Json语法异常,导致解析失败
            errorMsg = "数据解析失败,请稍后重试";
        } else if (throwable instanceof ParseException) {
            //ParseException异常表明Code不是200
            String errorCode = throwable.getLocalizedMessage();
            this.errorCode = TurnsUtils.getInt(errorCode, 0);
            errorMsg = throwable.getMessage();
            if (TextUtils.isEmpty(errorMsg)) {
                //errorMsg为空，显示errorCode
                errorMsg = errorCode;
            }
        } else {
            errorMsg = "";
        }
        if (!TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showShort(errorMsg);
        }
    }

    /**
     * 获取错误码
     *
     * @return
     */
    public int getErrorCode() {
        return errorCode;
    }

}
