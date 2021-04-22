package com.vice.bloodpressure.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  更换手机号之输入手机号
 * 作者: LYD
 * 创建日期: 2019/9/24 16:30
 */
public class UserPhoneInputPhoneNumberActivity extends BaseHandlerActivity {
    private static final int GET_REGISTER_CODE = 10010;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_next_step)
    ColorTextView tvNextStep;
    @BindView(R.id.tv_change_or_bind)
    TextView tvChangeOrBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPageTitle();
        setTextChangeListener();
    }

    /**
     * 设置标题
     */
    private void setPageTitle() {
        //setWhiteTitleBar();
        String isBind = getIntent().getStringExtra("isBind");
        if ("0".equals(isBind)) {
            setTitle("绑定手机号");
            tvChangeOrBind.setText("绑定手机号后，下次可以直接使用新手机号登陆");
        } else {
            setTitle("更换手机号");
            tvChangeOrBind.setText("更换手机号后，下次可以直接使用新手机号登陆");
        }
    }

    /**
     * 设置监听
     */
    private void setTextChangeListener() {
        TextWatcherUtils.addTextChangedListener(new TextWatcherUtils.OnTextChangedListener() {
            @Override
            public void onTextChanged(String s) {
                if (s.length() > 0) {
                    tvNextStep.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.white));
                    tvNextStep.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.main_home));
                } else {
                    tvNextStep.getColorHelper().setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
                    tvNextStep.getColorHelper().setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
                }
            }
        }, etPhone);
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_user_phone_input_phone_number, contentLayout, false);
        return view;
    }


    @OnClick(R.id.tv_next_step)
    public void onViewClicked() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileSimple(phone)) {
            ToastUtils.showShort("请输入合法的手机号");
            return;
        }
        getRegisterCode(phone);
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
                String phone = etPhone.getText().toString().trim();
                Intent intent = new Intent(getPageContext(), UserPhoneInputRegisterCodeActivity.class);
                intent.putExtra("isBind", getIntent().getStringExtra("isBind"));
                intent.putExtra("phone", phone);
                startActivity(intent);
                break;
        }
    }
}
