package com.vice.bloodpressure.ui.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.AppointmentCheckAddBean;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.PatientOfTreatListBean;
import com.vice.bloodpressure.bean.ScheduleInfoBean;
import com.vice.bloodpressure.bean.ScheduleInfoPostBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.PickerUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 预约确认
 * 作者: LYD
 * 创建日期: 2019/10/21 16:01
 */
public class AppointmentCheckActivity extends BaseHandlerActivity {
    private static final int GET_PATIENT_OF_TREAT_LIST = 10086;
    private static final int GET_PATIENT_OF_TREAT_LIST_NO_DATA = 30002;
    private static final int GET_DATA = 10010;
    private static final int ADD_PATIENT = 10011;
    private static final String TAG = "AppointmentCheckActivity";
    private static final int ADD_APPOINTMENT_SUCCESS = 10012;
    @BindView(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @BindView(R.id.tv_hospital_department)
    TextView tvHospitalDepartment;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_time_select)
    TextView tvTimeSelect;
    @BindView(R.id.tv_people_select)
    TextView tvPeopleSelect;

    //时间段
    private List<String> listStr;
    //医生信息
    private ScheduleInfoBean data;
    //预约人id
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("预约确认");
        getCheckData();
    }


    /**
     * 获取检验数据
     */
    private void getCheckData() {
        ScheduleInfoPostBean data = (ScheduleInfoPostBean) getIntent().getSerializableExtra("data");
        String schday = data.getSchday();
        String sid = data.getSid();
        String type = data.getType();
        HashMap map = new HashMap<>();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        map.put("sid", sid);
        map.put("schday", schday);
        map.put("type", type);
        XyUrl.okPost(XyUrl.GET_SCHEDULE_INFO, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ScheduleInfoBean data = JSONObject.parseObject(value, ScheduleInfoBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_appointment_check_detail, contentLayout, false);
        return view;
    }


    @OnClick({R.id.ll_time_select, R.id.ll_people_select, R.id.tv_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择时间
            case R.id.ll_time_select:
                if (listStr != null && listStr.size() > 0) {
                    selectTime();
                } else {
                    ToastUtils.showShort("时间段为空");
                }
                break;
            //选择就诊人
            case R.id.ll_people_select:
                getPatientOfTreatList();
                break;
            //预约挂号
            case R.id.tv_check:
                checkAdd();
                break;
        }
    }


    /**
     * 校验添加
     */
    private void checkAdd() {
        String time = tvTimeSelect.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            ToastUtils.showShort("请选择就诊时间");
            return;
        }
        String name = tvPeopleSelect.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请选择就诊人");
            return;
        }
        goAdd();
    }


    /**
     * 提交
     */
    private void goAdd() {
        if (data != null && !TextUtils.isEmpty(id)) {
            int sid = data.getSid();
            int docuserid = data.getDocuserid();
            String schday = data.getSchday();
            String type = data.getType();
            String schtime = tvTimeSelect.getText().toString().trim();
            HashMap map = new HashMap<>();
            map.put("sid", sid);
            map.put("schuser", id);
            map.put("docuserid", docuserid);
            map.put("schday", schday);
            map.put("type", type);
            map.put("schtime", schtime);
            XyUrl.okPost(XyUrl.ADD_SCHEDULING, map, new OkHttpCallBack<String>() {
                @Override
                public void onSuccess(String msg) {
                    AppointmentCheckAddBean data = JSONObject.parseObject(msg, AppointmentCheckAddBean.class);
                    Message handlerMessage = getHandlerMessage();
                    handlerMessage.what = ADD_APPOINTMENT_SUCCESS;
                    handlerMessage.obj = data;
                    sendHandlerMessage(handlerMessage);
                }

                @Override
                public void onError(int error, String errorMsg) {
                    ToastUtils.showShort(errorMsg);
                }
            });

        }
    }

    /**
     * 选择时间
     */
    private void selectTime() {
        PickerUtils.showOption(getPageContext(), new PickerUtils.TimePickerCallBack() {
            @Override
            public void execEvent(String content) {
                tvTimeSelect.setText(content);
            }

        }, listStr);
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
            case GET_DATA:
                data = (ScheduleInfoBean) msg.obj;
                String hospitalname = data.getHospitalname();
                String depname = data.getDepname();
                String docname = data.getDocname();
                String strschday = data.getStrschday();
                String username = data.getUsername();
                tvHospitalName.setText("医院：" + hospitalname);
                tvHospitalDepartment.setText("科室：" + depname);
                tvDoctorName.setText("医生：" + docname);
                tvTime.setText("日期：" + strschday);
                //默认就诊人
                tvPeopleSelect.setText(username);
                listStr = data.getStimestr();
                //默认就诊人id
                id = data.getId() + "";
                break;
            //list不为空
            case GET_PATIENT_OF_TREAT_LIST:
                PatientOfTreatListBean data = (PatientOfTreatListBean) msg.obj;
                int isHaveDefault = data.getHasdefult();
                List<PatientOfTreatListBean.PatientsBean> list = data.getPatients();
                intent = new Intent(getPageContext(), PatientOfTreatListActivity.class);
                intent.putExtra("from", "check");
                intent.putExtra("patientList", (Serializable) list);
                intent.putExtra("isHaveDefault", isHaveDefault);
                startActivityForResult(intent, ADD_PATIENT);
                break;
            //list为空
            case GET_PATIENT_OF_TREAT_LIST_NO_DATA:
                intent = new Intent(getPageContext(), PatientOfTreatAddOrEditActivity.class);
                intent.putExtra("isEdit", 0);
                //intent.putExtra("patientInfo", null);
                startActivityForResult(intent, ADD_PATIENT);
                break;
            case ADD_APPOINTMENT_SUCCESS:
                AppointmentCheckAddBean addData = (AppointmentCheckAddBean) msg.obj;
                String schid = addData.getSchid();
                intent = new Intent(getPageContext(), AppointmentDetailActivity.class);
                intent.putExtra("schid", schid);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_PATIENT:
                    //预约id
                    id = data.getStringExtra("id");
                    //预约人姓名
                    String name = data.getStringExtra("name");
                    tvPeopleSelect.setText(name);
                    break;
            }
        }
    }
}
