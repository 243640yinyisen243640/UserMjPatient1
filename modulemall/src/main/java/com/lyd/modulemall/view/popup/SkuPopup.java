package com.lyd.modulemall.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.lyd.modulemall.R;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

/**
 * 描述:  sku
 * 作者: LYD
 * 创建日期: 2021/3/10 17:15
 */
public class SkuPopup extends BasePopupWindow {

    public SkuPopup(Context context) {
        super(context);
        ImageView imgClose = findViewById(R.id.img_close);
        imgClose.setOnClickListener(view -> dismiss());
        setPopupGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_sku);
    }


    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.FROM_BOTTOM)
                .toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation()
                .withTranslation(TranslationConfig.TO_BOTTOM)
                .toDismiss();
    }
}
