package com.lyd.baselib.constant;


/**
 * 描述: 常量接口
 * 作者: LYD
 * 创建日期: 2018/6/12 17:35
 */
public interface BaseConstantParam {
    String SERVER_VERSION = "201211";
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 10;

    int DEFAULT_REQUEST_SUCCESS = 200;
    /**
     * 网络请求 默认数据为空
     */
    int DEFAULT_NO_DATA = 30002;
    /**
     * token过期
     */
    int DEFAULT_TOKEN_EXPIRED = 20001;


    /**
     * EventBus Code(静态内部类)
     */
    final class EventCode {
        //患者添加
        public static final int MALL_FINISH_RO_MAIN_ACTIVITY = 20001;
        public static final int WE_CHAT_PAY_SUCCESS = 20002;
        public static final int WE_CHAT_PAY_FAILED = 20003;
        //订单搜索
        public static final int MALL_ORDER_SEARCH = 20004;
    }
}
