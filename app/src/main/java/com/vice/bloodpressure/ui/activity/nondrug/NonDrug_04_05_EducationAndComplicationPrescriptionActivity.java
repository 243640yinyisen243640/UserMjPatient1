package com.vice.bloodpressure.ui.activity.nondrug;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.Gson;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.EducationFiveAdapter;
import com.vice.bloodpressure.adapter.NonDrugSportMultiAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.NonDrugSportMultiBean;
import com.vice.bloodpressure.bean.nondrug.FoodFirstBean;
import com.vice.bloodpressure.bean.nondrug.NonDrugResultBean;
import com.vice.bloodpressure.bean.nondrug.SportBean;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.PickerUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 教育和并发症出发
 * 作者: LYD
 * 创建日期: 2019/5/8 11:41
 */
public class NonDrug_04_05_EducationAndComplicationPrescriptionActivity extends BaseHandlerActivity {
    private static final int ADD_SUCCESS = 10010;
    private static final String TAG = "EducationAndComplicationPrescriptionActivity";
    @BindView(R.id.tv_retina_condition)
    TextView tvRetinaCondition;
    @BindView(R.id.tv_retina_time)
    TextView tvRetinaTime;
    @BindView(R.id.tv_retina_symptom)
    TextView tvRetinaSymptom;
    @BindView(R.id.rl_retina_symptom)
    LinearLayout rlRetinaSymptom;

    @BindView(R.id.tv_nerve_condition)
    TextView tvNerveCondition;
    @BindView(R.id.tv_nerve_time)
    TextView tvNerveTime;
    @BindView(R.id.rv_nerve_check)
    RecyclerView rvNerveCheck;

    @BindView(R.id.tv_legs_condition)
    TextView tvLegsCondition;
    @BindView(R.id.tv_legs_time)
    TextView tvLegsTime;
    @BindView(R.id.rv_legs_check)
    RecyclerView rvLegsCheck;

    @BindView(R.id.rv_five)
    RecyclerView rvFive;

    @BindView(R.id.tv_smoke_history)
    TextView tvSmokeHistory;
    @BindView(R.id.tv_sugar_condition)
    TextView tvSugarCondition;
    @BindView(R.id.tv_sugar_time)
    TextView tvSugarTime;
    @BindView(R.id.tv_sugar_symptom)
    TextView tvSugarSymptom;
    @BindView(R.id.rl_sugar_symptom)
    LinearLayout rlSugarSymptom;
    @BindView(R.id.ll_education)
    LinearLayout llEducation;


    //糖尿病类型
    @BindView(R.id.rl_blood_type)
    RelativeLayout rlBloodType;
    @BindView(R.id.tv_blood_type)
    TextView tvBloodType;
    @BindView(R.id.rl_blood_time)
    RelativeLayout rlBloodTime;
    @BindView(R.id.tv_blood_time)
    TextView tvBloodTime;

    private NonDrugSportMultiAdapter nerveAdapter;
    private NonDrugSportMultiAdapter legsAdapter;
    private List<String> selectDatasFirst = new ArrayList<>();
    private List<String> selectDatasSecond = new ArrayList<>();

