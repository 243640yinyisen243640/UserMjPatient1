package com.lyd.modulemall.utils;


import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.hjq.gson.factory.GsonFactory;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.modulemall.constant.MallConstantParam;

import rxhttp.wrapper.converter.GsonConverter;
import rxhttp.wrapper.param.Method;
import rxhttp.wrapper.param.RxHttp;

/**
 * RxHttp公共参数添加工具类
 */
public class RxHttpPublicParamsAddUtils {

    public static void initRxHttp() {
        //设置debug模式，默认为false，设置为true后，发请求，过滤"RxHttp"能看到请求日志
        RxHttp.setDebug(true);
        //获取单例的 Gson 对象（已处理容错）
        Gson gson = GsonFactory.getSingletonGson();
        GsonConverter gsonConverter = GsonConverter.create(gson);
        RxHttp.setConverter(gsonConverter);
        //获取公共参数Token
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String token = user != null ? user.getToken() : "";
        //添加测试token
        //String token = "66f4f16a3182ad32e1d8bcc713784b29";
        RxHttp.setOnParamAssembly(p -> {
            Method method = p.getMethod();
            if (method.isGet()) { //Get请求

            } else if (method.isPost()) { //Post请求

            }
            return p.add("access_token", token).add("version", MallConstantParam.SERVER_VERSION);
        });
    }

}
