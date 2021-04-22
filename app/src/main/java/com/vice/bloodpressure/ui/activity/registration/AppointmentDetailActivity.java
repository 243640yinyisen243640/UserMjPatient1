package com.vice.bloodpressure.ui.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.AppointmentDetailBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.hospital.checkdata.CheckDataListActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.wei.android.lib.colorview.helper.ColorTextViewHelper;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 描述:   预约详情
 * 作者: LYD
 * 创建日期: 2019/10/21 17:56
 */
public class AppointmentDetailActivity extends BaseHandlerActivity implements View.OnClickListener {
    private static final int GET_DETAIL = 10010;
    private static final int CANCEL_SUCCESS = 10086;
    @BindView(R.id.iv_state)
    ImageView imgState;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_hospital_name)
    TextView tvHospitalName;
    @BindView(R.id.tv_hospital_department)
    TextView tvHospitalDepartment;
    @BindView(R.id.tv_doctor_name)
    TextView tvDoctorName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_patient_name)
    TextView tvPatientName;
    @BindView(R.id.tv_id_number)
    TextView tvIdNumber;
    @BindView(R.id.tv_appoint_number)
    TextView tvAppointNumber;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    private ColorTextView tvMore;

    private String username;
    private String ofid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        String schid = getIntent().getStringExtra("schid");
        getDetail(schid);
        //initStatusBar(this, true, true, R.color.main_home);

        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.main_home).init();
    }


    /**
     * 获取详情
     *
     * @param schid
     */
    private void getDetail(String schid) {
        HashMap map = new HashMap<>();
        map.put("schid", schid);
        XyUrl.okPost(XyUrl.GET_SCHEDULING, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                AppointmentDetailBean data = JSONObject.parseObject(value, AppointmentDetailBean.class);
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = GET_DETAIL;
                handlerMessage.obj = data;
                sendHandlerMessage(handlerMessage);
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });

    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        setTitle("");
        setTitleBarBg(ColorUtils.getColor(R.color.main_home));
        Button btBack = getBack();
        btBack.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_base_back, 0, 0, 0);

        getLlMore().removeAllViews();
        tvMore = new ColorTextView(getPageContext());
        tvMore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tvMore.setPadding(10, 5, 10, 5);
        getLlMore().addView(tvMore);
        ColorTextViewHelper helper = tvMore.getColorHelper();
        helper.setTextColorNormal(ColorUtils.getColor(R.color.white_text));
        helper.setBorderColorNormal(ColorUtils.getColor(R.color.white_text));
        int borderWidth = ConvertUtils.dp2px(1);
        int radius = ConvertUtils.dp2px(2);
        helper.setBorderWidthNormal(borderWidth);
        helper.setCornerRadiusBottomLeftNormal(radius);
        helper.setCornerRadiusBottomRightNormal(radius);
        helper.setCornerRadiusTopLeftNormal(radius);
        helper.setCornerRadiusTopRightNormal(radius);
        helper.setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
        tvMore.setVisibility(View.GONE);
        getLlMore().setOnClickListener(this);
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_appointment_detail, contentLayout, false);
        return view;
    }


    /**
     * 设置数据
     *
     * @param data
     */
    private void setData(AppointmentDetailBean data) {
        //预约状态  1 预约成功  2 已取消  3 已过期  4 已就诊
        int status = data.getStatus();
        switch (status) {
            case 1:
                imgState.setImageResource(R.drawable.appoint_success);
                tvState.setText("预约成功");
                tvMore.setText("取消预约");
                tvMore.setVisibility(View.VISIBLE);
                break;
            case 2:
                imgState.setImageResource(R.drawable.appoint_cancel);
                tvState.setText("已取消");
                tvMore.setVisibility(View.GONE);
                break;
            case 3:
                imgState.setImageResource(R.drawable.appoint_out_date);
                tvState.setText("已过期");
                tvMore.setVisibility(View.GONE);
                break;
            case 4:
                imgState.setImageResource(R.drawable.appoint_see_a_doctor);
                tvState.setText("已就诊");
                tvMore.setText("查看结果");
                tvMore.setVisibility(View.VISIBLE);
                break;
        }
        //第一部分
        String hospitalname = data.getHospitalname();
        String depname = data.getDepname();
        String docname = data.getDocname();
        String schday = data.getSchday();
        String username = data.getUsername();
        String idcard = data.getIdcard();
        //第二部分
        String schnum = data.getSchnum();
        String addtime = data.getAddtime();
        //第一部分设置
        tvHospitalName.setText("医院：" + hospitalname);
        tvHospitalDepartment.setText("科室：" + depname);
        tvDoctorName.setText("医生：" + docname);
        tvTime.setText("日期：" + schday);
        tvPatientName.setText("就诊人：" + username);
        tvIdNumber.setText("身份证号：" + idcard);
        //第二部分设置
        tvAppointNumber.setText("预约编号：" + schnum);
        tvOrderTime.setText("下单时间：" + addtime);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //查看结果
            case R.id.ll_more:
                String text = tvMore.getText().toString().trim();
                if ("取消预约".endsWith(text)) {
                    showCancelDialog();
                } else {
                    Intent intent = new Intent(getPageContext(), CheckDataListActivity.class);
                    intent.putExtra("name", username);
                    intent.putExtra("ofid", ofid);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 显示取消弹窗
     */
    private void showCancelDialog() {
        DialogUtils.getInstance().showDialog(getPageContext(), "亲，确定取消预约吗？", "最多一天取消两次!", true, new DialogUtils.DialogCallBack() {
            @Override
            public void execEvent() {
                doCancelAppointment();
            }
        });
    }

    /**
     * 取消预约
     */
    private void doCancelAppointment() {
        String schid = getIntent().getStringExtra("schid");
        HashMap map = new HashMap<>();
        map.put("schid", schid);
        map.put("status", 2);
        XyUrl.okPostSave(XyUrl.CANCEL_SCHEDULE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String msg) {
                //ToastUtils.showShort(msg);
                //getDetail(schid);
                Message handlerMessage = getHandlerMessage();
                handlerMessage.what = CANCEL_SUCCESS;
                sendHandlerMessage(handlerMessage);
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
            case GET_DETAIL:
                AppointmentDetailBean data = (AppointmentDetailBean) msg.obj;
                username = data.getUsername();
                ofid = data.getSchuser();
                setData(data);
                break;
            case CANCEL_SUCCESS:
                String schid = getIntent().getStringExtra("schid");
                getDetail(schid);
                //发消息刷新
                EventBusUtils.post(new EventMessage<>(ConstantParam.APPOINT_REFRESH));
                break;
        }
    }
}
