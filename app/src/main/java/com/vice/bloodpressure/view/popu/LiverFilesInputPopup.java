package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.vice.bloodpressure.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 描述:  弹窗输入
 * 作者: LYD
 * 创建日期: 2020/4/10 14:01
 */
public class LiverFilesInputPopup extends BasePopupWindow {
    public LiverFilesInputPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_liver_files_input);
    }
}
