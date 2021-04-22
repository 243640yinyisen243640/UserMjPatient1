package com.vice.bloodpressure.ui.activity.patienteducation;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.http.SslError;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.provider.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.view.MyWebView;

import butterknife.BindView;
import butterknife.OnClick;

public class PatientEducationAudioActivity extends BaseHandlerActivity {
    @BindView(R.id.img_audio)
    ImageView imgAudio;
    @BindView(R.id.sb)
    SeekBar seekBar;
    @BindView(R.id.tv_progress_time)
    TextView tvProgressTime;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.webview)
    MyWebView webView;
    private int clickCount;
    private TimerTaskManager taskManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        initProgress();
        setWebView();
        String duration = getIntent().getStringExtra("audioDuration");
        tvTotalTime.setText(duration);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_patient_education_audio, contentLayout, false);
        return view;
    }


    /**
     * 格式化毫秒为时长
     *
     * @param time
     * @return
     */
    public static String formatMusicTime(long time) {
        String min = (time / (1000 * 60)) + "";
        String second = (time % (1000 * 60) / 1000) + "";
        if (min.length() < 2) {
            min = 0 + min;
        }
        if (second.length() < 2) {
            second = 0 + second;
        }
        return min + ":" + second;
    }
    private void setWebView() {
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        //使用WebView加载网页,而不是浏览器
        webView.setWebViewClient(getWebViewClient());
        webView.setWebChromeClient(getWebChromeClient());
    }

    private void initProgress() {
        //未开始播放
        seekBar.setEnabled(false);
        taskManager = new TimerTaskManager();
        taskManager.setUpdateProgressTask(new Runnable() {
            @Override
            public void run() {
                //设置进度条
                //总时长
                long duration = StarrySky.with().getDuration();
                //播放位置
                long position = StarrySky.with().getPlayingPosition();
                //缓冲位置
                long buffered = StarrySky.with().getBufferedPosition();
                if (seekBar.getMax() != duration) {
                    seekBar.setMax((int) duration);
                }
                seekBar.setProgress((int) position);
                seekBar.setSecondaryProgress((int) buffered);

                //设置进度文字
                tvProgressTime.setText(formatMusicTime(position));
            }
        });


        //设置滑动监听
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                StarrySky.with().seekTo(seekBar.getProgress());
            }
        });

    }

    @Override
    public void processHandlerMsg(Message msg) {

    }

    @OnClick(R.id.img_audio)
    public void onViewClicked() {
        seekBar.setEnabled(true);
        clickCount = clickCount + 1;
        AnimationDrawable am = (AnimationDrawable) imgAudio.getBackground();
        if (isOddNumber(clickCount)) {
            am.start();
            int id = getIntent().getIntExtra("id", 0);
            String audioUrl = getIntent().getStringExtra("audioUrl");
            //开始播放
            SongInfo info = new SongInfo();
            info.setSongId(id + "");
            info.setSongUrl(audioUrl);
            StarrySky.with().playMusicByInfo(info);
            //开始更新进度
            taskManager.startToUpdateProgress();
        } else {
            am.stop();
            am.selectDrawable(0);
            //暂停播放
            StarrySky.with().pauseMusic();
            //结束更新进度
            taskManager.stopToUpdateProgress();
        }
    }

    /**
     * 判断是否是奇数
     *
     * @param number 数值
     * @return
     */
    public Boolean isOddNumber(int number) {
        if ((number & 1) == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 停止播放
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        StarrySky.with().stopMusic();
        taskManager.removeUpdateProgressTask();
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