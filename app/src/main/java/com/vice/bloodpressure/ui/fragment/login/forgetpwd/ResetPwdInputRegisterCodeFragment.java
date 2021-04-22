package com.vice.bloodpressure.ui.fragment.login.forgetpwd;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
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
 * 描述:  输入验证码
 * 作者: LYD
 * 创建日期: 2019/9/20 20:14
 */
public class ResetPwdInputRegisterCodeFragment extends BaseFragment {
    private static final int GET_REGISTER_CODE = 10086;
    private static final int CHECK_REGISTER_CODE_SUCCESS = 10010;
    @BindView(R.id.tv_register_phone_number)
    TextView tvRegisterPhoneNumber;
    @BindView(R.id.et_input_register_code)
    EditText etInputRegisterCode;
    @BindView(R.id.tv_register_time)
    ColorTextView tvRegisterTime;
    @BindView(R.id.tv_sure)
    ColorTextView tvSure;
    private String phoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reset_pwd_input_register_code;
    }

    @Override
    protected void init(View rootView) {
        phoneNumber = getArguments().getString("phoneNumber");
        initCountDownTimer();
        setTextChangeListener();
    }

    /**
     * 设置监听
     */
    private void setTextChangeListener() {
        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() == 4) {
                    tvSure.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvSure.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                    tvSure.setEnabled(true);
                } else {
                    tvSure.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvSure.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                    tvSure.setEnabled(false);
                }
            }
        }, etInputRegisterCode);
    }


    /**
     * 初始化
     */
    private void initCountDownTimer() {
        tvRegisterPhoneNumber.setText("短信验证码已发送至 +86 " + phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11));
        ShowTimerUtils.showTimer(tvRegisterTime, 60, getPageContext());
    }


    /**
     * 获取验证码
     *
     * @param phoneNumber
     */
    private void getRegisterCode(String phoneNumber) {
        HashMap map = new HashMap<>();
        map.put("username", phoneNumber);
        map.put("type", ConstantParam.SendCodeType.REGISTER.getName());
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

    /**
     * 重新设置密码
     *
     * @param registerCode
     */
    private void toDoResetPwd(String registerCode) {
        String phoneNumber = getArguments().getString("phoneNumber");
        HashMap map = new HashMap<>();
        map.put("username", phoneNumber);
        map.put("code", registerCode);
        XyUrl.okPostSave(XyUrl.RESET_PWD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort("获取成功");

                Message message = getHandlerMessage();
                message.what = CHECK_REGISTER_CODE_SUCCESS;
                sendHandlerMessage(message);


            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });


    }

    @OnClick({R.id.img_back, R.id.tv_register_time, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.tv_register_time:
                getRegisterCode(phoneNumber);
                break;
            case R.id.tv_sure:
                String registerCode = etInputRegisterCode.getText().toString().trim();
                if (TextUtils.isEmpty(registerCode)) {
                    ToastUtils.showShort("请输入验证码");
                    return;
                }
                //修改为先校验验证码
                toDoResetPwd(registerCode);
                break;
        }
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_REGISTER_CODE:
                tvRegisterPhoneNumber.setText("短信验证码已发送至 +86 " + phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11));
                ShowTimerUtils.showTimer(tvRegisterTime, 60, getPageContext());
                break;
            case CHECK_REGISTER_CODE_SUCCESS:
                ResetPwdInputNewPwdFragment resetPwdInputNewPwdFragment = new ResetPwdInputNewPwdFragment();
                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber", phoneNumber);
                resetPwdInputNewPwdFragment.setArguments(bundle);
                FragmentUtils.replace(getParentFragmentManager(), resetPwdInputNewPwdFragment, R.id.fl_fragment, true);
                break;
        }
    }

}
