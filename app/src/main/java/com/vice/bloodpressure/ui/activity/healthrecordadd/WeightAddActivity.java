package com.vice.bloodpressure.ui.activity.healthrecordadd;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lsp.RulerView;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
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
 * 描述:  体重记录
 * 作者: LYD
 * 创建日期: 2020/5/9 13:56
 */
public class WeightAddActivity extends BaseHandlerActivity {
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.ruler_weight)
    RulerView rulerWeight;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_select_time)
    LinearLayout llSelectTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("记录体重");
        setRuler();
        setTvSave();
        setCurrentTime();
    }

    private void setCurrentTime() {
        SimpleDateFormat Allformat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String allTimeString = TimeUtils.millis2String(System.currentTimeMillis(), Allformat);
        tvTime.setText(allTimeString);
    }

    private void setTvSave() {
        getTvSave().setText("保存");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSave();
            }
        });
    }


    /**
     * 保存体重
     */
    private void toSave() {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", loginBean.getToken());
        map.put("weight", tvWeight.getText().toString().trim());
        map.put("type", 2);
        map.put("datetime", tvTime.getText().toString().trim());
        XyUrl.okPostSave(XyUrl.ADD_WEIGHT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(R.string.save_ok);
                EventBusUtils.post(new EventMessage<>(ConstantParam.ADD_WEIGHT));
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });


    }

    private void setRuler() {
        rulerWeight.setOnChooseResulterListener(new RulerView.OnChooseResulterListener() {
            @Override
            public void onEndResult(String result) {

            }

            @Override
            public void onScrollResult(String result) {
                tvWeight.setText(floatStringToIntString(result));
            }
        });
    }

    private String floatStringToIntString(String floatString) {
        int a = (int) Float.parseFloat(floatString);
        return String.valueOf(a);
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_weight_add, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.ll_select_time)
    public void onViewClicked() {
        PickerUtils.showTimeHm(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvTime.setText(content);
            }
        });
    }
}