    private EducationFiveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isHaveEducation();
        setTitle("并发症处方");
        initRvNerve();
        initRvFive();
    }

    private void isHaveEducation() {
        String educationState = SPStaticUtils.getString("educationState");
        if ("1".equals(educationState)) {
            llEducation.setVisibility(View.GONE);
        } else {
            llEducation.setVisibility(View.GONE);
        }
    }

    /**
     * 高血压 下五个
     */
    private void initRvFive() {
        rvFive.setLayoutManager(new LinearLayoutManager(this));
        String[] stringArray = getResources().getStringArray(R.array.data_five);
        adapter = new EducationFiveAdapter(getPageContext(), R.layout.item_rv_five, Arrays.asList(stringArray));
        rvFive.setAdapter(adapter);
    }

    /**
     * 糖尿病下肢血管病变
     *
     * @param type
     */
    private void initRvLegs(String type) {
        String[] listString;
        if ("0".equals(type)) {
            listString = getResources().getStringArray(R.array.data_legs_have_symptom);
        } else {
            listString = getResources().getStringArray(R.array.data_legs_no_symptom);
        }
        ArrayList<NonDrugSportMultiBean> list = new ArrayList<>();
        for (int i = 0; i < listString.length; i++) {
            list.add(new NonDrugSportMultiBean(listString[i], NonDrugSportMultiBean.Type.TypeOne));
        }
        legsAdapter = new NonDrugSportMultiAdapter(list);
        rvLegsCheck.setLayoutManager(new LinearLayoutManager(this));
        rvLegsCheck.setAdapter(legsAdapter);
        legsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!legsAdapter.isSelected.get(position)) {
                    legsAdapter.isSelected.put(position, true);
                    legsAdapter.notifyItemChanged(position);
                    selectDatasSecond.add(position + "");
                } else {
                    legsAdapter.isSelected.put(position, false);
                    legsAdapter.notifyItemChanged(position);
                    selectDatasSecond.remove(position + "");
                }
            }
        });
    }


    /**
     * 糖尿病神经病变
     */
    private void initRvNerve() {
        String[] listString = getResources().getStringArray(R.array.data_nerve_no_symptom);
        ArrayList<NonDrugSportMultiBean> list = new ArrayList<>();
        for (String s : listString) {
            list.add(new NonDrugSportMultiBean(s, NonDrugSportMultiBean.Type.TypeOne));
        }
        nerveAdapter = new NonDrugSportMultiAdapter(list);
        rvNerveCheck.setLayoutManager(new LinearLayoutManager(this));
        rvNerveCheck.setAdapter(nerveAdapter);
        nerveAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!nerveAdapter.isSelected.get(position)) {
                    nerveAdapter.isSelected.put(position, true);//修改map的值保存状态
                    nerveAdapter.notifyItemChanged(position);
                    selectDatasFirst.add(position + "");
                } else {
                    nerveAdapter.isSelected.put(position, false);//修改map的值保存状态
                    nerveAdapter.notifyItemChanged(position);
                    selectDatasFirst.remove(position + "");
                }
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_education_and_complication_prescription, contentLayout, false);
        return layout;
    }


    @OnClick({
            R.id.tv_sugar_condition, R.id.rl_sugar_condition,
            R.id.tv_sugar_time, R.id.rl_sugar_time,

            R.id.tv_sugar_symptom, R.id.rl_sugar_symptom,
            R.id.rl_retina_condition,
            R.id.tv_retina_condition,

            R.id.rl_retina_time,
            R.id.tv_retina_time,

            R.id.rl_retina_symptom,
            R.id.tv_retina_symptom,

            R.id.rl_nerve_condition,
            R.id.tv_nerve_condition,

            R.id.rl_nerve_time,
            R.id.tv_nerve_time,

            R.id.rl_legs_condition,
            R.id.tv_legs_condition,

            R.id.rl_smoke_history,
            R.id.tv_smoke_history,

            R.id.rl_legs_time,
            R.id.tv_legs_time,

            R.id.rl_blood_type,
            R.id.tv_blood_type,

            R.id.rl_blood_time,
            R.id.tv_blood_time,

            R.id.bt_done})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //糖尿病肾病
            case R.id.tv_sugar_condition:
            case R.id.rl_sugar_condition:
                showOptionThreePicker("-1");
                break;
            //糖尿病肾病 确诊时间
            case R.id.tv_sugar_time:
            case R.id.rl_sugar_time:
                showTimePicker("-1");
                break;
            //糖尿病肾病 症状
            case R.id.tv_sugar_symptom:
            case R.id.rl_sugar_symptom:
                String sugarCondition = tvSugarCondition.getText().toString().trim();
                String[] sugarHaveSymptom = getResources().getStringArray(R.array.data_sugar_have_symptom);
                String[] sugarNoSymptom = getResources().getStringArray(R.array.data_sugar_no_symptom);
                if ("确诊有".equals(sugarCondition)) {
                    PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                        @Override
                        public void execEvent(String text) {
                            tvSugarSymptom.setText(text);
                        }
                    }, Arrays.asList(sugarHaveSymptom));
                } else {
                    PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                        @Override
                        public void execEvent(String text) {
                            tvSugarSymptom.setText(text);
                        }
                    }, Arrays.asList(sugarNoSymptom));
                }
                break;
            //糖尿病视网膜病变
            case R.id.rl_retina_condition:
            case R.id.tv_retina_condition:
                showOptionThreePicker("0");
                break;
            //糖尿病视网膜病变 时间
            case R.id.rl_retina_time:
            case R.id.tv_retina_time:
                showTimePicker("0");
                break;
            //糖尿病视网膜病变 症状
            case R.id.rl_retina_symptom:
            case R.id.tv_retina_symptom:
                String retinaCondition = tvRetinaCondition.getText().toString().trim();
                String[] retinaHaveSymptom = getResources().getStringArray(R.array.data_retina_have_symptom);
                String[] retinaNoSymptom = getResources().getStringArray(R.array.data_retina_no_symptom);
                if ("确诊有".equals(retinaCondition)) {
                    PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                        @Override
                        public void execEvent(String text) {
                            tvRetinaSymptom.setText(text);
                        }
                    }, Arrays.asList(retinaHaveSymptom));
                } else {
                    PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                        @Override
                        public void execEvent(String text) {
                            tvRetinaSymptom.setText(text);
                        }
                    }, Arrays.asList(retinaNoSymptom));
                }
                break;
            //糖尿病神经病变
            case R.id.rl_nerve_condition:
            case R.id.tv_nerve_condition:
                showOptionThreePicker("1");
                break;
            //糖尿病神经病变 时间
            case R.id.rl_nerve_time:
            case R.id.tv_nerve_time:
                showTimePicker("1");
                break;
            //糖尿病下肢血管病变
            case R.id.rl_legs_condition:
            case R.id.tv_legs_condition:
                showOptionThreePicker("2");
                break;
            //糖尿病下肢血管病变 时间
            case R.id.rl_legs_time:
            case R.id.tv_legs_time:
                showTimePicker("2");
                break;
            //吸烟史//是 否
            case R.id.rl_smoke_history:
            case R.id.tv_smoke_history:
                String[] stringYesOrNo = {"是", "否"};
                List<String> yesOrNoList = Arrays.asList(stringYesOrNo);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvSmokeHistory.setText(text);
                    }
                }, yesOrNoList);
                break;
            //糖尿病类型确诊情况
            case R.id.rl_blood_type:
            case R.id.tv_blood_type:
                String[] diabetesMellitusCondition = getResources().getStringArray(R.array.tnb_type);
                PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String text) {
                        tvBloodType.setText(text);
                    }
                }, Arrays.asList(diabetesMellitusCondition));
                break;
            //糖尿病类型时间
            case R.id.rl_blood_time:
            case R.id.tv_blood_time:
                showTimePicker("3");
                break;
            case R.id.bt_done:
                toDoSubmit();
                break;
        }
    }

    private void toDoSubmit() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        FoodFirstBean foodFirstBean = (FoodFirstBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.FOOD_FIRST);
        NonDrugResultBean nonDrugResultBean = new NonDrugResultBean();
        nonDrugResultBean.setUserid(userLogin.getUserid());
        //第一部分设置
        NonDrugResultBean.DietaryBean dietaryBean = new NonDrugResultBean.DietaryBean();
        dietaryBean.setTgshengao(foodFirstBean.getHeight());
        dietaryBean.setTgtizhong(foodFirstBean.getWeight());
        dietaryBean.setAge(foodFirstBean.getAge());
        dietaryBean.setXtkongfu(foodFirstBean.getRecentEmpty());
        dietaryBean.setXtcaihou(foodFirstBean.getRecentTwoHours());
        dietaryBean.setXthbalc(foodFirstBean.getRecentSugar());
        //第二部分设置
        dietaryBean.setSystolic(foodFirstBean.getHighPressure());
        dietaryBean.setDiastolic(foodFirstBean.getLowPressure());
        dietaryBean.setSytc(foodFirstBean.getTc());
        dietaryBean.setSytg(foodFirstBean.getTg());
        dietaryBean.setSyldl(foodFirstBean.getLdl());
        dietaryBean.setSyhdl(foodFirstBean.getHdl());
        //第三部分
        dietaryBean.setJbzhiye(foodFirstBean.getLabourIntensity());
        dietaryBean.setSssh(foodFirstBean.getKidney());
        dietaryBean.setSmoke(foodFirstBean.getSmoke());
        //第四部分
        dietaryBean.setSyxqjg(foodFirstBean.getSrc());
        dietaryBean.setSyacr(foodFirstBean.getAcr());
        dietaryBean.setSyxqgfr(foodFirstBean.getGfr());
        //第五部分
        dietaryBean.setNephropathy(foodFirstBean.getDiabeticNephropathy());
        dietaryBean.setNephropathylei(foodFirstBean.getDiabeticNephropathyPeriod());
        dietaryBean.setBf2time(foodFirstBean.getDiabeticNephropathyTime());
        //第六部分
        dietaryBean.setXtpg(foodFirstBean.getRecentThreeDays());
        dietaryBean.setXtpgdian(foodFirstBean.getRecentThreeDaysTime());
        dietaryBean.setBzjhys(foodFirstBean.getIsBalance());
        dietaryBean.setJiacanis(foodFirstBean.getRecentThreeDaysAdd());
        String recentThreeDaysAddCount = foodFirstBean.getRecentThreeDaysAddCount();
        if (!TextUtils.isEmpty(recentThreeDaysAddCount)) {
            dietaryBean.setJiacanci(recentThreeDaysAddCount);
            switch (recentThreeDaysAddCount) {
                case "1":
                    dietaryBean.setJiacansj1(foodFirstBean.getRecentThreeDaysAddTime());
                    break;
                case "2":
                    dietaryBean.setJiacansj2(foodFirstBean.getRecentThreeDaysAddTime());
                    break;
                case "3":
                    dietaryBean.setJiacansj3(foodFirstBean.getRecentThreeDaysAddTime());
                    break;
            }
        }

        //第二部分提交
        HashMap<Integer, String> selectMap = new Gson().fromJson(SPStaticUtils.getString("selectMap"), new HashMap<>().getClass());
        HashMap<Integer, String> saveMap = new Gson().fromJson(SPStaticUtils.getString("saveMap"), new HashMap<>().getClass());
        NonDrugResultBean.DietaryBean.FoodsBean foodsBean = new NonDrugResultBean.DietaryBean.FoodsBean();

        NonDrugResultBean.DietaryBean.FoodsBean.VapotatoBean vapotatoBean = new NonDrugResultBean.DietaryBean.FoodsBean.VapotatoBean();
        for (Map.Entry<Integer, String> entrySelect : selectMap.entrySet()) {
            switch (entrySelect.getValue()) {
                case "大米":
                    vapotatoBean.setDami(saveMap.get(entrySelect.getKey()));
                    break;
                case "面粉":
                    vapotatoBean.setMianfen(saveMap.get(entrySelect.getKey()));
                    break;
                case "混合面":
                    vapotatoBean.setHunhe(saveMap.get(entrySelect.getKey()));
                    break;
                case "各种挂面":
                    vapotatoBean.setGuamian(saveMap.get(entrySelect.getKey()));
                    break;
                case "绿豆、豆、干豌豆":
                    vapotatoBean.setLvdou(saveMap.get(entrySelect.getKey()));
                    break;
                case "油饼":
                    vapotatoBean.setYoubing(saveMap.get(entrySelect.getKey()));
                    break;
                case "油条":
                    vapotatoBean.setYoutiao(saveMap.get(entrySelect.getKey()));
                    break;
                case "马铃薯":
                    vapotatoBean.setMalingshu(saveMap.get(entrySelect.getKey()));
                    break;
                case "馒头":
                    vapotatoBean.setMantou(saveMap.get(entrySelect.getKey()));
                    break;
                case "烙饼":
                    vapotatoBean.setLaobing(saveMap.get(entrySelect.getKey()));
                    break;
                case "面饼":
                    vapotatoBean.setMianbing(saveMap.get(entrySelect.getKey()));
                    break;
                case "烧饼":
                    vapotatoBean.setShaobing(saveMap.get(entrySelect.getKey()));
                    break;
                case "咸面包":
                    vapotatoBean.setMianbao(saveMap.get(entrySelect.getKey()));
                    break;
                case "窝窝头":
                    vapotatoBean.setWowo(saveMap.get(entrySelect.getKey()));
                    break;
                case "生面条":
                    vapotatoBean.setMiantiao(saveMap.get(entrySelect.getKey()));
                    break;
            }
        }
        //蔬菜类
        String vegetable = SPStaticUtils.getString("vegetable");
        foodsBean.setVegetables(vegetable);

        //水果
        HashMap<Integer, String> selectMapFruit = new Gson().fromJson(SPStaticUtils.getString("selectMapFruit"), new HashMap<>().getClass());
        HashMap<Integer, String> saveMapFruit = new Gson().fromJson(SPStaticUtils.getString("saveMapFruit"), new HashMap<>().getClass());
        NonDrugResultBean.DietaryBean.FoodsBean.FruitsBean fruitBean = new NonDrugResultBean.DietaryBean.FoodsBean.FruitsBean();
        for (Map.Entry<Integer, String> entrySelect : selectMapFruit.entrySet()) {
            switch (entrySelect.getValue()) {
                case "杏":
                    fruitBean.setXing(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "梨":
                    fruitBean.setLi(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "橘子":
                    fruitBean.setJvzi(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "橙子":
                    fruitBean.setChengzi(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "柚子":
                    fruitBean.setYouzi(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "桃":
                    fruitBean.setTao(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "葡萄(带皮)":
                    fruitBean.setPutao(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "苹果(带皮)":
                    fruitBean.setPingguo(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "猕猴桃(带皮)":
                    fruitBean.setMihoutao(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "柿":
                    fruitBean.setShi(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "香蕉":
                    fruitBean.setXiangjiao(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "鲜荔枝":
                    fruitBean.setLizhi(saveMapFruit.get(entrySelect.getKey()));
                    break;
                case "西瓜":
                    fruitBean.setXigua(saveMapFruit.get(entrySelect.getKey()));
                    break;
            }
        }
        //豆制品
        HashMap<Integer, String> selectMapBean = new Gson().fromJson(SPStaticUtils.getString("selectMapBean"), new HashMap<>().getClass());
        HashMap<Integer, String> saveMapBean = new Gson().fromJson(SPStaticUtils.getString("saveMapBean"), new HashMap<>().getClass());
        NonDrugResultBean.DietaryBean.FoodsBean.SoyproductsBean soyproductsBean = new NonDrugResultBean.DietaryBean.FoodsBean.SoyproductsBean();
        for (Map.Entry<Integer, String> entrySelect : selectMapBean.entrySet()) {
            switch (entrySelect.getValue()) {
                case "腐竹":
                    soyproductsBean.setFuzhu(saveMapBean.get(entrySelect.getKey()));
                    break;
                case "北豆腐":
                    soyproductsBean.setBeidoufu(saveMapBean.get(entrySelect.getKey()));
                    break;
                case "南豆腐":
                    soyproductsBean.setNandoufu(saveMapBean.get(entrySelect.getKey()));
                    break;
                case "豆浆":
                    soyproductsBean.setDoujiang(saveMapBean.get(entrySelect.getKey()));
                    break;
            }
        }
        //肉蛋
        HashMap<Integer, String> selectMapMeat = new Gson().fromJson(SPStaticUtils.getString("selectMapMeat"), new HashMap<>().getClass());
        HashMap<Integer, String> saveMapMeat = new Gson().fromJson(SPStaticUtils.getString("saveMapMeat"), new HashMap<>().getClass());
        NonDrugResultBean.DietaryBean.FoodsBean.MeateggsBean meateggsBean = new NonDrugResultBean.DietaryBean.FoodsBean.MeateggsBean();
        for (Map.Entry<Integer, String> entrySelect : selectMapMeat.entrySet()) {
            switch (entrySelect.getValue()) {
                case "瘦猪肉":
                    meateggsBean.setShouzhurou(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "牛肉":
                    meateggsBean.setNiurou(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "羊肉":
                    meateggsBean.setYangrou(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "鸭肉":
                    meateggsBean.setYarou(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "带骨排骨":
                    meateggsBean.setPaigu(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "鸡蛋(一大个带壳)":
                    meateggsBean.setJidan(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "鸭蛋、松花蛋(一大个带壳)":
                    meateggsBean.setYadan(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "鹌鹑蛋(六个带壳)":
                    meateggsBean.setAnchun(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "虾类":
                    meateggsBean.setXia(saveMapMeat.get(entrySelect.getKey()));
                    break;
                case "鱼类":
                    meateggsBean.setYu(saveMapMeat.get(entrySelect.getKey()));
                    break;
            }
        }
        //奶
        HashMap<Integer, String> selectMapMilk = new Gson().fromJson(SPStaticUtils.getString("selectMapMilk"), new HashMap<>().getClass());
        HashMap<Integer, String> saveMapMilk = new Gson().fromJson(SPStaticUtils.getString("saveMapMilk"), new HashMap<>().getClass());
        NonDrugResultBean.DietaryBean.FoodsBean.MilksBean milksBean = new NonDrugResultBean.DietaryBean.FoodsBean.MilksBean();
        for (Map.Entry<Integer, String> entrySelect : selectMapMilk.entrySet()) {
            switch (entrySelect.getValue()) {
                case "奶粉":
                    milksBean.setNaifen(saveMapMilk.get(entrySelect.getKey()));
                    break;
                case "牛奶":
                    milksBean.setNiunai(saveMapMilk.get(entrySelect.getKey()));
                    break;
                case "羊奶":
                    milksBean.setYangnai(saveMapMilk.get(entrySelect.getKey()));
                    break;
                case "其他":
                    milksBean.setQita(saveMapMilk.get(entrySelect.getKey()));
                    break;
            }
        }
        //油脂
        String oil = SPStaticUtils.getString("oil");
        foodsBean.setOils(oil);
        //坚果
        HashMap<Integer, String> selectMapNut = new Gson().fromJson(SPStaticUtils.getString("selectMapNut"), new HashMap<>().getClass());
        HashMap<Integer, String> saveMapNut = new Gson().fromJson(SPStaticUtils.getString("saveMapNut"), new HashMap<>().getClass());
        NonDrugResultBean.DietaryBean.FoodsBean.NutsBean nutsBean = new NonDrugResultBean.DietaryBean.FoodsBean.NutsBean();
        for (Map.Entry<Integer, String> entrySelect : selectMapNut.entrySet()) {
            switch (entrySelect.getValue()) {
                case "杏仁":
                    nutsBean.setXingren(saveMapNut.get(entrySelect.getKey()));
                    break;
                case "花生米":
                    nutsBean.setHuashengmi(saveMapNut.get(entrySelect.getKey()));
                    break;
                case "核桃":
                    nutsBean.setHetao(saveMapNut.get(entrySelect.getKey()));
                    break;
                case "其他":
                    nutsBean.setNutqita(saveMapNut.get(entrySelect.getKey()));
                    break;
            }
        }
        //酒
        HashMap<Integer, String> selectMapWine = new Gson().fromJson(SPStaticUtils.getString("selectMapWine"), new HashMap<>().getClass());
        HashMap<Integer, String> saveMapWine = new Gson().fromJson(SPStaticUtils.getString("saveMapWine"), new HashMap<>().getClass());
        HashMap<Integer, String> saveMapWineSecond = new Gson().fromJson(SPStaticUtils.getString("saveMapWineSecond"), new HashMap<>().getClass());
        NonDrugResultBean.DietaryBean.FoodsBean.WinesBean winesBean = new NonDrugResultBean.DietaryBean.FoodsBean.WinesBean();
        for (Map.Entry<Integer, String> entrySelect : selectMapWine.entrySet()) {
            switch (entrySelect.getValue()) {
                case "红酒":
                    winesBean.setHongjiu(saveMapWine.get(entrySelect.getKey()));
                    winesBean.setWinenum(saveMapWineSecond.get(entrySelect.getKey()));
                    break;
                case "啤酒":
                    winesBean.setPijiu(saveMapWine.get(entrySelect.getKey()));
                    winesBean.setWinenum(saveMapWineSecond.get(entrySelect.getKey()));
                    break;
                case "白酒":
                    winesBean.setBaijiu(saveMapWine.get(entrySelect.getKey()));
                    winesBean.setWinenum(saveMapWineSecond.get(entrySelect.getKey()));
                    break;
                case "黄酒":
                    winesBean.setHuangjiu(saveMapWine.get(entrySelect.getKey()));
                    winesBean.setWinenum(saveMapWineSecond.get(entrySelect.getKey()));
                    break;
            }
        }
        //日常饮食
        foodsBean.setVapotato(vapotatoBean);
        foodsBean.setFruits(fruitBean);
        foodsBean.setSoyproducts(soyproductsBean);
        foodsBean.setMeateggs(meateggsBean);
        foodsBean.setMilks(milksBean);
        foodsBean.setNuts(nutsBean);
        foodsBean.setWines(winesBean);
        //设置日常饮食
        dietaryBean.setFoods(foodsBean);
        nonDrugResultBean.setDietary(dietaryBean);
        //运动处方
        String sportState = SPStaticUtils.getString("sportState");
        if ("1".equals(sportState)) {
            String sportSave = SPStaticUtils.getString("sportSave");
            if ("1".equals(sportSave)) {
                SportBean sportBean = (SportBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.SPORT);
                NonDrugResultBean.SportsBean sportsBean = new NonDrugResultBean.SportsBean();
                sportsBean.setYdbu(sportBean.getYdbu());
                sportsBean.setSteps(sportBean.getSteps());
                sportsBean.setYdzhuan(sportBean.getYdzhuan());
                sportsBean.setKong(sportBean.getKong());
                sportsBean.setYdcitime(sportBean.getYdcitime());
                sportsBean.setYdzhouci(sportBean.getYdzhouci());
                sportsBean.setYdls1("1");
                sportsBean.setYdls2("1");
                sportsBean.setYdls3("1");
                sportsBean.setYdls4("1");
                sportsBean.setYdls5("1");
                sportsBean.setYdls6("1");
                sportsBean.setYdls7("1");
                sportsBean.setYdls8("1");
                sportsBean.setYdls9("1");
                sportsBean.setYdls10("1");
                sportsBean.setYdls11(sportBean.getYdls11());
                sportsBean.setYdls12(sportBean.getYdls12());
                sportsBean.setYdls13(sportBean.getYdls13());
                sportsBean.setYdls14(sportBean.getYdls14());
                sportsBean.setYdls15(sportBean.getYdls15());
                sportsBean.setBf10(sportBean.getBf10());
                nonDrugResultBean.setSports(sportsBean);
            }
        }
        //监测处方
        String checkState = SPStaticUtils.getString("checkState");
        if ("1".equals(checkState)) {
            String checkPrescription = SPStaticUtils.getString("CheckPrescription");
            nonDrugResultBean.setMonitor(checkPrescription);
        }
        //教育处方
        String educationState = SPStaticUtils.getString("educationState");
        nonDrugResultBean.setEducation(educationState);
        //并发症处方
        NonDrugResultBean.ComplicationsBean complicationsBean = new NonDrugResultBean.ComplicationsBean();
        String sugarConditionText = tvSugarCondition.getText().toString().trim();
        String sugarTimeText = tvSugarTime.getText().toString().trim();
        String sugarSymptomText = tvSugarSymptom.getText().toString().trim();
        switch (sugarConditionText) {
            case "确诊无":
                complicationsBean.setBf1("1");
                break;
            case "确诊有":
                complicationsBean.setBf1("2");
                switch (sugarSymptomText) {
                    case "I期":
                        complicationsBean.setNephropathylei("1");
                        break;
                    case "II期":
                        complicationsBean.setNephropathylei("2");
                        break;
                    case "III期":
                        complicationsBean.setNephropathylei("3");
                        break;
                    case "IV期":
                        complicationsBean.setNephropathylei("4");
                        break;
                    case "V期":
                        complicationsBean.setNephropathylei("5");
                        break;
                }
                break;
            case "未诊断":
                complicationsBean.setBf1("3");
                if ("无症状".equals(sugarSymptomText)) {
                    complicationsBean.setBf1z3("1");
                } else {
                    complicationsBean.setBf1z3("2");
                }
                break;
        }
        String secondFromString = getSecondFromString(sugarTimeText);
        Log.e(TAG, "secondFromString: " + secondFromString);
        complicationsBean.setBf1time(secondFromString);
        String retinaConditionText = tvRetinaCondition.getText().toString().trim();
        String retinaTimeText = tvRetinaTime.getText().toString().trim();
        String retinaSymptomText = tvRetinaSymptom.getText().toString().trim();
        switch (retinaConditionText) {
            case "确诊无":
                complicationsBean.setBf3("1");
                break;
            case "确诊有":
                complicationsBean.setBf3("2");
                if ("无症状".equals(retinaSymptomText)) {
                    complicationsBean.setBf33("1");
                } else {
                    complicationsBean.setBf33("2");
                }
                break;
            case "未诊断":
                complicationsBean.setBf3("3");
                switch (retinaSymptomText) {
                    case "仅有微动脉瘤":
                        complicationsBean.setBf32("1");
                        break;
                    case "存在微动脉瘤合并轻度微血管异常(串珠样改变,出血)":
                        complicationsBean.setBf32("2");
                        break;
                    case "存在较重微血管异常(串珠样改变，出血)":
                        complicationsBean.setBf32("3");
                        break;
                    case "出现一种或多种改变:①新生血管形成;②玻璃体积血;③视网膜前出血":
                        complicationsBean.setBf32("4");
                        break;
                }
                break;
        }
        complicationsBean.setBf3time(getSecondFromString(retinaTimeText));
        String nerveConditionText = tvNerveCondition.getText().toString().trim();
        String nerveTimeText = tvNerveTime.getText().toString().trim();
        switch (nerveConditionText) {
            case "确诊无":
                complicationsBean.setBf4("1");
                break;
            case "确诊有":
                complicationsBean.setBf4("2");
                break;
            case "未诊断":
                complicationsBean.setBf4("3");
                ////症状多选
                for (int i = 0; i < selectDatasFirst.size(); i++) {
                    switch (selectDatasFirst.get(i)) {
                        case "0":
                            complicationsBean.setBf41("1");
                            break;
                        case "1":
                            complicationsBean.setBf42("1");
                            break;
                        case "2":
                            complicationsBean.setBf43("1");
                            break;
                        case "3":
                            complicationsBean.setBf44("1");
                            break;
                        case "4":
                            complicationsBean.setBf45("1");
                            break;
                    }
                }
                break;
        }
        complicationsBean.setBf4time(getSecondFromString(nerveTimeText));
        String legsConditionText = tvLegsCondition.getText().toString().trim();
        String legsTime = tvLegsTime.getText().toString().trim();
        //症状多选
        switch (legsConditionText) {
            case "确诊无":
                complicationsBean.setBf9("1");
                break;
            case "确诊有":
                complicationsBean.setBf9("2");
                for (int i = 0; i < selectDatasSecond.size(); i++) {
                    switch (selectDatasSecond.get(i)) {
                        case "0":
                            complicationsBean.setBf95("1");
                            break;
                        case "1":
                            complicationsBean.setBf96("1");
                            break;
                    }
                }
                break;
            case "未诊断":
                complicationsBean.setBf9("3");
                ////症状多选
                for (int i = 0; i < selectDatasSecond.size(); i++) {
                    switch (selectDatasSecond.get(i)) {
                        case "0":
                            complicationsBean.setBf91("1");
                            break;
                        case "1":
                            complicationsBean.setBf92("1");
                            break;
                        case "2":
                            complicationsBean.setBf93("1");
                            break;
                        case "3":
                            complicationsBean.setBf94("1");
                            break;
                    }
                }
                break;
        }
        complicationsBean.setBf9time(getSecondFromString(legsTime));
        //吸烟
        String smoke = tvSmokeHistory.getText().toString().trim();
        switch (smoke) {
            case "是":
                complicationsBean.setJbxiyan("1");
                break;
            case "否":
                complicationsBean.setJbxiyan("2");
                break;
        }
        //五个Rc
        HashMap<Integer, String> selectMapCondition = adapter.selectMapCondition;
        HashMap<Integer, String> selectMapTime = adapter.selectMapTime;
        for (Map.Entry<Integer, String> entrySelect : selectMapCondition.entrySet()) {
            switch (entrySelect.getKey()) {
                case 0://高血压
                    switch (entrySelect.getValue()) {
                        case "确诊无":
                            complicationsBean.setJw1("1");
                            break;
                        case "确诊有":
                            complicationsBean.setJw1("2");
                            break;
                        case "未诊断":
                            complicationsBean.setJw1("3");
                            break;
                    }
                    break;
                case 1://高血脂
                    switch (entrySelect.getValue()) {
                        case "确诊无":
                            complicationsBean.setJw2("1");
                            break;
                        case "确诊有":
                            complicationsBean.setJw2("2");
                            break;
                        case "未诊断":
                            complicationsBean.setJw2("3");
                            break;
                    }
                    break;
                case 2://冠心病
                    switch (entrySelect.getValue()) {
                        case "确诊无":
                            complicationsBean.setJw3("1");
                            break;
                        case "确诊有":
                            complicationsBean.setJw3("2");
                            break;
                        case "未诊断":
                            complicationsBean.setJw3("3");
                            break;
                    }
                    break;
                case 3://脑血管病
                    switch (entrySelect.getValue()) {
                        case "确诊无":
                            complicationsBean.setJw4("1");
                            break;
                        case "确诊有":
                            complicationsBean.setJw4("2");
                            break;
                        case "未诊断":
                            complicationsBean.setJw4("3");
                            break;
                    }
                    break;
            }
        }
        String bloodType = tvBloodType.getText().toString().trim();
        switch (bloodType) {
            case "1型糖尿病":
                complicationsBean.setJbtanglei("1");
                break;
            case "2型糖尿病":
                complicationsBean.setJbtanglei("2");
                break;
            case "妊娠糖尿病":
                complicationsBean.setJbtanglei("3");
                break;
            case "其他类型":
                complicationsBean.setJbtanglei("4");
                break;
        }
        for (Map.Entry<Integer, String> entrySelect : selectMapTime.entrySet()) {
            switch (entrySelect.getKey()) {
                case 0://高血压
                    complicationsBean.setJw1time(getSecondFromString(entrySelect.getValue()));
                    break;
                case 1://高血脂
                    complicationsBean.setJw2time(getSecondFromString(entrySelect.getValue()));
                    break;
                case 2://冠心病
                    complicationsBean.setJw3time(getSecondFromString(entrySelect.getValue()));
                    break;
                case 3://脑血管病
                    complicationsBean.setJw4time(getSecondFromString(entrySelect.getValue()));
                    break;
            }
        }
        String bloodTime = tvBloodTime.getText().toString().trim();
        complicationsBean.setJbquetime(getSecondFromString(bloodTime));
        nonDrugResultBean.setEducation(educationState);
        nonDrugResultBean.setComplications(complicationsBean);
        String jsonResult = JSON.toJSONString(nonDrugResultBean);
        XyUrl.okPostJson(XyUrl.ADD_PROFESSION, jsonResult, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                sendHandlerMessage(ADD_SUCCESS);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }

    private void showOptionThreePicker(String type) {
        String[] diabetesMellitusCondition = getResources().getStringArray(R.array.data_diabetes_mellitus_condition);
        PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String text) {
                switch (type) {
                    case "-1":
                        tvSugarCondition.setText(text);
                        if ("确诊无".equals(text)) {
                            rlSugarSymptom.setVisibility(View.GONE);
                        } else {
                            rlSugarSymptom.setVisibility(View.VISIBLE);
                            tvSugarSymptom.setText("");
                        }
                        break;
                    case "0":
                        tvRetinaCondition.setText(text);
                        if ("确诊无".equals(text)) {
                            rlRetinaSymptom.setVisibility(View.GONE);
                        } else {
                            rlRetinaSymptom.setVisibility(View.VISIBLE);
                            tvRetinaSymptom.setText("");
                        }
                        break;
                    case "1":
                        tvNerveCondition.setText(text);
                        if ("未诊断".equals(text)) {
                            rvNerveCheck.setVisibility(View.VISIBLE);
                        } else {
                            rvNerveCheck.setVisibility(View.GONE);
                        }
                        break;
                    case "2":
                        tvLegsCondition.setText(text);
                        switch (text) {
                            case "确诊无":
                                rvLegsCheck.setVisibility(View.GONE);
                                break;
                            case "确诊有":
                                rvLegsCheck.setVisibility(View.VISIBLE);
                                initRvLegs("0");
                                break;
                            case "未诊断":
                                rvLegsCheck.setVisibility(View.VISIBLE);
                                initRvLegs("1");
                                break;
                        }
                        break;
                }

            }
        }, Arrays.asList(diabetesMellitusCondition));
    }

    private void showTimePicker(String type) {
        PickerUtils.showTime(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                switch (type) {
                    case "-1":
                        tvSugarTime.setText(content);
                        break;
                    case "0":
                        tvRetinaTime.setText(content);
                        break;
                    case "1":
                        tvNerveTime.setText(content);
                        break;
                    case "2":
                        tvLegsTime.setText(content);
                        break;
                    case "3":
                        tvBloodTime.setText(content);
                        break;
                }
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case ADD_SUCCESS:
                DialogUtils.getInstance().showDialog(getPageContext(), "温馨提示", "您的信息已提交,请等待医生为你生成报告!", false, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        ActivityUtils.finishToActivity(MainActivity.class, false);
                    }
                });
                break;
        }
    }


    /**
     * 根据当前时间字符串 获取时间戳秒数
     *
     * @param time
     * @return
     */
    private String getSecondFromString(String time) {
        long lms = TimeUtils.string2Millis(time, TimeFormatUtils.getDefaultFormat());
        long ls = lms / 1000;
        return ls + "";
    }
}
