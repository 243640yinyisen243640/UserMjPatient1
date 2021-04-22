package com.lyd.baselib.utils.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 描述: 蓝牙状态监听 广播接受者
 * 作者: LYD
 * 创建日期: 2019/12/26 9:32
 */
public class BleStatusBroadcastReceiver extends BroadcastReceiver {
    private BleStatusChangeListener listener;

    public BleStatusBroadcastReceiver(BleStatusChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        switch (action) {
            case BluetoothDevice.ACTION_ACL_CONNECTED:
                //ToastUtils.showShort("蓝牙设备:" + device.getName() + "已连接");
                break;
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                //ToastUtils.showShort("蓝牙设备:" + device.getName() + "已断开");
                break;
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueState) {
                    case BluetoothAdapter.STATE_OFF:
                        listener.onBleClose();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        //listener.onBleOpen();
                        break;
                }
                break;
        }
    }

    /**
     * 蓝牙状态监听器
     */
    public interface BleStatusChangeListener {
        //蓝牙打开
        //void onBleOpen();

        //蓝牙关闭
        void onBleClose();
    }
}
