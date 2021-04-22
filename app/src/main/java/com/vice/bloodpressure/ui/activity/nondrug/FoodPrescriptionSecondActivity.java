package com.vice.bloodpressure.ui.activity.nondrug;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.google.gson.Gson;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.FoodRiceAdapterSecond;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.nondrug.FoodFirstBean;
import com.vice.bloodpressure.bean.nondrug.NonDrugResultBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.view.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 饮食处方 第二步 添加饮食
 * 作者: LYD
 * 创建日期: 2019/5/6 15:22
 */
public class FoodPrescriptionSecondActivity extends BaseHandlerActivity {
    private static final String TAG = "FoodPrescriptionSecondActivity";
    private static final int ADD_SUCCESS = 10010;
    @BindView(R.id.lv_rice)
    MyListView lvRice;

    @BindView(R.id.lv_vegetables)
    MyListView lvVegetables;

    @BindView(R.id.lv_fruit)
    MyListView lvFruit;

    @BindView(R.id.lv_beans)
    MyListView lvBeans;

    @BindView(R.id.lv_meat_egg)
    MyListView lvMeatEgg;

    @BindView(R.id.lv_milk)
    MyListView lvMilk;

    @BindView(R.id.lv_oil)
    MyListView lvOil;

    @BindView(R.id.lv_nut)
    MyListView lvNut;

