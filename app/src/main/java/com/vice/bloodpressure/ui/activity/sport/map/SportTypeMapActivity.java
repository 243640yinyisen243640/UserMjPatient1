package com.vice.bloodpressure.ui.activity.sport.map;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.sport.SportTypeMapLeftFragment;
import com.vice.bloodpressure.ui.fragment.sport.SportTypeMapRightFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 运动类型之地图
 * 作者: LYD
 * 创建日期: 2020/9/27 14:16
 */
public class SportTypeMapActivity extends BaseHandlerActivity {
    @BindView(R.id.bt_back_new)
    Button btBackNew;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private List<Fragment> fragmentList;
    private String[] typeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        initTabLayout();
    }

    private void initTabLayout() {
        fragmentList = new ArrayList<>();
        typeArray = getResources().getStringArray(R.array.sport_type_map_title_bike);
        SportTypeMapLeftFragment leftFragment = new SportTypeMapLeftFragment();
        SportTypeMapRightFragment rightFragment = new SportTypeMapRightFragment();
        Bundle bundle = new Bundle();
        leftFragment.setArguments(bundle);
        rightFragment.setArguments(bundle);
        fragmentList.add(leftFragment);
        fragmentList.add(rightFragment);
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tlTab.setupWithViewPager(vpContent);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_sport_type_map, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.bt_back_new)
    public void onViewClicked() {
        finish();
    }


    private class TabAdapter extends FragmentPagerAdapter {

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
    }
}