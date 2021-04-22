package com.vice.bloodpressure.ui.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.suke.widget.SwitchButton;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.PatientOfTreatAddBean;
import com.vice.bloodpressure.bean.PatientOfTreatListBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.edittext.IdNumberKeyListener;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 描述:添加就诊人 和 编辑就诊人  页面
 * 作者: LYD
 * 创建日期: 2019/10/24 18:09
 * 参数名:isEdit
 * 参数类型:int
 * 参数意义:0 是添加 1是编辑
 */
public class PatientOfTreatAddOrEditActivity extends BaseHandlerActivity implements View.OnClickListener {
    private static final int ADD_PATIENT_SUCCESS = 10086;
    private static final String TAG = "PatientOfTreatAddOrEditActivity";
    @BindView(R.id.et_person_name)
    EditText etPersonName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.sb_default)
    SwitchButton sbDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加身份证号监听
        etIdNumber.setKeyListener(new IdNumberKeyListener());
        getLlMore().setOnClickListener(this);
        //默认就诊人 1有  2无
        int isHaveDefault = getIntent().getIntExtra("isHaveDefault", 0);
        if (1 == isHaveDefault) {
            sbDefault.setChecked(false);
        } else {
            sbDefault.setChecked(true);
        }
        int isEdit = getIntent().getIntExtra("isEdit", 0);
        initTitle(isEdit);
        setIsEdit(isEdit);
    }

    /**
     * 设置标题
     *
     * @param isEdit
     */
    private void initTitle(int isEdit) {
        if (0 == isEdit) {
            setTitle("添加就诊人");
        } else {
            setTitle("编辑就诊人");
        }
        showTvSave();
    }


    /**
     * 设置姓名手机号身份证是否可编辑
     *
     * @param isEdit
     */
    private void setIsEdit(int isEdit) {
        if (0 == isEdit) {
            etIdNumber.setEnabled(true);
            etPersonName.setEnabled(true);
            etPhoneNumber.setEnabled(true);
        } else {
            etIdNumber.setEnabled(false);
            etPersonName.setEnabled(false);
            etPhoneNumber.setEnabled(false);
            //设置值
            PatientOfTreatListBean.PatientsBean data = (PatientOfTreatListBean.PatientsBean) getIntent().getSerializableExtra("patientInfo");
            String username = data.getUsername();
            String tel = data.getTel();
            String idcard = data.getIdcard();
            int defaultX = data.getDefaultX();
            etPersonName.setText(username);
            etPhoneNumber.setText(tel);
            etIdNumber.setText(idcard);
            if (1 == defaultX) {
                sbDefault.setChecked(true);
            } else {
                sbDefault.setChecked(false);
            }
        }
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_patient_of_treat_add_or_edit, contentLayout, false);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_more:
                check();
                break;
        }
    }

    /**
     * 校验
     */
    private void check() {
        String personName = etPersonName.getText().toString().trim();
        if (TextUtils.isEmpty(personName)) {
            ToastUtils.showShort("请输入就诊人");
            return;
        }
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileSimple(phoneNumber)) {
            ToastUtils.showShort("请输入合法手机号");
            return;
        }
        String idNumber = etIdNumber.getText().toString().trim();
        if (TextUtils.isEmpty(idNumber)) {
            ToastUtils.showShort("请输入身份证号");
            return;
        }
        if (!RegexUtils.isIDCard18(idNumber)) {
            ToastUtils.showShort("请输入合法身份证号");
            return;
        }
        showAddOrEditDialog(personName, phoneNumber, idNumber);
    }


    /**
     * 显示添加编辑对话框
     *
     * @param personName
     * @param phoneNumber
     * @param idNumber
     */
    private void showAddOrEditDialog(String personName, String phoneNumber, String idNumber) {
        DialogUtils.getInstance().showDialog(getPageContext(), "", "确定存储该就诊人吗？", true, new DialogUtils.DialogCallBack() {
            @Override
            public void execEvent() {
                goAddOrEdit(personName, phoneNumber, idNumber);
            }
        });
    }

    /**
     * 添加或者编辑
     *
     * @param personName
     * @param phoneNumber
     * @param idNumber
     */
    private void goAddOrEdit(String personName, String phoneNumber, String idNumber) {
        //获取是否默认就诊人
        boolean checked = sbDefault.isChecked();
        String isDefault = "";
        if (checked) {
            isDefault = "1";
        } else {
            isDefault = "2";
        }
        //获取是否 添加编辑
        int isEdit = getIntent().getIntExtra("isEdit", 0);
        String type = "";
        String ofid = "";
        if (0 == isEdit) {
            //添加
            type = "1";
            ofid = "";
        } else {
            //编辑
            type = "2";
            PatientOfTreatListBean.PatientsBean data = (PatientOfTreatListBean.PatientsBean) getIntent().getSerializableExtra("patientInfo");
            ofid = data.getId() + "";
        }
        HashMap map = new HashMap<>();
        map.put("username", personName);
        map.put("tel", phoneNumber);
        map.put("idcard", idNumber);
        map.put("default", isDefault);
        map.put("type", type);
        //编辑时传
        map.put("ofid", ofid);
        XyUrl.okPost(XyUrl.ADD_SCH_PATIENT, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                PatientOfTreatAddBean data = JSONObject.parseObject(value, PatientOfTreatAddBean.class);
                Message msg = getHandlerMessage();
                msg.obj = data;
                msg.what = ADD_PATIENT_SUCCESS;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case ADD_PATIENT_SUCCESS:
                ToastUtils.showShort("操作成功");
                //获取schid
                PatientOfTreatAddBean data = (PatientOfTreatAddBean) msg.obj;
                String name = etPersonName.getText().toString().trim();
                String id = data.getId();
                //回传数据
                Intent intent = getIntent();
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                setResult(RESULT_OK, intent);
                //结束当前页面
                finish();
                //添加编辑成功以后 通知刷新
                EventBusUtils.post(new EventMessage<>(ConstantParam.PATIENT_ADD_OR_EDIT));
                break;
        }
    }
}
