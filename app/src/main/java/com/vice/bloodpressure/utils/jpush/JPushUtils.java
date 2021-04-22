//package com.vice.bloodpressure.utils.jpush;
//
//import com.blankj.utilcode.util.Utils;
//
//import cn.jpush.android.api.JPushInterface;
//
//public class JPushUtils {
//
//    private JPushUtils() {
//    }
//
//    /**
//     * 初始化
//     */
//    public static void init() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(Utils.getApp());
//    }
//
//
//    /**
//     * 设置别名
//     *
//     * @param alias
//     */
//    public static void setAlias(String alias) {
//        JPushInterface.setAlias(Utils.getApp(), 10086, alias);
//    }
//
//    /**
//     * 删除别名
//     */
//    public static void deleteAlias() {
//        JPushInterface.deleteAlias(Utils.getApp(), 10086);
//    }
//}
