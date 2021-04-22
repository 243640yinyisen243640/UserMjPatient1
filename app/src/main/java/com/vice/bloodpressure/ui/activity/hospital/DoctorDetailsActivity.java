package com.vice.bloodpressure.ui.activity.hospital;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MyDoctorBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.ImageLoaderUtil;
import com.wei.android.lib.colorview.view.ColorTextView;

import org.json.JSONException;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述:医生详情
 * 作者: LYD
 * 创建日期: 2020/11/9 16:57
 */
public class DoctorDetailsActivity extends BaseHandlerActivity {
    private static final int GET_DATA = 10010;
    private static final String TAG = "DoctorDetailsActivity";
    @BindView(R.id.img_head)
    QMUIRadiusImageView imgHead;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_doctor_type)
    TextView tvDoctorType;
    @BindView(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @BindView(R.id.tv_doctor_desc)
    TextView tvDoctorDesc;
    @BindView(R.id.tv_add_doctor)
    ColorTextView tvAddDoctor;
    private String docid;
    private MyDoctorBean doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.doctor_details));
        docid = getIntent().getStringExtra("docid");
        getData();
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_doctor_details, contentLayout, false);
    }


    /**
     * 医生详情
     */
    private void getData() {
        HashMap map = new HashMap<>();
        map.put("docid", docid);
        XyUrl.okPost(XyUrl.DOCTOR_DETAILS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                doctor = JSONObject.parseObject(value, MyDoctorBean.class);
                Message message = Message.obtain();
                message.what = GET_DATA;
                message.obj = doctor;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DATA:
                doctor = (MyDoctorBean) msg.obj;
                ImageLoaderUtil.loadCircleImg(imgHead, doctor.getImgurl(), R.drawable.default_doctor_head);
                tvDoctorName.setText(doctor.getDocname());
                tvDoctorType.setText(doctor.getDoczhc());
                tvHospitalName.setText(doctor.getHospitalname());
                tvDoctorDesc.setText(doctor.getContents());
                break;
        }
    }

    @OnClick(R.id.tv_add_doctor)
    public void onViewClicked() {
        HashMap map = new HashMap<>();
        map.put("docid", docid);
        XyUrl.okPostGetErrorData(XyUrl.ADD_DOCTOR, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(value);
                    String message = jsonObject.getString("message");
                    ToastUtils.showShort(message);
                    ActivityUtils.finishToActivity(MainActivity.class, false);
                    //finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int error, String data) throws JSONException {
                org.json.JSONObject jsonObject = new org.json.JSONObject(data);
                String message = jsonObject.getString("message");
                ToastUtils.showShort(message);
            }
        });
    }
}
