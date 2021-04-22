package com.vice.bloodpressure.ui.activity.homesign;


import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.ToastUtils;
import com.suke.widget.SwitchButton;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.Service;
import com.lyd.baselib.utils.SharedPreferencesUtils;

import butterknife.BindView;

/*
 * 包名:     com.vice.bloodpressure.ui.activity.familydoctor
 * 类名:     PrivacySettingActivity
 * 描述:     隐私设置
 * 作者:     ZWK
 * 创建日期: 2020/1/3 17:27
 */

public class PrivacySettingActivity extends BaseHandlerActivity {

    @BindView(R.id.sb)
    SwitchButton sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("隐私设置");
        int notAllow = getIntent().getIntExtra("is_allow", -1);
        sb.setChecked(notAllow == 0);

        sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                int x = isChecked ? 0 : 1;
                LoginBean loginBean = (LoginBean) SharedPreferencesUtils.getBean(getApplicationContext(), SharedPreferencesUtils.USER_INFO);
                String token = loginBean.getToken();
                int uid = Integer.parseInt(loginBean.getUserid());
                RxHttpUtils.createApi(Service.class)
                        .privacySettings(token, x, uid)
                        .compose(Transformer.switchSchedulers())
                        .subscribe(new CommonObserver<BaseData>() {
                            @Override
                            protected void onError(String errorMsg) {

                            }

                            @Override
                            protected void onSuccess(BaseData baseData) {
                                ToastUtils.showShort("操作成功");
                            }
                        });
            }
        });
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_privacy_setting, contentLayout, false);
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
