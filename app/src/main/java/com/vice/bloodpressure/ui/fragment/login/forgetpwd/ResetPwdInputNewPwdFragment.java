package com.vice.bloodpressure.ui.fragment.login.forgetpwd;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.TextWatcherUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:  重置密码之
 * 作者: LYD
 * 创建日期: 2019/9/20 21:00
 */
public class ResetPwdInputNewPwdFragment extends BaseFragment {
    private static final int TO_POP_ALL_FRAGMENT = 10010;
    @BindView(R.id.et_input_pwd)
    EditText etInputPwd;
    @BindView(R.id.tv_sure)
    ColorTextView tvSure;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reset_pwd_input_new_pwd;
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
        }, etInputPwd);
    }




    @OnClick({R.id.img_back, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                FragmentUtils.pop(getParentFragmentManager());
                break;
            case R.id.tv_sure:
                String pwd = etInputPwd.getText().toString().trim();
                int minPwdWidth = 6;
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                if (pwd.length() < minPwdWidth) {
                    ToastUtils.showShort("至少输入" + minPwdWidth + "位密码");
                    return;
                }
                toDoResetPwd(pwd);
                break;
        }
    }

    /**
     * 重新设置密码
     *
     * @param pwd
     */
    private void toDoResetPwd(String pwd) {
        String phoneNumber = getArguments().getString("phoneNumber");
        HashMap map = new HashMap<>();
        map.put("username", phoneNumber);
        map.put("newpass", pwd);
        XyUrl.okPostSave(XyUrl.RESET_PWD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort("获取成功");
                sendHandlerMessage(TO_POP_ALL_FRAGMENT);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });


    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case TO_POP_ALL_FRAGMENT:
                //清空添加的Fragment
                FragmentUtils.popAll(getParentFragmentManager());
                break;
        }
    }
}
