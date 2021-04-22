package com.vice.bloodpressure.ui.activity.hospital;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;


/**
 * 描述: 科室公告详情
 * 作者: LYD
 * 创建日期: 2020/12/5 16:40
 */
public class DepartmentDetailsActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.sign_details));
        textView = findViewById(R.id.tv_department_details);
        String content = getIntent().getStringExtra("content");
        textView.setText(content);
    }



    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_department_details, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
