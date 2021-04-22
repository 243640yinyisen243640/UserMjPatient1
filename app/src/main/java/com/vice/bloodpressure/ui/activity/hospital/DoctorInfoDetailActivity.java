package com.vice.bloodpressure.ui.activity.hospital;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MyDoctorBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  医生详情 之解绑
 * 作者: LYD
 * 创建日期: 2020/12/16 11:25
 */
public class DoctorInfoDetailActivity extends BaseHandlerActivity {
    private static final int GET_DOCTOR_INFO = 10010;
    @BindView(R.id.iv_head)
    QMUIRadiusImageView ivHead;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_department_name)
    TextView tvDepartmentName;
    @BindView(R.id.tv_doctor_type)
    TextView tvDoctorType;
    @BindView(R.id.tv_un_bind)
    ColorTextView tvUnBind;
    @BindView(R.id.tv_doctor_desc)
    TextView tvDoctorDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("医生详情");
        getDoctorInfo();
    }

    /**
     * 获取医生详情
     */
    private void getDoctorInfo() {
        int docid = getIntent().getIntExtra("docid", 0);
        HashMap map = new HashMap<>();
        map.put("docid", docid);
        XyUrl.okPost(XyUrl.DOCTOR_DETAILS, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MyDoctorBean doctor = JSONObject.parseObject(value, MyDoctorBean.class);
                Message msg = Message.obtain();
                msg.obj = doctor;
                msg.what = GET_DOCTOR_INFO;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_doctor_info_detail, contentLayout, false);
        return view;
    }

    @OnClick(R.id.tv_un_bind)
    public void onViewClicked() {
        DialogUtils.getInstance().showCommonDialog(getPageContext(), "", "确定要解绑医生吗",
                "确定", "我再想想", new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        Map<String, Object> map = new HashMap<>();
                        XyUrl.okPostSave(XyUrl.UN_BIND_DOC, map, new OkHttpCallBack<String>() {
                            @Override
                            public void onSuccess(String value) {
                                ToastUtils.showShort(value);
                                ActivityUtils.finishToActivity(MainActivity.class, false);
                                //startActivity(new Intent(getPageContext(), MainActivity.class));
                                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_TO_OUTSIDE));
                                EventBusUtils.post(new EventMessage<>(ConstantParam.RONGIM_RED_POINT_REFRESH));
                            }

                            @Override
                            public void onError(int errorCode, final String errorMsg) {

                            }
                        });
                    }
                }, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {

                    }
                });
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DOCTOR_INFO:
                MyDoctorBean data = (MyDoctorBean) msg.obj;
                setDoctorInfo(data);
                break;
        }
    }

    private void setDoctorInfo(MyDoctorBean data) {
        String imgurl = data.getImgurl();
        String docname = data.getDocname();
        String depname = data.getDepname();
        String doczhc = data.getDoczhc();
        String contents = data.getContents();
        Glide.with(Utils.getApp()).load(imgurl).into(ivHead);
        tvDoctorName.setText(docname);
        tvDepartmentName.setText(depname);
        tvDoctorType.setText(doczhc);
        tvDoctorDesc.setText(contents);
    }
}