package com.lyd.modulemall.utils;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.lyd.baselib.utils.edittext.IdNumberKeyListener;
import com.lyd.modulemall.R;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;



/**
 * 描述:QMUIDialog封装的工具类
 * 作者: LYD
 * 所需参数: 无
 * 创建日期: 2018/10/22 16:18
 */
public class DialogUtils {

    private static volatile DialogUtils instance = null;

    private DialogUtils() {
    }

    /**
     * 懒汉单例(双重检查)
     *
     * @return
     */
    public static DialogUtils getInstance() {
        if (instance == null) {
            synchronized (DialogUtils.class) {
                if (instance == null) {
                    instance = new DialogUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 显示不可取消的Dialog
     *
     * @param context
     * @param titleInfo
     * @param messageInfo
     * @param isHaveCancel
     * @param callBack
     */
    public void showNotCancelDialog(Context context, String titleInfo, String messageInfo, boolean isHaveCancel, final DialogUtils.DialogCallBack callBack) {
        QMUIDialog.MessageDialogBuilder messageDialog = new QMUIDialog.MessageDialogBuilder(context);
        messageDialog.setActionDivider(1, R.color.qmui_config_color_separator, 0, 0);//添加分割线
        messageDialog.setTitle(titleInfo);//设置标题
        messageDialog.setMessage(messageInfo);//设置提示信息
        messageDialog.setCanceledOnTouchOutside(false);
        messageDialog.setCancelable(false);
        if (isHaveCancel) {
            messageDialog.addAction("取消", (dialog, index) -> dialog.dismiss());
        }
        messageDialog.addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE, (dialog, index) -> {
            callBack.execEvent();//设置确定的回调
            dialog.dismiss();
        });
        messageDialog.create(R.style.DialogThemeCommon).show();
    }

    /**
     * 显示提示信息:居中
     *
     * @param context     上下文
     * @param titleInfo   标题
     * @param messageInfo 提示信息
     * @param callBack    确定回调
     */
    public void showDialog(Context context, String titleInfo, String messageInfo, boolean isHaveCancel, final DialogUtils.DialogCallBack callBack) {
        showDialog(context, titleInfo, messageInfo, isHaveCancel, callBack, R.style.DialogThemeCommon);
    }

    /**
     * 显示提示信息
     *
     * @param context
     * @param titleInfo
     * @param messageInfo
     * @param callBack
     */
    public void showDialog(Context context, String titleInfo, String messageInfo, boolean isHaveCancel, final DialogUtils.DialogCallBack callBack, int style) {
        QMUIDialog.MessageDialogBuilder messageDialog = new QMUIDialog.MessageDialogBuilder(context);
        messageDialog.setActionDivider(1, R.color.qmui_config_color_separator, 0, 0);//添加分割线
        messageDialog.setTitle(titleInfo);//设置标题
        messageDialog.setMessage(messageInfo);//设置提示信息
        messageDialog.setCanceledOnTouchOutside(false);
        messageDialog.setCancelable(false);
        if (isHaveCancel) {
            messageDialog.addAction("取消", (dialog, index) -> dialog.dismiss());
        }
        messageDialog.addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE, (dialog, index) -> {
            callBack.execEvent();//设置确定的回调
            dialog.dismiss();
        });
        messageDialog.create(style).show();
    }

    /**
     * @param context
     * @param titleInfo
     * @param messageInfo
     * @param leftBtnInfo
     * @param rightBtnInfo
     * @param leftCallBack
     * @param rightCallBack
     */
    public void showCommonDialog(Context context,
                                 String titleInfo,
                                 String messageInfo,
                                 String leftBtnInfo,
                                 String rightBtnInfo,
                                 final DialogUtils.DialogCallBack leftCallBack,
                                 final DialogUtils.DialogCallBack rightCallBack) {
        QMUIDialog.MessageDialogBuilder messageDialog = new QMUIDialog.MessageDialogBuilder(context);
        messageDialog.setActionDivider(1, R.color.qmui_config_color_separator, 0, 0);//添加分割线
        messageDialog.setTitle(titleInfo);//设置标题
        messageDialog.setMessage(messageInfo);//设置提示信息
        messageDialog.addAction(leftBtnInfo, (dialog, index) -> {
            leftCallBack.execEvent();//设置确定的回调
            dialog.dismiss();
        });
        messageDialog.addAction(0, rightBtnInfo, QMUIDialogAction.ACTION_PROP_NEGATIVE, (dialog, index) -> {
            rightCallBack.execEvent();//设置确定的回调
            dialog.dismiss();
        });
        messageDialog.create(R.style.DialogThemeCommon).show();
    }

    /**
     * 显示输入Dialog
     *
     * @param context
     * @param title
     * @param hint
     * @param callBack
     */
    public void showEditTextDialog(Context context, String title, String hint, final DialogUtils.DialogInputCallBack callBack) {
        showEditTextDialog(context, title, hint, InputType.TYPE_CLASS_TEXT, callBack);
    }


    public void showDecimalNumberInputDialog(Context context, String title, String hint, final DialogUtils.DialogInputCallBack callBack) {
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
        builder.setTitle(title)
                .setPlaceholder(hint)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        String text = builder.getEditText().getText().toString().trim();
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort(hint);
                        } else {
                            callBack.execEvent(text);
                            dialog.dismiss();
                        }
                    }
                })
                .create(R.style.DialogThemeCommon).show();
        EditText editText = builder.getEditText();
        editText.setInputType(8194);
    }

    /**
     * 显示输入Dialog
     *
     * @param context
     * @param title
     * @param hint
     * @param type
     * @param callBack
     */
    public void showEditTextDialog(Context context, String title, String hint, int type, final DialogUtils.DialogInputCallBack callBack) {
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
        builder.setTitle(title)
                .setPlaceholder(hint)
                .setInputType(type)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        String text = builder.getEditText().getText().toString().trim();
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort(hint);
                        } else {
                            callBack.execEvent(text);
                            dialog.dismiss();
                        }
                    }
                })
                .create(R.style.DialogThemeCommon).show();

    }


    public void showEditTextIdNumberDialog(Context context, String title, String hint, final DialogUtils.DialogInputCallBack callBack) {
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(context);
        builder.setTitle(title)
                .setPlaceholder(hint)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        String text = builder.getEditText().getText().toString().trim();
                        if (TextUtils.isEmpty(text)) {
                            ToastUtils.showShort(hint);
                        } else {
                            callBack.execEvent(text);
                            dialog.dismiss();
                        }
                    }
                })
                .create(R.style.DialogThemeCommon).show();
        EditText editText = builder.getEditText();
        editText.setKeyListener(new IdNumberKeyListener());
    }

    public interface DialogCallBack {
        void execEvent();
    }

    public interface DialogInputCallBack {
        void execEvent(String text);
    }
}
