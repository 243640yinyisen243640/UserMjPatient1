package com.vice.bloodpressure.ui.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.PhoneUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ConnectUsActivity extends BaseActivity {

    @BindView(R.id.img_call)
    ImageView imgCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("联系我们");
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_connect_us, contentLayout, false);
        return view;
    }

    @OnClick(R.id.img_call)
    public void onViewClicked() {
        PhoneUtils.dial("020-83627407");
    }
}