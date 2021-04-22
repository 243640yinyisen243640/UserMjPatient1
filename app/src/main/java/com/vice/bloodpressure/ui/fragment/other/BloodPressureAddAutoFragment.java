package com.vice.bloodpressure.ui.fragment.other;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanAndConnectCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.vice.bloodpressure.R;
import com.vice.bloodpressure.base.fragment.BaseLazyFragment;
import com.vice.bloodpressure.view.popu.SmartAddPopup;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述:自动添加血压(蓝牙)
 * 作者: LYD
 * 创建日期: 2019/7/8 16:12
 */
public class BloodPressureAddAutoFragment extends BaseLazyFragment {
    private static final String TAG = "BloodPressureAddAutoFragment";

    @BindView(R.id.img_add_left)
    ImageView imgAddLeft;
    @BindView(R.id.tv_add_left)
    TextView tvAddLeft;

    @BindView(R.id.img_add_center_left)
    ImageView imgAddCenterLeft;
    @BindView(R.id.tv_add_center_left)
    TextView tvAddCenterLeft;
    @BindView(R.id.img_add_center_right)
    ImageView imgAddCenterRight;
    @BindView(R.id.tv_add_center_right)
    TextView tvAddCenterRight;

    private BluetoothStateBroadcastReceiver mReceiver;

    //弹窗开始
    private SmartAddPopup smartAddPopup;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blood_pressure_auto;
    }

    @Override
    protected void init(View rootView) {
        initPopu();
    }

    private void initPopu() {
        smartAddPopup = new SmartAddPopup(getPageContext());
        TextView tvLook = smartAddPopup.findViewById(R.id.tv_look);
        ImageView imgDismiss = smartAddPopup.findViewById(R.id.img_dismiss);
        tvLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartAddPopup.dismiss();
            }
        });
        imgDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartAddPopup.dismiss();
            }
        });
    }

    @Override
    public void loadData() {
        boolean bleState = getBleState();
        setScanRule();
        startScan();
        registerBluetoothReceiver();
        if (!bleState) {
            ToastUtils.showShort("请打开蓝牙");
        } else {
            imgAddLeft.setImageResource(R.drawable.smart_add_left_checked);
            tvAddLeft.setText("蓝牙已经打开");
            setScanRule();
            startScan();
        }
    }

    /**
     * 开始扫描
     */
    private void startScan() {
        BleManager.getInstance().scanAndConnect(new BleScanAndConnectCallback() {
            @Override
            public void onScanFinished(BleDevice scanResult) {
                //扫描结束，结果即为扫描到的第一个符合扫描规则的BLE设备，如果为空表示未搜索到（主线程）
                if (scanResult == null) {
                    smartAddPopup.showPopupWindow();
                }
            }

            @Override
            public void onStartConnect() {
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                //连接失败
                smartAddPopup.showPopupWindow();
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                //连接成功
                imgAddCenterLeft.setImageResource(R.drawable.smart_add_center_left_checked);
                tvAddCenterLeft.setText("血压计已打开");
                imgAddCenterRight.setImageResource(R.drawable.smart_add_center_right_checked);
                tvAddCenterRight.setText("连接成功");
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
            }

            @Override
            public void onScanStarted(boolean success) {
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
            }
        });
    }

    /**
     * 设置扫描规则
     */
    private void setScanRule() {
        //弹窗结束
        int scanTime = 2 * 1000;
        BleScanRuleConfig scanRuleConfig =
                new BleScanRuleConfig.Builder()
                        //.setServiceUuids("")//只扫描指定的服务的设备，可选
                        //.setDeviceMac(mac)//只扫描指定mac的设备，可选
                        .setDeviceName(true, "ClinkBlood")//只扫描指定广播名的设备，可选
                        .setAutoConnect(true)//连接时的autoConnect参数，可选，默认false
                        .setScanTimeOut(scanTime)//扫描超时时间，可选，默认10秒
                        .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }


    /**
     * 获取ble状态
     *
     * @return
     */
    private boolean getBleState() {
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        return bleAdapter.isEnabled();
    }

    /**
     * 注册
     */
    private void registerBluetoothReceiver() {
        if (mReceiver == null) {
            mReceiver = new BluetoothStateBroadcastReceiver();
        }
        IntentFilter stateChangeFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        IntentFilter connectedFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter disConnectedFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        getContext().registerReceiver(mReceiver, stateChangeFilter);
        getContext().registerReceiver(mReceiver, connectedFilter);
        getContext().registerReceiver(mReceiver, disConnectedFilter);
    }

    /**
     * 取消注册
     */
    private void unregisterBluetoothReceiver() {
        if (mReceiver != null) {
            getContext().unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBluetoothReceiver();
    }


    @OnClick({R.id.ll_get_blood_pressure_machine, R.id.bt_get_again, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_get_blood_pressure_machine:
                break;
            case R.id.bt_get_again:
                break;
            case R.id.bt_save:
                break;
        }
    }

    @Override
    public void processHandlerMsg(Message msg) {

    }


    /**
     * 本地广播接收器
     */
    private class BluetoothStateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            switch (action) {
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                    //Toast.makeText(context , "蓝牙设备:" + device.getName() + "已链接", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    //Toast.makeText(context , "蓝牙设备:" + device.getName() + "已断开", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_TURNING_ON:
                            break;
                        case BluetoothAdapter.STATE_ON:
                            ToastUtils.showShort("蓝牙已打开");
                            imgAddLeft.setImageResource(R.drawable.smart_add_left_checked);
                            tvAddLeft.setText("蓝牙已打开");
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            ToastUtils.showShort("蓝牙已关闭");
                            imgAddLeft.setImageResource(R.drawable.smart_add_left_unchecked);
                            tvAddLeft.setText("请打开蓝牙");
                            break;
                    }
                    break;
            }
        }
    }
}
