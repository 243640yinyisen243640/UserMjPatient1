package com.vice.bloodpressure.net;

import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.BuildConfig;
import com.vice.bloodpressure.ui.activity.user.LoginActivity;
import com.vice.bloodpressure.utils.SPUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XyUrl {
    //主地址 Start
    private final static boolean EXTERNAL_RELEASE = BuildConfig.ENVIRONMENT;
    private final static String DOMAIN = "https://wt.app.wotongsoft.com";
    private final static String DOMAIN_TEST = "http://d.xiyuns.cn/";
    public final static String HOST_URL = EXTERNAL_RELEASE ? DOMAIN : DOMAIN_TEST;
    //主地址 End
    //运动Start
    //运动答题
    public final static String INDEX_ADD_SPORT_QUESTION = HOST_URL + "/addQuestion";
    //首页运动信息
    public final static String INDEX_SPORT = HOST_URL + "/indexSport";
    //运动添加
    public final static String INDEX_ADD_SPORT = HOST_URL + "/addSport";
    //运动历史
    public final static String INDEX_SPORT_LIST = HOST_URL + "/sportList";
    //运动历史
    public final static String INDEX_CHANGE_SPORT = HOST_URL + "/sportChange";
    //运动历史
    public final static String INDEX_DIET_REFRESH = HOST_URL + "/replaFood";
    //运动End


    //方案民族选择
    public final static String GET_NATION_LIST = HOST_URL + "/getnationnals";
    //解绑医生
    public final static String UN_BIND_DOC = HOST_URL + "/unBindDoc";
    public final static String CLEAR_USER = HOST_URL + "/clearUser";
    //商城开始
    public final static String MALL_GOOD_CLASSIFY = HOST_URL + "/goodsClassify";
    //app端商品列表及banner图
    public final static String MALL_BANNER_AND_GOODS_LIST = HOST_URL + "/lists";
    //商城结束

    //血糖删除
    public final static String DEL_BLOOD_SUGAR = HOST_URL + "/port/Record/delBloodsugar";
    //控制目标上传(上传以后 不进首页 跳转完善信息)
    public final static String SET_USERDATA = HOST_URL + "/index/index/setuserdata";
    //完善个人信息
    public final static String COMPLETE_PERSONAL_INFO = HOST_URL + "/personalInformation";
    //判断血压当月数量
    public final static String GET_BLOOD_DATA = HOST_URL + "/index/Blood/getdata";
    //首页获取控制目标
    public final static String GET_USERDATE = HOST_URL + "/index/index/getuserdata";
    //修改控制目标
    public final static String ADD_TARGET = HOST_URL + "/addTarget";
    //恢复血糖控制目标
    public final static String RESET_TARGET = HOST_URL + "/reasetApplay";
    //血糖测量时间点修改
    public final static String ADD_SUGAR_POINT = HOST_URL + "/addSugarPoint";
    //轮播图
    public final static String GET_ADV = HOST_URL + "/port/Advertising/getAdv";
    //资讯收藏和取消收藏
    public final static String INFORMATION_COLLECTION = HOST_URL + "/port/information/infoCollection";
    //添加血压
    public final static String ADD_BLOOD = HOST_URL + "/port/record/addbloodpressure";
    //获取血压数据
    public final static String QUERY_BLOOD = HOST_URL + "/port/record/getbloodpressure";
    //添加血糖
    public final static String ADD_SUGAR = HOST_URL + "/port/record/addbloodglucose";
    //添加BMI
    public final static String ADD_BMI = HOST_URL + "/port/record/addbmi";
    //获取BMI数据
    public final static String QUERY_BMI = HOST_URL + "/port/record/getbmi";
    //健康记录 添加用药
    public final static String ADD_PHARMACY = HOST_URL + "/port/record/addpharmacy";
    //档案 添加用药
    public final static String ADD_PHARMACY_RECORD = HOST_URL + "/port/Personal/addmedicine";
    //获取用药数据
    public final static String QUERY_PHARMACY = HOST_URL + "/port/record/getpharmacy";
    //添加运动
    public final static String ADD_SPORT = HOST_URL + "/port/record/addexercise";
    public final static String GET_SPORT = HOST_URL + "/port/Food/getsport";
    //获取运动数据
    public final static String QUERY_SPORT = HOST_URL + "/port/record/getexercise";
    //获取饮食数据
    public final static String QUERY_FOOD = HOST_URL + "/port/record/getdiet";
    //添加检查记录
    public final static String ADD_CHECK = HOST_URL + "/port/record/addexamine";
    //获取检查记录
    public final static String GET_EXAMINE = HOST_URL + "/port/record/getexamine";
    //添加糖化血红蛋白
    public final static String ADD_HEMOGLOBIN = HOST_URL + "/port/record/addhemoglobin";
    //获取糖化血红蛋白
    public final static String QUERY_HEMOGLOBIN = HOST_URL + "/port/record/gethemoglobin";
    //省份列表
    public final static String PROVINCES = HOST_URL + "/port/Hospitals/getProns";
    //城市列表
    public final static String CITYS = HOST_URL + "/port/Hospitals/getCitys";
    //医院列表
    public final static String HOSPITAL = HOST_URL + "/port/Hospitals/getHospital";
    //省份医院搜索
    public final static String SEARCH_PROVINCE_HOSPITAL = HOST_URL + "/port/Hospitals/searchPronhospital";
    //城市医院搜索
    public final static String SEARCH_CITY_HOSPITAL = HOST_URL + "/port/Hospitals/searchHospital";
    //医院详情
    public final static String HOSPITAL_DETAILS = HOST_URL + "/port/Hospitals/getHospitalinfo";
    //医院科室列表
    public final static String LEFT_SDEPS = HOST_URL + "/port/Hospitals/getDepartmentslist";
    //科室医生列表
    public final static String RIGHT_SDEPS = HOST_URL + "/port/Hospitals/getDocs";
    //患者添加医生
    public final static String ADD_DOCTOR = HOST_URL + "/port/Hospitals/userAdddoc";
    //我的医生
    public final static String MY_DOCTOR = HOST_URL + "/port/Hospitals/myDoctor";
    //医生详情
    public final static String DOCTOR_DETAILS = HOST_URL + "/port/Hospitals/getDocinfo";
    //扫描二维码
    public final static String GET_QRCODE_INFO = HOST_URL + "/qrcodeInfo";
    //医生建议
    public final static String DOCTOR_ADVICE = HOST_URL + "/port/Hospitals/docAdvice";
    //医生大讲堂
    public final static String DOCTOR_CLASS = HOST_URL + "/port/Hospitals/docClass";
    //我的医生最新公告条
    public final static String DEPARTMENT_FIVE = HOST_URL + "/port/Hospitals/depAnnouncement";
    //科室公告列表及详情
    public final static String DEPARTMENT_LIST_AND_DETAIL = HOST_URL + "/port/Hospitals/depAnnouncements";
    //预约住院列表
    public final static String GET_INHOSPITAL = HOST_URL + "/port/Inhospital/getInhospital";
    //预约详情
    public final static String GET_INFO = HOST_URL + "/port/Inhospital/getinfo";
    //预约住院信息提交
    public final static String SUBMIT_HOSPITAL = HOST_URL + "/port/Inhospital/addInhospital";
    //随访管理开始
    public final static String GET_FOLLOW_NEW = HOST_URL + "/port/Followmgt/getFollow";
    //随访查看
    public final static String GET_FOLLOW_DETAIL_NEW = HOST_URL + "/port/Followmgt/editFollow";
    //随访提交
    public final static String FOLLOW_ADD = HOST_URL + "/port/Followmgt/addFollow";
    //随访管理结束
    //上传图片
    public final static String UPLOAD_PHOTO = HOST_URL + "/port/uploadpic/uploadpictures";
    //获取融云token
    public final static String GET_IM_TOKEN = HOST_URL + "/index/index/getrongtoken";


    //家庭医生
    public final static String HOME_SIGN_IS_FAMILY = HOST_URL + "/index/Familysign/isFamily";

    //快速问诊详情
    public final static String VISIT_DETAILS = HOST_URL + "/port/visitdoc/visitDetails";
    //圈子首页接口
    public final static String CIRCLE_HOME = HOST_URL + "/port/Circle/circleshow";
    //发布文章评论接口
    public final static String CIRCLE_COMMENT = HOST_URL + "/port/Circle/comment";
    //问答圈子点击查看详情接口
    public final static String CIRCLE_DETAILS = HOST_URL + "/port/Circle/circledetails";
    //问答圈子点击评论查看详情接口
    public final static String COMMENT_DETAILS = HOST_URL + "/port/Circle/commentdetails";
    //回复评论接口
    public final static String COMMENT_REVERT = HOST_URL + "/port/Circle/commentrevert";
    //问答圈子我的消息接口
    public final static String CIRCLE_MYNEWS = HOST_URL + "/port/Circle/mynews";
    //添加饮食
    public final static String HEALTH_RECORD_ADD_FOOD = HOST_URL + "/port/Record/adddiet";
    //血糖保存
    public final static String PERSONAL_SAVE_GLUCOSE = HOST_URL + "/port/Personal/glucose";
    //既往病史添加
    public final static String PERSONAL_SAVE_BINGFA = HOST_URL + "/port/Personal/bingfa";

    //中医体质检测开始
    public final static String TCM_HOME_PAGE = HOST_URL + "/port/Physical/physical_detail";
    //获取最新减重处方
    public final static String GET_NEW_WEIGHT = HOST_URL + "/getNewWeight";
    public final static String TCM_LIST = HOST_URL + "/port/Physical/physical_list";
    //中医体质检测结束


    //个人中心开始
    //个人档案
    public final static String PERSONAL_RECORD = HOST_URL + "/port/Personal/personalshow";
    //用药记录
    public final static String PERSONAL_RECORD_MEDICINE_HISTORY = HOST_URL + "/port/Personal/medicine";
    //个人档案保存  个人资料编辑
    public final static String PERSONAL_SAVE = HOST_URL + "/port/Personal/personal";
    //头像上传
    public final static String UPLOAD_PHOTO_PIC = HOST_URL + "/index/myinfo/imgtipc";
    //版本更新
    public final static String GET_UPDATE = HOST_URL + "/port/Doctor/getVersion";
    //个人中心结束

    //智能决策开始
    //获取题目
    public final static String OBTAIN_PLAN = HOST_URL + "/index/plan/index";
    //药物详情
    public final static String GET_DRUGS_DETAIL = HOST_URL + "/index/plan/getdrugsinfo";
    //降压方案列表
    public final static String PLAN_GET_PLAN_LIST = HOST_URL + "/index/Plan/getPlanlist";
    //app端糖尿病病足列表及详情
    public final static String GET_BLOOD_GLUCOSE_FOLLOW = HOST_URL + "/port/Followmgt/BloodGlucoseFollow";
    //最近降压报告
    public final static String GET_NEW_REPORT = HOST_URL + "/index/plan/getNewrepot";
    //药物分组列表
    public final static String GET_DRUGS_GROUP = HOST_URL + "/index/plan/drugsGroup";
    //其他同类药物
    public final static String GET_DRUGS_OTHER = HOST_URL + "/index/Plan/getOtherDrugs";
    //更换药物
    public final static String DRUGS_CHANGE = HOST_URL + "/index/Plan/changeDrugs";
    //降压方案详情
    public final static String PLAN_GET_PLAN_DETAIL = HOST_URL + "/index/Plan/getPlandetails";
    //获取药物
    public final static String GET_DRUGS_GROUP_DETAIL = HOST_URL + "/index/plan/drugs";
    //处方数据实例
    public final static String ADD_PROFESSION = HOST_URL + "/port/doctor/addprofession";
    //高血压处方添加
    public final static String ADD_HBP = HOST_URL + "/port/doctor/addBpdrugprescript";
    //减重处方添加
    public final static String ADD_LOSE_WEIGHT = HOST_URL + "/addLoseWeight";
    //处方列表
    public final static String GET_DOCTOR_PROFESSION_LIST = HOST_URL + "/port/Doctor/professionList";
    //运动周报列表
    public final static String SPORT_REPORT_LIST = HOST_URL + "/sportReportList";
    //运动周报详情
    public final static String SPORT_REPORT_DETAIL = HOST_URL + "/weekReport";
    //减重处方列表
    public final static String GET_LOSE_WEIGHT_LIST = HOST_URL + "/weightLossPrescription";
    //获取最新处方信息
    public final static String GET_NEW_PROFESSION = HOST_URL + "/port/Doctor/getNewprofession";
    //智能决策结束

    //药品管理开始
    //药品一级分类
    public final static String GET_MEDICINE_LEVEL_ONE = HOST_URL + "/port/Medicine/showorally";
    //药品二级分类
    public final static String GET_MEDICINE_LEVEL_TWO = HOST_URL + "/port/Medicine/drugUplist";
    //药品列表
    public final static String GET_MEDICINE_LIST = HOST_URL + "/port/Medicine/druglist";
    //药品搜索
    public final static String GET_MEDICINE_SEARCH = HOST_URL + "/port/Medicine/searchDrugs";
    //药品管理结束

    //首页相关开始
    //血糖预警
    public final static String GET_BLOOD_TWORNING = HOST_URL + "/port/Worning/bloodTworning";
    //血压预警
    public final static String GET_BLOOD_YWORNING = HOST_URL + "/port/Worning/bloodYworning";
    //提醒消息列表
    public final static String GET_SHOW_MESSAGE = HOST_URL + "/port/Message/showmessage";
    //系统消息详情
    public final static String GET_PORT_MESSAGE_ALTERMESSAGE = HOST_URL + "/port/Message/altermessage";
    //系统消息已读接口
    public final static String GET_PORT_MESSAGE_EDIT = HOST_URL + "/port/Message/editMessage";
    //血压折线图(日期修改)
    public final static String GET_INDEX_BLOOD_INDEX = HOST_URL + "/index/Blood/index";
    //血糖折线图
    public final static String GET_INDEX_BUSGAR_INDEX = HOST_URL + "/index/Busgar/index";
    //bmi折线图
    public final static String GET_INDEX_BMI_INDEX = HOST_URL + "/index/bmi/index";
    //处理医生添加消息  (医生添加患者无需患者同意)
    //public final static String GET__PORT_MESSAGE_EDITRELATION = HOST_URL + "/port/Message/editRelation";
    //小工具(6个)
    //消息提醒条数
    public final static String GET__PORT_MESSAGE_WARNCOUNT = HOST_URL + "/port/message/warncount";
    //商品推荐
    public final static String GET_GOODS_RECOMMEND = HOST_URL + "/shop/Goods/recommended";
    //首页相关结束

    //资讯开始
    //首页资讯列表
    public final static String GET__PORT_INFORMATION_RECOMMEND = HOST_URL + "/port/Information/recommend";
    //首页血糖记录
    public final static String GET_HOME_BLOOD_SUGAR_LIST = HOST_URL + "/nearFuturebg";
    //资讯分类
    public final static String GET__PORT_INFORMATION_CATEGORY = HOST_URL + "/port/Information/category";
    //资讯文章列表
    public final static String GET__PORT_INFORMATION_INFOLIST = HOST_URL + "/port/Information/infoList";
    //资讯详情
    public final static String GET__PORT_INFORMATION_INFODETAILS = HOST_URL + "/port/Information/infoDetails";
    //资讯结束

    //知识库开始
    //首页知识库分类
    public final static String GET_KNOWLEDGE_CATEGORY = HOST_URL + "/port/Knows/getKnows";
    //知识库分类文章列表
    public final static String GET_KNOWLEDGE_LIST = HOST_URL + "/port/knows/knowsInfos";
    //知识库详情
    public final static String GET_KNOWLEDGE_DETAILS = HOST_URL + "/port/knows/knowsDetails";
    //知识库结束

    //智能分析开始
    //血压分析
    public final static String GET_INDEX_BLOOD_REPORTBP = HOST_URL + "/index/blood/reportbp";
    //月份列表
    public final static String GET_PORT_PERSONAL_MONTHLIST = HOST_URL + "/port/Personal/monthlist";
    //血糖分析
    public final static String GET_PORT_ANALYSIS_BLOOD = HOST_URL + "/port/analysis/blood";
    //智能分析结束


    //食材库开始
    //食材 一级分类
    public final static String GET_FOOD_CATEGORY = HOST_URL + "/port/Food/getFoodClass";
    //食材 二级分类
    public final static String GET_FOOD_LIST = HOST_URL + "/port/Food/showfood";
    //食物 详情
    public final static String GET_FOOD_DETAIL = HOST_URL + "/port/Food/foodDetails";
    //食物搜索
    public final static String GET_FOOD_SEARCH = HOST_URL + "/port/Food/soufood";
    //食材库结束

    //饮食方案开始
    //饮食方案添加
    public final static String DIET_PLAN_ADD = HOST_URL + "/port/Food/dietPlanAdd";
    //判断是否答过题
    public final static String DIET_LAST_DIET_PLAN = HOST_URL + "/port/Food/getLastDietPlan";
    //手动饮食添加
    public final static String MY_DIET_PLAN_ADD = HOST_URL + "/port/Food/myDietPlanAdd";
    //换我想吃
    public final static String CHANGE_MY_DIET = HOST_URL + "/port/Food/changMyDiet";
    //清除保存id(我自己选 和 换我想吃)
    public final static String CLEAR_IDS = HOST_URL + "/port/Food/clearIds";
    //早中午选择保存
    public final static String GREEN_ID_SAVE = HOST_URL + "/port/Food/greensidsSave";
    //菜换一换
    public final static String GREEN_CHANGE = HOST_URL + "/port/Food/greensChange";
    //菜谱一级分类
    public final static String GET_GREENS_CLASSIFY = HOST_URL + "/port/Food/getGreensClassify";
    //菜谱二级列表
    public final static String GET_GREENS_CLASSIFY_LIST = HOST_URL + "/port/Food/contrastGreens";
    //菜谱搜索
    public final static String GET_GREENS_SEARCH = HOST_URL + "/port/Food/greensSearch";
    //饮食方案结束

    //肝病营养开始
    //肝病记录列表
    public final static String HEPATOPATHY_PABULUM_LIVER_LIST = HOST_URL + "/port/Userdata/liverList";
    //肝病详情
    public final static String HEPATOPATHY_PABULUM_DETAIL = HOST_URL + "/port/Userdata/liverDetails";
    //添加血氧
    public final static String ADD_BLOOD_OXYGEN = HOST_URL + "/port/Record/addBloodox";
    //血氧记录
    public final static String GET_BLOOD_OXYGEN = HOST_URL + "/port/Record/getBloodox";
    //添加体重
    public final static String ADD_WEIGHT = HOST_URL + "/addweight";
    //体重列表
    public final static String GET_WEIGHT_LIST = HOST_URL + "/getweight";
    //体重图表
    public final static String GET_WEIGHT_CHART = HOST_URL + "/getweightNum";
    //肝病营养结束


    //账号安全开始
    //修改密码
    public final static String CHANGE_PWD = HOST_URL + "/index/Myinfo/editPass";
    //绑定身份证-账号安全(强制绑定传1,其他时候可传可不传)
    public final static String BIND_ID_NUMBER = HOST_URL + "/index/Myinfo/bindIdcard";
    //绑定手机号-账号安全
    public final static String BIND_PHONE_NUMBER = HOST_URL + "/index/Myinfo/bindMobile";
    //账号安全结束

    //登录注册开始
    //验证码
    public final static String SEND_CODE = HOST_URL + "/sendcode";
    //用户注册及快速登录 账号密码登录
    public final static String LOGIN_NEW = HOST_URL + "/login";
    //绑定密码
    public final static String SET_PWD = HOST_URL + "/index/Myinfo/setPass";
    //忘记密码
    public final static String RESET_PWD = HOST_URL + "/index/Login/reLogin";
    //登录注册结束
    //商城标题
    public final static String GET_SHOP_TITLE = HOST_URL + "/index/index/gettitlecs";
    //检验数据开始
    //返回各监测信息最新更新时间
    public final static String GET_EXAMINE_TIME = HOST_URL + "/port/record/getExamineTime";
    //获取监测信息（彩超，心电，CT，TR生化分析)
    public final static String GET_EXAMINE_INFO = HOST_URL + "/port/record/getExamineInfo";
    //获取血常规记录
    public final static String GET_EXAMINE_INFO_BLOOD = HOST_URL + "/port/record/getBloodRoutine";
    //获取血常规详情
    public final static String GET_EXAMINE_INFO_BLOOD_DETAIL = HOST_URL + "/port/record/detailBloodRoutine";
    //获取尿常规记录
    public final static String GET_EXAMINE_INFO_PISS = HOST_URL + "/port/record/getUrinaryRoutine";
    //尿常规详情
    public final static String GET_EXAMINE_INFO_PISS_DETAIL = HOST_URL + "/port/record/detailUrinaryRoutine";
    //获取电解质记录
    public final static String GET_ELECTROLYTE = HOST_URL + "/port/record/getElectrolyte";
    //检验数据结束

    //我的资讯收藏
    public final static String GET_NEWS_COLLECTION_LIST = HOST_URL + "/port/Information/myCollection";

    //预约挂号开始
    //可预约医院列表
    public final static String GET_HOSPITAL_LIST = HOST_URL + "/scheduling/Hospitals/hospitalList";
    //医院搜索
    public final static String GET_SEARCH_HOSPITAL = HOST_URL + "/scheduling/Hospitals/searchHospital";
    //预约医院科室列表
    public final static String GET_DEPARTMENT_LIST = HOST_URL + "/scheduling/Hospitals/departmentList";
    //科室可预约医生
    public final static String GET_SCHEDULE_DOC = HOST_URL + "/scheduling/Hospitals/scheduleDoc";
    //添加/编辑就诊人
    public final static String ADD_SCH_PATIENT = HOST_URL + "/scheduling/Schedule/addschPatient";
    //就诊人列表
    public final static String GET_SCH_PATIENTS_LIST = HOST_URL + "/scheduling/Schedule/schPatients";
    //医生排班详情
    public final static String GET_SCHEDULE_DETAIL = HOST_URL + "/scheduling/Hospitals/scheduleDetail";
    //提交预约信息
    public final static String ADD_SCHEDULING = HOST_URL + "/scheduling/Schedule/addScheduling";
    //预约详情
    public final static String GET_SCHEDULING = HOST_URL + "/scheduling/Schedule/getScheduling";
    //预约确认信息
    public final static String GET_SCHEDULE_INFO = HOST_URL + "/scheduling/Schedule/scheduleInfo";
    //我的预约
    public final static String GET_SCHEDULE_LIST = HOST_URL + "/scheduling/Schedule/scheduleList";
    //我的报告预约人列表
    public final static String GET_OFF_PATIENTS_LIST = HOST_URL + "/scheduling/Schedule/offPatients";
    //就诊医生建议
    public final static String GET_DOC_ADVICE_LIST = HOST_URL + "/port/Record/getDocAdvice";
    //我的报告预约人列表
    public final static String DEL_PATIENT_OF_TREAT = HOST_URL + "/scheduling/Schedule/delPatient";
    //取消/删除预约
    public final static String CANCEL_SCHEDULE = HOST_URL + "/scheduling/Schedule/cancelSchedule";
    //预约挂号结束

    //免疫荧光列表
    public final static String GET_IMMUNE = HOST_URL + "/port/Record/getImmune";
    //免疫荧光详情
    public final static String GET_DETAIL_IMMUNE = HOST_URL + "/port/Record/detailImmune";

    //凝血列表
    public final static String GET_INSTRUMENT_LIST = HOST_URL + "/port/Record/instrumentList";
    //凝血详情
    public final static String GET_DETAIL_INSTRUMENT = HOST_URL + "/port/Record/detailInstrument";

    //我的设备开始
    //绑定设备
    public final static String DEVICE_BIND = HOST_URL + "/port/Equipment/bindPequipment";
    //解锁设备
    public final static String DEVICE_UN_BIND = HOST_URL + "/port/Equipment/unbindPequipment";
    //设备查询
    public final static String DEVICE_SEARCH = HOST_URL + "/port/Personal/personalimei";
    //我的设备结束

    //家签开始
    //医生信息
    public final static String SIGN_DOCTOR_INFO = HOST_URL + "/port/Familysign/doctorinfo";
    //患者信息
    public final static String SIGN_PATIENT_INFO = HOST_URL + "/port/Familysign/userinfo";
    //患者信息
    public final static String SIGN_APPLY_DETAIL = HOST_URL + "/port/message/familyApplyDdetail";
    //家签结束

    //智能教育文章列表
    public final static String GET_SMART_EDUCATION_LIST = HOST_URL + "/port/Educations/myEducations";

    //肝病档案显示
    public final static String GET_LIVER_FILES = HOST_URL + "/port/Personal/hepatopathy";
    //肝病档案修改
    public final static String EDIT_LIVER_FILE = HOST_URL + "/port/Personal/hepatopathyEdit";
    //档案类型判断
    public final static String IS_LIVER_FILE = HOST_URL + "/port/Personal/archivestyle";


    //智能搜索 Start
    //标签列表
    public final static String GET_VISIT = HOST_URL + "/port/visitdoc/getvisit";
    //二级标签分类
    public final static String GET_VISITER = HOST_URL + "/port/visitdoc/getvisiter";
    //快速问诊搜索
    //public final static String SEARCHVISIT = HOST_URL + "/port/Visitdoc/searchVisit";
    //快速问诊列表
    public final static String VISITINFOS = HOST_URL + "/port/visitdoc/visitinfos";
    //智能搜索 End

    //健康记录删除
    public final static String HEALTH_RECORD_DEL = HOST_URL + "/port/Record/healthRecordsDel";


    //智能教育 Start
    //教育推荐
    public final static String SMART_EDUCATION_INDEX_SERIES = HOST_URL + "/indexSeries";
    //首页饮食
    public final static String SMART_EDUCATION_INDEX_DIET = HOST_URL + "/port/food/dietPlanIndex";
    //学习列表
    public final static String SMART_EDUCATION_LEARN_LIST = HOST_URL + "/learnsList";
    //系列文章(暂时不用)
    public final static String SMART_EDUCATION_ARTICLE_LIST = HOST_URL + "/articleList";
    //时长及已学课程统计
    public final static String SMART_EDUCATION_LEARN_TIME = HOST_URL + "/getStatistical";
    //分类第一次进入
    public final static String SMART_EDUCATION_CATTERY_FIRST_LEVEL_LIST = HOST_URL + "/sugarcategory";
    //子分类查看
    public final static String SMART_EDUCATION_CATTERY_SECOND_LEVEL_LIST = HOST_URL + "/getCategory";
    //系列搜索
    public final static String SMART_EDUCATION_SEARCH_LIST = HOST_URL + "/serchList";
    //分类系列列表
    public final static String SMART_EDUCATION_SERIES_LIST = HOST_URL + "/seriesList";
    //学习时长计算
    public final static String SMART_EDUCATION_SERIAS_CLASS = HOST_URL + "/seriasClass";
    //学习时长计算
    public final static String SMART_EDUCATION_LEARN_TIME_CALCULATE = HOST_URL + "/learnTime";
    //智能教育 End


    private static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");


    /**
     * 基本Okhttp请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void okPost(String url, Map<String, Object> params, final OkHttpCallBack<String> callback) {
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        //Json方式提交
        JSONObject json = new JSONObject(params);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json.toString());
        //提交
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpInstance.getInstance()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        try {
                            String res = response.body().string();
                            JSONObject object = new JSONObject(res);
                            String code = object.getString("code");
                            if ("200".equals(code)) {
                                String data = object.getString("data");
                                callback.onSuccess(data);
                            } else if ("20001".equals(code)) {
                                exit();
                            } else {
                                String msg = object.getString("msg");
                                callback.onError(Integer.valueOf(code), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 上传json数据
     *
     * @param url
     * @param json
     * @param callback
     */
    public static void okPostJson(String url, String json, final OkHttpCallBack<String> callback) {
        //showProgress();
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(body).build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //dismissProgress();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //dismissProgress();
                try {
                    String res = response.body().string();
                    JSONObject object = new JSONObject(res);
                    String code = object.getString("code");
                    if ("200".equals(code)) {
                        String msg = object.getString("msg");
                        callback.onSuccess(msg);
                    } else if ("20001".equals(code)) {
                        exit();
                    } else {
                        String msg = object.getString("msg");
                        callback.onError(Integer.valueOf(code), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 获取错误数据
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void okPostGetErrorData(String url, Map<String, Object> params, final OkHttpCallBack<String> callback) {
        //showProgress();
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        JSONObject json = new JSONObject(params);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json.toString());
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(body).build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //dismissProgress();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //dismissProgress();
                try {
                    String res = response.body().string();
                    JSONObject object = new JSONObject(res);
                    String code = object.getString("code");
                    if ("200".equals(code)) {
                        String data = object.getString("data");
                        callback.onSuccess(data);
                    } else if ("20001".equals(code)) {
                        exit();
                    } else {
                        String data = object.getString("data");
                        callback.onError(Integer.valueOf(code), data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * okhttp：post请求（保存添加）
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void okPostSave(String url, Map<String, Object> params, final OkHttpCallBack<String> callback) {
        //showProgress();
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        JSONObject json = new JSONObject(params);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json.toString());
        Request request = new Request.Builder()
                .url(url)//地址
                .post(body)//请求参数
                .build();
        OkHttpInstance.getInstance()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //dismissProgress();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //dismissProgress();
                        try {
                            String res = response.body().string();
                            JSONObject object = new JSONObject(res);
                            String code = object.getString("code");
                            if ("200".equals(code)) {
                                String msg = object.getString("msg");
                                callback.onSuccess(msg);
                            } else if ("20001".equals(code)) {
                                exit();
                            } else {
                                String msg = object.getString("msg");
                                callback.onError(Integer.valueOf(code), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * okhttp：post请求（保存添加）
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void loginOut(String url, Map<String, Object> params, final OkHttpCallBack<String> callback) {
        //showProgress();
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        JSONObject json = new JSONObject(params);
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json.toString());
        Request request = new Request.Builder()
                .url(url)//地址
                .post(body)//请求参数
                .build();
        OkHttpInstance.getInstance()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //dismissProgress();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //dismissProgress();
                        try {
                            String res = response.body().string();
                            JSONObject object = new JSONObject(res);
                            String code = object.getString("code");
                            if ("200".equals(code)) {
                                String msg = object.getString("msg");
                                callback.onSuccess(msg);
                            } else if ("20001".equals(code)) {
                                CloudPushService pushService = PushServiceFactory.getCloudPushService();
                                pushService.unbindAccount(new CommonCallback() {
                                    @Override
                                    public void onSuccess(String s) {

                                    }

                                    @Override
                                    public void onFailed(String s, String s1) {

                                    }
                                });
                                ToastUtils.showShort("请登陆");
                                //SPStaticUtils.clear();
                                SharedPreferencesUtils.clear();
                                SPUtils.clear();
                                ActivityUtils.finishAllActivities();
                                Intent intent = new Intent(Utils.getApp(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Utils.getApp().startActivity(intent);
                            } else {
                                String msg = object.getString("msg");
                                callback.onError(Integer.valueOf(code), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * okhttp：更改头像
     *
     * @param url
     * @param head
     * @param callback
     */
    public static void okHead(String url, String params, String head, final OkHttpCallBack<String> callback) {
        //showProgress();
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(head);
        builder.addFormDataPart("tipc", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        builder.addFormDataPart("access_token", params);
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //dismissProgress();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //dismissProgress();
                try {
                    String res = response.body().string();
                    JSONObject object = new JSONObject(res);
                    String code = object.getString("code");
                    if ("200".equals(code)) {
                        String msg = object.getString("data");
                        callback.onSuccess(msg);
                    } else if ("20001".equals(code)) {
                        exit();
                    } else {
                        String msg = object.getString("msg");
                        callback.onError(-1, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * okhttp：上传图片
     *
     * @param url
     * @param pList
     * @param callback
     */
    public static void okUpload(String url, String pList, final OkHttpCallBack<String> callback) {
        //showProgress();
        boolean connected = NetworkUtils.isConnected();
        if (!connected) {
            ToastUtils.showShort("网络连接不可用，请稍后重试");
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(pList);
        builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).post(requestBody).build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //dismissProgress();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //dismissProgress();
                try {
                    String res = response.body().string();
                    JSONObject object = new JSONObject(res);
                    String code = object.getString("code");
                    if ("200".equals(code)) {
                        String msg = object.getString("data");
                        callback.onSuccess(msg);
                    } else if ("20001".equals(code)) {
                        exit();
                    } else {
                        String msg = object.getString("msg");
                        callback.onError(-1, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 退出
     */
    private static void exit() {
        Log.e("BaseActivity", "发送广播");
        Intent intent = new Intent();
        intent.setAction("LoginOut");
        Utils.getApp().sendBroadcast(intent);
    }
}
