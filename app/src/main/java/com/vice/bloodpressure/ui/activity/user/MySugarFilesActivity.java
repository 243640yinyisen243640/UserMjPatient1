package com.vice.bloodpressure.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.HealthArchiveGroupLevelAdapter;
import com.vice.bloodpressure.adapter.MySugarFilesMedicineHistoryListAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.HealthArchiveGroupLevelOneBean;
import com.vice.bloodpressure.bean.HealthArchiveGroupLevelZeroBean;
import com.vice.bloodpressure.bean.PersonalRecordBean;
import com.vice.bloodpressure.bean.PersonalRecordMedicineHistoryBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.AdapterViewClickListener;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.healthrecordadd.PharmacyAddActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.TimeFormatUtils;
import com.vice.bloodpressure.utils.TurnsUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 糖尿病档案
 * 作者: LYD
 * 创建日期: 2019/5/13 16:59
 */
public class MySugarFilesActivity extends BaseHandlerEventBusActivity implements AdapterViewClickListener {
    private static final String TAG = "MySugarFilesActivity";
    private static final int GET_DATA = 10010;
    private static final int GET_DATA_MEDICINE_HISTORY = 10011;
    private static final int GET_DATA_MEDICINE_HISTORY_ERROR = 10012;
    @BindView(R.id.rv_health_archive)
    RecyclerView rvHealthArchive;
    //用药史
    @BindView(R.id.img_medicine_history)
    ImageView imgMedicineHistory;
    @BindView(R.id.ll_medicine_history)
    LinearLayout llMedicineHistory;
    @BindView(R.id.rv_medicine_history)
    RecyclerView rvMedicineHistory;
    @BindView(R.id.el_medicine_history)
    ExpandableLayout elMedicineHistory;


