package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.ui.fragment.highbloodpressuretest.TestHealthOneFragment;

/**
 * 描述: 方案申请 之高血压
 * 作者: LYD
 * 创建日期: 2020/6/16 9:48
 */
public class TestOfHbpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("方案申请");
        addFirstTestFragment();
        getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoFinish();
            }
        });
    }

    private void toDoFinish() {
        ActivityUtils.finishToActivity(MakePolicyActivity.class, false);
        finish();
    }

    private void addFirstTestFragment() {
        TestHealthOneFragment testFragment = new TestHealthOneFragment();
        FragmentUtils.replace(getSupportFragmentManager(), testFragment, R.id.ll_fragment_root, false);
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_test_of_hbp, contentLayout, false);
        return layout;
    }

    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toDoFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
