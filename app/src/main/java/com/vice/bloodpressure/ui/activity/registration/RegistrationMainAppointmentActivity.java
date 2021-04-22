package com.vice.bloodpressure.ui.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.PatientOfTreatListBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.registration.report.ReportListMainActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  挂号预约首页
 * 作者: LYD
 * 创建日期: 2019/10/18 10:21
 */
public class RegistrationMainAppointmentActivity extends BaseHandlerActivity {
    private static final int GET_PATIENT_OF_TREAT_LIST = 10086;
    private static final int GET_PATIENT_OF_TREAT_LIST_NO_DATA = 30002;
    @BindView(R.id.rl_one)
    RelativeLayout rlOne;
    @BindView(R.id.rl_two)
    RelativeLayout rlTwo;
    @BindView(R.id.rl_three)
    RelativeLayout rlThree;
    @BindView(R.id.rl_four)
    RelativeLayout rlFour;
    @BindView(R.id.rl_five)
    RelativeLayout rlFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("挂号预约");
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_registration_appointment, contentLayout, false);
        return view;
    }


    @OnClick({R.id.rl_one, R.id.rl_two, R.id.rl_three, R.id.rl_four, R.id.rl_five})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //体检预约
            case R.id.rl_one:
                startActivity(new Intent(getPageContext(), PhysicalExaminationListOfHospitalActivity.class));
                break;
            //报告查询
            case R.id.rl_two:
                startActivity(new Intent(getPageContext(), ReportListMainActivity.class));
                break;
            //我的全部预约
            case R.id.rl_three:
                startActivity(new Intent(getPageContext(), MyAppointListMainActivity.class));
                break;
            //就诊人
            case R.id.rl_four:
                getPatientOfTreatList();
                break;
            //体检注意事项
            case R.id.rl_five:
                startActivity(new Intent(getPageContext(), CheckNoticeItemActivity.class));
                break;
        }
    }


    /**
     * 获取就诊人列表
     */
    private void getPatientOfTreatList() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_SCH_PATIENTS_LIST, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                PatientOfTreatListBean list = JSONObject.parseObject(value, PatientOfTreatListBean.class);
                Message msg = getHandlerMessage();
                msg.what = GET_PATIENT_OF_TREAT_LIST;
                msg.obj = list;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = getHandlerMessage();
                msg.what = GET_PATIENT_OF_TREAT_LIST_NO_DATA;
                sendHandlerMessage(msg);
            }
        });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        Intent intent = null;
        switch (msg.what) {
            //list不为空
            case GET_PATIENT_OF_TREAT_LIST:
                PatientOfTreatListBean data = (PatientOfTreatListBean) msg.obj;
                int isHaveDefault = data.getHasdefult();
                List<PatientOfTreatListBean.PatientsBean> patients = data.getPatients();
                intent = new Intent(getPageContext(), PatientOfTreatListActivity.class);
                intent.putExtra("from", "main");
                intent.putExtra("patientList", (Serializable) patients);
                intent.putExtra("isHaveDefault", isHaveDefault);
                startActivity(intent);
                break;
            //list为空
            case GET_PATIENT_OF_TREAT_LIST_NO_DATA:
                intent = new Intent(getPageContext(), PatientOfTreatListActivity.class);
                intent.putExtra("from", "main");
                startActivity(intent);
                break;
        }
    }
}
