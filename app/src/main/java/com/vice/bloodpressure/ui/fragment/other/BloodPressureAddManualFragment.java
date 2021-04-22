package com.vice.bloodpressure.ui.fragment.other;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.lsp.RulerView;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.ui.activity.healthrecordlist.BmiDetailActivity;
import com.vice.bloodpressure.utils.PickerUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:手动添加BMI/血压
 * 作者: LYD
 * 创建日期: 2019/7/8 16:12
 */
public class BloodPressureAddManualFragment extends BaseFragment {
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
    private boolean bmi;

    @SuppressLint("ValidFragment")
    public BloodPressureAddManualFragment(boolean bmi) {
        this.bmi = bmi;
    }

    public BloodPressureAddManualFragment() {
    }


    @Override
    public void onStart() {
        super.onStart();
        if (bmi) {
            TextView tvHeight = getView().findViewById(R.id.tv_first_left);
            tvHeight.setText("身高");
            TextView tvHeightUnit = getView().findViewById(R.id.tv_first_right);
            tvHeightUnit.setText("cm");
            TextView tvWeight = getView().findViewById(R.id.tv_second_left);
            tvWeight.setText("体重");
            TextView tvWeightUnit = getView().findViewById(R.id.tv_second_right);
            tvWeightUnit.setText("kg");

            rulerViewHigh.setFirstScale(17);
            rulerViewLow.setFirstScale(6);
            tvHigh.setText("170");
            tvLow.setText("60");

            LinearLayout llBmiFragment = getView().findViewById(R.id.ll_bmi_fragment);
            llBmiFragment.setVisibility(View.VISIBLE);
            Button btnBmiInfo = getView().findViewById(R.id.btn_bmi_info);
            btnBmiInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity().getApplicationContext(), BmiDetailActivity.class));
                }
            });

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blood_pressure_add_manual;
    }

    @Override
    protected void init(View rootView) {
        initRulerListener();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String nowString = TimeUtils.millis2String(System.currentTimeMillis(), simpleDateFormat);
        tvTime.setText(nowString);
    }


    private void initRulerListener() {
        rulerViewHigh.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvHigh.setText(floatStringToIntString(result));
                if (bmi) {
                    EventBusUtils.post(new EventMessage<>(ConstantParam.BMI_HEIGHT, result));
                } else
                    EventBusUtils.post(new EventMessage<>(ConstantParam.BLOOD_PRESSURE_ADD_HIGH, result));
            }
        });
        rulerViewLow.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvLow.setText(floatStringToIntString(result));
                if (bmi) {
                    EventBusUtils.post(new EventMessage<>(ConstantParam.BMI_WEIGHT, result));
                } else
                    EventBusUtils.post(new EventMessage<>(ConstantParam.BLOOD_PRESSURE_ADD_LOW, result));
            }
        });
    }


    @OnClick(R.id.ll_select_time)
    public void onViewClicked() {
        PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvTime.setText(content);
                EventBusUtils.post(new EventMessage<>(ConstantParam.BLOOD_PRESSURE_ADD_TIME, content));
            }

        });
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }

}
