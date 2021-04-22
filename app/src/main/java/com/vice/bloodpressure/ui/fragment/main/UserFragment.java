package com.vice.bloodpressure.ui.fragment.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingView;
import com.lyd.baselib.bean.LoginBean;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.lyd.baselib.utils.eventbus.EventBusUtils;
import com.lyd.baselib.utils.eventbus.EventMessage;
import com.lyd.modulemall.ui.activity.order.MyOrderListActivity;
import com.lyd.modulemall.ui.activity.user.MyCouponListActivity;
import com.lyd.modulemall.ui.activity.user.MyProductCollectListActivity;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.adapter.HomeUserAdapter;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.bean.BannerBean;
import com.vice.bloodpressure.bean.DeviceChangeBean;
import com.vice.bloodpressure.bean.IsLiverFilesBean;
import com.vice.bloodpressure.constant.ConstantParam;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.mydevice.MyBindDeviceListActivity;
import com.vice.bloodpressure.ui.activity.mydevice.ScanActivity;
import com.vice.bloodpressure.ui.activity.setting.AboutUsActivity;
import com.vice.bloodpressure.ui.activity.setting.SettingActivity;
import com.vice.bloodpressure.ui.activity.user.MeActivity;
import com.vice.bloodpressure.ui.activity.user.MyLiverFilesActivity;
import com.vice.bloodpressure.ui.activity.user.MyReportListActivity;
import com.vice.bloodpressure.ui.activity.user.MySugarFilesActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UserFragment extends BaseFragment implements SimpleImmersionOwner {
    private static final String TAG = "UserFragment";
    private static final int HAVE_BIND_DEVICE = 10016;
    private static final int BIND_DEVICE_NOT = 10017;
    private static final int IS_LIVER_FILE = 10018;
    private static final int GET_BANNER_LIST = 10019;
    private static final int HAVE_BIND_DOCTOR_SUCCESS = 10020;
    private static final int HAVE_BIND_DOCTOR_ERROR = 10021;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.ll_personal)
    LinearLayout llPersonal;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
