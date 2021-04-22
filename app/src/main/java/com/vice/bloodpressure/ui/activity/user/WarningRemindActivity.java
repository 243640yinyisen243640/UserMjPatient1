package com.vice.bloodpressure.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.ui.fragment.other.BloodWarningFragment;
import com.vice.bloodpressure.ui.fragment.other.SugarWarningFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 首页预警提醒
 * 作者: LYD
 * 创建日期: 2019/4/15 17:56
 */

public class WarningRemindActivity extends BaseActivity {
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.view_warning_left)
    View viewWarningLeft;
    @BindView(R.id.rl_blood_warning)
    RelativeLayout rlBloodWarning;
    @BindView(R.id.tv_system)
    TextView tvSystem;
    @BindView(R.id.view_warning_right)
    View viewWarningRight;
    @BindView(R.id.rl_sugar_warning)
    RelativeLayout rlSugarWarning;
    @BindView(R.id.fragment_warning)
    FrameLayout fragmentWarning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.warning_remind));
        addFragment();
    }


    /**
     * 添加Fragment
     */
    private void addFragment() {
        BloodWarningFragment bloodWarningFragment = new BloodWarningFragment();
        FragmentUtils.replace(getSupportFragmentManager(), bloodWarningFragment, R.id.fragment_warning, false);
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_warning_remind, contentLayout, false);
        return layout;
    }


    @OnClick({R.id.rl_blood_warning, R.id.rl_sugar_warning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_blood_warning:
                viewWarningLeft.setVisibility(View.VISIBLE);
                viewWarningRight.setVisibility(View.GONE);
                BloodWarningFragment bloodWarningFragment = new BloodWarningFragment();
                FragmentUtils.replace(getSupportFragmentManager(), bloodWarningFragment, R.id.fragment_warning, false);
                break;
            case R.id.rl_sugar_warning:
                viewWarningLeft.setVisibility(View.GONE);
                viewWarningRight.setVisibility(View.VISIBLE);
                SugarWarningFragment sugarWarningFragment = new SugarWarningFragment();
                FragmentUtils.replace(getSupportFragmentManager(), sugarWarningFragment, R.id.fragment_warning, false);
                break;
        }
    }
}