    //城市选择器
    CityPickerView mCityPickerView = new CityPickerView();
    private PersonalRecordBean personalRecordBean;
    private int sexInt;
    private HealthArchiveGroupLevelAdapter adapter;
    private MySugarFilesMedicineHistoryListAdapter medicineHistoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("个人档案");
        getData();
        //初始化城市选择器
        mCityPickerView.init(this);
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_my_files, contentLayout, false);
        return layout;
    }

    /**
     * 获取个人信息
     */
    private void getData() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", userLogin.getToken());
        XyUrl.okPost(XyUrl.PERSONAL_RECORD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //获取基本
                personalRecordBean = JSONObject.parseObject(value, PersonalRecordBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = personalRecordBean;
                sendHandlerMessage(message);
                //获取历史
                getHistory();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取用药史
     */
    private void getHistory() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", userLogin.getToken());
        XyUrl.okPost(XyUrl.PERSONAL_RECORD_MEDICINE_HISTORY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<PersonalRecordMedicineHistoryBean> historyBean = JSONObject.parseArray(value, PersonalRecordMedicineHistoryBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA_MEDICINE_HISTORY;
                message.obj = historyBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message message = getHandlerMessage();
                message.what = GET_DATA_MEDICINE_HISTORY_ERROR;
                sendHandlerMessage(message);
            }
        });
    }

    /***
     * 添加数据
     * @param data
     * @param list
     */
    private void addData(PersonalRecordBean data, List<PersonalRecordMedicineHistoryBean> list) {
        List<HealthArchiveGroupLevelZeroBean> lv0 = new ArrayList<>();
        //添加一级数据
        String[] stringRes = new String[]{"患者基本信息", "体格检查", "血糖情况",
                "实验室检查", "并发症/合并症", "既往病史"};
        for (int i = 0; i < stringRes.length; i++) {
            lv0.add(new HealthArchiveGroupLevelZeroBean(stringRes[i]));
        }
        //添加二级数据
        //患者基本信息
        String[] stringResOneLeft = new String[]{
                "真实姓名", "昵称", "性别", "出生日期",
                "民族", "糖尿病类型", "糖尿病确诊日期", "吸烟",
                "饮酒", "文化程度", "职业情况", "婚姻情况",
                "长期卧床", "籍贯", "住址", "医疗支付方式",
                "身份证号", "就诊卡号", "是否独居", "高血压诊断",
                "高血压确诊日期", "妊娠", "妊娠日期"};

        String nickname = data.getNickname();
        String petname = data.getPetname();
        String sex = data.getSex() + "";
        String birthTime = getTime(data.getBirthtime());

        String minzu = data.getMinzu();
        String diabeteslei = data.getDiabeteslei() + "";
        long diabetesleitime = data.getDiabetesleitime();
        String diabetesleiTime = getTime(diabetesleitime);

        String smoke = data.getSmoke() + "";

        String drink = data.getDrink() + "";
        String culture = data.getCulture() + "";
        String perofession = data.getProfession() + "";
        String marriage = data.getMarriage() + "";

        String bedrid = data.getBedrid() + "";
        String nativeplace = data.getNativeplace();
        String address = data.getAddress();
        String payWay = data.getZhifufangshi() + "";

        String idCard = data.getIdcard();
        String jzkahao = data.getJzkahao();
        String duju = data.getDuju() + "";
        String gxyzhenduan = data.getGxyzhenduan() + "";

        String gxyTime = getTime(data.getGxytime());
        String gestation = data.getGestation() + "";
        String gestationTime = getTime(data.getGestationtime());


        String[] stringResOneRight = new String[]{
                nickname, petname, sex, birthTime,
                minzu, diabeteslei, diabetesleiTime, smoke,
                drink, culture, perofession, marriage,
                bedrid, nativeplace, address, payWay,
                idCard, jzkahao, duju, gxyzhenduan,
                gxyTime, gestation, gestationTime};

        for (int i = 0; i < stringResOneLeft.length; i++) {
            HealthArchiveGroupLevelOneBean lv1 = null;
            switch (i) {
                //性别
                case 2:
                    String sexGet = stringResOneRight[i];
                    if ("1".equals(sexGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "男");
                        lv0.get(0).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "女");
                        lv0.get(0).addSubItem(lv1);
                    }
                    break;
                //糖尿病类型 1：1型  2：2型  3：妊娠  4其他
                case 5:
                    String tnbType = stringResOneRight[i];
                    switch (tnbType) {
                        case "0":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "无");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "1":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "1型");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "2型");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "妊娠");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "4":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "其他");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "未知");
                            lv0.get(0).addSubItem(lv1);
                            break;
                    }
                    break;
                case 7://吸烟   1是 2否
                case 8://喝酒
                case 12://长期卧床
                case 18://独居
                case 21://妊娠
                    String yesOrNo = stringResOneRight[i];
                    if ("1".equals(yesOrNo)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "是");
                        lv0.get(0).addSubItem(lv1);
                    } else if ("2".equals(yesOrNo)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "否");
                        lv0.get(0).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "请选择");
                        lv0.get(0).addSubItem(lv1);
                    }
                    break;
                case 9://文化程度 1 小学及以下 2初中 3高中 4大学及以上
                    String cultureGet = stringResOneRight[i];
                    switch (cultureGet) {
                        case "1":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "小学及以下");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "初中");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "高中");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "4":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "大学及以上");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "请选择");
                            lv0.get(0).addSubItem(lv1);
                            break;
                    }
                    break;
                case 10://1 轻体力 2 中体力 3 重体
                    String profession = stringResOneRight[i];
                    switch (profession) {
                        case "1":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "轻体力");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "中体力");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "重体力");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "请选择");
                            lv0.get(0).addSubItem(lv1);
                            break;
                    }
                    break;
                case 11://1未婚 2已婚
                    String marriageGet = stringResOneRight[i];
                    if ("1".equals(marriageGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "未婚");
                        lv0.get(0).addSubItem(lv1);
                    } else if ("2".equals(marriageGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "已婚");
                        lv0.get(0).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "请选择");
                        lv0.get(0).addSubItem(lv1);
                    }
                    break;
                case 15://医疗支付方式
                    String pay = stringResOneRight[i];
                    switch (pay) {
                        case "1":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "社会医疗基本保险");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "2":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "新型农村合作医疗保险");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "3":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "城镇居民医疗保险");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "4":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "商业保险");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "5":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "公费医疗");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "6":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "自费医疗");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        case "7":
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "其它");
                            lv0.get(0).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "请选择");
                            lv0.get(0).addSubItem(lv1);
                            break;
                    }
                    break;
                case 19: //高血压诊断
                    String gxyzhenduanGet = stringResOneRight[i];
                    if ("1".equals(gxyzhenduanGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "确诊有");
                        lv0.get(0).addSubItem(lv1);
                    } else if ("2".equals(gxyzhenduanGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "未诊断");
                        lv0.get(0).addSubItem(lv1);
                    } else if ("3".equals(gxyzhenduanGet)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "确诊无");
                        lv0.get(0).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], "请选择");
                        lv0.get(0).addSubItem(lv1);
                    }
                    break;
                default:
                    lv1 = new HealthArchiveGroupLevelOneBean(stringResOneLeft[i], stringResOneRight[i]);
                    lv0.get(0).addSubItem(lv1);
                    break;
            }
        }
        //体格检查展示
        String[] stringResTwoLeft = new String[]{
                "身高", "体重", "BMI", "腰围",
                "臀围", "腰臀比", "收缩压", "舒张压",
                "静息心率"};
        //身高体重bmi
        double height = TurnsUtils.getDouble(data.getHeight(), 1);
        double weight = TurnsUtils.getDouble(data.getWeight(), 1);
        double heightM = height * 0.01;
        double bmi;
        String bmiStr;
        if ("请输入  ".equals(data.getWeight()) || "请输入  ".equals(data.getHeight())) {
            bmiStr = "";
        } else {
            bmi = weight / (heightM * heightM);
            //保留一位小数
            String result = String.format("%.1f", bmi);
            bmiStr = result;
        }
        //腰围臀围 腰臀比
        double Waistline = TurnsUtils.getDouble(data.getWaistline(), 1);
        double Hipline = TurnsUtils.getDouble(data.getHipline(), 1);
        double wCompareH;
        String wCompareHStr;
        if ("请输入  ".equals(data.getWaistline()) || "请输入  ".equals(data.getHipline())) {
            wCompareHStr = "";
        } else {
            wCompareH = Waistline / Hipline;
            wCompareHStr = String.format("%.1f", wCompareH);
        }
        String[] stringResTwoRight = new String[]{
                data.getHeight(), data.getWeight(), bmiStr, data.getWaistline(),
                data.getHipline(), wCompareHStr, data.getSystolic(), data.getDiastolic(),
                data.getHeartrate()};
        String[] stringResTwoUnits = Utils.getApp().getResources().getStringArray(R.array.my_file_physique_unit);
        for (int i = 0; i < stringResTwoLeft.length; i++) {
            HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResTwoLeft[i], stringResTwoRight[i], stringResTwoUnits[i]);
            lv0.get(1).addSubItem(lv1);
        }
        //血糖情况展示
        String[] stringResThreeLeft = new String[]{
                "OGTT2h血糖", "H b A 1 c", "随机血糖", "空腹血糖",
                "餐 后2h 血 糖", "夜间血糖", "睡前血糖", "近期经常发生低血糖"};
        String[] stringResThreeRight = new String[]{
                data.getXtogtt2h(), data.getXthbalc(), data.getXtsuiji(), data.getXtkongfu(),
                data.getXtcaihou(), data.getXtyejian(), data.getXtshuiqian(),
                data.getXtdi()};
        String[] stringResThreeUnits = Utils.getApp().getResources().getStringArray(R.array.my_file_blood_sugar);
        for (int i = 0; i < stringResThreeLeft.length; i++) {
            if (i == 7) {
                String s = stringResThreeRight[i];
                if ("1".equals(s)) {
                    HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResThreeLeft[i], "是");
                    lv0.get(2).addSubItem(lv1);
                } else {
                    HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResThreeLeft[i], "否");
                    lv0.get(2).addSubItem(lv1);
                }
            } else {
                HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResThreeLeft[i], stringResThreeRight[i], stringResThreeUnits[i]);
                lv0.get(2).addSubItem(lv1);
            }
        }
        //实验室检查   尿常规（尿蛋白）  0无  1阴性  2阳性
        String[] stringResFourLeft = new String[]{
                "总胆固醇(TC)", "甘油三酯", "低密度脂蛋白(LDL-C)", "谷丙转氨酶(ALT)",
                "谷草转氨酶(AST)", "总胆红素(T-BIL)", "高密度脂蛋白(HDL-C)", "碱性磷酸酶(ALP)",
                "尿常规(尿蛋白)", "24小时尿白蛋白(24hUAE)", "尿白蛋白/肌酐(ACR)", "尿白蛋白(UAE)", "尿素氮(BUN)", "内生肌酐清除率(Ccr)"};
        String[] stringResFourRight = new String[]{
                data.getSytc(), data.getSytg(), data.getSyldl(), data.getSyalt(),
                data.getSyast(), data.getSytbil(), data.getSyhdl(), data.getSyalp(),
                data.getSyncg(), data.getSyhuae(), data.getSyacr(), data.getSyuae(),
                data.getSynsd(), data.getSynsjg(), data.getSyxqjg()};
        String[] stringResFourUnits = Utils.getApp().getResources().getStringArray(R.array.my_file_laboratory_unit);
        for (int i = 0; i < stringResFourLeft.length; i++) {
            HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResFourLeft[i], stringResFourRight[i], stringResFourUnits[i]);
            lv0.get(3).addSubItem(lv1);
        }
        sexInt = data.getSex();
        if (1 == sexInt) {
            HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean("血清肌酐(SRC)", data.getSyxqjg(), "mmol/L");
            lv0.get(3).addSubItem(lv1);
        } else {
            HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean("血清肌酐(SRC)", data.getSyxqjgg(), "mmol/L");
            lv0.get(3).addSubItem(lv1);
        }
        //并发症/合并症
        String[] stringResFiveLeft = new String[]{
                "糖尿病肾病", "糖尿病视网膜病变", "糖尿病神经病变", "糖尿病下肢血管病变",
                "糖尿病足", "糖尿病酮症酸中毒", "糖尿病高渗综合征"};
        String[] stringResFiveRight = new String[]{data.getNephropathy() + "", data.getRetina() + "", data.getNerve() + "", data.getLegs() + "",
                data.getDiabeticfoot() + "", data.getKetoacidosis() + "", data.getHypertonic() + ""};
        //1 确诊无 2 确诊有 3 未确诊
        for (int i = 0; i < stringResFiveLeft.length; i++) {
            String yesOrNo = stringResFiveRight[i];
            if ("1".equals(yesOrNo)) {
                HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResFiveLeft[i], "确诊无");
                lv0.get(4).addSubItem(lv1);
            } else if ("2".equals(yesOrNo)) {
                HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResFiveLeft[i], "确诊有");
                lv0.get(4).addSubItem(lv1);
            } else {
                HealthArchiveGroupLevelOneBean lv1 = new HealthArchiveGroupLevelOneBean(stringResFiveLeft[i], "未确诊");
                lv0.get(4).addSubItem(lv1);
            }
        }


        //既往病史
        String[] stringResSixLeft = new String[]{
                "高血压", "高脂血症", "冠心病", "脑血管病",
                "糖尿病家族史", "心血管疾病家族史"};
        String[] stringResSixRight = new String[]{
                data.getHighblood() + "", data.getHlp() + "", data.getCoronary() + "", data.getCerebrovascular() + "",
                data.getDiabetes() + "", data.getAngiocarpy() + ""};
        for (int i = 0; i < stringResSixLeft.length; i++) {
            HealthArchiveGroupLevelOneBean lv1 = null;
            switch (i) {
                case 0:
                    switch (data.getHighblood()) {
                        case 1://1 确诊无 2 确诊有 3 未确诊
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "确诊无");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 2:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "确诊有");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 3:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "未确诊");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "请选择");
                            lv0.get(5).addSubItem(lv1);
                            break;
                    }
                    break;
                case 1:
                    switch (data.getHighblood()) {
                        case 1://1 确诊无 2 确诊有 3 未确诊
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "确诊无");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 2:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "确诊有");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 3:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "未确诊");
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "请选择");
                            lv0.get(5).addSubItem(lv1);
                            break;
                    }
                    break;
                case 2:
                    switch (data.getHighblood()) {
                        case 1://1 确诊无 2 确诊有 3 未确诊
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "确诊无");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 2:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "确诊有");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 3:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "未确诊");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "请选择");
                            lv0.get(5).addSubItem(lv1);
                            break;
                    }
                    break;
                case 3:
                    switch (data.getHighblood()) {
                        case 1://1 确诊无 2 确诊有 3 未确诊
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "确诊无");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 2:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "确诊有");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        case 3:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "未确诊");
                            lv0.get(5).addSubItem(lv1);
                            break;
                        default:
                            lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "请选择");
                            lv0.get(5).addSubItem(lv1);
                            break;
                    }
                    break;
                case 4:
                case 5:
                    String yesOrNo = stringResSixRight[i];
                    if ("1".equals(yesOrNo)) {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "是");
                        lv0.get(5).addSubItem(lv1);
                    } else {
                        lv1 = new HealthArchiveGroupLevelOneBean(stringResSixLeft[i], "否");
                        lv0.get(5).addSubItem(lv1);
                    }
                    break;
            }
        }

        //添加0级数据
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getPageContext());
        ArrayList<MultiItemEntity> multiItemEntityArrayList = new ArrayList<>(lv0);
        adapter = new HealthArchiveGroupLevelAdapter(this, multiItemEntityArrayList, this);
        rvHealthArchive.setAdapter(adapter);
        //解决复用错乱
        rvHealthArchive.setItemViewCacheSize(23);
        rvHealthArchive.setLayoutManager(linearLayoutManager);


        //用药史设值
        medicineHistoryListAdapter = new MySugarFilesMedicineHistoryListAdapter(list);
        rvMedicineHistory.setLayoutManager(new LinearLayoutManager(getPageContext()));
        rvMedicineHistory.setAdapter(medicineHistoryListAdapter);


        medicineHistoryListAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                toDelMedicineHistory(list, position);
                return true;
            }
        });
        medicineHistoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = new Intent(getPageContext(), PharmacyAddActivity.class);
                PersonalRecordMedicineHistoryBean detailBean = list.get(position);
                intent.putExtra("detailBean", detailBean);
                startActivity(intent);
            }
        });
    }

    private void toDelMedicineHistory(List<PersonalRecordMedicineHistoryBean> list, int position) {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String token = userLogin.getToken();
        DialogUtils.getInstance().showNotCancelDialog(getPageContext(), "",
                "是否确认删除？", true, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        RxHttpUtils.createApi(Service.class)
                                .delMedicine(token, list.get(position).getId() + "")
                                .compose(Transformer.switchSchedulers())
                                .subscribe(new CommonObserver<BaseData>() {
                                    @Override
                                    protected void onError(String errorMsg) {

                                    }

                                    @Override
                                    protected void onSuccess(BaseData bean) {
                                        list.remove(position);
                                        medicineHistoryListAdapter.notifyDataSetChanged();
                                    }
                                });
                    }
                });
    }


    @Override
    public void adapterViewClick(BaseViewHolder help) {
        TextView tvLeft = help.getView(R.id.tv_left);
        TextView tvRight = help.getView(R.id.tv_right);
        String text = tvLeft.getText().toString();
        switch (text) {
            case "真实姓名":
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "真实姓名", "请输入真实姓名", text1 -> {
                    tvRight.setText(text1);
                    toDoSave("nickname", text1);
                });
                break;
            case "昵称":
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "昵称", "请输入昵称", text1 -> {
                    tvRight.setText(text1);
                    toDoSave("petname", text1);
                });
                break;
            case "性别":
                ArrayList<String> sexList = new ArrayList<>();
                sexList.add("男");
                sexList.add("女");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("男".equals(content)) {
                            toDoSave("sex", "1");
                        } else {
                            toDoSave("sex", "2");
                        }
                    }
                }, sexList);
                break;
            case "出生日期":
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                        long timeS = timeMs / 1000;
                        toDoSave("birthtime", timeS + "");
                    }
                });
                break;
            case "民族":
                String[] nations = Utils.getApp().getResources().getStringArray(R.array.nation_list);
                List<String> list = Arrays.asList(nations);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        toDoSave("minzu", content);
                    }
                }, list);
                break;
            case "糖尿病类型"://1：1型  2：2型  3：妊娠  4 其他
                ArrayList<String> diabetesTypeList = new ArrayList<>();
                diabetesTypeList.add("1型");
                diabetesTypeList.add("2型");
                diabetesTypeList.add("妊娠");
                diabetesTypeList.add("其他");
                diabetesTypeList.add("无");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        switch (content) {
                            case "1型":
                                toDoSave("diabeteslei", "1");
                                break;
                            case "2型":
                                toDoSave("diabeteslei", "2");
                                break;
                            case "妊娠":
                                toDoSave("diabeteslei", "3");
                                break;
                            case "其他":
                                toDoSave("diabeteslei", "4");
                                break;
                            case "无":
                                toDoSave("diabeteslei", "0");
                                break;
                        }
                    }
                }, diabetesTypeList);
                break;
            case "糖尿病确诊日期":
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                        long timeS = timeMs / 1000;
                        toDoSave("diabetesleitime", timeS + "");
                    }
                });
                break;
            case "吸烟":
                ArrayList<String> smokeList = new ArrayList<>();
                smokeList.add("是");
                smokeList.add("否");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("是".equals(content)) {
                            toDoSave("smoke", "1");
                        } else {
                            toDoSave("smoke", "2");
                        }
                    }
                }, smokeList);
                break;
            case "饮酒":
                ArrayList<String> drinkList = new ArrayList<>();
                drinkList.add("是");
                drinkList.add("否");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("是".equals(content)) {
                            toDoSave("drink", "1");
                        } else {
                            toDoSave("drink", "2");
                        }
                    }
                }, drinkList);
                break;
            case "文化程度"://1 小学及以下 2 初中 3 高中 4 大学及以上
                ArrayList<String> cultureTypeList = new ArrayList<>();
                cultureTypeList.add("小学及以下");
                cultureTypeList.add("初中");
                cultureTypeList.add("高中");
                cultureTypeList.add("大学及以上");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        switch (content) {
                            case "小学及以下":
                                toDoSave("culture", "1");
                                break;
                            case "初中":
                                toDoSave("culture", "2");
                                break;
                            case "高中":
                                toDoSave("culture", "3");
                                break;
                            case "大学及以上":
                                toDoSave("culture", "4");
                                break;
                        }
                    }
                }, cultureTypeList);
                break;
            case "职业情况"://1 轻体力 2 中体力 3 重体
                ArrayList<String> professionTypeList = new ArrayList<>();
                professionTypeList.add("轻体力");
                professionTypeList.add("中体力");
                professionTypeList.add("重体力");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        switch (content) {
                            case "轻体力":
                                toDoSave("profession", "1");
                                break;
                            case "中体力":
                                toDoSave("profession", "2");
                                break;
                            case "重体力":
                                toDoSave("profession", "3");
                                break;
                        }
                    }
                }, professionTypeList);
                break;
            case "婚姻情况"://婚姻情况 1 未婚 2 已婚
                ArrayList<String> marriageList = new ArrayList<>();
                marriageList.add("未婚");
                marriageList.add("已婚");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("未婚".equals(content)) {
                            toDoSave("marriage", "1");
                        } else {
                            toDoSave("marriage", "2");
                        }
                    }
                }, marriageList);
                break;
            case "长期卧床"://1是 2否
                ArrayList<String> bedridList = new ArrayList<>();
                bedridList.add("是");
                bedridList.add("否");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("是".equals(content)) {
                            toDoSave("bedrid", "1");
                        } else {
                            toDoSave("bedrid", "2");
                        }
                    }
                }, bedridList);
                break;
            case "籍贯":
                showCityPickerView(tvRight);
                break;
            case "住址":
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "住址", "请输入住址", text1 -> {
                    tvRight.setText(text1);
                    toDoSave("address", text1);
                });
                break;
            case "医疗支付方式":// 1 社会医疗基本保险 2 新型农村合作医疗保险 3 城镇居民医疗保险 4 商业保险 5 公费医疗 6 自费医疗 7 其它
                ArrayList<String> payList = new ArrayList<>();
                payList.add("社会医疗基本保险");
                payList.add("新型农村合作医疗保险");
                payList.add("城镇居民医疗保险");
                payList.add("商业保险");
                payList.add("公费医疗");
                payList.add("自费医疗");
                payList.add("其它");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        switch (content) {
                            case "社会医疗基本保险":
                                toDoSave("zhifufangshi", "1");
                                break;
                            case "新型农村合作医疗保险":
                                toDoSave("zhifufangshi", "2");
                                break;
                            case "城镇居民医疗保险":
                                toDoSave("zhifufangshi", "3");
                                break;
                            case "商业保险":
                                toDoSave("zhifufangshi", "4");
                                break;
                            case "公费医疗":
                                toDoSave("zhifufangshi", "5");
                                break;
                            case "自费医疗":
                                toDoSave("zhifufangshi", "6");
                                break;
                            case "其它":
                                toDoSave("zhifufangshi", "7");
                                break;
                        }
                    }
                }, payList);
                break;
            case "身份证号":
                DialogUtils.getInstance().showEditTextIdNumberDialog(getPageContext(), "身份证号", "请输入身份证号", text1 -> {
                    tvRight.setText(text1);
                    if (!RegexUtils.isIDCard18(text1)) {
                        ToastUtils.showShort("请输入合法的身份证号码");
                        return;
                    }
                    toDoSave("idcard", text1);
                });
                break;
            case "就诊卡号":
                DialogUtils.getInstance().showEditTextDialog(getPageContext(), "就诊卡号", "请输入就诊卡号", InputType.TYPE_CLASS_NUMBER, text1 -> {
                    tvRight.setText(text1);
                    toDoSave("jzkahao", text1);
                });
                break;
            case "是否独居"://独居：1是 2否
                ArrayList<String> dujuList = new ArrayList<>();
                dujuList.add("是");
                dujuList.add("否");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("是".equals(content)) {
                            toDoSave("duju", "1");
                        } else {
                            toDoSave("duju", "2");
                        }
                    }
                }, dujuList);
                break;
            case "高血压诊断"://高血压诊断   1 确诊有  2 未诊断
                ArrayList<String> gxyzhenduanList = new ArrayList<>();
                gxyzhenduanList.add("确诊有");
                gxyzhenduanList.add("未诊断");
                gxyzhenduanList.add("确诊无");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊有".equals(content)) {
                            toDoSave("gxyzhenduan", "1");
                        } else if ("未诊断".equals(content)) {
                            toDoSave("gxyzhenduan", "2");
                        } else if ("确诊无".equals(content)) {
                            toDoSave("gxyzhenduan", "3");
                        }
                    }
                }, gxyzhenduanList);
                break;
            case "高血压确诊日期":
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                        long timeS = timeMs / 1000;
                        toDoSave("gxytime", timeS + "");
                    }
                });
                break;
            case "妊娠"://妊娠 ：1是  2否
                ArrayList<String> gestationList = new ArrayList<>();
                gestationList.add("是");
                gestationList.add("否");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("是".equals(content)) {
                            toDoSave("gestation", "1");
                        } else {
                            toDoSave("gestation", "2");
                        }
                    }
                }, gestationList);
                break;
            case "妊娠日期":
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        long timeMs = TimeUtils.string2Millis(content, TimeFormatUtils.getDefaultFormat());
                        long timeS = timeMs / 1000;
                        toDoSave("gestationtime", timeS + "");
                    }
                });
                break;
            //第二部分
            ////////////////////////////////////////////////////
            case "身高"://height
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "身高", "请输入身高", text1 -> {
                    //tvRight.setText(text1);
                    SPStaticUtils.put("height", text1);
                    toDoSave("height", text1);
                    resetValue(help, tvRight, text1);
                    resetBmi(help);
                });
                break;
            case "体重"://weight
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "体重", "请输入体重", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    SPStaticUtils.put("weight", text1);
                    toDoSave("weight", text1);
                    resetBmi(help);
                });
                break;
            case "BMI"://计算,不可修改
                String weight = SPStaticUtils.getString("weight");
                String height = SPStaticUtils.getString("height");
                break;
            case "腰围"://waistline
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "腰围", "请输入腰围", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("waistline", text1);
                    SPStaticUtils.put("waistline", text1);
                    resetThr();
                });
                break;
            case "臀围"://hipline
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "臀围", "请输入臀围", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("hipline", text1);
                    SPStaticUtils.put("hipline", text1);
                    resetThr();
                });
                break;
            case "腰臀比"://计算,不可修改
                break;
            case "收缩压"://systolic
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "收缩压", "请输入收缩压", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("systolic", text1);
                });
                break;
            case "舒张压"://diastolic
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "舒张压", "请输入舒张压", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("diastolic", text1);
                });
                break;
            case "静息心率"://heartrate
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "静息心率", "请输入静息心率", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSave("heartrate", text1);
                });
                break;
            //第三部分
            //////////////////////////////////////////////////////////////////
            case "OGTT2h血糖"://xtogtt2h
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "OGTT2h血糖", "请输入OGTT2h血糖", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtogtt2h", text1);
                });
                break;
            case "H b A 1 c":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "H b A 1 c", "请输入H b A 1 c", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xthbalc", text1);
                });
                break;
            case "随机血糖":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "随机血糖", "请输入随机血糖", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtsuiji", text1);
                });
                break;
            case "空腹血糖":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "空腹血糖", "请输入空腹血糖", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtkongfu", text1);
                });
                break;
            case "餐 后2h 血 糖":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "餐 后2h 血 糖", "请输入餐 后2h 血 糖", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtcaihou", text1);
                });
                break;
            case "夜间血糖":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "夜间血糖", "请输入夜间血糖", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtyejian", text1);
                });
                break;
            case "睡前血糖":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "睡前血糖", "请输入睡前血糖", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("xtshuiqian", text1);
                });
                break;
            case "近期经常发生低血糖"://是否
                ArrayList<String> xtogtt2hList = new ArrayList<>();
                xtogtt2hList.add("是");
                xtogtt2hList.add("否");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("是".equals(content)) {
                            toDoSaveGlucose("xtdi", "1");
                        } else {
                            toDoSaveGlucose("xtdi", "2");
                        }
                    }
                }, xtogtt2hList);
                break;
            //第四部分
            /////////////////////////////////////////////////////
            case "总胆固醇(TC)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "总胆固醇(TC)", "请输入总胆固醇(TC)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("sytc", text1);
                });
                break;
            case "甘油三酯":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "甘油三酯", "请输入甘油三酯", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("sytg", text1);
                });
                break;
            case "低密度脂蛋白(LDL-C)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "低密度脂蛋白(LDL-C)", "请输入低密度脂蛋白(LDL-C)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syldl", text1);
                });
                break;
            case "谷丙转氨酶(ALT)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "谷丙转氨酶(ALT)", "请输入谷丙转氨酶(ALT)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syalt", text1);
                });
                break;
            case "谷草转氨酶(AST)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "谷草转氨酶(AST)", "请输入谷草转氨酶(AST)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syast", text1);
                });
                break;
            case "总胆红素(T-BIL)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "总胆红素(T-BIL)", "请输入总胆红素(T-BIL)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("sytbil", text1);
                });
                break;
            case "高密度脂蛋白(HDL-C)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "高密度脂蛋白(HDL-C)", "请输入高密度脂蛋白(HDL-C)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syhdl", text1);
                });
                break;
            case "碱性磷酸酶(ALP)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "碱性磷酸酶(ALP)", "请输入碱性磷酸酶(ALP)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syalp", text1);
                });
                break;
            case "尿常规(尿蛋白)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "尿常规(尿蛋白)", "请输入尿常规(尿蛋白)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syncg", text1);
                });
                break;
            case "24小时尿白蛋白(24hUAE)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "24小时尿白蛋白(24hUAE)", "请输入24小时尿白蛋白(24hUAE)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syhuae", text1);
                });
                break;
            case "尿白蛋白/肌酐(ACR)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "尿白蛋白/肌酐(ACR)", "请输入尿白蛋白/肌酐(ACR)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syacr", text1);
                });
                break;
            case "尿白蛋白(UAE)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "尿白蛋白(UAE)", "请输入尿白蛋白(UAE)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("syuae", text1);
                });
                break;
            case "尿素氮(BUN)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "尿素氮(BUN)", "请输入尿素氮(BUN)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("synsd", text1);
                });
                break;
            case "内生肌酐清除率(Ccr)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "内生肌酐清除率(Ccr)", "请输入内生肌酐清除率(Ccr)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    toDoSaveGlucose("synsjg", text1);
                });
                break;
            case "血清肌酐(SRC)":
                DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "血清肌酐(SRC)", "请输入血清肌酐(SRC)", text1 -> {
                    //tvRight.setText(text1);
                    resetValue(help, tvRight, text1);
                    if (1 == sexInt) {
                        //男性
                        toDoSaveGlucose("syxqjg", text1);
                    } else {
                        //女性
                        toDoSaveGlucose("syxqjgg", text1);
                    }
                });
                break;
            //第五部分
            ///////////////////////////////////////////
            case "糖尿病肾病"://nephropathy
                ArrayList<String> nephropathyList = new ArrayList<>();
                nephropathyList.add("确诊无");
                nephropathyList.add("确诊有");
                nephropathyList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("nephropathy", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("nephropathy", "2");
                        } else {
                            toDoSaveBINGFA("nephropathy", "3");
                        }
                    }
                }, nephropathyList);
                break;
            case "糖尿病视网膜病变"://retina
                ArrayList<String> retinaList = new ArrayList<>();
                retinaList.add("确诊无");
                retinaList.add("确诊有");
                retinaList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("retina", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("retina", "2");
                        } else {
                            toDoSaveBINGFA("retina", "3");
                        }
                    }
                }, retinaList);
                break;
            case "糖尿病神经病变"://nerve
                ArrayList<String> nerveList = new ArrayList<>();
                nerveList.add("确诊无");
                nerveList.add("确诊有");
                nerveList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("nerve", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("nerve", "2");
                        } else {
                            toDoSaveBINGFA("nerve", "3");
                        }
                    }
                }, nerveList);
                break;
            case "糖尿病下肢血管病变"://legs
                ArrayList<String> legsList = new ArrayList<>();
                legsList.add("确诊无");
                legsList.add("确诊有");
                legsList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("legs", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("legs", "2");
                        } else {
                            toDoSaveBINGFA("legs", "3");
                        }
                    }
                }, legsList);
                break;
            case "糖尿病足"://diabeticfoot
                ArrayList<String> diabeticfootList = new ArrayList<>();
                diabeticfootList.add("确诊无");
                diabeticfootList.add("确诊有");
                diabeticfootList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("diabeticfoot", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("diabeticfoot", "2");
                        } else {
                            toDoSaveBINGFA("diabeticfoot", "3");
                        }
                    }
                }, diabeticfootList);
                break;
            case "糖尿病酮症酸中毒"://ketoacidosis
                ArrayList<String> ketoacidosList = new ArrayList<>();
                ketoacidosList.add("确诊无");
                ketoacidosList.add("确诊有");
                ketoacidosList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("ketoacidosis", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("ketoacidosis", "2");
                        } else {
                            toDoSaveBINGFA("ketoacidosis", "3");
                        }
                    }
                }, ketoacidosList);
                break;
            case "糖尿病高渗综合征"://hypertonic
                ArrayList<String> hypertonicList = new ArrayList<>();
                hypertonicList.add("确诊无");
                hypertonicList.add("确诊有");
                hypertonicList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("hypertonic", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("hypertonic", "2");
                        } else {
                            toDoSaveBINGFA("hypertonic", "3");
                        }
                    }
                }, hypertonicList);
                break;
            //第六部分
            /////////////////////////////////////
            case "高血压"://highblood
                ArrayList<String> highbloodList = new ArrayList<>();
                highbloodList.add("确诊无");
                highbloodList.add("确诊有");
                highbloodList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("highblood", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("highblood", "2");
                        } else {
                            toDoSaveBINGFA("highblood", "3");
                        }
                    }
                }, highbloodList);
                break;
            case "高脂血症"://hlp
                ArrayList<String> hlpList = new ArrayList<>();
                hlpList.add("确诊无");
                hlpList.add("确诊有");
                hlpList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("hlp", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("hlp", "2");
                        } else {
                            toDoSaveBINGFA("hlp", "3");
                        }
                    }
                }, hlpList);
                break;
            case "冠心病"://coronary
                ArrayList<String> coronaryList = new ArrayList<>();
                coronaryList.add("确诊无");
                coronaryList.add("确诊有");
                coronaryList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("coronary", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("coronary", "2");
                        } else {
                            toDoSaveBINGFA("coronary", "3");
                        }
                    }
                }, coronaryList);
                break;
            case "脑血管病"://cerebrovascular
                ArrayList<String> cerebrovascularList = new ArrayList<>();
                cerebrovascularList.add("确诊无");
                cerebrovascularList.add("确诊有");
                cerebrovascularList.add("未诊断");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("确诊无".equals(content)) {
                            toDoSaveBINGFA("cerebrovascular", "1");
                        } else if ("确诊有".equals(content)) {
                            toDoSaveBINGFA("cerebrovascular", "2");
                        } else {
                            toDoSaveBINGFA("cerebrovascular", "3");
                        }
                    }
                }, cerebrovascularList);
                break;
            case "糖尿病家族史"://diabetes
                ArrayList<String> diabetesList = new ArrayList<>();
                diabetesList.add("是");
                diabetesList.add("否");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("是".equals(content)) {
                            toDoSave("diabetes", "1");
                        } else {
                            toDoSave("diabetes", "2");
                        }
                    }
                }, diabetesList);
                break;
            case "心血管疾病家族史"://angiocarpy
                ArrayList<String> angiocarpyList = new ArrayList<>();
                angiocarpyList.add("是");
                angiocarpyList.add("否");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvRight.setText(content);
                        if ("是".equals(content)) {
                            toDoSave("angiocarpy", "1");
                        } else {
                            toDoSave("angiocarpy", "2");
                        }
                    }
                }, angiocarpyList);
                break;
        }
    }


    /**
     * 展示城市选择器
     */
    private void showCityPickerView(TextView textView) {
        String textColor = "#057dff";
        CityConfig.WheelType mWheelType = CityConfig.WheelType.PRO_CITY_DIS;
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")
                .cancelTextColor(textColor)
                .confirTextColor(textColor)
                .visibleItemsCount(5)
                .province("河南")
                .city("郑州市")
                .district("金水区")
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .setCityWheelType(mWheelType)
                .setCustomItemLayout(R.layout.item_city)
                .setCustomItemTextViewId(R.id.item_city_name_tv)
                .setShowGAT(false)
                .build();
        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                if (province != null) {
                    String name = province.getName();
                    sb.append(name);
                    String id = province.getId();
                    toDoSave("jbprov", id);
                }
                if (city != null) {
                    String name = city.getName();
                    sb.append(name);
                    String id = city.getId();
                    toDoSave("jbcity", id);
                }
                if (district != null) {
                    String name = district.getName();
                    sb.append(name);
                    String id = district.getId();
                    toDoSave("jbdist", id);
                }
                toDoSave("nativeplace", sb.toString());
                textView.setText(sb);
            }

            @Override
            public void onCancel() {

            }
        });
        mCityPickerView.showCityPicker();
    }

    /**
     * 并发症/合并症  既往病史
     *
     * @param fieldName
     * @param fieldValue
     */
    private void toDoSaveBINGFA(String fieldName, String fieldValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("fieldname", fieldName);
        map.put("fieldvalue", fieldValue);
        XyUrl.okPostSave(XyUrl.PERSONAL_SAVE_BINGFA, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 血糖添加  实验室检查
     *
     * @param fieldName
     * @param fieldValue
     */
    private void toDoSaveGlucose(String fieldName, String fieldValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("fieldname", fieldName);
        map.put("fieldvalue", fieldValue);
        XyUrl.okPostSave(XyUrl.PERSONAL_SAVE_GLUCOSE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    /**
     * 添加 患者基本信息  体格检查
     *
     * @param fieldName
     * @param fieldValue
     */
    private void toDoSave(String fieldName, String fieldValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("fieldname", fieldName);
        map.put("fieldvalue", fieldValue);
        XyUrl.okPostSave(XyUrl.PERSONAL_SAVE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showShort(msg);
                //刷新本地保存的姓名
                if ("nickname".equals(fieldName)) {
                    LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                    loginBean.setNickname(fieldValue);
                    SharedPreferencesUtils.putBean(getPageContext(), SharedPreferencesUtils.USER_INFO, loginBean);
                }
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.ADD_MEDICINE:
                getHistory();
                break;
        }
    }


    private void resetValue(BaseViewHolder help, TextView textView, String value) {
        HealthArchiveGroupLevelOneBean bean = (HealthArchiveGroupLevelOneBean) adapter.getItem(help.getAdapterPosition());
        String unit = bean.getUnit();
        SpannableString spannableString = new SpannableString(value + " " + unit);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.gray_text));
        spannableString.setSpan(colorSpan, 0, value.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void resetBmi(BaseViewHolder help) {
        String height = SPStaticUtils.getString("height");
        String weight = SPStaticUtils.getString("weight");
        NumberFormat nf = NumberFormat.getNumberInstance();
        //保留两位小数
        nf.setMaximumFractionDigits(1);
        //如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.HALF_UP);
        if (height != "" && weight != "") {
            String str = nf.format(Double.valueOf(weight) / Double.valueOf(height) / Double.valueOf(height) * 10000);
            resetValue(help, adapter.getTvBmi(), str);
            SpannableString spannableString = new SpannableString(str + " " + "kg/m²");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.gray_text));
            spannableString.setSpan(colorSpan, 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            adapter.getTvBmi().setText(spannableString);
        }
    }

    private void resetThr() {
        String waistline = SPStaticUtils.getString("waistline");
        String hipline = SPStaticUtils.getString("hipline");
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        if (waistline != "" && hipline != "") {
            String str = nf.format(Double.valueOf(waistline) / Double.valueOf(hipline));
            adapter.getTvThr().setText(str);
        }
    }


    private String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strTime = "";
        if (0 == time) {
            strTime = "请选择";
        } else {
            strTime = TimeUtils.millis2String(time * 1000L, format);
        }
        return strTime;
    }


    @OnClick({R.id.ll_medicine_history, R.id.ll_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_medicine_history:
                toToggle(elMedicineHistory, imgMedicineHistory);
                break;
            case R.id.ll_add:
                Intent intent = new Intent(getPageContext(), PharmacyAddActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
        }
    }

    /**
     * 切换
     *
     * @param el
     * @param img
     */
    private void toToggle(ExpandableLayout el, ImageView img) {
        el.toggle();
        Bitmap bitmap = ImageUtils.getBitmap(R.drawable.right_arrow);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (el.isExpanded()) {
            Bitmap resizedBitmap = ImageUtils.rotate(bitmap, 90, width, height);
            img.setImageBitmap(resizedBitmap);
        } else {
            Bitmap resizedBitmap = ImageUtils.rotate(bitmap, 0, width, height);
            img.setImageBitmap(resizedBitmap);
        }
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                personalRecordBean = (PersonalRecordBean) msg.obj;
                break;
            //用药史有数据
            case GET_DATA_MEDICINE_HISTORY:
                List<PersonalRecordMedicineHistoryBean> list = (List<PersonalRecordMedicineHistoryBean>) msg.obj;
                addData(personalRecordBean, list);
                break;
            //用药史没有数据
            case GET_DATA_MEDICINE_HISTORY_ERROR:
                List<PersonalRecordMedicineHistoryBean> listEmpty = new ArrayList<>();
                addData(personalRecordBean, listEmpty);
                break;
        }
    }
}
