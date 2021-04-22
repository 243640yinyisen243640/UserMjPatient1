package com.vice.bloodpressure.ui.activity.mydevice;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.lyd.baselib.bean.LoginBean;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.ui.activity.MainActivity;
import com.lyd.baselib.utils.SharedPreferencesUtils;
import com.wei.android.lib.colorview.view.ColorEditText;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述: Imei输入页面
 * 作者: LYD
 * 创建日期: 2019/3/22 15:48
 */
public class InputImeiActivity extends BaseActivity {
    private static final String TAG = "InputImeiActivity";
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.et_imei)
    ColorEditText etImei;
    @BindView(R.id.tv_hint)
    TextView tvHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIMEI();
        setType();
    }

    private void setType() {
        String type = getIntent().getStringExtra("type");
        switch (type) {
            case "1":
                setTitle("手动输入IMEI号");
                imgBg.setImageResource(R.drawable.imei_bg);
                tvHint.setText("请确定您输入正确的IMEI号码");
                etImei.setHint("请输入IMEI号码");
                break;
            case "2":
                setTitle("手动输入SN号");
                imgBg.setImageResource(R.drawable.sn_bg);
                tvHint.setText("请确定您输入正确的SN号码");
                etImei.setHint("请输入SN号码");
                break;
            case "4":
                setTitle("手动输入SN号");
                imgBg.setImageResource(R.drawable.bp_sn_bg);
                tvHint.setText("请确定您输入正确的SN号码");
                etImei.setHint("请输入SN号码");
                break;
            case "3":
                setTitle("绑定设备号");
                imgBg.setImageResource(R.drawable.default_scan_bg);
                tvHint.setText("请确定您输入正确的设备号");
                etImei.setHint("请输入设备号");
                break;
        }
    }

    private void setIMEI() {
        String imei = getIntent().getStringExtra("imei");
        etImei.setText(imei);
        etImei.setSelection(imei.length());
    }

    @Override
    protected View addContentLayout() {
        View layout = getLayoutInflater().inflate(R.layout.activity_input_imei, contentLayout, false);
        return layout;
    }


    @OnClick(R.id.bt_sure)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_sure:
                toBind();
                break;
        }
    }

    private void toBind() {
        String imei = etImei.getText().toString().trim();
        if (TextUtils.isEmpty(imei)) {
            ToastUtils.showShort("请输入设备号");
            return;
        }
        //执行
        LoginBean userLogin = (LoginBean) SharedPreferencesUtils.getBean(this, SharedPreferencesUtils.USER_INFO);
        HashMap<String, Object> map = new HashMap<>();
        map.put("access_token", userLogin.getToken());
        map.put("imei", imei);
        XyUrl.okPostSave(XyUrl.DEVICE_BIND, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                ToastUtils.showShort("获取成功");
                ActivityUtils.finishToActivity(MainActivity.class, false);
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });

    }
}
