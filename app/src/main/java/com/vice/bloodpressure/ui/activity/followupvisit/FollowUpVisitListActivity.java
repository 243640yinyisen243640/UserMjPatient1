package com.vice.bloodpressure.ui.activity.followupvisit;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.ui.fragment.other.FollowUpVisitFragment;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:随访管理列表
 * 作者: LYD
 * 创建日期: 2019/7/19 9:49
 */
public class FollowUpVisitListActivity extends BaseHandlerActivity {
    private static final String TAG = "FollowUpVisitListActivity";
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] typeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("随访管理列表");
        initFragment();
        hideTitleBar();
    }

    private void initFragment() {
        if (getIntent().getBooleanExtra("is_family", false)) {
            typeArray = getResources().getStringArray(R.array.follow_up_visit_title_no_liver);
        } else {
            typeArray = getResources().getStringArray(R.array.follow_up_visit_title);
        }
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        for (int i = 0; i < typeArray.length; i++) {
            FollowUpVisitFragment fragment = new FollowUpVisitFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", i + 1 + "");
            if (user != null) {
                bundle.putString("userId", user.getUserid());
            }
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        //设置TabLayout的模式
        tlTab.setTabMode(TabLayout.MODE_FIXED);
        //ViewPager的适配器
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        //关联ViewPager和TabLayout
        tlTab.setupWithViewPager(vpContent);
        //ButterKnife报错处理
        vpContent.setOffscreenPageLimit(1);
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_follow_up_visit_list, contentLayout, false);
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
