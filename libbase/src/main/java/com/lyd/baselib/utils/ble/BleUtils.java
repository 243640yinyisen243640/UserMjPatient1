package com.lyd.baselib.utils.ble;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

/**
 * 描述:    蓝牙相关工具类
 * 作者: LYD
 * 创建日期: 2019/7/15 17:09
 */
public class BleUtils {
    private static final String TAG = "BleUtils";

    /**
     * 获取ble状态
     *
     * @return
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static boolean getBleState() {
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        return bleAdapter.isEnabled();
    }

    /**
     * 静默开启蓝牙
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public static boolean openBle() {
        BluetoothAdapter bleAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean enable = bleAdapter.enable();
        return enable;
    }

    /**
     * 弹出对话框方式启动蓝牙
     * 在Fragment中调用
     *
     * @param fragment
     * @param requestCode
     */
    public static void openBle(Fragment fragment, int requestCode) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        fragment.startActivityForResult(enableBtIntent, requestCode);
    }

    /**
     * 弹出对话框方式启动蓝牙
     * 在Activity中调用
     *
     * @param activity
     * @param requestCode
     */
    public static void openBle(Activity activity, int requestCode) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, requestCode);
    }
}
