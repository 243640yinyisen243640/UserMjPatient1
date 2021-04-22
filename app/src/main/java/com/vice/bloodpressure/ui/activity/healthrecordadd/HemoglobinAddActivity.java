package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by yicheng on 2018/6/4.
 * 添加血红蛋白
 */

public class HemoglobinAddActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvHemoglobinTime;
    private EditText etHemoglobin;

    private LoginBean user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("记录糖化血红蛋白");
        user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        initViews();
        setTime();
        showTvSave();
    }


    private void initViews() {
        RelativeLayout rlHemoglobinTime = findViewById(R.id.rl_hemoglobin_time);
        rlHemoglobinTime.setOnClickListener(this);
        tvHemoglobinTime = findViewById(R.id.tv_hemoglobin_time);
        etHemoglobin = findViewById(R.id.et_hemoglobin);
        TextView tvSave = getTvSave();
        tvSave.setText("保存");
        tvSave.setOnClickListener(this);
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hemoglobin_add, contentLayout, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_hemoglobin_time:
                PickerUtils.showTimeHm(HemoglobinAddActivity.this, new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvHemoglobinTime.setText(content);
                    }
                });
                break;
            case R.id.tv_more:
                saveData();
                break;
        }
    }

    /**
     * 保存数据
     */
    private void saveData() {
        //记录时间
        String datetime = tvHemoglobinTime.getText().toString().trim();
        //糖化血红蛋白值
        String diastaticvalue = etHemoglobin.getText().toString().trim();
        if (TextUtils.isEmpty(diastaticvalue)) {
            ToastUtils.showShort("请输入糖化值");
            return;
        }
        if (TextUtils.isEmpty(datetime)) {
            ToastUtils.showShort("请添加时间");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("diastaticvalue", diastaticvalue);
        map.put("datetime", datetime);
        XyUrl.okPostSave(XyUrl.ADD_HEMOGLOBIN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(R.string.save_ok);
                EventBusUtils.post(new EventMessage<>(ConstantParam.HEMOGLOBIN_RECORD_ADD));
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    private void setTime() {
        SimpleDateFormat Allformat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), Allformat);
        tvHemoglobinTime.setText(allTimeString);
    }
}
