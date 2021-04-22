package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.wei.android.lib.colorview.view.ColorButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 方案申请 之高血压
 * 作者: LYD
 * 创建日期: 2020/10/15 10:25
 */public class HbpSubmitMainActivity extends BaseHandlerActivity {
    @BindView(R.id.bt_start_test)
    ColorButton btStartTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("方案申请");
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_high_blood_pressure_submit_main, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.bt_start_test)
    public void onViewClicked() {
        startActivity(new Intent(getPageContext(), TestOfHbpActivity.class));
    }
}