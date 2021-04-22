package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.vice.bloodpressure.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  下线弹窗
 * 作者: LYD
 * 创建日期: 2020/10/30 14:37
 */
public class LoginOutPopup extends BasePopupWindow {

    //    public static boolean is_Show = false;

    public LoginOutPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_login_out);
    }

    //    @Override
    //    public void showPopupWindow() {
    //        if (is_Show) {
    //            return;
    //        }
    //        super.showPopupWindow();
    //        is_Show = true;
    //    }
    //
    //    @Override
    //    public void dismiss() {
    //        super.dismiss();
    //        is_Show = false;
    //    }
}
