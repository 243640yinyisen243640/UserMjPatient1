package com.vice.bloodpressure.ui.fragment.main;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.MallNativeGoodClassifyListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 原生商城
 * 作者: LYD
 * 创建日期: 2020/5/29 16:30
 */
public class MallNativeFragment extends BaseFragment {
    private static final int GET_GOOD_CLASSIFY = 10010;
    @BindView(R.id.tbl_list)
    TabLayout tblMain;
    @BindView(R.id.vp_list)
    ViewPager vpMain;
    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] listTitle = {};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_native;
    }

    @Override
    protected void init(View rootView) {
        FloatingView.get().remove();
        getGoodClassify();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
    }


    /**
     * 获取商城一级分类
     */
    private void getGoodClassify() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.MALL_GOOD_CLASSIFY, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List list = JSONObject.parseArray(value, MallNativeGoodClassifyListBean.class);
                Message msg = getHandlerMessage();
                msg.obj = list;
                msg.what = GET_GOOD_CLASSIFY;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_GOOD_CLASSIFY:
                List<MallNativeGoodClassifyListBean> listGoods = (List<MallNativeGoodClassifyListBean>) msg.obj;
                if (listGoods != null && listGoods.size() > 0) {
                    setVp(listGoods);
                }
                break;
        }
    }

    private void setVp(List<MallNativeGoodClassifyListBean> listGoods) {
        if (!isAdded()) return;
        List<String> listFoodLei = new ArrayList<>();
        //设置
        for (int i = 0; i < listGoods.size(); i++) {
            //一级类名
            MallNativeGoodClassifyListBean data = listGoods.get(i);
            String foodLei = data.getCname();
            int id = data.getId();
            listFoodLei.add(foodLei);
            //设置Fragment
            MallNativeChildFragment childFragment = new MallNativeChildFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            childFragment.setArguments(bundle);
            listFragment.add(childFragment);
        }
        listTitle = listFoodLei.toArray(new String[listFoodLei.size()]);
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        vpMain.setOffscreenPageLimit(listFragment.size() - 1);
        vpMain.setAdapter(adapter);
        tblMain.setupWithViewPager(vpMain);
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
