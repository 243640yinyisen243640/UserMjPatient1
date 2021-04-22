package com.vice.bloodpressure.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.blankj.utilcode.util.FragmentUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.login.registerandlogin.LoginFragment;

/**
 * 描述: 登录新页面
 * 作者: LYD
 * 创建日期: 2019/9/17 15:00
 */
public class LoginActivity extends BaseHandlerActivity {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        addFirstFragment();
        initStatusBar();
    }


    /**
     * 初始化状态栏
     */
    public void initStatusBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .init();
    }

    /**
     * 添加第一个Fragment
     */
    private void addFirstFragment() {
        LoginFragment loginRegisterFragment = new LoginFragment();
        FragmentUtils.replace(getSupportFragmentManager(), loginRegisterFragment, R.id.fl_fragment);
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_login, contentLayout, false);
        return view;
    }


    @Override
    public void processHandlerMsg(Message msg) {

    }
}
