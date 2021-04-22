package com.vice.bloodpressure.ui.activity.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;
import com.gyf.immersionbar.ImmersionBar;
import com.lyd.libsteps.StepService;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.bean.ImeiGetBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.lyd.baselib.utils.SharedPreferencesUtils;

/**
 * 描述: 启动页
 * 作者: LYD
 * 创建日期: 2019/3/25 15:06
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "LYD";
    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
        @Override
        public void onWakeUp(AppData appData) {
            //获取绑定数据
            String bindData = appData.getData();
            ImeiGetBean imeiGetBean = GsonUtils.fromJson(bindData, ImeiGetBean.class);
            String imei = imeiGetBean.getIMEI();
            //判断
            LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
            if (user != null) {
                if (!TextUtils.isEmpty(imei)) {
                    SPStaticUtils.put("scanImei", imei);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将window的背景图设置为空
        getWindow().setBackgroundDrawable(null);
        hideTitleBar();
        initStatusBar();
        setSplash();
        //获取唤醒参数
        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);
        initStepService();
    }

    /**
     * 初始化计步服务
     */
    private void initStepService() {
        Intent intent = new Intent(this, StepService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //此处要调用，否则App在后台运行时，会无法截获
        OpenInstall.getWakeUp(intent, wakeUpAdapter);
    }

    /**
     * 初始化状态栏
     */
    public void initStatusBar() {
        ImmersionBar.with(this)
                .keyboardEnable(false)
                .statusBarDarkFont(false)
                .fitsSystemWindows(false)
                .statusBarColor(R.color.transparent)
                .init();
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_splash, contentLayout, false);
        return layout;
    }

    /**
     * 设置启动页
     */
    private void setSplash() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        if (user != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 1500);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, 1500);
        }
    }

    @Override
    protected void onDestroy() {
        wakeUpAdapter = null;
        super.onDestroy();
    }
}