//    @BindView(R.id.banner)
//    Banner banner;
    @BindView(R.id.rv_my_service)
    RecyclerView rvMyService;

    private List<BannerBean> bannerList;
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void init(View rootView) {
        FloatingView.get().remove();
        setMyService();
        setUserInfo();
        getBannerList();
    }


    /**
     *
     */
    private void getBannerList() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("docid", user.getDocid());
        map.put("type", 2);
        XyUrl.okPost(XyUrl.GET_ADV, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                List<BannerBean> bannerList = JSONObject.parseArray(value, BannerBean.class);
                Message message = Message.obtain();
                message.what = GET_BANNER_LIST;
                message.obj = bannerList;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }


    private void setMyService() {
        List<String> listStr = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            listStr.add(i + "");
        }
        HomeUserAdapter adapter = new HomeUserAdapter(listStr);
        rvMyService.setLayoutManager(new GridLayoutManager(getPageContext(), 4));
        rvMyService.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), MyReportListActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        getDoctorInfo();
                        break;
                    case 2:
                        getIsHaveBind();
                        break;
                    //关于我们
                    case 3:
                        startActivity(new Intent(getPageContext(), AboutUsActivity.class));
                        break;
                }
            }
        });
    }


    /**
     *
     */
    private void getIsHaveBind() {
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
        HashMap map = new HashMap<>();
        map.put("access_token", userLogin.getToken());
        XyUrl.okPost(XyUrl.DEVICE_SEARCH, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                DeviceChangeBean data = JSONObject.parseObject(value, DeviceChangeBean.class);
                Message message = getHandlerMessage();
                message.obj = data;
                message.what = HAVE_BIND_DEVICE;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(BIND_DEVICE_NOT);
            }
        });
    }

    private void setUserInfo() {
        LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getPageContext(), SharedPreferencesUtils.USER_INFO);
        if (TextUtils.isEmpty(user.getUsername())) {
            tvPhone.setText(user.getIdcard());
        } else {
            tvPhone.setText(user.getUsername());
        }
        //图片毛玻璃
        Glide.with(Utils.getApp())
                .load(user.getPicture())
                .placeholder(R.drawable.default_head)
                .error(R.drawable.default_head)
                .into(imgHead);
    }


    @OnClick({R.id.tv_edit, R.id.img_setting, R.id.ll_personal, R.id.ll_order, R.id.ll_collect, R.id.ll_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                startActivity(new Intent(getActivity(), MeActivity.class));
                break;
            case R.id.img_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.ll_personal:
                isLiverFiles();
                break;
            case R.id.ll_order:
                //                String baseUrl = BuildConfig.ENVIRONMENT ? "http://port.xiyuns.cn" : "http://d.xiyuns.cn";
                //                LoginBean user = (LoginBean) SharedPreferencesUtils.getBean(getActivity(), SharedPreferencesUtils.USER_INFO);
                //                String userCode = user.getUsercode();
                //                String url = "/shopcs.php/index/myorder/guid/" + userCode;
                //                Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                //                intent.putExtra("title", "我的订单");
                //                intent.putExtra("url", baseUrl + url);
                //                startActivity(intent);

                //原生订单
                startActivity(new Intent(getPageContext(), MyOrderListActivity.class));
                break;
            case R.id.ll_collect:
                //ToastUtils.showShort("该功能暂未开放，敬请期待");
                startActivity(new Intent(getPageContext(), MyProductCollectListActivity.class));
                break;
            case R.id.ll_coupon:
                //ToastUtils.showShort("该功能暂未开放，敬请期待");
                Intent intent = new Intent(getPageContext(), MyCouponListActivity.class);
                intent.putExtra("activity_id", "-1");
                startActivity(intent);
                break;
        }
    }


    /**
     * 是否是肝病档案
     */
    private void isLiverFiles() {
        Map<String, Object> map = new HashMap<>();
        XyUrl.okPost(XyUrl.IS_LIVER_FILE, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //获取基本
                IsLiverFilesBean isLiverFilesBean = JSONObject.parseObject(value, IsLiverFilesBean.class);
                Message message = getHandlerMessage();
                message.what = IS_LIVER_FILE;
                message.obj = isLiverFilesBean;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    /**
     * 获取医生信息
     */
    private void getDoctorInfo() {
        HashMap map = new HashMap<>();
        XyUrl.okPost(XyUrl.MY_DOCTOR, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                sendHandlerMessage(HAVE_BIND_DOCTOR_SUCCESS);
            }

            @Override
            public void onError(int error, String errorMsg) {
                sendHandlerMessage(HAVE_BIND_DOCTOR_ERROR);
            }
        });
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.main_home).init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }


    //SimpleImmersionOwner接口实现

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }


    @Override
    public void processHandlerMsg(Message msg) {
        Intent intent = null;
        switch (msg.what) {
            //已经绑定设备
            case HAVE_BIND_DEVICE:
                DeviceChangeBean deviceBean = (DeviceChangeBean) msg.obj;
                //保存血糖设备码
                String imei = deviceBean.getImei();
                SPStaticUtils.put("imei", imei);
                //保存血压设备码
                String snnum = deviceBean.getSnnum();
                SPStaticUtils.put("snnum", snnum);
                intent = new Intent(getPageContext(), MyBindDeviceListActivity.class);
                startActivity(intent);
                break;
            //没有绑定设备
            case BIND_DEVICE_NOT:
                PermissionUtils
                        .permission(PermissionConstants.CAMERA)
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(getPageContext(), ScanActivity.class);
                                intent.putExtra("type", "user");
                                startActivity(intent);
                            }

                            @Override
                            public void onDenied() {
                                ToastUtils.showShort("请允许使用相机权限");
                            }
                        }).request();
                break;
            case IS_LIVER_FILE:
                IsLiverFilesBean isLiverFilesBean = (IsLiverFilesBean) msg.obj;
                String archivestyle = isLiverFilesBean.getArchivestyle();
                if ("2".equals(archivestyle)) {
                    intent = new Intent(getPageContext(), MyLiverFilesActivity.class);
                } else {
                    intent = new Intent(getPageContext(), MySugarFilesActivity.class);
                }
                startActivity(intent);
                break;
            case GET_BANNER_LIST:

                break;
            case HAVE_BIND_DOCTOR_SUCCESS:
                //startActivity(new Intent(getPageContext(), MainActivity.class));
                EventBusUtils.post(new EventMessage<>(ConstantParam.HOME_TO_OUTSIDE));
                break;
            case HAVE_BIND_DOCTOR_ERROR:
                ToastUtils.showShort("暂无绑定医生");
                break;
        }
    }
}
