package com.vice.bloodpressure.ui.activity.food;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.FragmentUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.ui.fragment.healthydiet.HealthyDietTwoFragment;

import butterknife.BindView;

/**
 * 描述:  食材库
 * 作者: LYD
 * 创建日期: 2020/4/8 11:42
 */
public class FoodActivity extends BaseHandlerActivity {
    @BindView(R.id.fl_food)
    FrameLayout flFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("食材库");
        addFragment();
    }

    private void addFragment() {
        HealthyDietTwoFragment fragment = new HealthyDietTwoFragment();
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.fl_food, false);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_food, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
