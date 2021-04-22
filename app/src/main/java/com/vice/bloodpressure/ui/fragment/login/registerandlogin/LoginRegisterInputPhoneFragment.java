package com.vice.bloodpressure.ui.fragment.login.registerandlogin;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:   注册
 * 作者: LYD
 * 创建日期: 2019/9/17 20:29
 */
public class LoginRegisterInputPhoneFragment extends BaseFragment {
    private static final int GET_REGISTER_CODE = 10086;
    @BindView(R.id.et_input_phone_number)
    EditText etInputPhoneNumber;
    @BindView(R.id.tv_next_step)
    ColorTextView tvNextStep;
    private String phoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_register_step_one;
    }

    @Override
    protected void init(View rootView) {
        setTextChangeListener();
    }

    /**
     * 设置监听
     */
    private void setTextChangeListener() {
        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() == 11) {
                    tvNextStep.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvNextStep.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                    tvNextStep.setEnabled(true);
                } else {
                    tvNextStep.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvNextStep.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                    tvNextStep.setEnabled(false);
                }
            }
        }, etInputPhoneNumber);
    }

    @OnClick({R.id.img_back, R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.tv_next_step:
                checkPhoneNumber();
                break;
        }
    }


    /**
     * 校验手机号
     */
    private void checkPhoneNumber() {
        phoneNumber = etInputPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileSimple(phoneNumber)) {
            ToastUtils.showShort("请输入合法的手机号");
            return;
        }
        getRegisterCode(phoneNumber);
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

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_REGISTER_CODE:
                LoginRegisterInputRegisterCodeFragment loginRegisterStepTwoFragment = new LoginRegisterInputRegisterCodeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber", phoneNumber);
                bundle.putInt("type", ConstantParam.SendCodeType.REGISTER.getName());
                loginRegisterStepTwoFragment.setArguments(bundle);
                FragmentUtils.replace(getParentFragmentManager(), loginRegisterStepTwoFragment, R.id.fl_fragment, true);
                break;
        }
    }
}
