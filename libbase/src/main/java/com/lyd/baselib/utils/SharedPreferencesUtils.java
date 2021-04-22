package com.lyd.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.blankj.utilcode.util.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class SharedPreferencesUtils {
    //SP的名称
    public static final String SP_NAME = "login";
    //用户信息
    public static final String USER_INFO = "user_info";
    //饮食第一项
    public static final String FOOD_FIRST = "food_first";
    //饮食第一项
    public static final String SPORT = "sport";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 存放实体类以及任意类型
     * 保存用户信息到本地
     *
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    public static void putBean(Context context, String key, Object obj) {
        if (obj instanceof Serializable) {
            SharedPreferences.Editor editor = getSharedPreferences(Utils.getApp()).edit();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0));
                editor.putString(key, string64);
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("the obj must implement Serializable");
        }
    }

    /**
     * 获取本地用户信息
     *
     * @param context
     * @param key
     * @return
     */
    public static Object getBean(Context context, String key) {
        Object obj = null;
        try {
            String base64 = getSharedPreferences(Utils.getApp()).getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 清空
     */
    public static void clear() {
        getSharedPreferences(Utils.getApp()).edit().clear().apply();
    }
}
