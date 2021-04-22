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
import com.vice.bloodpressure.bean.SignDetailBean;
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
 * 描述: 患者信息
 * 作者: LYD
 * 创建日期: 2020/1/17 9:31
 */
public class SignPatientInfoActivity extends BaseHandlerActivity implements View.OnClickListener {
    private static final int GET_DATA = 10086;
    private static final int SIGN_ADD_PATIENT = 10087;
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
    @BindView(R.id.ll_select_address)
    LinearLayout llSelectAddress;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;
    @BindView(R.id.et_build_organ)
    EditText etBuildOrgan;
    @BindView(R.id.et_person_in_charge_name)
    EditText etPersonInChargeName;
    @BindView(R.id.img_sign_add_patient)
    ImageView imgSignAddPatient;
    @BindView(R.id.tv_sign_add_patient)
    TextView tvSignAddPatient;
    @BindView(R.id.ll_patient)
    LinearLayout llPatient;
    @BindView(R.id.img_sign_add_doctor)
    ImageView imgSignAddDoctor;
    @BindView(R.id.tv_sign_add_doctor)
    TextView tvSignAddDoctor;
    @BindView(R.id.tv_submit)
    ColorTextView tvSubmit;

    //签名图片
    private String filePath;
    private String pId;
    private String cId;
    private String dId;
    private String relationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDetail();
        getTvSave().setText("拒绝");
        getTvSave().setOnClickListener(this);
    }

    private void getDetail() {
        int id = getIntent().getIntExtra("id", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        XyUrl.okPost(XyUrl.SIGN_APPLY_DETAIL, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SignDetailBean data = JSONObject.parseObject(value, SignDetailBean.class);
                Message message = getHandlerMessage();
                message.what = GET_DATA;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_sign_patient_info, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                SignDetailBean data = (SignDetailBean) msg.obj;
                String nickname = data.getNickname();
                etName.setText(nickname);
                String relationname = data.getRelationname();
                tvRelation.setText(relationname);
                String idcard = data.getIdcard();
                etIdNumber.setText(idcard);
                String tel = data.getTel();
                etPhone.setText(tel);
                String nativeplace = data.getNativeplace();
                tvAddress.setText(nativeplace);
                String address = data.getAddress();
                etAddressDetail.setText(address);
                String hospitalname = data.getHospitalname();
                String docname = data.getDocname();
                etBuildOrgan.setText(hospitalname);
                etPersonInChargeName.setText(docname);
                String doc_sign = data.getDoc_sign();
                String user_sign = data.getUser_sign();
                Glide.with(Utils.getApp())
                        .load(user_sign)
                        .error(R.drawable.img_sign_add_patient)
                        .placeholder(R.drawable.img_sign_add_patient)
                        .into(imgSignAddPatient);

                Glide.with(Utils.getApp())
                        .load(doc_sign)
                        .error(R.drawable.img_sign_add_doctor)
                        .placeholder(R.drawable.img_sign_add_doctor)
                        .into(imgSignAddDoctor);
                pId = data.getJbprov() + "";
                cId = data.getJbcity() + "";
                dId = data.getJbdist() + "";
                relationId = data.getRelation() + "";
                break;
        }
    }

    @OnClick({R.id.tv_relation, R.id.ll_select_address, R.id.img_sign_add_patient, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_relation:
                showSelectRelation();
                break;
            case R.id.ll_select_address:
                CityPickerUtils.show(getPageContext(), new CityPickerUtils.CityPickerCallBack() {
                    @Override
                    public void execEvent(String pName, String pId,
                                          String cName, String cId,
                                          String dName, String did) {
                        tvAddress.setText(pName + cName + dName);
                        SignPatientInfoActivity.this.pId = pId;
                        SignPatientInfoActivity.this.cId = cId;
                        SignPatientInfoActivity.this.dId = did;
                    }
                });
                break;
            case R.id.img_sign_add_patient:
                Intent intent = new Intent(getPageContext(), SignatureEditActivity.class);
                startActivityForResult(intent, SIGN_ADD_PATIENT);
                break;
            case R.id.tv_submit:
                doDeal("2");
                break;
        }
    }

    private void doDeal(String status) {
        //患者信息
        String name = etName.getText().toString().trim();
        String relation = tvRelation.getText().toString().trim();
        String idNumber = etIdNumber.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        String addressDetail = etAddressDetail.getText().toString().trim();
        //签名
        if ("2".equals(status)) {
            if (TextUtils.isEmpty(filePath)) {
                ToastUtils.showShort("请添加签名");
                return;
            }
        }
        int id = getIntent().getIntExtra("id", 0);
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", loginBean.getToken());
        map.put("id", id + "");
        map.put("status", status);
        map.put("nickname", name);
        map.put("relation", relationId);
        map.put("relationname", relation);
        map.put("idcard", idNumber);
        map.put("tel", phone);
        map.put("nativeplace", address);
        map.put("address", addressDetail);
        map.put("jbprov", pId);
        map.put("jbcity", cId);
        map.put("jbdist", dId);
        RxHttpUtils.createApi(Service.class)
                .familyDoctorApplyDeal(getMultipartPart("user_sign", filePath, map, status))
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
                                                      Map<String, Object> paramsMap, String status) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != paramsMap) {
            for (String key : paramsMap.keySet()) {
                builder.addFormDataPart(key, (String) paramsMap.get(key));
            }
        }
        if ("2".equals(status)) {
            File file = new File(filePath);
            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart(fileName, file.getName(), imageBody);
        }
        return builder.build().parts();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more:
                doDeal("1");
                break;
        }
    }


}
