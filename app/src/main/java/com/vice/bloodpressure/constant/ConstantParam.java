package com.vice.bloodpressure.constant;


import com.lyd.baselib.BuildConfig;

/**
 * 描述: 类常量
 * 作者: LYD
 * 创建日期: 2019/9/19 13:58
 */
public class ConstantParam {
    public static final String SERVER_VERSION = "210223";
    private static final boolean EXTERNAL_RELEASE = BuildConfig.ENVIRONMENT;
//    private static final String IM_KEY_RELEASE = "x18ywvqfxbd8c";
    private static final String IM_KEY_RELEASE = "z3v5yqkbz2ei0";
    private static final String IM_KEY_DEBUG = "e0x9wycfe4jxq";
    public static final String IM_KEY = EXTERNAL_RELEASE ? IM_KEY_RELEASE : IM_KEY_DEBUG;
    /**
     * 注册问题 key
     */
    public static final String REGISTER_QUESTION_KEY = "register_question_key";
    /**
     * 暂无数据
     */
    public static final int NO_DATA = 30002;

    /**
     * 小米推送key开始
     */
    public static final String MI_PUSH_ID = "2882303761517853353";
    public static final String MI_PUSH_KEY = "5511785339353";
    /**
     * OPPOkey开始
     */
    public static final String OPPO_PUSH_KEY = "63d86bfd3a5441929cc66f5f11e9dabb";
    public static final String OPPO_PUSH_APP_SECRET = "91f24bf3f35b4e0daffeb0625e100f1f";
    /**
     * Bugly开始
     */
    public static final String BUGLY_APP_ID = "a5be6a30bf";

    /**
     * EventBus Code 开始
     */
    //选择食物
    public static final int FOOD_SELECT = 1000;
    //更换药物
    public static final int CHANGE_MEDICINE = 1001;
    //发送GroupID
    public static final int SEND_GROUP_ID = 1003;
    //添加用药
    public static final int ADD_MEDICINE = 1004;
    //手动添加血压 高血压
    public static final int BLOOD_PRESSURE_ADD_HIGH = 1006;
    //手动添加血压 低血压
    public static final int BLOOD_PRESSURE_ADD_LOW = 1007;
    //手动添加血压 时间
    public static final int BLOOD_PRESSURE_ADD_TIME = 1008;
    //手动添加身高
    public static final int BMI_HEIGHT = 1009;
    //手动添加体重
    public static final int BMI_WEIGHT = 1010;
    //手动添加血糖
    public static final int BLOOD_SUGAR_ADD = 1011;
    //手动添加血糖
    public static final int FOLLOW_UP_VISIT_SUBMIT = 1012;
    //添加肝病营养
    public static final int HEPATOPATHY_PABULUM_ADD = 1013;
    //修改或者绑定手机号
    public static final int CHANG_BIND_PHONE = 1014;
    //修改或者绑定手机号
    public static final int BIND_ID_NUMBER = 1015;
    //添加或者编辑就诊人
    public static final int PATIENT_ADD_OR_EDIT = 1017;
    //添加血压记录
    public static final int BLOOD_PRESSURE_RECORD_ADD = 1018;
    //添加饮食记录
    public static final int FOOD_RECORD_ADD = 1019;
    //添加运动记录
    public static final int SPORT_RECORD_ADD = 1020;
    //添加用药记录
    public static final int PHARMACY_RECORD_ADD = 1021;
    //添加BMI记录
    public static final int BMI_RECORD_ADD = 1022;
    //添加糖化血红蛋白记录
    public static final int HEMOGLOBIN_RECORD_ADD = 1023;
    //添加检查记录
    public static final int CHECK_RECORD_ADD = 1024;
    //添加患者消息更新
    public static final int WARN_COUNT_REFRESH = 1025;
    //预约刷新
    public static final int APPOINT_REFRESH = 1026;
    //食材库搜索
    public static final int FOOD_SEARCH = 1027;
    //菜谱搜索
    public static final int GREEN_SEARCH = 1028;
    //食材库刷新
    public static final int FOOD_REFRESH = 1029;
    //菜谱刷新
    public static final int GREEN_REFRESH = 1030;
    //标题更新
    public static final int DIET_PLAN_QUESTION_TITLE = 1031;
    //饮食选择Fragment传值到Activity
    public static final int DIET_PLAN_SELECT_FRAGMENT_TO_ACTIVITY = 1032;
    //搜索后选中
    public static final int SEARCH_TO_SELECT = 1033;
    //刷新
    public static final int DIET_PLAN_CHANGE_REFRESH = 1034;
    //删除血糖
    public static final int DEL_SUGAR_REFRESH = 1035;
    //添加血氧
    public static final int ADD_BLOOD_OXYGEN = 1036;
    //添加体重
    public static final int ADD_WEIGHT = 1037;
    //头像刷新
    public static final int RONG_HEAD_REFRESH = 1038;
    //解绑后刷新
    public static final int RONGIM_RED_POINT_REFRESH = 1039;
    //刷新智能教育数量
    public static final int SMART_LOOK_COUNT_REFRESH = 1040;
    //首页新闻刷新
    public static final int NEWS_LOOK_COUNT_REFRESH = 1041;
    //智能教育 课程状态刷新
    public static final int SMART_EDUCATION_COURSE_REFRESH = 1042;
    //运动答题 标题更新
    public static final int HOME_SPORT_QUESTION_TITLE = 1043;
    //运动答题
    public static final int HOME_SPORT_QUESTION_SUBMIT = 1044;
    //饮食答题
    public static final int HOME_DIET_QUESTION_SUBMIT = 1045;
    //首页智能教育刷新
    public static final int HOME_EDUCATION_REFRESH = 1046;
    //
    public static final int HOME_TO_OUTSIDE = 1047;
    //食物类型 选中发送
    public static final int FOOD_TYPE_SELECT_SEND = 1048;
    //食物类型 选中发送
    public static final int FOOD_TYPE_SEND = 1049;


    /**
     * EventBus Code 结束
     */

    public enum SendCodeType {
        //验证码类型 1 注册 2 快捷登录 3 密码重置 4绑定手机 5 更换手机 默认为1
        REGISTER(1, "注册"),
        QUICK_LOGIN(2, "快捷登录"),
        RESET_PWD(3, "密码重置"),
        BIND_PHONE_NUMBER(4, "绑定手机"),
        CHANGE_PHONE_NUMBER(5, "更换手机");
        private Integer name;
        private String value;

        SendCodeType(Integer name, String value) {
            this.name = name;
            this.value = value;
        }


        public Integer getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

    }
}
