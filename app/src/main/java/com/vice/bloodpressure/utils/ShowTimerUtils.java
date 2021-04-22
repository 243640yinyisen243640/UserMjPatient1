package com.vice.bloodpressure.utils;

import android.content.Context;
import android.os.CountDownTimer;

import com.vice.bloodpressure.R;
import com.wei.android.lib.colorview.view.ColorTextView;


public class ShowTimerUtils {
    private ShowTimerUtils() {
    }

    /**
     * @param view       显示倒计时textView
     * @param latterTime 计时时长
     * @param context
     */
    public static void showTimer(final ColorTextView view, final int latterTime, final Context context) {
        new CountDownTimer(latterTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //设置不能点击
                view.setClickable(false);
                String show = String.format(context.getString(R.string.get_code_latter), millisUntilFinished / 1000);
                view.setText(show);// 设置倒计时时间
            }

            @Override
            public void onFinish() {
                view.setText(R.string.user_send_code);
                //重新获得点击
                view.setClickable(true);
            }
        }.start();

    }

}
