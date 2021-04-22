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
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.lihang.ShadowLayout;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.InHospitalDetailEntity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.engine.GlideImageEngine;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName:
 * @Package: com.vice.bloodpressure.ui.activity.familydoctor
 * @ClassName: HospitalizationAppointmentDetailActivity
 * @Description: 预约住院详情
 * @Author: zwk
 * @CreateDate: 2020/1/3 16:37
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/1/3 16:37
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class HospitalizationAppointmentDetailActivity extends BaseHandlerActivity {

    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.tv_six)
    TextView tvSix;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.tv_doctor_reply)
    TextView tvDoctorReply;
    @BindView(R.id.sl_reply)
    ShadowLayout slReply;

    int id;
    private InHospitalDetailEntity bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("预约住院");
        id = getIntent().getIntExtra("id", -1);
        initData();
    }

    private void initData() {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        RxHttpUtils.createApi(Service.class)
                .familyinhospitaldetail(loginBean.getToken(), id)
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<InHospitalDetailEntity>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<InHospitalDetailEntity> inHospitalDetailEntityBaseData) {
                        bean = inHospitalDetailEntityBaseData.getData();
                        tvOne.setText(bean.getName());
                        if ("1".equals(bean.getType())) {
                            tvTwo.setText("初次住院");
                        } else if ("2".equals(bean.getType())) {
                            tvTwo.setText("再次住院");
                        }
                        tvThree.setText(bean.getAge());
                        if ("1".equals(bean.getSex())) {
                            tvFour.setText("男");
                        } else if ("2".equals(bean.getSex())) {
                            tvFour.setText("女");
                        }
                        tvFive.setText(bean.getTelephone());
                        Date date = TimeUtils.string2Date(bean.getIntime(), "yyyy-MM-dd HH:mm");
                        String time = TimeUtils.date2String(date, "yyyy-MM-dd");
                        tvSix.setText(time);
                        tvDescribe.setText(bean.getDescribe());
                        tvDoctorReply.setText(bean.getReason());
                        if (bean.getPic().size() > 0) {
                            Glide.with(getPageContext()).load(bean.getPic().get(0)).placeholder(R.drawable.default_image).into(ivOne);
                        }
                        if (bean.getPic().size() > 1) {
                            Glide.with(getPageContext()).load(bean.getPic().get(1)).placeholder(R.drawable.default_image).into(ivTwo);
                        }
                        if (bean.getPic().size() > 2) {
                            Glide.with(getPageContext()).load(bean.getPic().get(2)).placeholder(R.drawable.default_image).into(ivThree);
                        }
                        if ("2".equals(bean.getStatus())) {
                            slReply.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_hospitalization_appointment_detail, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.iv_one, R.id.iv_two, R.id.iv_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_one:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageList((ArrayList<String>) bean.getPic())
                        .setCurrentPosition(0)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivOne);
                break;
            case R.id.iv_two:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageList((ArrayList<String>) bean.getPic())
                        .setCurrentPosition(1)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivTwo);
                break;
            case R.id.iv_three:
                MNImageBrowser.with(this)
                        .setImageEngine(new GlideImageEngine())
                        .setImageList((ArrayList<String>) bean.getPic())
                        .setCurrentPosition(2)
                        .setIndicatorHide(false)
                        .setFullScreenMode(true)
                        .show(ivThree);
                break;
        }
    }

}
