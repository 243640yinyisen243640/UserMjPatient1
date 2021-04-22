package com.vice.bloodpressure.base.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.gyf.immersionbar.ImmersionBar;
import com.lyd.baselib.utils.DisableDisplayDpiChangeUtils;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.ui.activity.user.LoginActivity;
import com.vice.bloodpressure.utils.SPUtils;
import com.vice.bloodpressure.view.popu.LoginOutPopup;
import com.wei.android.lib.colorview.view.ColorTextView;

import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

/**
 * 描述: 基础Activity
 * 作者: LYD
 * 创建日期: 2019/3/25 10:05
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private Button btnBack;
    private TextView tvTitle;
    protected RelativeLayout contentLayout;
    private TextView tvSave;
    private RelativeLayout rlTitleBar;
    private LinearLayout llMore;
    private Context mContext;
    private OtherEquipmentLoginReceiver receiver;
    private LoginOutPopup loginOutPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisableDisplayDpiChangeUtils.disabledDisplayDpiChange(this);
        mContext = this;
        //必须在setContentView()之前调用
        setScreenOrientation(true);
        setContentView(R.layout.activity_base);
        btnBack = findViewById(R.id.bt_back);
        tvTitle = findViewById(R.id.tv_title);
        tvSave = findViewById(R.id.tv_more);
        rlTitleBar = findViewById(R.id.rl_title_bar);
        llMore = findViewById(R.id.ll_more);
        contentLayout = findViewById(R.id.rl_content);
        btnBack.setOnClickListener(view -> finish());
        //添加内容布局
        contentLayout.addView(addContentLayout());
        //必须在setContentView()之后调用
        ButterKnife.bind(this);
        //状态栏
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
        loginOutPopup = new LoginOutPopup(this);
        //DisableDisplayDpiChangeUtils.disabledDisplayDpiChange(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LoginOut");
        receiver = new OtherEquipmentLoginReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销广播
        unregisterReceiver(receiver);
    }


    /**
     * 添加内容布局
     *
     * @return
     */
    protected abstract View addContentLayout();

    /**
     * 显示标题
     */
    public void showTitleBar() {
        rlTitleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏标题
     */
    public void hideTitleBar() {
        rlTitleBar.setVisibility(View.GONE);
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    public void setTitleBarBg(int color) {
        rlTitleBar.setBackgroundColor(color);
    }

    /**
     * 隐藏返回键
     */
    public void hideBack() {
        btnBack.setVisibility(View.GONE);
    }

    /**
     * 返回
     *
     * @return
     */
    public Button getBack() {
        return btnBack;
    }


    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            if (title.length() > 10) {
                String subTitle = title.substring(0, 10);
                tvTitle.setText(subTitle + "...");
            } else {
                tvTitle.setText(title);
            }
        }
    }


    /**
     * 获取右上角保存
     *
     * @return
     */

    public TextView getTvSave() {
        showTvSave();
        return tvSave;
    }

    /**
     * 显示右上角保存
     */
    public void showTvSave() {
        tvSave.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏右上角保存
     */
    public void hideTvSave() {
        tvSave.setVisibility(View.GONE);
    }

    /**
     * 返回右上角
     *
     * @return
     */
    public LinearLayout getLlMore() {
        return llMore;
    }

    /**
     * 获取当前上下文
     *
     * @return
     */
    public Context getPageContext() {
        return mContext;
    }


    /**
     * 设置屏幕横竖屏切换
     *
     * @param screenRotate true  竖屏     false  横屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private void setScreenOrientation(Boolean screenRotate) {
        if (screenRotate) {
            //设置竖屏模式
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            //设置横屏模式
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    /**
     * 在其他设备登录
     */
    public class OtherEquipmentLoginReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if ("LoginOut".equals(intent.getAction())) {
                Log.e(TAG, "收到下线广播");
                otherEquipmentLogin();
            }
        }
    }

    /**
     * 推送登录
     */
    private void otherEquipmentLogin() {
        Log.e(TAG, "执行");
        RongIM.getInstance().logout();
        loginOutPopup.showPopupWindow();
        ColorTextView tvSure = loginOutPopup.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }


//    /**
//     * 重写 getResource 方法，防止系统字体影响
//     * 禁止app字体大小跟随系统字体大小调节
//     */
//    @Override
//    public Resources getResources() {
//        Resources resources = super.getResources();
//        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
//            Configuration configuration = resources.getConfiguration();
//            configuration.setToDefaults();
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }
//        return resources;
//    }


}