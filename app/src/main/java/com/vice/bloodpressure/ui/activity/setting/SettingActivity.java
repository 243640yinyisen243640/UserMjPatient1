package com.vice.bloodpressure.ui.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.user.AccountAndSafetyActivity;
import com.vice.bloodpressure.ui.activity.user.LoginActivity;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.SPUtils;
import com.wei.android.lib.colorview.helper.ColorTextViewHelper;
import com.wei.android.lib.colorview.view.ColorButton;
import com.wei.android.lib.colorview.view.ColorTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * 描述: 设置
 * 作者: LYD
 * 创建日期: 2020/6/2 16:21
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_account_safe)
    TextView tvAccountSafe;
    @BindView(R.id.bt_exit)
    ColorButton btExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPageTitle();
    }

    private void setPageTitle() {
        setTitle("设置");
        getLlMore().removeAllViews();
        ColorTextView tvMore = new ColorTextView(getPageContext());
        tvMore.setText("注销");
        tvMore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tvMore.setPadding(20, 5, 20, 5);
        ColorTextViewHelper helper = tvMore.getColorHelper();
        helper.setTextColorNormal(ColorUtils.getColor(R.color.gray_text));
        helper.setBackgroundColorNormal(ColorUtils.getColor(R.color.color_eb));
        int radius = ConvertUtils.dp2px(4);
        helper.setCornerRadiusBottomLeftNormal(radius);
        helper.setCornerRadiusBottomRightNormal(radius);
        helper.setCornerRadiusTopLeftNormal(radius);
        helper.setCornerRadiusTopRightNormal(radius);
        getLlMore().addView(tvMore);
        getLlMore().setOnClickListener(this);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_setting, contentLayout, false);
        return view;
    }

    @OnClick({R.id.tv_account_safe, R.id.tv_reset_sugar, R.id.tv_sugar_time_reset, R.id.bt_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account_safe:
                startActivity(new Intent(getPageContext(), AccountAndSafetyActivity.class));
                break;
            case R.id.tv_reset_sugar:
                startActivity(new Intent(getPageContext(), SugarControlTargetSettingActivity.class));
                break;
            case R.id.tv_sugar_time_reset:
                startActivity(new Intent(getPageContext(), SugarTimeResetActivity.class));
                break;
            case R.id.bt_exit:
                toExit();
                break;
        }
    }

    private void showLogOut() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String nickname = user.getUsername();
        String message = "您正在删除账号: " + nickname + "此操作将让您失去本账号";
        DialogUtils.getInstance().showCommonDialog(getPageContext(), "风险提示", message,
                "注销账号", "我再想想", new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {
                        toLogOut();
                    }
                }, new DialogUtils.DialogCallBack() {
                    @Override
                    public void execEvent() {

                    }
                });
    }


    /**
     * 注销账号
     */
    private void toLogOut() {
        Map<String, Object> map = new HashMap<>();
        XyUrl.loginOut(XyUrl.CLEAR_USER, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                SharedPreferencesUtils.clear();
                SPUtils.clear();
                ActivityUtils.finishAllActivities();
                Intent intent = new Intent(Utils.getApp(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {

            }
        });
    }


    /**
     * 退出登录 提示
     */
    private void toExit() {
        RongIM.getInstance().logout();
        DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要退出登录?", true, () -> {
            CloudPushService pushService = PushServiceFactory.getCloudPushService();
            pushService.unbindAccount(new CommonCallback() {
                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onFailed(String s, String s1) {

                }
            });
            ToastUtils.showShort("请登陆");
            //SPStaticUtils.clear();
            SharedPreferencesUtils.clear();
            SPUtils.clear();
            ActivityUtils.finishAllActivities();
            Intent intent = new Intent(Utils.getApp(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注销
            case R.id.ll_more:
                showLogOut();
                break;
        }
    }
}