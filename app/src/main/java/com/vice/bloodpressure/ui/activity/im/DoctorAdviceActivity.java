package com.vice.bloodpressure.ui.activity.im;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.bean.CheckAdviceBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import butterknife.BindView;

/**
 * 描述: 医生建议
 * 作者: LYD
 * 创建日期: 2019/6/15 10:31
 */
public class DoctorAdviceActivity extends BaseActivity {
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_text_unit)
    TextView tvTextUnit;
    @BindView(R.id.tv_advice)
    TextView tvAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("医生建议");
        String id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            setDoctorAdviceData();
        } else {
            getData(id);
        }
    }

    private void getData(String id) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .checkAdvice(token, id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<CheckAdviceBean>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<CheckAdviceBean> checkAdviceBeanBaseData) {
                        CheckAdviceBean data = checkAdviceBeanBaseData.getData();
                        String advice = data.getContent();
                        String type = getIntent().getStringExtra("type");
                        String typeName = getIntent().getStringExtra("typeName");
                        String val = getIntent().getStringExtra("val");
                        tvAdvice.setText(advice);
                        tvText.setText(val);
                        //1 血压 2 血糖
                        if ("1".equals(type)) {
                            tvDesc.setText("血压值");
                            tvTextUnit.setText("mmHg");
                        } else {
                            tvDesc.setText(String.format("%s血糖值", typeName));
                            tvTextUnit.setText("mmol/L");
                        }
                    }
                });
    }


    /**
     * 设置医生建议数据
     */
    private void setDoctorAdviceData() {
        String advice = getIntent().getStringExtra("advice");
        String type = getIntent().getStringExtra("type");
        String typeName = getIntent().getStringExtra("typeName");
        String val = getIntent().getStringExtra("val");
        tvAdvice.setText(advice);
        tvText.setText(val);
        //1 血压 2 血糖
        if ("1".equals(type)) {
            tvDesc.setText("血压值");
            tvTextUnit.setText("mmHg");
        } else {
            tvDesc.setText(String.format("%s血糖值", typeName));
            tvTextUnit.setText("mmol/L");
        }
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_doctor_advice, contentLayout, false);
    }
}
