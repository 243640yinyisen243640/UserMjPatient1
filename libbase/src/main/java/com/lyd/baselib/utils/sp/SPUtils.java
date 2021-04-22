package com.lyd.baselib.utils.sp;

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

/**
 * 描述: sp保存和读取对象,对象必须序列化
 * 作者: LYD
 * 创建日期: 2018/5/5 14:31
 */
public class SPUtils {
    private static final String NAME = "LYD";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存序列化的对象
     *
     * @param key
     * @param obj
     */
    public static void putBean(String key, Object obj) {
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
            throw new IllegalArgumentException("the obj must implement Serializble");
        }
    }

    /**
     * 读取序列化的对象
     *
     * @param key
     * @return
     */
    public static Object getBean(String key) {
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
}

