package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.vice.bloodpressure.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  用药添加
 * 作者: LYD
 * 创建日期: 2020/9/16 15:35
 */
public class MedicineAddPopup extends BasePopupWindow {
    public MedicineAddPopup(Context context) {
        super(context);
        setBackgroundColor(Color.TRANSPARENT);
        //setOutSideDismiss(false);
        //setOutSideTouchable(true);
        //getPopupWindow().setFocusable(false);
        //setFitSize(false);


        //获取控件高度
        int screenHeight = ScreenUtils.getScreenHeight();
        int maxHeight = screenHeight - 450;
        setMaxHeight(maxHeight);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_medicine_add);
    }
}
