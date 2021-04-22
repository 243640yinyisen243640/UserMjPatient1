package com.vice.bloodpressure.imp;

import android.view.View;

import com.vice.bloodpressure.bean.im.ImWarningMessage;


public interface ImWarningClickListener {
    void onCardClick(View view, ImWarningMessage content);
}
