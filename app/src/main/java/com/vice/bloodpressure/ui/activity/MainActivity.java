package com.vice.bloodpressure.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.Utils;
import com.gyf.immersionbar.ImmersionBar;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.BuildConfig;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.constant.BaseConstantParam;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerEventBusActivity;
import com.vice.bloodpressure.bean.MyDoctorBean;
import com.vice.bloodpressure.bean.RongUserBean;
import com.vice.bloodpressure.bean.RongYunBean;
import com.vice.bloodpressure.bean.ShopTitleBean;
import com.vice.bloodpressure.bean.UpdateBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.mydevice.InputImeiActivity;
import com.vice.bloodpressure.ui.fragment.main.HomeFragment;
import com.vice.bloodpressure.ui.fragment.main.MallHomeNotOpenFragment;
import com.vice.bloodpressure.ui.fragment.main.MallWebFragment;
import com.vice.bloodpressure.ui.fragment.main.OutOfHospitalBindFragment;
import com.vice.bloodpressure.ui.fragment.main.OutOfHospitalFragment;
import com.vice.bloodpressure.ui.fragment.main.RegistrationFragment;
import com.vice.bloodpressure.ui.fragment.main.UserFragment;
import com.vice.bloodpressure.utils.DialogUtils;
import com.vice.bloodpressure.utils.NotificationUtils;
import com.vice.bloodpressure.utils.UpdateUtils;
import com.vice.bloodpressure.view.NumberProgressBar;
import com.vice.bloodpressure.view.popu.HomeGuidePopup;
import com.vice.bloodpressure.view.popu.UpdatePopup;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xiaomi.mipush.sdk.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2020/4/15 13:45
 */
