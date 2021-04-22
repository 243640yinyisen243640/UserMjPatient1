package com.vice.bloodpressure.ui.fragment.login.registerandlogin;

import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.RegisterQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.ui.activity.user.RegisterQuestionActivity;
import com.vice.bloodpressure.utils.SPUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.ShowTimerUtils;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: 输入验证码
 * 作者: LYD
 * 创建日期: 2019/9/18 9:23
 */
public class LoginRegisterInputRegisterCodeFragment extends BaseFragment {
    private static final String TAG = "LoginRegisterInputRegisterCodeFragment";
    private static final int GET_REGISTER_CODE = 10086;
    private static final int LOGIN_SUCCESS = 10010;
    private static final int BIND_PHONE_NUMBER_SUCCESS = 10000;

    @BindView(R.id.tv_register_phone_number)
    TextView tvRegisterPhoneNumber;
    @BindView(R.id.et_input_register_code)
    EditText etInputRegisterCode;
    @BindView(R.id.tv_register_time)
    ColorTextView tvRegisterTime;
    @BindView(R.id.tv_register)
    ColorTextView tvRegister;

    private String phoneNumber;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_register_step_two;
    }

    @Override
    protected void init(View rootView) {
        phoneNumber = getArguments().getString("phoneNumber");
        type = getArguments().getInt("type", 0);
        switch (type) {
            case 1://注册
                tvRegister.setText("注册");
                break;
            case 2://快速登录
                tvRegister.setText("登录");
                break;
            case 4://绑定手机
                tvRegister.setText("绑定");
                break;
        }
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
                    tvRegister.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvRegister.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                    tvRegister.setEnabled(true);
                } else {
                    tvRegister.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvRegister.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                    tvRegister.setEnabled(false);
                }
            }
        }, etInputRegisterCode);
    }


    /**
     * 设置初次倒计时
     */
    private void initCountDownTimer() {
        tvRegisterPhoneNumber.setText("短信验证码已发送至 +86 " + phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11));
        ShowTimerUtils.showTimer(tvRegisterTime, 60, getPageContext());
    }


    @OnClick({R.id.img_back, R.id.tv_register_time, R.id.tv_register, R.id.tv_register_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.tv_register_time:
                getRegisterCode(phoneNumber);
                break;
            case R.id.tv_register:
                String registerCode = etInputRegisterCode.getText().toString().trim();
                if (TextUtils.isEmpty(registerCode)) {
                    ToastUtils.showShort("请输入验证码");
                    return;
                }

                switch (type) {
                    case 1://注册
                        toDoRegister(registerCode);
                        break;
                    case 2://快速登录
                        toDoRegister(registerCode);
                        break;
                    case 4://绑定手机
                        toBindPhoneNumber(registerCode);
                        break;
                }
                break;
            case R.id.tv_register_agreement:
                Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "用户协议");
                intent.putExtra("url", "file:////android_asset/user_protocol.html");
                startActivity(intent);
                break;
        }
    }


    /**
     * 注册
     *
     * @param registerCode
     */
    private void toDoRegister(String registerCode) {
        HashMap map = new HashMap<>();
        map.put("username", phoneNumber);
        map.put("code", registerCode);
        XyUrl.okPost(XyUrl.LOGIN_NEW, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                LoginBean loginBean = JSONObject.parseObject(value, LoginBean.class);
                Message message = getHandlerMessage();
                message.what = LOGIN_SUCCESS;
                message.obj = loginBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });


    }

    /**
     * 绑定手机号
     *
     * @param registerCode
     */
    private void toBindPhoneNumber(String registerCode) {
        HashMap map = new HashMap<>();
        map.put("mobile", phoneNumber);
        map.put("code", registerCode);
        XyUrl.okPostSave(XyUrl.BIND_PHONE_NUMBER, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                Message message = getHandlerMessage();
                message.what = BIND_PHONE_NUMBER_SUCCESS;
                sendHandlerMessage(message);
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
     * @param phoneNumber
     */
    private void getRegisterCode(String phoneNumber) {
        HashMap map = new HashMap<>();
        map.put("username", phoneNumber);
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


    /**
     * 检测是否绑定身份证号或者设置密码
     *
     * @param loginBean
     */
    private void checkIsBindIdCardOrPwd(LoginBean loginBean) {
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


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_REGISTER_CODE:
                tvRegisterPhoneNumber.setText("短信验证码已发送至 +86 " + phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11));
                ShowTimerUtils.showTimer(tvRegisterTime, 60, getPageContext());
                break;
            case LOGIN_SUCCESS:
                LoginBean loginBean = (LoginBean) msg.obj;
                //保存登录信息
                SharedPreferencesUtils.putBean(getPageContext(), SharedPreferencesUtils.USER_INFO, loginBean);
                //检查是否绑定身份证和密码和答题
                checkIsBindIdCardOrPwd(loginBean);
                break;
            case BIND_PHONE_NUMBER_SUCCESS:
                //身份证号绑定修改
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
                user.setUsername(phoneNumber);
                SharedPreferencesUtils.putBean(getPageContext(), SharedPreferencesUtils.USER_INFO, user);
                //身份证号绑定修改
                checkIsBindIdCardOrPwd(user);
                break;
        }
    }
}
