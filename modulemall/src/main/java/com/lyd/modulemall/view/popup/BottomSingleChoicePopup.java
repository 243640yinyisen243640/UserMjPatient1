package com.lyd.modulemall.view.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.lyd.modulemall.R;

import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.TranslationConfig;

public class BottomSingleChoicePopup extends BasePopupWindow {
    public BottomSingleChoicePopup(Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(view -> dismiss());
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

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_bottom_single_choice);
    }
}
