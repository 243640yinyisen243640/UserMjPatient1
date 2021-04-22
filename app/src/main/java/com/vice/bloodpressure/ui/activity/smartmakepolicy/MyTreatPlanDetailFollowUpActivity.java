package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MyTreatPlanDetailBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 就诊详情
 * 作者: LYD
 * 创建日期: 2019/4/3 15:18
 */
public class MyTreatPlanDetailFollowUpActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    @BindView(R.id.tv_follow_up_advice)
    TextView tvFollowUpAdvice;
    @BindView(R.id.ll_doctor_advice)
    LinearLayout llDoctorAdvice;
    @BindView(R.id.tv_doctor_advice)
    TextView tvDoctorAdvice;

    @BindView(R.id.bt_get_again)
    ColorButton btGetAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("智能决策");
        getData();
        setGetBtAgain();
    }

    private void setGetBtAgain() {
        String type = getIntent().getStringExtra("type");
        if ("0".equals(type)) {
            btGetAgain.setVisibility(View.VISIBLE);
        } else {
            btGetAgain.setVisibility(View.GONE);
        }
    }

    private void getData() {
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        XyUrl.okPost(XyUrl.PLAN_GET_PLAN_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MyTreatPlanDetailBean data = JSONObject.parseObject(value, MyTreatPlanDetailBean.class);
                Message msg = getHandlerMessage();
                msg.what = GET_DATA;
                msg.obj = data;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_my_treat_plan_detail_follow_up, contentLayout, false);
        return layout;
    }

    @OnClick({R.id.bt_get_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_again://答题
                startActivity(new Intent(getPageContext(), HbpSubmitMainActivity.class));
                break;
        }
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                MyTreatPlanDetailBean data = (MyTreatPlanDetailBean) msg.obj;
                String recommend = data.getRecommend();
                String s1 = recommend.replace('；', '\n');
                tvFollowUpAdvice.setText(s1);

                if (TextUtils.isEmpty(data.getMessage())) {
                    llDoctorAdvice.setVisibility(View.GONE);
                } else {
                    llDoctorAdvice.setVisibility(View.VISIBLE);
                    tvDoctorAdvice.setText(data.getMessage());
                }
                break;
        }

    }
}
