package com.vice.bloodpressure.ui.activity.mydevice;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.activity.BaseActivity;
import com.vice.bloodpressure.net.OkHttpCallBack;
import com.vice.bloodpressure.net.XyUrl;
import com.vice.bloodpressure.utils.DialogUtils;
import com.wei.android.lib.colorview.view.ColorButton;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:我的设备
 * 作者: LYD
 * 创建日期: 2019/12/23 10:24
 */
public class MyBindDeviceActivity extends BaseActivity {
    @BindView(R.id.tv_imei)
    TextView tvImei;
    @BindView(R.id.bt_unbind)
    ColorButton btUnbind;
    private String deviceNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的设备");
        int position = getIntent().getIntExtra("position", -1);
        if (0 == position) {
            deviceNumber = SPStaticUtils.getString("imei");
        } else if (1 == position) {
            deviceNumber = SPStaticUtils.getString("snnum");
        }
        tvImei.setText("设备号:" + deviceNumber);
    }


    @Override
    protected View addContentLayout() {
        return getLayoutInflater().inflate(R.layout.activity_my_device, contentLayout, false);
    }


    private void toUnbind() {
        DialogUtils.getInstance().showDialog(getPageContext(), "提示", "确定要解绑设备?", true, this::toDoUnbind);
    }

    private void toDoUnbind() {
        //执行
        HashMap<String, Object> map = new HashMap<>();
        map.put("imei", deviceNumber);
        XyUrl.okPostSave(XyUrl.DEVICE_UN_BIND, map, new OkHttpCallBack<String>() {
            @Override
            public void onSuccess(String value) {
                //清除本地设备码
                clearLocalDeviceNumber();
                ToastUtils.showShort("获取成功");
                finish();
            }

            @Override
            public void onError(int error, String errorMsg) {
                ToastUtils.showShort(errorMsg);
            }
        });
    }

    private void clearLocalDeviceNumber() {
        int position = getIntent().getIntExtra("position", -1);
        if (0 == position) {
            SPStaticUtils.put("imei", "");
        } else if (1 == position) {
            SPStaticUtils.put("snnum", "");
        }
    }


    @OnClick(R.id.bt_unbind)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_unbind:
                toUnbind();
                break;
        }
    }
}
