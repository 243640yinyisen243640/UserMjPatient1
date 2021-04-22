package com.vice.bloodpressure.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.blankj.utilcode.util.FragmentUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.loginquestion.IsHaveSugarDiseaseFragment;

/**
 * 描述:  注册问题
 * 作者: LYD
 * 创建日期: 2020/3/30 15:46
 */
public class RegisterQuestionActivity extends BaseHandlerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleBar();
        addFirstFragment();
    }

    private void addFirstFragment() {
        IsHaveSugarDiseaseFragment fragment = new IsHaveSugarDiseaseFragment();
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.fl_question, false);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_register_question, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
