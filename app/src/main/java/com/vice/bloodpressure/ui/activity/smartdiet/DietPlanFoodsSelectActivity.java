package com.vice.bloodpressure.ui.activity.smartdiet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.DietPlanFoodChildBean;
import com.vice.bloodpressure.bean.DietPlanFoodListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.enumuse.DietPlanTwoStepsTitle;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.fragment.healthydiet.DietPlanSelectFoodsFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 食物选择页面
 * 作者: LYD
 * 创建日期: 2020/3/18 9:26
 */
public class DietPlanFoodsSelectActivity extends BaseHandlerEventBusActivity {
    private static final String TAG = "DietPlanSelectFoodsActivity";
    private static final int SEARCH_FOOD = 10010;
    private static final int ADD_SUCCESS = 10014;
    private static final int ADD_FAILED = 10015;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tbl_list)
    TabLayout tlTab;
    @BindView(R.id.vp_list)
    ViewPager vpContent;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] typeArray;
    private HashMap<Integer, DietPlanFoodChildBean> allMap;
    private List<List<DietPlanFoodChildBean>> listAll = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置标题
        setPageTitle();
        //设置右上角保存
        setMore();
        //获取菜谱分类列表
        getMyDietPlanList();
        //KeyboardUtils.hideSoftInput(DietPlanFoodsSelectActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //KeyboardUtils.hideSoftInput(DietPlanFoodsSelectActivity.this);
    }

    /**
     * 设置标题
     */
    private void setPageTitle() {
        int position = getIntent().getIntExtra("position", 0);
        String title = DietPlanTwoStepsTitle.getNameFromPosition(position);
        setTitle(title);
    }

    /**
     * 设置保存
     */
    private void setMore() {
        allMap = new HashMap<>();
        getTvSave().setText("保存");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSaveSelect();
            }
        });
    }


    /**
     * 保存选中
     */
    private void toSaveSelect() {
        if (allMap != null && allMap.size() > 0) {
            toDoSaveSelect();
        } else {
            ToastUtils.showShort("请选择");
        }
    }


    /**
     * 保存到后台
     */
    private void toDoSaveSelect() {
        List<DietPlanFoodChildBean> selectList = new ArrayList<>(allMap.values());
        StringBuilder str = new StringBuilder();
        if (selectList.size() > 0) {
            for (int i = 0; i < selectList.size(); i++) {
                int id = selectList.get(i).getId();
                str.append(id);
                str.append(",");
            }
            str.deleteCharAt(str.length() - 1);
        }
        String day = getIntent().getStringExtra("day");
        String id = getIntent().getStringExtra("id");
        int position = getIntent().getIntExtra("position", 0);
        HashMap map = new HashMap<>();
        //map.put("day", day);
        //map.put("id", id);
        map.put("type", position);
        map.put("ids", str);
        XyUrl.okPostSave(XyUrl.GREEN_ID_SAVE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //ToastUtils.showShort("保存成功");
                getIntent().putExtra("allMap", allMap);
                setResult(RESULT_OK, getIntent());
                finish();
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                //Message message = getHandlerMessage();
                //message.what = ADD_FAILED;
                //sendHandlerMessage(message);
                ToastUtils.showShort(errorMsg);
            }
        });
    }


    /**
     * 获取菜谱分类列表
     */
    private void getMyDietPlanList() {
        int position = getIntent().getIntExtra("position", 0);
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getMyDietPlanDetailList(token, position + "")
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<DietPlanFoodListBean>>() {
                    @Override
                    protected void onSuccess(BaseData<DietPlanFoodListBean> dietPlanDetailBeanBaseData) {
                        DietPlanFoodListBean data = dietPlanDetailBeanBaseData.getData();
                        initFragment(data);
                    }

                    @Override
                    protected void onError(String errorMsg) {

                    }
                });
    }


    /**
     * 展示分类页面
     *
     * @param data
     */
    private void initFragment(DietPlanFoodListBean data) {
        List<String> listStr = data.getStyle();
        typeArray = listStr.toArray(new String[0]);
        List<DietPlanFoodChildBean> mainFood = data.getMainfood();
        List<DietPlanFoodChildBean> meat = data.getMeat();
        List<DietPlanFoodChildBean> vegetables = data.getVegetables();
        List<DietPlanFoodChildBean> drink = data.getDrink();
        List<DietPlanFoodChildBean> others = data.getOthers();
        if (mainFood != null) {
            listAll.add(mainFood);
        }
        if (meat != null) {
            listAll.add(meat);
        }
        if (vegetables != null) {
            listAll.add(vegetables);
        }
        if (drink != null) {
            listAll.add(drink);
        }
        if (others != null) {
            listAll.add(others);
        }
        for (int i = 0; i < typeArray.length; i++) {
            List<DietPlanFoodChildBean> list = listAll.get(i);
            DietPlanSelectFoodsFragment fragment = new DietPlanSelectFoodsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            bundle.putSerializable("list", (Serializable) list);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        //设置TabLayout的模式
        tlTab.setTabMode(TabLayout.MODE_FIXED);
        //ViewPager的适配器
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        //关联ViewPager和TabLayout
        tlTab.setupWithViewPager(vpContent);
        //懒加载改为预加载
        vpContent.setOffscreenPageLimit(listAll.size() - 1);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_diet_plan_select_foods, contentLayout, false);
        return view;
    }


    @OnClick({R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                int position = getIntent().getIntExtra("position", 0);
                Intent intent = new Intent(this, DietPlanFoodsSearchActivity.class);
                intent.putExtra("type", position);
                startActivityForResult(intent, SEARCH_FOOD);
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.DIET_PLAN_SELECT_FRAGMENT_TO_ACTIVITY:
                HashMap<Integer, DietPlanFoodChildBean> selectMap = (HashMap<Integer, DietPlanFoodChildBean>) event.getData();
                allMap.putAll(selectMap);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SEARCH_FOOD:
                    DietPlanFoodChildBean searchBean = (DietPlanFoodChildBean) data.getSerializableExtra("searchBean");
                    //分类Id
                    int cid = searchBean.getCid();
                    //菜id
                    int id = searchBean.getId();

                    break;
            }
        }
    }


    @Override
    public void processHandlerMsg(Message msg) {

    }

    /**
     * 1.将FragmentPagerAdapter替换成FragmentStatePagerAdapter
     */
    private class TabAdapter extends FragmentStatePagerAdapter {

        private TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        //设置每个Tab的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return typeArray[position];
        }

        //2.清除之前的Fragment
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
