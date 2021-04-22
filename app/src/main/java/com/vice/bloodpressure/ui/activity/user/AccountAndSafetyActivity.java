package com.vice.bloodpressure.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * 描述: 账号与安全
 * 作者: LYD
 * 创建日期: 2019/9/11 9:36
 */
public class AccountAndSafetyActivity extends BaseHandlerEventBusActivity {
    @BindView(R.id.tv_id_number)
    TextView tvIdNumber;
    @BindView(R.id.ll_look_id_number)
    LinearLayout llLookIdNumber;

    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.ll_set_phone_number)
    LinearLayout llSetPhoneNumber;

    @BindView(R.id.ll_set_pwd)
    LinearLayout llSetPwd;
    @BindView(R.id.tv_bind_or_look_id_card)
    TextView tvBindOrLookIdCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("账号与安全");
        setIdAndPhoneNumber();
    }

    /**
     * 设置身份证和手机号
     */
    private void setIdAndPhoneNumber() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String idcard = user.getIdcard();
        String username = user.getUsername();
        setIdNumber(idcard);
        setPhoneNumber(username);
    }

    /**
     * 设置手机号
     *
     * @param username
     */
    private void setPhoneNumber(String username) {
        if (TextUtils.isEmpty(username)) {
            tvPhoneNumber.setText("未绑定手机号");
        } else if (11 == username.length()) {
            tvPhoneNumber.setText(username.substring(0, 3) + "****" + username.substring(7, 11));
        }
    }

    /**
     * 设置身份证
     *
     * @param idcard
     */
    private void setIdNumber(String idcard) {
        if (TextUtils.isEmpty(idcard)) {
            tvIdNumber.setText("未绑定身份证号");
            tvBindOrLookIdCard.setText("绑定");
        } else if (18 == idcard.length()) {
            tvIdNumber.setText(idcard.substring(0, 10) + "****" + idcard.substring(14, 18));
            tvBindOrLookIdCard.setText("查看");
        }
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_account_and_safety, contentLayout, false);
        return view;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick({R.id.ll_look_id_number, R.id.ll_set_phone_number, R.id.ll_set_pwd, R.id.bt_exit})
    public void onViewClicked(View view) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        switch (view.getId()) {
            case R.id.ll_look_id_number://查看身份证
                //判断是否绑定身份证
                String idcard = user.getIdcard();
                if (TextUtils.isEmpty(idcard)) {
                    startActivity(new Intent(getPageContext(), BindIdNumberActivity.class));
                } else {
                    ToastUtils.showShort("身份证号已绑定,只可查看,不可修改");
                }
                break;
            case R.id.ll_set_phone_number://设置手机号
                //判断是否设置过手机号 0否1是  (username是否为空)
                String username = user.getUsername();
                if (TextUtils.isEmpty(username)) {
                    //未绑定
                    Intent intent = new Intent(getPageContext(), UserPhoneBindOrNoActivity.class);
                    intent.putExtra("isBind", "0");
                    startActivity(intent);
                } else {
                    //绑定
                    Intent intent = new Intent(getPageContext(), UserPhoneBindOrNoActivity.class);
                    intent.putExtra("isBind", "1");
                    startActivity(intent);
                }
                break;
            case R.id.ll_set_pwd://设置密码
                startActivity(new Intent(getPageContext(), ChangePwdActivity.class));
                break;
            case R.id.bt_exit:
                RongIM.getInstance().logout();
                DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要退出登录?", true, () -> {
                    CloudPushService pushService = PushServiceFactory.getCloudPushService();
                    pushService.unbindAccount(new CommonCallback() {
                        @Override
                        public void onSuccess(String s) {

                        }

                        @Override
                        public void onFailed(String s, String s1) {

                        }
                    });
                    ToastUtils.showShort("请登陆");
                    //SPStaticUtils.clear();
                    SharedPreferencesUtils.clear();
                    SPUtils.clear();
                    ActivityUtils.finishAllActivities();
                    Intent intent = new Intent(Utils.getApp(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                });
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.CHANG_BIND_PHONE:
                String phone = event.getMsg();
                setPhoneNumber(phone);
                break;
            case ConstantParam.BIND_ID_NUMBER:
                String idcard = event.getMsg();
                setIdNumber(idcard);
                break;
        }
    }
}
