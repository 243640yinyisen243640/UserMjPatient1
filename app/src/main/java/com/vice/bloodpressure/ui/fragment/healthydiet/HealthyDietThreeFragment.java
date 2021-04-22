package com.vice.bloodpressure.ui.fragment.healthydiet;


import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.FoodsCategoryBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.edittext.EditTextUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  健康饮食之菜谱库
 * 作者: LYD
 * 创建日期: 2019/12/18 9:33
 */
public class HealthyDietThreeFragment extends BaseFragment {
    private static final int GET_FIRST_CLASSIFY = 10086;
    private static final String TAG = "HealthyDietThreeFragment";
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    ColorTextView tvSearch;
    @BindView(R.id.tbl_list)
    TabLayout tblList;
    @BindView(R.id.vp_list)
    ViewPager vpList;

    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] listTitle = {};
    private List<Integer> listId = new ArrayList<>();
    //当前
    private int currentPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_healthy_diet_two;
    }

    @Override
    protected void init(View rootView) {
        getFoodFirstClassify();
        setSearch();
    }

    private void setSearch() {
        EditTextUtils.setOnEditorActionListener(etSearch, new EditTextUtils.OnActionSearchListener() {
            @Override
            public void onActionSearch() {
                toSearch();
            }
        });
    }

    /**
     * 获取一级分类
     */
    private void getFoodFirstClassify() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_GREENS_CLASSIFY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<FoodsCategoryBean> foodList = JSONObject.parseArray(value, FoodsCategoryBean.class);
                Message message = getHandlerMessage();
                message.what = GET_FIRST_CLASSIFY;
                message.obj = foodList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                //Message message = getHandlerMessage();
                //message.what = GET_ERROR;
                //sendHandlerMessage(message);
            }
        });

    }


    private void initFragment() {
        //添加Fragment
        for (int i = 0; i < listTitle.length; i++) {
            HealthyDietChildThreeFragment childFragment = new HealthyDietChildThreeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", listId.get(i));
            bundle.putString("position", i + "");
            bundle.putString("type", "three");
            childFragment.setArguments(bundle);
            listFragment.add(childFragment);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        vpList.setOffscreenPageLimit(listFragment.size() - 1);
        vpList.setAdapter(adapter);
        tblList.setupWithViewPager(vpList);
        //TabLayout切换监听
        tblList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
                EventBusUtils.post(new EventMessage(ConstantParam.GREEN_REFRESH));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //ViewPager切换监听
        vpList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        toSearch();
    }


    private void toSearch() {
        String keyWord = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showShort("请输入搜索内容");
            return;
        }
        EventBusUtils.post(new EventMessage(ConstantParam.GREEN_SEARCH, keyWord, currentPosition + ""));
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_FIRST_CLASSIFY:
                List<FoodsCategoryBean> list = (List<FoodsCategoryBean>) msg.obj;
                List<String> listFoodLei = new ArrayList<>();
                String[] strings = new String[listFoodLei.size()];
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        FoodsCategoryBean data = list.get(i);
                        //一级类名
                        String foodLei = data.getClassify_name();
                        listFoodLei.add(foodLei);
                        //一级Id
                        int id = data.getId();
                        listId.add(id);
                    }
                }
                listTitle = listFoodLei.toArray(strings);
                initFragment();
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }
    }
}
