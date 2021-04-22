package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.vice.bloodpressure.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  三天血糖未测弹窗
 * 作者: LYD
 * 创建日期: 2020/10/19 14:49
 */
public class WarnAddBloodPopup extends BasePopupWindow {
    public WarnAddBloodPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_warn_add_blood);
    }
}
