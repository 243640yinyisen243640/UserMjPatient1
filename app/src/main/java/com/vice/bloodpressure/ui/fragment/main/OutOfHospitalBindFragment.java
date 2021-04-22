package com.vice.bloodpressure.ui.fragment.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPStaticUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.NoticeAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.MyDoctorBean;
import com.vice.bloodpressure.bean.NoticeBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.followupvisit.FollowUpVisitListActivity;
import com.vice.bloodpressure.ui.activity.hospital.DepartmentListActivity;
import com.vice.bloodpressure.ui.activity.hospital.DoctorInfoDetailActivity;
import com.vice.bloodpressure.ui.activity.hospital.MakeListActivity;
import com.vice.bloodpressure.ui.activity.patienteducation.PatientEducationListActivity;
import com.vice.bloodpressure.utils.ImageLoaderUtil;
import com.vice.bloodpressure.view.MyListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.model.Conversation;

/**
 * 描述: 院外管理 已绑定
 * 作者: LYD
 * 创建日期: 2019/4/18 14:54
 */

public class OutOfHospitalBindFragment extends BaseFragment implements View.OnClickListener, IUnReadMessageObserver, SimpleImmersionOwner {
    private static final String TAG = "OutOfHospitalBindFragment";
    private static final int GET_DOCTOR_INFO = 10010;
    private static final int GET_NOTICE = 10011;
    private TextView tvDoctorName;//医生名
    private TextView tvDoctorCall;//职称
    private TextView tvHosName;//所属医院
    private TextView tvHosDepartment;//科室
    private ImageView ivDoctorImg;//头像
    private MyListView lvNotice;//头像
    private TextView tvRedPoint;
    private List<NoticeBean> departmentList;
    private MyDoctorBean doctor;
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_doctor;
    }

    @Override
    protected void init(View view) {
        FloatingView.get().remove();
        ivDoctorImg = view.findViewById(R.id.iv_doctor_img);
        lvNotice = view.findViewById(R.id.lv_notice);
        tvDoctorName = view.findViewById(R.id.tv_doctor_name);
        tvDoctorCall = view.findViewById(R.id.tv_doctor_call);
        tvHosName = view.findViewById(R.id.tv_hos_name);
        tvHosDepartment = view.findViewById(R.id.tv_hos_department);
        //问医生
        LinearLayout llAskDoctor = view.findViewById(R.id.ll_ask_doctor);
        llAskDoctor.setOnClickListener(this);
        //预约住院
        LinearLayout llMakeHospital = view.findViewById(R.id.ll_make_hospital);
        llMakeHospital.setOnClickListener(this);
        //医生建议
        LinearLayout llDoctorAdvice = view.findViewById(R.id.ll_doctor_advice);
        llDoctorAdvice.setOnClickListener(this);
        //随访管理
        LinearLayout llFollow = view.findViewById(R.id.ll_follow);
        llFollow.setOnClickListener(this);
        TextView tvLookMore = view.findViewById(R.id.tv_look_more);
        tvLookMore.setOnClickListener(this);
        ivDoctorImg.setOnClickListener(this);
        tvRedPoint = view.findViewById(R.id.tv_red_point);
        getImUnReadMsgCount();
        getDoctorInfo();
    }


    /**
     * 获取融云消息未读数量
     */
    private void getImUnReadMsgCount() {
        final Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.PRIVATE};
        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);
    }


    /**
     * 获取医生详情
     */
    private void getDoctorInfo() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.MY_DOCTOR, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                doctor = JSONObject.parseObject(value, MyDoctorBean.class);
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

    /**
     * 科室公告
     */
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("docid", doctor.getDocid());
        XyUrl.okPost(XyUrl.DEPARTMENT_FIVE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                departmentList = JSONObject.parseArray(value, NoticeBean.class);
                Message msg = Message.obtain();
                msg.obj = departmentList;
                msg.what = GET_NOTICE;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_doctor_img:
                intent = new Intent(getPageContext(), DoctorInfoDetailActivity.class);
                intent.putExtra("docid", doctor.getDocid());
                startActivity(intent);
                break;
            case R.id.ll_ask_doctor://问医生
                RongIM.getInstance().startPrivateChat(getActivity(), doctor.getDocid() + "", doctor.getDocname());
                break;
            case R.id.ll_doctor_advice://医生建议
                intent = new Intent(getActivity(), PatientEducationListActivity.class);
                intent.putExtra("docId", doctor.getDocid() + "");
                startActivity(intent);
                break;
            case R.id.ll_make_hospital://预约住院
                intent = new Intent(getActivity(), MakeListActivity.class);
                intent.putExtra("doctorId", doctor.getDocid() + "");
                startActivity(intent);
                break;
            case R.id.ll_follow://随访管理
                intent = new Intent(getActivity(), FollowUpVisitListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_look_more://科室公告
                intent = new Intent(getActivity(), DepartmentListActivity.class);
                intent.putExtra("doctor", doctor);
                startActivity(intent);
                break;
        }
    }

    //SimpleImmersionOwner接口实现

    @Override
    public void onCountChanged(int count) {
        if (count > 0) {
            tvRedPoint.setVisibility(View.VISIBLE);
        } else {
            tvRedPoint.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).statusBarColor(R.color.transparent).init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_NOTICE://科室公告
                departmentList = (List<NoticeBean>) msg.obj;
                lvNotice.setAdapter(new NoticeAdapter(getActivity(), R.layout.item_notice, departmentList));
                break;
            case GET_DOCTOR_INFO:
                doctor = (MyDoctorBean) msg.obj;
                //docid = doctor.getDocid();
                ImageLoaderUtil.loadCircleImg(ivDoctorImg, doctor.getImgurl(), R.drawable.default_doctor_head);
                tvDoctorName.setText(doctor.getDocname());
                tvDoctorCall.setText(doctor.getDoczhc());
                tvHosName.setText(doctor.getHospitalname());
                tvHosDepartment.setText(doctor.getDepname());
                SPStaticUtils.put("docName", doctor.getDocname());
                SPStaticUtils.put("docImg", doctor.getImgurl());
                getData();
                break;
        }
    }
}
