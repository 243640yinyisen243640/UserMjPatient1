package com.vice.bloodpressure.ui.activity.nondrug;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.NonDrugSportAdapter;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.nondrug.FoodFirstBean;
import com.vice.bloodpressure.bean.nondrug.NonDrugResultBean;
import com.vice.bloodpressure.bean.nondrug.SportBean;
import com.vice.bloodpressure.imp.OnItemClickListener;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NonDrug_03_CheckPrescriptionActivity extends BaseHandlerActivity {
    private static final String TAG = "CheckPrescriptionActivity";
    private static final int ADD_SUCCESS = 10010;
    @BindView(R.id.rv_check)
    RecyclerView rvCheck;
    //下一步
    @BindView(R.id.bt_next)
    Button btNext;

    private NonDrugSportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("监测处方");
        setBottomBtNextText();
        initRv();
    }

    private void setBottomBtNextText() {
        //String sportState = SPStaticUtils.getString("sportState");
        //String checkState = SPStaticUtils.getString("checkState");
        String complicationState = SPStaticUtils.getString("complicationState");
        if ("1".equals(complicationState)) {
            btNext.setText("下一步");
            //Intent intent = new Intent(getPageContext(), NonDrug_04_05_EducationAndComplicationPrescriptionActivity.class);
            //startActivity(intent);
        } else {
            //toDoSubmit();
            btNext.setText("完成填写");
        }
    }

    private void initRv() {
        //RvUtils.setRvForScrollView(rvCheck);
        String[] listString = Utils.getApp().getResources().getStringArray(R.array.check_prescription);
        List<String> list = Arrays.asList(listString);
        adapter = new NonDrugSportAdapter(list);

        rvCheck.setLayoutManager(new LinearLayoutManager(this));
        rvCheck.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelection(position);
                SPStaticUtils.put("CheckPrescription", position + 1 + "");
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_check_prescription, contentLayout, false);
        return layout;
    }

    @OnClick(R.id.bt_next)
    public void onViewClicked() {
        String complicationState = SPStaticUtils.getString("complicationState");
        if ("1".equals(complicationState)) {
            Intent intent = new Intent(getPageContext(), NonDrug_04_05_EducationAndComplicationPrescriptionActivity.class);
            startActivity(intent);
        } else {
            toDoSubmit();
        }
    }

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
                String bf10 = sportBean.getBf10();
                sportsBean.setBf10(bf10);
                nonDrugResultBean.setSports(sportsBean);
            }
        }
        //监测处方
        String checkPrescription = SPStaticUtils.getString("CheckPrescription");
        nonDrugResultBean.setMonitor(checkPrescription);
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
