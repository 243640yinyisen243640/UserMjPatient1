package com.vice.bloodpressure.ui.fragment.login.phoneoridcardlogin;

import android.content.Intent;
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
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.RegisterQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.ui.activity.user.RegisterQuestionActivity;
import com.vice.bloodpressure.ui.fragment.login.registerandlogin.LoginBindIdCardFragment;
import com.vice.bloodpressure.ui.fragment.login.registerandlogin.LoginBindPwdFragment;
import com.vice.bloodpressure.ui.fragment.login.registerandlogin.LoginRegisterInputRegisterCodeFragment;
import com.vice.bloodpressure.utils.SPUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  绑定手机号
 * 作者: LYD
 * 创建日期: 2019/9/21 7:48
 */
public class LoginBindPhoneNumberFragment extends BaseFragment {
    private static final int GET_REGISTER_CODE = 10086;

    @BindView(R.id.et_input_phone_number)
    EditText etInputPhoneNumber;
    @BindView(R.id.tv_next_step)
    ColorTextView tvNextStep;

    private String phoneNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_bind_phone_number;
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

    /**
     * 检测是否绑定身份证号或者设置密码
     */
    private void checkIsBindIdCardOrPwd() {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
        String idcard = loginBean.getIdcard();//身份证号
        String password = loginBean.getPassword();//1第一次注册登录，未设置密码  2已设置密码,
        String is_one = loginBean.getIs_one();//1:未答题2:已答题
        if (TextUtils.isEmpty(idcard)) {
            LoginBindIdCardFragment loginBindIdCaraFragment = new LoginBindIdCardFragment();
            FragmentUtils.replace(getParentFragmentManager(), loginBindIdCaraFragment, R.id.fl_fragment, true);
        } else if ("1".equals(password)) {
            LoginBindPwdFragment loginBindPwdFragment = new LoginBindPwdFragment();
            FragmentUtils.replace(getParentFragmentManager(), loginBindPwdFragment, R.id.fl_fragment, true);
        } else if ("1".equals(is_one)) {
            //去答题
            //ProblemOneFragment problemOneFragment = new ProblemOneFragment();
            //FragmentUtils.replace(getFragmentManager(), problemOneFragment, R.id.fl_fragment, true);
            RegisterQuestionAddBean bean = new RegisterQuestionAddBean();
            SPUtils.putBean(ConstantParam.REGISTER_QUESTION_KEY, bean);
            Intent intent = new Intent(getPageContext(), RegisterQuestionActivity.class);
            startActivity(intent);
        } else {
            //进首页
            //SharedPreferencesUtils.saveAutoLogin(getActivity(), true);
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
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
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
        String idcard = user.getIdcard();
        HashMap map = new HashMap<>();
        map.put("username", phoneNumber);
        map.put("oldusername", idcard);
        map.put("type", ConstantParam.SendCodeType.BIND_PHONE_NUMBER.getName());
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


    @OnClick({R.id.img_back, R.id.tv_jump, R.id.tv_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.tv_jump:
                //跳过判断
                checkIsBindIdCardOrPwd();
                break;
            case R.id.tv_next_step:
                //下一步判断
                checkPhoneNumber();
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_REGISTER_CODE:
                LoginRegisterInputRegisterCodeFragment loginRegisterStepTwoFragment = new LoginRegisterInputRegisterCodeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber", phoneNumber);
                bundle.putInt("type", ConstantParam.SendCodeType.BIND_PHONE_NUMBER.getName());
                loginRegisterStepTwoFragment.setArguments(bundle);
                FragmentUtils.replace(getParentFragmentManager(), loginRegisterStepTwoFragment, R.id.fl_fragment, true);
                break;
        }
    }
}
