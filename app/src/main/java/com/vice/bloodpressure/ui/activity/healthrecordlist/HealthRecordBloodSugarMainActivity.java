package com.vice.bloodpressure.ui.activity.healthrecordlist;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.google.android.material.tabs.TabLayout;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.SevenAndThirtyBloodSugarListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.ui.fragment.other.SevenAndThirtyBloodSugarListFragment;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.rxhttputils.CustomDataObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 血糖记录
 * 作者: LYD
 * 创建日期: 2019/6/5 15:34
 */
public class HealthRecordBloodSugarMainActivity extends BaseActivity {
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private List<Fragment> fragmentList;
    private String[] typeArray;

    /**
     * 获取
     */
    private void getSevenAndThirty() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String userid = user.getUserid();
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .getSevenAndThirtyBloodSugar(token, userid, ConstantParam.SERVER_VERSION)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CustomDataObserver<SevenAndThirtyBloodSugarListBean>() {
                    @Override
                    protected void onSuccess(SevenAndThirtyBloodSugarListBean bean) {
                        initFragment(bean);
                    }
                });
    }

    private void initFragment(SevenAndThirtyBloodSugarListBean bean) {
        fragmentList = new ArrayList<>();
        typeArray = getResources().getStringArray(R.array.health_record_title);
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String userid = user.getUserid();
        if (getIntent().getIntExtra("user_id", -1) != -1) {
            userid = String.valueOf(getIntent().getIntExtra("user_id", -1));
        }
        for (int i = 0; i < 2; i++) {
            SevenAndThirtyBloodSugarListFragment fragment = new SevenAndThirtyBloodSugarListFragment();
            Bundle bundle = new Bundle();
            if (getIntent().getIntExtra("user_id", -1) != -1) {
                bundle.putBoolean("family", true);
            }
            bundle.putString("userid", userid);
            bundle.putString("type", i + "");
            bundle.putSerializable("bean", bean);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        vpContent.setAdapter(new TabAdapter(getSupportFragmentManager()));
        vpContent.setOffscreenPageLimit(1);
        tlTab.setupWithViewPager(vpContent);
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_health_record_blood_sugar_main, contentLayout, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血糖记录");
        getSevenAndThirty();
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
