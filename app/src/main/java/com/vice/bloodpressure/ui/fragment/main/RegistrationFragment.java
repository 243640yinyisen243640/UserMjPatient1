package com.vice.bloodpressure.ui.fragment.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.ui.activity.registration.RegistrationMainAppointmentActivity;
import com.vice.bloodpressure.ui.activity.registration.RegistrationMainTreatActivity;

import butterknife.OnClick;


/**
 * 描述:  挂号Fragment
 * 作者: LYD
 * 创建日期: 2019/10/18 10:17
 */
public class RegistrationFragment extends BaseFragment implements SimpleImmersionOwner {

    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_registration;
    }

    @Override
    protected void init(View rootView) {
        FloatingView.get().remove();
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    //SimpleImmersionOwner接口实现

    @OnClick({R.id.rl_registration, R.id.rl_treat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //挂号预约
            case R.id.rl_registration:
                startActivity(new Intent(getPageContext(), RegistrationMainAppointmentActivity.class));
                break;
            //诊疗预约
            case R.id.rl_treat:
                startActivity(new Intent(getPageContext(), RegistrationMainTreatActivity.class));
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }
}