    @BindView(R.id.lv_wine)
    MyListView lvWine;
    //下一步
    @BindView(R.id.bt_next)
    Button btNext;
    //谷署类
    private FoodRiceAdapterSecond riceAdapter;
    private List<String> riceList;
    //蔬菜类
    private FoodRiceAdapterSecond vegetablesAdapter;
    //水果类
    private FoodRiceAdapterSecond fruitAdapter;
    private List<String> fruitList;
    //豆制品
    private FoodRiceAdapterSecond beanAdapter;
    private List<String> beanList;
    //肉蛋
    private FoodRiceAdapterSecond meatAndEggAdapter;
    private List<String> meatAndEggList;
    //奶制品
    private FoodRiceAdapterSecond milkAdapter;
    private List<String> milkList;
    //油脂
    private FoodRiceAdapterSecond oilAdapter;
    //坚果
    private FoodRiceAdapterSecond nutAdapter;
    private List<String> nutList;
    //酒
    private FoodRiceAdapterSecond wineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("填写资料");
        setBottomBtNextText();
        setLvRiceData();
        setLvVegetablesData();
        setLvFruit();
        setLvBean();
        setLvMeatAndEgg();
        setLvMilk();
        setLvOil();
        setLvNut();
        setLvWine();
    }

    private void setBottomBtNextText() {
        String sportState = SPStaticUtils.getString("sportState");
        String checkState = SPStaticUtils.getString("checkState");
        String complicationState = SPStaticUtils.getString("complicationState");
        if ("1".equals(sportState)) {
            btNext.setText("下一步");
            //Intent intent = new Intent(getPageContext(), NonDrug_02_SportPrescriptionActivity.class);
            //intent.putExtra("type", getIntent().getStringExtra("type"));
            //startActivity(intent);
        } else if ("1".equals(checkState)) {
            btNext.setText("下一步");
            //Intent intent = new Intent(getPageContext(), NonDrug_03_CheckPrescriptionActivity.class);
            //startActivity(intent);
        } else if ("1".equals(complicationState)) {
            btNext.setText("下一步");
            //Intent intent = new Intent(getPageContext(), NonDrug_04_05_EducationAndComplicationPrescriptionActivity.class);
            //startActivity(intent);
        } else {
            //toDoSubmit();
            btNext.setText("完成填写");
        }
    }

    private void setLvWine() {
        List<String> wineList = new ArrayList<>();
        wineList.add("0");
        wineAdapter = new FoodRiceAdapterSecond(getPageContext(), wineList, "8");
        lvWine.setAdapter(wineAdapter);
    }

    /**
     * 坚果
     */
    private void setLvNut() {
        nutList = new ArrayList<>();
        nutList.add("0");
        nutAdapter = new FoodRiceAdapterSecond(getPageContext(), nutList, "7");
        lvNut.setAdapter(nutAdapter);
    }

    /**
     * 油脂
     */
    private void setLvOil() {
        List<String> oilList = new ArrayList<>();
        oilList.add("0");
        oilAdapter = new FoodRiceAdapterSecond(getPageContext(), oilList, "6");
        lvOil.setAdapter(oilAdapter);
    }

    /**
     * 奶
     */
    private void setLvMilk() {
        milkList = new ArrayList<>();
        milkList.add("0");
        milkAdapter = new FoodRiceAdapterSecond(getPageContext(), milkList, "5");
        lvMilk.setAdapter(milkAdapter);
    }

    /**
     * 肉蛋
     */
    private void setLvMeatAndEgg() {
        meatAndEggList = new ArrayList<>();
        meatAndEggList.add("0");
        meatAndEggAdapter = new FoodRiceAdapterSecond(getPageContext(), meatAndEggList, "4");
        lvMeatEgg.setAdapter(meatAndEggAdapter);
    }

    /**
     * 豆制品
     */
    private void setLvBean() {
        beanList = new ArrayList<>();
        beanList.add("0");
        beanAdapter = new FoodRiceAdapterSecond(getPageContext(), beanList, "3");
        lvBeans.setAdapter(beanAdapter);
    }

    /**
     * 水果
     */
    private void setLvFruit() {
        fruitList = new ArrayList<>();
        fruitList.add("0");
        fruitAdapter = new FoodRiceAdapterSecond(getPageContext(), fruitList, "2");
        lvFruit.setAdapter(fruitAdapter);
    }

    /**
     * 设置蔬菜类
     */
    private void setLvVegetablesData() {
        List<String> vegetablesList = new ArrayList<>();
        vegetablesList.add("0");
        vegetablesAdapter = new FoodRiceAdapterSecond(getPageContext(), vegetablesList, "1");
        lvVegetables.setAdapter(vegetablesAdapter);
    }

    /**
     * 设置谷署
     */
    private void setLvRiceData() {
        riceList = new ArrayList<>();
        riceList.add("0");
        riceAdapter = new FoodRiceAdapterSecond(getPageContext(), riceList, "0");
        lvRice.setAdapter(riceAdapter);
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_food_prescription_second, contentLayout, false);
        return layout;
    }

    @OnClick({R.id.rl_rice_add, R.id.rl_fruit_add, R.id.rl_beans_add, R.id.rl_meat_egg_add, R.id.rl_milk_add, R.id.rl_nut_add, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_rice_add://谷署
                riceList.add("1");
                riceAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_fruit_add://水果
                fruitList.add("1");
                fruitAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_beans_add://豆制品
                beanList.add("1");
                beanAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_meat_egg_add://肉蛋
                meatAndEggList.add("1");
                meatAndEggAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_milk_add://奶制品
                milkList.add("1");
                milkAdapter.notifyDataSetChanged();
                break;
            case R.id.rl_nut_add:
                nutList.add("1");
                nutAdapter.notifyDataSetChanged();
                break;
            case R.id.bt_next:
                String sportState = SPStaticUtils.getString("sportState");
                String checkState = SPStaticUtils.getString("checkState");
                String complicationState = SPStaticUtils.getString("complicationState");
                //谷署类
                HashMap<Integer, String> selectMap = riceAdapter.selectMap;
                HashMap<Integer, String> saveMap = riceAdapter.saveMap;
                SPStaticUtils.put("selectMap", new Gson().toJson(selectMap));
                SPStaticUtils.put("saveMap", new Gson().toJson(saveMap));
                //蔬菜类
                String vegetables = vegetablesAdapter.saveMap.get(0);
                SPStaticUtils.put("vegetable", vegetables);
                //水果
                HashMap<Integer, String> selectMapFruit = fruitAdapter.selectMap;
                HashMap<Integer, String> saveMapFruit = fruitAdapter.saveMap;
                SPStaticUtils.put("selectMapFruit", new Gson().toJson(selectMapFruit));
                SPStaticUtils.put("saveMapFruit", new Gson().toJson(saveMapFruit));
                //豆制品
                HashMap<Integer, String> selectMapBean = beanAdapter.selectMap;
                HashMap<Integer, String> saveMapBean = beanAdapter.saveMap;
                SPStaticUtils.put("selectMapBean", new Gson().toJson(selectMapBean));
                SPStaticUtils.put("saveMapBean", new Gson().toJson(saveMapBean));
                //肉蛋
                HashMap<Integer, String> selectMapMeat = meatAndEggAdapter.selectMap;
                HashMap<Integer, String> saveMapMeat = meatAndEggAdapter.saveMap;
                SPStaticUtils.put("selectMapMeat", new Gson().toJson(selectMapMeat));
                SPStaticUtils.put("saveMapMeat", new Gson().toJson(saveMapMeat));
                //奶
                HashMap<Integer, String> selectMapMilk = milkAdapter.selectMap;
                HashMap<Integer, String> saveMapMilk = milkAdapter.saveMap;
                SPStaticUtils.put("selectMapMilk", new Gson().toJson(selectMapMilk));
                SPStaticUtils.put("saveMapMilk", new Gson().toJson(saveMapMilk));
                //油脂
                String oil = oilAdapter.saveMap.get(0);
                SPStaticUtils.put("oil", oil);
                //坚果
                HashMap<Integer, String> selectMapNut = nutAdapter.selectMap;
                HashMap<Integer, String> saveMapNut = nutAdapter.saveMap;
                SPStaticUtils.put("selectMapNut", new Gson().toJson(selectMapNut));
                SPStaticUtils.put("saveMapNut", new Gson().toJson(saveMapNut));
                //酒
                HashMap<Integer, String> selectMapWine = wineAdapter.selectMap;
                HashMap<Integer, String> saveMapWine = wineAdapter.saveMap;
                HashMap<Integer, String> saveMapWineSecond = wineAdapter.saveMapSecond;
                SPStaticUtils.put("selectMapWine", new Gson().toJson(selectMapWine));
                SPStaticUtils.put("saveMapWine", new Gson().toJson(saveMapWine));
                SPStaticUtils.put("saveMapWineSecond", new Gson().toJson(saveMapWineSecond));
                if ("1".equals(sportState)) {
                    Intent intent = new Intent(getPageContext(), NonDrug_02_SportPrescriptionActivity.class);
                    intent.putExtra("type", getIntent().getStringExtra("type"));
                    startActivity(intent);
                } else if ("1".equals(checkState)) {
                    Intent intent = new Intent(getPageContext(), NonDrug_03_CheckPrescriptionActivity.class);
                    startActivity(intent);
                } else if ("1".equals(complicationState)) {
                    Intent intent = new Intent(getPageContext(), NonDrug_04_05_EducationAndComplicationPrescriptionActivity.class);
                    startActivity(intent);
                } else {
                    toDoSubmit();
                }
                break;
        }
    }

    /**
     * 提交数据
     */
    private void toDoSubmit() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        FoodFirstBean foodFirstBean = (FoodFirstBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.FOOD_FIRST);
        NonDrugResultBean nonDrugResultBean = new NonDrugResultBean();//最后需要转换的Bean
        nonDrugResultBean.setUserid(userLogin.getUserid());
        //第一部分设置
        NonDrugResultBean.DietaryBean dietaryBean = new NonDrugResultBean.DietaryBean();
        dietaryBean.setTgshengao(foodFirstBean.getHeight());
        dietaryBean.setTgtizhong(foodFirstBean.getWeight());
        dietaryBean.setAge(foodFirstBean.getAge());
        //减重新增
        dietaryBean.setWaistline(foodFirstBean.getWaistline());
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
        //第三部分 判断
        dietaryBean.setJbzhiye(foodFirstBean.getLabourIntensity());
        dietaryBean.setSssh(foodFirstBean.getKidney());
        dietaryBean.setSmoke(foodFirstBean.getSmoke());
        //第四部分
        String src = foodFirstBean.getSrc();
        dietaryBean.setSyxqjg(src);
        dietaryBean.setSyacr(foodFirstBean.getAcr());
        String gfr = foodFirstBean.getGfr();
        dietaryBean.setSyxqgfr(gfr);
        //第五部分 判断
        String diabeticNephropathy = foodFirstBean.getDiabeticNephropathy();
        dietaryBean.setNephropathy(diabeticNephropathy);


        String diabeticNephropathyPeriod = foodFirstBean.getDiabeticNephropathyPeriod();
        dietaryBean.setNephropathylei(diabeticNephropathyPeriod);

        String diabeticNephropathyTime = foodFirstBean.getDiabeticNephropathyTime();
        dietaryBean.setBf2time(diabeticNephropathyTime);

        //第六部分 判断
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
        //开始第二部分
        //谷署类
        HashMap<Integer, String> selectMap = riceAdapter.selectMap;
        HashMap<Integer, String> saveMap = riceAdapter.saveMap;
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
        for (Map.Entry<Integer, String> vegetableEntry : vegetablesAdapter.saveMap.entrySet()) {
            String vegetable = vegetableEntry.getValue();
            foodsBean.setVegetables(vegetable);
        }
        //水果
        HashMap<Integer, String> selectMapFruit = fruitAdapter.selectMap;
        HashMap<Integer, String> saveMapFruit = fruitAdapter.saveMap;
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
        HashMap<Integer, String> selectMapBean = beanAdapter.selectMap;
        HashMap<Integer, String> saveMapBean = beanAdapter.saveMap;
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
        HashMap<Integer, String> selectMapMeat = meatAndEggAdapter.selectMap;
        HashMap<Integer, String> saveMapMeat = meatAndEggAdapter.saveMap;
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
        HashMap<Integer, String> selectMapMilk = milkAdapter.selectMap;
        HashMap<Integer, String> saveMapMilk = milkAdapter.saveMap;
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
        for (Map.Entry<Integer, String> oilEntry : oilAdapter.saveMap.entrySet()) {
            String oil = oilEntry.getValue();
            foodsBean.setOils(oil);
        }
        //坚果
        HashMap<Integer, String> selectMapNut = nutAdapter.selectMap;
        HashMap<Integer, String> saveMapNut = nutAdapter.saveMap;
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
        HashMap<Integer, String> selectMapWine = wineAdapter.selectMap;
        HashMap<Integer, String> saveMapWine = wineAdapter.saveMap;
        HashMap<Integer, String> saveMapWineSecond = wineAdapter.saveMapSecond;
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
        String jsonResult = JSON.toJSONString(nonDrugResultBean);
        String postUrl = "";
        if ("weight".equals(getIntent().getStringExtra("type"))) {
            postUrl = XyUrl.ADD_LOSE_WEIGHT;
        } else {
            postUrl = XyUrl.ADD_PROFESSION;
        }
        XyUrl.okPostJson(postUrl, jsonResult, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                sendHandlerMessage(ADD_SUCCESS);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

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
}
