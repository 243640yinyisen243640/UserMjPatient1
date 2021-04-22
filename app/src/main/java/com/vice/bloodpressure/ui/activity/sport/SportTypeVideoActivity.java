package com.vice.bloodpressure.ui.activity.sport;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.sport.SportTypeMapRightFragment;
import com.vice.bloodpressure.ui.fragment.sport.SportTypeVideoLeftFragment;
import com.vice.bloodpressure.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 跳绳和太极
 * 作者: LYD
 * 创建日期: 2020/10/14 10:54
 */
public class SportTypeVideoActivity extends BaseHandlerActivity {
    @BindView(R.id.bt_back_new)
    Button btBackNew;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    NoScrollViewPager vpContent;
    private List<Fragment> fragmentList;
    private String[] typeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        initTabLayout();
    }

    private void initTabLayout() {
        int sportType = getIntent().getExtras().getInt("sportType");
        if (5 == sportType) {
            typeArray = getResources().getStringArray(R.array.sport_type_video_title_skip);
        } else {
            typeArray = getResources().getStringArray(R.array.sport_type_video_title_taiji);
        }
        fragmentList = new ArrayList<>();
        SportTypeVideoLeftFragment leftFragment = new SportTypeVideoLeftFragment();
        SportTypeMapRightFragment rightFragment = new SportTypeMapRightFragment();
      
        leftFragment.setArguments(getIntent().getExtras());
        rightFragment.setArguments(getIntent().getExtras());
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