package com.vice.bloodpressure.ui.fragment.login.registerandlogin;

import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseEventBusFragment;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.bean.RegisterQuestionAddBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.vice.bloodpressure.ui.activity.user.RegisterQuestionActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.SPUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.vice.bloodpressure.utils.edittext.IdNumberKeyListener;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:   绑定身份证号
 * 作者: LYD
 * 创建日期: 2019/9/19 17:00
 */
public class LoginBindIdCardFragment extends BaseEventBusFragment {
    private static final int HAVE_BIND_ID_CARD = 10086;
    private static final int BIND_ID_CARD_SUCCESS = 10087;
    @BindView(R.id.et_input_id_card)
    EditText etInputIdCard;
    @BindView(R.id.tv_bind)
    ColorTextView tvBind;
    private String idCard;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_bind_id_card;
    }

    @Override
    protected void init(View rootView) {
        setTextChangeListener();
    }

    /**
     * 设置监听
     */
    private void setTextChangeListener() {
        etInputIdCard.setKeyListener(new IdNumberKeyListener());
        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String etString) {
                if (etString.length() == 18) {
                    tvBind.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvBind.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                    tvBind.setEnabled(true);
                } else {
                    tvBind.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvBind.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                    tvBind.setEnabled(false);
                }
            }
        }, etInputIdCard);
    }

    @OnClick({R.id.img_back, R.id.tv_jump, R.id.tv_bind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.tv_jump://跳过
                checkIsBindPwd();
                break;
            case R.id.tv_bind://绑定
                idCard = etInputIdCard.getText().toString().trim();
                if (TextUtils.isEmpty(idCard)) {
                    ToastUtils.showShort("请输入身份证号");
                    return;
                }
                if (!RegexUtils.isIDCard18(idCard)) {
                    ToastUtils.showShort("请输入合法的身份证号");
                    return;
                }
                String message = "身份证号一旦提交不可修改，请确认是否输入正确";
                DialogUtils.getInstance().showCommonDialog(getPageContext(), "", message,
                        "返回确认", "绑定", new DialogUtils.DialogCallBack() {
                            @Override
                            public void execEvent() {
                            }
                        }, new DialogUtils.DialogCallBack() {
                            @Override
                            public void execEvent() {
                                toDoBindIdCard("");
                            }
                        });
                break;
        }
    }

    /**
     * 已经绑定手机号
     */
    private void haveBindIdCard() {
        String message = "该身份证号已完成登记注册，若继续强制绑定则会将原身份证账号注销且数据清除不可恢复";
        DialogUtils.getInstance().showCommonDialog(getPageContext(), "", message,
                "继续绑定", "考虑考虑", new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        toDoBindIdCard("1");
                    }
                }, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {

                    }
                });
    }

    /**
     * 是否绑定密码和答题
     */
    private void checkIsBindPwd() {
        LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
        String password = loginBean.getPassword();//1第一次注册登录，未设置密码  2已设置密码,
        String is_one = loginBean.getIs_one();//1:未答题2:已答题
        if ("1".equals(password)) {
            LoginBindPwdFragment loginBindPwdFragment = new LoginBindPwdFragment();
            FragmentUtils.replace(getParentFragmentManager(), loginBindPwdFragment, R.id.fl_fragment, true);
        } else if ("1".equals(is_one)) {
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

    /**
     * 绑定身份证号
     *
     * @param isYes 是否强制绑定
     */
    private void toDoBindIdCard(String isYes) {
        HashMap map = new HashMap<>();
        map.put("idcard", idCard);
        map.put("isyes", isYes);
        XyUrl.okPostSave(XyUrl.BIND_ID_NUMBER, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort(value);
                Message message = getHandlerMessage();
                message.what = BIND_ID_CARD_SUCCESS;
                sendHandlerMessage(message);

            }

            @Override
            public void onError(int error, String errorMsg) {
                if (30009 == error) {
                    Message message = getHandlerMessage();
                    message.what = HAVE_BIND_ID_CARD;
                    sendHandlerMessage(message);
                } else {
                    ToastUtils.showShort(errorMsg);
                }
            }
        });

    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case HAVE_BIND_ID_CARD:
                haveBindIdCard();
                break;
            case BIND_ID_CARD_SUCCESS:
                //身份证号绑定修改
                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
                user.setIdcard(idCard);
                SharedPreferencesUtils.putBean(getActivity(), SharedPreferencesUtils.USER_INFO, user);
                //身份证号绑定修改
                checkIsBindPwd();
                break;
        }
    }
}
