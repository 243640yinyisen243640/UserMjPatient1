package com.vice.bloodpressure.ui.activity.followupvisit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.GifSizeFilter;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.AddPicAdapter;
import com.vice.bloodpressure.adapter.FollowUpVisitBloodSugarAddAdapter;
import com.vice.bloodpressure.adapter.FollowUpVisitBloodSugarMedicineAddAdapter;
import com.vice.bloodpressure.adapter.FollowUpVisitRvAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.FollowUpVisitBloodSugarAddBean;
import com.vice.bloodpressure.bean.FollowUpVisitBloodSugarDataBean;
import com.vice.bloodpressure.bean.FollowUpVisitLvBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.AdapterClickImp;
import com.vice.bloodpressure.imp.AdapterClickSearchImp;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.OkHttpInstance;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.ListCastUtils;
import com.vice.bloodpressure.utils.PickerUtils;
import com.vice.bloodpressure.utils.TurnsUtils;
import com.vice.bloodpressure.view.MyListView;
import com.vice.bloodpressure.view.popu.FollowUpVisitSavePopup;
import com.vice.bloodpressure.view.popu.SlideFromBottomPopup;
import com.wei.android.lib.colorview.view.ColorEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * 描述:血糖提交
 * 作者: LYD
 * 创建日期: 2019/7/26 10:09
 */
public class FollowUpVisitBloodSugarSubmitActivity extends BaseHandlerActivity implements View.OnClickListener, AdapterClickImp, AdapterClickSearchImp {
    private static final int GET_FOLLOW_UP_VISIT_DETAIL = 10010;
    private static final String TAG = "FollowUpVisitBloodSugarSubmitActivity";
    private static final int SAVE_RIGHT = 0;
    private static final int SAVE_LEFT = 1;
    private static final int BACK_RIGHT = 2;
    private static final int BACK_LEFT = 3;
    private static final int REQUEST_CAMERA = 1000;
    private static final int REQUEST_CODE_CHOOSE_LIVER = 1998;
    private static final int REQUEST_CODE_CHOOSE_HEART = 2998;
    private static final int REQUEST_CODE_CHOOSE_EYES = 3998;
    private static final int REQUEST_CODE_CHOOSE_NEUROPATHY = 4998;
    private static final int LIVER = 1111;
    private static final int HEART = 2222;
    private static final int EYES = 3333;
    private static final int NEUROPATHY = 4444;
    private static final int COMPRESS_OVER = 8888;
    private static final int ADD_FOLLOW_OVER = 9999;
    //标题栏开始
    @BindView(R.id.tv_title_new)
    TextView tvTitle;
    @BindView(R.id.tv_more_new)
    TextView tvMore;
    //标题栏结束
    @BindView(R.id.tv_top_add_time)
    TextView tvTopAddTime;
    @BindView(R.id.tv_top_submit_time)
    TextView tvTopSubmitTime;
    @BindView(R.id.et_summary_main_question)
    ColorEditText etSummaryMainQuestion;
    //    @BindView(R.id.et_summary_improve_measure)
    //    ColorEditText etSummaryImproveMeasure;
    @BindView(R.id.et_summary_main_purpose)
    ColorEditText etSummaryMainPurpose;
    //总结
    @BindView(R.id.ll_summary)
    LinearLayout llSummary;
    @BindView(R.id.rv_symptom)
    RecyclerView rvSymptom;
    //症状
    @BindView(R.id.ll_symptom)
    LinearLayout llSymptom;
    //体征
    @BindView(R.id.et_physical_sign_high)
    ColorEditText etPhysicalSignHigh;
    @BindView(R.id.et_physical_sign_low)
    ColorEditText etPhysicalSignLow;
    @BindView(R.id.et_height)
    ColorEditText etHeight;
    @BindView(R.id.et_weight)
    ColorEditText etWeight;
    @BindView(R.id.tv_bmi)
    TextView tvBmi;
    @BindView(R.id.rb_physical_sign_not)
    RadioButton rbPhysicalSignNot;
    @BindView(R.id.rb_physical_sign_have)
    RadioButton rbPhysicalSignHave;
    @BindView(R.id.rb_physical_sign_not_second)
    RadioButton rbPhysicalSignNotSecond;
    @BindView(R.id.et_physical_other)
    ColorEditText etPhysicalOther;

    @BindView(R.id.ll_physical_sign)
    LinearLayout llPhysicalSign;
    @BindView(R.id.et_smoke)
    ColorEditText etSmoke;
    @BindView(R.id.et_drink)
    ColorEditText etDrink;
    @BindView(R.id.et_sport_count)
    ColorEditText etSportCount;
    @BindView(R.id.et_sport_time)
    ColorEditText etSportTime;
    @BindView(R.id.et_main_food)
    ColorEditText etMainFood;
    @BindView(R.id.rb_psychological_adjust_well)
    RadioButton rbPsychologicalAdjustWell;
    @BindView(R.id.rb_psychological_adjust_common)
    RadioButton rbPsychologicalAdjustCommon;
    @BindView(R.id.rb_psychological_adjust_bad)
    RadioButton rbPsychologicalAdjustBad;
    @BindView(R.id.rb_follow_doctor_well)
    RadioButton rbFollowDoctorWell;
    @BindView(R.id.rb_follow_doctor_common)
    RadioButton rbFollowDoctorCommon;
    @BindView(R.id.rb_follow_doctor_bad)
    RadioButton rbFollowDoctorBad;
    //生活方式
    @BindView(R.id.ll_life_style)
    LinearLayout llLifeStyle;
    @BindView(R.id.et_empty_sugar)
    ColorEditText etEmptySugar;
    @BindView(R.id.et_blood_red)
    ColorEditText etBloodRed;
    @BindView(R.id.tv_check_time)
    TextView tvCheckTime;
    //辅助检查
    @BindView(R.id.ll_examine)
    LinearLayout llExamine;
    @BindView(R.id.rb_drug_use_yield_rule)
    RadioButton rbDrugUseYieldRule;
    @BindView(R.id.rb_drug_use_yield_gap)
    RadioButton rbDrugUseYieldGap;
    @BindView(R.id.rb_drug_use_yield_not_take_medicine)
    RadioButton rbDrugUseYieldNotTakeMedicine;
    @BindView(R.id.rg_adverse_drug_reaction_have)
    RadioButton rgAdverseDrugReactionHave;
    @BindView(R.id.rg_adverse_drug_reaction_not)
    RadioButton rgAdverseDrugReactionNot;
    @BindView(R.id.rb_hypoglycemic_reaction_not)
    RadioButton rbHypoglycemicReactionNot;
    @BindView(R.id.rb_hypoglycemic_reaction_once)
    RadioButton rbHypoglycemicReactionOnce;
    @BindView(R.id.rb_hypoglycemic_reaction_often)
    RadioButton rbHypoglycemicReactionOften;
    @BindView(R.id.rb_classify_satisfaction)
    RadioButton rbClassifySatisfaction;
    @BindView(R.id.rb_classify_satisfaction_not)
    RadioButton rbClassifySatisfactionNot;
    @BindView(R.id.rg_drug_use_classify)
    RadioGroup rgDrugUseClassify;
    @BindView(R.id.rb_classify_adverse_reaction)
    RadioButton rbClassifyAdverseReaction;
    @BindView(R.id.rb_classify_complication)
    RadioButton rbClassifyComplication;
    @BindView(R.id.rg_drug_use_classify_second)
    RadioGroup rgDrugUseClassifySecond;
    @BindView(R.id.lv_drug_use)
    MyListView lvDrugUse;
    @BindView(R.id.et_insulin)
    ColorEditText etInsulin;
    @BindView(R.id.et_insulin_dosage)
    ColorEditText etInsulinDosage;
    //用药情况
    @BindView(R.id.ll_drug_use)
    LinearLayout llDrugUse;
    //血糖
    @BindView(R.id.lv_seven_day_blood_sugar)
    ListView lvSevenDayBloodSugar;
    @BindView(R.id.ll_blood_sugar)
    LinearLayout llBloodSugar;
    //尿常规
    @BindView(R.id.ll_piss_common)
    LinearLayout llPissCommon;
    @BindView(R.id.et_piss_common_one_left)
    ColorEditText etPissCommonOneLeft;
    @BindView(R.id.et_piss_common_one_right)
    ColorEditText etPissCommonOneRight;
    @BindView(R.id.et_piss_common_two_left)
    ColorEditText etPissCommonTwoLeft;
    @BindView(R.id.et_piss_common_two_right)
    ColorEditText etPissCommonTwoRight;
    @BindView(R.id.et_piss_common_three_left)
    ColorEditText etPissCommonThreeLeft;
    @BindView(R.id.et_piss_common_three_right)
    ColorEditText etPissCommonThreeRight;
    @BindView(R.id.et_piss_common_four_left)
    ColorEditText etPissCommonFourLeft;
    @BindView(R.id.et_piss_common_four_right)
    ColorEditText etPissCommonFourRight;
    @BindView(R.id.et_piss_common_five_left)
    ColorEditText etPissCommonFiveLeft;
    @BindView(R.id.et_piss_common_five_right)
    ColorEditText etPissCommonFiveRight;
    @BindView(R.id.et_piss_common_six_left)
    ColorEditText etPissCommonSixLeft;
    //血脂
    @BindView(R.id.ll_blood_fat)
    LinearLayout llBloodFat;
    @BindView(R.id.et_blood_fat_one)
    ColorEditText etBloodFatOne;
    @BindView(R.id.et_blood_fat_two)
    ColorEditText etBloodFatTwo;
    @BindView(R.id.et_blood_fat_three)
    ColorEditText etBloodFatThree;
    @BindView(R.id.et_blood_fat_four)
    ColorEditText etBloodFatFour;
    //尿微量白蛋白
    @BindView(R.id.ll_piss_tiny_albumin)
    LinearLayout llPissTinyAlbumin;
    @BindView(R.id.et_piss_tiny_albumin)
    ColorEditText etPissTinyAlbumin;
    //血清肌酐
    @BindView(R.id.ll_serum)
    LinearLayout llSerum;
    @BindView(R.id.et_serum)
    ColorEditText etSerum;
    //肝功能
    @BindView(R.id.ll_liver)
    LinearLayout llLiver;
    @BindView(R.id.rv_liver)
    RecyclerView rvLiver;
    @BindView(R.id.tv_liver_desc)
    ColorEditText tvLiverDesc;
    //促甲状腺激素(TSH)
    @BindView(R.id.ll_tsh)
    LinearLayout llTsh;
    @BindView(R.id.et_tsh)
    ColorEditText etTsh;
    //心电图
    @BindView(R.id.ll_heart)
    LinearLayout llHeart;
    @BindView(R.id.rv_heart)
    RecyclerView rvHeart;
    @BindView(R.id.tv_heart_desc)
    ColorEditText tvHeartDesc;
    //眼底照相
    @BindView(R.id.ll_eyes)
    LinearLayout llEyes;
    @BindView(R.id.rv_eyes)
    RecyclerView rvEyes;
    @BindView(R.id.tv_eyes_desc)
    ColorEditText tvEyesDesc;
    //神经病变相关检查
    @BindView(R.id.ll_neuropathy)
    LinearLayout llNeuropathy;
    @BindView(R.id.rv_neuropathy)
    RecyclerView rvNeuropathy;
    @BindView(R.id.tv_neuropathy_desc)
    ColorEditText tvNeuropathyDesc;


