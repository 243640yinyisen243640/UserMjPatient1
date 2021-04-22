package com.vice.bloodpressure.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.ShowTimerUtils;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 描述: 输入验证码
 * 作者: LYD
 * 创建日期: 2019/9/25 11:18
 */
public class UserPhoneInputRegisterCodeActivity extends BaseHandlerActivity {
    private static final int GET_REGISTER_CODE = 10010;
    @BindView(R.id.tv_register_phone_number)
    TextView tvRegisterPhoneNumber;
    @BindView(R.id.et_input_register_code)
    EditText etInputRegisterCode;
    @BindView(R.id.tv_register_time)
    ColorTextView tvRegisterTime;
    @BindView(R.id.tv_register)
    ColorTextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPageTitle();
        initCountDownTimer();
        setTextChangeListener();
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_user_phone_input_register_code, contentLayout, false);
        return view;
    }

    /**
     * 设置监听
     */
    private void setTextChangeListener() {
        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() > 0) {
                    tvRegister.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvRegister.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                } else {
                    tvRegister.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvRegister.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                }
            }
        }, etInputRegisterCode);
    }

    /**
     * 设置初次倒计时
     */
    private void initCountDownTimer() {
        String phoneNumber = getIntent().getStringExtra("phone");
        tvRegisterPhoneNumber.setText("短信验证码已发送至 +86 " + phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11));
        ShowTimerUtils.showTimer(tvRegisterTime, 60, getPageContext());
    }

    /**
     * 设置标题
     */
    private void setPageTitle() {
        //setWhiteTitleBar();
        String isBind = getIntent().getStringExtra("isBind");
        if ("0".equals(isBind)) {
            setTitle("绑定手机号");
        } else {
            setTitle("更换手机号");
        }
    }

    @OnClick({R.id.tv_register_time, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_time:
                //获取验证码
                String phone = getIntent().getStringExtra("phone");
                getRegisterCode(phone);
                break;
            case R.id.tv_register:
                //验证
                toDoBind();
                break;
        }
    }


    /**
     * 绑定
     */
    private void toDoBind() {
        String phone = getIntent().getStringExtra("phone");
        String registerCode = etInputRegisterCode.getText().toString().trim();
        HashMap map = new HashMap<>();
        map.put("mobile", phone);
        map.put("code", registerCode);
        XyUrl.okPostSave(XyUrl.BIND_PHONE_NUMBER, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //手机号绑定修改
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                user.setUsername(phone);
                SharedPreferencesUtils.putBean(getPageContext(), SharedPreferencesUtils.USER_INFO, user);
                //手机号绑定修改
                ToastUtils.showShort("获取成功");
                EventBusUtils.post(new EventMessage<>(ConstantParam.CHANG_BIND_PHONE, phone));
                //修改
                ActivityUtils.finishToActivity(AccountAndSafetyActivity.class, false);
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    private void getRegisterCode(String phone) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String username = user.getUsername();
        String isBind = getIntent().getStringExtra("isBind");
        String type;
        String oldUserName;
        if ("0".equals(isBind)) {
            type = "4";//绑定
            oldUserName = user.getIdcard();
        } else {
            type = "5";//更换
            oldUserName = username;
        }
        HashMap map = new HashMap<>();
        map.put("username", phone);
        map.put("oldusername", oldUserName);
        map.put("type", type);
        XyUrl.okPostSave(XyUrl.SEND_CODE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                Message message = getHandlerMessage();
                message.what = GET_REGISTER_CODE;
                sendHandlerMessage(message);
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
            case GET_REGISTER_CODE:
                String newPhoneNumber = etInputRegisterCode.getText().toString().trim();
                tvRegisterPhoneNumber.setText("短信验证码已发送至 +86 " + newPhoneNumber.substring(0, 3) + "****" + newPhoneNumber.substring(7, 11));
                ShowTimerUtils.showTimer(tvRegisterTime, 60, getPageContext());
                break;
        }
    }
}
