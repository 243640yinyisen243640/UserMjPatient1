package com.vice.bloodpressure.ui.activity.registration;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;

/**
 * 描述:诊疗预约主页
 * 作者: LYD
 * 创建日期: 2019/10/18 11:51
 */
public class RegistrationMainTreatActivity extends BaseHandlerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("诊疗预约");
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_registration_treat, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
