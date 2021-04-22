package com.vice.bloodpressure.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.wei.android.lib.colorview.view.ColorTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:更换手机号之 显示手机号或手机号未绑定
 * 作者: LYD
 * 创建日期: 2019/9/24 16:12
 */
public class UserPhoneBindOrNoActivity extends BaseActivity {
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_change_phone)
    ColorTextView tvChangePhone;
    @BindView(R.id.ll_bind)
    LinearLayout llBind;
    @BindView(R.id.ll_not_bind)
    LinearLayout llNotBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPageTitle();
    }


    /**
     * 设置标题
     */
    private void setPageTitle() {
        //setWhiteTitleBar();
        String isBind = getIntent().getStringExtra("isBind");
        if ("0".equals(isBind)) {
            //未绑定
            setTitle("手机号");
            llBind.setVisibility(View.GONE);
            llNotBind.setVisibility(View.VISIBLE);
        } else {
            //绑定
            setTitle("账号与安全");
            LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
            String username = loginBean.getUsername();
            tvPhone.setText(username);
            llBind.setVisibility(View.VISIBLE);
            llNotBind.setVisibility(View.GONE);
        }
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_user_phone_bind_or_no, contentLayout, false);
        return view;
    }


    @OnClick({R.id.tv_change_phone, R.id.tv_bind_phone})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_change_phone:
                //修改手机号
                intent = new Intent(getPageContext(), UserPhoneInputPhoneNumberActivity.class);
                intent.putExtra("isBind", "1");
                startActivity(intent);
                break;
            case R.id.tv_bind_phone:
                //绑定手机号
                intent = new Intent(getPageContext(), UserPhoneInputPhoneNumberActivity.class);
                intent.putExtra("isBind", "0");
                startActivity(intent);
                break;
        }

    }
}
