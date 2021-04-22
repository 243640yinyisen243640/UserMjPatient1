package com.vice.bloodpressure.ui.activity.homesign;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.SignDoctorInfoBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  家庭医生详情
 * 作者: LYD
 * 创建日期: 2020/1/3 9:20
 */
public class HomeDoctorDetailActivity extends BaseHandlerActivity {
    private static final int GET_DETAIL = 10086;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_doctor_profession)
    TextView tvDoctorProfession;
    @BindView(R.id.tv_doctor_good_mark)
    TextView tvDoctorGoodMark;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_skill_desc)
    TextView tvSkillDesc;
    @BindView(R.id.tv_quick_sign)
    ColorTextView tvQuickSign;
    private SignDoctorInfoBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String doctorName = getIntent().getStringExtra("doctor_name");
        setTitle(doctorName);
        getDoctorInfoDetail();
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_home_doctor_detail, contentLayout, false);
        return view;
    }


    /**
     * 获取医生详情
     */
    private void getDoctorInfoDetail() {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        int doctor_id = getIntent().getIntExtra("doctor_id", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", loginBean.getToken());
        map.put("docid", doctor_id);
        XyUrl.okPost(XyUrl.SIGN_DOCTOR_INFO, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SignDoctorInfoBean data = JSONObject.parseObject(value, SignDoctorInfoBean.class);
                Message msg = Message.obtain();
                msg.what = GET_DETAIL;
                msg.obj = data;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DETAIL:
                data = (SignDoctorInfoBean) msg.obj;
                String imgurl = data.getImgurl();
                Glide.with(Utils.getApp()).
                        load(imgurl)
                        .placeholder(R.drawable.default_doctor_head)
                        .error(R.drawable.default_doctor_head)
                        .into(imgHead);
                String docname = data.getDocname();
                tvDoctorName.setText(docname);
                String doczhc = data.getDoczhc();
                tvDoctorProfession.setText(doczhc);
                String address = data.getAddress();
                tvAddress.setText(address);
                String signRate = data.getSigning_rate();
                tvDoctorGoodMark.setText(signRate + "%");
                String specialty = data.getSpecialty() == null ? "" : data.getSpecialty();
                String contents = data.getContents() == null ? "" : data.getContents();
                SpanUtils.with(tvSkillDesc)
                        .append("【擅长】").setForegroundColor(ColorUtils.getColor(R.color.black_text)).setFontSize(16, true)
                        .append(specialty).setForegroundColor(ColorUtils.getColor(R.color.gray_text)).setFontSize(14, true)
                        .append("\n\n")
                        .append("【简介】").setForegroundColor(ColorUtils.getColor(R.color.black_text)).setFontSize(16, true)
                        .append(contents).setForegroundColor(ColorUtils.getColor(R.color.gray_text)).setFontSize(14, true)
                        .create();
                break;
        }
    }

    @OnClick(R.id.tv_quick_sign)
    public void onViewClicked() {
        Intent intent = new Intent(this, QuickSignAddActivity.class);
        intent.putExtra("doctor_id", data.getDocid());
        startActivity(intent);
    }
}
