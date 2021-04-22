package com.vice.bloodpressure.ui.fragment.login.quicklogin;


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
import com.vice.bloodpressure.ui.fragment.login.registerandlogin.LoginRegisterInputRegisterCodeFragment;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 快速登录输入手机号
 * 作者: LYD
 * 创建日期: 2019/9/21 7:13
 */
public class QuickLoginFragment extends BaseFragment {
    private static final int GET_REGISTER_CODE = 10086;
    @BindView(R.id.et_input_phone_number)
    EditText etInputPhoneNumber;
    @BindView(R.id.tv_get_register_code)
    ColorTextView tvGetRegisterCode;
    //手机号
    private String phoneNumber;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quick_login_input_phone_number;
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
                    tvGetRegisterCode.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvGetRegisterCode.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                    tvGetRegisterCode.setEnabled(true);
                } else {
                    tvGetRegisterCode.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvGetRegisterCode.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                    tvGetRegisterCode.setEnabled(false);
                }
            }
        }, etInputPhoneNumber);
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
        map.put("type", ConstantParam.SendCodeType.QUICK_LOGIN.getName());
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


    @OnClick({R.id.tv_get_register_code, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.tv_get_register_code:
                checkPhoneNumber();
                break;
        }
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_REGISTER_CODE:
                LoginRegisterInputRegisterCodeFragment loginRegisterStepTwoFragment = new LoginRegisterInputRegisterCodeFragment();
                //Fragment传递值到Fragment开始
                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber", phoneNumber);
                bundle.putInt("type", ConstantParam.SendCodeType.QUICK_LOGIN.getName());
                loginRegisterStepTwoFragment.setArguments(bundle);
                //Fragment传递值到Fragment结束
                FragmentUtils.replace(getParentFragmentManager(), loginRegisterStepTwoFragment, R.id.fl_fragment, true);
                break;
        }
    }

}
