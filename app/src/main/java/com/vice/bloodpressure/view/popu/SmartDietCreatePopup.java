package com.vice.bloodpressure.view.popu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.vice.bloodpressure.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import razerdp.basepopup.BasePopupWindow;

public class SmartDietCreatePopup extends BasePopupWindow {
    public SmartDietCreatePopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        //setBackground(null);
        try {
            // 如果加载的是gif动图，第一步需要先将gif动图资源转化为GifDrawable
            // 将gif图资源转化为GifDrawable
            GifImageView imgGif = findViewById(R.id.img_gif);
            GifDrawable gifDrawable = new GifDrawable(Utils.getApp().getResources(), R.drawable.smart_diet_loading);
            // gif1加载一个动态图gif
            imgGif.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_smart_diet_create);
    }
}