    FollowUpVisitBloodSugarDataBean mainBean;
    //返回保存
    private FollowUpVisitSavePopup popupBack;
    private FollowUpVisitSavePopup popupSave;
    //返回保存
    //症状
    private FollowUpVisitRvAdapter adapter;
    private List<String> selectDatas = new ArrayList<>();
    //症状
    //后台返回状态值
    private String status;
    //后台返回状态值
    //用药情况
    private FollowUpVisitBloodSugarMedicineAddAdapter medicineAddAdapter;
    private List<FollowUpVisitLvBean> medicineList;
    //用药情况
    //拍照选择照片
    private File file;
    private SlideFromBottomPopup photoPopu;
    //拍照选择照片
    //血糖
    private List<FollowUpVisitBloodSugarAddBean> sevenSugarList;
    private FollowUpVisitBloodSugarAddAdapter sugarAddAdapter;
    //血糖

    //肝功能
    private AddPicAdapter liverAddPicAdapter;
    private List<String> liverPics;
    //心电图
    private AddPicAdapter heartAddPicAdapter;
    private List<String> heartPics;
    //眼底检查
    private AddPicAdapter eyesAddPicAdapter;
    private List<String> eyesPics;
    //神经病变
    private AddPicAdapter neuropathyAddPicAdapter;
    private List<String> neuropathyPics;
    private int mPosition;
    private QMUITipDialog tipDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBar();
        initPopup();
        setRadioGroup();
        setHeightAndWeightListener();
        KeyboardUtils.fixAndroidBug5497(this);
        //获取详情
        getFollowUpVisitDetail();
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_follow_up_visit_blood_sugar_submit, contentLayout, false);
    }

    /**
     * 设置标题栏
     */
    private void setTitleBar() {
        hideTitleBar();
        tvTitle.setText("随访管理");
    }

    void newAddPic(int type) {
        PermissionUtils
                .permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        selectPhoto(type);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort("请允许使用相机权限和存储权限");
                    }
                }).request();
    }

    /**
     * 弹窗 添加保存
     */
    private void initPopup() {
        //返回键 保存 弹窗
        popupBack = new FollowUpVisitSavePopup(getPageContext());
        popupSave = new FollowUpVisitSavePopup(getPageContext());
        TextView tvContentTopBack = popupBack.findViewById(R.id.tv_content_top);
        TextView tvContentBottomBack = popupBack.findViewById(R.id.tv_content_bottom);
        TextView tvLeftBack = popupBack.findViewById(R.id.tv_left);
        TextView tvRightBack = popupBack.findViewById(R.id.tv_right);
        TextView tvContentTopSave = popupSave.findViewById(R.id.tv_content_top);
        TextView tvContentBottomSave = popupSave.findViewById(R.id.tv_content_bottom);
        TextView tvLeftSave = popupSave.findViewById(R.id.tv_left);
        TextView tvRightSave = popupSave.findViewById(R.id.tv_right);
        tvContentTopBack.setText("您还未完成管理问卷填写");
        tvContentBottomBack.setText("是否保存");
        tvLeftBack.setText("不保存");
        tvRightBack.setText("保存草稿");
        tvContentTopSave.setText("您已完成管理问卷填写");
        tvContentBottomSave.setText("是否提交");
        tvLeftSave.setText("保存草稿");
        tvRightSave.setText("完成提交");
        tvLeftBack.setOnClickListener(this);
        tvLeftBack.setTag(BACK_LEFT);
        tvRightBack.setOnClickListener(this);
        tvRightBack.setTag(BACK_RIGHT);
        tvLeftSave.setOnClickListener(this);
        tvLeftSave.setTag(SAVE_LEFT);
        tvRightSave.setOnClickListener(this);
        tvRightSave.setTag(SAVE_RIGHT);
    }

    /**
     * 设置
     */
    private void setRadioGroup() {
        rbClassifySatisfaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgDrugUseClassifySecond.clearCheck();
            }
        });
        rbClassifySatisfactionNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgDrugUseClassifySecond.clearCheck();
            }
        });
        rbClassifyAdverseReaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgDrugUseClassify.clearCheck();
            }
        });
        rbClassifyComplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgDrugUseClassify.clearCheck();
            }
        });
    }

    /**
     * 设置身高体重监听求BMI值
     */
    private void setHeightAndWeightListener() {
        etHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String height = s.toString();
                String weight = etWeight.getText().toString().trim();
                if (!TextUtils.isEmpty(weight)) {
                    double doubleHeight = TurnsUtils.getDouble(height, 0);
                    double doubleWeight = TurnsUtils.getDouble(weight, 0);
                    double doubleHeightM = doubleHeight / 100;
                    double bmi = doubleWeight / (doubleHeightM * doubleHeightM);
                    DecimalFormat df = new DecimalFormat("0.0");
                    tvBmi.setText(df.format(bmi));
                }
            }
        });
        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String weight = s.toString();
                String height = etHeight.getText().toString().trim();
                if (!TextUtils.isEmpty(height)) {
                    double doubleHeight = TurnsUtils.getDouble(height, 0);
                    double doubleWeight = TurnsUtils.getDouble(weight, 0);
                    double doubleHeightM = doubleHeight / 100;
                    double bmi = doubleWeight / (doubleHeightM * doubleHeightM);
                    DecimalFormat df = new DecimalFormat("0.0");
                    tvBmi.setText(df.format(bmi));
                }
            }
        });
    }

    /**
     * 查看随访管理
     */
    private void getFollowUpVisitDetail() {
        String id = getIntent().getStringExtra("id");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        XyUrl.okPost(XyUrl.GET_FOLLOW_DETAIL_NEW, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                FollowUpVisitBloodSugarDataBean bean = JSONObject.parseObject(value, FollowUpVisitBloodSugarDataBean.class);
                Message message = getHandlerMessage();
                message.what = GET_FOLLOW_UP_VISIT_DETAIL;
                message.obj = bean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    /**
     * 设置右上角
     *
     * @param status
     */
    private void setRightText(String status) {
        if ("2".equals(status) || "3".equals(status)) {
            tvMore.setText("保存");
            tvMore.setVisibility(View.VISIBLE);
        } else {
            tvMore.setVisibility(View.GONE);
        }
    }

    /**
     * 设置显示隐藏
     *
     * @param data
     */
    private void setVisibleOrGone(FollowUpVisitBloodSugarDataBean data) {
        List<String> list = data.getQuestionstr();
        //总结是否显示隐藏
        int status = data.getStatus();
        if (5 == status) {
            llSummary.setVisibility(View.VISIBLE);
            String paquestion = data.getPaquestion();
            String measures = data.getMeasures();
            String target = data.getTarget();
            etSummaryMainQuestion.setText(paquestion + measures);
            //etSummaryImproveMeasure.setText(measures);
            etSummaryMainPurpose.setText(target);
        } else {
            llSummary.setVisibility(View.GONE);
        }
        //十五个是否显示隐藏
        if (list != null && list.size() > 0) {
            //症状
            if (list.contains("1")) {
                llSymptom.setVisibility(View.VISIBLE);
            }
            //体征
            if (list.contains("2")) {
                llPhysicalSign.setVisibility(View.VISIBLE);
            }
            //生活方式
            if (list.contains("3")) {
                llLifeStyle.setVisibility(View.VISIBLE);
            }
            //辅助检查
            if (list.contains("4")) {
                llExamine.setVisibility(View.VISIBLE);
            }
            //用药情况
            if (list.contains("5")) {
                llDrugUse.setVisibility(View.VISIBLE);
            }
            //血糖
            if (list.contains("6")) {
                llBloodSugar.setVisibility(View.VISIBLE);
            }
            //尿常规
            if (list.contains("7")) {
                llPissCommon.setVisibility(View.VISIBLE);
            }
            //血脂
            if (list.contains("8")) {
                llBloodFat.setVisibility(View.VISIBLE);
            }
            //尿微量白蛋白
            if (list.contains("9")) {
                llPissTinyAlbumin.setVisibility(View.VISIBLE);
            }
            //血清肌酐
            if (list.contains("10")) {
                llSerum.setVisibility(View.VISIBLE);
            }
            //肝功能
            if (list.contains("11")) {
                llLiver.setVisibility(View.VISIBLE);
            }
            //促甲状腺激素(TSH)
            if (list.contains("12")) {
                llTsh.setVisibility(View.VISIBLE);
            }
            //心电图
            if (list.contains("13")) {
                llHeart.setVisibility(View.VISIBLE);
            }
            //眼底照相
            if (list.contains("14")) {
                llEyes.setVisibility(View.VISIBLE);
            }
            //神经病变相关检查
            if (list.contains("15")) {
                llNeuropathy.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 设置头部信息时间
     *
     * @param data
     */
    private void setTopData(FollowUpVisitBloodSugarDataBean data) {
        String stime = data.getStime();
        String etime = data.getEtime();
        tvTopAddTime.setText(String.format(getString(R.string.please_input_one), stime, etime));
        tvTopSubmitTime.setText(String.format(getString(R.string.please_input_two), etime));
    }

    /**
     * 设置五个数据
     *
     * @param data
     */
    private void setFifteenData(FollowUpVisitBloodSugarDataBean data) {
        //症状
        List<String> symptom = data.getSymptom();//症状
        if (symptom != null) {
            setRvSymptom(symptom);
        } else {
            List<String> symptomNew = new ArrayList<>();
            setRvSymptom(symptomNew);
        }
        //体征
        setPhysicalSign(data);
        //生活方式
        setLifeStyle(data);
        //辅助检查
        setExamine(data);
        //用药情况
        setDrugUse(data);
        //设置血糖
        setBloodSugar(data);
        //设置尿常规
        setPissCommon(data);
        //设置血脂
        if (data.getBloodfat().size() > 0) {
            etBloodFatOne.setText(data.getBloodfat().get(0));
        }
        if (data.getBloodfat().size() > 1) {
            etBloodFatTwo.setText(data.getBloodfat().get(1));
        }
        if (data.getBloodfat().size() > 2) {
            etBloodFatThree.setText(data.getBloodfat().get(2));
        }
        if (data.getBloodfat().size() > 3) {
            etBloodFatFour.setText(data.getBloodfat().get(3));
        }
        //设置尿微量蛋白
        if (!TextUtils.isEmpty(data.getUrinemicro())) {
            etPissTinyAlbumin.setText(data.getUrinemicro());
        }
        //设置血清肌酐
        if (!TextUtils.isEmpty(data.getCreatinine())) {
            etSerum.setText(data.getCreatinine());
        }
        //设置肝功能
        setLiver(data);
        //设置促甲状腺激素(TSH)
        if (!TextUtils.isEmpty(data.getStimulating())) {
            etTsh.setText(data.getStimulating());
        }
        //设置心电图
        setHeart(data);
        //设置眼底检查
        setEyes(data);
        //设置神经病变相关
        setNeuropathy(data);
    }


    /**
     * 设置症状
     *
     * @param symptom
     */
    private void setRvSymptom(List<String> symptom) {
        String[] stringArray = getResources().getStringArray(R.array.follow_up_visit_blood_sugar_symptom);
        List<String> list = Arrays.asList(stringArray);
        adapter = new FollowUpVisitRvAdapter(getPageContext(), R.layout.item_include_follow_up_visit_symptom, list, symptom, status);
        int symptomItemHeight = 50;
        int symptomSpanCount = 3;
        ViewGroup.LayoutParams params = rvSymptom.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ConvertUtils.dp2px(list.size() / symptomSpanCount * symptomItemHeight);
        rvSymptom.setLayoutParams(params);
        rvSymptom.setLayoutManager(new GridLayoutManager(getPageContext(), symptomSpanCount));
        rvSymptom.setAdapter(adapter);
        //添加已选中
        selectDatas.addAll(symptom);
        //添加已选中
        if (!status.equals("4") && !status.equals("5")) {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (!adapter.selectMap.get(position)) {
                        adapter.selectMap.put(position, true);//修改map的值保存状态
                        adapter.notifyItemChanged(position);
                        selectDatas.add(position + 1 + "");
                    } else {
                        adapter.selectMap.put(position, false);//修改map的值保存状态
                        adapter.notifyItemChanged(position);
                        selectDatas.remove(position + 1 + "");
                    }
                }
            });
        }
    }

    /**
     * 设置生活方式
     *
     * @param data
     */
    private void setLifeStyle(FollowUpVisitBloodSugarDataBean data) {
        String smok = data.getSmok();
        String drink = data.getDrink();
        String sportnum = data.getSportnum();
        String sporttime = data.getSporttime();
        String mainfood = data.getMainfood();
        int psychological = data.getPsychological();
        int behavior = data.getBehavior();
        etSmoke.setText(smok);
        etDrink.setText(drink);
        etSportCount.setText(sportnum);
        etSportTime.setText(sporttime);
        etMainFood.setText(mainfood);
        if (1 == psychological) {
            rbPsychologicalAdjustWell.setChecked(true);
            rbPsychologicalAdjustCommon.setChecked(false);
            rbPsychologicalAdjustBad.setChecked(false);
        } else if (2 == psychological) {
            rbPsychologicalAdjustWell.setChecked(false);
            rbPsychologicalAdjustCommon.setChecked(true);
            rbPsychologicalAdjustBad.setChecked(false);
        } else if (3 == psychological) {
            rbPsychologicalAdjustWell.setChecked(false);
            rbPsychologicalAdjustCommon.setChecked(false);
            rbPsychologicalAdjustBad.setChecked(true);
        }
        if (1 == behavior) {
            rbFollowDoctorWell.setChecked(true);
            rbFollowDoctorCommon.setChecked(false);
            rbFollowDoctorBad.setChecked(false);
        } else if (2 == behavior) {
            rbFollowDoctorWell.setChecked(false);
            rbFollowDoctorCommon.setChecked(true);
            rbFollowDoctorBad.setChecked(false);
        } else if (3 == behavior) {
            rbFollowDoctorWell.setChecked(false);
            rbFollowDoctorCommon.setChecked(false);
            rbFollowDoctorBad.setChecked(true);
        }
    }

    /**
     * 辅助检查
     *
     * @param data
     */
    private void setExamine(FollowUpVisitBloodSugarDataBean data) {
        String fastingbloodsugar = data.getFastingbloodsugar();
        String hemoglobin = data.getHemoglobin();
        String examinetime = data.getExaminetime();
        etEmptySugar.setText(fastingbloodsugar);
        etBloodRed.setText(hemoglobin);
        tvCheckTime.setText(examinetime);
    }


    /**
     * 设置体征
     *
     * @param data
     */
    private void setPhysicalSign(FollowUpVisitBloodSugarDataBean data) {
        String systolic = data.getSystolic();//收缩压
        String diastolic = data.getDiastolic();//舒张压
        String height = data.getHeight();
        String weight = data.getWeight();
        int pulsation = data.getPulsation();//足背动脉搏动（1未触及2触及）
        String elseX = data.getOther();//其它
        etPhysicalSignHigh.setText(systolic);//收缩压
        etPhysicalSignLow.setText(diastolic);//舒张压
        etHeight.setText(height);//身高
        etWeight.setText(weight);//体重
        //足背动脉搏动（1未触及2触及）
        if (1 == pulsation) {
            rbPhysicalSignNot.setChecked(true);
            rbPhysicalSignHave.setChecked(false);
            rbPhysicalSignNotSecond.setChecked(false);
        } else if (2 == pulsation) {
            rbPhysicalSignNot.setChecked(false);
            rbPhysicalSignHave.setChecked(true);
            rbPhysicalSignNotSecond.setChecked(false);
        } else if (3 == pulsation) {
            rbPhysicalSignNot.setChecked(false);
            rbPhysicalSignHave.setChecked(false);
            rbPhysicalSignNotSecond.setChecked(true);
        }
        etPhysicalOther.setText(elseX);
    }

    /**
     * 用药情况
     *
     * @param data
     */
    private void setDrugUse(FollowUpVisitBloodSugarDataBean data) {
        int compliance = data.getCompliance();
        int drugreactions = data.getDrugreactions();
        int reaction = data.getReaction();
        int followstyle = data.getFollowstyle();
        String insulin = data.getInsulin();
        String insulinnum = data.getInsulinnum();
        //用药依从性
        if (1 == compliance) {
            rbDrugUseYieldRule.setChecked(true);
            rbDrugUseYieldGap.setChecked(false);
            rbDrugUseYieldNotTakeMedicine.setChecked(false);
        } else if (2 == compliance) {
            rbDrugUseYieldRule.setChecked(false);
            rbDrugUseYieldGap.setChecked(true);
            rbDrugUseYieldNotTakeMedicine.setChecked(false);
        } else if (3 == compliance) {
            rbDrugUseYieldRule.setChecked(false);
            rbDrugUseYieldGap.setChecked(false);
            rbDrugUseYieldNotTakeMedicine.setChecked(true);
        }
        //药物不良反应
        if (1 == drugreactions) {
            rgAdverseDrugReactionHave.setChecked(false);
            rgAdverseDrugReactionNot.setChecked(true);
        } else if (2 == drugreactions) {
            rgAdverseDrugReactionHave.setChecked(true);
            rgAdverseDrugReactionNot.setChecked(false);
        }
        //低血糖反应
        if (1 == reaction) {
            rbHypoglycemicReactionNot.setChecked(true);
            rbHypoglycemicReactionOnce.setChecked(false);
            rbHypoglycemicReactionOften.setChecked(false);
        } else if (2 == reaction) {
            rbHypoglycemicReactionNot.setChecked(false);
            rbHypoglycemicReactionOnce.setChecked(true);
            rbHypoglycemicReactionOften.setChecked(false);
        } else if (3 == reaction) {
            rbHypoglycemicReactionNot.setChecked(false);
            rbHypoglycemicReactionOnce.setChecked(false);
            rbHypoglycemicReactionOften.setChecked(true);
        }
        //此次随访分类
        if (1 == followstyle) {
            rbClassifySatisfaction.setChecked(true);
            rbClassifySatisfactionNot.setChecked(false);
            rbClassifyAdverseReaction.setChecked(false);
            rbClassifyComplication.setChecked(false);
        } else if (2 == followstyle) {
            rbClassifySatisfaction.setChecked(false);
            rbClassifySatisfactionNot.setChecked(true);
            rbClassifyAdverseReaction.setChecked(false);
            rbClassifyComplication.setChecked(false);
        } else if (3 == followstyle) {
            rbClassifySatisfaction.setChecked(false);
            rbClassifySatisfactionNot.setChecked(false);
            rbClassifyAdverseReaction.setChecked(true);
            rbClassifyComplication.setChecked(false);
        } else if (4 == followstyle) {
            rbClassifySatisfaction.setChecked(false);
            rbClassifySatisfactionNot.setChecked(false);
            rbClassifyAdverseReaction.setChecked(false);
            rbClassifyComplication.setChecked(true);
        }
        //胰岛素
        etInsulin.setText(insulin);
        etInsulinDosage.setText(insulinnum);
        //设置药物
        List<List<String>> medicdetail = data.getMedicdetail();
        medicineList = new ArrayList<>();
        for (int i = 0; i < medicdetail.size(); i++) {
            FollowUpVisitLvBean bean = new FollowUpVisitLvBean();
            bean.setName("");
            bean.setCount("");
            bean.setDosage("");
            medicineList.add(bean);
        }
        medicineAddAdapter = new FollowUpVisitBloodSugarMedicineAddAdapter(this, medicineList, medicdetail, this, status);
        lvDrugUse.setAdapter(medicineAddAdapter);
    }

    /**
     * 设置血糖
     *
     * @param data
     */
    private void setBloodSugar(FollowUpVisitBloodSugarDataBean data) {
        List<List<String>> sugars = data.getSugars();
        //手动设置返回的值
        //取七天血糖值
        sevenSugarList = new ArrayList<>();
        if (sugars != null && 7 == sugars.size()) {
            for (int i = 0; i < sugars.size(); i++) {
                List<String> list = sugars.get(i);
                FollowUpVisitBloodSugarAddBean bean = new FollowUpVisitBloodSugarAddBean();
                for (int j = 0; j < list.size(); j++) {
                    String s = list.get(j);
                    switch (j) {
                        case 0:
                            bean.setOne(s);
                            break;
                        case 1:
                            bean.setTwo(s);
                            break;
                        case 2:
                            bean.setThree(s);
                            break;
                        case 3:
                            bean.setFour(s);
                            break;
                        case 4:
                            bean.setFive(s);
                            break;
                        case 5:
                            bean.setSix(s);
                            break;
                        case 6:
                            bean.setSeven(s);
                            break;
                        case 7:
                            bean.setEight(s);
                            break;
                    }
                }
                sevenSugarList.add(bean);
            }
        }
        sugarAddAdapter = new FollowUpVisitBloodSugarAddAdapter(getPageContext(),
                R.layout.item_seven_and_thirty_bottom_for_follow_up_visit, sevenSugarList, this, status);
        lvSevenDayBloodSugar.setAdapter(sugarAddAdapter);
    }

    /**
     * 设置尿常规
     */
    private void setPissCommon(FollowUpVisitBloodSugarDataBean data) {
        List<TextView> tvList = new ArrayList<>();
        tvList.add(etPissCommonOneLeft);
        tvList.add(etPissCommonOneRight);

        tvList.add(etPissCommonTwoLeft);
        tvList.add(etPissCommonTwoRight);

        tvList.add(etPissCommonThreeLeft);
        tvList.add(etPissCommonThreeRight);

        tvList.add(etPissCommonFourLeft);
        tvList.add(etPissCommonFourRight);

        tvList.add(etPissCommonFiveLeft);
        tvList.add(etPissCommonFiveRight);

        tvList.add(etPissCommonSixLeft);
        List<String> pissCommonList = data.getRoutine();
        if (pissCommonList != null && 11 == pissCommonList.size()) {
            for (int i = 0; i < pissCommonList.size(); i++) {
                String s = pissCommonList.get(i);
                TextView tv = tvList.get(i);
                tv.setText(s);
            }
        }
    }

    //设置肝功能
    private void setLiver(FollowUpVisitBloodSugarDataBean data) {
        liverPics = new ArrayList<>();
        if (!TextUtils.isEmpty(data.getLiverfun1())) {
            liverPics.add(data.getLiverfun1());
        }
        if (!TextUtils.isEmpty(data.getLiverfun2())) {
            liverPics.add(data.getLiverfun2());
        }
        if (!TextUtils.isEmpty(data.getLiverfun3())) {
            liverPics.add(data.getLiverfun3());
        }
        if (!TextUtils.isEmpty(data.getLivercon())) {
            tvLiverDesc.setText(data.getLivercon());
        }
        liverAddPicAdapter = new AddPicAdapter(this, new AddPicAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int position) {
                mPosition = position;
                newAddPic(REQUEST_CODE_CHOOSE_LIVER);
            }
        });
        liverAddPicAdapter.setList(liverPics);
        liverAddPicAdapter.setStatus(status);
        rvLiver.setAdapter(liverAddPicAdapter);
        rvLiver.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //设置心电图
    private void setHeart(FollowUpVisitBloodSugarDataBean data) {
        heartPics = new ArrayList<>();
        if (!TextUtils.isEmpty(data.getHeartpic1())) {
            heartPics.add(data.getHeartpic1());
        }
        if (!TextUtils.isEmpty(data.getHeartpic2())) {
            heartPics.add(data.getHeartpic2());
        }
        if (!TextUtils.isEmpty(data.getHeartpic3())) {
            heartPics.add(data.getHeartpic3());
        }
        if (!TextUtils.isEmpty(data.getHeartcontent())) {
            tvHeartDesc.setText(data.getHeartcontent());
        }
        heartAddPicAdapter = new AddPicAdapter(this, new AddPicAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int position) {
                mPosition = position;
                newAddPic(REQUEST_CODE_CHOOSE_HEART);
            }
        });
        heartAddPicAdapter.setList(heartPics);
        heartAddPicAdapter.setStatus(status);
        rvHeart.setAdapter(heartAddPicAdapter);
        rvHeart.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //设置眼底检查
    private void setEyes(FollowUpVisitBloodSugarDataBean data) {
        eyesPics = new ArrayList<>();
        if (!TextUtils.isEmpty(data.getEyespic1())) {
            eyesPics.add(data.getEyespic1());
        }
        if (!TextUtils.isEmpty(data.getEyespic2())) {
            eyesPics.add(data.getEyespic2());
        }
        if (!TextUtils.isEmpty(data.getEyespic3())) {
            eyesPics.add(data.getEyespic3());
        }
        if (!TextUtils.isEmpty(data.getEyescontent())) {
            tvEyesDesc.setText(data.getEyescontent());
        }
        LogUtils.e(eyesPics);
        eyesAddPicAdapter = new AddPicAdapter(this, new AddPicAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int position) {
                mPosition = position;
                newAddPic(REQUEST_CODE_CHOOSE_EYES);
            }
        });
        eyesAddPicAdapter.setList(eyesPics);
        eyesAddPicAdapter.setStatus(status);
        rvEyes.setAdapter(eyesAddPicAdapter);
        rvEyes.setLayoutManager(new GridLayoutManager(this, 3));
    }

    //设置神经病变
    private void setNeuropathy(FollowUpVisitBloodSugarDataBean data) {
        neuropathyPics = new ArrayList<>();
        if (!TextUtils.isEmpty(data.getNeuropathypic1())) {
            neuropathyPics.add(data.getNeuropathypic1());
        }
        if (!TextUtils.isEmpty(data.getNeuropathypic2())) {
            neuropathyPics.add(data.getNeuropathypic2());
        }
        if (!TextUtils.isEmpty(data.getNeuropathypic3())) {
            neuropathyPics.add(data.getNeuropathypic3());
        }
        if (!TextUtils.isEmpty(data.getNeuropathycontent())) {
            tvNeuropathyDesc.setText(data.getNeuropathycontent());
        }
        neuropathyAddPicAdapter = new AddPicAdapter(this, new AddPicAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int position) {
                mPosition = position;
                newAddPic(REQUEST_CODE_CHOOSE_NEUROPATHY);
            }
        });
        neuropathyAddPicAdapter.setList(neuropathyPics);
        neuropathyAddPicAdapter.setStatus(status);
        rvNeuropathy.setAdapter(neuropathyAddPicAdapter);
        rvNeuropathy.setLayoutManager(new GridLayoutManager(this, 3));
    }

    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果返回键按下
            if ("2".equals(status) || "3".equals(status)) {
                popupBack.showPopupWindow();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 提交数据
     *
     * @param status
     */
    private void toDoSubmit(String status) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String id = getIntent().getStringExtra("id");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("access_token", user.getToken());
        builder.addFormDataPart("version", ConstantParam.SERVER_VERSION);
        builder.addFormDataPart("id", id);
        builder.addFormDataPart("data[status]", status);
        //设置状态
        //第一次版本五个开始
        //症状
        if (View.VISIBLE == llSymptom.getVisibility()) {
            StringBuilder sbSymptom = new StringBuilder();
            for (int i = 0; i < selectDatas.size(); i++) {
                sbSymptom.append(selectDatas.get(i));
                sbSymptom.append(",");
            }
            if (sbSymptom.length() > 0) {
                String symptom = sbSymptom.substring(0, sbSymptom.length() - 1);
                builder.addFormDataPart("data[symptom]", symptom);
            }
        }
        //体征
        if (View.VISIBLE == llPhysicalSign.getVisibility()) {
            String systolic = etPhysicalSignHigh.getText().toString().trim();
            String diastolic = etPhysicalSignLow.getText().toString().trim();
            String height = etHeight.getText().toString().trim();
            String weight = etWeight.getText().toString().trim();
            builder.addFormDataPart("data[systolic]", systolic);
            builder.addFormDataPart("data[diastolic]", diastolic);
            builder.addFormDataPart("data[height]", height);
            builder.addFormDataPart("data[weight]", weight);
            if (rbPhysicalSignNot.isChecked()) {
                builder.addFormDataPart("data[pulsation]", "1");
            } else if (rbPhysicalSignHave.isChecked()) {
                builder.addFormDataPart("data[pulsation]", "2");
            } else if (rbPhysicalSignNotSecond.isChecked()) {
                builder.addFormDataPart("data[pulsation]", "3");
            }
            String other = etPhysicalOther.getText().toString().trim();
            builder.addFormDataPart("data[other]", other);
        }
        //生活方式
        if (View.VISIBLE == llLifeStyle.getVisibility()) {
            String smoke = etSmoke.getText().toString().trim();
            builder.addFormDataPart("data[smok]", smoke);
            String drink = etDrink.getText().toString().trim();
            builder.addFormDataPart("data[drink]", drink);
            String sportCount = etSportCount.getText().toString().trim();
            builder.addFormDataPart("data[sportnum]", sportCount);
            String sportTime = etSportTime.getText().toString().trim();
            builder.addFormDataPart("data[sporttime]", sportTime);
            String mainFood = etMainFood.getText().toString().trim();
            builder.addFormDataPart("data[mainfood]", mainFood);
            //心理状态1:良好 2：一般 3：差
            if (rbPsychologicalAdjustWell.isChecked()) {
                builder.addFormDataPart("data[psychological]", "1");
            } else if (rbPsychologicalAdjustCommon.isChecked()) {
                builder.addFormDataPart("data[psychological]", "2");
            } else if (rbPsychologicalAdjustBad.isChecked()) {
                builder.addFormDataPart("data[psychological]", "3");
            }
            //（遵医行为）1良好2一般3差
            if (rbFollowDoctorWell.isChecked()) {
                builder.addFormDataPart("data[behavior]", "1");
            } else if (rbFollowDoctorCommon.isChecked()) {
                builder.addFormDataPart("data[behavior]", "2");
            } else if (rbFollowDoctorBad.isChecked()) {
                builder.addFormDataPart("data[behavior]", "3");
            }
        }
        //辅助检查
        if (View.VISIBLE == llExamine.getVisibility()) {
            String emptySugar = etEmptySugar.getText().toString().trim();
            builder.addFormDataPart("data[fastingbloodsugar]", emptySugar);
            String bloodRed = etBloodRed.getText().toString().trim();
            builder.addFormDataPart("data[hemoglobin]", bloodRed);
            String checkTime = tvCheckTime.getText().toString().trim();
            builder.addFormDataPart("data[examinetime]", checkTime);
        }
        //用药情况
        //用药依从性 1规律2不间断3不服药
        if (View.VISIBLE == llDrugUse.getVisibility()) {
            if (rbDrugUseYieldRule.isChecked()) {
                builder.addFormDataPart("data[compliance]", "1");
            } else if (rbDrugUseYieldGap.isChecked()) {
                builder.addFormDataPart("data[compliance]", "2");
            } else if (rbDrugUseYieldNotTakeMedicine.isChecked()) {
                builder.addFormDataPart("data[compliance]", "3");
            }
            if (rgAdverseDrugReactionHave.isChecked()) {
                builder.addFormDataPart("data[drugreactions]", "2");
            } else if (rgAdverseDrugReactionNot.isChecked()) {
                builder.addFormDataPart("data[drugreactions]", "1");
            }
            if (rbHypoglycemicReactionNot.isChecked()) {
                builder.addFormDataPart("data[reaction]", "1");
            } else if (rbHypoglycemicReactionOnce.isChecked()) {
                builder.addFormDataPart("data[reaction]", "2");
            } else if (rbHypoglycemicReactionOften.isChecked()) {
                builder.addFormDataPart("data[reaction]", "3");
            }
            if (rbClassifySatisfaction.isChecked()) {
                builder.addFormDataPart("data[followstyle]", "1");
            } else if (rbClassifySatisfactionNot.isChecked()) {
                builder.addFormDataPart("data[followstyle]", "2");
            } else if (rbClassifyAdverseReaction.isChecked()) {
                builder.addFormDataPart("data[followstyle]", "3");
            } else if (rbClassifyComplication.isChecked()) {
                builder.addFormDataPart("data[followstyle]", "4");
            }

            String insulin = etInsulin.getText().toString().trim();
            String insulinDosage = etInsulinDosage.getText().toString().trim();
            builder.addFormDataPart("data[insulin]", insulin);
            builder.addFormDataPart("data[insulinnum]", insulinDosage);
            //无限添加的药品
            HashMap<Integer, String> saveMapName = medicineAddAdapter.saveMapName;
            HashMap<Integer, String> saveMapCount = medicineAddAdapter.saveMapCount;
            HashMap<Integer, String> saveMapDosage = medicineAddAdapter.saveMapDosage;
            for (int i = 0; i < saveMapName.size(); i++) {
                String name = saveMapName.get(i);
                String count = saveMapCount.get(i);
                String dosage = saveMapDosage.get(i);
                builder.addFormDataPart("medicdetail[]", defaultValue(name));
                builder.addFormDataPart("medicdetail[]", defaultValue(count));
                builder.addFormDataPart("medicdetail[]", defaultValue(dosage));
            }
        }
        //第一次版本五个结束
        //第二次十个开始
        //血糖
        if (View.VISIBLE == llBloodSugar.getVisibility()) {
            for (int i = 0; i < sevenSugarList.size(); i++) {
                FollowUpVisitBloodSugarAddBean bean = sevenSugarList.get(i);
                builder.addFormDataPart("sugars[]", defaultValue(bean.getOne()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getTwo()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getThree()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getFour()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getFive()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getSix()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getSeven()));
                builder.addFormDataPart("sugars[]", defaultValue(bean.getEight()));
            }
        }
        //尿常规
        if (View.VISIBLE == llPissCommon.getVisibility()) {
            String One = etPissCommonOneLeft.getText().toString().trim();
            String Two = etPissCommonOneRight.getText().toString().trim();
            String Three = etPissCommonTwoLeft.getText().toString().trim();
            String Four = etPissCommonTwoRight.getText().toString().trim();
            String Five = etPissCommonThreeLeft.getText().toString().trim();
            String Six = etPissCommonThreeRight.getText().toString().trim();
            String Seven = etPissCommonFourLeft.getText().toString().trim();
            String Eight = etPissCommonFourRight.getText().toString().trim();
            String Nine = etPissCommonFiveLeft.getText().toString().trim();
            String Ten = etPissCommonFiveRight.getText().toString().trim();
            String Elven = etPissCommonSixLeft.getText().toString().trim();
            builder.addFormDataPart("data[routine][]", defaultValue(One));
            builder.addFormDataPart("data[routine][]", defaultValue(Two));
            builder.addFormDataPart("data[routine][]", defaultValue(Three));
            builder.addFormDataPart("data[routine][]", defaultValue(Four));
            builder.addFormDataPart("data[routine][]", defaultValue(Five));
            builder.addFormDataPart("data[routine][]", defaultValue(Six));
            builder.addFormDataPart("data[routine][]", defaultValue(Seven));
            builder.addFormDataPart("data[routine][]", defaultValue(Eight));
            builder.addFormDataPart("data[routine][]", defaultValue(Nine));
            builder.addFormDataPart("data[routine][]", defaultValue(Ten));
            builder.addFormDataPart("data[routine][]", defaultValue(Elven));
        }
        //血脂
        if (View.VISIBLE == llBloodFat.getVisibility()) {
            String one = etBloodFatOne.getText().toString().trim();
            String two = etBloodFatTwo.getText().toString().trim();
            String three = etBloodFatThree.getText().toString().trim();
            String four = etBloodFatFour.getText().toString().trim();
            builder.addFormDataPart("data[bloodfat][]", defaultValue(one));
            builder.addFormDataPart("data[bloodfat][]", defaultValue(two));
            builder.addFormDataPart("data[bloodfat][]", defaultValue(three));
            builder.addFormDataPart("data[bloodfat][]", defaultValue(four));
        }
        //尿微量白蛋白
        if (View.VISIBLE == llPissTinyAlbumin.getVisibility()) {
            String urinemicro = etPissTinyAlbumin.getText().toString().trim();
            builder.addFormDataPart("data[urinemicro]", urinemicro);
        }
        //血清肌酐
        if (View.VISIBLE == llSerum.getVisibility()) {
            String creatinine = etSerum.getText().toString().trim();
            builder.addFormDataPart("data[creatinine]", creatinine);
        }
        //上传肝功能照片
        if (View.VISIBLE == llLiver.getVisibility()) {
            if (liverPics != null) {
                if (liverPics.size() > 0) {
                    if (liverPics.get(0).contains("http:")) {
                        builder.addFormDataPart("liverfun1", liverPics.get(0));
                    } else {
                        File file = new File(liverPics.get(0));
                        builder.addFormDataPart("liverfun1", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("liverfun1", "");
                }
                if (liverPics.size() > 1) {
                    if (liverPics.get(1).contains("http:")) {
                        builder.addFormDataPart("liverfun2", liverPics.get(1));
                    } else {
                        File file = new File(liverPics.get(1));
                        builder.addFormDataPart("liverfun2", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("liverfun2", "");
                }
                if (liverPics.size() > 2) {
                    if (liverPics.get(2).contains("http:")) {
                        builder.addFormDataPart("liverfun3", liverPics.get(2));
                    } else {
                        File file = new File(liverPics.get(2));
                        builder.addFormDataPart("liverfun3", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("liverfun3", "");
                }
            }
            String liverDesc = tvLiverDesc.getText().toString().trim();
            builder.addFormDataPart("data[livercon]", liverDesc);
        }
        //促甲状腺激素
        if (View.VISIBLE == llTsh.getVisibility()) {
            String stimulating = etTsh.getText().toString().trim();
            builder.addFormDataPart("data[stimulating]", stimulating);
        }
        //心电图
        if (View.VISIBLE == llHeart.getVisibility()) {
            if (heartPics != null) {
                if (heartPics.size() > 0) {
                    if (heartPics.get(0).contains("http:")) {
                        builder.addFormDataPart("heartpic1", heartPics.get(0));
                    } else {
                        File file = new File(heartPics.get(0));
                        builder.addFormDataPart("heartpic1", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("heartpic1", "");
                }
                if (heartPics.size() > 1) {
                    if (heartPics.get(1).contains("http:")) {
                        builder.addFormDataPart("heartpic2", heartPics.get(1));
                    } else {
                        File file = new File(heartPics.get(1));
                        builder.addFormDataPart("heartpic2", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("heartpic2", "");
                }
                if (heartPics.size() > 2) {
                    if (heartPics.get(2).contains("http:")) {
                        builder.addFormDataPart("heartpic3", heartPics.get(2));
                    } else {
                        File file = new File(heartPics.get(2));
                        builder.addFormDataPart("heartpic3", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("heartpic3", "");
                }
            }
            String heartcontent = tvHeartDesc.getText().toString().trim();
            builder.addFormDataPart("data[heartcontent]", heartcontent);
        }
        //眼底检查
        if (View.VISIBLE == llEyes.getVisibility()) {
            if (eyesPics != null) {
                if (eyesPics.size() > 0) {
                    if (eyesPics.get(0).contains("http:")) {
                        builder.addFormDataPart("eyespic1", eyesPics.get(0));
                    } else {
                        File file = new File(eyesPics.get(0));
                        builder.addFormDataPart("eyespic1", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("eyespic1", "");
                }
                if (eyesPics.size() > 1) {
                    if (eyesPics.get(1).contains("http:")) {
                        builder.addFormDataPart("eyespic2", eyesPics.get(1));
                    } else {
                        File file = new File(eyesPics.get(1));
                        builder.addFormDataPart("eyespic2", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("eyespic2", "");
                }
                if (eyesPics.size() > 2) {
                    if (eyesPics.get(2).contains("http:")) {
                        builder.addFormDataPart("eyespic3", eyesPics.get(2));
                    } else {
                        File file = new File(eyesPics.get(2));
                        builder.addFormDataPart("eyespic3", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("eyespic3", "");
                }
            }
            String eyescontent = tvEyesDesc.getText().toString().trim();
            builder.addFormDataPart("data[eyescontent]", eyescontent);
        }
        //神经病变相关
        if (View.VISIBLE == llNeuropathy.getVisibility()) {
            if (neuropathyPics != null) {
                if (neuropathyPics.size() > 0) {
                    if (neuropathyPics.get(0).contains("http:")) {
                        builder.addFormDataPart("neuropathypic1", neuropathyPics.get(0));
                    } else {
                        File file = new File(neuropathyPics.get(0));
                        builder.addFormDataPart("neuropathypic1", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("neuropathypic1", "");
                }
                if (neuropathyPics.size() > 1) {
                    if (neuropathyPics.get(1).contains("http:")) {
                        builder.addFormDataPart("neuropathypic2", neuropathyPics.get(1));
                    } else {
                        File file = new File(neuropathyPics.get(1));
                        builder.addFormDataPart("neuropathypic2", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("neuropathypic2", "");
                }
                if (neuropathyPics.size() > 2) {
                    if (neuropathyPics.get(2).contains("http:")) {
                        builder.addFormDataPart("neuropathypic3", neuropathyPics.get(2));
                    } else {
                        File file = new File(neuropathyPics.get(2));
                        builder.addFormDataPart("neuropathypic3", file.getName(), RequestBody.create(MediaType.parse("image"), file));
                    }
                } else {
                    builder.addFormDataPart("neuropathypic3", "");
                }
            }
            String neuropathycontent = tvNeuropathyDesc.getText().toString().trim();
            builder.addFormDataPart("data[neuropathycontent]", neuropathycontent);
        }
        //第二次十个结束
        Request request = new Request.Builder()
                .url(XyUrl.FOLLOW_ADD)
                .post(builder.build())
                .build();
        OkHttpInstance.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                LogUtils.e("onFailure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                sendHandlerMessage(ADD_FOLLOW_OVER);
                LogUtils.e("onResponse");
            }
        });
    }


    /**
     * 返回点击
     */
    private void backClick() {
        if ("2".equals(status) || "3".equals(status)) {
            popupBack.showPopupWindow();
        } else {
            finish();
        }
    }

    /**
     * 保存点击
     */
    private void saveClick() {
        if ("2".equals(status) || "3".equals(status)) {
            popupSave.showPopupWindow();
        }
    }

    /**
     * 选择时间
     */
    private void selectTime() {
        PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvCheckTime.setText(content);
            }
        });
    }

    @OnClick({R.id.bt_back_new, R.id.tv_more_new, R.id.ll_select_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back_new:
                backClick();
                break;
            case R.id.tv_more_new:
                saveClick();
                break;
            case R.id.ll_select_time:
                selectTime();
                break;
        }
    }


    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag) {
            case BACK_LEFT:
                popupBack.dismiss();
                finish();
                break;
            case BACK_RIGHT:
            case SAVE_LEFT:
                toDoSubmit("3");
                popupSave.dismiss();
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("正在加载")
                        .create();
                tipDialog.show();
                break;
            case SAVE_RIGHT:
                toDoSubmit("4");
                popupSave.dismiss();
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("正在加载")
                        .create();
                tipDialog.show();
                break;
        }
    }

    /**
     * 动态添加药品
     *
     * @param view
     * @param position
     */
    public void onAdapterClick(View view, int position) {
        FollowUpVisitLvBean bean = new FollowUpVisitLvBean();
        bean.setName("");
        bean.setCount("");
        bean.setDosage("");
        medicineList.add(bean);
        medicineAddAdapter.notifyDataSetChanged();
    }

    /**
     * 血糖添加
     *
     * @param view
     * @param position
     */
    @Override
    public void onAdapterClickSearch(View view, int position) {
        switch (view.getId()) {
            case R.id.fl_one:
                showEdit(0, position);
                break;
            case R.id.fl_two:
                showEdit(1, position);
                break;
            case R.id.fl_three:
                showEdit(2, position);
                break;
            case R.id.fl_four:
                showEdit(3, position);
                break;
            case R.id.fl_five:
                showEdit(4, position);
                break;
            case R.id.fl_six:
                showEdit(5, position);
                break;
            case R.id.fl_seven:
                showEdit(6, position);
                break;
            case R.id.fl_eight:
                showEdit(7, position);
                break;
        }
    }

    /**
     * 显示添加血糖
     *
     * @param timeHorizontal (时间段)
     * @param timeVertical   (第一天 第二天)
     */
    private void showEdit(int timeHorizontal, int timeVertical) {
        FollowUpVisitBloodSugarAddBean bean = sevenSugarList.get(timeVertical);
        DialogUtils.getInstance().showDecimalNumberInputDialog(getPageContext(), "血糖",
                "请输入血糖", text1 -> {
                    switch (timeHorizontal) {
                        case 0:
                            bean.setOne(text1);
                            break;
                        case 1:
                            bean.setTwo(text1);
                            break;
                        case 2:
                            bean.setThree(text1);
                            break;
                        case 3:
                            bean.setFour(text1);
                            break;
                        case 4:
                            bean.setFive(text1);
                            break;
                        case 5:
                            bean.setSix(text1);
                            break;
                        case 6:
                            bean.setSeven(text1);
                            break;
                        case 7:
                            bean.setEight(text1);
                            break;
                    }
                    sugarAddAdapter.notifyDataSetChanged();
                });

    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case COMPRESS_OVER:
                List<String> list = ListCastUtils.castList(msg.obj, String.class);
                switch (msg.arg1) {
                    case LIVER:
                        liverPics = list;
                        break;
                    case HEART:
                        heartPics = list;
                        break;
                    case EYES:
                        eyesPics = list;
                        break;
                    case NEUROPATHY:
                        neuropathyPics = list;
                        break;
                }
                break;
            case ADD_FOLLOW_OVER:
                tipDialog.dismiss();
                //清除压缩文件
                FileUtils.deleteAllInDir("/storage/emulated/0/Android/data/com.vice.bloodpressure/cache/luban_disk_cache/");
                ToastUtils.showShort("获取成功");
                EventBusUtils.post(new EventMessage<>(ConstantParam.FOLLOW_UP_VISIT_SUBMIT));
                finish();
                break;
            case GET_FOLLOW_UP_VISIT_DETAIL:
                FollowUpVisitBloodSugarDataBean data = (FollowUpVisitBloodSugarDataBean) msg.obj;
                mainBean = data;
                //根据状态设置
                status = data.getStatus() + "";
                //设置不可使用
                if (status.equals("4") || status.equals("5")) {
                    //体征
                    etPhysicalSignHigh.setFocusable(false);
                    etPhysicalSignHigh.setFocusableInTouchMode(false);
                    etPhysicalSignLow.setFocusable(false);
                    etPhysicalSignLow.setFocusableInTouchMode(false);
                    etHeight.setFocusable(false);
                    etHeight.setFocusableInTouchMode(false);
                    etWeight.setFocusable(false);
                    etWeight.setFocusableInTouchMode(false);
                    etPhysicalOther.setFocusable(false);
                    etPhysicalOther.setFocusableInTouchMode(false);
                    rbPhysicalSignHave.setClickable(false);
                    rbPhysicalSignNot.setClickable(false);
                    rbPhysicalSignNotSecond.setClickable(false);
                    //生活方式
                    etSmoke.setFocusable(false);
                    etSmoke.setFocusableInTouchMode(false);
                    etDrink.setFocusable(false);
                    etDrink.setFocusableInTouchMode(false);
                    etSportCount.setFocusable(false);
                    etSportCount.setFocusableInTouchMode(false);
                    etSportTime.setFocusable(false);
                    etSportTime.setFocusableInTouchMode(false);
                    etMainFood.setFocusable(false);
                    etMainFood.setFocusableInTouchMode(false);
                    rbPsychologicalAdjustWell.setClickable(false);
                    rbPsychologicalAdjustCommon.setClickable(false);
                    rbPsychologicalAdjustBad.setClickable(false);
                    rbFollowDoctorWell.setClickable(false);
                    rbFollowDoctorCommon.setClickable(false);
                    rbFollowDoctorBad.setClickable(false);
                    //辅助检查
                    etEmptySugar.setFocusable(false);
                    etEmptySugar.setFocusableInTouchMode(false);
                    etBloodRed.setFocusable(false);
                    etBloodRed.setFocusableInTouchMode(false);
                    tvCheckTime.setOnClickListener(null);
                    //用药情况
                    rbDrugUseYieldRule.setClickable(false);
                    rbDrugUseYieldGap.setClickable(false);
                    rbDrugUseYieldNotTakeMedicine.setClickable(false);
                    rgAdverseDrugReactionHave.setClickable(false);
                    rgAdverseDrugReactionNot.setClickable(false);
                    rbHypoglycemicReactionNot.setClickable(false);
                    rbHypoglycemicReactionOnce.setClickable(false);
                    rbHypoglycemicReactionOften.setClickable(false);
                    etInsulin.setFocusable(false);
                    etInsulin.setFocusableInTouchMode(false);
                    etInsulinDosage.setFocusable(false);
                    etInsulinDosage.setFocusableInTouchMode(false);
                    //尿常规
                    etPissCommonOneLeft.setFocusable(false);
                    etPissCommonOneLeft.setFocusableInTouchMode(false);
                    etPissCommonOneRight.setFocusable(false);
                    etPissCommonOneRight.setFocusableInTouchMode(false);
                    etPissCommonTwoLeft.setFocusable(false);
                    etPissCommonTwoLeft.setFocusableInTouchMode(false);
                    etPissCommonTwoRight.setFocusable(false);
                    etPissCommonTwoRight.setFocusableInTouchMode(false);
                    etPissCommonThreeLeft.setFocusable(false);
                    etPissCommonThreeLeft.setFocusableInTouchMode(false);
                    etPissCommonThreeRight.setFocusable(false);
                    etPissCommonThreeRight.setFocusableInTouchMode(false);
                    etPissCommonFourLeft.setFocusable(false);
                    etPissCommonFourLeft.setFocusableInTouchMode(false);
                    etPissCommonFourRight.setFocusable(false);
                    etPissCommonFourRight.setFocusableInTouchMode(false);
                    etPissCommonFiveLeft.setFocusable(false);
                    etPissCommonFiveLeft.setFocusableInTouchMode(false);
                    etPissCommonFiveRight.setFocusable(false);
                    etPissCommonFiveRight.setFocusableInTouchMode(false);
                    etPissCommonSixLeft.setFocusable(false);
                    etPissCommonSixLeft.setFocusableInTouchMode(false);
                    //血脂
                    etBloodFatOne.setFocusable(false);
                    etBloodFatOne.setFocusableInTouchMode(false);
                    etBloodFatTwo.setFocusable(false);
                    etBloodFatTwo.setFocusableInTouchMode(false);
                    etBloodFatThree.setFocusable(false);
                    etBloodFatThree.setFocusableInTouchMode(false);
                    etBloodFatFour.setFocusable(false);
                    etBloodFatFour.setFocusableInTouchMode(false);
                    //尿微量蛋白
                    etPissTinyAlbumin.setFocusable(false);
                    etPissTinyAlbumin.setFocusableInTouchMode(false);
                    //血清肌酐
                    etSerum.setFocusable(false);
                    etSerum.setFocusableInTouchMode(false);
                    //促甲状腺激素
                    etTsh.setFocusable(false);
                    etTsh.setFocusableInTouchMode(false);
                    //心电图
                    tvHeartDesc.setFocusable(false);
                    tvHeartDesc.setFocusableInTouchMode(false);
                    //肝功能
                    tvLiverDesc.setFocusable(false);
                    tvLiverDesc.setFocusableInTouchMode(false);
                    //眼底照相
                    tvEyesDesc.setFocusable(false);
                    tvEyesDesc.setFocusableInTouchMode(false);
                    //神经病变相关
                    tvNeuropathyDesc.setFocusable(false);
                    tvNeuropathyDesc.setFocusableInTouchMode(false);
                }

                setRightText(status);
                //设置显示隐藏
                setVisibleOrGone(data);
                //设置头部
                setTopData(data);
                //设置十五个
                setFifteenData(data);
                break;

        }
    }


    /**
     * 知乎选择照片
     */
    private void selectPhoto(int type) {
        Matisse.from(FollowUpVisitBloodSugarSubmitActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.vice.bloodpressure.FileProvider"))
                //最多选1张
                .maxSelectable(1)
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                //.capture(true)
                //.captureStrategy(new CaptureStrategy(true, "com.vice.bloodpressure.FileProvider", "Test"))
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(type);
    }

    private String defaultValue(String value) {
        if (!TextUtils.isEmpty(value) && value != null) {
            return value;
        } else {
            return "";
        }
    }

    /**
     * 拍照和选择相册的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    photoPopu.dismiss();
                    if (file != null) {
                        String path = file.getPath();
                        liverPics.add(path);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE_LIVER:
                    List<String> pathList = Matisse.obtainPathResult(data);
                    if (pathList != null && 1 == pathList.size()) {
                        String path = pathList.get(0);
                        //选择照片返回的文件
                        if (mPosition < liverPics.size()) {
                            liverPics.set(mPosition, path);
                        } else if (liverPics.size() < 3) {
                            liverPics.add(path);
                        }
                        compress(liverPics, LIVER);
                        liverAddPicAdapter.setList(liverPics);
                        liverAddPicAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE_HEART:
                    List<String> pathList1 = Matisse.obtainPathResult(data);
                    if (pathList1 != null && 1 == pathList1.size()) {
                        String path = pathList1.get(0);
                        //选择照片返回的文件
                        if (mPosition < heartPics.size()) {
                            heartPics.set(mPosition, path);
                        } else if (heartPics.size() < 3) {
                            heartPics.add(path);
                        }
                        compress(heartPics, HEART);
                        heartAddPicAdapter.setList(heartPics);
                        heartAddPicAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE_EYES:
                    List<String> pathList2 = Matisse.obtainPathResult(data);
                    if (pathList2 != null && 1 == pathList2.size()) {
                        String path = pathList2.get(0);
                        //选择照片返回的文件
                        if (mPosition < eyesPics.size()) {
                            eyesPics.set(mPosition, path);
                        } else if (eyesPics.size() < 3) {
                            eyesPics.add(path);
                        }
                        compress(eyesPics, EYES);
                        eyesAddPicAdapter.setList(eyesPics);
                        eyesAddPicAdapter.notifyDataSetChanged();
                    }
                    break;
                case REQUEST_CODE_CHOOSE_NEUROPATHY:
                    List<String> pathList3 = Matisse.obtainPathResult(data);
                    if (pathList3 != null && 1 == pathList3.size()) {
                        String path = pathList3.get(0);
                        //选择照片返回的文件
                        if (mPosition < neuropathyPics.size()) {
                            neuropathyPics.set(mPosition, path);
                        } else if (neuropathyPics.size() < 3) {
                            neuropathyPics.add(path);
                        }
                        compress(neuropathyPics, NEUROPATHY);
                        neuropathyAddPicAdapter.setList(neuropathyPics);
                        neuropathyAddPicAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void compress(List<String> photos, int type) {

        Luban.with(this)
                .load(photos)
                .ignoreBy(1024)
                .setTargetDir("")
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif") || path.contains("http"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    List<String> list = new ArrayList<>();

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        String path = file.getPath();
                        list.add(path);
                        if (photos.size() == list.size()) {
                            Message msg = Message.obtain();
                            msg.what = COMPRESS_OVER;
                            msg.arg1 = type;
                            msg.obj = list;
                            sendHandlerMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

}
