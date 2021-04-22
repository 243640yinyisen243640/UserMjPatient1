package com.vice.bloodpressure.ui.activity.patienteducation;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
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
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.utils.GlideUtils;
import com.vice.bloodpressure.view.MyWebView;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class PatientEducationVideoActivity extends BaseActivity {
    @BindView(R.id.vp_detail)
    JzvdStd jzvdStd;
    @BindView(R.id.webview)
    MyWebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        setVideo();
        setWebView();
    }


    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_patient_education_video, contentLayout, false);
        return view;
    }


    private void setWebView() {
        String url = getIntent().getStringExtra("url");
        webview.loadUrl(url);
        //使用WebView加载网页,而不是浏览器
        webview.setWebViewClient(getWebViewClient());
        webview.setWebChromeClient(getWebChromeClient());
    }

    private void setVideo() {
        String videoUrl = getIntent().getStringExtra("videoUrl");
        String title = getIntent().getStringExtra("title");
        jzvdStd.setUp(videoUrl, title);
        GlideUtils.loadCover(jzvdStd.posterImageView, videoUrl);
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


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}