package com.vice.bloodpressure.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.RegexUtils;
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
 * 描述:修改和绑定手机号
 * 作者:LYD
 * 创建日期:2019/9/12 11:04
 */
public class ChangeOrBindPhoneNumberActivity extends BaseHandlerActivity {
    private static final String TAG = "ChangeOrBindPhoneNumberActivity";
    private static final int GET_REGISTER_CODE = 10010;
    //第一步开始
    @BindView(R.id.et_old_phone_number)
    EditText etOldPhoneNumber;
    @BindView(R.id.tv_next_step)
    ColorTextView tvNextStep;
    @BindView(R.id.ll_input_old_phone_number)
    LinearLayout llInputOldPhoneNumber;
    //第一步结束

    //第二步开始
    @BindView(R.id.et_new_phone_number)
    EditText etNewPhoneNumber;
    @BindView(R.id.tv_get_register_code)
    ColorTextView tvGetRegisterCode;
    @BindView(R.id.ll_input_new_phone_number)
    LinearLayout llInputNewPhoneNumber;
    //第二步结束

    //第三步开始
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;
    @BindView(R.id.tv_register_phone)
    TextView tvRegisterPhone;
    @BindView(R.id.tv_register_time)
    ColorTextView tvRegisterTime;
    @BindView(R.id.tv_submit)
    ColorTextView tvSubmit;
    @BindView(R.id.ll_input_register_code)
    LinearLayout llInputRegisterCode;
    //第三步结束

    private String oldPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setTextChangeListener();
    }

    /**
     * 设置监听
     */
    private void setTextChangeListener() {
        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() > 0) {
                    tvNextStep.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvNextStep.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                } else {
                    tvNextStep.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvNextStep.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                }
            }
        }, etOldPhoneNumber);

        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() > 0) {
                    tvGetRegisterCode.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvGetRegisterCode.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                } else {
                    tvGetRegisterCode.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvGetRegisterCode.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                }
            }
        }, etNewPhoneNumber);


        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() > 0) {
                    tvSubmit.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvSubmit.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                } else {
                    tvSubmit.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvSubmit.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                }
            }
        }, etRegisterCode);
    }

    /**
     * 初始化页面
     */
    private void init() {
        String isBind = getIntent().getStringExtra("isBind");
        if ("0".equals(isBind)) {
            setTitle("绑定手机号");
            llInputOldPhoneNumber.setVisibility(View.GONE);
            llInputNewPhoneNumber.setVisibility(View.VISIBLE);
            etNewPhoneNumber.setHint("请输入手机号");
            tvGetRegisterCode.setText("下一步");
        } else {
            setTitle("修改手机号");
            llInputOldPhoneNumber.setVisibility(View.VISIBLE);
            llInputNewPhoneNumber.setVisibility(View.GONE);
            etNewPhoneNumber.setHint("请输入新手机号");
            tvGetRegisterCode.setText("获取验证码");
        }
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_change_or_bind_phone_number, contentLayout, false);
        return view;
    }


    @OnClick({R.id.tv_next_step, R.id.tv_get_register_code, R.id.tv_submit, R.id.tv_register_time})
    public void onViewClicked(View view) {
        String newPhoneNumber = etNewPhoneNumber.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_next_step://下一步
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);

                oldPhoneNumber = etOldPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(oldPhoneNumber)) {
                    ToastUtils.showShort("请输入原手机号");
                    return;
                }
                if (!RegexUtils.isMobileSimple(oldPhoneNumber)) {
                    ToastUtils.showShort("请输入合法的手机号");
                    return;
                }
                //判断原手机号和输入手机号是否一致
                String username = user.getUsername();
                if (!oldPhoneNumber.equals(username)) {
                    ToastUtils.showShort("原手机号不正确");
                    return;
                }
                llInputOldPhoneNumber.setVisibility(View.GONE);
                llInputNewPhoneNumber.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_get_register_code://获取验证码
                if (TextUtils.isEmpty(newPhoneNumber)) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }
                if (!RegexUtils.isMobileSimple(newPhoneNumber)) {
                    ToastUtils.showShort("请输入合法的手机号");
                    return;
                }
                getRegisterCode(newPhoneNumber);
                break;
            case R.id.tv_submit://验证
                String registerCode = etRegisterCode.getText().toString().trim();
                if (TextUtils.isEmpty(registerCode)) {
                    ToastUtils.showShort("请输入验证码");
                    return;
                }
                toDoBind();
                break;
            case R.id.tv_register_time:
                getRegisterCode(newPhoneNumber);
                break;
        }
    }


    /**
     * 绑定
     */
    private void toDoBind() {
        String newPhoneNumber = etNewPhoneNumber.getText().toString().trim();
        String registerCode = etRegisterCode.getText().toString().trim();
        HashMap map = new HashMap<>();
        map.put("mobile", newPhoneNumber);
        map.put("code", registerCode);
        XyUrl.okPostSave(XyUrl.BIND_PHONE_NUMBER, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //身份证号绑定修改
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                user.setUsername(newPhoneNumber);
                SharedPreferencesUtils.putBean(getPageContext(), SharedPreferencesUtils.USER_INFO, user);
                //身份证号绑定修改
                ToastUtils.showShort("获取成功");
                EventBusUtils.post(new EventMessage<>(ConstantParam.CHANG_BIND_PHONE, newPhoneNumber));
                finish();
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
     * @param newPhoneNumber
     */
    private void getRegisterCode(String newPhoneNumber) {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String isBind = getIntent().getStringExtra("isBind");
        String type;
        String oldUserName;
        if ("0".equals(isBind)) {
            type = "4";//绑定
            oldUserName = user.getIdcard();
        } else {
            type = "5";//更换
            oldUserName = oldPhoneNumber;
        }
        HashMap map = new HashMap<>();
        map.put("username", newPhoneNumber);
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
                llInputNewPhoneNumber.setVisibility(View.GONE);
                llInputRegisterCode.setVisibility(View.VISIBLE);
                String newPhoneNumber = etNewPhoneNumber.getText().toString().trim();
                tvRegisterPhone.setText("短信验证码已发送至 +86 " + newPhoneNumber.substring(0, 3) + "****" + newPhoneNumber.substring(7, 11));
                ShowTimerUtils.showTimer(tvRegisterTime, 60, getPageContext());
                break;
        }
    }
}