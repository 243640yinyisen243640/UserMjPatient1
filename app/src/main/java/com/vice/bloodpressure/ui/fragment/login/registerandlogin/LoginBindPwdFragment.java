package com.vice.bloodpressure.ui.fragment.login.registerandlogin;

import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
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
import com.vice.bloodpressure.utils.SPUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  设置密码
 * 作者: LYD
 * 创建日期: 2019/9/18 17:41
 */
public class LoginBindPwdFragment extends BaseFragment {
    private static final int SET_PWD_SUCCESS = 10010;

    @BindView(R.id.et_input_pwd)
    EditText etInputPwd;
    @BindView(R.id.et_input_pwd_repeat)
    EditText etInputPwdRepeat;

    @BindView(R.id.tv_sure)
    ColorTextView tvSure;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_bind_pwd;
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
                if (etString.length() > 0) {
                    tvSure.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvSure.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                } else {
                    tvSure.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvSure.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                }
            }
        }, etInputPwd, etInputPwdRepeat);
    }


    @OnClick({R.id.img_back, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.tv_sure:
                String pwd = etInputPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                String pwdRepeat = etInputPwdRepeat.getText().toString().trim();
                if (TextUtils.isEmpty(pwdRepeat)) {
                    ToastUtils.showShort("请确认密码");
                    return;
                }
                if (!pwd.equals(pwdRepeat)) {
                    ToastUtils.showShort("两次输入密码不一致");
                    return;
                }
                toSetPwd(pwd, pwdRepeat);
                break;
        }
    }


    /**
     * 设置密码
     *
     * @param pwd
     * @param pwdRepeat
     */
    private void toSetPwd(String pwd, String pwdRepeat) {
        HashMap map = new HashMap<>();
        map.put("password", pwd);
        map.put("repassword", pwdRepeat);
        XyUrl.okPostSave(XyUrl.SET_PWD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                Message message = getHandlerMessage();
                message.what = SET_PWD_SUCCESS;
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
            case SET_PWD_SUCCESS:
                checkIsAnswerQuestion();
                break;
        }

    }


    /**
     * 判断是否答题
     */
    private void checkIsAnswerQuestion() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
        String isOne = user.getIs_one();
        if ("1".equals(isOne)) {
            //清空所有
            //去答题
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
}
