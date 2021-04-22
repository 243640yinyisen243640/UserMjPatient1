package com.vice.bloodpressure.ui.fragment.healthweight;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.FragmentUtils;
import com.google.android.material.tabs.TabLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 描述: 体重记录之图表
 * 作者: LYD
 * 创建日期: 2020/5/11 10:59
 */
public class WeightChartFragment extends BaseFragment {
    @BindView(R.id.tbl_list)
    TabLayout tblList;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] listTitle = {"7次", "30次", "60次"};


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weight_chart;
    }

    @Override
    protected void init(View rootView) {
        initFragment();
    }

    private void initFragment() {
        //添加Fragment
        for (int i = 0; i < listTitle.length; i++) {
            WeightChartChildFragment childFragment = new WeightChartChildFragment();
            Bundle bundle = new Bundle();
            bundle.putString("position", i + "");
            childFragment.setArguments(bundle);
            listFragment.add(childFragment);
        }
        for (int i = 0; i < listTitle.length; i++) {
            //添加tab
            tblList.addTab(tblList.newTab().setText(listTitle[i]));
        }
        //添加第一个
        Fragment fragment = listFragment.get(0);
        FragmentUtils.replace(getChildFragmentManager(), fragment, R.id.fl_content, false);
        //TabLayout切换监听
        tblList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = listFragment.get(position);
                FragmentUtils.replace(getChildFragmentManager(), fragment, R.id.fl_content, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