public class MainActivity extends BaseHandlerEventBusActivity implements View.OnClickListener, IUnReadMessageObserver, OnDownloadListener {
    private static final String TAG = "MainActivity";
    private static final int GET_SHOP_TITLE = 10011;
    private static final int GET_UPDATE_DATA = 10012;
    private static final int GET_IM_TOKEN = 10013;
    private static final int BIND = 10014;
    private static final int NO_BIND = 10015;

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.img_main_home)
    ImageView imgMainHome;
    @BindView(R.id.tv_main_home)
    TextView tvMainHome;
    @BindView(R.id.rl_main_home)
    RelativeLayout rlMainHome;
    @BindView(R.id.img_main_register)
    ImageView imgMainRegister;
    @BindView(R.id.tv_main_register)
    TextView tvMainRegister;
    @BindView(R.id.rl_main_register)
    RelativeLayout rlMainRegister;
    @BindView(R.id.img_main_mall)
    ImageView imgMainMall;
    @BindView(R.id.tv_main_mall)
    TextView tvMainMall;
    @BindView(R.id.rl_main_mall)
    RelativeLayout rlMainMall;
    @BindView(R.id.img_main_me)
    ImageView imgMainMe;
    @BindView(R.id.tv_main_me)
    TextView tvMainMe;
    @BindView(R.id.rl_main_me)
    RelativeLayout rlMainMe;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.tv_main_outside)
    TextView tvMainOutside;
    @BindView(R.id.rl_main_outside)
    RelativeLayout rlMainOutside;
    @BindView(R.id.tv_red_point)
    ColorTextView tvRedPoint;

    private boolean isHaveUpdate;
    private HomeGuidePopup homeGuideEducationPopup;
    private HomeGuidePopup homeGuideDietPopup;
    private HomeGuidePopup homeGuideSportPopup;

    //Update Start
    //更新弹窗
    private UpdatePopup updatePopup;
    //更新的apk新版本号
    private AppCompatTextView tvUpdateName;
    //更新的新apk大小
    private AppCompatTextView tvUpdateSize;
    //更新内容
    private AppCompatTextView tvUpdateContent;
    //进度条
    private NumberProgressBar pbUpdateProgress;
    //升级按钮
    private AppCompatTextView tvUpdateUpdate;
    //关闭按钮(点击取消)
    private AppCompatImageView ivUpdateClose;
    //更新网址
    private String updateUrl;
    //下载的apk文件
    private File updateApk;
    //Update end

    private String mallUrl;
    private String registrationUrl;
    private String registrationStr;

    private OutOfHospitalFragment outOfHospitalFragment;

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxbedc85b967f57dc8";
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_main, contentLayout, false);
        return layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regToWx();
        //添加第一个Fragment
        addFirstFragment();
        //初始化血糖弹窗
        initPopup();
        //设置极光推送别名
        setUPushAlias();
        //设置标题
        getShopTitle();
        //获取融云token
        getImToken();
        //更新APP
        getUpdate();
        //获取融云消息未读数量
        getImUnReadMsgCount();
        //获取唤醒参数
        toSubmitScan();
        //设置用户信息代理
        setRongImUserInfo();
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.main_home).init();
    }


    private void regToWx() {
        //通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        //将应用的appId注册到微信(将该app注册到微信)
        api.registerApp(APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //将该app注册到微信
                api.registerApp(Constants.APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FloatingView.get().attach(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNotifySettings();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FloatingView.get().detach(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        toSubmitScan();
    }

    /**
     * 注销已注册的未读消息数变化监听器。
     * 接收未读消息消息的监听器。
     */
    @Override
    protected void onDestroy() {
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(this);
        super.onDestroy();
    }


    /**
     * 展示更新弹窗
     *
     * @param data
     */
    private void showUpdatePopup(UpdateBean data) {
        //更新判断
        if (isHaveUpdate) {
            toShowUpdateDialog(data);
            updatePopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    showGuidePopup();
                }
            });
        } else {
            showGuidePopup();
        }
    }

    /**
     * 展示引导弹窗
     */
    private void showGuidePopup() {
        //引导判断
        int count = SPStaticUtils.getInt("isFirstInstall", 0);
        if (0 == count) {
            homeGuideSportPopup.showPopupWindow();
            homeGuideDietPopup.showPopupWindow();
            homeGuideEducationPopup.showPopupWindow();
            SPStaticUtils.put("isFirstInstall", 1);
        }
    }


    private void toShowUpdateDialog(UpdateBean data) {
        updateUrl = data.getUpdateurl();
        String versionName = data.getVersionname();
        String apkSize = data.getAppsize();
        String updateContent = data.getUpcontent();
        tvUpdateName.setText(versionName);
        tvUpdateSize.setText(apkSize);
        tvUpdateContent.setText(updateContent);
        tvUpdateContent.setVisibility(updateContent == null ? View.GONE : View.VISIBLE);
        updatePopup.showPopupWindow();
    }

    private void toSubmitScan() {
        String scanImei = SPStaticUtils.getString("scanImei");
        if (!TextUtils.isEmpty(scanImei)) {
            Intent intent = new Intent(getPageContext(), InputImeiActivity.class);
            intent.putExtra("imei", scanImei);
            startActivity(intent);
        }
        SPStaticUtils.put("scanImei", "");
    }


    /**
     * 设置用户信息代理
     */
    private void setRongImUserInfo() {
        List<RongUserBean> rongList = new ArrayList<>();
        //设置用户
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(Utils.getApp(), SharedPreferencesUtils.USER_INFO);
        String userid = user.getUserid();
        String username = user.getNickname();
        String picture = user.getPicture();
        RongUserBean userBean = new RongUserBean(userid, username, picture);
        rongList.add(userBean);
        //设置医生
        String mainDocBind = SPStaticUtils.getString("mainDocBind");
        if (!TextUtils.isEmpty(mainDocBind)) {
            String mainDocId = SPStaticUtils.getString("mainDocId");
            String mainDocName = SPStaticUtils.getString("mainDocName");
            String mainDocImgUrl = SPStaticUtils.getString("mainDocImgUrl");
            RongUserBean doctorBean = new RongUserBean(mainDocId, mainDocName, mainDocImgUrl);
            rongList.add(doctorBean);
        }
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String imUserId) {
                for (RongUserBean userBean : rongList) {
                    String serverId = userBean.getId();
                    if (serverId.equals(imUserId)) {
                        UserInfo userInfo = new UserInfo(serverId, userBean.getName(), Uri.parse(userBean.getHeadImgUrl()));
                        //调用刷新方法, 刷新用户信息缓存
                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                        return userInfo;
                    }
                }
                return null;
            }
        }, false);
    }

    /**
     * 获取融云消息未读数量
     */
    private void getImUnReadMsgCount() {
        final Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.PRIVATE};
        RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);
    }

    /**
     * 设置别名
     */
    private void setUPushAlias() {
        LoginBean bean = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        String userid = bean.getUserid();
//        String aliasBefore = BuildConfig.ENVIRONMENT ? "p_" : "t_";
        String aliasBefore = BuildConfig.ENVIRONMENT ? "wt_" : "t_";
        //JPushUtils.setAlias(aliasBefore + userid);
        String bindAccount = aliasBefore + userid;
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.bindAccount(bindAccount, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFailed(String s, String s1) {
            }
        });
    }


    /**
     * 弹窗初始化
     */
    private void initPopup() {
        //引导弹窗教育
        homeGuideEducationPopup = new HomeGuidePopup(getPageContext());
        //引导教育弹窗
        homeGuideDietPopup = new HomeGuidePopup(getPageContext());
        ImageView imgHomeGuideDiet = homeGuideDietPopup.findViewById(R.id.img_home_guide);
        imgHomeGuideDiet.setImageResource(R.drawable.home_guide_diet);
        //引导运动弹窗
        homeGuideSportPopup = new HomeGuidePopup(getPageContext());
        ImageView imgHomeGuideSport = homeGuideSportPopup.findViewById(R.id.img_home_guide);
        imgHomeGuideSport.setImageResource(R.drawable.home_guide_sport);
        //更新弹窗
        updatePopup = new UpdatePopup(getPageContext());
        tvUpdateName = updatePopup.findViewById(R.id.tv_update_name);
        tvUpdateSize = updatePopup.findViewById(R.id.tv_update_size);
        tvUpdateContent = updatePopup.findViewById(R.id.tv_update_content);
        pbUpdateProgress = updatePopup.findViewById(R.id.pb_update_progress);
        tvUpdateUpdate = updatePopup.findViewById(R.id.tv_update_update);
        ivUpdateClose = updatePopup.findViewById(R.id.iv_update_close);
        tvUpdateUpdate.setOnClickListener(this);
        ivUpdateClose.setOnClickListener(this);
    }


    /**
     * 检查通知权限
     */
    private void checkNotifySettings() {
        boolean notificationEnabled = NotificationUtils.isNotificationEnabled(getPageContext());
        if (notificationEnabled) {

        } else {
            DialogUtils.getInstance().showDialog(getPageContext(), "", "请点击确定跳转至设置页面,并允许通知.", true, new DialogUtils.DialogCallBack() {
                @Override
                public void execEvent() {
                    NotificationUtils.goToSettings(getPageContext());
                }
            });
        }
    }

    /**
     * 获取更新
     */
    private void getUpdate() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_UPDATE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                UpdateBean data = JSONObject.parseObject(value, UpdateBean.class);
                Message msg = Message.obtain();
                msg.obj = data;
                msg.what = GET_UPDATE_DATA;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取融云Token
     */
    private void getImToken() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_IM_TOKEN, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                RongYunBean rongYunBean = JSONObject.parseObject(value, RongYunBean.class);
                Message message = Message.obtain();
                message.what = GET_IM_TOKEN;
                message.obj = rongYunBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取商城标题
     */
    private void getShopTitle() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.GET_SHOP_TITLE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ShopTitleBean data = JSONObject.parseObject(value, ShopTitleBean.class);
                Message message = Message.obtain();
                message.what = GET_SHOP_TITLE;
                message.obj = data;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //更新升级按钮
            case R.id.tv_update_update:
                //判断字体获取状态
                String text = (String) tvUpdateUpdate.getText();
                switch (text) {
                    case "升级":
                    case "下载失败":
                        //下载并安装
                        toDownLoadAndInstallApk();
                        break;
                    case "下载中":
                        //没有动作
                        break;
                    case "立即安装":
                        //安装apk
                        AppUtils.installApp(updateApk);
                        break;
                }
                break;
            //关闭按钮
            case R.id.iv_update_close:
                updatePopup.dismiss();
                break;
        }
    }

    private void toDownLoadAndInstallApk() {
        UpdateUtils.downloadAndInstall(updateUrl, this);
    }

    /**
     * 新增HomeFragment
     */
    private void addFirstFragment() {
        hideTitleBar();
        tvMainHome.setTextColor(getResources().getColor(R.color.main_home));
        tvMainRegister.setTextColor(getResources().getColor(R.color.black_text));
        tvMainMall.setTextColor(getResources().getColor(R.color.black_text));
        tvMainMe.setTextColor(getResources().getColor(R.color.black_text));
        tvMainOutside.setTextColor(getResources().getColor(R.color.black_text));

        rlMainHome.setEnabled(false);
        rlMainRegister.setEnabled(true);
        rlMainMall.setEnabled(true);
        rlMainMe.setEnabled(true);
        rlMainOutside.setEnabled(true);

        imgMainHome.setImageResource(R.drawable.home_selected);
        if (TextUtils.isEmpty(registrationUrl)) {
            imgMainRegister.setImageResource(R.drawable.registration_default);
        } else {
            imgMainRegister.setImageResource(R.drawable.main_bottom_position_02_check);
        }
        if (TextUtils.isEmpty(mallUrl)) {
            imgMainMall.setImageResource(R.drawable.home_mall_default);
        } else {
            imgMainMall.setImageResource(R.drawable.main_bottom_position_04_check);
        }
        imgMainMe.setImageResource(R.drawable.home_me_default);
        HomeFragment homeFragment = new HomeFragment();
        FragmentUtils.replace(getSupportFragmentManager(), homeFragment, R.id.fl_main, false);
    }

    /**
     * 判断预约模块
     */
    private void setShowRegistration() {
        showTitleBar();
        hideBack();
        tvMainHome.setTextColor(getResources().getColor(R.color.black_text));
        tvMainRegister.setTextColor(getResources().getColor(R.color.main_home));
        tvMainMall.setTextColor(getResources().getColor(R.color.black_text));
        tvMainMe.setTextColor(getResources().getColor(R.color.black_text));
        tvMainOutside.setTextColor(getResources().getColor(R.color.black_text));
        rlMainHome.setEnabled(true);
        rlMainRegister.setEnabled(false);
        rlMainMall.setEnabled(true);
        rlMainMe.setEnabled(true);
        rlMainOutside.setEnabled(true);
        imgMainHome.setImageResource(R.drawable.home_default);
        if (TextUtils.isEmpty(registrationUrl)) {
            imgMainRegister.setImageResource(R.drawable.registration_default);
        } else {
            imgMainRegister.setImageResource(R.drawable.main_bottom_position_02_check);
        }
        if (TextUtils.isEmpty(mallUrl)) {
            imgMainMall.setImageResource(R.drawable.home_mall_default);
        } else {
            imgMainMall.setImageResource(R.drawable.main_bottom_position_04_check);
        }
        imgMainMe.setImageResource(R.drawable.home_me_default);

        if (!TextUtils.isEmpty(registrationUrl)) {
            setTitle(registrationStr);
            imgMainRegister.setImageResource(R.drawable.main_bottom_position_02_checked);
            MallWebFragment mallWebFragment = new MallWebFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", registrationUrl);
            mallWebFragment.setArguments(bundle);
            FragmentUtils.replace(getSupportFragmentManager(), mallWebFragment, R.id.fl_main, false);
        } else {
            setTitle("挂号");
            imgMainRegister.setImageResource(R.drawable.registration_selected);
            RegistrationFragment registrationFragment = new RegistrationFragment();
            FragmentUtils.replace(getSupportFragmentManager(), registrationFragment, R.id.fl_main, false);
        }
    }


    private void setOutSide() {
        hideTitleBar();
        hideBack();
        tvMainHome.setTextColor(getResources().getColor(R.color.black_text));
        tvMainRegister.setTextColor(getResources().getColor(R.color.black_text));
        tvMainMall.setTextColor(getResources().getColor(R.color.black_text));
        tvMainMe.setTextColor(getResources().getColor(R.color.black_text));
        tvMainOutside.setTextColor(getResources().getColor(R.color.main_home));
        rlMainHome.setEnabled(true);
        rlMainRegister.setEnabled(true);
        rlMainMall.setEnabled(true);
        rlMainMe.setEnabled(true);
        rlMainOutside.setEnabled(false);
        imgMainHome.setImageResource(R.drawable.home_default);
        if (TextUtils.isEmpty(registrationUrl)) {
            imgMainRegister.setImageResource(R.drawable.registration_default);
        } else {
            imgMainRegister.setImageResource(R.drawable.main_bottom_position_02_check);
        }
        if (TextUtils.isEmpty(mallUrl)) {
            imgMainMall.setImageResource(R.drawable.home_mall_default);
        } else {
            imgMainMall.setImageResource(R.drawable.main_bottom_position_04_check);
        }
        imgMainMe.setImageResource(R.drawable.home_me_default);
        if (NetworkUtils.isConnected()) {
            getDoctorInfo();
        } else {
            outOfHospitalFragment = new OutOfHospitalFragment();
            FragmentUtils.replace(getSupportFragmentManager(), outOfHospitalFragment, R.id.fl_main, false);
        }
        setTitle("院外管理");
    }

    /**
     * 判断商城模块
     */
    private void setShowMall() {
        hideTitleBar();
        hideBack();
        tvMainHome.setTextColor(getResources().getColor(R.color.black_text));
        tvMainRegister.setTextColor(getResources().getColor(R.color.black_text));
        tvMainMall.setTextColor(getResources().getColor(R.color.main_home));
        tvMainMe.setTextColor(getResources().getColor(R.color.black_text));
        tvMainOutside.setTextColor(getResources().getColor(R.color.black_text));
        rlMainHome.setEnabled(true);
        rlMainRegister.setEnabled(true);
        rlMainMall.setEnabled(false);
        rlMainMe.setEnabled(true);
        rlMainOutside.setEnabled(true);
        imgMainHome.setImageResource(R.drawable.home_default);
        if (TextUtils.isEmpty(registrationUrl)) {
            imgMainRegister.setImageResource(R.drawable.registration_default);
        } else {
            imgMainRegister.setImageResource(R.drawable.main_bottom_position_02_check);
        }
        if (TextUtils.isEmpty(mallUrl)) {
            imgMainMall.setImageResource(R.drawable.home_mall_default);
        } else {
            imgMainMall.setImageResource(R.drawable.main_bottom_position_04_check);
        }
        imgMainMe.setImageResource(R.drawable.home_me_default);
        if (!TextUtils.isEmpty(mallUrl)) {
            imgMainMall.setImageResource(R.drawable.main_bottom_position_04_checked);
//            MallWebFragment mallWebFragment = new MallWebFragment();
            MallHomeNotOpenFragment mallWebFragment = new MallHomeNotOpenFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", mallUrl);
            mallWebFragment.setArguments(bundle);
            FragmentUtils.replace(getSupportFragmentManager(), mallWebFragment, R.id.fl_main, false);
        } else {
            imgMainMall.setImageResource(R.drawable.home_mall_selected);
            //MallNativeFragment mallNativeFragment = new MallNativeFragment();
//            MallHomeFragment mallNativeFragment = new MallHomeFragment();
            MallHomeNotOpenFragment mallNativeFragment = new MallHomeNotOpenFragment();
            FragmentUtils.replace(getSupportFragmentManager(), mallNativeFragment, R.id.fl_main, false);
        }
    }

    private void setShowMe() {
        hideTitleBar();
        tvMainHome.setTextColor(getResources().getColor(R.color.black_text));
        tvMainRegister.setTextColor(getResources().getColor(R.color.black_text));
        tvMainMall.setTextColor(getResources().getColor(R.color.black_text));
        tvMainMe.setTextColor(getResources().getColor(R.color.main_home));
        tvMainOutside.setTextColor(getResources().getColor(R.color.black_text));
        rlMainHome.setEnabled(true);
        rlMainRegister.setEnabled(true);
        rlMainMall.setEnabled(true);
        rlMainMe.setEnabled(false);
        rlMainOutside.setEnabled(true);
        imgMainHome.setImageResource(R.drawable.home_default);
        if (TextUtils.isEmpty(registrationUrl)) {
            imgMainRegister.setImageResource(R.drawable.registration_default);
        } else {
            imgMainRegister.setImageResource(R.drawable.main_bottom_position_02_check);
        }
        if (TextUtils.isEmpty(mallUrl)) {
            imgMainMall.setImageResource(R.drawable.home_mall_default);
        } else {
            imgMainMall.setImageResource(R.drawable.main_bottom_position_04_check);
        }
        imgMainMe.setImageResource(R.drawable.home_me_selected);
        UserFragment userFragment = new UserFragment();
        FragmentUtils.replace(getSupportFragmentManager(), userFragment, R.id.fl_main, false);
    }


    /**
     * 获取医生信息
     */
    private void getDoctorInfo() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.MY_DOCTOR, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MyDoctorBean doctor = JSONObject.parseObject(value, MyDoctorBean.class);
                Message msg = Message.obtain();
                msg.obj = doctor;
                msg.what = BIND;
                sendHandlerMessage(msg);
            }

            @Override
            public void onError(int error, String errorMsg) {
                Message msg = Message.obtain();
                msg.what = NO_BIND;
                sendHandlerMessage(msg);
            }
        });
    }

    /**
     * 连接融云服务器
     *
     * @param rongYun
     */
    private void connectRongServer(RongYunBean rongYun) {
        RongIM.connect(rongYun.getToken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {

            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

            }
        });
    }


    @Override
    public void start() {
    }

    @Override
    public void downloading(int max, int progress) {
        //百分比
        int curr = (int) (progress / (double) max * 100.0);
        //下载中
        tvUpdateUpdate.setText("下载中");
        pbUpdateProgress.setVisibility(View.VISIBLE);
        pbUpdateProgress.setProgress(curr);
    }

    @Override
    public void done(File apk) {
        updateApk = apk;
        //立即安装
        tvUpdateUpdate.setText("立即安装");
        pbUpdateProgress.setVisibility(View.GONE);
    }

    @Override
    public void cancel() {
    }

    @Override
    public void error(Exception e) {
        e.printStackTrace();
        tvUpdateUpdate.setText("下载失败");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        ClickUtils.back2HomeFriendly("再按一次退出程序");
    }

    /**
     * 未读消息数量监听
     *
     * @param count
     */
    @Override
    public void onCountChanged(int count) {
        if (count > 0) {
            tvRedPoint.setVisibility(View.VISIBLE);
        } else {
            tvRedPoint.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.rl_main_home, R.id.rl_main_register, R.id.rl_main_mall, R.id.rl_main_me, R.id.rl_main_outside})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_main_home://首页
                addFirstFragment();
                break;
            case R.id.rl_main_register://圈子
                setShowRegistration();
                break;
            case R.id.rl_main_mall://商城
                setShowMall();
                break;
            case R.id.rl_main_me://我的
                setShowMe();
                break;
            case R.id.rl_main_outside:
                setOutSide();
                break;
        }
    }


    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_IM_TOKEN:
                RongYunBean rongYunBean = (RongYunBean) msg.obj;
                connectRongServer(rongYunBean);
                break;
            //绑定医生
            case BIND:
                MyDoctorBean doctorBean = (MyDoctorBean) msg.obj;
                int docid = doctorBean.getDocid();
                String docname = doctorBean.getDocname();
                String imgurl = doctorBean.getImgurl();
                SPStaticUtils.put("mainDocBind", "1");
                SPStaticUtils.put("mainDocId", docid + "");
                SPStaticUtils.put("mainDocName", docname);
                SPStaticUtils.put("mainDocImgUrl", imgurl);
                EventBusUtils.post(new EventMessage(ConstantParam.RONG_HEAD_REFRESH));
                OutOfHospitalBindFragment outOfHospitalBindFragment = new OutOfHospitalBindFragment();
                FragmentUtils.replace(getSupportFragmentManager(), outOfHospitalBindFragment, R.id.fl_main, false);
                break;
            //未绑定医生
            case NO_BIND:
                outOfHospitalFragment = new OutOfHospitalFragment();
                FragmentUtils.replace(getSupportFragmentManager(), outOfHospitalFragment, R.id.fl_main, false);
                break;
            case GET_UPDATE_DATA:
                int localVersionCode = AppUtils.getAppVersionCode();
                UpdateBean data = (UpdateBean) msg.obj;
                int netVersionCode = data.getVersion();
                if (localVersionCode < netVersionCode) {
                    isHaveUpdate = true;
                } else {
                    isHaveUpdate = false;
                }
                //展示弹窗
                showUpdatePopup(data);
                break;
            case GET_SHOP_TITLE:
                ShopTitleBean shopTitleBean = (ShopTitleBean) msg.obj;
                registrationStr = shopTitleBean.getScheduling();
                tvMainRegister.setText(registrationStr);
                tvMainMall.setText(shopTitleBean.getTitle());
                //registrationUrl = "https://www.zhihu.com/";
                registrationUrl = shopTitleBean.getSchurl();
                //mallUrl = "https://www.baidu.com/";
                mallUrl = shopTitleBean.getUrl();
                if (TextUtils.isEmpty(registrationUrl)) {
                    imgMainRegister.setImageResource(R.drawable.registration_default);
                } else {
                    imgMainRegister.setImageResource(R.drawable.main_bottom_position_02_check);
                }
                if (TextUtils.isEmpty(mallUrl)) {
                    imgMainMall.setImageResource(R.drawable.home_mall_default);
                } else {
                    imgMainMall.setImageResource(R.drawable.main_bottom_position_04_check);
                }
                break;
        }
    }

    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.RONG_HEAD_REFRESH:
                setRongImUserInfo();
                break;
            case ConstantParam.RONGIM_RED_POINT_REFRESH:
                tvRedPoint.setVisibility(View.GONE);
                break;
            case ConstantParam.HOME_TO_OUTSIDE:
                setOutSide();
                break;
            case BaseConstantParam.EventCode.MALL_FINISH_RO_MAIN_ACTIVITY:
                //收到通知
                Log.e(TAG, "支付成功以后回到首页");
                startActivity(new Intent(getPageContext(), MainActivity.class));
                break;
        }
    }
}
