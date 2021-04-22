package com.vice.bloodpressure.ui.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.fastjson.JSONObject;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.base.activity.BaseWebViewActivity;
import com.vice.bloodpressure.bean.UpdateBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.UpdateUtils;
import com.vice.bloodpressure.view.NumberProgressBar;
import com.vice.bloodpressure.view.popu.UpdatePopup;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseHandlerActivity implements View.OnClickListener, OnDownloadListener {
    private static final int GET_UPDATE_DATA = 10010;
    private static final String TAG = "AboutUsActivity";
    @BindView(R.id.tv_question_feed_back)
    TextView tvQuestionFeedBack;
    @BindView(R.id.tv_connect_us)
    TextView tvConnectUs;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.tv_user_agreement)
    TextView tvUserAgreement;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    //更新开始
    //更新进度
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
    //关闭按钮和线(显示隐藏)
    private LinearLayout llUpdateCancel;
    //关闭按钮(点击取消)
    private AppCompatImageView ivUpdateClose;
    //更新进度
    //更新网址
    private String updateUrl;
    private File updateApk;
    //更新结束

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关于我们");
        initUpdatePopup();
        setVersionName();
    }

    private void setVersionName() {
        String appVersionName = AppUtils.getAppVersionName();
        tvVersionName.setText("v" + appVersionName);
    }

    private void initUpdatePopup() {
        updatePopup = new UpdatePopup(Utils.getApp());
        tvUpdateName = updatePopup.findViewById(R.id.tv_update_name);
        tvUpdateSize = updatePopup.findViewById(R.id.tv_update_size);
        tvUpdateContent = updatePopup.findViewById(R.id.tv_update_content);
        pbUpdateProgress = updatePopup.findViewById(R.id.pb_update_progress);
        tvUpdateUpdate = updatePopup.findViewById(R.id.tv_update_update);
        llUpdateCancel = updatePopup.findViewById(R.id.ll_update_cancel);
        ivUpdateClose = updatePopup.findViewById(R.id.iv_update_close);
        tvUpdateUpdate.setOnClickListener(this);
        ivUpdateClose.setOnClickListener(this);
    }

    @Override
    protected View addContentLayout() {
        View view = getLayoutInflater().inflate(R.layout.activity_about_us, contentLayout, false);
        return view;
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
                Message msg = getHandlerMessage();
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
     * 显示更新弹窗
     *
     * @param data
     */
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

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_UPDATE_DATA:
                UpdateBean data = (UpdateBean) msg.obj;
                int localVersionCode = AppUtils.getAppVersionCode();
                int netVersionCode = data.getVersion();
                if (localVersionCode < netVersionCode) {
                    toShowUpdateDialog(data);
                } else {
                    ToastUtils.showShort("当前版本已是最新版本");
                }
                break;
        }
    }

    @OnClick({R.id.tv_question_feed_back, R.id.tv_connect_us, R.id.ll_update, R.id.tv_user_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_question_feed_back:
                startActivity(new Intent(getPageContext(), FeedBackActivity.class));
                break;
            case R.id.tv_connect_us:
                startActivity(new Intent(getPageContext(), ConnectUsActivity.class));
                break;
            case R.id.ll_update:
                getUpdate();
                break;
            case R.id.tv_user_agreement:
                Intent intent = new Intent(getPageContext(), BaseWebViewActivity.class);
                intent.putExtra("title", "用户服务协议");
                intent.putExtra("url", "file:///android_asset/user_protocol.html");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //更新升级按钮
            case R.id.tv_update_update:
                //判断字体获取状态
                String text = (String) tvUpdateUpdate.getText();
                switch (text) {
                    case "升级":
                    case "下载失败":
                        //下载并安装
                        //toDownLoadAndInstallApk();
                        UpdateUtils.downloadAndInstall(updateUrl, this);
                        //String state = Environment.getExternalStorageState();
                        break;
                    case "下载中":
                        //没有动作
                        break;
                    case "立即安装":
                        //安装apk
                        AppUtils.installApp(updateApk);
                        //ApkUtil.installApk(getPageContext(), "com.xy.xydoctor.FileProvider", updateApk);
                        break;
                }
                break;
            //关闭按钮
            case R.id.iv_update_close:
                updatePopup.dismiss();
                break;
        }
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

    }
}