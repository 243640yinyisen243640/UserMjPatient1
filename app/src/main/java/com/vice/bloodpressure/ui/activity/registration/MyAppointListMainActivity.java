package com.vice.bloodpressure.ui.activity.registration;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.registration.MyAppointmentListFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述:   我的预约
 * 作者: LYD
 * 创建日期: 2019/10/28 9:29
 */
public class MyAppointListMainActivity extends BaseHandlerActivity {
    @BindView(R.id.tl_main)
    SegmentTabLayout tlMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] titles = {"最新预约", "全部预约"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的预约");
        initTlAndVp();
    }

    /**
     * 初始化TabLayout和ViewPager
     */
    private void initTlAndVp() {
        for (int i = 0; i < titles.length; i++) {
            MyAppointmentListFragment fragment = new MyAppointmentListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            fragment.setArguments(bundle);
            listFragment.add(fragment);
        }
        //设置Adapter
        vpMain.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //设置标题
        tlMain.setTabData(titles);
        //设置切换
        tlMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpMain.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //滑动切换
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tlMain.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //设置默认
        vpMain.setCurrentItem(0);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_my_appoint_list, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

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
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }
    }
}
