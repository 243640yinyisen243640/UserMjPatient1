package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsp.RulerView;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  血氧添加
 * 作者: LYD
 * 创建日期: 2020/5/11 9:16
 */
public class BloodOxygenAddActivity extends BaseHandlerActivity {
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.ruler_view_high)
    RulerView rulerViewHigh;
    @BindView(R.id.tv_low)
    TextView tvLow;
    @BindView(R.id.ruler_view_low)
    RulerView rulerViewLow;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_select_time)
    LinearLayout llSelectTime;


    private void setFirstTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String nowString = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        tvTime.setText(nowString);
    }

    private void initTitle() {
        setTitle("记录血氧");
        getTvSave().setVisibility(View.VISIBLE);
        getTvSave().setText("保存");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void initRulerListener() {
        rulerViewHigh.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvHigh.setText(floatStringToIntString(result));

            }
        });
        rulerViewLow.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvLow.setText(floatStringToIntString(result));
            }
        });
    }


    @OnClick({R.id.ll_select_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_select_time:
                PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
                    @Override
                    public void execEvent(String content) {
                        tvTime.setText(content);
                    }
                });
                break;
        }
    }

    private void saveData() {
        String oxygen = tvHigh.getText().toString().trim();
        String pulse = tvLow.getText().toString().trim();
        String time = tvTime.getText().toString().trim();
        HashMap<String, Object> map = new HashMap<>();
        map.put("oxygen", oxygen);
        map.put("bpmval", pulse);
        map.put("type", 2);
        map.put("datetime", time);
        XyUrl.okPostSave(XyUrl.ADD_BLOOD_OXYGEN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(R.string.save_ok);
                EventBusUtils.post(new EventMessage<>(ConstantParam.ADD_BLOOD_OXYGEN));
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_blood_oxygen_add, contentLayout, false);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        initRulerListener();
        setFirstTime();
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }
}
