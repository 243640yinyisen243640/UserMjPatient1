package com.vice.bloodpressure.ui.fragment.smarteducation;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseFragment;
import com.vice.bloodpressure.view.MyWebView;

import butterknife.BindView;


/**
 * 描述:  智能教育 详情之内容Fragment
 * 作者: LYD
 * 创建日期: 2020/8/20 9:46
 */
public class SmartEducationClassDetailLeftFragment extends BaseFragment {
    @BindView(R.id.webview)
    MyWebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_smart_education_class_detail_left_text;
    }

    @Override
    protected void init(View rootView) {
        setWebView();
    }

    private void setWebView() {
        String url = getArguments().getString("webLink");
        webView.loadUrl(url);
        //使用WebView加载网页,而不是浏览器
        webView.setWebViewClient(getWebViewClient());
        webView.setWebChromeClient(getWebChromeClient());
    }

    @Override
    public void processHandlerMsg(Message msg) {

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
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
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
            }
        };
        return webChromeClient;
    }
}
