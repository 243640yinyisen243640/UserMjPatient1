package com.lyd.baselib.widget.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.view.ContextThemeWrapper;

/**
 * 描述: 基础WebView
 * 作者: LYD
 * 创建日期: 2020/7/22 16:27
 */
public  class MyWebView extends WebView {

    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(getFixedContext(context), attrs, defStyleAttr, 0);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        WebSettings settings = getSettings();
        //如果访问的页面中要与Javascript交互，则WebView必须设置支持Javascript
        settings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        settings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);

        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        settings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);

        //其他细节操作
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        //设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //解决 Android 5.0 上 WebView 默认不允许加载 Http 与 Https 混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //不显示滚动条
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }


    /**
     * 修复原生 WebView 和 AndroidX 在 Android 5.x 上面崩溃的问题
     * doc：https://stackoverflow.com/questions/41025200/android-view-inflateexception-error-inflating-class-android-webkit-webview
     */
    public static Context getFixedContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new ContextThemeWrapper(context, context.getTheme());
        }
        return context;
    }

    /**
     * 获取当前的 url
     *
     * @return 返回原始的 url，因为有些url是被WebView解码过的
     */
    @Override
    public String getUrl() {
        String originalUrl = super.getOriginalUrl();
        //避免开始时同时加载两个地址而导致的崩溃
        if (originalUrl != null) {
            return originalUrl;
        }
        return super.getUrl();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeTimers();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimers();
    }

    public void onDestroy() {
        ((ViewGroup) getParent()).removeView(this);
        //清除历史记录
        clearHistory();
        //停止加载
        stopLoading();
        //加载一个空白页
        loadUrl("about:blank");
        setWebViewClient(null);
        setWebChromeClient(null);
        //移除WebView所有的View对象
        removeAllViews();
        //销毁此的WebView的内部状态
        destroy();
    }

}