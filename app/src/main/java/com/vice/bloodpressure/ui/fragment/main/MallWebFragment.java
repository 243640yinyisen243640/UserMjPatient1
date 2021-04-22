package com.vice.bloodpressure.ui.fragment.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.imuxuan.floatingview.FloatingView;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.view.MyWebView;

import butterknife.BindView;

/**
 * 描述: 商城
 * 作者: LYD
 * 创建日期: 2019/4/18 14:53
 */

public class MallWebFragment extends BaseFragment implements SimpleImmersionOwner {
    private static final String TAG = "MallFragment";
    @BindView(R.id.wv)
    MyWebView webView;
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_web;
    }

    @Override
    protected void init(View rootView) {
        FloatingView.get().remove();
        String url = getArguments().getString("url");
        initWebView(url);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String url) {
        WebSettings webSettings = webView.getSettings();
        //设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        //是否开启本地DOM存储
        webSettings.setDomStorageEnabled(true);
        //设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //禁用文字缩放
        webSettings.setTextZoom(100);
        //允许缓存，设置缓存位置
        webSettings.setAppCacheEnabled(false);
        webSettings.setAppCachePath(getPageContext().getDir("appcache", 0).getPath());
        //允许WebView使用File协议
        webSettings.setAllowFileAccess(true);
        //默认不允许加载本地文件
        webSettings.setAllowFileAccessFromFileURLs(true);
        //设置支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //设置UA
        webSettings.setUserAgentString(webSettings.getUserAgentString() + "xyApp/" + AppUtils.getAppVersionName());
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        //在Android5.0上WebView默认不允许加载Http与Https混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        WebViewClient webViewClient = new WebViewClient() {

            /**
             * 重定向URL请求，返回true表示拦截此url，返回false表示不拦截此url。
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("weixin://wap/pay?")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else if (url.contains("platformapi/startApp")) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        startActivity(intent);
                    } catch (Exception e) {
                        ToastUtils.showShort("设备未安装支付宝");
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        };
        WebChromeClient webChromeClient = new WebChromeClient() {
            /**
             * 允许JS弹窗
             * @param view
             * @param url
             * @param message
             * @param result
             * @return
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        };
        webView.loadUrl(url);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
        //小心这个！！！暂停整个 WebView所有布局、解析、JS。
        webView.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();
    }

    //SimpleImmersionOwner接口实现

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

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
    public void initImmersionBar() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(true).statusBarColor(R.color.white).init();
    }

    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }
}
