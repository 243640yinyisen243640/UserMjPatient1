package com.vice.bloodpressure.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.wei.android.lib.colorview.view.ColorEditText;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 描述:  修改密码
 * 作者: LYD
 * 创建日期: 2019/9/11 10:07
 */
public class ChangePwdActivity extends BaseHandlerActivity implements View.OnClickListener {
    @BindView(R.id.et_old_pwd)
    ColorEditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    ColorEditText etNewPwd;
    @BindView(R.id.et_new_pwd_repeat)
    ColorEditText etNewPwdRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KeyboardUtils.fixAndroidBug5497(this);
        setPageTitle();
    }

    /**
     * 设置标题
     */
    private void setPageTitle() {
        getTvSave().setText("保存");
        //setWhiteTitleBar();
        showTvSave();
        setTitle("修改密码");
        getTvSave().setOnClickListener(this);
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_change_pwd, contentLayout, false);
        return view;
    }

    /**
     * 校验密码
     */
    private void checkPwd() {
        int minPwdLength = 6;
        String oldPwd = etOldPwd.getText().toString().trim();
        String newPwd = etNewPwd.getText().toString().trim();
        String newPwdRepeat = etNewPwdRepeat.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUtils.showShort("请输入原始密码");
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            ToastUtils.showShort("请输入新密码");
            return;
        }
        if (newPwd.length() < minPwdLength) {
            ToastUtils.showShort("新密码位数不得少于" + minPwdLength + "位");
            return;
        }
        if (TextUtils.isEmpty(newPwdRepeat)) {
            ToastUtils.showShort("请再次输入新密码");
            return;
        }
        if (newPwdRepeat.length() < minPwdLength) {
            ToastUtils.showShort("确认新密码位数不得少于" + minPwdLength + "位");
            return;
        }
        if (!newPwd.equals(newPwdRepeat)) {
            ToastUtils.showShort("两次输入密码不一致");
            return;
        }
        toDoChangePwd(oldPwd, newPwd, newPwdRepeat);
    }

    /**
     * 提交新密码
     */
    private void toDoChangePwd(String oldPwd, String newPwd, String newPwdRepeat) {
        HashMap<String, Object> map = new HashMap<>();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        map.put("oldpassword", oldPwd);
        map.put("newpassword", newPwd);
        map.put("repassword", newPwdRepeat);
        XyUrl.okPostSave(XyUrl.CHANGE_PWD, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort("获取成功");
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more://右上角保存
                checkPwd();
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
