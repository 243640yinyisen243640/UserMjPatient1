package com.vice.bloodpressure.ui.activity.homesign;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.SignProtocolBean;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.engine.GlideImageEngine;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: SignProtocolActivity
 * @Description: 签约协议
 * @Author: ZWK
 * @CreateDate: 2020/1/3 16:50
 */

public class SignProtocolActivity extends BaseHandlerActivity {

    @BindView(R.id.iv_doctor_autograph)
    ImageView ivDoctorAutograph;
    @BindView(R.id.iv_patient_autograph)
    ImageView ivPatientAutograph;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_group_photo)
    ImageView ivGroupPhoto;

    private SignProtocolBean signProtocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("签约协议");
        getTvSave().setText("解约");
        getTvSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.getInstance().showDialog(getPageContext(), "", "请确定是否解约", true, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        rescission();
                    }
                });
            }
        });
        init();
    }


    private void rescission() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        RxHttpUtils.createApi(Service.class)
                .rescission(user.getToken())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData baseData) {
                        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                        loginBean.setSignid("");
                        SharedPreferencesUtils.putBean(getPageContext(), SharedPreferencesUtils.USER_INFO, loginBean);
                        ToastUtils.showShort("解约成功");
                        ActivityUtils.finishToActivity(MainActivity.class, false);
                    }
                });
    }


    private void init() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();
        RxHttpUtils.createApi(Service.class)
                .agreement(token, Integer.parseInt(user.getUserid()))
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<SignProtocolBean>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<SignProtocolBean> signProtocolBeanBaseData) {
                        signProtocol = signProtocolBeanBaseData.getData();
                        if (signProtocol == null) {
                            signProtocol = new SignProtocolBean();
                        }
                        Glide.with(Utils.getApp()).load(signProtocol.getGroup_photo()).placeholder(R.drawable.group_photo).into(ivGroupPhoto);
                        Glide.with(Utils.getApp()).load(signProtocol.getDoc_sign()).placeholder(R.drawable.doctor_autograph).into(ivDoctorAutograph);
                        Glide.with(Utils.getApp()).load(signProtocol.getUser_sign()).placeholder(R.drawable.patient_autograph).into(ivPatientAutograph);
                        tvTime.setText(signProtocol.getAddtime());
                    }
                });
    }

    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_signing_protocol, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.iv_group_photo, R.id.iv_doctor_autograph, R.id.iv_patient_autograph})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_group_photo:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageUrl(signProtocol.getGroup_photo())
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivGroupPhoto);
                break;
            case R.id.iv_doctor_autograph:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageUrl(signProtocol.getDoc_sign())
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivDoctorAutograph);
                break;
            case R.id.iv_patient_autograph:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageUrl(signProtocol.getUser_sign())
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivPatientAutograph);
                break;
        }
    }
}
