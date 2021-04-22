package com.lyd.modulemall.ui;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.lyd.baselib.base.activity.BaseViewBindingActivity;
import com.lyd.baselib.widget.view.MyWebView;
import com.lyd.modulemall.R;
import com.lyd.modulemall.databinding.ActivityNewWebviewBinding;


/**
 * 描述: 基础webview
 * 作者: LYD
 * 创建日期: 2019/3/28 11:11
 */
public class BaseWebViewActivity extends BaseViewBindingActivity<ActivityNewWebviewBinding> {
    private static final String TAG = "BaseWebViewActivity";
    private MyWebView webView;
    

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        KeyboardUtils.fixAndroidBug5497(this);
        String url = getIntent().getStringExtra("url");
        setWebView();
        setPageTitle();
        loadUrl();
        initWebView(url);
    }


    /**
     * 设置标题
     */
    private void setPageTitle() {
        String title = getIntent().getStringExtra("title");
        setTitle(title);
    }

    /**
     * 加载网页
     */
    private void loadUrl() {
        String url = getIntent().getStringExtra("url");
        initWebView(url);
    }

    /**
     * 添加WebView
     * 不设置的话,有的网页高度不够!!!
     */
    private void setWebView() {
        //初始化
        webView = new MyWebView(getPageContext());
        //添加
        binding.llBaseWebview.addView(webView);
        //动态设置宽高为充满
        ViewGroup.LayoutParams layoutParams = webView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        webView.setLayoutParams(layoutParams);
    }

    /**
     * 开始加载
     *
     * @param url
     */
    private void initWebView(String url) {
        //水平不显示
        webView.setHorizontalScrollBarEnabled(false);
        //垂直不显示
        webView.setVerticalScrollBarEnabled(false);
        //加载Html链接
        webView.loadUrl(url);
        //加载Html标签
        //webView.loadDataWithBaseURL(null, getNewContent(url), "text/html", "utf-8", null);
        //使用WebView加载网页,而不是浏览器
        webView.setWebViewClient(getWebViewClient());
        webView.setWebChromeClient(getWebChromeClient());
    }


    /**
     * 处理各种通知 & 请求事件
     *
     * @return
     */
    private WebViewClient getWebViewClient() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.pbWebProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.pbWebProgress.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }
        };
        return webViewClient;
    }

    /**
     * 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
     *
     * @return
     */
    private WebChromeClient getWebChromeClient() {
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            /**
             * 收到加载进度变化
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    binding.pbWebProgress.setVisibility(View.GONE);
                } else {
                    binding.pbWebProgress.setProgress(newProgress);
                    binding.pbWebProgress.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }
        };
        return webChromeClient;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //用来确认WebView里是否还有可回退的历史记录。
        //通常我们会在WebView里重写返回键的点击事件，通过该方法判断WebView里是否还有历史记录，若有则返回上一页。
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
