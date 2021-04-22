package com.vice.bloodpressure.ui.activity.homesign;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;

public class ServicePackDetailActivity extends BaseHandlerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("服务包详情");
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_service_pack_detail, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
