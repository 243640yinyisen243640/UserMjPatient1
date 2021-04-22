package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.allen.library.RxHttpUtils;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.SignDoctorInfoBean;
import com.vice.bloodpressure.bean.SignPatientInfoBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.CityPickerUtils;
import com.vice.bloodpressure.utils.PickerUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 描述:  快速签约(新增编辑)
 * 作者: LYD
 * 创建日期: 2020/1/3 9:59
 */
public class QuickSignAddActivity extends BaseHandlerActivity {
    private static final int GET_USER_INFO = 10086;
    private static final int SIGN_ADD_PATIENT = 10087;
    private static final int GET_DOCTOR_INFO = 10088;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_relation)
    TextView tvRelation;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;
    @BindView(R.id.et_build_organ)
    EditText etBuildOrgan;
    @BindView(R.id.et_person_in_charge_name)
    EditText etPersonInChargeName;
    @BindView(R.id.tv_sign_rate)
    TextView tvSignRate;
    @BindView(R.id.tv_good_count)
    ColorTextView tvGoodCount;
    @BindView(R.id.tv_middle_count)
    ColorTextView tvMiddleCount;
    @BindView(R.id.tv_bad_count)
    ColorTextView tvBadCount;
    @BindView(R.id.img_sign_add_patient)
    ImageView imgSignAddPatient;
    @BindView(R.id.tv_sign_add_patient)
    TextView tvSignAddPatient;
    @BindView(R.id.ll_patient)
    LinearLayout llPatient;
    @BindView(R.id.tv_submit)
    ColorTextView tvSubmit;
    //签名图片
    private String filePath;
    private String pId = "";
    private String cId = "";
    private String dId = "";
    private String relationId = "";
    private int docid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("新增编辑");
        String doctor_id = getIntent().getIntExtra("doctor_id", 0) + "";
        getDoctorInfoDetail(doctor_id);
        getUserInfoDetail();
        String docid = getIntent().getStringExtra("docid");
        if (!TextUtils.isEmpty(docid)) {
            getDoctorInfoDetail(docid);
        }
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_quick_sign_add, contentLayout, false);
        return view;
    }

    /**
     * 获取用户信息
     */
    private void getUserInfoDetail() {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String userid = loginBean.getUserid();
        String token = loginBean.getToken();
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        XyUrl.okPost(XyUrl.SIGN_PATIENT_INFO, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SignPatientInfoBean data = JSONObject.parseObject(value, SignPatientInfoBean.class);
                Message msg = Message.obtain();
                msg.what = GET_USER_INFO;
                msg.obj = data;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取医生详情
     */
    private void getDoctorInfoDetail(String doctor_id) {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", loginBean.getToken());
        map.put("docid", doctor_id);
        XyUrl.okPost(XyUrl.SIGN_DOCTOR_INFO, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SignDoctorInfoBean data = JSONObject.parseObject(value, SignDoctorInfoBean.class);
                Message msg = Message.obtain();
                msg.what = GET_DOCTOR_INFO;
                msg.obj = data;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 设置医生信息
     *
     * @param data
     */
    private void setDoctorInfo(SignDoctorInfoBean data) {
        String hospitalname = data.getHospitalname();
        String docname = data.getDocname();
        String signRate = data.getSigning_rate();
        etBuildOrgan.setText(hospitalname);
        etPersonInChargeName.setText(docname);
        tvSignRate.setText(signRate + "%");
        String applause_rate = data.getApplause_rate();
        String median_rate = data.getMedian_rate();
        String negative_rate = data.getNegative_rate();
        tvGoodCount.setText("好评 " + applause_rate);
        tvMiddleCount.setText("中评 " + median_rate);
        tvBadCount.setText("差评 " + negative_rate);
    }

    /**
     * 校验提交
     */
    private void doSubmit() {
        //患者信息
        String name = etName.getText().toString().trim();
        String relation = tvRelation.getText().toString().trim();
        String idNumber = etIdNumber.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        String addressDetail = etAddressDetail.getText().toString().trim();
        //签名
        if (TextUtils.isEmpty(filePath)) {
            ToastUtils.showShort("请添加签名");
            return;
        }
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", loginBean.getToken());
        map.put("nickname", name);
        map.put("relation", relationId);
        map.put("idcard", idNumber);
        map.put("tel", phone);
        map.put("nativeplace", address);
        map.put("address", addressDetail);
        map.put("jbprov", pId);
        map.put("jbcity", cId);
        map.put("jbdist", dId);
        map.put("docid", docid + "");
        RxHttpUtils.createApi(Service.class)
                .familyDoctorAdd(getMultipartPart("user_sign", filePath, map))
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<String>() {
                    @Override
                    protected void onError(String errorMsg) {
                    }

                    @Override
                    protected void onSuccess(String string) {
                        ToastUtils.showShort("获取成功");
                        ActivityUtils.finishToActivity(MainActivity.class, false);
                    }
                });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_USER_INFO:
                SignPatientInfoBean patientInfoBean = (SignPatientInfoBean) msg.obj;
                String nickname = patientInfoBean.getNickname();
                etName.setText(nickname);
                etName.setSelection(nickname.length());
                String idcard = patientInfoBean.getIdcard();
                etIdNumber.setText(idcard);
                String tel = patientInfoBean.getTel();
                etPhone.setText(tel);
                String nativeplace = patientInfoBean.getNativeplace();
                tvAddress.setText(nativeplace);
                String address = patientInfoBean.getAddress();
                etAddressDetail.setText(address);
                pId = patientInfoBean.getJbprov();
                cId = patientInfoBean.getJbcity();
                dId = patientInfoBean.getJbdist();
                break;
            case GET_DOCTOR_INFO:
                SignDoctorInfoBean doctorInfoBean = (SignDoctorInfoBean) msg.obj;
                docid = doctorInfoBean.getDocid();
                setDoctorInfo(doctorInfoBean);
                break;
        }
    }


    @OnClick({R.id.tv_relation, R.id.tv_address, R.id.img_sign_add_patient, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择关系(户主,孙子)
            case R.id.tv_relation:
                showSelectRelation();
                break;
            //地区三级联动
            case R.id.tv_address:
                showSelectAddress();
                break;
            //添加手写签名
            case R.id.img_sign_add_patient:
                Intent intent = new Intent(getPageContext(), SignatureEditActivity.class);
                startActivityForResult(intent, SIGN_ADD_PATIENT);
                break;
            //提交
            case R.id.tv_submit:
                doSubmit();
                break;
        }
    }

    /**
     * 选择地区
     */
    private void showSelectAddress() {
        CityPickerUtils.show(getPageContext(), new CityPickerUtils.CityPickerCallBack() {
            @Override
            public void execEvent(String pName, String pId,
                                  String cName, String cId,
                                  String dName, String did) {
                tvAddress.setText(pName + cName + dName);
                QuickSignAddActivity.this.pId = pId;
                QuickSignAddActivity.this.cId = cId;
                QuickSignAddActivity.this.dId = did;
            }
        });
    }

    /**
     * 选择关系
     */
    private void showSelectRelation() {
        String[] stringArray = getResources().getStringArray(R.array.home_sign_relation);
        List<String> relationList = ArrayUtils.asList(stringArray);
        PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
            @Override
            public void execEvent(String content, int position) {
                tvRelation.setText(content);
                relationId = position + 1 + "";
            }
        }, relationList);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case SIGN_ADD_PATIENT:
                    filePath = data.getStringExtra("savePath");
                    Glide.with(Utils.getApp()).load(filePath).into(imgSignAddPatient);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 上传单张图片
     *
     * @param fileName  后台接收图片流的参数名
     * @param filePath  图片路径
     * @param paramsMap 普通参数 图文混合参数
     * @return List<MultipartBody.Part>
     */
    private List<MultipartBody.Part> getMultipartPart(String fileName, String filePath,
                                                      Map<String, String> paramsMap) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != paramsMap) {
            for (String key : paramsMap.keySet()) {
                builder.addFormDataPart(key, paramsMap.get(key));
            }
        }
        File file = new File(filePath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart(fileName, file.getName(), imageBody);
        return builder.build().parts();
    }
}
