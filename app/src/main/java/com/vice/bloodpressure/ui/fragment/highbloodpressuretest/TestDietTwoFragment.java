package com.vice.bloodpressure.ui.fragment.highbloodpressuretest;

import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.FragmentUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.FoodRiceAdapterSecond;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.highbloodpressuretest.TestOfDietBean;
import com.vice.bloodpressure.utils.SPUtils;
import com.vice.bloodpressure.view.MyListView;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TestDietTwoFragment extends BaseFragment {
    private static final String TAG = "TestDietTwoFragment";
    @BindView(R.id.lv_rice)
    MyListView lvRice;
    @BindView(R.id.rl_rice_add)
    RelativeLayout rlRiceAdd;
    @BindView(R.id.lv_vegetables)
    MyListView lvVegetables;
    @BindView(R.id.rl_vegetables_add)
    RelativeLayout rlVegetablesAdd;
    @BindView(R.id.lv_fruit)
    MyListView lvFruit;
    @BindView(R.id.rl_fruit_add)
    RelativeLayout rlFruitAdd;
    @BindView(R.id.lv_beans)
    MyListView lvBeans;
    @BindView(R.id.rl_beans_add)
    RelativeLayout rlBeansAdd;
    @BindView(R.id.lv_meat_egg)
    MyListView lvMeatEgg;
    @BindView(R.id.rl_meat_egg_add)
    RelativeLayout rlMeatEggAdd;
    @BindView(R.id.lv_milk)
    MyListView lvMilk;
    @BindView(R.id.rl_milk_add)
    RelativeLayout rlMilkAdd;
    @BindView(R.id.lv_oil)
    MyListView lvOil;
    @BindView(R.id.lv_nut)
    MyListView lvNut;
    @BindView(R.id.rl_nut_add)
    RelativeLayout rlNutAdd;
    @BindView(R.id.lv_wine)
    MyListView lvWine;
    @BindView(R.id.bt_next_question)
    ColorButton btNextQuestion;


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
    protected int getLayoutId() {
        return R.layout.fragment_test_diet_two;
    }

    @Override
    protected void init(View rootView) {
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
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.rl_rice_add, R.id.rl_vegetables_add, R.id.rl_fruit_add, R.id.rl_beans_add, R.id.rl_meat_egg_add, R.id.rl_milk_add, R.id.rl_nut_add, R.id.bt_next_question})
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
            case R.id.bt_next_question:
                toJumpNextQuestion();
                break;
        }
    }

    private void toJumpNextQuestion() {
        TestOfDietBean dietBean = (TestOfDietBean) SPUtils.getBean("hbpDietBean");
        TestOfDietBean.FoodsBean foodsBean = new TestOfDietBean.FoodsBean();
        //开始第二部分
        //谷署类
        HashMap<Integer, String> selectMap = riceAdapter.selectMap;
        HashMap<Integer, String> saveMap = riceAdapter.saveMap;
        TestOfDietBean.FoodsBean.VapotatoBean vapotatoBean = new TestOfDietBean.FoodsBean.VapotatoBean();
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
        TestOfDietBean.FoodsBean.FruitsBean fruitBean = new TestOfDietBean.FoodsBean.FruitsBean();
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
        TestOfDietBean.FoodsBean.SoyproductsBean soyproductsBean = new TestOfDietBean.FoodsBean.SoyproductsBean();
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
        TestOfDietBean.FoodsBean.MeateggsBean meateggsBean = new TestOfDietBean.FoodsBean.MeateggsBean();
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
        TestOfDietBean.FoodsBean.MilksBean milksBean = new TestOfDietBean.FoodsBean.MilksBean();
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
        TestOfDietBean.FoodsBean.NutsBean nutsBean = new TestOfDietBean.FoodsBean.NutsBean();
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
        TestOfDietBean.FoodsBean.WinesBean winesBean = new TestOfDietBean.FoodsBean.WinesBean();
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
        dietBean.setFoods(foodsBean);
        //保存饮食数据
        SPUtils.putBean("hbpDietBean", dietBean);
        TestSportFragment sportFragment = new TestSportFragment();
        FragmentUtils.replace(getParentFragmentManager(), sportFragment, R.id.ll_fragment_root, false);
    }
}
