package com.vice.bloodpressure.ui.activity.smartanalyse;

import android.os.Bundle;
import android.view.View;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;

public class BloodPressureReportNoDataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血压分析报告");
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_blood_pressure_report_no_data, contentLayout, false);
        return view;
    }
}