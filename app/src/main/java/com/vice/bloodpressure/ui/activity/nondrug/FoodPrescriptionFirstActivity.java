package com.vice.bloodpressure.ui.activity.nondrug;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.PersonalRecordBeanFroNonDrug;
import com.vice.bloodpressure.bean.nondrug.FoodFirstBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.PickerUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 饮食处方 第一步填写资料
 * 作者: LYD
 * 创建日期: 2019/5/6 15:22
 */
public class FoodPrescriptionFirstActivity extends BaseHandlerActivity {
    private static final String TAG = "FoodPrescriptionFirstActivity";
    private static final int GET_DATA = 10086;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.et_age)
    EditText etAge;
    //减重新增腰围
    @BindView(R.id.et_waistline)
    EditText etWaistline;
    @BindView(R.id.et_recent_empty)
    EditText etRecentEmpty;
    @BindView(R.id.et_recent_two_hours)
    EditText etRecentTwoHours;
    @BindView(R.id.et_recent_sugar)
    EditText etRecentSugar;
    @BindView(R.id.et_high_pressure)
    EditText etHighPressure;
    @BindView(R.id.et_low_pressure)
    EditText etLowPressure;
    @BindView(R.id.et_tc)
    EditText etTc;
    @BindView(R.id.et_tg)
    EditText etTg;
    @BindView(R.id.et_ldl)
    EditText etLdl;
    @BindView(R.id.et_hdl)
    EditText etHdl;
    @BindView(R.id.tv_labour_intensity)
    TextView tvLabourIntensity;
    @BindView(R.id.tv_kidney)
    TextView tvKidney;
    @BindView(R.id.tv_smoke)
    TextView tvSmoke;
    @BindView(R.id.et_src)
    EditText etSrc;
    @BindView(R.id.et_acr)
    EditText etAcr;
    @BindView(R.id.et_gfr)
    EditText etGfr;
    @BindView(R.id.tv_diabetic_nephropathy)
    TextView tvDiabeticNephropathy;
    @BindView(R.id.tv_is_diabetic_nephropathy)
    TextView tvIsDiabeticNephropathy;
    @BindView(R.id.tv_diabetic_nephropathy_period)
    TextView tvDiabeticNephropathyPeriod;
    @BindView(R.id.tv_diabetic_nephropathy_time)
    TextView tvDiabeticNephropathyTime;
    @BindView(R.id.ll_diabetic_nephropathy_bottom_2)
    LinearLayout llDiabeticNephropathyBottom2;
    @BindView(R.id.tv_recent_three_days)
    TextView tvRecentThreeDays;
    @BindView(R.id.tv_recent_three_days_time)
    TextView tvRecentThreeDaysTime;

    @BindView(R.id.ll_yes)
    LinearLayout llYes;
    @BindView(R.id.tv_recent_three_days_is_balance)
    TextView tvRecentThreeDaysIsBalance;
    @BindView(R.id.ll_no_balance)
    LinearLayout llNoBalance;
    @BindView(R.id.tv_recent_three_days_add)
    TextView tvRecentThreeDaysAdd;
    @BindView(R.id.ll_no_add)
    LinearLayout llNoAdd;
    @BindView(R.id.tv_recent_three_days_add_count)
    TextView tvRecentThreeDaysAddCount;
    @BindView(R.id.ll_no_add_count)
    LinearLayout llNoAddCount;
    @BindView(R.id.tv_recent_three_days_add_time)
    TextView tvRecentThreeDaysAddTime;
    @BindView(R.id.ll_no_add_time)
    LinearLayout llNoAddTime;

    //减重隐藏
    @BindView(R.id.ll_weight_one)
    LinearLayout llWeightOne;
    @BindView(R.id.ll_weight_two)
    LinearLayout llWeightTwo;
    @BindView(R.id.ll_waistline)
    LinearLayout llWaistLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyboardUtils.fixAndroidBug5497(this);
        setTitle("填写资料");
        getData();
        setIsWeight();
    }

    private void setIsWeight() {
        String type = getIntent().getStringExtra("type");
        if ("weight".equals(type)) {
            llWeightOne.setVisibility(View.GONE);
            llWeightTwo.setVisibility(View.GONE);
            llWaistLine.setVisibility(View.VISIBLE);
            //减重改为肾病
            tvIsDiabeticNephropathy.setText("是否患有肾病");
        } else {
            llWeightOne.setVisibility(View.VISIBLE);
            llWeightTwo.setVisibility(View.VISIBLE);
            llWaistLine.setVisibility(View.GONE);
            //减重改为肾病
            tvIsDiabeticNephropathy.setText("是否患有糖尿病肾病");
        }
    }

    /**
     * 获取用户信息
     */
    private void getData() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", userLogin.getToken());
        XyUrl.okPost(XyUrl.PERSONAL_RECORD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                PersonalRecordBeanFroNonDrug personalRecordBean = JSONObject.parseObject(value, PersonalRecordBeanFroNonDrug.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = personalRecordBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 设置用户信息-身高体重年龄
     *
     * @param data
     */
    private void setData(PersonalRecordBeanFroNonDrug data) {
        String height = data.getHeight();
        String weight = data.getWeight();
        String age = data.getAge() + "";
        etHeight.setText(height);
        etWeight.setText(weight);
        etAge.setText(age);
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_food_prescription, contentLayout, false);
        return layout;
    }


    @OnClick({R.id.rl_labour_intensity,
            R.id.tv_labour_intensity,
            R.id.rl_kidney,
            R.id.tv_kidney,
            R.id.rl_smoke,
            R.id.tv_smoke,
            R.id.rl_diabetic_nephropathy,
            R.id.tv_diabetic_nephropathy,
            R.id.rl_diabetic_nephropathy_period,
            R.id.tv_diabetic_nephropathy_period,
            R.id.rl_diabetic_nephropathy_time,
            R.id.tv_diabetic_nephropathy_time,
            R.id.rl_recent_three_days,
            R.id.tv_recent_three_days,
            R.id.ll_yes,
            R.id.tv_recent_three_days_time,
            R.id.ll_no_balance,
            R.id.tv_recent_three_days_is_balance,
            R.id.ll_no_add,
            R.id.tv_recent_three_days_add,
            R.id.ll_no_add_count,
            R.id.tv_recent_three_days_add_count,
            R.id.ll_no_add_time,
            R.id.tv_recent_three_days_add_time,
            R.id.bt_next})
    public void onViewClicked(View view) {
        String[] stringYesOrNo = {"是", "否"};
        List<String> yesOrNoList = Arrays.asList(stringYesOrNo);
        switch (view.getId()) {
            //劳动强度 //1 轻体力 2 中体力 3 重体力
            case R.id.rl_labour_intensity:
            case R.id.tv_labour_intensity:
                KeyboardUtils.hideSoftInput(tvLabourIntensity);
                String[] strArray = {"轻体力", "中体力", "重体力"};
                List<String> labourIntensityList = Arrays.asList(strArray);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvLabourIntensity.setText(text);
                    }
                }, labourIntensityList);
                break;
            //肾损//是 否
            case R.id.rl_kidney:
            case R.id.tv_kidney:
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvKidney.setText(text);
                    }
                }, yesOrNoList);
                break;
            //吸烟
            case R.id.rl_smoke:
            case R.id.tv_smoke:
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvSmoke.setText(text);
                    }
                }, yesOrNoList);
                break;
            //糖尿病肾病  //1 确诊无 2 确诊有
            case R.id.rl_diabetic_nephropathy:
            case R.id.tv_diabetic_nephropathy:
                KeyboardUtils.hideSoftInput(tvDiabeticNephropathy);
                String[] diabeticString = {"确诊无", "确诊有"};
                List<String> diabeticList = Arrays.asList(diabeticString);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvDiabeticNephropathy.setText(text);
                        if ("确诊有".equals(text)) {
                            llDiabeticNephropathyBottom2.setVisibility(View.VISIBLE);
                        } else {
                            llDiabeticNephropathyBottom2.setVisibility(View.GONE);
                        }
                    }
                }, diabeticList);
                break;
            //糖尿病肾病 分期  Ⅰ期  Ⅱ期 Ⅲ期 Ⅳ期 Ⅴ期
            case R.id.rl_diabetic_nephropathy_period:
            case R.id.tv_diabetic_nephropathy_period:
                String[] diabeticPeriodString = {"Ⅰ期", "Ⅱ期", "Ⅲ期", "Ⅳ期", "Ⅴ期"};
                List<String> diabeticPeriodList = Arrays.asList(diabeticPeriodString);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvDiabeticNephropathyPeriod.setText(text);
                    }
                }, diabeticPeriodList);
                break;
            //糖尿病肾病 时间
            case R.id.rl_diabetic_nephropathy_time:
            case R.id.tv_diabetic_nephropathy_time:
                PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvDiabeticNephropathyTime.setText(content);
                    }
                });
                break;
            //近三天血糖是否偏高
            case R.id.rl_recent_three_days:
            case R.id.tv_recent_three_days:
                tvRecentThreeDays.setText("");
                tvRecentThreeDaysTime.setText("");
                tvRecentThreeDaysIsBalance.setText("");
                tvRecentThreeDaysAdd.setText("");
                tvRecentThreeDaysAddCount.setText("");
                tvRecentThreeDaysAddTime.setText("");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvRecentThreeDays.setText(text);
                        if ("是".equals(text)) {
                            llYes.setVisibility(View.VISIBLE);
                            llNoBalance.setVisibility(View.GONE);
                            llNoAdd.setVisibility(View.GONE);
                            llNoAddCount.setVisibility(View.GONE);
                            llNoAddTime.setVisibility(View.GONE);
                        } else {
                            llYes.setVisibility(View.GONE);
                            llNoBalance.setVisibility(View.VISIBLE);
                            llNoAdd.setVisibility(View.GONE);
                            llNoAddCount.setVisibility(View.GONE);
                            llNoAddTime.setVisibility(View.GONE);
                        }
                    }
                }, yesOrNoList);
                break;
            //血糖偏高时间
            case R.id.ll_yes:
            case R.id.tv_recent_three_days_time:
                tvRecentThreeDaysTime.setText("");
                tvRecentThreeDaysIsBalance.setText("");
                tvRecentThreeDaysAdd.setText("");
                tvRecentThreeDaysAddCount.setText("");
                tvRecentThreeDaysAddTime.setText("");
                String[] recentThreeDaysTimeString = {"早餐前/后", "午餐前/后", "晚餐前/后",
                        "早餐前/后,午餐前/后", "早餐前/后,晚餐前/后", "午餐前/后,晚餐前/后", "早餐前/后,午餐前/后,晚餐前/后"};
                List<String> recentThreeDaysTimeList = Arrays.asList(recentThreeDaysTimeString);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvRecentThreeDaysTime.setText(text);
                    }
                }, recentThreeDaysTimeList);
                break;
            //是否使用标准化均衡饮食方案
            case R.id.ll_no_balance:
            case R.id.tv_recent_three_days_is_balance:
                tvRecentThreeDaysIsBalance.setText("");
                tvRecentThreeDaysAdd.setText("");
                tvRecentThreeDaysAddCount.setText("");
                tvRecentThreeDaysAddTime.setText("");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvRecentThreeDaysIsBalance.setText(text);
                        if ("是".equals(text)) {
                            llNoAdd.setVisibility(View.GONE);
                            llNoAddCount.setVisibility(View.GONE);
                            llNoAddTime.setVisibility(View.GONE);
                        } else {
                            llNoAdd.setVisibility(View.VISIBLE);
                            llNoAddCount.setVisibility(View.GONE);
                            llNoAddTime.setVisibility(View.GONE);
                        }
                    }
                }, yesOrNoList);
                break;
            //是否使用加餐方案
            case R.id.ll_no_add:
            case R.id.tv_recent_three_days_add:
                tvRecentThreeDaysAdd.setText("");
                tvRecentThreeDaysAddCount.setText("");
                tvRecentThreeDaysAddTime.setText("");
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvRecentThreeDaysAdd.setText(text);
                        if ("是".equals(text)) {
                            llNoAddCount.setVisibility(View.VISIBLE);
                            llNoAddTime.setVisibility(View.GONE);
                        } else {
                            llNoAddCount.setVisibility(View.GONE);
                            llNoAddTime.setVisibility(View.GONE);
                        }
                    }
                }, yesOrNoList);
                break;
            //请选择加餐次数
            case R.id.ll_no_add_count:
            case R.id.tv_recent_three_days_add_count:
                tvRecentThreeDaysAddCount.setText("");
                tvRecentThreeDaysAddTime.setText("");
                String[] recentThreeDaysAddCountString = {"1次", "2次", "3次"};
                List<String> recentThreeDaysAddCountList = Arrays.asList(recentThreeDaysAddCountString);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvRecentThreeDaysAddCount.setText(text);
                        llNoAddTime.setVisibility(View.VISIBLE);
                    }

                }, recentThreeDaysAddCountList);
                break;
            //请选择加餐时间
            case R.id.ll_no_add_time:
            case R.id.tv_recent_three_days_add_time:
                tvRecentThreeDaysAddTime.setText("");
                String count = tvRecentThreeDaysAddCount.getText().toString().trim();
                String[] recentThreeDaysAddTimeString = {"早餐前/后", "午餐前/后", "晚餐前/后",
                        "早餐前/后,午餐前/后", "早餐前/后,晚餐前/后", "午餐前/后,晚餐前/后",
                        "早餐前/后、午餐前/后，早餐前/后、晚餐前/后，午餐前/后、晚餐前/后"};
                List<String> recentThreeDaysAddTimeList = Arrays.asList(recentThreeDaysAddTimeString);
                switch (count) {
                    case "1次":
                        recentThreeDaysAddTimeList = recentThreeDaysAddTimeList.subList(0, 3);
                        break;
                    case "2次":
                        recentThreeDaysAddTimeList = recentThreeDaysAddTimeList.subList(3, 6);
                        break;
                    case "3次":
                        recentThreeDaysAddTimeList = recentThreeDaysAddTimeList.subList(3, 7);
                        break;
                }
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvRecentThreeDaysAddTime.setText(text);
                    }

                }, recentThreeDaysAddTimeList);
                break;
            case R.id.bt_next://下一步
                toDoNext();
                break;
        }
    }

    /**
     * 下一步
     */
    private void toDoNext() {
        FoodFirstBean foodFirstBean = new FoodFirstBean();
        //第一部分
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        //减重新增腰围
        String waistline = etWaistline.getText().toString().trim();
        String recentEmpty = etRecentEmpty.getText().toString().trim();
        String recentTwoHours = etRecentTwoHours.getText().toString().trim();
        String recentSugar = etRecentSugar.getText().toString().trim();
        foodFirstBean.setHeight(height);
        foodFirstBean.setWeight(weight);
        foodFirstBean.setAge(age);
        foodFirstBean.setWaistline(waistline);
        foodFirstBean.setRecentEmpty(recentEmpty);
        foodFirstBean.setRecentTwoHours(recentTwoHours);
        foodFirstBean.setRecentSugar(recentSugar);
        //第二部分
        String highPressure = etHighPressure.getText().toString().trim();
        String lowPressure = etLowPressure.getText().toString().trim();
        String tc = etTc.getText().toString().trim();
        String tg = etTg.getText().toString().trim();
        String ldl = etLdl.getText().toString().trim();
        String hdl = etHdl.getText().toString().trim();
        foodFirstBean.setHighPressure(highPressure);
        foodFirstBean.setLowPressure(lowPressure);
        foodFirstBean.setTc(tc);
        foodFirstBean.setTg(tg);
        foodFirstBean.setLdl(ldl);
        foodFirstBean.setHdl(hdl);
        //第三部分
        String labourIntensity = tvLabourIntensity.getText().toString().trim();
        String kidney = tvKidney.getText().toString().trim();
        String smoke = tvSmoke.getText().toString().trim();
        switch (labourIntensity) {
            case "轻体力":
                foodFirstBean.setLabourIntensity("1");
                break;
            case "中体力":
                foodFirstBean.setLabourIntensity("2");
                break;
            case "重体力":
                foodFirstBean.setLabourIntensity("3");
                break;
        }
        switch (kidney) {
            case "是":
                foodFirstBean.setKidney("1");
                break;
            case "否":
                foodFirstBean.setKidney("2");
                break;
        }
        switch (smoke) {
            case "是":
                foodFirstBean.setSmoke("1");
                break;
            case "否":
                foodFirstBean.setSmoke("2");
                break;
        }
        //第四部分
        String src = etSrc.getText().toString().trim();
        String acr = etAcr.getText().toString().trim();
        String gfr = etGfr.getText().toString().trim();
        foodFirstBean.setSrc(src);
        foodFirstBean.setAcr(acr);
        foodFirstBean.setGfr(gfr);
        //第五部分
        String diabeticNephropathy = tvDiabeticNephropathy.getText().toString().trim();
        String diabeticNephropathyPeriod = tvDiabeticNephropathyPeriod.getText().toString().trim();
        String diabeticNephropathyTime = tvDiabeticNephropathyTime.getText().toString().trim();
        switch (diabeticNephropathy) {
            case "确诊无":
                foodFirstBean.setDiabeticNephropathy("1");
                break;
            case "确诊有":
                foodFirstBean.setDiabeticNephropathy("2");
                break;
        }
        //"Ⅰ期", "Ⅱ期", "Ⅲ期", "Ⅳ期", "Ⅴ期
        switch (diabeticNephropathyPeriod) {
            case "Ⅰ期":
                foodFirstBean.setDiabeticNephropathyPeriod("1");
                break;
            case "Ⅱ期":
                foodFirstBean.setDiabeticNephropathyPeriod("2");
                break;
            case "Ⅲ期":
                foodFirstBean.setDiabeticNephropathyPeriod("3");
                break;
            case "Ⅳ期":
                foodFirstBean.setDiabeticNephropathyPeriod("4");
                break;
            case "Ⅴ期":
                foodFirstBean.setDiabeticNephropathyPeriod("5");
                break;
        }
        foodFirstBean.setDiabeticNephropathyTime(diabeticNephropathyTime);
        //判断
        if (TextUtils.isEmpty(height)) {
            ToastUtils.showShort("请输入身高");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            ToastUtils.showShort("请输入体重");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            ToastUtils.showShort("请输入年龄");
            return;
        }
        //第六部分
        String type = getIntent().getStringExtra("type");
        if (!"weight".equals(type)) {
            String recentThreeDays = tvRecentThreeDays.getText().toString().trim();
            String recentThreeDaysTime = tvRecentThreeDaysTime.getText().toString().trim();
            String isBalance = tvRecentThreeDaysIsBalance.getText().toString().trim();
            String recentThreeDaysAdd = tvRecentThreeDaysAdd.getText().toString().trim();
            String recentThreeDaysAddCount = tvRecentThreeDaysAddCount.getText().toString().trim();
            String recentThreeDaysAddTime = tvRecentThreeDaysAddTime.getText().toString().trim();
            switch (recentThreeDays) {
                case "是":
                    foodFirstBean.setRecentThreeDays("1");
                    break;
                case "否":
                    foodFirstBean.setRecentThreeDays("2");
                    break;
            }
            switch (recentThreeDaysTime) {
                case "早餐前/后":
                    foodFirstBean.setRecentThreeDaysTime("1");
                    break;
                case "午餐前/后":
                    foodFirstBean.setRecentThreeDaysTime("2");
                    break;
                case "晚餐前/后":
                    foodFirstBean.setRecentThreeDaysTime("3");
                    break;
                case "早餐前/后,午餐前/后":
                    foodFirstBean.setRecentThreeDaysTime("4");
                    break;
                case "早餐前/后,晚餐前/后":
                    foodFirstBean.setRecentThreeDaysTime("5");
                    break;
                case "午餐前/后,晚餐前/后":
                    foodFirstBean.setRecentThreeDaysTime("6");
                    break;
                case "早餐前/后,午餐前/后,晚餐前/后":
                    foodFirstBean.setRecentThreeDaysTime("7");
                    break;
            }
            switch (isBalance) {
                case "是":
                    foodFirstBean.setIsBalance("1");
                    break;
                case "否":
                    foodFirstBean.setIsBalance("2");
                    break;
            }
            switch (recentThreeDaysAdd) {
                case "是":
                    foodFirstBean.setRecentThreeDaysAdd("1");
                    break;
                case "否":
                    foodFirstBean.setRecentThreeDaysAdd("2");
                    break;
            }
            switch (recentThreeDaysAddCount) {
                case "1次":
                    foodFirstBean.setRecentThreeDaysAddCount("1");
                    switch (recentThreeDaysAddTime) {
                        case "早餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("1");
                            break;
                        case "午餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("2");
                            break;
                        case "晚餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("3");
                            break;
                    }
                    break;
                case "2次":
                    foodFirstBean.setRecentThreeDaysAddCount("2");
                    switch (recentThreeDaysAddTime) {
                        case "早餐前/后,午餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("1");
                            break;
                        case "早餐前/后,晚餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("2");
                            break;
                        case "午餐前/后,晚餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("3");
                            break;
                    }
                    break;
                case "3次":
                    foodFirstBean.setRecentThreeDaysAddCount("3");
                    switch (recentThreeDaysAddTime) {
                        case "早餐前/后,午餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("1");
                            break;
                        case "早餐前/后,晚餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("2");
                            break;
                        case "午餐前/后,晚餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("3");
                            break;
                        case "早餐前/后、午餐前/后，早餐前/后、晚餐前/后，午餐前/后、晚餐前/后":
                            foodFirstBean.setRecentThreeDaysAddTime("4");
                            break;
                    }
                    break;
            }
            if (TextUtils.isEmpty(recentThreeDays)) {
                ToastUtils.showShort("请选择近三天血糖是否偏高");
                return;
            }
            //血糖偏高时间
            if (llYes.getVisibility() == View.VISIBLE) {
                if (TextUtils.isEmpty(recentThreeDaysTime)) {
                    ToastUtils.showShort("请选择血糖偏高的时间");
                    return;
                }
            }
            //是否使用标准化饮食
            if (llNoBalance.getVisibility() == View.VISIBLE) {
                if (TextUtils.isEmpty(isBalance)) {
                    ToastUtils.showShort("请选择是否使用标准化饮食");
                    return;
                }
            }
            //是否采用加餐方案
            if (llNoAdd.getVisibility() == View.VISIBLE) {
                if (TextUtils.isEmpty(recentThreeDaysAdd)) {
                    ToastUtils.showShort("请选择是否使用加餐方案");
                    return;
                }
            }
            //加餐次数
            if (llNoAddCount.getVisibility() == View.VISIBLE) {
                if (TextUtils.isEmpty(recentThreeDaysAddCount)) {
                    ToastUtils.showShort("请选择加餐次数");
                    return;
                }
                if (TextUtils.isEmpty(recentThreeDaysAddTime)) {
                    ToastUtils.showShort("请选择加餐时间");
                    return;
                }
            }
        }
        SharedPreferencesUtils.putBean(getPageContext(), SharedPreferencesUtils.FOOD_FIRST, foodFirstBean);
        Intent intent = new Intent(getPageContext(), FoodPrescriptionSecondActivity.class);
        intent.putExtra("type", getIntent().getStringExtra("type"));
        startActivity(intent);
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                PersonalRecordBeanFroNonDrug data = (PersonalRecordBeanFroNonDrug) msg.obj;
                setData(data);
                break;
        }
    }
}
