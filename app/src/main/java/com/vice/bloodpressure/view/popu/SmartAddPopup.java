package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.vice.bloodpressure.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:   智能添加弹窗
 * 作者: LYD
 * 创建日期: 2019/7/11 14:39
 */
public class SmartAddPopup extends BasePopupWindow {
    public SmartAddPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_smart_add);
    }


}
