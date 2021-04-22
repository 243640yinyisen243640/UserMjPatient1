package com.vice.bloodpressure;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDex;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.allen.library.RxHttpUtils;
import com.allen.library.bean.BaseData;
import com.allen.library.interceptor.Transformer;
import com.allen.library.observer.CommonObserver;
import com.blankj.utilcode.util.GsonUtils;
import com.clj.fastble.BleManager;
import com.fm.openinstall.OpenInstall;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.StarrySkyConfig;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.vice.bloodpressure.base.BaseApplication;
import com.vice.bloodpressure.bean.CheckAdviceBean;
import com.vice.bloodpressure.bean.im.ImWarningMessage;
import com.vice.bloodpressure.bean.im.ImWarningMessageContentBean;
import com.vice.bloodpressure.bean.im.ImWarningMessageProvider;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.imp.ImWarningClickListener;
import com.vice.bloodpressure.net.OkHttpInstance;
import com.vice.bloodpressure.net.Service;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.im.DoctorAdviceActivity;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import okhttp3.OkHttpClient;

/**
 * 描述: APP
 * 作者: LYD
 * 创建日期: 2019/3/20 16:19
 */
public class App extends BaseApplication implements RongIMClient.ConnectionStatusListener, RongIMClient.OnReceiveMessageListener, RongIM.ConversationListBehaviorListener {
    private static final String TAG = "App";


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.background);
                return new MaterialHeader(context).setShowBezierWave(true)
                        .setColorSchemeColors(ContextCompat.getColor(context, R.color.main_home));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale)
                        .setAnimatingColor(context.getResources().getColor(R.color.main_home));
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initJPush();
        initIm();
        initRxHttp();
        initBle();
        initOpenInstall();
        initBugly();
        initAliPush();
        initAudio();
    }


    private void initAudio() {
        StarrySkyConfig config = new StarrySkyConfig().newBuilder()
                .build();
        StarrySky.init(this, config, null);
    }


    private void initBugly() {
        //测试阶段建议设置成true，发布时设置为false
        //CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_ID, false);
    }


    private void initOpenInstall() {
        if (isMainProcess()) {
            OpenInstall.setDebug(true);
            OpenInstall.init(this);
        }
    }

    /**
     * 初始化极光推送
     */
    private void initJPush() {
        //JPushInterface.setDebugMode(true);
        //JPushInterface.init(this);
        //JPushUtils.init();
    }


    /**
     * 蓝牙
     */
    private void initBle() {
        BleManager.getInstance().init(this);
    }

    /**
     * 初始化
     */
    private void initRxHttp() {
        OkHttpClient okClient = OkHttpInstance.createInstance();

        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                .setBaseUrl(XyUrl.HOST_URL)
                .setOkClient(okClient);
    }


    /**
     * 初始化融云
     */
    private void initIm() {
        //        PushConfig config = new PushConfig.Builder()
        //                .enableMiPush(ConstantParam.MI_PUSH_ID, ConstantParam.MI_PUSH_KEY)
        //                .enableOppoPush(ConstantParam.OPPO_PUSH_KEY, ConstantParam.OPPO_PUSH_APP_SECRET)
        //                .enableVivoPush(true)
        //                .enableHWPush(false)
        //                .build();
        //        RongPushClient.setPushConfig(config);
        RongIM.init(this, ConstantParam.IM_KEY);
        //实现单点登录
        RongIM.setConnectionStatusListener(this);
        //注册自定义消息类型
        RongIM.registerMessageType(ImWarningMessage.class);
        //注册消息监听
        RongIM.setOnReceiveMessageListener(this);
        //注册消息模板
        RongIM.registerMessageTemplate(new ImWarningMessageProvider(new ImWarningClickListener() {
            @Override
            public void onCardClick(View view, ImWarningMessage content) {
                getCheckAdvice(view, content);
            }
        }));
        //会话列表操作监听
        RongIM.setConversationListBehaviorListener(this);
    }

    private void getCheckAdvice(View view, ImWarningMessage content) {
        String getContent = content.getContent();
        ImWarningMessageContentBean bean = GsonUtils.fromJson(getContent, ImWarningMessageContentBean.class);
        int wid = bean.getWid();
        int type = bean.getType();
        String typeName = bean.getTypename();
        String val = bean.getVal();
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        String token = user.getToken();

        RxHttpUtils.createApi(Service.class)
                .checkAdvice(token, wid + "")
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<BaseData<CheckAdviceBean>>() {
                    @Override
                    protected void onError(String errorMsg) {

                    }

                    @Override
                    protected void onSuccess(BaseData<CheckAdviceBean> checkAdviceBeanBaseData) {
                        CheckAdviceBean data = checkAdviceBeanBaseData.getData();
                        String advice = data.getContent();
                        //2已处理
                        Intent intent = new Intent(App.this, DoctorAdviceActivity.class);
                        intent.putExtra("advice", advice);
                        intent.putExtra("type", type + "");
                        intent.putExtra("typeName", typeName);
                        intent.putExtra("val", val);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

    }

    @Override
    public boolean onReceived(Message message, int i) {
        return false;
    }

    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }


    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }


    /**
     * 初始化云推送通道
     */
    private void initAliPush() {
        createNotificationChannel();
        PushServiceFactory.init(this);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(this, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.e("aliPush", "初始化成功");
                String deviceId = pushService.getDeviceId();
                Log.e("aliPush", "设备deviceId==" + deviceId);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e("aliPush", "初始化失败-- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        //        //小米通道注册
        //        MiPushRegister.register(this, "2882303761517853353", "5511785339353");
        //        //华为通道注册
        //        HuaWeiRegister.register(this);
        //        // OPPO通道注册
        //        OppoRegister.register(this, "63d86bfd3a5441929cc66f5f11e9dabb", "91f24bf3f35b4e0daffeb0625e100f1f");
        //        // VIVO通道注册
        //        VivoRegister.register(this);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //通知渠道的id
            String id = "1";
            //用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            //用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            //配置通知渠道的属性
            mChannel.setDescription(description);
            //设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            //设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
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
