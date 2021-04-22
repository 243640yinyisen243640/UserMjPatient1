package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MyTreatPlanDetailBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class HbpDetailActivity extends BaseHandlerActivity {
    private static final int GET_DETAIL = 10010;
    @BindView(R.id.img_state)
    ImageView imgState;
    @BindView(R.id.tv_degree)
    TextView tvDegree;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_treat_advice)
    TextView tvTreatAdvice;
    @BindView(R.id.tv_doctor_advice)
    TextView tvDoctorAdvice;
    @BindView(R.id.ll_doctor_advice)
    LinearLayout llDoctorAdvice;
    @BindView(R.id.bt_re_get)
    ColorButton btReGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("血压管理方案");
        getDetail();
    }

    private void getDetail() {
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        XyUrl.okPost(XyUrl.PLAN_GET_PLAN_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MyTreatPlanDetailBean data = JSONObject.parseObject(value, MyTreatPlanDetailBean.class);
                Message msg = getHandlerMessage();
                msg.what = GET_DETAIL;
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
        View view = getLayoutInflater().inflate(R.layout.activity_hbp_detail, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DETAIL:
                MyTreatPlanDetailBean data = (MyTreatPlanDetailBean) msg.obj;
                String degree = data.getDegree();
                switch (degree) {
                    case "无风险":
                        imgState.setImageResource(R.drawable.hbp_detail_two);
                        break;
                    case "未确诊":
                        imgState.setImageResource(R.drawable.hbp_detail_one);
                        break;
                    case "低危":
                        imgState.setImageResource(R.drawable.hbp_detail_three);
                        break;
                    case "中危":
                        imgState.setImageResource(R.drawable.hbp_detail_four);
                        break;
                    case "高危":
                        imgState.setImageResource(R.drawable.hbp_detail_five);
                        break;
                    case "很高危":
                        imgState.setImageResource(R.drawable.hbp_detail_six);
                        break;
                }
                int bplevel = data.getBplevel();
                String strBpLevel = "";
                switch (bplevel) {
                    case 1:
                        strBpLevel = "正常高值";
                        break;
                    case 2:
                        strBpLevel = "高血压1级";
                        break;
                    case 3:
                        strBpLevel = "高血压2级";
                        break;
                    case 4:
                        strBpLevel = "高血压3级";
                        break;
                }
                tvDegree.setText(strBpLevel + "(" + degree + ")");
                String content = data.getContent();
                tvDesc.setText(content);
                String recommend = data.getRecommend();
                tvTreatAdvice.setText(recommend);
                String message = data.getMessage();
                if (TextUtils.isEmpty(message)) {
                    llDoctorAdvice.setVisibility(View.GONE);
                } else {
                    llDoctorAdvice.setVisibility(View.VISIBLE);
                    tvDoctorAdvice.setText(message);
                }
                break;
        }
    }

    @OnClick({R.id.bt_re_get, R.id.bt_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                toDoFinish();
                break;
            case R.id.bt_re_get:
                startActivity(new Intent(getPageContext(), HbpSubmitMainActivity.class));
                break;
        }

    }

    /**
     * 监听物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            toDoFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toDoFinish() {
        ActivityUtils.finishToActivity(MakePolicyActivity.class, false);
        finish();
    }
}