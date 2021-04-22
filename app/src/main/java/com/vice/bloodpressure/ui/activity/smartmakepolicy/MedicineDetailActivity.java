package com.vice.bloodpressure.ui.activity.smartmakepolicy;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MedicineDetailBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.view.JustifyTextView;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 描述: 用药详情
 * 作者: LYD
 * 创建日期: 2019/3/30 17:24
 * 参数:type 0:药品库 1:智能决策
 */
public class MedicineDetailActivity extends BaseHandlerActivity {

    private static final int GET_DATA = 10010;

    @BindView(R.id.tv_medicine_name)
    TextView tvMedicineName;
    @BindView(R.id.tv_medicine_use)
    TextView tvMedicineUse;
    @BindView(R.id.tv_medicine_indication)
    TextView tvMedicineIndication;
    @BindView(R.id.tv_medicine_contraindication)
    TextView tvMedicineContraindication;
    @BindView(R.id.tv_medicine_untoward_effect)
    TextView tvMedicineUntowardEffect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setTitle("用药要求");
        getData();
    }

    private void getData() {
        HashMap map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        XyUrl.okPost(XyUrl.GET_DRUGS_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {

                MedicineDetailBean data = JSONObject.parseObject(value, MedicineDetailBean.class);
                Message msg = getHandlerMessage();
                msg.obj = data;
                msg.what = GET_DATA;
                sendHandlerMessage(msg);

            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_medicine_detail, contentLayout, false);
        return layout;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                MedicineDetailBean data = (MedicineDetailBean) msg.obj;
                tvMedicineName.setText(data.getTitle());
                tvMedicineUse.setText(data.getUsage());
                tvMedicineIndication.setText(data.getIndication());
                tvMedicineContraindication.setText(data.getContra());
                tvMedicineUntowardEffect.setText(data.getBad());
                break;
        }
    }

    private void initView() {
        TextView tvLeftOne = findViewById(R.id.tv_left_one);
        JustifyTextView tvLeftTwo = findViewById(R.id.tv_left_two);
        JustifyTextView tvLeftThree = findViewById(R.id.tv_left_three);
        JustifyTextView tvLeftFour = findViewById(R.id.tv_left_four);
        JustifyTextView tvLeftFive = findViewById(R.id.tv_left_five);

        tvLeftTwo.setTitleWidth(tvLeftOne);
        tvLeftThree.setTitleWidth(tvLeftOne);
        tvLeftFour.setTitleWidth(tvLeftOne);
        tvLeftFive.setTitleWidth(tvLeftOne);

    }
}
