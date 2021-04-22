package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.vice.bloodpressure.R;

import razerdp.basepopup.BasePopupWindow;

public class FollowUpVisitSavePopup extends BasePopupWindow {
    public FollowUpVisitSavePopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        ImageView imgDismiss = findViewById(R.id.img_dismiss);
        imgDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_follow_up_visit_save);
    }
}
