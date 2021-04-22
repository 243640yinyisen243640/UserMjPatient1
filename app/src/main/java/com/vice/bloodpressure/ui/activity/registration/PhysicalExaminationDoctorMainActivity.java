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
import com.vice.bloodpressure.bean.RegistrationDepartmentsListBean;
import com.vice.bloodpressure.ui.fragment.registration.AppointmentOfDoctorFragment;
import com.vice.bloodpressure.ui.fragment.registration.AppointmentOfTimeFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述: 体检预约之医生列表主页面
 * 作者: LYD
 * 创建日期: 2019/10/18 16:27
 */
public class PhysicalExaminationDoctorMainActivity extends BaseHandlerActivity {
    @BindView(R.id.tl_main)
    SegmentTabLayout tlMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private String[] titles = {"按医生预约", "按日期预约"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegistrationDepartmentsListBean data = (RegistrationDepartmentsListBean) getIntent().getSerializableExtra("departmentsBean");
        String depname = data.getDepname();
        int depid = data.getDepid();
        setTitle(depname);
        initTlAndVp(depid);
    }


    /**
     * 初始化TabLayout
     *
     * @param depid
     */
    private void initTlAndVp(int depid) {
        Bundle bundle = new Bundle();
        bundle.putInt("depid", depid);
        AppointmentOfDoctorFragment doctorFragment = new AppointmentOfDoctorFragment();
        AppointmentOfTimeFragment timeFragment = new AppointmentOfTimeFragment();
        doctorFragment.setArguments(bundle);
        timeFragment.setArguments(bundle);
        //添加Fragment
        listFragment.add(doctorFragment);
        listFragment.add(timeFragment);
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
        View view = getLayoutInflater().inflate(R.layout.activity_physical_examination_doctor_main, contentLayout, false);
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
