package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.wei.android.lib.colorview.view.ColorButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 提交申请
 * 作者: LYD
 * 创建日期: 2019/4/2 11:17
 */
public class SubmitApplyActivity extends BaseActivity {
    @BindView(R.id.bt_apply_again)
    Button btApplyAgain;
    @BindView(R.id.bt_back_to_main_activity)
    ColorButton btBackToMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("提交申请");
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_submit_apply, contentLayout, false);
        return layout;
    }

    @OnClick({R.id.bt_apply_again, R.id.bt_back_to_main_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_apply_again:
                startActivity(new Intent(getPageContext(), HbpSubmitMainActivity.class));
                break;
            case R.id.bt_back_to_main_activity:
                ActivityUtils.finishToActivity(MainActivity.class, false);
                break;
        }
    }


}
