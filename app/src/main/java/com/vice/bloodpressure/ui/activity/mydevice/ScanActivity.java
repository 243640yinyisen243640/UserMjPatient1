package com.vice.bloodpressure.ui.activity.mydevice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseHandlerActivity;
import com.vice.bloodpressure.bean.MyDoctorBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.homesign.MyQRCodeActivity;
import com.vice.bloodpressure.ui.activity.hospital.DoctorDetailsActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import me.devilsen.czxing.code.BarcodeFormat;
import me.devilsen.czxing.util.BarCodeUtil;
import me.devilsen.czxing.view.ScanBoxView;
import me.devilsen.czxing.view.ScanListener;
import me.devilsen.czxing.view.ScanView;

/**
 * 描述: 扫描页面
 * 作者: LYD
 * 创建日期: 2019/3/22 15:39
 */
public class ScanActivity extends BaseHandlerActivity implements ScanListener {
    private static final String TAG = "ScanActivity";
    private static final int GET_DOCID = 10010;
    @BindView(R.id.scan_view)
    ScanView mScanView;
    @BindView(R.id.img_my_qrcode)
    ImageView imgMyQrcode;


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_scan, contentLayout, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsShowMyQrCode();
        initScan();
        initTitle();
    }

    private void setIsShowMyQrCode() {
        String from = getIntent().getStringExtra("type");
        if ("home".equals(from)) {
            imgMyQrcode.setVisibility(View.VISIBLE);
        } else {
            imgMyQrcode.setVisibility(View.GONE);
        }
    }

    private void initTitle() {
        setTitle("扫一扫");
        getLlMore().removeAllViews();
        TextView tvMore = new TextView(getPageContext());
        tvMore.setTextColor(ColorUtils.getColor(R.color.main_home));
        tvMore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        tvMore.setPadding(10, 5, 10, 5);
        getLlMore().addView(tvMore);
        tvMore.setText("手动绑定");
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getPageContext(), MyDeviceListActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * 扫描初始化
     */
    private void initScan() {
        ScanBoxView scanBox = mScanView.getScanBox();
        //设置扫码框上下偏移量，可以为负数
        scanBox.setBoxTopOffset(-BarCodeUtil.dp2px(this, 50));
        //设置边框大小
        scanBox.setBorderSize(BarCodeUtil.dp2px(this, 250), BarCodeUtil.dp2px(this, 250));
        //设置扫码框四周的颜色
        scanBox.setMaskColor(Color.parseColor("#9C272626"));
        //scanBox.setHorizontalScanLine();
        scanBox.setScanNoticeText("将二维码/条形码放入框内，即可自动扫描。");
        //获取扫码回调
        mScanView.setScanListener(this);
    }

    @OnClick({R.id.img_my_qrcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_my_qrcode:
                startActivity(new Intent(getPageContext(), MyQRCodeActivity.class));
                break;
        }
    }

    @Override
    public void onScanSuccess(String resultText, BarcodeFormat format) {
        if (resultText.startsWith("guid")) {
            String result = resultText.substring(4);
            //扫医生二维码
            getDocidFormGuid(result);
        } else if (resultText.contains("IMEI=")) {
            //扫条形码
            String startText = resultText.substring(0, resultText.indexOf("IMEI="));
            resultText = resultText.substring(startText.length() + 5);
            Intent intent = new Intent(getPageContext(), InputImeiActivity.class);
            intent.putExtra("imei", resultText);
            intent.putExtra("type", "3");
            startActivity(intent);
        } else {
            //扫产品二维码
            Intent intent = new Intent(getPageContext(), InputImeiActivity.class);
            intent.putExtra("imei", resultText);
            intent.putExtra("type", "3");
            startActivity(intent);
        }
    }

    private void getDocidFormGuid(String result) {
        HashMap map = new HashMap<>();
        map.put("guid", result);
        XyUrl.okPost(XyUrl.GET_QRCODE_INFO, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                MyDoctorBean doctor = JSONObject.parseObject(value, MyDoctorBean.class);
                Message message = Message.obtain();
                message.what = GET_DOCID;
                message.obj = doctor;
                sendHandlerMessage(message);
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    public void onOpenCameraError() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //打开后置摄像头开始预览，但是并未开始识别
        mScanView.openCamera();
        //显示扫描框，并开始识别
        mScanView.startScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanView.stopScan();
        //关闭摄像头预览，并且隐藏扫描框
        mScanView.closeCamera();
    }

    @Override
    protected void onDestroy() {
        //销毁二维码扫描控件
        mScanView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void processHandlerMsg(Message msg) {
        switch (msg.what) {
            case GET_DOCID:
                MyDoctorBean data = (MyDoctorBean) msg.obj;
                int docid = data.getDocid();
                Intent intent = new Intent(Utils.getApp(), DoctorDetailsActivity.class);
                intent.putExtra("docid", docid + "");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
                break;
        }
    }
}
