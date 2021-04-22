package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.vice.bloodpressure.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:检验数据血常规
 * 作者:LYD
 * 创建日期:2019/9/10 14:15
 */
public class CheckDataBloodAndPissCommonPopup extends BasePopupWindow {
    public CheckDataBloodAndPissCommonPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_check_data_blood_common);
    }
}
