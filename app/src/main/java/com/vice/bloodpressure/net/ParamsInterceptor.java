package com.vice.bloodpressure.net;


import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;


/**
 * 描述: 公共参数拦截器
 * Interceptors
 * https://blog.csdn.net/ysy950803/article/details/94778870
 * https://blog.csdn.net/sinat_28884723/article/details/96479961
 * https://blog.csdn.net/wenyingzhi/article/details/80510249
 * 作者: LYD
 * 创建日期: 2019/12/25 14:25
 */
public class ParamsInterceptor implements Interceptor {
    private static final String TAG = "ParamsInterceptor";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //获取公共参数Token
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String token = user != null ? user.getToken() : "";
        //指定上传Json
        MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");
        //得到请求
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        //得到请求体
        RequestBody body = request.body();
        //创建缓存
        Buffer buffer = new Buffer();
        //将请求体内容写入缓存
        body.writeTo(buffer);
        //读取参数字符串
        String parameterStr = buffer.readUtf8();
        //转换并添加参数
        try {
            JSONObject jsonObject = JSONObject.parseObject(parameterStr);
            jsonObject.put("access_token", token);
            jsonObject.put("version", ConstantParam.SERVER_VERSION);
            parameterStr = jsonObject.toString();
            RequestBody requestBody = RequestBody.create(mediaTypeJson, parameterStr);
            request = requestBuilder.post(requestBody).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.proceed(request);
    }
}