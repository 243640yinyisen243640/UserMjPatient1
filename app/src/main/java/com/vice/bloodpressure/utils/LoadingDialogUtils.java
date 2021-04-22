package com.vice.bloodpressure.utils;

import android.app.Activity;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.lang.ref.WeakReference;


/**
 * 加载
 */
public class LoadingDialogUtils {
    private static QMUITipDialog loadingDialog;
    private static WeakReference<Activity> reference;

    public static void init(Activity act) {
        if (loadingDialog == null || reference == null || reference.get() == null || reference.get().isFinishing()) {
            reference = new WeakReference<>(act);
            loadingDialog = new QMUITipDialog.Builder(reference.get())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
        }
    }


    /**
     * 显示等待框
     */
    public static void show(Activity act) {
        init(act);
        loadingDialog.show();
    }

    /**
     * 隐藏等待框
     */
    public static void dismiss() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


    /**
     * 注销加载框，避免发生内存泄露
     */
    public static void unInit() {
        dismiss();
        loadingDialog = null;
        reference = null;
    }
}
