package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.vice.bloodpressure.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述: 智能教育首页弹窗
 * (弹窗顺序教育 饮食 运动)
 * 作者: LYD
 * 创建日期: 2020/10/19 14:44
 */
public class HomeGuidePopup extends BasePopupWindow {
    private static final String TAG = "";

    public HomeGuidePopup(Context context) {
        super(context);
        setOverlayStatusbar(false);
        ImageView imgHomeGuide = findViewById(R.id.img_home_guide);
        imgHomeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_home_guide);
    }
}
